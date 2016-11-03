package es.caib.sistra.plugins.sms.impl.mock;

import es.caib.sistra.plugins.sms.ConstantesSMS;
import es.caib.sistra.plugins.sms.EstadoEnvio;
import es.caib.sistra.plugins.sms.PluginSmsIntf;

/**
 * 	
 * 	Objeto MOCK para simular envio por sms
 *
 */
public class PluginSmsMock implements PluginSmsIntf{

	public void enviarSMS(String idEnvio, String cuentaSMS,String telefono,String mensaje,boolean inmediato) throws Exception{
		return;
	}
	
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception{
		EstadoEnvio e = new EstadoEnvio();
		e.setEstado(ConstantesSMS.ESTADO_NO_IMPLEMENTADO);
		e.setDescripcionEstado("Operacion no soportada");
		return e;
	}

}
