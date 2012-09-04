package es.caib.sistra.plugins.sms;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con la pasarela de env�os SMS
 *
 */
public interface PluginSmsIntf  extends PluginSistraIntf {
	
	/**
	 * Realiza el env�o de un SMS
	 * 
	 * @param cuentaSMS Cuenta SMS de la pasarela de env�os con la que se debe enviar.
	 * De esta forma se permite definir diferentes cuentas de env�o por si la pasarela de env�os SMS tiene esta funcionalidad 
	 * @param telefono N�mero de tel�fono al que se env�a el mensaje
	 * @param mensaje Mensaje SMS
	 * @param inmediato Indica si el SMS debe enviarse inmediatamente. De esta forma se permite indicar prioridades en los mensajes
	 * por si la pasarela de env�os tiene esta funcionalidad
	 * @throws Exception En caso de error debe generar una excepcion
	 */
	public void enviarSMS(String idEnvio, String cuentaSMS,String telefono,String mensaje,boolean inmediato) throws Exception;
	
	/**
	 * Consulta el estado de un env�o
	 * <br/>
	 * Esta funcionalidad se podr� implementar si es soportada por la pasarela de env�os
	 * @param idEnvio C�digo de env�o
	 * @return EstadoEnvio estado del env�o
	 * @throws Exception
	 */
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception;
	
}
