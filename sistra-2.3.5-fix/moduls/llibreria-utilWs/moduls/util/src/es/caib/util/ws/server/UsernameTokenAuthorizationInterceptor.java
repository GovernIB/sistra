package es.caib.util.ws.server;

import java.util.Map;
import java.util.Properties;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

import es.caib.util.ws.ConfigurationUtil;
import es.caib.util.ws.Constantes;

public class UsernameTokenAuthorizationInterceptor extends WSS4JInInterceptor{
	
	public UsernameTokenAuthorizationInterceptor(Map<String, Object> properties) {
		
		super(properties);
		
		try{		
			
			String tipoConfiguracion = (String) properties.get("tipoConfiguracion");
			if (tipoConfiguracion == null) {
				tipoConfiguracion = Constantes.TIPO_CONFIGURACION_PROPERTIES;
			}
			
			Properties props;
			if (Constantes.TIPO_CONFIGURACION_PROPERTIES.equals(tipoConfiguracion)) {
				props = ConfigurationUtil.getInstance().obtenerPropiedades();							
			 } else {
				 props = System.getProperties();
			 }
			
			String auth = props.getProperty("sistra.ws.authenticacion");
			String timestamp = props.getProperty("sistra.ws.authenticacion.usernameToken.generateTimestamp");
			
			 if ("USERNAMETOKEN".equals(auth) && "true".equals(timestamp)){
				 properties.put( "action", WSHandlerConstants.TIMESTAMP + " " + properties.get("action"));
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
			 String tipoConfiguracion = (String) this.getProperties().get("tipoConfiguracion");
			 if (tipoConfiguracion == null) {
				tipoConfiguracion = Constantes.TIPO_CONFIGURACION_PROPERTIES;
			 }
			 Properties props;
			 if (Constantes.TIPO_CONFIGURACION_PROPERTIES.equals(tipoConfiguracion)) {
				props = ConfigurationUtil.getInstance().obtenerPropiedades();							
			 } else {
				 props = System.getProperties();
			 }
			 
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
