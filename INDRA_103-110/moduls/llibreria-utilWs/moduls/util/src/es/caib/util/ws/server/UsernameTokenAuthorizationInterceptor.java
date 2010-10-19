package es.caib.util.ws.server;

import java.util.Map;
import java.util.Properties;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

import es.caib.util.ws.ConfigurationUtil;

public class UsernameTokenAuthorizationInterceptor extends WSS4JInInterceptor{
	
	public UsernameTokenAuthorizationInterceptor(Map<String, Object> properties) {
		
		super(properties);
		
		try{		
			 Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
			 String timestamp = props.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp");
			 if ("true".equals(timestamp)){
				 properties.put( "action", WSHandlerConstants.TIMESTAMP + " " + properties.get("action"));
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
