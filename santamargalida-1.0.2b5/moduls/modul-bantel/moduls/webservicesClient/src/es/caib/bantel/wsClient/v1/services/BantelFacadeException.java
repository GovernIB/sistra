
package es.caib.bantel.wsClient.v1.services;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.2
 * Fri Jul 17 09:04:37 CEST 2009
 * Generated source version: 2.2.2
 * 
 */

@WebFault(name = "fault", targetNamespace = "urn:es:caib:bantel:ws:v1:model:BantelFacade")
public class BantelFacadeException extends Exception {
    public static final long serialVersionUID = 20090717090437L;
    
    private es.caib.bantel.wsClient.v1.model.BantelFacadeException fault;

    public BantelFacadeException() {
        super();
    }
    
    public BantelFacadeException(String message) {
        super(message);
    }
    
    public BantelFacadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BantelFacadeException(String message, es.caib.bantel.wsClient.v1.model.BantelFacadeException fault) {
        super(message);
        this.fault = fault;
    }

    public BantelFacadeException(String message, es.caib.bantel.wsClient.v1.model.BantelFacadeException fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public es.caib.bantel.wsClient.v1.model.BantelFacadeException getFaultInfo() {
        return this.fault;
    }
}
