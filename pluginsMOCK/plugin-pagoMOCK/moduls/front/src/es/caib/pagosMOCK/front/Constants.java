package es.caib.pagosMOCK.front;

import java.io.Serializable;

/**
 * Constants globals.
 */
public class Constants implements Serializable 
{
	/**
     * Atributo de contexto donde se guarda el lenguaje por defecto.
     */
    public static final String DEFAULT_LANG_KEY 	= "es.caib.pagosMOCK.front.DEFAULT_LANG";
    public static final String DEFAULT_LANG 		= "ca";	
    
    /**
     * Atributo de sesion donde se guarda la sesion de pagos
     */
	public static final String DATOS_SESION_KEY		= "es.caib.pagosMOCK.front.DATOS_SESION";
	
	 /**
     * Atributo de sesion donde se guarda la url de retorno a SISTRA
     */
	public static final String URL_RETORNO_SISTRA_KEY		= "es.caib.pagosMOCK.front.URL_RETORNO_SISTRA";
	
	/**
	 * Atributo de request donde se guarda mensaje para fail
	 */
	public static String MESSAGE_KEY = "mensaje";
	
	public static String MOSTRAR_EN_IFRAME = "mostrarSistraEnIframe";
	
}
