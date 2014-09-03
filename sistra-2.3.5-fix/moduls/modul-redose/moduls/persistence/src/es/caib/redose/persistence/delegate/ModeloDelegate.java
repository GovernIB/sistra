package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.Modelo;
import es.caib.redose.persistence.intf.ModeloFacade;
import es.caib.redose.persistence.util.ModeloFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class ModeloDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarModelo(Modelo modelo) throws DelegateException {
        try {
            return getFacade().grabarModelo(modelo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Modelo obtenerModelo(Long idModelo) throws DelegateException {
        try {
            return getFacade().obtenerModelo(idModelo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Modelo obtenerModelo(String idModelo) throws DelegateException {
        try {        	
            return getFacade().obtenerModelo(idModelo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
  
    public List listarModelos() throws DelegateException {
        try {
            return getFacade().listarModelos();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarModelo(Long id) throws DelegateException {
        try {
            getFacade().borrarModelo(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ModeloFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return ModeloFacadeUtil.getHome( ).create();
    }

    protected ModeloDelegate() throws DelegateException {       
    }                  
}
