package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.zonaper.model.LogVerificacionMovil;
import es.caib.zonaper.persistence.intf.LogVerificacionMovilFacade;
import es.caib.zonaper.persistence.util.LogVerificacionMovilFacadeUtil;

/**
 * Business delegate para operar con Log de Registro.
 */
public class LogVerificacionMovilDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	 public LogVerificacionMovil obtenerLogVerificacionMovil(Long id) throws DelegateException {
        try {
            return getFacade().obtenerLogVerificacionMovil(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	 public void grabarLogVerificacionMovil(LogVerificacionMovil logRegistro) throws DelegateException {
        try {
           getFacade().grabarLogVerificacionMovil(logRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    

	    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private LogVerificacionMovilFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return LogVerificacionMovilFacadeUtil.getHome( ).create();
    }

    protected LogVerificacionMovilDelegate() throws DelegateException {       
    }                  
}

