package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo;
import es.caib.zonaper.modelInterfaz.EstadoPagosTramite;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.intf.PadBackOfficeFacade;
import es.caib.zonaper.persistence.util.PadBackOfficeFacadeUtil;

public class PadBackOfficeDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public String altaExpediente( ExpedientePAD expediente ) throws DelegateException
	{
		try
		{
			return getFacade().altaExpediente( expediente );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ExpedientePAD consultaExpediente( long unidadAdministrativa, String identificadorExpediente ) throws DelegateException	
	{
		try
		{
			return getFacade().consultaExpediente( unidadAdministrativa, identificadorExpediente );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public ExpedientePAD consultaExpediente( long unidadAdministrativa, String identificadorExpediente, String claveExpediente ) throws DelegateException	
	{
		try
		{
			return getFacade().consultaExpediente( unidadAdministrativa, identificadorExpediente, claveExpediente );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void altaEvento( long unidadAdministrativa, String identificadorExpediente, EventoExpedientePAD evento )  throws DelegateException	
	{
		try
		{
			getFacade().altaEvento( unidadAdministrativa, identificadorExpediente, evento );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public void altaEvento( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, EventoExpedientePAD evento )  throws DelegateException	
	{
		try
		{
			getFacade().altaEvento( unidadAdministrativa, identificadorExpediente, claveExpediente, evento );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	public boolean existeZonaPersonalUsuario( String nifUsuario ) throws DelegateException	
	{
		try
		{
			return getFacade().existeZonaPersonalUsuario( nifUsuario);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public String altaZonaPersonalUsuario( String nif, String nombre, String apellido1, String apellido2)  throws DelegateException	
	{
		try
		{
			return getFacade().altaZonaPersonalUsuario( nif, nombre, apellido1, apellido2) ;
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
		
	public DetalleAcuseRecibo obtenerDetalleAcuseRecibo(String numeroRegistro) throws DelegateException	
	{
		try
		{
			return getFacade().obtenerDetalleAcuseRecibo( numeroRegistro);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
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
	
	public void bajaExpediente( long unidadAdministrativa, String identificadorExpediente , String claveExpediente)   throws DelegateException	
	{
		try
		{
			getFacade().bajaExpediente( unidadAdministrativa, identificadorExpediente , claveExpediente);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}


	public EstadoPagosTramite obtenerEstadoPagosTramite(String identificadorPersistenciaTramite)  throws DelegateException	
	{
		try
		{
			return getFacade().obtenerEstadoPagosTramite(identificadorPersistenciaTramite);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}


	
	/*
	public EventoExpedientePAD obtenerEventoExpediente( long unidadAdministrativa, String idExpediente, String fechaEvento ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerEventoExpediente( unidadAdministrativa, idExpediente, fechaEvento );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public EventoExpedientePAD obtenerEventoExpediente( long unidadAdministrativa, String idExpediente, String claveExpediente, String fechaEvento ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerEventoExpediente( unidadAdministrativa, idExpediente, claveExpediente, fechaEvento );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	*/
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
	private Hashtable homeProperties;
	
	public void setProperties(Hashtable props){
		homeProperties = props;
	}
	
    private PadBackOfficeFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	if (homeProperties != null)
    		return PadBackOfficeFacadeUtil.getHome(homeProperties).create();
    	else
    		return PadBackOfficeFacadeUtil.getHome().create();
    }
}
