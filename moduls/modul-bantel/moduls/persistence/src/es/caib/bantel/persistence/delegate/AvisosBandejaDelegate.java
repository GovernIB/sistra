package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.intf.AvisosBandejaFacade;
import es.caib.bantel.persistence.util.AvisosBandejaFacadeUtil;

/**
 * Business delegate para operar con AvisosBandeja.
 */
public class AvisosBandejaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	public void actualizarAviso(String idAviso, Date fechaAviso) throws DelegateException {
        try {
            getFacade().actualizarAviso(idAviso, fechaAviso);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public Date obtenerFechaUltimoAviso(String idAviso)  throws DelegateException {
        try {
            return getFacade().obtenerFechaUltimoAviso(idAviso);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }                
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private AvisosBandejaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return AvisosBandejaFacadeUtil.getHome( ).create();
    }

    protected AvisosBandejaDelegate() throws DelegateException {       
    }                  
}

