package es.caib.zonaper.front;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
	/**
	 * Atributo donde se guarda el lenguaje establecido en la pagina de login
	 */
	public  static final String KEY_LANGUAGE_LOGIN ="es.caib.zonaper.front.languageLoginPage";
	/**
	 * Atributo que indica que hemos establecido al inicar el lenguage establecido en la pagina de login
	 */
	public  static final String KEY_LANGUAGE_LOGIN_SET ="es.caib.zonaper.front.languageLoginPageSet";
	
	/**
     * Atributo de contexto donde se guarda si se debe resetear la sesion al iniciar (fuerza sso para caib)
     */
    public static final String RESETEAR_SESION_WEB		=  "es.caib.zonaper.front.resetearSesionInicio";
    
    /**
     * Atributo de contexto donde se guarda la info del organismo
     */
    public static final String ORGANISMO_INFO_KEY		=  "es.caib.zonaper.front.organismoInfo";
    
	
    /**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY 	= "es.caib.zonaper.front.DEFAULT_LANG";
    public static final String DEFAULT_LANG 		= "es";	
	public static final String DATOS_SESION_KEY		= "es.caib.zonaper.front.DATOS_SESION";
	public static String NOMBREFICHERO_KEY 			= "nombrefichero";
	public static String DATOSFICHERO_KEY 			= "datosfichero";
	public static String URLFICHERO_KEY 			= "urlAcceso";
	public static String MENU_NAVEGACION_PREFFIX 	= "menu.navegacion";
	public static String MENU_NAVEGACION_LAST 	= "menu.navegacion.ultimo";
	
	public static char NIVEL_AUTENTICACION_ANONIMO = 'A';
	
	/**
	 * Constantes acceso a XML de pagos
	 */
	public static String XML_PAGO_TIPO = "/PAGO/DATOS_PAGO/TIPO";
	public static String XML_PAGO_TIPO_TELEMATICO = "T";
	public static String XML_PAGO_LOCALIZADOR = "/PAGO/DATOS_PASARELA/LOCALIZADOR";
	
	/**
	 * Constantes para mensajes
	 */
	public static String MENSAJE_MENU_NAVEGACION="mensaje.menu.navegacion";
	public static String MENSAJE_TITULO="mensaje.titulo";
	public static String MENSAJE_TEXTO="mensaje.texto";
	
	/**
	 * Constantes para cachear ultimos elementos mostrados
	 */
	public static String ULTIMO_DETALLE_EXPEDIENTE="ultimo.detalle.expediente";
	
	/**
	 * Atributo que indica para acceso anonimo con que id persistencia se ha entrado
	 */
	public  static final String KEY_ANONIMO_ID_PERSISTENCIA ="es.caib.zonaper.front.idPersistencia";
	
	/**
     * Atributo de contexto donde se guarda la implementacion de la firma
     */
    public static final String IMPLEMENTACION_FIRMA_KEY 			= "implementacionFirma";
	
    public static final String MOSTRAR_EN_IFRAME = "mostrarSistraEnIframe";
}
