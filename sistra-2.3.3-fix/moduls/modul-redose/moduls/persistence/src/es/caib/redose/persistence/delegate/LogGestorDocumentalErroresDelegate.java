package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.Page;
import es.caib.redose.persistence.intf.LogGestorDocumentalErroresFacade;
import es.caib.redose.persistence.util.LogGestorDocumentalErroresFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class LogGestorDocumentalErroresDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public LogGestorDocumentalError obtenerLogGestorDocumentalError(Long idLogError) throws DelegateException {
        try {
            return getFacade().obtenerLogGestorDocumentalError(idLogError);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
   public List listarLogGestorDocumentalErrores(Date startDate, Date endDate, String idUsuario) throws DelegateException {
        try {
            return getFacade().listarLogGestorDocumentalErrores(startDate,endDate,idUsuario);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarLogGestorDocumentalError(Long id) throws DelegateException {
        try {
            getFacade().borrarLogGestorDocumentalError(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    
    public void borrarLogGestorDocumentalErrores() throws DelegateException {
        try {
            getFacade().borrarLogGestorDocumentalErrores();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
   
    public Page busquedaPaginada(int pagina, int longitudPagina )throws DelegateException {
        try {
            return getFacade().busquedaPaginada(pagina, longitudPagina);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public Long grabarError(LogGestorDocumentalError obj) throws DelegateException {
    	 try {
             return getFacade().grabarError(obj);
         } catch (Exception e) {
         	e.printStackTrace();	
             throw new DelegateException(e);
         }
    }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */    
    private LogGestorDocumentalErroresFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return LogGestorDocumentalErroresFacadeUtil.getHome( ).create();
    }

    protected LogGestorDocumentalErroresDelegate() throws DelegateException {       
    }                  
}
