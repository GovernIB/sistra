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
	private boolean debugEnabled;

	public PluginLog(LogScript scriptLog, boolean pdebugEnabled){
		logScript=scriptLog;
		debugEnabled = pdebugEnabled;
	}

	public void debug(String mensaje){
		if (logScript!=null) {
			logScript.addLogDebug(mensaje);
		}
		if (debugEnabled) {
			log.debug(mensaje);
		}
	}
}
