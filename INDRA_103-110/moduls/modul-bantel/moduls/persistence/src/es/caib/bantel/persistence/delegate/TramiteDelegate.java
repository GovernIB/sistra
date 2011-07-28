package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.intf.TramiteFacade;
import es.caib.bantel.persistence.util.TramiteFacadeUtil;

/**
 * Business delegate para operar con Tramite.
 */
public class TramiteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public String grabarTramite(Tramite tramite) throws DelegateException {
        try {
            return getFacade().grabarTramite(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Tramite obtenerTramite(String idTramite) throws DelegateException {
        try {
            return getFacade().obtenerTramite(idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Tramite obtenerTramitePorId(String idTramite) throws DelegateException {
        try {
            return getFacade().obtenerTramitePorId(idTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public void borrarTramite(String id) throws DelegateException {
        try {
            getFacade().borrarTramite(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarTramites() throws DelegateException {
        try {
            return getFacade().listarTramites();            
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
    
    public boolean puedoBorrarTramite(String id)throws DelegateException 
    {
  	   try
  	   {
  		   return getFacade().puedoBorrarTramite(id);
  	   }
  	   catch (Exception e) 
  	   {
             throw new DelegateException(e);
         }
     }
    
    public void borrarFicheroExportacion(String id)throws DelegateException 
    {
   	   try
   	   {
   		   getFacade().borrarFicheroExportacion(id);
   	   }
   	   catch (Exception e) 
   	   {
              throw new DelegateException(e);
          }
      }
   
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private TramiteFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return TramiteFacadeUtil.getHome( ).create();
    }

    protected TramiteDelegate() throws DelegateException {       
    }                  
}

