package es.caib.sistra.plugins.email.impl.mock;

import es.caib.sistra.plugins.email.ConstantesEmail;
import es.caib.sistra.plugins.email.EstadoEnvio;
import es.caib.sistra.plugins.email.PluginEmailIntf;


/**
 * 	
 * 	Objeto MOCK para verificar envio por email
 *
 */
public class PluginEmailMock implements PluginEmailIntf{
	
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception{
		EstadoEnvio e = new EstadoEnvio();
		e.setEstado(ConstantesEmail.ESTADO_NO_IMPLEMENTADO);
		e.setDescripcionEstado("Operacion no soportada por el plugin");
		return e;
	}

}
