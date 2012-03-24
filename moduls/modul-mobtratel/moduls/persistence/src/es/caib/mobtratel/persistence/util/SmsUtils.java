package es.caib.mobtratel.persistence.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.MensajeSms;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.sms.EstadoEnvio;
import es.caib.sistra.plugins.sms.PluginSmsIntf;
import es.caib.xml.ConstantesXML;

/**
 * Clase de utilidad para enviar sms
 */
public class SmsUtils
{
	private static SmsUtils instance = null;
	private static PluginSmsIntf pluginSms = null;
	private Log log = LogFactory.getLog( SmsUtils.class  );

	protected SmsUtils(){
	}
	
	 /**
	 * Obtiene instancia smsutils
	 * @return Instancia smsutils
	 * @throws Exception
	 */
	public static SmsUtils getInstance() throws Exception{
		if(instance == null)
		{
			instance = new SmsUtils();
			pluginSms = PluginFactory.getInstance().getPluginEnvioSMS();
		}
		return instance;
	}
	
	/**
	 * Realiza envio de mensaje
	 * @param ms Mensaje
	 * @param telefono Telefono
	 * @param cuenta Cuenta
	 * @throws Exception
	 */
	public void enviarMensaje(MensajeSms ms, String telefono, String cuenta ) throws Exception{
		log.debug("Enviando mensaje a traves del plugin");
		byte[] mensaje = ms.getMensaje();
		String mens = (mensaje != null ? new String( mensaje, ConstantesXML.ENCODING ):"");
		pluginSms.enviarSMS(cuenta,telefono,mens,ms.getEnvio().isInmediato());
		log.debug("Mensaje enviado a traves del plugin");
    }   
	
	
	/**
	 * Verifica envio de mensaje
	 * @param ms Mensaje
	 * @throws Exception
	 */
	public EstadoEnvio verificarEnvioMensaje(MensajeSms ms) throws Exception{
		log.debug("Verificando envio mensaje a traves del plugin");
		EstadoEnvio estado = pluginSms.consultarEstadoEnvio(ms.getCodigo().toString());
		log.debug("Mensaje verificado a traves del plugin: estado = " + estado.getEstado());
		return estado;
    }   
	
	

}	

