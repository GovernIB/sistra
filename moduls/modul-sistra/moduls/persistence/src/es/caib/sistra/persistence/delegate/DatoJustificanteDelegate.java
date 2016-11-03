package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.persistence.intf.DatoJustificanteFacade;
import es.caib.sistra.persistence.util.DatoJustificanteFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class DatoJustificanteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarDatoJustificante(DatoJustificante datoJustificante,Long id) throws DelegateException {
        try {
            return getFacade().grabarDatoJustificante(datoJustificante,id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public DatoJustificante obtenerDatoJustificante(Long idDatoJustificante) throws DelegateException {
        try {
            return getFacade().obtenerDatoJustificante(idDatoJustificante);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
     
    public List listarDatoJustificantees(Long id) throws DelegateException {
        try {
            return getFacade().listarDatosJustificante(id);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarDatoJustificante(Long id) throws DelegateException {
        try {
            getFacade().borrarDatoJustificante(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void upDatoJustificante(Long id) throws DelegateException {
        try {
            getFacade().upDatoJustificante(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void downDatoJustificante(Long id) throws DelegateException {
        try {
            getFacade().downDatoJustificante(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private DatoJustificanteFacade getFacade() throws NamingException,RemoteException,CreateException {     	
    	return DatoJustificanteFacadeUtil.getHome().create();    	    	   
    }
   
}

