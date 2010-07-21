package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.persistence.intf.MensajeTramiteFacade;
import es.caib.sistra.persistence.util.MensajeTramiteFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class MensajeTramiteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarMensajeTramite(MensajeTramite mensajeTramite,Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().grabarMensajeTramite(mensajeTramite,idTramiteVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public MensajeTramite obtenerMensajeTramite(Long idMensajeTramite) throws DelegateException {
        try {
            return getFacade().obtenerMensajeTramite(idMensajeTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public MensajeTramite obtenerMensajeTramite( String idTramite,int idVersion,String idMensajeTramite ) throws DelegateException {
    	try {
            return getFacade().obtenerMensajeTramite(idTramite,idVersion,idMensajeTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Map listarMensajeTramites(Long idTramiteVersion) throws DelegateException {
        try {
            return getFacade().listarMensajesTramite(idTramiteVersion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarMensajeTramite(Long id) throws DelegateException {
        try {
            getFacade().borrarMensajeTramite(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private MensajeTramiteFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return MensajeTramiteFacadeUtil.getHome( ).create();
    }

    protected MensajeTramiteDelegate() throws DelegateException {        
    }                  
}

