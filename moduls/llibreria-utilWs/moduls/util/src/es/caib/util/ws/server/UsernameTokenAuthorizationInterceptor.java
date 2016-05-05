package es.caib.util.ws.server;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

import es.caib.util.ws.ConfigurationUtil;

public class UsernameTokenAuthorizationInterceptor extends WSS4JInInterceptor{

	private static Log log = LogFactory.getLog(UsernameTokenAuthorizationInterceptor.class);

	protected boolean activar;
    protected boolean generarTimestamp;


	public UsernameTokenAuthorizationInterceptor(Map<String, Object> properties) {

		super(properties);

		try{

			// Verificamos si esta marcado la activacion mediante xml o es mediante propiedades
	    	if ( properties.get("activar") != null) {
	    		// Activacion mediante XML
	    		activar =  Boolean.parseBoolean((String) properties.get("activar"));
	    		generarTimestamp = Boolean.parseBoolean((String) properties.get("generarTimestamp"));
	    	} else {
	    		// Activacion mediante propiedades (para SISTRA)
	    		Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
	    		String auth = props.getProperty("sistra.ws.authenticacion");
	    		activar = "USERNAMETOKEN".equals(auth);
	    		String timestamp = props.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp");
	    		generarTimestamp = "true".equals(timestamp);
	    	}

	    	if (activar){
    			if (generarTimestamp) {
    				properties.put( "action", WSHandlerConstants.TIMESTAMP + " " + properties.get("action"));
    	    	}
    		} else {
    			properties.put( "action", WSHandlerConstants.NO_SECURITY);
    		}

		 }catch (Exception ex){
			 throw new Fault(ex);
		 }

	}

	public void handleMessage(SoapMessage msg) throws Fault {

		log.debug("UsernameTokenAuthorizationInterceptor - handleMessage init");

		if (!activar) {
			 log.debug("UsernameTokenAuthorizationInterceptor - handleMessage end (no USERNAMETOKEN auth)");
			 return;
		 }else{
			 log.debug("UsernameTokenAuthorizationInterceptor - handleMessage end");
			 super.handleMessage(msg);
		 }
	}

}
