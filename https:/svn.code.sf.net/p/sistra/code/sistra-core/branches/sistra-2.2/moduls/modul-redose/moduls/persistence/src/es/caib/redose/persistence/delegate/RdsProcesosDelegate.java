package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.persistence.intf.RdsProcesos;
import es.caib.redose.persistence.util.RdsProcesosUtil;

/**
 * Business delegate para operar con RDS.
 */
public class RdsProcesosDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	 public void purgadoDocumentos() throws DelegateException{
    	try {
           getFacade().purgadoDocumentos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public void consolidacionGestorDocumental() throws DelegateException{
		 try {
	       getFacade().consolidacionGestorDocumental();
	    } catch (Exception e) {
	        throw new DelegateException(e);
	    }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RdsProcesos getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RdsProcesosUtil.getHome().create();
    }

    protected RdsProcesosDelegate() throws DelegateException {       
    }                  
}
