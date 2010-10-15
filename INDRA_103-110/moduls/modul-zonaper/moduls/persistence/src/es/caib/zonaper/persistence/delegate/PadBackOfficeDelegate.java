package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
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
