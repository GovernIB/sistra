package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.Entidad;
import es.caib.bantel.persistence.intf.EntidadFacade;
import es.caib.bantel.persistence.util.EntidadFacadeUtil;

/**
 * Business delegate para operar con Procedimiento.
 */
public class EntidadDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public String grabarEntidad(Entidad entidad) throws DelegateException {
        try {
            return getFacade().grabarEntidad(entidad);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Entidad obtenerEntidad(String idproc) throws DelegateException {
        try {
            return getFacade().obtenerEntidad(idproc);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }        
        
    public void borrarEntidad(String id) throws DelegateException {
        try {
            getFacade().borrarEntidad(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarEntidades() throws DelegateException {
        try {
            return getFacade().listarEntidades();            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public boolean puedoBorrarEntidad(String id)throws DelegateException 
    {
  	   try
  	   {
  		   return getFacade().puedoBorrarEntidad(id);
  	   }
  	   catch (Exception e) 
  	   {
             throw new DelegateException(e);
         }
     }
       
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private EntidadFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return EntidadFacadeUtil.getHome( ).create();
    }

    protected EntidadDelegate() throws DelegateException {       
    }                  
}

