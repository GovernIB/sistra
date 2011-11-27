package es.caib.sistra.plugins.sms;

/**
 * Constantes plugin envío SMS
 * 
 */
public class ConstantesSMS {
	/**
	 * Estado desconocido.
	 * <br/> Si la plataforma de envíos no tiene la funcionalidad para saber el estado de un envío puede devolver este estado 
	 */
	public static final char ESTADO_DESCONOCIDO = 'D';
	/**
	 * Estado pendiente de enviar 
	 */	
	public static final char ESTADO_PENDIENTE = 'P';
	/**
	 * Estado enviado 
	 */
	public static final char ESTADO_ENVIADO = 'E';	
}
