package es.caib.exception;

import org.apache.commons.logging.Log;

public class UtilLog {

	public static void logError(Log log, String message, Exception ex) {
		 LoggableIntf lex = (LoggableIntf) ex;
		 if (!lex.isLogged()) {
			 log.error(message, ex);
			 lex.setLogged(true);
		 }
	}

}
