package es.caib.sistra.modelInterfaz;

import java.io.Serializable;

/**
 * Clase que modeliza documentos que pueden devolverse en un trámite de tipo consulta
 *<br/>
 * Estos podrán ser:
 * <br/>
 * <ul>
 * 	<li>referencia RDS del documento</li>
 *  <li>XML + Modelo + Versión + Plantilla de visualización</li>
 *  <li>documento binario </li>
 *  <li>url acceso</li>
 *  </ul>
 */
public class DocumentoConsulta implements Serializable{

	/**
	 * Documento RDS
	 */
	public final static char TIPO_DOCUMENTO_RDS = 'R';
	/**
	 * Documento XML
	 */
	public final static char TIPO_DOCUMENTO_XML = 'X';
	/**
	 * Documento binario
	 */
	public final static char TIPO_DOCUMENTO_BIN = 'B';
	/**
	 * Url
	 */
	public final static char TIPO_DOCUMENTO_URL = 'U';
	
	/**
	 * Tipo de documento (rds,xml,binario o url)
	 */
	private char tipoDocumento;
	/**
	 * Nombre del documento
	 */
	private String nombreDocumento;	
	/**
	 * Para TIPO_DOCUMENTO_RDS indica codigo referencia RDS del documento 
	 */
	private long codigoRDS;
	/**
	 * Para TIPO_DOCUMENTO_RDS indica clave referencia RDS del documento 
	 */
	private String claveRDS;	
	/**
	 * Para TIPO_DOCUMENTO_XML indica el documento XML a visualizar 
	 */
	private String xml;
	/**
	 * Para TIPO_DOCUMENTO_XML indica el modelo de documento 
	 */
	private String modelo;
	/**
	 * Para TIPO_DOCUMENTO_XML indica la versión de documento
	 */
	private int version;
	/**
	 * Para TIPO_DOCUMENTO_XML indica la plantilla de documento XML a visualizar 
	 */
	private String plantilla;
	/**
	 * Para TIPO_DOCUMENTO_BIN indica los datos del documento
	 */
	private byte[] contenidoFichero;
	/**
	 * Para TIPO_DOCUMENTO_BIN indica el nombre del fichero (con extensión) 
	 */
	private String nombreFichero;
	/**
	 * Para TIPO_DOCUMENTO_URL indica la url de acceso 
	 */
	private String urlAcceso;
	/**
	 * Para TIPO_DOCUMENTO_URL indica si se abre en nueva ventana 
	 */
	private boolean urlNuevaVentana;
	
	/**
	 * Establece contenido del fichero (para documento de tipo binario)
	 * @return
	 */
	public byte[] getContenidoFichero() {
		return contenidoFichero;
	}
	/**
	 * Establece contenido del fichero (para documento de tipo binario)
	 * @param contenidoFichero
	 */
	public void setContenidoFichero(byte[] contenidoFichero) {
		this.contenidoFichero = contenidoFichero;
	}
	/**
	 * Modelo RDS (para documentos de tipo RDS y XML)
	 * @return
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Modelo RDS (para documentos de tipo RDS y XML)
	 * @param modelo
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	/**
	 * Nombre del fichero con su extensión (para documentos binarios)
	 * @return
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * Nombre del fichero con su extensión (para documentos binarios)
	 * @param nombreFichero
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	/**
	 * Establece plantilla RDS de visualización
	 * @return
	 */
	public String getPlantilla() {
		return plantilla;
	}
	/**
	 * Establece plantilla RDS de visualización
	 * @param plantilla
	 */
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}	
	/**
	 * Tipo de documento (RDS,XML,binario o url)
	 * @return
	 */
	public char getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * Tipo de documento (RDS,XML,binario o url)
	 * @param tipoDocumento
	 */
	public void setTipoDocumento(char tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * Versión del modelo RDS (para documentos de tipo RDS y XML)
	 * @return
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * Versión del modelo RDS (para documentos de tipo RDS y XML)
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * Contenido del XML (para documentos de tipo XML)
	 * @return
	 */
	public String getXml() {
		return xml;
	}
	/**
	 * Contenido del XML (para documentos de tipo XML)
	 * @param xml
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
	/**
	 * Url acceso (para documentos de tipo url)
	 * @return
	 */
	public String getUrlAcceso() {
		return urlAcceso;
	}
	/**
	 * Url acceso (para documentos de tipo url)
	 */
	public void setUrlAcceso(String urlAcceso) {
		this.urlAcceso = urlAcceso;
	}
	/**
	 * Nombre descriptivo del documento
	 * @return
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	/**
	 * Nombre descriptivo del documento	
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	/**
	 * Clave RDS (para documentos de tipo RDS)
	 * @return
	 */
	public String getClaveRDS() {
		return claveRDS;
	}
	/**
	 * Clave RDS (para documentos de tipo RDS)
	 */
	public void setClaveRDS(String claveRDS) {
		this.claveRDS = claveRDS;
	}
	
	/**
	 * Código RDS (para documentos de tipo RDS)
	 * @return
	 */
	public long getCodigoRDS() {
		return codigoRDS;
	}
	/**
	 * Código RDS (para documentos de tipo RDS)
	 * @param codigoRDS
	 */
	public void setCodigoRDS(long codigoRDS) {
		this.codigoRDS = codigoRDS;
	}
	/**
	 * Indica si la url debe abrirse en una ventana nueva (para documentos de tipo url)
	 * @return
	 */
	public boolean isUrlNuevaVentana() {
		return urlNuevaVentana;
	}
	/**
	 * Indica si la url debe abrirse en una ventana nueva (para documentos de tipo url) 
	 */
	public void setUrlNuevaVentana(boolean urlNuevaVentana) {
		this.urlNuevaVentana = urlNuevaVentana;
	}
	
}
