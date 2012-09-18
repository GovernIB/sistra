package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.persistence.intf.CuadernoCargaFacade;
import es.caib.sistra.persistence.util.CuadernoCargaFacadeUtil;

public class CuadernoCargaDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public Long grabarCuadernoCarga( CuadernoCarga obj ) throws DelegateException
	{
		 try 
		 {
			 return getFacade().grabarCuadernoCarga( obj );
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public CuadernoCarga obtenerCuadernoCarga( Long id ) throws DelegateException
	{
		try 
		{
			 return getFacade().obtenerCuadernoCarga( id );
		}
		catch (Exception e) 
		{
		   throw new DelegateException(e);
		}
	}
	
	public void borrarCuadernoCarga( Long id ) throws DelegateException
	{
		try 
		{
			 getFacade().borrarCuadernoCarga( id );
		}
		catch (Exception e) 
		{
	       throw new DelegateException(e);
	    }
	}
	
	public List listarCuadernosCarga() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosCarga();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public List listarCuadernosPendientesDesarrollador() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosPendientesDesarrollador();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public List listarCuadernosPendientesAuditoria() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosPendientesAuditoria();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public List listarCuadernosFinalizados() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosFinalizados();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public List listarCuadernosAuditados() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosAuditados();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	public List listarCuadernosPendientes() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosPendientes();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	
	/*
	
	
	public List listarCuadernosGestionados() throws DelegateException
	{
		try 
		 {
			 return getFacade().listarCuadernosGestionados();
		 }
		 catch (Exception e) 
		 {
	        throw new DelegateException(e);
	     }
	}
	*/
	
	/* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
	private CuadernoCargaFacade getFacade() throws NamingException,RemoteException,CreateException {
		return CuadernoCargaFacadeUtil.getHome().create();
	}
	
	protected CuadernoCargaDelegate() throws DelegateException
	{
		
	}
}
