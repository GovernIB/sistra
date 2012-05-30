package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.intf.FicheroExportacionFacade;
import es.caib.bantel.persistence.util.FicheroExportacionFacadeUtil;

/**
 * Business delegate para operar con Procedimiento.
 */
public class FicheroExportacionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	 public FicheroExportacion obtenerFicheroExportacion(String id) throws DelegateException {
        try {
            return getFacade().obtenerFicheroExportacion(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	 public String guardarFicheroExportacion(FicheroExportacion obj) throws DelegateException {
        try {
            return getFacade().guardarFicheroExportacion(obj);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	 
	 public void borrarArchivoFicheroExportacion(String id)  throws DelegateException {
	        try {
	            getFacade().borrarArchivoFicheroExportacion(id);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	 
	 public void borrarFicheroExportacion(String id)  throws DelegateException {
        try {
            getFacade().borrarFicheroExportacion(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
	 public List listarFicherosExportacion() throws DelegateException {
        try {
            return getFacade().listarFicherosExportacion();            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
	 
	 public FicheroExportacion findFicheroExportacion(String id) throws DelegateException {
	        try {
	            return getFacade().findFicheroExportacion(id);            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }    
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private FicheroExportacionFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return FicheroExportacionFacadeUtil.getHome( ).create();
    }

    protected FicheroExportacionDelegate() throws DelegateException {       
    }                  
}

