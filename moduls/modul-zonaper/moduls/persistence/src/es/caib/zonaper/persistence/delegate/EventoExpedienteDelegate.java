package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.persistence.intf.EventoExpedienteFacade;
import es.caib.zonaper.persistence.util.EventoExpedienteFacadeUtil;

public class EventoExpedienteDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public EventoExpediente obtenerEventoExpedienteAutenticado( Long id )  throws DelegateException
	{
		try
		{
			return getFacade().obtenerEventoExpedienteAutenticado( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public EventoExpediente obtenerEventoExpedienteAnonimo( Long id, String idPersistencia )  throws DelegateException
	{
		try
		{
			return getFacade().obtenerEventoExpedienteAnonimo( id, idPersistencia );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public EventoExpediente obtenerEventoExpediente( Long id ) throws DelegateException{
		try
		{
			return getFacade().obtenerEventoExpediente( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public EventoExpediente obtenerEventoExpediente( long unidadAdministrativa, String identificadorExpediente, java.sql.Timestamp fechaEvento ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerEventoExpediente( unidadAdministrativa, identificadorExpediente, fechaEvento );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Long grabarNuevoEventoExpediente( EventoExpediente evento ) throws DelegateException
	{
		try
		{
			return getFacade().grabarNuevoEventoExpediente( evento );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void marcarConsultadoEventoExpedienteAutenticado( Long id )throws DelegateException
	{
		try
		{
			getFacade().marcarConsultadoEventoExpedienteAutenticado( id );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void marcarConsultadoEventoExpedienteAnonimo( Long id , String idPersistencia)throws DelegateException
	{
		try
		{
			getFacade().marcarConsultadoEventoExpedienteAnonimo( id ,  idPersistencia);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private  EventoExpedienteFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return EventoExpedienteFacadeUtil.getHome( ).create();
    }
    
    protected EventoExpedienteDelegate() throws DelegateException {
    }
}
