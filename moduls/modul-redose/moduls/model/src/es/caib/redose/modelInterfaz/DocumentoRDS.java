package es.caib.redose.modelInterfaz;

import java.util.Date;

import es.caib.sistra.plugins.firma.FirmaIntf;

/**
 * Clase que modeliza un Documento en la comunicaci�n de otros m�dulos con el RDS.
 *  <br/>
 * El RDS tiene registrado los tipos de documentos que admite: se definen modelos y versiones de un modelo.
 *  <br/>
 * Al insertar un documento en el RDS se debe indicar de que modelo-versi�n se trata.
 * 
 * 
 */
public class DocumentoRDS  implements java.io.Serializable {
	
	/**
	 * Referencia RDS del documento
	 */
	private ReferenciaRDS referenciaRDS;
	/**
	 * Modelo del documento
	 */
	private String modelo;
	/**
	 * Versi�n
	 */
	private int version=-1;
	/**
	 * Titulo del documento
	 */	
	private String titulo;
	/**
	 * Nif de la persona que inserta el documento en el RDS
	 */
	private String nif;
	/**
	* Usuario Seycon de la persona que inserta el documento en el RDS (en caso de estar autenticado)
	*/
	private String usuarioSeycon;
	/**
	 * Unidad Administrativa responsable del documento
	 */
	private long unidadAdministrativa=-1;
	/**
	 * Nombre del fichero asociado al documento
	 */
	private String nombreFichero;
	/**
	 * Extensi�n del fichero asociado al documento
	 */
	private String extensionFichero;
	/**
	 * Datos del fichero asociado al documento
	 */
	private byte[] datosFichero;
	/**
	 * Firmas del documento
	 */
	private FirmaIntf[] firmas;
	/**
	 * C�digo de ubicaci�n d�nde se almacenar� el documento.
	 * Por defecto se utiliza la por defecto del RDS.
	 */
	private String codigoUbicacion;		
	
	// Datos establecidos por el RDS
	/**
	 * Fecha de introducci�n en el RDS. Este dato es calculado por el RDS.
	 */	
	private Date fechaRDS; 
	/**
	 * Indica si el documento es estructurado (XML). Este dato es de solo lectura y depende del modelo del documento.
	 */
	private boolean estructurado; 
	/**
	 * Hash del fichero calculado por el RDS. Este dato es de solo lectura y es calculado por el RDS.
	 */
	private String hashFichero;
	/**
	 * C�digo plantilla a utilizar para visualizar el documento estructurado.
	 * Si no se especifica utiliza la plantilla por defecto
	 */
	private String plantilla=null;
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificaci�n (en caso de tenerla). Este dato es de solo lectura y es calculado por el RDS.
	 */	
	private String urlVerificacion=null;
	/**
	 * Para documentos estructurados indica el idioma para formatear el documento
	 */
	private String idioma;
	/**
	 * En caso de que el documento este consolidado en un gestor documental, se indica la referencia en el gestor documental. Este dato es de solo lectura y es establecido por el RDS.
	 */
	private String referenciaGestorDocumental;
	/**
     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
     */
	private String codigoDocumentoCustodia;
	/**
	 * Indica CSV del documento (campo calculado por el REDOSE en caso de que tenga CSV).
	 */
	private String csv;
	/**
	 * Indica si el documento tiene plantilla de visualizaci�n asociada
	 */
	private boolean plantillaVisualizacion;

