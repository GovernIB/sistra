package es.caib.bantel.wsClient.v2.client;

import java.util.List;
import java.util.concurrent.Future;

import javax.xml.namespace.QName;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.wsClient.v2.model.AvisoEntradasResponse;
import es.caib.util.ws.client.WsClientSistraUtil;

/**
 * Cliente de WS para interfaz version 1 de backoffices para Sistra
 * 
 */
public class ClienteWS {
	
	private static Log log = LogFactory.getLog(ClienteWS.class);
	private static final QName SERVICE_NAME = new QName("urn:es:caib:bantel:ws:v2:services", "BantelFacadeService");
	private static final QName PORT_NAME = new QName("urn:es:caib:bantel:ws:v2:services", "BantelFacade");
	
	/**
	 * Avisa Entradas a traves interfaz de ws
	 * 
	 * @param entradas de la que se quiere avisar
	 * @param url Endpoint del ws
	 * @param user Usuario autenticación (nulo si no autenticación)
	 * @param pass Password autenticación (nulo si no autenticación)
	 * @param async indica si la llamada al Web Service se tiene que realizar de forma asincrona o no
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void avisarEntradasWS(List entradas, String url, String user, String pass, boolean async) throws Exception{
		
		log.debug("Avisar Entradas por webservice v2: url=" + url);
		
		try {
			es.caib.bantel.wsClient.v2.services.BantelFacade port = generarPort(url,user,pass);
			
			es.caib.bantel.wsClient.v2.model.ReferenciasEntrada refsEnt = new es.caib.bantel.wsClient.v2.model.ReferenciasEntrada();
	    	for(int i=0;i<entradas.size();i++){
	    		es.caib.bantel.wsClient.v2.model.ReferenciaEntrada refEnt = new es.caib.bantel.wsClient.v2.model.ReferenciaEntrada();
	    		refEnt.setNumeroEntrada( ((ReferenciaTramiteBandeja) entradas.get(i)).getNumeroEntrada());
	    		refEnt.setClaveAcceso( ((ReferenciaTramiteBandeja) entradas.get(i)).getClaveAcceso());
	    		refsEnt.getReferenciaEntrada().add(refEnt);
	    	}
	    	if(async){
	    		AsyncHandler<AvisoEntradasResponse> handler = null;
	    		Future<?> avisoEntradasReturn = port.avisoEntradasAsync(refsEnt, handler);
	    	}else{
	    		port.avisoEntradas(refsEnt);
	    	}
		}catch(es.caib.bantel.wsClient.v2.services.BantelFacadeException e){
	    	log.debug("Error en el aviso de entradas: "+e.getMessage(),e);
	    	throw new Exception(e);
	    } catch(SOAPFaultException e){
	    	log.debug( "Error en el interceptor usuario incorrecto: " + e.getMessage(), e );
	    	throw new Exception(e);
	    } catch(Exception e){
		   log.debug("Error en el web service. "+e.getMessage(), e);
		   throw e;
	    }
	}
	
    // ----------------------------------------------------------------------------------------------------
	//	 FUNCIONES PRIVADAS	
	//----------------------------------------------------------------------------------------------------
	
	
	private static es.caib.bantel.wsClient.v2.services.BantelFacade generarPort(String url,String user,String pass) throws Exception{
		javax.xml.ws.Service service =javax.xml.ws.Service.create(SERVICE_NAME); 
		service.addPort(PORT_NAME,javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, url);
		es.caib.bantel.wsClient.v2.services.BantelFacade port = service.getPort(PORT_NAME,es.caib.bantel.wsClient.v2.services.BantelFacade.class);
        
		// Configura puerto para autenticacion y paso por proxy
		WsClientSistraUtil.configurePort((BindingProvider)port,url,user,pass);
                
        return port;
	}
		
}
