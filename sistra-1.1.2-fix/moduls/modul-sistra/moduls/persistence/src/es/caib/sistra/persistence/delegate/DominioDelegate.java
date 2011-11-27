package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.Dominio;
import es.caib.sistra.persistence.intf.DominioFacade;
import es.caib.sistra.persistence.util.DominioFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class DominioDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarDominio(Dominio dominio, Long idOrg ) throws DelegateException {
        try {
            return getFacade().grabarDominio(dominio, idOrg );
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Dominio obtenerDominio(Long idDominio) throws DelegateException {
        try {
            return getFacade().obtenerDominio(idDominio);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Dominio obtenerDominio(String idDominio) throws DelegateException {
        try {
            return getFacade().obtenerDominio(idDominio);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public List listarDominios() throws DelegateException {
        try {
            return getFacade().listarDominios();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    public List listarDominios( Long codOrganoOrigen ) throws DelegateException {
        try {
            return getFacade().listarDominios( codOrganoOrigen );
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public void borrarDominio(Long id) throws DelegateException {
        try {
            getFacade().borrarDominio(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private DominioFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return DominioFacadeUtil.getHome().create();
    }

    protected DominioDelegate() throws DelegateException {        
    }                  
}

