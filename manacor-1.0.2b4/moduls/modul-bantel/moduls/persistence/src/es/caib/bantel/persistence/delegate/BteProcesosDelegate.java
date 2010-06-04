package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.persistence.intf.BteProcesosFacade;
import es.caib.bantel.persistence.util.BteProcesosFacadeUtil;

/**
 * Interfaz para operar con procesos de la BTE
 */
public class BteProcesosDelegate implements StatelessDelegate {

	public void avisoBackOffices() throws DelegateException {
        try {
            getFacade().avisoBackOffices();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
	public void avisoGestores() throws DelegateException {
        try {
            getFacade().avisoGestores();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   
    private BteProcesosFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return BteProcesosFacadeUtil.getHome( ).create();
    }

    protected BteProcesosDelegate() throws DelegateException {
        
    }                  
}

