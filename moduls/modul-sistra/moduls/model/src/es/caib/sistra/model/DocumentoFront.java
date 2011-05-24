package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Clase que se utiliza para pasar al front la 
 * información del documento
 */
public class DocumentoFront  implements Serializable{
	
	public final static char OBLIGATORIO = 'S';
	public final static char OPCIONAL = 'N';
	public final static char DEPENDIENTE = 'D';
	public final static char ESTADO_CORRECTO = 'S';
	public final static char ESTADO_INCORRECTO = 'N';
	public final static char ESTADO_NORELLENADO = 'V';
	
	/**
	 * Identificador documento
	 */
	private String identificador; // Identificador documento	
	/**
	 * Instancia de documento (1 para todos los casos excepto para anexos genéricos)
	 */
	private int instancia; 		// Instancia documento (1 para todos los casos excepto para documentos genéricos)
	/**
	 * Modelo documento
	 */
	private String modelo;
	/**
	 * Version documento
	 */
	private int version;
	/**
	 * Descripcion
	 */
	private String descripcion;
	/**
	 * Información descriptiva
	 */
	private String informacion;
	/**
	 * Indica obligatoriedad del documento (Obligatorio/Opcional/Dependiente) (Tras ejecutar script de obligatoriedad)
	 */
	private char obligatorio;
	/**
	 * Indica si el documento original es Dependiente antes de evaluar el script de obligatoriedad
	 */
	private boolean dependiente;
	/**
	 * En caso de que exista flujo de tramitación indica el nif de la persona que debe rellenar el documento
	 */
	private String nifFlujo;
	/**
	 * Lista de formularios que intervienen en el script de flujo con el formato [FORM1][FORM2]...
	 */
	private String formulariosScriptFlujo;
	/**
	 * Indica si el documento debe pre-registrarse, es decir, se debe mostrar luego en un punto de entrega
	 * (impreso y firmado) en caso de tramites presenciales
	 */
	private boolean prerregistro;
	/**
	 * Indica si el formulario debe presentarse como justificante en caso de que el tramite sea presencial
	 */
	private boolean formularioJustificante;
	/**
	 * Indica si el documento debe firmarse digitalmente individualmente
	 */
	private boolean firmar;
	/**
	 * En caso de que se tenga que firmar indica los formularios que intervienene en el script de firma que indica el firmante
	 */
	private String formulariosScriptFirma;	
	/**
	 * Indica si el documento debe firmarse por delegados de la entidad a traves bandeja de firma
	 */
	private boolean firmaDelegada;
	/**
	 * Indica si el documento debe firmarse por delegados de la entidad a traves bandeja de firma.
	 * (marca el estado una vez se ha incorporado el documento)
	 */
	private boolean pendienteFirmaDelegada;
	/**
	 * Indica si el documento se ha rechazado en la bandeja de firma
	 */
	private boolean rechazadaFirmaDelegada;
	/**
	 * DNI del firmante(s) del documento (después de evaluar campo firmante). En caso de existir varios estarian separados por #.
	 * Si esta vacío deberá firmarlo quien inicia el trámite
	 */
	private String firmante;
	/**
	 * En caso de que se indiquen firmante(s) del documento se intentara buscar el nombre en el registro de personas de la zona personal.
	 * En caso de existir varios estarian separados por # 
	 */
	private String nombreFirmante;
	/**
	 * Indica si el documento ha sido firmado
	 */
	private boolean firmado = false;
	/**
	 * Indica si el documento esta en la Pad
	 */
	private boolean pad;	
	/**
	 * Presentar telemáticamente
	 */
	private boolean anexoPresentarTelematicamente;
	/**
	 * Lista de extensiones permitidas separadas por ; (Lista vacía permite todas las extensiones)
	 */
	private String anexoExtensiones;
	/**
	 * Tamaño máximo permitido (0 indica cualquier tamaño)
	 */
	private int anexoTamanyo;
	/**
	 * Url para descargar plantilla
	 */
	private String anexoPlantilla;
	/**
	 * Indica si debe indicar en el paso "Debe saber" que se descargue la plantilla
	 */
	private boolean anexoMostrarPlantilla;
	/**
	 * Indica que en lugar de anexar el documento se deberá presentar el original con la finalidad de 
	 * compulsar el documento (-> por tanto el trámite será presencial)
	 */
	private boolean anexoCompulsar;
	/**
	 * Indica que en lugar de anexar el documento se deberá presentar una fotocopia (-> por tanto el trámite será presencial)
	 */
	private boolean anexoFotocopia;
	/**
	 * Indica si el anexo es "genérico": se permitirá al ciudadano introducir una descripción  
	 */
	private boolean anexoGenerico;
	/**
	 * Número máximo de instancias permitidas para un documento genérico
	 */
	private int anexoGenericoMax;
	/**
	 * Descripción personalizada para un documento genérico
	 */
	private String anexoGenericoDescripcion;
	/**
	 * Fichero anexado actualmente
	 */
	private String anexoFichero;
	/**
	 * Para pagos indica los metodos de pago posibles (Presencial (P) / Telematico (T) / Ambos (A))
	 */
	private char pagoMetodos;
	/**
	 * Estado del documento (correcto/incorrecto/no rellenado)
	 */
	private char estado;	
	/**
	 * Si el pago se ha realizado indica el tipo de pago (Presencial (P) / Telematico (T))
	 */
	private char pagoTipo;
	/**
	 * Si especificado, indica el content type del documento en caso de firma
	 */
	private String contentType;
	
	
	public char getEstado() {
		return estado;
	}
	public void setEstado(char estado) {
		this.estado = estado;
	}
	public String getAnexoExtensiones() {
		return anexoExtensiones;
	}
	public void setAnexoExtensiones(String anexoExtensiones) {
		this.anexoExtensiones = anexoExtensiones;
	}
	public boolean isAnexoFotocopia() {
		return anexoFotocopia;
	}
	public void setAnexoFotocopia(boolean anexoFotocopia) {
		this.anexoFotocopia = anexoFotocopia;
	}
	public boolean isAnexoGenerico() {
		return anexoGenerico;
	}
	public void setAnexoGenerico(boolean anexoGenerico) {
		this.anexoGenerico = anexoGenerico;
	}
	public int getAnexoGenericoMax() {
		return anexoGenericoMax;
	}
	public void setAnexoGenericoMax(int anexoGenericoMax) {
		this.anexoGenericoMax = anexoGenericoMax;
	}
	public boolean isAnexoMostrarPlantilla() {
		return anexoMostrarPlantilla;
	}
	public void setAnexoMostrarPlantilla(boolean anexoMostrarPlantilla) {
		this.anexoMostrarPlantilla = anexoMostrarPlantilla;
	}
	public String getAnexoPlantilla() {
		return anexoPlantilla;
	}
	public void setAnexoPlantilla(String anexoPlantilla) {
		this.anexoPlantilla = anexoPlantilla;
	}
	public int getAnexoTamanyo() {
		return anexoTamanyo;
	}
	public void setAnexoTamanyo(int anexoTamanyo) {
		this.anexoTamanyo = anexoTamanyo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isFirmar() {
		return firmar;
	}
	public void setFirmar(boolean firmar) {
		this.firmar = firmar;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String idDocumento) {
		this.identificador = idDocumento;
	}
	public String getInformacion() {
		return informacion;
	}
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	public char getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(char obligatorio) {
		this.obligatorio = obligatorio;
	}
	public boolean isPad() {
		return pad;
	}
	public void setPad(boolean pad) {
		this.pad = pad;
	}
	public int getInstancia() {
		return instancia;
	}
	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}
	public boolean isDependiente() {
		return dependiente;
	}
	public void setDependiente(boolean dependiente) {
		this.dependiente = dependiente;
	}
	public String getFirmante() {
		return firmante;
	}
	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}
	public boolean isAnexoCompulsar()
	{
		return anexoCompulsar;
	}
	public void setAnexoCompulsar(boolean anexoCompulsar)
	{
		this.anexoCompulsar = anexoCompulsar;
	}
	public String getAnexoFichero() {
		return anexoFichero;
	}
	public void setAnexoFichero(String anexoFichero) {
		this.anexoFichero = anexoFichero;
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
	public boolean isPrerregistro()
	{
		return prerregistro;
	}
	public void setPrerregistro(boolean prerregistro)
	{
		this.prerregistro = prerregistro;
	}
	public char getPagoTipo() {
		return pagoTipo;
	}
	public void setPagoTipo(char pagoTipo) {
		this.pagoTipo = pagoTipo;
	}
	public boolean isFirmado() {
		return firmado;
	}
	public void setFirmado(boolean firmado) {
		this.firmado = firmado;
	}
	public boolean isAnexoPresentarTelematicamente() {
		return anexoPresentarTelematicamente;
	}
	public void setAnexoPresentarTelematicamente(
			boolean anexoPresentarTelematicamente) {
		this.anexoPresentarTelematicamente = anexoPresentarTelematicamente;
	}
	public char getPagoMetodos() {
		return pagoMetodos;
	}
	public void setPagoMetodos(char pagoMetodos) {
		this.pagoMetodos = pagoMetodos;
	}
	public String getAnexoGenericoDescripcion() {
		return anexoGenericoDescripcion;
	}
	public void setAnexoGenericoDescripcion(String anexoGenericoDescripcion) {
		this.anexoGenericoDescripcion = anexoGenericoDescripcion;
	}
	public String getNifFlujo() {
		return nifFlujo;
	}
	public void setNifFlujo(String nifFlujo) {
		this.nifFlujo = nifFlujo;
	}
	public String getFormulariosScriptFlujo() {
		return formulariosScriptFlujo;
	}
	public void setFormulariosScriptFlujo(String formulariosScriptFlujo) {
		this.formulariosScriptFlujo = formulariosScriptFlujo;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public boolean isFormularioJustificante() {
		return formularioJustificante;
	}
	public void setFormularioJustificante(boolean formularioJustificante) {
		this.formularioJustificante = formularioJustificante;
	}
	public boolean isPendienteFirmaDelegada() {
		return pendienteFirmaDelegada;
	}
	public void setPendienteFirmaDelegada(boolean pendienteFirmaDelegada) {
		this.pendienteFirmaDelegada = pendienteFirmaDelegada;
	}
	public String getFormulariosScriptFirma() {
		return formulariosScriptFirma;
	}
	public void setFormulariosScriptFirma(String formulariosScriptFirma) {
		this.formulariosScriptFirma = formulariosScriptFirma;
	}
	public boolean isFirmaDelegada() {
		return firmaDelegada;
	}
	public void setFirmaDelegada(boolean firmaDelegada) {
		this.firmaDelegada = firmaDelegada;
	}
	public String getNombreFirmante() {
		return nombreFirmante;
	}
	public void setNombreFirmante(String nombreFirmantes) {
		this.nombreFirmante = nombreFirmantes;
	}
	public boolean isRechazadaFirmaDelegada() {
		return rechazadaFirmaDelegada;
	}
	public void setRechazadaFirmaDelegada(boolean rechazadaFirmaDelegada) {
		this.rechazadaFirmaDelegada = rechazadaFirmaDelegada;
	}
	
	
	
}
