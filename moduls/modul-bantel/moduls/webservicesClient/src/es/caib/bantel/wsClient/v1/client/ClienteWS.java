package es.caib.bantel.wsClient.v1.client;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import es.caib.bantel.model.ReferenciaTramiteBandeja;

/**
 * Cliente de WS para interfaz version 1 de backoffices para Sistra
 * 
 */
public class ClienteWS {
	
	private static Log log = LogFactory.getLog(ClienteWS.class);
	private static final QName SERVICE_NAME = new QName("urn:es:caib:bantel:ws:v1:services", "BantelFacadeService");
	private static final QName PORT_NAME = new QName("urn:es:caib:bantel:ws:v1:services", "BantelFacade");
	
	/**
	 * Avisa Entradas a traves interfaz de ws
	 * 
	 * @param entradas de la que se quiere avisar
	 * @param url Endpoint del ws
	 * @param user Usuario autenticación (nulo si no autenticación)
	 * @param pass Password autenticación (nulo si no autenticación)
	 * @throws Exception 
	 * @throws Exception
	 */

	
	
	public static void avisarEntradasWS(List entradas, String url, String user, String pass) throws Exception{
		
		log.debug("Avisar Entradas por webservice v1: url=" + url);
		
		try {
			es.caib.bantel.wsClient.v1.services.BantelFacade port = generarPort(url,user,pass);
			
			es.caib.bantel.wsClient.v1.model.ReferenciasEntrada refsEnt = new es.caib.bantel.wsClient.v1.model.ReferenciasEntrada();
	    	for(int i=0;i<entradas.size();i++){
	    		es.caib.bantel.wsClient.v1.model.ReferenciaEntrada refEnt = new es.caib.bantel.wsClient.v1.model.ReferenciaEntrada();
	    		refEnt.setNumeroEntrada( ((ReferenciaTramiteBandeja) entradas.get(i)).getNumeroEntrada());
	    		refEnt.setClaveAcceso( ((ReferenciaTramiteBandeja) entradas.get(i)).getClaveAcceso());
	    		refsEnt.getReferenciaEntrada().add(refEnt);
	    	}
	    	port.avisoEntradas(refsEnt);
	    }catch(es.caib.bantel.wsClient.v1.services.BantelFacadeException e){
	    	log.error("Error en el aviso de entradas: "+e.getMessage(),e);
	    	throw new Exception(e);
	    } catch(SOAPFaultException e){
	    	log.error( "Error en el interceptor usuario incorrecto: " + e.getMessage(), e );
	    	throw new Exception(e);
	   }
	}
	
    // ----------------------------------------------------------------------------------------------------
	//	 FUNCIONES PRIVADAS	
	//----------------------------------------------------------------------------------------------------
	
	
	private static es.caib.bantel.wsClient.v1.services.BantelFacade generarPort(String url,String user,String pass) throws Exception{
		javax.xml.ws.Service service =javax.xml.ws.Service.create(SERVICE_NAME); 
		service.addPort(PORT_NAME,javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
		es.caib.bantel.wsClient.v1.services.BantelFacade port = service.getPort(PORT_NAME,es.caib.bantel.wsClient.v1.services.BantelFacade.class);
        BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, user);
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, pass);
        
        //si no existe el host dentro del nonProxyHost nos connectamos al proxy, si existe no nos connectamos.
        String proxyHost = System.getProperty("http.proxyHost"); 
        if( proxyHost != null && !"".equals(proxyHost)){
        	if(!validateNonProxyHosts(url)){
        		Client client = ClientProxy.getClient(port);
        		HTTPConduit conduit = (HTTPConduit) client.getConduit();
        		HTTPClientPolicy policy = conduit.getClient();  
        		policy.setProxyServer(proxyHost);  
        		policy.setProxyServerPort(Integer.parseInt(System.getProperty("http.proxyPort")));  
          
        		conduit.getProxyAuthorization().setUserName(System.getProperty("http.proxyUser"));  
        		conduit.getProxyAuthorization().setPassword(System.getProperty("http.proxyPassword"));
        	}
        }
        return port;
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
