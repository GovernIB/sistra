package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.intf.TramiteVersionFacade;
import es.caib.sistra.persistence.util.TramiteVersionFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class TramiteVersionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarTramiteVersion(TramiteVersion tramiteVersion,Long idTramite) throws DelegateException {
        try {
            return getFacade().grabarTramiteVersion(tramiteVersion,idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public TramiteVersion obtenerTramiteVersion(Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().obtenerTramiteVersion(idTramiteVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramiteVersion obtenerTramiteVersion(String idTramite,int idVersion) throws DelegateException {
        try {
            return getFacade().obtenerTramiteVersion(idTramite,idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramiteVersion obtenerTramiteVersionCompleto(String idTramite,int idVersion) throws DelegateException {
        try {
            return getFacade().obtenerTramiteVersionCompleto(idTramite,idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
  
    public Set listarTramiteVersiones(Long idTramite) throws DelegateException {
        try {
            return getFacade().listarTramiteVersiones(idTramite);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarTramiteVersion(Long id) throws DelegateException {
        try {
            getFacade().borrarTramiteVersion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void bloquearTramiteVersion(Long idTramiteVersion,String bloquear,String usuario) throws DelegateException {
        try {
            getFacade().bloquearTramiteVersion(idTramiteVersion,bloquear,usuario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private TramiteVersionFacade getFacade() throws NamingException,RemoteException,CreateException {    	    	
    	return TramiteVersionFacadeUtil.getHome( ).create();
    }

    protected TramiteVersionDelegate() throws DelegateException {        
    }                  
}

