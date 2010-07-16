package es.indra.util.graficos.generadorGraficos;

import org.w3c.dom.Node;

import es.indra.util.graficos.util.FuncionesCadena;
import es.indra.util.graficos.xml.DocXml;
import es.indra.util.graficos.xml.UtilXml;



/**
 * Interpreta XML que realiza una respuesta del sistema de Gestion
 * de Datos con los datos de un gráfico
 */
public class XMLDatosGrafico extends DocXml{
	
	
	/**
	 * Parsea un objeto DatosGrafico
	 * @return objeto DatosGrafico
	 */
	public void parse(DatosGrafico a_dat) throws Exception{		
		try{
			StringBuffer l_xml = new StringBuffer(8042);
			
			l_xml.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>");
			l_xml.append("\n<DATOS_GRAFICO>");
			
			l_xml.append("\n\t<TITULO>" + FuncionesCadena.normaliza(a_dat.getTituloGrafico()) + "</TITULO>");			
			l_xml.append("\n\t<TITULO_EJEX>" + FuncionesCadena.normaliza(a_dat.getTituloEjeX()) + "</TITULO_EJEX>");
			l_xml.append("\n\t<TITULO_EJEY>" + FuncionesCadena.normaliza(a_dat.getTituloEjeY()) + "</TITULO_EJEY>");								
			l_xml.append("\n\t<TIPO>"+ a_dat.getTipoGrafico() +"</TIPO>");
      l_xml.append("\n\t<COLOR>"+ a_dat.getColor_barra() +"</COLOR>");
			if (a_dat.getEscalaTiempo() != null && a_dat.getEscalaTiempo().length() > 0) 			
				l_xml.append("\n\t<ESCALA>"+ a_dat.getEscalaTiempo() +"</ESCALA>");
			
			l_xml.append("\n\t<LINEAS>");
			int li_lineas = a_dat.getTotalLineas();
			int li_valores=0;			 			
			for (int i=0;i<li_lineas;i++){
			 	l_xml.append("\n\t\t<LINEA>");
			 	Linea l_linea = a_dat.getLinea(i);		 			 			 				 			 		
			 	l_xml.append("\n\t\t\t<TITULO>" + FuncionesCadena.normaliza(l_linea.getTitulo()) + "</TITULO>");
			 	li_valores = l_linea.getTotalValores();
			 	l_xml.append("\n\t\t\t<VALORES>");
			 	for (int j=0;j<li_valores;j++){	
			 		l_xml.append("<VALOR>");
			 		l_xml.append("<X>" + FuncionesCadena.normaliza(l_linea.getValorX(j)) + "</X>");
			 		l_xml.append("<Y>" + FuncionesCadena.normaliza(l_linea.getValorY(j)) + "</Y>");			 		
			 		l_xml.append("</VALOR>");
			 	}	
			 	l_xml.append("\n\t\t\t</VALORES>");
			 	l_xml.append("\n\t\t</LINEA>");
			}	
			l_xml.append("\n\t</LINEAS>");
			l_xml.append("\n</DATOS_GRAFICO>");			
			String ls_xml = l_xml.toString();
		
			parse(ls_xml);			
			
		}catch(Throwable t){
			throw new Exception("Error parseando DatosGrafico: " + t.toString());
		}
	}
		
