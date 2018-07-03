package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.intf.ProcedimientoFacade;
import es.caib.bantel.persistence.util.ProcedimientoFacadeUtil;

/**
 * Business delegate para operar con Procedimiento.
 */
public class ProcedimientoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarProcedimiento(Procedimiento procedimiento) throws DelegateException {
        try {
            return getFacade().grabarProcedimiento(procedimiento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Procedimiento obtenerProcedimiento(Long id) throws DelegateException {
        try {
            return getFacade().obtenerProcedimiento(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }      

    public Procedimiento obtenerProcedimiento(String idproc) throws DelegateException {
        try {
            return getFacade().obtenerProcedimiento(idproc);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }        
        
    public void borrarProcedimiento(Long id) throws DelegateException {
        try {
            getFacade().borrarProcedimiento(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarProcedimientos() throws DelegateException {
        try {
            return getFacade().listarProcedimientos();            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarProcedimientos(String filtro) throws DelegateException {
        try {
            return getFacade().listarProcedimientos(filtro);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void  avisoRealizado(String id,Date fecha) throws DelegateException 
    {
 	   try
 	   {
 		   getFacade().avisoRealizado( id, fecha);
 	   }
 	   catch (Exception e) 
 	   {
            throw new DelegateException(e);
        }
    }
    
    public void errorConexion(String id,byte[] error) throws DelegateException 
    {
  	   try
  	   {
  		   getFacade().errorConexion( id, error);
  	   }
  	   catch (Exception e) 
  	   {
             throw new DelegateException(e);
         }
     }
    
    public boolean puedoBorrarProcedimiento(Long id)throws DelegateException 
    {
  	   try
  	   {
  		   return getFacade().puedoBorrarProcedimiento(id);
  	   }
  	   catch (Exception e) 
  	   {
             throw new DelegateException(e);
         }
     }
       
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private ProcedimientoFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return ProcedimientoFacadeUtil.getHome( ).create();
    }

    protected ProcedimientoDelegate() throws DelegateException {       
    }                  
}

