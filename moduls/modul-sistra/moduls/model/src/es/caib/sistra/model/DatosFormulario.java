package es.caib.sistra.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.util.HashMapIterable;

/**
 * Modeliza el XML de datos de un formulario
 */
public class DatosFormulario implements Serializable{
	/**
	 * Modelo (RDS)
	 */
	private String modelo;
	/**
	 * Versión (RDS)
	 */
	private int version;
	/**
	 * Plantilla específica a utilizar para visualizar el formulario (si nula entonces utiliza por defecto)
	 */
	private String plantilla = null;
	
	/**
	 * Datos cacheados del formulario
	 */
	private HashMapIterable datosFormulario=null; // Contiene los datos del formulario 
	
	/**
	 * Root del xml de formulario
	 */
	private final static String XML_ROOT = "/FORMULARIO";
	/**
	 * Propiedad indice para campos de listas de valores
	 */
	private final static String INDICE_LISTAS = "indice";
	
	
	public DatosFormulario(String modelo,int version){
		this.modelo = modelo;
		this.version = version;
	}
	
	/**
	 * Inicializa el formulario a partir de una Hash (campo/valor)	
	 * @return
	 */
	public void inicializar(Map valores) throws Exception{		
		
		HashMapIterable dat = new HashMapIterable();
		
		if (valores != null){
			String nombre,valor;
			for (Iterator it = valores.keySet().iterator();it.hasNext();){
				nombre = (String) it.next();
				valor = (String) valores.get(nombre);
				Nodo nodo = new Nodo(referenciaXPath(nombre),valor);				
				dat.put(nodo.getXpath(),nodo);
			}
		}
		
		datosFormulario = dat;
		
	}
	
	/**
	 * Obtiene numero de valores de un campo. Si no existe devuelve 0.
	 * @param campo
	 * @return
	 */
	public int getNumeroValoresCampo(String campo) throws Exception{			
		if (datosFormulario == null) throw new Exception("El formulario no esta inicializado");
		Object dato = datosFormulario.get(referenciaXPath(campo));
		if (dato == null) return 0;
		if (dato instanceof List){
			return ((List) dato).size();					
		}else{
			// Solo hay un elemento
			return 1;
		}		
	}
	
	/**
	 * Obtiene valor de un campo. Si no existe devuelve "".
	 * Para campos multivaluados devuelve primer valor
	 * @param campo
	 * @return
	 */
	public String getValorCampo(String campo) throws Exception{				
		return getValorCampo(campo,0);
	}
	
	/**
	 * Obtiene valor de un campo para multivaluados. Si no existe devuelve "".	 
	 * @param campo
	 * @return
	 */
	public String getValorCampo(String campo,int numValor) throws Exception{		
		if (datosFormulario == null) throw new Exception("El formulario no esta inicializado");
		Object dato = datosFormulario.get(referenciaXPath(campo));
		if (dato == null) return "";
		if (dato instanceof List){
			if (numValor >=((List) dato).size()) throw new Exception("No hay valor número " + numValor + "(existen" + ((List) dato).size() + " valores )");
			Nodo val = ((Nodo) ((List) dato).get(numValor));
			return val.getValor();			
		}else{
			// Solo hay un elemento
			if (numValor != 0) throw new Exception("Solo hay un valor para el campo. No hay valor " + numValor);
			Nodo val = (Nodo) dato;
			return val.getValor();
		}		
	}
	
	/**
	 * Obtiene indice para un campo de listas de valores. Si no existe devuelve "".
	 * Para campos multivaluados devuelve primer valor
	 * @param campo
	 * @return
	 */
	public String getIndiceCampo(String campo) throws Exception{		
		return getPropiedadCampo(campo,0,INDICE_LISTAS);
	}
	
	/**
	 * Obtiene indice para un campo de listas de valores para multivaluados. Si no existe devuelve "".
	 * @param campo
	 * @return
	 */
	public String getIndiceCampo(String campo,int numValor) throws Exception{		
		return getPropiedadCampo(campo,numValor,INDICE_LISTAS);
	}
	
	/**
	 * Establece indice para un campo de listas de valores
	 * Para campos multivaluados establece indice para primer valor
	 * @param campo
	 * @return
	 */
	public void setIndiceCampo(String campo,String valor) throws Exception{		
		setPropiedadCampo(campo,0,valor,INDICE_LISTAS);
	}
	
	/**
	 * Establece indice para un campo de listas de valores de un multivaluado
	 * @param campo
	 * @return
	 */
	public void setIndiceCampo(String campo,int numValor,String valor) throws Exception{		
		setPropiedadCampo(campo,numValor,valor,INDICE_LISTAS);
	}
	
