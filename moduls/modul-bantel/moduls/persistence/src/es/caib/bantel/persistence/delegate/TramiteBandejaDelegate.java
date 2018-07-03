package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.model.Page;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.persistence.intf.TramiteBandejaFacade;
import es.caib.bantel.persistence.util.TramiteBandejaFacadeUtil;
import es.caib.util.PropertiesOrdered;

/**
 * Business delegate para operar con TramiteBandeja.
 */
public class TramiteBandejaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarTramiteBandeja(TramiteBandeja tramite) throws DelegateException {
        try {
            return getFacade().grabarTramiteBandeja(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public TramiteBandeja obtenerTramiteBandeja(String numeroEntrada) throws DelegateException {
        try {
            return getFacade().obtenerTramiteBandeja(numeroEntrada);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramiteBandeja obtenerTramiteBandejaPorNumeroPreregistro(String numeroPreregistro)throws DelegateException {
        try {
            return getFacade().obtenerTramiteBandejaPorNumeroPreregistro(numeroPreregistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramiteBandeja obtenerTramiteBandejaPorNumeroRegistro(String numeroRegistro)throws DelegateException {
        try {
            return getFacade().obtenerTramiteBandejaPorNumeroRegistro(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
   
    public TramiteBandeja obtenerTramiteBandeja(Long idTramiteBandeja) throws DelegateException {
        try {
            return getFacade().obtenerTramiteBandeja(idTramiteBandeja);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public void borrarTramiteBandeja(Long id) throws DelegateException {
        try {
            getFacade().borrarTramiteBandeja(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public void borrarDocumentosTramite(Long id) throws DelegateException {
        try {
            getFacade().borrarDocumentosTramite(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Page busquedaPaginadaTramites( CriteriosBusquedaTramite criteriosBusqueda, int pagina, int longitudPagina ) throws DelegateException
    {
    	try 
    	{
    		return getFacade().busquedaPaginadaTramites( criteriosBusqueda, pagina, longitudPagina );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}
    
    }
    
    public List obtenerReferenciasEntradas(Long codigoProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta) throws DelegateException
    {
    	try 
    	{
    		return getFacade().obtenerReferenciasEntradas(codigoProcedimiento, identificadorTramite, procesada, desde, hasta );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    }
    
    
    public String[] obtenerNumerosEntradas(Long codigoProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta) throws DelegateException
    {
    	try 
    	{
    		return getFacade().obtenerNumerosEntradas(codigoProcedimiento, identificadorTramite, procesada, desde, hasta );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    }
       
    
    public void procesarEntrada(String numeroEntrada,String procesada) throws DelegateException
    {
    	try 
    	{
    		getFacade().procesarEntrada(numeroEntrada, procesada);
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
    public void procesarEntrada(String numeroEntrada,String procesada,String resultadoProcesamiento) throws DelegateException
    {
    	try 
    	{
    		getFacade().procesarEntrada(numeroEntrada, procesada, resultadoProcesamiento);
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
    
    public void procesarEntradas(CriteriosBusquedaTramite criteriosBusqueda,String procesada, String resultadoProcesamiento) throws DelegateException
    {
    	try 
    	{
    		getFacade().procesarEntradas(criteriosBusqueda,procesada,resultadoProcesamiento);
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
    public String generarNumeroEntrada()throws DelegateException
    {
    	try 
    	{
    		return getFacade().generarNumeroEntrada();
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
    public long obtenerTotalEntradasProcedimiento(String identificadorProcedimiento,String procesada,Date desde,Date hasta) throws DelegateException
    {
    	try 
    	{
    		return getFacade().obtenerTotalEntradasProcedimiento(identificadorProcedimiento,procesada, desde, hasta );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
    public String[] obtenerIdTramitesProcedimiento(String identificadorProcedimiento,String procesada,Date desde,Date hasta) throws DelegateException
    {
    	try 
    	{
    		return getFacade().obtenerIdTramitesProcedimiento(identificadorProcedimiento,procesada, desde, hasta );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    }
    
    public String[] exportarCSV(String numEntrada,PropertiesOrdered configuracionExportacion)throws DelegateException
    {
    	try 
    	{
    		return getFacade().exportarCSV(numEntrada,configuracionExportacion);
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}    
    } 
    
  
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private TramiteBandejaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return TramiteBandejaFacadeUtil.getHome( ).create();
    }

    protected TramiteBandejaDelegate() throws DelegateException {        
    }                  
}

