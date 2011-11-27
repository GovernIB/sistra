package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.persistence.intf.VersionWSFacade;
import es.caib.sistra.persistence.util.VersionWSFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class VersionWSDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    
    public List obtenerVersiones() throws DelegateException {
        try {
            return getFacade().obtenerVersiones();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
   
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private VersionWSFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return VersionWSFacadeUtil.getHome().create();
    }

    protected VersionWSDelegate() throws DelegateException {        
    }                  
}

