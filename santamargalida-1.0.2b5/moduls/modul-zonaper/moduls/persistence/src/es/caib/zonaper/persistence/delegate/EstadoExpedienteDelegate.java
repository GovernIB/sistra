package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.persistence.intf.EstadoExpedienteFacade;
import es.caib.zonaper.persistence.util.EstadoExpedienteFacadeUtil;

public class EstadoExpedienteDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public Page busquedaPaginadaExpedientes( int pagina, int longitudPagina )  throws DelegateException
	{
		try
		{
			return getFacade().busquedaPaginadaExpedientes(pagina, longitudPagina );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public EstadoExpediente obtenerExpedienteAnonimo(String idPersistencia ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerExpedienteAnonimo( idPersistencia );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private  EstadoExpedienteFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return EstadoExpedienteFacadeUtil.getHome( ).create();
    }
    
    protected EstadoExpedienteDelegate() throws DelegateException {
    }
}
