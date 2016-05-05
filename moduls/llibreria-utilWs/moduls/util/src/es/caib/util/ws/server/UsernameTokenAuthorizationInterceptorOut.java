package es.caib.util.ws.server;

import java.util.Map;
import java.util.Properties;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

import es.caib.util.ws.ConfigurationUtil;

public class UsernameTokenAuthorizationInterceptorOut extends WSS4JOutInterceptor{

	protected boolean activar;
    protected boolean generarTimestamp;

	public UsernameTokenAuthorizationInterceptorOut(Map<String, Object> properties) {

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


	    	if (activar && generarTimestamp) {
	    		properties.put( "action", WSHandlerConstants.TIMESTAMP);
	    	} else {
	    		properties.put( "action", WSHandlerConstants.NO_SECURITY);
	    	}


		 }catch (Exception ex){
			 throw new Fault(ex);
		 }

	}

	public void handleMessage(SoapMessage msg) throws Fault {
		if (!activar) {
			 return;
		 }else{
			 super.handleMessage(msg);
		 }
	}

}
