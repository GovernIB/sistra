
/*
 * 
 */

package es.caib.sistra.wsClient.v2.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.7
 * Wed Jul 25 12:21:54 CEST 2012
 * Generated source version: 2.2.7
 * 
 */


@WebServiceClient(name = "SistraFacadeService", 
                  wsdlLocation = "SistraFacade.wsdl",
                  targetNamespace = "urn:es:caib:sistra:ws:v2:services") 
public class SistraFacadeService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("urn:es:caib:sistra:ws:v2:services", "SistraFacadeService");
    public final static QName SistraFacade = new QName("urn:es:caib:sistra:ws:v2:services", "SistraFacade");
    static {
        URL url = null;
        try {
            url = new URL("SistraFacade.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from SistraFacade.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SistraFacadeService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SistraFacadeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SistraFacadeService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns SistraFacade
     */
    @WebEndpoint(name = "SistraFacade")
    public SistraFacade getSistraFacade() {
        return super.getPort(SistraFacade, SistraFacade.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SistraFacade
     */
    @WebEndpoint(name = "SistraFacade")
    public SistraFacade getSistraFacade(WebServiceFeature... features) {
        return super.getPort(SistraFacade, SistraFacade.class, features);
    }

}
