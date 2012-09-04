package es.caib.sistra.plugins.sms;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con la pasarela de envíos SMS
 *
 */
public interface PluginSmsIntf  extends PluginSistraIntf {
	
	/**
	 * Realiza el envío de un SMS
	 * 
	 * @param cuentaSMS Cuenta SMS de la pasarela de envíos con la que se debe enviar.
	 * De esta forma se permite definir diferentes cuentas de envío por si la pasarela de envíos SMS tiene esta funcionalidad 
	 * @param telefono Número de teléfono al que se envía el mensaje
	 * @param mensaje Mensaje SMS
	 * @param inmediato Indica si el SMS debe enviarse inmediatamente. De esta forma se permite indicar prioridades en los mensajes
	 * por si la pasarela de envíos tiene esta funcionalidad
	 * @throws Exception En caso de error debe generar una excepcion
	 */
	public void enviarSMS(String idEnvio, String cuentaSMS,String telefono,String mensaje,boolean inmediato) throws Exception;
	
	/**
	 * Consulta el estado de un envío
	 * <br/>
	 * Esta funcionalidad se podrá implementar si es soportada por la pasarela de envíos
	 * @param idEnvio Código de envío
	 * @return EstadoEnvio estado del envío
	 * @throws Exception
	 */
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception;
	
}