	/**
	 * Obtiene propiedad de un campo. Si no existe devuelve ""
	 * Utilizado para acceder a los indices de los campos de listas de valores
	 * @param campo
	 * @return
	 */
	private String getPropiedadCampo(String campo,int numValor,String propiedad) throws Exception{		
		if (datosFormulario == null) throw new Exception("El formulario no esta inicializado");			
				
		Object dato = datosFormulario.get(referenciaXPath(campo));
		if (dato == null) return "";
		Nodo val;
		if (dato instanceof List){
			if (numValor >= ((List) dato).size()) throw new Exception("No hay valor número " + numValor + "(existen" + ((List) dato).size() + " valores )");
			val = ((Nodo) ((List) dato).get(numValor));								
		}else{
			// Solo hay un elemento
			if (numValor != 0) throw new Exception("Solo hay un valor para el campo. No hay valor " + numValor);
			val = (Nodo) dato;			
		}		
		
		if (val.getAtributos() == null) return "";
		
		for (Iterator it = val.getAtributos().iterator();it.hasNext();){
			Par atributo = (Par) it.next();
			if (atributo.getNombre().equals(propiedad)){
				return atributo.getValor();
			}
		}		
		return "";
	}
	
	/**
	 * Establece propiedad de un campo.
	 * Utilizado para acceder a los indices de los campos de listas de valores
	 * @param campo
	 * @return
	 */
	private void setPropiedadCampo(String campo,int numValor,String valor,String propiedad) throws Exception{		
		if (datosFormulario == null) throw new Exception("El formulario no esta inicializado");				
		
		// Buscamos nodo
		String xpath = referenciaXPath(campo);
		
		Object dato = datosFormulario.get(referenciaXPath(campo));
		Nodo val;
		
		// Si no existe creamos Nodo
		if (dato == null) {			
			val = new Nodo(campo,"");
			val.setXpath(xpath);
			datosFormulario.put(val.getXpath(),val);
		}else{
			// Si existe accedemos al nodo
			if (dato instanceof List){
				if (numValor >= ((List) dato).size()) throw new Exception("No hay valor número " + numValor + "(existen" + ((List) dato).size() + " valores )");
				val = ((Nodo) ((List) dato).get(numValor));					
			}else{
				// Solo hay un elemento
				if (numValor != 0) throw new Exception("Solo hay un valor para el campo. No hay valor " + numValor);
				val = (Nodo) dato;			
			}	
		}
				
		// Buscamos atributo
		boolean enc = false;
		for (Iterator it = val.getAtributos().iterator();it.hasNext();){
			Par atributo = (Par) it.next();
			if (atributo.getNombre().equals(propiedad)){
				enc = true;
				atributo.setValor(valor);
			}
		}		
		
		// Si no existe atributo lo creamos
		if (!enc) {
			Par atributo = new Par(propiedad,valor);
			val.getAtributos().add(atributo);
		}
		
	}
		
	/**
	 * Establece valor de un campo.
	 * Para campos multivaluados establece valor del primer campo
	 * @param campo
	 * @param valor
	 */	
	public void setValorCampo(String campo,String valor) throws Exception{		
		setValorCampo(campo,0,valor); 
	}
	
	/**
	 * Establece valor de un campo multivaluado.
	 * Para campos multivaluados establece valor del primer campo
	 * @param campo
	 * @param valor
	 */	
	public void setValorCampo(String campo,int numValor,String valor) throws Exception{		
		if (datosFormulario == null) throw new Exception("El formulario no esta inicializado");				
		
		// Buscamos nodo
		String xpath = referenciaXPath(campo);
		Object dato = datosFormulario.get(xpath);
		Nodo val;
		
		// Si no existe creamos Nodo
		if (dato == null) {			
			val = new Nodo(campo,valor);
			val.setXpath(xpath);
			datosFormulario.put(val.getXpath(),val);
		}else{
			// Si existe accedemos al nodo
			if (dato instanceof List){
				
				// Comprobamos si estamos añadiendo un valor: numValor = numeroValores + 1
				if (numValor < ((List) dato).size())				
					val = ((Nodo) ((List) dato).get(numValor));
				else if (numValor == ((List) dato).size()){
					// Añadimos nodo
					val = new Nodo(campo,"");
					val.setXpath(xpath);
					((List) dato).add(val);									
				}else{
					throw new Exception("No existe valor " + numValor + ". Para añadir un nodo hay que establecer numero valor = numeroValores + 1");
				}					
			}else{
				// Solo hay un elemento
				if (numValor != 0) throw new Exception("Solo hay un valor para el campo. No hay valor " + numValor);
				val = (Nodo) dato;			
			}
			
			// Una vez obtenido el valor, establecemos valor
			val.setValor(valor);
		}	
		
	}
	
