package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.persistence.intf.ExpedienteFacade;
import es.caib.zonaper.persistence.util.ExpedienteFacadeUtil;

public class ExpedienteDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public Expediente obtenerExpedienteAutenticado( Long id ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAutenticado( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpedienteAnonimo( Long id, String idPersistencia )throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAnonimo( id,idPersistencia );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpediente( long unidadAdministrativa, String identificadorExpediente) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpediente( unidadAdministrativa, identificadorExpediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Long grabarExpediente( Expediente expediente ) throws DelegateException
	{
		try
		{
			return getFacade().grabarExpediente( expediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
		
	
	public int obtenerNumeroExpedientesPendientesUsuario() throws DelegateException
	{
		try
		{
			return getFacade().obtenerNumeroExpedientesPendientesUsuario();
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private ExpedienteFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ExpedienteFacadeUtil.getHome( ).create();
    }
    
    protected ExpedienteDelegate() throws DelegateException {       
    }      
}
