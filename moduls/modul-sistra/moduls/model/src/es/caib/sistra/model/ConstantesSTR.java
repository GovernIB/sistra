package es.caib.sistra.model;

public class ConstantesSTR {
		
	// MENSAJES PLATAFORMA
	public final static String MENSAJEPLATAFORMA_TODOS 	= "TODOS";
	public final static String MENSAJEPLATAFORMA_ANONIMOS 	= "ANONIMOS";
	public final static String MENSAJEPLATAFORMA_AUTENTICADOS 	= "AUTENTICA";
	public final static String MENSAJEPLATAFORMA_PAGOS 	= "PAGOS";
	
	// DESTINO TRAMITE
	public static final char DESTINO_BANDEJA='B';
	public static final char DESTINO_REGISTRO='R';
	public static final char DESTINO_CONSULTA='C';
	public static final char DESTINO_ASISTENTE='A';
	
	// TIPO DE TRAMITACION
	public final static char TIPO_TRAMITACION_PRESENCIAL = 'P';
	public final static char TIPO_TRAMITACION_TELEMATICA = 'T';
	public final static char TIPO_TRAMITACION_DEPENDIENTE = 'D';
	
	// NOTIFICACIONES TELEMATICAS
	public final static String NOTIFICACIONTELEMATICA_SINESPECIFICAR = "X";
	public final static String NOTIFICACIONTELEMATICA_NOPERMITIDA = "N";
	public final static String NOTIFICACIONTELEMATICA_PERMITIDA = "S";
	public final static String NOTIFICACIONTELEMATICA_OBLIGATORIA = "O";	
	
	// ALERTAS TRAMITACION
	public final static String ALERTASTRAMITACION_SINESPECIFICAR = "X";
	public final static String ALERTASTRAMITACION_NOPERMITIDA = "N";
	public final static String ALERTASTRAMITACION_PERMITIDA = "S";
	
	// PERSONALIZACION JUSTIFICANTE
	public final static String PERSONALIZACIONJUSTIFICANTE_SINESPECIFICAR = "X";
	public final static String PERSONALIZACIONJUSTIFICANTE_HABILITADA = "S";
	public final static String PERSONALIZACIONJUSTIFICANTE_NOHABILITADA = "N";	
	
	// VERIFICAR MOVIL
	public final static String VERIFICARMOVIL_SINESPECIFICAR = "X";
	public final static String VERIFICARMOVIL_HABILITADA = "S";
	public final static String  VERIFICARMOVIL_NOHABILITADA = "N";	
	
	// CODIGO DE PAIS ESPAÑA
	public final static String PAIS_ESPANYA = "ESPAÑA";
	public final static String CODIGO_PAIS_ESPANYA = "ESP";
	
	// CODIGO DE LENGUAJES
	public static String CAT_LANGUAGE = "ca";
	public static String CAS_LANGUAGE = "es";
	
	// TITULO PDF
	public static String PDF_FILE_TITLE_CAT = "Justificant";
	public static String PDF_FILE_TITLE_CAS = "Justificante";

	// TIEMPO EN CACHE PARA DOMINIOS
	public static int TIEMPO_EN_CACHE = 60 * 60 * 24;

	// SUBSANACION: NOMBRE DE PARAMETROS
	public static String SUBSANACION_PARAMETER_EXPEDIENTE_ID = "subsanacionExpedienteId";
	public static String SUBSANACION_PARAMETER_EXPEDIENTE_UA = "subsanacionExpedienteUA";
	
}
