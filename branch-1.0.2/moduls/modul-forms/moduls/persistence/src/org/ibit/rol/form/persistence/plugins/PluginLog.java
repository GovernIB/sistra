package org.ibit.rol.form.persistence.plugins;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.LogScript;

/**
 * Plugin que permite tracear scripts
 */
public class PluginLog {
	private static Log log = LogFactory.getLog(PluginLog.class);	
	private LogScript logScript;
	
	public PluginLog(LogScript scriptLog){
		logScript=scriptLog;		
	}
	
	public void debug(String mensaje){
		if (logScript!=null) logScript.addLogDebug(mensaje);
		log.debug(mensaje);		
	}
}
