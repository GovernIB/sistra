package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.RegistroExternoPreparado;
import es.caib.zonaper.persistence.intf.RegistroExternoPreparadoFacade;
import es.caib.zonaper.persistence.util.RegistroExternoPreparadoFacadeUtil;

/**
 * Business delegate para operar con RegistrosExternos.
 */
public class RegistroExternoPreparadoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarRegistroExternoPreparado(RegistroExternoPreparado registro) throws DelegateException {
        try {
            return getFacade().grabarRegistroExternoPreparado(registro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public RegistroExternoPreparado obtenerRegistroExternoPreparado(Long id) throws DelegateException {
        try {
            return getFacade().obtenerRegistroExternoPreparado(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarCaducados() throws DelegateException {
        try {
            return getFacade().listarCaducados();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void borrarRegistroExternoPreparado(Long id) throws DelegateException {
        try {
            getFacade().borrarRegistroExternoPreparado(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RegistroExternoPreparadoFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RegistroExternoPreparadoFacadeUtil.getHome( ).create();
    }

    protected RegistroExternoPreparadoDelegate() throws DelegateException {       
    }                  
}

