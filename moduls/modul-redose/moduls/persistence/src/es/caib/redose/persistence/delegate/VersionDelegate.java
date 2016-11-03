package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.Version;
import es.caib.redose.persistence.intf.VersionFacade;
import es.caib.redose.persistence.util.VersionFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class VersionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarVersion(Version modelo,Long idModelo) throws DelegateException {
        try {
            return getFacade().grabarVersion(modelo,idModelo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Version obtenerVersion(Long idVersion) throws DelegateException {
        try {
            return getFacade().obtenerVersion(idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Version obtenerVersion(String idModelo,int idVersion) throws DelegateException {
        try {
            return getFacade().obtenerVersion(idModelo,idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }        
    
    public Version obtenerVersionCompleta(String idModelo,int idVersion) throws DelegateException {
        try {
            return getFacade().obtenerVersionCompleta(idModelo,idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    public Set listarVersionesModelo(Long idModelo) throws DelegateException {
        try {
            return getFacade().listarVersionesModelo(idModelo);            
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarVersion(Long id) throws DelegateException {
        try {
            getFacade().borrarVersion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public boolean puedoBorrarVersion(Long id)throws DelegateException {
    	 try {
             return getFacade().puedoBorrarVersion(id);            
         } catch (Exception e) {
             throw new DelegateException(e);
         }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private VersionFacade getFacade() throws NamingException,CreateException,RemoteException {
        return VersionFacadeUtil.getHome( ).create();
    }

    protected VersionDelegate() throws DelegateException {        
    }       

}
