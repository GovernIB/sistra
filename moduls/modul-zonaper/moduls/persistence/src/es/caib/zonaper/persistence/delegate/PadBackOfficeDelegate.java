package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo;
import es.caib.zonaper.modelInterfaz.EstadoPagosTramite;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaElementosExpedientePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.intf.PadBackOfficeFacade;
import es.caib.zonaper.persistence.util.ConfigurationUtil;
import es.caib.zonaper.persistence.util.PadBackOfficeFacadeUtil;

import java.util.Date;

public class PadBackOfficeDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== M�TODOS DE NEGOCIO ============= */
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

	public DetalleAcuseRecibo obtenerDetalleAcuseRecibo(String entidad, String numeroRegistro) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleAcuseRecibo( entidad, numeroRegistro);
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

	public List obtenerPersistentes(String nif, Date fechaDesde, Date fechaHasta )  throws DelegateException
	{
		try
		{
			return getFacade().obtenerPersistentes(nif, fechaDesde, fechaHasta);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	public boolean existeExpediente(long unidadAdministrativa,
			String identificadorExpediente) throws DelegateException {
		try
		{
			return getFacade().existeExpediente(unidadAdministrativa, identificadorExpediente);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}


	public List obtenerElementosExpediente(FiltroBusquedaElementosExpedientePAD filtro, Integer pagina, Integer tamPagina) throws DelegateException {
		try
		{
			return getFacade().obtenerElementosExpediente(filtro, pagina, tamPagina);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	public Long obtenerTotalElementosExpediente(FiltroBusquedaElementosExpedientePAD filtro) throws DelegateException {
		try
		{
			return getFacade().obtenerTotalElementosExpediente(filtro);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	public String obtenerUrlAccesoAnonimo(String clave) throws DelegateException {
		try
		{
			return getFacade().obtenerUrlAccesoAnonimo(clave);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}

	 public String obtenerUrlTramitePersistente(TramitePersistentePAD tp)  throws DelegateException {
		try
		{
			return getFacade().obtenerUrlTramitePersistente(tp);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
		}

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
