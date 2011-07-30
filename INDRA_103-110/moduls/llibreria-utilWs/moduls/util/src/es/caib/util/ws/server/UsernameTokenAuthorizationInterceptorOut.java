package es.caib.util.ws.server;

import java.util.Map;
import java.util.Properties;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

import es.caib.util.ws.ConfigurationUtil;

public class UsernameTokenAuthorizationInterceptorOut extends WSS4JOutInterceptor{
	
	public UsernameTokenAuthorizationInterceptorOut(Map<String, Object> properties) {
		
		super(properties);
		
		try{		
			 Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
			 String auth = props.getProperty("sistra.ws.authenticacion");
			 String timestamp = props.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp");
			 if ("USERNAMETOKEN".equals(auth) && "true".equals(timestamp)){
				 properties.put( "action", WSHandlerConstants.TIMESTAMP);
			 } else {
				 properties.put( "action", WSHandlerConstants.NO_SECURITY);				
			 }			 
		 }catch (Exception ex){
			 throw new Fault(ex);
		 }		 
		 
	}
		
	public void handleMessage(SoapMessage msg) throws Fault {
		String auth = null;
		 try{		
			 Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
			 auth = props.getProperty("sistra.ws.authenticacion");			 
		 }catch (Exception ex){
			 throw new Fault(ex);
		 }
		 
		 if (!"USERNAMETOKEN".equals(auth)) {
			 return;
		 }else{
			 super.handleMessage(msg);
		 }
	}

}
