package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.persistence.intf.BandejaFirmaFacade;
import es.caib.zonaper.persistence.util.BandejaFirmaFacadeUtil;

public class BandejaFirmaDelegate implements StatelessDelegate
{
	/* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	public List obtenerDocumentosPendientesFirma(String nifEntidad) throws DelegateException
	{
		try
		{
			return getFacade().obtenerDocumentosPendientesFirma(nifEntidad);
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
	public void firmarDocumentoBandejaFirma( Long codigo, FirmaIntf firma) throws DelegateException	
	{
		try
		{
			getFacade().firmarDocumentoBandejaFirma(  codigo,firma );
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
	public void rechazarDocumentoBandejaFirma( Long codigo ) throws DelegateException	
	{
		try
		{
			getFacade().rechazarDocumentoBandejaFirma( codigo );
		}
		catch( Exception e )
		{
			throw new DelegateException( e );
		}
	}
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private BandejaFirmaFacade getFacade() throws NamingException,CreateException,RemoteException
    {
    	return BandejaFirmaFacadeUtil.getHome( ).create();
    }
    
    protected BandejaFirmaDelegate() throws DelegateException {       
    }      
}
