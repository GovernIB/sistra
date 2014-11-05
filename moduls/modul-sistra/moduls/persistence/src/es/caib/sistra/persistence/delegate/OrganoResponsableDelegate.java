package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.intf.OrganoResponsableFacade;
import es.caib.sistra.persistence.util.OrganoResponsableFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class OrganoResponsableDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarOrganoResponsable(OrganoResponsable organoResponsable) throws DelegateException {
        try {
            return getFacade().grabarOrganoResponsable(organoResponsable);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public OrganoResponsable obtenerOrganoResponsable(Long idOrganoResponsable) throws DelegateException {
        try {
            return getFacade().obtenerOrganoResponsable(idOrganoResponsable);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public List listarOrganoResponsables() throws DelegateException {
        try {
            return getFacade().listarOrganoResponsables();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    public List listarOrganoResponsables(String filtro) throws DelegateException {
        try {
            return getFacade().listarOrganoResponsables(filtro);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public void borrarOrganoResponsable(Long id) throws DelegateException {
        try {
            getFacade().borrarOrganoResponsable(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private OrganoResponsableFacade getFacade() throws NamingException,RemoteException,CreateException {   	    	
    	return OrganoResponsableFacadeUtil.getHome( ).create();
    }

    protected OrganoResponsableDelegate() throws DelegateException {       
    }                  
}

