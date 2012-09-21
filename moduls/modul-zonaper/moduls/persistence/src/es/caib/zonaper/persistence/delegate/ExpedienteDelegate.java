package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
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
	
	
	public boolean existeExpediente( long unidadAdministrativa, String identificadorExpediente) throws DelegateException
	{
		try
		{
			return getFacade().existeExpediente( unidadAdministrativa, identificadorExpediente);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Expediente obtenerExpedienteAuto( long unidadAdministrativa, String identificadorExpediente) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAuto( unidadAdministrativa, identificadorExpediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
	public Expediente obtenerExpediente( long unidadAdministrativa, String identificadorExpediente, String claveExpediente) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpediente( unidadAdministrativa, identificadorExpediente, claveExpediente );
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
	
	public Page busquedaPaginadaExpedientesGestor(
			FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) throws DelegateException
	{
		try
		{
			return getFacade().busquedaPaginadaExpedientesGestor(filtro, numPagina, longPagina);	
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}	
	
	public void modificarAvisosExpediente( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, ConfiguracionAvisosExpedientePAD configuracionAvisos) throws DelegateException
	{
		try
		{
			getFacade().modificarAvisosExpediente( unidadAdministrativa, identificadorExpediente, claveExpediente, configuracionAvisos );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public void borrarExpediente(long unidadAdministrativa,
			String identificadorExpediente)  throws DelegateException {
		try
		{
			getFacade().borrarExpediente( unidadAdministrativa, identificadorExpediente);
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
