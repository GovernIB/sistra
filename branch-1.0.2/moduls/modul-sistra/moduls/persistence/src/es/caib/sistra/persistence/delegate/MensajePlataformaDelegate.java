package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.MensajePlataforma;
import es.caib.sistra.persistence.intf.MensajePlataformaFacade;
import es.caib.sistra.persistence.util.MensajePlataformaFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class MensajePlataformaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarMensajePlataforma(MensajePlataforma mensajePlataforma) throws DelegateException {
        try {
            return getFacade().grabarMensajePlataforma(mensajePlataforma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public MensajePlataforma obtenerMensajePlataforma(Long idMensajePlataforma) throws DelegateException {
        try {
            return getFacade().obtenerMensajePlataforma(idMensajePlataforma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public MensajePlataforma obtenerMensajePlataforma(String idMensajePlataforma) throws DelegateException {
        try {        	
            return getFacade().obtenerMensajePlataforma(idMensajePlataforma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
  
    public List listarMensajePlataformas() throws DelegateException {
        try {
            return getFacade().listarMensajePlataformas();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private MensajePlataformaFacade getFacade() throws NamingException,RemoteException,CreateException {         	    	
    	return MensajePlataformaFacadeUtil.getHome( ).create();
    }

    protected MensajePlataformaDelegate() throws DelegateException {      
    }                  
}

