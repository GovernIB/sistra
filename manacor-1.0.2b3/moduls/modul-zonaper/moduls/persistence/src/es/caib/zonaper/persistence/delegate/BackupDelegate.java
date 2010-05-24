package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.persistence.intf.BackupFacade;
import es.caib.zonaper.persistence.util.BackupFacadeUtil;

public class BackupDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void procesaTramitesCaducados( Date fechaEjecucion, boolean borradoPreregistro ) throws DelegateException {
		try
		{
			getFacade().procesaTramitesCaducados( fechaEjecucion, borradoPreregistro );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private BackupFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return BackupFacadeUtil.getHome( ).create();
    }
    
    protected BackupDelegate()throws DelegateException 
    {       
    }

}
