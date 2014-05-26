package es.indra.util.graficos.generadorGraficos;

import java.util.Iterator;

import org.w3c.dom.Node;

import es.indra.util.graficos.util.FuncionesCadena;
import es.indra.util.graficos.xml.DocXml;
import es.indra.util.graficos.xml.UtilXml;


/**
 * Interpreta XML que realiza una petición al sistema de Gestion
 * de Datos para resolver los datos de un gráfico
 */
public class XMLPeticionGrafico extends DocXml{

	public XMLPeticionGrafico() {
		super();	
	}
	
	/**
	 * Parsea un objeto PeticionGrafico
	 * @return objeto PeticionGrafico
	 */
	public void parse(PeticionGrafico a_dat) throws Exception{		
		try{
			StringBuffer l_xml = new StringBuffer(8042);
			String ls_nombre;
			
			l_xml.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");
			l_xml.append("\n<PETICION_GRAFICO>");
			
			l_xml.append("\n\t<ID>" + FuncionesCadena.normaliza(a_dat.getIdGrafico()) + "</ID>");						
			l_xml.append("\n\t<PARAMETROS>");
			
			Iterator l_it = a_dat.getParametros().keySet().iterator();
			
			while (l_it.hasNext()){
				ls_nombre = (String) l_it.next();				
			 	l_xml.append("\n\t\t<PARAMETRO>");
		 		l_xml.append("<NOMBRE>" + FuncionesCadena.normaliza(ls_nombre) + "</NOMBRE>");
		 		l_xml.append("<VALOR>" + FuncionesCadena.normaliza((String) a_dat.getParametros().get(ls_nombre)) + "</VALOR>");			 		
			 	l_xml.append("\n\t\t</PARAMETRO>");
			}	
			l_xml.append("\n\t</PARAMETROS>");
			l_xml.append("\n</PETICION_GRAFICO>");			
			String ls_xml = l_xml.toString();
		
			parse(ls_xml);			
			
		}catch(Throwable t){
			throw new Exception("Error parseando PeticionGrafico: " + t.toString());
		}
	}
	
	/**
	 * Interpreta XML obteniendo los datos de la petición del 
	 * gráfico en un objeto PeticionGrafico
	 * @return objeto PeticionGrafico
	 */
	public PeticionGrafico getPeticionGrafico() throws Exception{		
		try
		{			
			// Obtener nodo inicial
			Node l_nodo = this.getXml().getFirstChild();
	        while (l_nodo != null && (!l_nodo.getNodeName().equalsIgnoreCase("PETICION_GRAFICO") || l_nodo.getNodeType() != Node.ELEMENT_NODE))
    	    {
	        	l_nodo = UtilXml.getNextSibling(l_nodo);
	        }	             	        	      
			 
			// Recorremos los hijos e insertamos los valores correspondientes
	        PeticionGrafico l_pet = new PeticionGrafico();
			Node l_child = UtilXml.getFirstChild(l_nodo); 			
			while (l_child != null)
			{
				if (l_child.getNodeName().equalsIgnoreCase("ID")) {
					l_pet.setIdGrafico(UtilXml.getFirstChild(l_child).getNodeValue());
				}else if (l_child.getNodeName().equalsIgnoreCase("PARAMETROS")) {
					getParametros(l_child,l_pet);
				}				
				l_child = UtilXml.getNextSibling(l_child);
			}
	        return l_pet;
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Peticion Grafico: " + ex.toString());			
		}					
	}
	
	private void getParametros(Node a_nodo,PeticionGrafico a_pet) throws Exception{		
		try
		{										        	        	       			 	     
			Node l_child = UtilXml.getFirstChild(a_nodo);			
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("PARAMETRO")) getParametro(l_child,a_pet);
				l_child = UtilXml.getNextSibling(l_child);
	        }
	        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Parametros: " + ex.toString());			
		}					
	}
	
	private void getParametro(Node a_nodo,PeticionGrafico a_pet) throws Exception{		
		try
		{				
			Node l_child = UtilXml.getFirstChild(a_nodo);		
			String ls_nombre=null,ls_valor=null;
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("NOMBRE")){
    	    		ls_nombre = UtilXml.getFirstChild(l_child).getNodeValue();    	    		
				}else if (l_child.getNodeName().equalsIgnoreCase("VALOR")){
    	    		ls_valor = UtilXml.getFirstChild(l_child).getNodeValue();    	    		
				}
				l_child = UtilXml.getNextSibling(l_child);
	        }
			if (ls_nombre==null || ls_valor==null) throw new Exception("Error leyendo Parametro: No se ha especificado nombre o valor de un parametro");			
			a_pet.addParametro(ls_nombre,ls_valor);
	        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Parametro: " + ex.toString());			
		}					
	}
	
	
}
