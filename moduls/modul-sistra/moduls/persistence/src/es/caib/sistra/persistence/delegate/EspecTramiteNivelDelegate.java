package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.intf.EspecTramiteNivelFacade;
import es.caib.sistra.persistence.util.EspecTramiteNivelFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class EspecTramiteNivelDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarEspecTramiteNivel(EspecTramiteNivel espec) throws DelegateException {
        try {
            return getFacade().grabarEspecTramiteNivel(espec);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public EspecTramiteNivel obtenerEspecTramiteNivel(Long idEspecTramiteNivel) throws DelegateException {
        try {
            return getFacade().obtenerEspecTramiteNivel(idEspecTramiteNivel);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    
   public void borrarEspecTramiteNivel(Long id) throws DelegateException {
        try {
            getFacade().borrarEspecTramiteNivel(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
   
   public TramiteVersion obtenerTramiteVersion(Long id) throws DelegateException {
       try {
           return getFacade().obtenerTramiteVersion(id);            
       } catch (Exception e) {
           throw new DelegateException(e);
       }
   }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private EspecTramiteNivelFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return EspecTramiteNivelFacadeUtil.getHome().create();
    }

    protected EspecTramiteNivelDelegate() throws DelegateException {
    }                  
}