	/**
	 * Interpreta XML que realiza una respuesta del sistema de Gestion
	 * de Datos con los datos de un gráfico
	 * @return objeto DatosGrafico
	 */
	public DatosGrafico getDatosGrafico() throws Exception{		
		try
		{			
			// Obtener nodo inicial
			Node l_nodo = this.getXml().getFirstChild();
	        while (l_nodo != null && (!l_nodo.getNodeName().equalsIgnoreCase("DATOS_GRAFICO") || l_nodo.getNodeType() != Node.ELEMENT_NODE))
    	    {
	        	l_nodo = UtilXml.getNextSibling(l_nodo);
	        }	             	        	      
			 
			// Recorremos los hijos e insertamos los valores correspondientes
	        DatosGrafico l_dat = new DatosGrafico();
			Node l_child = UtilXml.getFirstChild(l_nodo); 			
			while (l_child != null)
			{
				if (l_child.getNodeName().equalsIgnoreCase("TITULO")) {
          if (UtilXml.getFirstChild(l_child) != null)
            l_dat.setTituloGrafico(UtilXml.getFirstChild(l_child).getNodeValue());
				}else if (l_child.getNodeName().equalsIgnoreCase("TITULO_EJEX")) {
					if (UtilXml.getFirstChild(l_child) != null)
						l_dat.setTituloEjeX(UtilXml.getFirstChild(l_child).getNodeValue());
				}else if (l_child.getNodeName().equalsIgnoreCase("TITULO_EJEY")) {
					if (UtilXml.getFirstChild(l_child) != null)
						l_dat.setTituloEjeY(UtilXml.getFirstChild(l_child).getNodeValue());
				}else if (l_child.getNodeName().equalsIgnoreCase("TIPO")) {
					l_dat.setTipoGrafico(UtilXml.getFirstChild(l_child).getNodeValue());
        }else if (l_child.getNodeName().equalsIgnoreCase("COLOR")) {
					l_dat.setColor_barra(UtilXml.getFirstChild(l_child).getNodeValue());
        }else if (l_child.getNodeName().equalsIgnoreCase("ESCALA")) {
					if (UtilXml.getFirstChild(l_child) != null)
						l_dat.setEscalaTiempo(UtilXml.getFirstChild(l_child).getNodeValue());
				}else if (l_child.getNodeName().equalsIgnoreCase("LINEAS")) {
					getLineas(l_child,l_dat);
				}				
				l_child = UtilXml.getNextSibling(l_child);
			}
	        return l_dat;
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Peticion Grafico: " + ex.toString());			
		}					
	}
	
	private void getLineas(Node a_nodo,DatosGrafico a_pet) throws Exception{		
		try
		{										        	        	       			 	     
			Node l_child = UtilXml.getFirstChild(a_nodo);			
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("LINEA")) getLinea(l_child,a_pet);
				l_child = UtilXml.getNextSibling(l_child);
	        }
	        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Lineas: " + ex.toString());			
		}					
	}
	
	private void getLinea(Node a_nodo,DatosGrafico a_pet) throws Exception{		
		try
		{				
			Node l_child = UtilXml.getFirstChild(a_nodo);		
			String ls_nombre=null;
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("TITULO")){
              if (UtilXml.getFirstChild(l_child) != null) ls_nombre = UtilXml.getFirstChild(l_child).getNodeValue();    	    		
				}else if (l_child.getNodeName().equalsIgnoreCase("VALORES")){
    	    		getValores(l_child,ls_nombre,a_pet);    	    		
				}
				l_child = UtilXml.getNextSibling(l_child);
	        }				        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Linea: " + ex.toString());			
		}					
	}
	
	private void getValores(Node a_nodo,String as_tituloLinea,DatosGrafico a_pet) throws Exception{		
		try
		{										        	
			Linea l_linea = a_pet.nuevaLinea();
			l_linea.setTitulo(as_tituloLinea);
			
			Node l_child = UtilXml.getFirstChild(a_nodo);			
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("VALOR")) getValor(l_child,l_linea);
				l_child = UtilXml.getNextSibling(l_child);
	        }
	        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Valores: " + ex.toString());			
		}					
	}
	
	private void getValor(Node a_nodo,Linea a_linea) throws Exception{		
		try
		{				
			Node l_child = UtilXml.getFirstChild(a_nodo);		
			String ls_x=null,ls_y=null;
			while (l_child != null)
    	    {
    	    	if (l_child.getNodeName().equalsIgnoreCase("X")){
    	    		ls_x = UtilXml.getFirstChild(l_child).getNodeValue();    	    		
				}else if (l_child.getNodeName().equalsIgnoreCase("Y")){
					ls_y = UtilXml.getFirstChild(l_child).getNodeValue();    	    		
				}
				l_child = UtilXml.getNextSibling(l_child);
	        }					
			if (ls_x == null) ls_x = "";
			if (ls_y == null) ls_y = "";
			a_linea.addValor(ls_x,ls_y);	        
		}
		catch (Throwable ex)
		{
	      	throw new Exception("Error leyendo Valor: " + ex.toString());			
		}					
	}
	
		
}
