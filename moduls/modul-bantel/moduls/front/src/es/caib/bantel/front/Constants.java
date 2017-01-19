package es.caib.bantel.front;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable {

	/** Contexto raiz sistra */
	public static final String CONTEXTO_RAIZ = "es.caib.sistra.contextRootFront";
	
    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.bantel.front.DEFAULT_LANG";
    
    public static final String DEFAULT_LANG = "ca";
    
	public static final String IMPLEMENTACION_FIRMA_KEY = "implementacionFirma";
    
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
	//public static String OPCION_SELECCIONADA_KEY2 = "opcionSeleccionada2";
	
	
	public static String MESSAGE_KEY = "message";
	public static String MESSAGE_ACTION_KEY = "messageAction";
	public static String MESSAGE_ACTION_PARAMS_KEY = "messageActionParams";
	public static String MESSAGE_ACTION_LABEL_KEY = "messageActionLabelKey";
	
	
	public static String EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY = "es.caib.bantel.front.expedienteActual.identificador";
	public static String EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY = "es.caib.bantel.front.expedienteActual.unidadAdministrativa";
	public static String EXPEDIENTE_ACTUAL_CLAVE_KEY = "es.caib.bantel.front.expedienteActual.claveAcceso";
	public static String EXPEDIENTE_ACTUAL_PROCEDIMIENTO_KEY = "es.caib.bantel.front.expedienteActual.idProcedimiento";
		
	public static String GESTIONEXPEDIENTES_OBLIGATORIOAVISOS = "es.caib.bantel.front.gestionExpedientes.obligatorioAvisos";
	
	
	public static String FUENTE_DATOS_PAGINA_ACTUAL = "es.caib.bantel.front.fuenteDatos.paginaActual";
	public static int FUENTE_DATOS_PAGINA_TAMANYO = 10;
}
