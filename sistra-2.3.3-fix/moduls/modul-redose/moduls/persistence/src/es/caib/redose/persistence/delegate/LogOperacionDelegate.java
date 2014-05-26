package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.Page;
import es.caib.redose.persistence.intf.LogOperacionFacade;
import es.caib.redose.persistence.util.LogOperacionFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class LogOperacionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarLogOperacion(LogOperacion logOperacion) throws DelegateException {
        try {
            return getFacade().grabarLogOperacion(logOperacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public LogOperacion obtenerLogOperacion(Long idLogOperacion) throws DelegateException {
        try {
            return getFacade().obtenerLogOperacion(idLogOperacion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
   public List listarLogOperaciones(Date startDate, Date endDate, String idUsuario, String idTipoOperacion) throws DelegateException {
        try {
            return getFacade().listarLogOperaciones(startDate,endDate,idUsuario,idTipoOperacion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarLogOperacion(Long id) throws DelegateException {
        try {
            getFacade().borrarLogOperacion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    
    public void borrarLogOperaciones(Date startDate, Date endDate, String idAplicacion, String idTipoOperacion) throws DelegateException {
        try {
            getFacade().borrarLogOperaciones(startDate,endDate,idAplicacion,idTipoOperacion);
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

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */    
    private LogOperacionFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return LogOperacionFacadeUtil.getHome( ).create();
    }

    protected LogOperacionDelegate() throws DelegateException {       
    }                  
}