	/**
	 * Obtiene los datos del formulario en bytes
	 * @return byte[]
	 */
	public byte[] getBytes() throws Exception{		
		String datos = getString();
		return datos.getBytes(ConstantesXML.ENCODING);
	}
	
	
	/**
	 * Establece los datos del formulario a partir de una array de bytes
	 * @param datos
	 */
	public void setBytes(byte[] datos) throws Exception{
		try{
			// Parseamos los datos del formulario a la hash			
			Analizador analizador = new Analizador ();			
	    	datosFormulario = analizador.analizar ( new ByteArrayInputStream(datos), ConstantesXML.ENCODING );	    
	    	
	    	// Si el formulario esta vacío <FORMULARIO></FORMULARIO> no debe haber nada en la hash
	    	if (datosFormulario.size() == 1 && ((String) datosFormulario.keySet().iterator().next()).equals(XML_ROOT)){
	    		datosFormulario.remove(XML_ROOT);
	    	}
	    	
		}catch (Exception e){			
			throw new Exception("Error al establecer datos de formulario: " + e.toString());
		}		
	}
	
	/**
	 * Establece los datos del formulario a partir de un String
	 * @param datos
	 */
	public void setString(String datos) throws Exception  {		
		try{
			// Parseamos los datos del formulario a la hash
			setBytes(datos.getBytes(ConstantesXML.ENCODING));						
		}catch (Exception e){			
			throw new Exception("Error al establecer datos de formulario: " + e.toString());
		}
	}
	
	/**
	 * Obtiene datos del formulario como un String
	 * @param datosFormulario
	 */
	public String getString() throws Exception {		
		String xml = "";
		
		// Generamos XML a partir de la hash en caso de que haya elementos (no este solo el XML_ROOT)
		// Si no generamos xml vacío, ya que nos aseguramos que el xml vacío se genera siempre de la misma forma
		if (
				this.datosFormulario.size() > 0 &&
				!(this.datosFormulario.size() == 1 && this.datosFormulario.get(XML_ROOT) != null)
			){
			
				Generador generador = new Generador();
				generador.setEncoding(ConstantesXML.ENCODING);
				xml = generador.generarXML(this.datosFormulario);
		}
		
		// Generamos cabecera (substituimos <XMLROOT> por <XMLROOT modelo="xx" version="y" [plantilla="zzz"]>		
		String root = XML_ROOT.substring(1);		
		String plant = "";
		if (this.getPlantilla() != null){
			plant = " plantilla=\"" + this.getPlantilla() + "\" ";
		}
		if (xml.length() > 0){
			// Generación cabecera xml
			int pos = xml.indexOf(root); 
			int posFin = xml.indexOf(">",pos);
			if (pos == -1 || posFin == -1) throw new Exception("No se encuentra tag raíz: " + root + " en xml de formulario: \n " + xml);
			xml = xml.substring(0,pos) + root +
					" modelo=\"" + this.modelo + "\" version=\"" + this.version + "\" " + plant + xml.substring(posFin);
		}else{
			// Generación de xml vacío
			xml = "<?xml version=\"1.0\" encoding=\""+ ConstantesXML.ENCODING +"\"?>\n<"+ root +
					" modelo=\"" + this.modelo + "\" version=\"" + this.version + plant + "\"></" + root + ">";
		}		
		return xml;
	}
	
	/**
	 * Convierte referencia campo del tipo seccion1.campo1 a expresión XPath /instancia/seccion1/campo1 
	 * @param referenciaCampo
	 * @return
	 */
	public static String referenciaXPath(String referenciaCampo){
		return referenciaCampo = XML_ROOT + "/" + referenciaCampo.replaceAll("\\.","/");
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	
	
	// Extrae la plantilla del xml (Devuelve null si no tiene)
	public static String extraerPlantilla(String xml) throws Exception{    		    	
		if (xml != null &&  xml.length() > 0){    			
			// Extraemos plantilla
			String root = DatosFormulario.XML_ROOT.substring(1);
			int pos = xml.indexOf(root); 
			int posFin = xml.indexOf(">",pos);
			if (pos == -1 || posFin == -1) throw new Exception("No se encuentra tag raíz: " + root + " en xml de formulario: \n " + xml);
			String plant = " plantilla=\"";	    			
			int posPlan = xml.indexOf(plant);			
			if (posPlan > 0 && posPlan < posFin){
				int posPlanIni = posPlan + plant.length();
				int posPlanFin = xml.indexOf("\"",posPlanIni + 1);
				return xml.substring(posPlanIni,posPlanFin);    				
			}	    		    				    		
		}			    		
		return null;
	}
		
}
