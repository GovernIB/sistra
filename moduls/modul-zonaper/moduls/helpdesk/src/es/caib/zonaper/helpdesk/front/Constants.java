package es.caib.zonaper.helpdesk.front;



import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable {

    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.zonaper.helpdesk.front.DEFAULT_LANG";
    
    public static final String DEFAULT_LANG = "ca";
    public static final String TODOS = "T";
    public static final String CASTELLANO = "es";
    public static final String CATALAN = "ca";

    public static int TIEMPO_EN_CACHE = 60 * 60 * 24;
	
	public static String FORMATO_FECHAS = "dd/MM/yyyy HH:mm";
	
	public static String XMLPAGO_CONFIRMADO = "S";
	public static String XMLPAGO_NO_INICIADO = "V";
	public static String XMLPAGO_PENDIENTE_CONFIRMAR = "N";
	public static String XMLPAGO_EN_CURSO = "C";
	public static String XMLPAGO_TIEMPO_EXCEDIDO = "T";
	
	public static String PAGO_PAGADO = "PP";
	public static String PAGO_NO_PAGADO = "PNP";
	public static String PAGO_NO_COMPROBADO = "PNC";
	
	public static char PRESENCIAL = 'P';
	public static char TELEMATICO = 'T';
	
	public static String NO_FINALIZADO = "P";
	public static String PENDIENTE_CONFIRMACION = "C";
	public static String FINALIZADO = "T";
	public static String BORRADO = "B";
	public static String NO_EXISTE = "N";
	
	public static String ERROR_BUSQUEDA = "X";
 
    public static String RESPUESTA_FRONT_KEY = "respuestaFront";
	public static String TRAMITE_KEY = "tramite";
	public static String MENSAJE_KEY = "message";
	public static String PARAMS_KEY = "params";
	public static String PRINCIPAL_KEY = "userPrincipal";
	public static String MODO_AUTENTICACION_KEY = "metodoAutenticacion";
	public static String PAYMENT_DATA_KEY = "datospago";
	public static String PAYMENT_KEY = "pago";
	
	public static String NIVELES_AUTENTICACION_PARAMS_KEY = "niveles";
	public static String TRAMITES_PARAMS_KEY = "tramites";
	public static String DIAS_PERSISTENCIA_PARAMS_KEY = "diaspersistencia";
	public static String DESCRIPCION_TRAMITE_PARAMS_KEY = "descripcion";
	
	public static String RESULTADO_REGISTRO_KEY = "resultado";
	
	public static String POINT_EXTENSION = ".";
	
	public static char MODO_AUTENTICACION_USUARIO_PWD = 'U';
	public static char MODO_AUTENTICACION_ANONIMO = 'A';
	public static char MODO_AUTENTICACION_CERTIFICADO = 'C';
	
	public static char TIPO_CIRCUITO_TRAMITACION_TELEMATICO = 'T';
	public static char TIPO_CIRCUITO_TRAMITACION_PRESENCIAL = 'P';
	public static char TIPO_CIRCUITO_TRAMITACION_DEPENDE = 'D';
	
	public static char TIPO_PAGO_TELEMATICO = 'T';
	public static char TIPO_PAGO_PRESENCIAL = 'P';
	
	
	public static String UNAUTHENTICATED_IDENTITY = "nobody";
	
	public static int EXTENSION_MAX_LENGTH = 3; 
	
	public static String NOMBREFICHERO_KEY = "nombrefichero";
	public static String DATOSFICHERO_KEY = "datosfichero";
	
	
	public static String OPCION_SELECCIONADA_KEY = "opcionSeleccionada";
	
	
	public static String MESSAGE_KEY = "message";
	public static String MESSAGE_ACTION_KEY = "messageAction";
	public static String MESSAGE_ACTION_PARAMS_KEY = "messageActionParams";
	public static String MESSAGE_ACTION_LABEL_KEY = "messageActionLabelKey";

	public static String CLAVE_TAB = "1"; 
	public static String ESTADO_TAB = "2"; 
	public static String PAGO_TAB = "3"; 
	public static String AUDITORIA_TAB = "4"; 
	public static String USUARIOS_TAB = "5";
	public static String BACKUP_TAB = "6";
	public static String PREREGISTRO_TAB = "7";
	
	public static String TIPO_REGISTRO  = "P";
	public static String TIPO_BANDEJA  = "N";

}
