package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.persistence.intf.ProcesoBackupFacade;
import es.caib.zonaper.persistence.util.ProcesoBackupFacadeUtil;

public class ProcesoBackupDelegate implements StatelessDelegate
{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public void backupTramitePersistente( TramitePersistente tramitePersistente ) throws DelegateException {
		try
		{
			getFacade().backupTramitePersistente(tramitePersistente );
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	public void backupTramitePreregistro(EntradaPreregistro entradaPrerregistro ) throws DelegateException {
		try
		{
			getFacade().backupTramitePreregistro(entradaPrerregistro);
		}
		catch (Exception e) {
            throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
     private ProcesoBackupFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ProcesoBackupFacadeUtil.getHome( ).create();
    }
    
    protected ProcesoBackupDelegate()throws DelegateException 
    {       
    }

}
