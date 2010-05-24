package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import es.caib.sistra.model.*;
import es.caib.sistra.persistence.intf.*;
import es.caib.sistra.persistence.util.*;

/**
 * Business delegate para operar con dummys.
 */
public class TramiteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarTramite(Tramite tramite,Long idOrg) throws DelegateException {
        try {
            return getFacade().grabarTramite(tramite,idOrg);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Tramite obtenerTramite(Long idTramite) throws DelegateException {
        try {
            return getFacade().obtenerTramite(idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Tramite obtenerTramite(String idTramite) throws DelegateException {
        try {        	
            return getFacade().obtenerTramite(idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
  
    public List listarTramites() throws DelegateException {
        try {
            return getFacade().listarTramites();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    public Set listarTramitesOrganoResponsable(Long id) throws DelegateException {
        try {
            return getFacade().listarTramitesOrganoResponsable(id);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarTramite(Long id) throws DelegateException {
        try {
            getFacade().borrarTramite(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private TramiteFacade getFacade() throws NamingException,RemoteException,CreateException {         	    	
    	return TramiteFacadeUtil.getHome( ).create();
    }

    protected TramiteDelegate() throws DelegateException {      
    }                  
}

