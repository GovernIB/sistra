package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.persistence.intf.ProcesoRechazarNotificacionFacade;
import es.caib.zonaper.persistence.util.ProcesoRechazarNotificacionFacadeUtil;

public class ProcesoRechazarNotificacionDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void rechazarNotificacion( Long idNotificacion )  throws DelegateException {
		try
		{
			getFacade().rechazarNotificacion( idNotificacion ) ;
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private ProcesoRechazarNotificacionFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ProcesoRechazarNotificacionFacadeUtil.getHome( ).create();
    }
    
    protected ProcesoRechazarNotificacionDelegate()throws DelegateException 
    {       
    }

}