	// Getters / Setters
	/**
	 * Indica si el documento es estructurado (XML).Este dato es establecido por el RDS seg�n el modelo del documento.
	 */
	public boolean isEstructurado() {
		return estructurado;
	}
	/**
	 * Indica si el documento es estructurado (XML). Este dato es establecido por el RDS seg�n el modelo del documento.
	 * @param estructurado
	 */
	public void setEstructurado(boolean estructurado) {
		this.estructurado = estructurado;
	}
	/**
	 * Obtiene modelo del documento
	 * @return C�digo de modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Establece el modelo del documento. Cualquier documento del RDS debe pertenecer a una versi�n de un modelo registrado.
	 * @param modelo C�digo de modelo
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	/**
	 * Obtiene nif del responsable del documento
	 * @return
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * Establece nif del propietario del documento
	 * @param nif Nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * Obtiene t�tulo del documento
	 * @return T�tulo documento
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Establece t�tulo del documento
	 * @param titulo T�tulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Obtiene Unidad Administrativa responsable del documento
	 * @return C�digo unidad administrativa
	 */
	public long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	/**
	 * Establece Unidad Administrativa responsable del documento
	 * @param unidadAdministrativa C�digo unidad administrativa
	 */
	public void setUnidadAdministrativa(long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	/**
	 * Obtiene usuario seycon propietario del documento (en caso de estar autenticado)
	 * @return Usuario seycon
	 */
	public String getUsuarioSeycon() {
		return usuarioSeycon;
	}
	/**
	 * Establece usuario seycon propietario del documento (en caso de estar autenticado)
	 * @param usuarioSeycon Usuario seycon
	 */
	public void setUsuarioSeycon(String usuarioSeycon) {
		this.usuarioSeycon = usuarioSeycon;
	}
	/**
	 * Obtiene versi�n. Cualquier documento del RDS debe pertenecer a una versi�n de un modelo registrado.
	 * @return Versi�n
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * Establece versi�n.
	 * @param version Versi�n
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * Obtiene referencia RDS del documento
	 * @return Referencia RDS
	 */
	public ReferenciaRDS getReferenciaRDS() {
		return referenciaRDS;
	}
	/**
	 * Obtiene firmas asociadas al documento
	 * @return Lista de firmas
	 */
	public FirmaIntf[] getFirmas() {
		return firmas;
	}
	/**
	 * Obtiene c�digo de ubicaci�n en la que est� almacenado f�sicamente el documento. Por defecto se utiliza la por defecto del RDS. 
	 * @return C�digo de ubicaci�n
	 */
	public String getCodigoUbicacion() {
		return codigoUbicacion;
	}
	/**
	 * Establece c�digo de ubicaci�n en la que se almacenar� el documento. Por defecto se utiliza la por defecto del RDS.
	 * @param codigoUbicacion
	 */
	public void setCodigoUbicacion(String codigoUbicacion) {
		this.codigoUbicacion = codigoUbicacion;
	}
	/**
	 * Obtiene fichero asociado al documento
	 * @return Datos del fichero
	 */
	public byte[] getDatosFichero() {
		return datosFichero;
	}
	/**
	 * Establece datos del fichero
	 * @param datosFichero Datos fichero
	 */
	public void setDatosFichero(byte[] datosFichero) {
		this.datosFichero = datosFichero;
	}
	/**
	 * Obtiene nombre f�sico del fichero
	 * @return Nombre fichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * Establece nombre f�sico del fichero
	 * @param nombreFichero Nombre fichero
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	/**
	 * Obtiene fecha de inserci�n en el RDS. Este dato es calculado por el RDS.
	 * @return Fecha de inserci�n en el RDS
	 */
	public Date getFechaRDS() {
		return fechaRDS;
	}
	/**
	 * Obtiene fecha de inserci�n en el RDS. Este dato es calculado por el RDS.
	 * @param fechaRDS
	 */
	public void setFechaRDS(Date fechaRDS) {
		this.fechaRDS = fechaRDS;
	}
	/**
	 * Obtiene hash del fichero calculado por el RDS. Este dato es calculado por el RDS.
	 * @return Hash del fichero
	 */
	public String getHashFichero() {
		return hashFichero;
	}
	/**
	 * Establece hash del fichero calculado por el RDS. Este dato es calculado por el RDS.
	 * @return Hash del fichero
	 */
	public void setHashFichero(String hashFichero) {
		this.hashFichero = hashFichero;
	}
	/**
	 * Obtiene extensi�n del fichero
	 * @return Extensi�n fichero
	 */
	public String getExtensionFichero() {
		return extensionFichero;
	}
	/**
	 * Establece extensi�n del fichero
	 * @return Extensi�n fichero
	 */
	public void setExtensionFichero(String extensionFichero) {
		this.extensionFichero = extensionFichero;
	}
	/**
	 * Establece referencia RDS
	 * @param referenciaRDS Referencia RDS
	 */
	public void setReferenciaRDS(ReferenciaRDS referenciaRDS) {
		this.referenciaRDS = referenciaRDS;
	}
	/**
	 * Establece lista de firmas asociadas al documento
	 * @param firmas Firmas
	 */
	public void setFirmas(FirmaIntf[] firmas) {
		this.firmas = firmas;
	}
	/**
	 * Obtiene plantilla espec�fica para visualizar el documento estructurado.
	 * Si no se especifica utiliza la plantilla por defecto de la versi�n.
	 * @return C�digo plantilla especifica
	 */
	public String getPlantilla() {
		return plantilla;
	}
	/**
	 * Establece plantilla espec�fica para visualizar el documento estructurado. Si no se especifica utiliza la plantilla por defecto de la versi�n.
	 * @param plantilla C�digo plantilla especifica
	 */
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificaci�n (en caso de tenerla). Este dato es establecido por el RDS.
	 */	
	public String getUrlVerificacion() {
		return urlVerificacion;
	}
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificaci�n (en caso de tenerla).  Este dato es establecido por el RDS.
	 */	
	public void setUrlVerificacion(String urlVerificacion) {
		this.urlVerificacion = urlVerificacion;
	}	
	/**
	 * Para documentos estructurados indica el idioma para formatear el documento
	 *
	 * @return Idioma (es,ca)
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * Para documentos estructurados indica el idioma para formatear el documento
	 *
	 * @param idioma Idioma (es,ca)
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	/**
	 * En caso de que el documento este consolidado en un gestor documental, se indica la referencia en el gestor documental. Este dato es de solo lectura y es establecido por el RDS.
	 * @return Referencia del documento en el gestor documental
	 */
	public String getReferenciaGestorDocumental() {
		return referenciaGestorDocumental;
	}
	/**
	 * En caso de que el documento este consolidado en un gestor documental, se indica la referencia en el gestor documental. Este dato es de solo lectura y es establecido por el RDS.
	 * @param referenciaGestorDocumental Referencia del documento en el gestor documental
	 */
	public void setReferenciaGestorDocumental(String referenciaGestorDocumental) {
		this.referenciaGestorDocumental = referenciaGestorDocumental;
	}
	/**
     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
     * @return Referencia del documento en custodia
     */
    public String getCodigoDocumentoCustodia() {
		return codigoDocumentoCustodia;
	}
    /**
     * En caso de que se haya guardado en custodia, indicara el codigo del documento en custodia
     * @param codigoDocumentoCustodia Referencia del documento en custodia
     */
    public void setCodigoDocumentoCustodia(String codigoDocumentoCustodia) {
		this.codigoDocumentoCustodia = codigoDocumentoCustodia;
	}
	public String getCsv() {
		return csv;
	}
	public void setCsv(String csv) {
		this.csv = csv;
	}
	
	public boolean isPlantillaVisualizacion() {
		return plantillaVisualizacion;
	}
	public void setPlantillaVisualizacion(boolean plantillaVisualizacion) {
		this.plantillaVisualizacion = plantillaVisualizacion;
	}
	
}
