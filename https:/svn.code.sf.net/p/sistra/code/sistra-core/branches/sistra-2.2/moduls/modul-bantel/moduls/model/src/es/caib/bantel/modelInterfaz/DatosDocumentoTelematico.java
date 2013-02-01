package es.caib.bantel.modelInterfaz;

import java.io.Serializable;

import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.util.HashMapIterable;

/**
 * 
 * Datos del documento referentes a su presentación telemática. 
 * Permite acceder a las propiedades y a los datos del documento (el documento se extrae del RDS a partir de su referencia RDS).
 * Para los documentos estructurados (formularios) existen utilidades para generar una Hash para poder recorrer
 * los campos del formulario mediante su XPath.
 *   
 */
public class DatosDocumentoTelematico implements Serializable { 				
		
		/**
		 * Identificador del documento. Compone junto con el numero de instancia la Referencia del documento dentro del asiento registral 
		 */
		private String identificador;
		/**
		 * Número de instancia del documento. Compone junto con el identificador la Referencia del documento dentro del asiento registral 
		 */
		private int numeroInstancia=1;
		/**
		 * Código de la Referencia RDS del fichero
		 */
		private long codigoReferenciaRds;
		/**
		 * Clave de la Referencia RDS del fichero
		 */		
	    private String claveReferenciaRds;
	    /**
	     * En caso de que el RDS este sincronizado con un gestor documental, indicara la referencia del documento en el Gestor Documental
	     */
	    private String referenciaGestorDocumental;
	    /**
	     * Nombre del fichero
	     */	    
	    private String nombre;
	    /**
	     * Extensión del fichero
	     */	    	    
	    private String extension;
	    /**
	     * Contenido del fichero
	     */	    	    
	    private byte[] content;
	    /**
	     * Firmas asociadas al fichero
	     */	    	    
	    private FirmaIntf[] firmas;
	    /**
	     * Indica si el documento es estructurado (XML)
	     */
	    private boolean estructurado;
	    /**
	     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
	     */
	    private String codigoDocumentoCustodia;
		
	    
	    // UTILIDADES
	    /**
	     * Para documentos estructurados construye una Hash que permite recorrer los campos del documento mediante su Xpath
	     */
	    public HashMapIterable toHashMapIterable() throws Exception{
	    	if (!estructurado) throw new Exception("El documento no es estructurado");
	    	String xml = new String(content,ConstantesXML.ENCODING);
	    	Analizador analiza = new Analizador ();
	    	return analiza.analizar(xml);	    	
	    }
	    
	    
	    // GETTERS / SETTERS
	    /**
		 * Código de la Referencia RDS del fichero
		 */
		public long getCodigoReferenciaRds() {
			return codigoReferenciaRds;
		}
		/**
		 * Clave de la Referencia RDS del fichero
		 */	
		public String getClaveReferenciaRds() {
			return claveReferenciaRds;
		}
		/**
		 * Clave de la Referencia RDS del fichero
		 */	
		public void setClaveReferenciaRds(String claveReferenciaRds) {
			this.claveReferenciaRds = claveReferenciaRds;
		}
		/**
	     * Contenido del fichero
	     */	
		public byte[] getContent() {
			return content;
		}
		/**
	     * Contenido del fichero
	     */	
		public void setContent(byte[] content) {
			this.content = content;
		}
		/**
	     * Indica si el documento es estructurado (XML)
	     */
		public boolean isEstructurado() {
			return estructurado;
		}
		/**
	     * Indica si el documento es estructurado (XML)
	     */
		public void setEstructurado(boolean estructurado) {
			this.estructurado = estructurado;
		}
		/**
	     * Extensión del fichero
	     */	
		public String getExtension() {
			return extension;
		}
		/**
	     * Extensión del fichero
	     */	
		public void setExtension(String extension) {
			this.extension = extension;
		}
		/**
	     * Firmas asociadas al fichero
	     */	
		public FirmaIntf[] getFirmas() {
			return firmas;
		}
		/**
	     * Firmas asociadas al fichero
	     */	
		public void setFirmas(FirmaIntf[] firmas) {
			this.firmas = firmas;
		}
		/**
	     * Nombre del fichero
	     */
		public String getNombre() {
			return nombre;
		}
		/**
	     * Nombre del fichero
	     */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		/**
		 * Código de la Referencia RDS del fichero
		 */
		public void setCodigoReferenciaRds(long codigoReferenciaRds) {
			this.codigoReferenciaRds = codigoReferenciaRds;
		}

		/**
		 * Identificador del documento. Compone junto con el numero de instancia la Referencia del documento dentro del asiento registral 
		 */
		public String getIdentificador() {
			return identificador;
		}

		/**
		 * Identificador del documento. Compone junto con el numero de instancia la Referencia del documento dentro del asiento registral 
		 */
		public void setIdentificador(String identificador) {
			this.identificador = identificador;
		}

		/**
		 * Número de instancia del documento. Compone junto con el identificador la Referencia del documento dentro del asiento registral 
		 */
		public int getNumeroInstancia() {
			return numeroInstancia;
		}

		/**
		 * Número de instancia del documento. Compone junto con el identificador la Referencia del documento dentro del asiento registral 
		 */
		public void setNumeroInstancia(int numeroInstancia) {
			this.numeroInstancia = numeroInstancia;
		}

		/**
		 *  En caso de que el RDS este sincronizado con un gestor documental, indicara la referencia del documento en el Gestor Documental.
		 * @return
		 */
		public String getReferenciaGestorDocumental() {
			return referenciaGestorDocumental;
		}

		/**
		 *  En caso de que el RDS este sincronizado con un gestor documental, indicara la referencia del documento en el Gestor Documental.
		 * @param referenciaGestorDocumental
		 */
		public void setReferenciaGestorDocumental(String referenciaGestorDocumental) {
			this.referenciaGestorDocumental = referenciaGestorDocumental;
		}

		/**
	     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
	     * @return
	     */
		public String getCodigoDocumentoCustodia() {
			return codigoDocumentoCustodia;
		}

		/**
	     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
	     * @param codigoDocumentoCustodia
	     */
		public void setCodigoDocumentoCustodia(String codigoDocumentoCustodia) {
			this.codigoDocumentoCustodia = codigoDocumentoCustodia;
		}
		
}
