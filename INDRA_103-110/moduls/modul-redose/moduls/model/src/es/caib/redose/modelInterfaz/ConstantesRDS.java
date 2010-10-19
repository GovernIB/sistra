package es.caib.redose.modelInterfaz;

/**
 * Constantes del módulo RDS
 *
 */
public class ConstantesRDS {
	// CONSTANTES TIPO DE USO
	/**
	 * Tipo de uso documentos PAD
	 */
	public final static String TIPOUSO_PAD = "PAD";
	/**
	 * Tipo de uso para documentos de un trámite en persistencia
	 */
	public final static String TIPOUSO_TRAMITEPERSISTENTE = "TRA";
	/**
	 * Tipo de uso para documentos asociados a un registro de entrada
	 */
	public final static String TIPOUSO_REGISTROENTRADA = "RTE";
	/**
	 * Tipo de uso para documentos asociados a un registro de salida
	 */
	public final static String TIPOUSO_REGISTROSALIDA = "RTS";
	/**
	 * Tipo de uso para documentos asociados a bandeja
	 */
	public final static String TIPOUSO_BANDEJA = "BTE";
	/**
	 * Tipo de uso para documentos asociados a preregistro
	 */
	public final static String TIPOUSO_PREREGISTRO = "PRE";
	/**
	 * Tipo de uso para documentos asociados a un envio telematico
	 */
	public final static String TIPOUSO_ENVIO = "ENV";
	/**
	 * Tipo de uso para documentos asociados a un evento de expediente
	 */
	public final static String TIPOUSO_EXPEDIENTE = "EXP";
	/**
	 * Tipo de uso para documentos de autorización de delegacion
	 */
	public final static String TIPOUSO_DELEGACION = "DLG";
	/**
	 * Tipo de uso para envio de edictos al BOIB
	 */
	public final static String TIPOUSO_ENVEDICTE = "EDI";
	
	
	// CONSTANTES TIPOS DE PLANTILLAS
	/**
	 * Tipo plantilla HTML
	 */
	public static final transient String PLANTILLA_TIPO_HTM="HTM";
	/**
	 * Tipo plantilla PDF
	 */
	public static final transient String PLANTILLA_TIPO_PDF="PDF";
	
	// CONSTANTES TIPOS DE MODELO
	/**
	 * Modelo asiento registral
	 */
	public static final String MODELO_ASIENTO_REGISTRAL = "GE0002ASIENTO";
	/**
	 * Modelo justificante registro
	 */
	public static final String MODELO_JUSTIFICANTE_REGISTRO = "GE0001JUSTIF";
	/**
	 * Modelo datos propios trámite
	 */
	public static final String MODELO_DATOS_PROPIOS = "GE0003DATPROP";
	/**
	 * Modelo documento identidad
	 */
	public static final String MODELO_DOCUMENTO_IDENTIDAD= "GE0004DOCID";	
	/**
	 * Modelo Anexo genérico
	 */
	public static final String MODELO_ANEXO_GENERICO = "GE0005ANEXGEN";
	/**
	 * Modelo pago
	 */
	public static final String MODELO_PAGO = "GE0006PAGO";
	/**
	 * Modelo Documento de expediente
	 */
	public static final String MODELO_DOCUMENTO_EXPEDIENTE = "GE0007DOCEXP";
	/**
	 * Modelo aviso notificacion
	 */
	public static final String MODELO_AVISO_NOTIFICACION = "GE0008AVINOT";
	/**
	 * Modelo oficio remision
	 */
	public static final String MODELO_OFICIO_REMISION = "GE0009OFIREM";
	/**
	 * Modelo notificacion
	 */
	public static final String MODELO_NOTIFICACION= "GE0011NOTIFICA";
	/**
	 * Modelo autorizacion delegacion
	 */
	public static final String MODELO_AUTORIZACION_DELEGACION= "GE0012DELEGA";

	// VERSIONES ACTUALES DE MODELOS DE DOCUMENTOS UTILIZADOS
	/**
	 * Version actual de asiento
	 */
	public static final int VERSION_ASIENTO=1;
	/**
	 * Version actual de justificante
	 */
	public static final int VERSION_JUSTIFICANTE=1;
	/**
	 * Version actual de datos propios del trámite
	 */
	public static final int VERSION_DATOS_PROPIOS=1;
	/**
	 * Version actual de pago
	 */
	public static final int VERSION_PAGO=1;
	/**
	 * Modelo aviso notificacion
	 */
	public static final int VERSION_AVISO_NOTIFICACION = 1;
	/**
	 * Modelo oficio remision
	 */
	public static final int VERSION_OFICIO_REMISION = 1;
		
	
	// ALGORITMO HASH UTILIZADO AL INSERTAR DOCUMENTO
	/**
	 * Algoritmo de hash utilizado al insertar un documento
	 */
	public static final String HASH_ALGORITMO = "SHA-512";
	/**
	 * Encoding utilizado 
	 */
	public static final String HASH_ENCODING = "UTF-8";
	
	// PARA SINCRONIZACION CON GESTOR DOCUMENTAL INDICA QUE DOCUMENTOS NO SE MIGRARAN (DOCUMENTOS ANTIGUOS)
	public static final String GESDOC_DOCUMENTO_NO_CONSOLIDABLE = "#NOCONSOLIDABLE#";
	
	
	
}
