package es.caib.redose.persistence.formateadores;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.util.HashMapIterable;
import es.indra.util.pdf.PDFDocumentTemplate;

/**
 * Generador de PDFs para XMLs de Formularios 
 *
 */
public class FormateadorPdfFormularios implements FormateadorDocumento{
	
	protected static final String CODIGO_LISTAS = "[CODIGO]";
	protected final static String INDICE_LISTAS = "indice";
	
	private Map datosIniciales = new HashMap(); // Permite establecer datos en el map que se utiliza para establecer los datos en el PDF
	
	private boolean soloLectura = true; // Establecemos una variable para que si se hereda se pueda modificar si se deja el formulario a solo lectura. 
	
	/**
	 * Genera PDF a partir Xpath
	 */
	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception {
		
		// Parseamos xml
		Analizador analizador = new Analizador ();					
    	HashMapIterable datosFormulario = analizador.analizar(new ByteArrayInputStream(documento.getDatosFichero()),ConstantesXML.ENCODING);	
		
    	// Creamos Map de valores
    	HashMap datos = new HashMap();
    	    	
    	// Cargamos datos desde xml 
    	for (Iterator it=datosFormulario.iterator();it.hasNext();){
    		
    		Object dat = it.next();
    		
    		// Comprobamos si es un multivaluado
    		String value="";
    		String index="";
    		String refCampo="";
    		boolean primer = true,indexed =false;
    		if (dat instanceof List){
    			for (Iterator it2 = ((List)dat).iterator();it2.hasNext();){
    				Nodo nodo = (Nodo) it2.next();
    				
    				if (!primer) {
    					value += ", ";
    					index += ", ";
    				}
    				else {
    					refCampo = referenciaCampo(nodo.getXpath());
    					primer = false;
    				}
    				
    				value += nodo.getValor();
    				
            		// En caso de ser lista de valores buscamos atributo de codigo
            		if (nodo.getAtributos() != null && nodo.getAtributos().size() > 0){
            			indexed = true;
            			for (Iterator it3 = nodo.getAtributos().iterator();it3.hasNext();){
            				Par atributo = (Par) it3.next();
            				if (atributo.getNombre().equals(INDICE_LISTAS)){
            					index += atributo.getValor();
            				}
            			}	
            		}
    			}
    			
    			datos.put(refCampo,value);
    			if (indexed) datos.put(refCampo + CODIGO_LISTAS ,index);
    			
    		}else{
    			// Valor unico
    			Nodo nodo = (Nodo) dat;
        		datos.put(referenciaCampo(nodo.getXpath()),nodo.getValor());
        		
        		// En caso de ser lista de valores buscamos atributo de codigo
        		if (nodo.getAtributos() != null && nodo.getAtributos().size() > 0){
        			for (Iterator it2 = nodo.getAtributos().iterator();it2.hasNext();){
        				Par atributo = (Par) it2.next();
        				if (atributo.getNombre().equals(INDICE_LISTAS)){
        					datos.put(referenciaCampo(nodo.getXpath()) + CODIGO_LISTAS,atributo.getValor());
        				}
        			}	
        		}   
    		}
    		 		
    	}
    	
    	// Cargamos datos iniciales (extendido por formateadores)
    	for (Iterator it=datosIniciales.keySet().iterator();it.hasNext();){
    		String key = (String) it.next();   		
    		datos.put(key,datosIniciales.get(key));
    	}
    	
    	// Obtenemos usos del documento para ver si tiene usos asociados a registro/envio/preregistro
    	UsoRDS uso = UtilRDS.obtenerNumeroEntrada(usos);
    	
    	// Si no hay entrada intentamos obtener numero salida
    	if (uso == null){
    		uso = UtilRDS.obtenerNumeroSalida(usos);
    	}
    	
    	if (uso != null){
	    	datos.put(XPATH_REGISTRONUMERO,uso.getReferencia());
	    	if (uso.getTipoUso().startsWith("PRE")){    		
	    		datos.put(XPATH_DIGITOCONTROL,StringUtil.calculaDC(uso.getReferencia()));
	    	}
	    	if (uso.getFechaSello() != null){
	    		datos.put(XPATH_FECHAREGISTRO,StringUtil.fechaACadena(uso.getFechaSello(),"dd/MM/yyyy HH:mm"));
	    	}
    	}
    	 
    	// Establecemos funcion para poder tratar los datos en formateadores que extiendan de esta clase
    	tratarDatos(datos);
    	
    	
		// Creamos pdf a partir plantilla y establecemos valores
		PDFDocumentTemplate pdf = new PDFDocumentTemplate(plantilla.getArchivo().getDatos());
		if (isSoloLectura()) {
			pdf.establecerSoloImpresion();
		}
		pdf.ponerValor(datos);
		
		// Devolvemos pdf generado
		DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
		documentoF.setDatosFichero(pdf.guardarEnMemoria());		
		documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");		
		
		return documentoF;
	}

	
	/**
	 * Funcion para poder tratar los datos en formateadores que extiendan de esta clase
	 * @param datos Datos
	 * @throws Exception 
	 */
	protected void tratarDatos(HashMap datos) throws Exception {
		// Por defecto no se trata nada		
	}

	/**
	 * Convierte referencia campo de expresión XPath /instancia/seccion1/campo1 al tipo seccion1.campo1 
	 * @param referenciaCampo
	 * @return
	 */
	protected String referenciaCampo(String referenciaXPath){		
		return referenciaXPath.substring(referenciaXPath.indexOf("/",1) + 1).replaceAll("/","\\.");
	}
	
	/**
	 * Añade dato que se añadirá al map que se introducira como datos en el pdf
	 * @param referenciaCampo
	 * @param valor
	 */
	protected void addDato(String referenciaCampo,String valor){
		datosIniciales.put(referenciaCampo,valor);
	}

	/**
	 * Indica si se dejara el PDF como solo lectura.
	 * @return
	 */
	protected boolean isSoloLectura() {
		return soloLectura;
	}

	/**
	 * Indica si se dejara el PDF como solo lectura.
	 * @return
	 */
	protected void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

		
}
