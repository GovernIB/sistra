package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.persistence.intf.DelegacionFacade;
import es.caib.zonaper.persistence.util.DelegacionFacadeUtil;

public class DelegacionDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public List obtenerDelegacionesUsuario() throws DelegateException
	{
		try
		{
			return getFacade().obtenerDelegacionesUsuario();
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	/**
     * Obtiene permisos de delegacion sobre la entidad del usuario autenticado
     * 
     * @param nif nif/cif entidad
     * @return permisos entidad
     * 
     */
	public String obtenerPermisosDelegacion(String nifEntidad)throws DelegateException	
	{
		try
		{
			return getFacade().obtenerPermisosDelegacion( nifEntidad);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Obtiene permisos de delegacion sobre la entidad del nif indincado
     * 
     * @param nif nif/cif entidad
     *  @param nif nif usuario
     * @return permisos entidad
     * 
     */
	public String obtenerPermisosDelegacion(String nifEntidad, String nifUsuario)throws DelegateException	
	{
		try
		{
			return getFacade().obtenerPermisosDelegacion( nifEntidad, nifUsuario);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Indica si la entidad tiene habilitada la delegacion
     * 
     * @param nif nif/cif entidad
     * @return boolean
     *     
     */
	public boolean habilitadaDelegacion(String nifEntidad) throws DelegateException
	{
		try{    		 
    		return getFacade().habilitadaDelegacion(nifEntidad);    	
    	}catch (Exception ex){
    		throw new DelegateException(ex);
    	}          
	}

	/**
     * Anula una delegacion
     * 
     * @param codigoDelegacion la delegación que queremos anular
     *     
     */
	public void anularDelegacion(long codigoDelegacion) throws DelegateException
	{
		try{    		 
    		getFacade().anularDelegacion(codigoDelegacion);    	
    	}catch (Exception ex){
    		throw new DelegateException(ex);
    	}          
	}
	
	
	/**
     * da de alta una delegacion
     * 
     * @param deleg la delegación que queremos dar de alta
     *     
     */
	public int altaDelegacion(ReferenciaRDS refRDS, FirmaIntf firma) throws DelegateException
	{
		try{    		 
    		return getFacade().altaDelegacion(refRDS, firma);    	
    	}catch (Exception ex){
    		throw new DelegateException(ex);
    	}          
	}
	
	/**
     * habilita la delegacion de las entidades
     * 
     * @param nif nif/cif entidad
     *     
     */
	public void habilitarDelegacion(String nifEntidad, String habilitar)throws DelegateException	
	{
		try
		{
			getFacade().habilitarDelegacion( nifEntidad, habilitar);
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	/**
     * Obtiene la delegacion con el codigo indicado
     * 
     * @param codigo codigo de la delegacion
     * @return delegacion 
     * 
     */
	public Delegacion obtenerDelegacion(long codigo)throws DelegateException	
	{
		try
		{
			return getFacade().obtenerDelegacion( codigo );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
	public List obtenerDelegacionesEntidad(String nifEntidad) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDelegacionesEntidad(nifEntidad);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
	
	public String obtenerRepresentanteEntidad(String nifEntidad) throws DelegateException
	{
		try
		{
			return getFacade().obtenerRepresentanteEntidad(nifEntidad);
		}
		catch (Exception e) 
		{
            throw new DelegateException(e);
        }
	}
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private DelegacionFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return DelegacionFacadeUtil.getHome( ).create();
    }
    
    protected DelegacionDelegate() throws DelegateException {       
    }      
}
