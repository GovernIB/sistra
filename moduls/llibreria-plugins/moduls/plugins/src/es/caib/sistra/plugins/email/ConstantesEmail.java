package es.caib.sistra.plugins.email;

/**
 * Constantes plugin envío SMS
 * 
 */
public class ConstantesEmail {
	/**
	 * Si la plataforma de envíos no tiene la funcionalidad para saber el estado de un envío puede devolver este estado 
	 */
	public static final String ESTADO_NO_IMPLEMENTADO = "X";
	/**
	 * Estado desconocido.
	 */
	public static final String ESTADO_DESCONOCIDO = "D";
	/**
	 * Estado pendiente de enviar 
	 */	
	public static final String ESTADO_PENDIENTE = "P";
	/**
	 * Estado enviado 
	 */
	public static final String ESTADO_ENVIADO = "E";	
	/**
	 * Estado no enviado 
	 */
	public static final String ESTADO_NO_ENVIADO = "N";	
}
