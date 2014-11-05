package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.persistence.intf.GestorBandejaFacade;
import es.caib.bantel.persistence.util.GestorBandejaFacadeUtil;

public class GestorBandejaDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
    public String grabarGestorBandeja(GestorBandeja obj, String[] identificadoresTramites ) throws DelegateException {
        try {
            return getFacade().grabarGestorBandeja(obj, identificadoresTramites );
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public GestorBandeja obtenerGestorBandeja(String idGestorBandeja) throws DelegateException {
        try {
            return getFacade().obtenerGestorBandeja(idGestorBandeja);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public GestorBandeja findGestorBandeja(String id) throws DelegateException
    {
    	try 
    	{
            return getFacade().findGestorBandeja(id);
        } 
    	catch (Exception e) 
    	{
            throw new DelegateException(e);
        }
    }
        
    public void borrarGestorBandeja(String id) throws DelegateException {
        try {
            getFacade().borrarGestorBandeja(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
   public List listarGestoresBandeja() throws DelegateException 
   {
	   try
	   {
		   return getFacade().listarGestoresBandeja();
	   }
	   catch (Exception e) 
	   {
           throw new DelegateException(e);
       }
   }
   
   
   public List listarGestoresBandeja(String filtro) throws DelegateException 
   {
	   try
	   {
		   return getFacade().listarGestoresBandeja(filtro);
	   }
	   catch (Exception e) 
	   {
           throw new DelegateException(e);
       }
   }
   
        
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private GestorBandejaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return GestorBandejaFacadeUtil.getHome( ).create();
    }

    protected GestorBandejaDelegate() throws DelegateException {      
    }
}
