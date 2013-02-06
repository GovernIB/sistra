package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.persistence.intf.TramiteNivelFacade;
import es.caib.sistra.persistence.util.TramiteNivelFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class TramiteNivelDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarTramiteNivel(TramiteNivel tramiteNivel,Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().grabarTramiteNivel(tramiteNivel,idTramiteVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public TramiteNivel obtenerTramiteNivel(Long idTramiteNivel) throws DelegateException {
        try {
            return getFacade().obtenerTramiteNivel(idTramiteNivel);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
   
   public Set listarTramiteNiveles(Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().listarNivelesTramite(idTramiteVersion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarTramiteNivel(Long id) throws DelegateException {
        try {
            getFacade().borrarTramiteNivel(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private TramiteNivelFacade getFacade() throws NamingException,RemoteException,CreateException {  	    	
    	return TramiteNivelFacadeUtil.getHome( ).create();
    }

    protected TramiteNivelDelegate() throws DelegateException {       
    }                  
}

