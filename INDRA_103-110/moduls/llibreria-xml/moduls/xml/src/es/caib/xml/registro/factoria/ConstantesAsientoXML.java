package es.caib.xml.registro.factoria;

/**
 * 
 * Constantes para el XML de asiento registral
 *
 */
public class ConstantesAsientoXML {

	// -------------	TIPOS DE ASIENTO -----------------------------------
	/**
	 * Registro de Entrada
	 */
	public final static char TIPO_REGISTRO_ENTRADA = 'E';
	/**
	 * Registro de Salida
	 */
	public final static char TIPO_REGISTRO_SALIDA = 'S';
	/**
	 * Acuse de Recibo
	 */
	public final static char TIPO_ACUSE_RECIBO = 'R';	
	/**
	 * Envío
	 */
	public final static char TIPO_ENVIO = 'B';
	/**
	 * Preregistro
	 */
	public final static char TIPO_PREREGISTRO = 'P';
	/**
	 * Preenvío
	 */
	public final static char TIPO_PREENVIO = 'N';
	
	// -------------	NIVELES DE AUTENTICACION ---------------------------
	public static final char NIVEL_AUTENTICACION_ANONIMO = 'A';
	public static final char NIVEL_AUTENTICACION_USUARIO = 'U';
	public static final char NIVEL_AUTENTICACION_CERTIFICADO = 'C';
	
	// ------------- IDENTIFICADOR DEL ASIENTO Y DATOS PROPIOS ----------------
	/**
	 * Identificador documento de Asiento dentro documentos tramite
	 */
	public static final String IDENTIFICADOR_ASIENTO = "ASR";
	/**
	 * Identificador documento de Datos Propios dentro documentos tramite
	 */
	public static final String IDENTIFICADOR_DATOS_PROPIOS = "DTP";	
	/**
	 * Identificador documento de Justificante dentro documentos tramite
	 */
	public static final String IDENTIFICADOR_JUSTIFICANTE = "JSR";
	/**
	 * Identificador documento de Aviso de notificación dentro documentos tramite
	 */
	public static final String IDENTIFICADOR_AVISO_NOTIFICACION = "ANT";
	/**
	 * Identificador documento de Oficio de remisión dentro documentos tramite
	 */
	public static final String IDENTIFICADOR_OFICIO_REMISION = "ORE";
	
	// -----------	FORMATO FECHA REGISTRO Y NUMERO REGISTRO ----------------------------------
	/**
	 * FECHA_REGISTRO: Formato
	 */
	public static final String FECHA_REGISTRO_FORMATO = "yyyyMMddHHmmss";
	/**
	 * NUMERO_REGISTRO: Tamaño máximo
	 */
	public static final int NUMEROREGISTRO_MAX = 50;	
	
	// ------------- 	DATOS ORIGEN ---------------------------------------
	/**
	 * CODIGO_ENTIDAD_REGISTRAL: Tamaño máximo
	 */
	public static final int DATOSORIGEN_CODIGOENTIDADREGISTRAL_MAX = 25;	
	/**
	 * TIPO_REGISTRO: Tamaño máximo
	 */
	public static final int DATOSORIGEN_TIPOREGISTRO_MAX = 1;		
		
	
	// ------------- 	DATOS INTERESADO ---------------------------------------
	/**
	 * TIPO_INTERESADO: Tamaño máximo
	 */	
	public static final int DATOSINTERESADO_TIPOINTERESADO_MAX = 3;
	/**
	 * TIPO_IDENTIFICACIÓN: Tamaño máximo
	 */		
	public static final int DATOSINTERESADO_TIPOIDENTIFICACION_MAX = 1;
	/**
	 * NUMERO_IDENTIFICACIÓN: Tamaño máximo
	 */			
	public static final int DATOSINTERESADO_NUMERO_IDENTIFICACION_MAX = 15;
	/**
	 * FORMATO_DATOS_INTERESADO: Tamaño máximo
	 */
	public static final int DATOSINTERESADO_FORMATODATOSINTERESADO_MAX = 2;
	/**
	 * IDENTIFICACION_INTERESADO: Tamaño máximo
	 */	
	public static final int DATOSINTERESADO_IDENTIFICACIONINTERESADO_MAX = 600;
	/**
	 * PROCEDENCIA_GEOGRAFICA: Tamaño máximo
	 */		
	public static final int DATOSINTERESADO_PROCEDENCIAGEOGRAFICA_MAX = 2;	
	/**
	 * Constante de Tipo de Interesado Representante 
	 */
	public static final String DATOSINTERESADO_TIPO_REPRESENTANTE = "RPT";	
	/**
	 * Constante de tipo de interesado representado 
	 */
	public static final String DATOSINTERESADO_TIPO_REPRESENTADO = "RPD";	
	/**
	 * Constante de tipo de interesado delegado 
	 */
	public static final String DATOSINTERESADO_TIPO_DELEGADO = "DLG";
	/**
	 * Constante de tipo de identificación NIF
	 */
	public static final char DATOSINTERESADO_TIPO_IDENTIFICACION_NIF = 'N';	
	/**
	 * Constante de tipo de identificación CIF 
	 */
	public static final char DATOSINTERESADO_TIPO_IDENTIFICACION_CIF = 'C';
	/**
	 * Constante de tipo de identificación NIE
	 */
	public static final char DATOSINTERESADO_TIPO_IDENTIFICACION_NIE = 'E';	
	/**
	 * Constante de formato de datos de interesado con apellido y nombre 
	 */
	public static final String DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM = "AN";
	
