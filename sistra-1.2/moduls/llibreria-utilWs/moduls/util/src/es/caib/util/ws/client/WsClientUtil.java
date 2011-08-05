package es.caib.util.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

public class WsClientUtil {
	
	private static Log log = LogFactory.getLog(WsClientUtil.class);
	
	public static void configurePort(BindingProvider port, String url,
			String user, String pass,String auth,boolean generateTimestamp,boolean logCalls, boolean disableCnCheck) throws Exception {

		Client client = ClientProxy.getClient(port);
		HTTPConduit conduit = (HTTPConduit) client.getConduit();
		Endpoint cxfEndpoint = client.getEndpoint();
			
		if (logCalls) {
			client.getInInterceptors().add(new LoggingInInterceptor());
			client.getOutInterceptors().add(new LoggingOutInterceptor());
		}
		
		if (user != null) {
			if ("BASIC".equals(auth)) {
				
				log.debug("Utilizando autenticacion basic");
				
				port.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
						user);
				port.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
						pass);
			} else if ("USERNAMETOKEN".equals(auth)) {
				
				log.debug("Utilizando autenticacion ws-security usernameToken");
				
				Map<String, Object> outProps = new HashMap<String, Object>();
				outProps.put(WSHandlerConstants.ACTION, (generateTimestamp ? WSHandlerConstants.TIMESTAMP
						+ " " : "")
						+ WSHandlerConstants.USERNAME_TOKEN);
				outProps.put(WSHandlerConstants.USER, user);
				outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
				ClientPasswordCallback cp = new ClientPasswordCallback(pass);
				outProps.put(WSHandlerConstants.PW_CALLBACK_REF, cp);
	
				WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
				cxfEndpoint.getOutInterceptors().add(wssOut);
				cxfEndpoint.getOutInterceptors().add(new SAAJOutInterceptor());
	
			} else {
				throw new Exception("Tipo de autenticacion ws no soportada: "
						+ auth);
			}
		}else{
			log.debug("No se establece autenticacion ya que el usuario es nulo");
		}


		// Vemos si hay que validar el certificado del servidor al que se invoca en comunicacion https
		if (disableCnCheck) {
			
			log.debug("No se va a comprobar el certificado del servidor invocado (https)");
			
			TLSClientParameters tlsParams = new TLSClientParameters();
			tlsParams.setDisableCNCheck(true);
			conduit.setTlsClientParameters(tlsParams);
		}
		
		// Vemos si hay que pasar por proxy
		String proxyHost = System.getProperty("http.proxyHost");
		if (proxyHost != null && !"".equals(proxyHost)) {
			if (!validateNonProxyHosts(url)) {
				
				log.debug("Estableciendo autenticacion para proxy");
				
				HTTPClientPolicy policy = conduit.getClient();
				policy.setProxyServer(proxyHost);
				policy.setProxyServerPort(Integer.parseInt(System
						.getProperty("http.proxyPort")));

				conduit.getProxyAuthorization().setUserName(
						System.getProperty("http.proxyUser"));
				conduit.getProxyAuthorization().setPassword(
						System.getProperty("http.proxyPassword"));
			}
		}
	}
	
	/**
	 * Busca els host de la url indicada dentro de la propiedad http.nonProxyHosts de la JVM 
	 * @param url Endpoint del ws
	 * @return true si el host esta dentro de la propiedad, fals en caso contrario
	 */
	private static boolean validateNonProxyHosts(String url) throws Exception{
		String nonProxyHosts = System.getProperty("http.nonProxyHosts");
	    boolean existe = false;
	    URL urlURL;
		try {
		    if(nonProxyHosts != null && !"".equals(nonProxyHosts)){
    			urlURL = new URL(url);
    			String[] nonProxyHostsArray = nonProxyHosts.split("\\|");
    			for (int i = 0; i < nonProxyHostsArray.length; i++) {
    				String a = nonProxyHostsArray[i].replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");;
    				if (urlURL.getHost().matches(a)) {
    					existe = true;
    					break;
    				}
    			}
		    }
		} catch (MalformedURLException e) {
			log.error("Error al validar los nonProxyHost "+e.getCause(), e);
			throw e;
		}
		return existe;
	}
	

}
