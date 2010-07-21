package es.caib.sistra.front;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable {

	/**
	 * Atributo donde se guarda el lenguaje establecido en la pagina de login
	 */
	public  static final String KEY_LANGUAGE_LOGIN ="es.caib.sistra.front.languageLoginPage";
	
    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY = "es.caib.sistra.front.DEFAULT_LANG";
    
    
    /**
     * Atributo de contexto donde se guarda la implementacion de la firma
     */
    public static final String IMPLEMENTACION_FIRMA_KEY 			= "implementacionFirma";
    
    /**
     * Atributo de contexto donde se guarda la info del organismo
     */
    public static final String ORGANISMO_INFO_KEY 			= "es.caib.sistra.front.infoOrganismo";
    
    
    /**
     * Atributo de contexto donde se guarda si se debe resetear la sesion al iniciar (fuerza sso para caib)
     */
    public static final String RESETEAR_SESION_WEB		=  "es.caib.sistra.front.resetearSesionInicio";
    
    /**
     * Constantes el gestor de formularios
     */
    public static final String GESTOR_FORM_SESSION_KEY = "es.caib.sistra.front.GESTOR_FORM@";
    public static final String GESTOR_FORM_CLASS = "es.caib.sistra.front.formulario.GestorFlujoFormularioFORMS";
    public static final String GESTOR_FORM_BLACKBOX_CLASS = "es.caib.sistra.front.formulario.GestorFlujoFormularioBLACKBOX";
    
    /**
     * Parámetros utilizados en las llamadas al gestor de formularios
     */
    public static final String GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO = "gstfrm";
    public static final String GESTOR_FORM_PARAM_TOKEN_LLAMADA = "ID_INSTANCIA";
    public static final String GESTOR_FORM_PARAM_XML_DATOS_INI="xmlInicial";
    public static final String GESTOR_FORM_PARAM_XML_DATOS_FIN="xmlFinal";
    public static final String GESTOR_FORM_PARAM_TOKEN_RETORNO="TOKEN";
            
    
    public static final String NO_REINIT = "NO_REINIT";
    
    public static final String RESPUESTA_FRONT_KEY = "respuestaFront";
	public static final String TRAMITE_KEY = "tramite";
	public static final String MENSAJE_KEY = "message";
	public static final String MENSAJE_PARAM = "messageParam";
	public static final String MENSAJE_URL_REDIRECT = "messageUrlRedirect"; // Parametro empleado para la acción genérica de redirección
	public static final String ERROR_TRATADO_KEY = "errHandled";
	public static final String PARAMS_KEY = "params";
	public static final String PRINCIPAL_KEY = "userPrincipal";
	public static final String MODO_AUTENTICACION_KEY = "metodoAutenticacion";
	
	
	
	public static final String URL_SESIONPAGO_KEY = "urlsesionpago";
		
	
	public static final String NOMBREFICHERO_KEY = "nombrefichero";
	public static final String DATOSFICHERO_KEY = "datosfichero";
	public static final String URLFICHERO_KEY = "urlAcceso";
	
	public static final String DATOS_SESION_PARAMS_KEY 			= "datossesion";
	public static final String NIVELES_AUTENTICACION_PARAMS_KEY 	= "niveles";
	public static final String TRAMITES_PARAMS_KEY 				= "tramites";
	public static final String USUARIOSPAD_PARAMS_KEY 				= "usuariosPAD";
	public static final String DIAS_PERSISTENCIA_PARAMS_KEY		= "diaspersistencia";
	public static final String DESCRIPCION_TRAMITE_PARAMS_KEY 	= "descripcion";
	
	public static final String RESULTADO_REGISTRO_KEY = "resultado";
	
	public static final String TRAMITE_REDUCIDO_KEY = "isTramiteReducido";
	
	public static final String POINT_EXTENSION = ".";
	
	public static final char MODO_AUTENTICACION_USUARIO_PWD = 'U';
	public static final char MODO_AUTENTICACION_ANONIMO = 'A';
	public static final char MODO_AUTENTICACION_CERTIFICADO = 'C';
	
	public static final char TIPO_CIRCUITO_TRAMITACION_TELEMATICO = 'T';
	public static final char TIPO_CIRCUITO_TRAMITACION_PRESENCIAL = 'P';
	public static final char TIPO_CIRCUITO_TRAMITACION_DEPENDE = 'D';
	
	public static final char TIPO_PAGO_TELEMATICO = 'T';
	public static final char TIPO_PAGO_PRESENCIAL = 'P';
	
	public static final String NOMBRE_FICHERO_CLAVEPERSISTENCIA_CAT = "Clau_<?>.html"; 
	public static final String NOMBRE_FICHERO_CLAVEPERSISTENCIA_CAS = "Clave_<?>.html";
	
	public static final String UNAUTHENTICATED_IDENTITY = "nobody";
		
	public static final int EXTENSION_MAX_LENGTH = 3; 
	
	public static final int MAX_FILE_NAME_SIZE = 85;
	
	public static final String CLAVE_ALMACENADA_KEY="clave_almacenada";
	
	public static final String MOSTRAR_FIRMA_DIGITAL="mostrarFirmaDigital";

}