	// ------------------	DATOS ASUNTO --------------------------------------------
	/**
	 * IDIOMA_ASUNTO: Tamaño máximo
	 */
	public final static int DATOSASUNTO_IDIOMA_ASUNTO_MAX = 2;
	/**
	 * TIPO_ASUNTO: Tamaño máximo
	 */
	public final static int DATOSASUNTO_TIPO_ASUNTO_MAX = 25;
	/**
	 * EXTRACTO_ASUNTO: Tamaño máximo
	 */
	public final static int DATOSASUNTO_EXTRACTO_ASUNTO_MAX = 200;
	/**
	 * Constante de idioma castellano 
	 */
	public final static String DATOSASUNTO_IDIOMA_ASUNTO_ES = "es";	
	/**
	 * Constante de idioma catalan
	 */
	public final static String DATOSASUNTO_IDIOMA_ASUNTO_CA = "ca";
	
	// -----------------	DATOS ANEXO	----------------------------------------------	
	/**
	 * IDENTIFICADOR_DOCUMENTO: Tamaño máximo
	 */
	public static final int DATOSANEXO_IDENTIFICADORDOCUMENTO_MAX = 20;	
	public static final int DATOSANEXO_NOMBREDOCUMENTO_MAX = 255;
	public static final int DATOSANEXO_EXTRACTODOCUMENTO_MAX = 200;	
	public static final int DATOSANEXO_HASHDOCUMENTO_MAX = 500;
	public static final int DATOSANEXO_CODIGONORMALIZADO_MAX = 20;
	/**
	 * Constante de documento de anexo estructurado 
	 */
	public final static char DATOSANEXO_ESTRUCTURADO = 'S';
	/**
	 * Constante de documento de anexo no estructurado
	 */
	public final static char DATOSANEXO_NO_ESTRUCTURADO = 'N';	
	/**
	 * Constante de tipo de documento con datos propios 
	 */
	public final static char DATOSANEXO_DATOS_PROPIOS = 'D';	
	/**
	 * Constante de tipo de documento de Aviso de notificación
	 */
	public final static char DATOSANEXO_AVISO_NOTIFICACION = 'A';
	/**
	 * Constante de tipo de documento de Oficio de remisión
	 */
	public final static char DATOSANEXO_OFICIO_REMISION = 'R';	
	/**
	 * Constante de tipo de documento otros
	 */
	public final static char DATOSANEXO_OTROS = 'O';
	/**
	 * Constante de tipo de documento extensión asiento
	 */
	public final static char DATOSANEXO_EXTENSION_ASIENTO = 'X';
	/**
	 * Constante de documento firmado por terceros
	 */
	public final static char DATOSANEXO_FIRMADO_POR_TERCEROS = 'S';	
	/**
	 * Constante de documento no firmado por terceros 
	 */
	public final static char DATOSANEXO_NO_FIRMADO_POR_TERCEROS = 'N';
	
	
	
}
