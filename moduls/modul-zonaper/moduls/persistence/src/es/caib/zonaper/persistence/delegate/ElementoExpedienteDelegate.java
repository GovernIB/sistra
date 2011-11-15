package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.persistence.intf.ElementoExpedienteFacade;
import es.caib.zonaper.persistence.util.ElementoExpedienteFacadeUtil;

public class ElementoExpedienteDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public ElementoExpedienteItf obtenerDetalleElementoExpediente(Long id) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleElementoExpediente(id);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpediente obtenerElementoExpedienteAutenticado( String tipoElemento,Long codigoElemento ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerElementoExpedienteAutenticado( tipoElemento, codigoElemento );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpediente obtenerElementoExpedienteAnonimo( String tipoElemento,Long codigoElemento, String idPersistencia ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerElementoExpedienteAnonimo( tipoElemento, codigoElemento, idPersistencia );
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpediente obtenerElementoExpediente( String tipoElemento,Long codigoElemento)  throws DelegateException
	{
		try
		{
			return getFacade().obtenerElementoExpediente(tipoElemento,codigoElemento);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(Long id) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleElementoExpedienteAutenticado(id);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(Long id, String idPersistencia) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleElementoExpedienteAnonimo(id,  idPersistencia);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAutenticado(String tipo, Long codigo)throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleElementoExpedienteAutenticado(tipo, codigo);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public ElementoExpedienteItf obtenerDetalleElementoExpedienteAnonimo(String tipo, Long codigo, String idPersistencia)throws DelegateException
	{
		try
		{
			return getFacade().obtenerDetalleElementoExpedienteAnonimo(tipo, codigo, idPersistencia);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public Map obtenerIdsExpedienteElementos( List codigosElementos )throws DelegateException
	{
		try
		{
			return getFacade().obtenerIdsExpedienteElementos(codigosElementos);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private  ElementoExpedienteFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return ElementoExpedienteFacadeUtil.getHome( ).create();
    }
    
    protected ElementoExpedienteDelegate() throws DelegateException {
    }
}
