package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.LogRegistroId;
import es.caib.zonaper.persistence.intf.LogRegistroFacade;
import es.caib.zonaper.persistence.util.LogRegistroFacadeUtil;

/**
 * Business delegate para operar con Log de Registro.
 */
public class LogRegistroDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	 public LogRegistro obtenerLogRegistro(LogRegistroId id) throws DelegateException {
        try {
            return getFacade().obtenerLogRegistro(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	 public void grabarLogRegistro(LogRegistro logRegistro) throws DelegateException {
        try {
           getFacade().grabarLogRegistro(logRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
	public void borrarLogRegistro(LogRegistroId id) throws DelegateException {
        try {
           getFacade().borrarLogRegistro(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
	public void borrarLogRegistro(LogRegistro logReg) throws DelegateException {
        try {
           getFacade().borrarLogRegistro(logReg);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public boolean tieneUsos(LogRegistro logReg) throws DelegateException {
	    try {
	        return getFacade().tieneUsos(logReg);
	    } catch (Exception e) {
	        throw new DelegateException(e);
	    }
	 }
	
	 public List listarLogRegistro() throws DelegateException {
		 try {
			 return getFacade().listarLogRegistro();
		 } catch (Exception e) {
			 throw new DelegateException(e);
		 } 
	 }
	    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private LogRegistroFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return LogRegistroFacadeUtil.getHome( ).create();
    }

    protected LogRegistroDelegate() throws DelegateException {       
    }                  
}

