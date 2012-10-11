package es.caib.sistra.persistence.plugins;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Plugin que permite tracear scripts
 */
public class PluginLog {
	private static Log log = LogFactory.getLog(PluginLog.class);	
	
	public void debug(String mensaje){
		log.debug(mensaje);
	}
}
