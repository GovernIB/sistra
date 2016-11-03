package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.admin.FicheroCuaderno;
import es.caib.sistra.persistence.intf.FicheroCuadernoFacade;
import es.caib.sistra.persistence.util.FicheroCuadernoFacadeUtil;

public class FicheroCuadernoDelegate implements StatelessDelegate
{
	
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public Long grabarFicheroCuaderno( FicheroCuaderno obj, Long idCuadernoCarga ) throws DelegateException
	{
		try
		{
			return getFacade().grabarFicheroCuaderno( obj, idCuadernoCarga );
		}
		catch (Exception e) 
		{
		   throw new DelegateException(e);
		}
	}
	
	public void borrarFicheroCuaderno( Long id ) throws DelegateException
	{
		try
		{
			getFacade().borrarFicheroCuaderno( id );
		}
		catch (Exception e) 
		{
		   throw new DelegateException(e);
		}
	}
	
	public FicheroCuaderno obtenerFicheroCuaderno( Long id ) throws DelegateException
	{
		try
		{
			return getFacade().obtenerFicheroCuaderno( id );
		}
		catch (Exception e) 
		{
		   throw new DelegateException(e);
		}
	}
	
	public List listarFicherosCuaderno( Long idCuadernoCarga ) throws DelegateException
	{
		try
		{
			return getFacade().listarFicherosCuaderno( idCuadernoCarga );
			
		}
		catch (Exception e) 
		{
		   throw new DelegateException(e);
		}
	}
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
	
	private FicheroCuadernoFacade getFacade() throws NamingException,RemoteException,CreateException
	{
		return FicheroCuadernoFacadeUtil.getHome().create();
	}
	
	protected FicheroCuadernoDelegate() throws DelegateException
	{
		
	}
}
