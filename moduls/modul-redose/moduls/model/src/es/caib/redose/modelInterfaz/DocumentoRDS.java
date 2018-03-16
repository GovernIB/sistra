package es.caib.redose.modelInterfaz;

import java.util.Date;

import es.caib.sistra.plugins.firma.FirmaIntf;

/**
 * Clase que modeliza un Documento en la comunicación de otros módulos con el RDS.
 *  <br/>
 * El RDS tiene registrado los tipos de documentos que admite: se definen modelos y versiones de un modelo.
 *  <br/>
 * Al insertar un documento en el RDS se debe indicar de que modelo-versión se trata.
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
	 * Versión
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
	 * Extensión del fichero asociado al documento
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
	 * Código de ubicación dónde se almacenará el documento.
	 * Por defecto se utiliza la por defecto del RDS.
	 */
	private String codigoUbicacion;		
	
	// Datos establecidos por el RDS
	/**
	 * Fecha de introducción en el RDS. Este dato es calculado por el RDS.
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
	 * Código plantilla a utilizar para visualizar el documento estructurado.
	 * Si no se especifica utiliza la plantilla por defecto
	 */
	private String plantilla=null;
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificación (en caso de tenerla). Este dato es de solo lectura y es calculado por el RDS.
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
	 * Indica si el documento tiene plantilla de visualización asociada
	 */
	private boolean plantillaVisualizacion;

	// Getters / Setters
	/**
	 * Indica si el documento es estructurado (XML).Este dato es establecido por el RDS según el modelo del documento.
	 */
	public boolean isEstructurado() {
		return estructurado;
	}
	/**
	 * Indica si el documento es estructurado (XML). Este dato es establecido por el RDS según el modelo del documento.
	 * @param estructurado
	 */
	public void setEstructurado(boolean estructurado) {
		this.estructurado = estructurado;
	}
	/**
	 * Obtiene modelo del documento
	 * @return Código de modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * Establece el modelo del documento. Cualquier documento del RDS debe pertenecer a una versión de un modelo registrado.
	 * @param modelo Código de modelo
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
	 * Obtiene título del documento
	 * @return Título documento
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Establece título del documento
	 * @param titulo Título
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * Obtiene Unidad Administrativa responsable del documento
	 * @return Código unidad administrativa
	 */
	public long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	/**
	 * Establece Unidad Administrativa responsable del documento
	 * @param unidadAdministrativa Código unidad administrativa
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
	 * Obtiene versión. Cualquier documento del RDS debe pertenecer a una versión de un modelo registrado.
	 * @return Versión
	 */
	public int getVersion() {
		return version;
	}
	/**
	 * Establece versión.
	 * @param version Versión
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
	 * Obtiene código de ubicación en la que está almacenado físicamente el documento. Por defecto se utiliza la por defecto del RDS. 
	 * @return Código de ubicación
	 */
	public String getCodigoUbicacion() {
		return codigoUbicacion;
	}
	/**
	 * Establece código de ubicación en la que se almacenará el documento. Por defecto se utiliza la por defecto del RDS.
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
	 * Obtiene nombre físico del fichero
	 * @return Nombre fichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}
	/**
	 * Establece nombre físico del fichero
	 * @param nombreFichero Nombre fichero
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	/**
	 * Obtiene fecha de inserción en el RDS. Este dato es calculado por el RDS.
	 * @return Fecha de inserción en el RDS
	 */
	public Date getFechaRDS() {
		return fechaRDS;
	}
	/**
	 * Obtiene fecha de inserción en el RDS. Este dato es calculado por el RDS.
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
	 * Obtiene extensión del fichero
	 * @return Extensión fichero
	 */
	public String getExtensionFichero() {
		return extensionFichero;
	}
	/**
	 * Establece extensión del fichero
	 * @return Extensión fichero
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
	 * Obtiene plantilla específica para visualizar el documento estructurado.
	 * Si no se especifica utiliza la plantilla por defecto de la versión.
	 * @return Código plantilla especifica
	 */
	public String getPlantilla() {
		return plantilla;
	}
	/**
	 * Establece plantilla específica para visualizar el documento estructurado. Si no se especifica utiliza la plantilla por defecto de la versión.
	 * @param plantilla Código plantilla especifica
	 */
	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificación (en caso de tenerla). Este dato es establecido por el RDS.
	 */	
	public String getUrlVerificacion() {
		return urlVerificacion;
	}
	/**
	 * En caso de ser un documento RDS formateado indica su url de verificación (en caso de tenerla).  Este dato es establecido por el RDS.
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
