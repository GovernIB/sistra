package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.intf.UbicacionFacade;
import es.caib.redose.persistence.util.UbicacionFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class UbicacionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarUbicacion(Ubicacion ubicacion) throws DelegateException {
        try {
            return getFacade().grabarUbicacion(ubicacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Ubicacion obtenerUbicacion(Long idUbicacion) throws DelegateException {
        try {
            return getFacade().obtenerUbicacion(idUbicacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Ubicacion obtenerUbicacion(String idUbicacion) throws DelegateException {
        try {        	
            return getFacade().obtenerUbicacion(idUbicacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
  
    public List listarUbicaciones() throws DelegateException {
        try {
            return getFacade().listarUbicaciones();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarUbicacion(Long id) throws DelegateException {
        try {
            getFacade().borrarUbicacion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private UbicacionFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return UbicacionFacadeUtil.getHome( ).create();
    }

    protected UbicacionDelegate() throws DelegateException {        
    }                  
}
