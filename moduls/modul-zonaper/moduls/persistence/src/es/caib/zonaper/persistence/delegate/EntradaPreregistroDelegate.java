package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistroBackup;
import es.caib.zonaper.persistence.intf.EntradaPreregistroFacade;
import es.caib.zonaper.persistence.util.EntradaPreregistroFacadeUtil;

/**
 * Business delegate para operar con EntradaPreregistro.
 */
public class EntradaPreregistroDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarEntradaPreregistro(EntradaPreregistro tramite) throws DelegateException {
        try {
            return getFacade().grabarEntradaPreregistro(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public EntradaPreregistro obtenerEntradaPreregistroReg(Long idEntradaPreregistro) throws DelegateException {
        try {
            return getFacade().obtenerEntradaPreregistroReg(idEntradaPreregistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaPreregistro obtenerEntradaPreregistroAutenticada(Long idEntradaPreregistro) throws DelegateException {
        try {
            return getFacade().obtenerEntradaPreregistroAutenticada(idEntradaPreregistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaPreregistro obtenerEntradaPreregistroAnonima(Long idEntradaPreregistro,String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEntradaPreregistroAnonima(idEntradaPreregistro,idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaPreregistro obtenerEntradaPreregistro(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEntradaPreregistro(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaPreregistro obtenerEntradaPreregistro(Long id)throws DelegateException {
        try {
            return getFacade().obtenerEntradaPreregistro(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaPreregistro obtenerEntradaPreregistroPorNumero( String numeroPreregistro ) throws DelegateException {
        try {
        	return getFacade().obtenerEntradaPreregistroPorNumero( numeroPreregistro );
        } catch (Exception e) {
            throw new DelegateException(e);
        }
        
    }
   
    /*
    public List listarEntradaPreregistrosUsuario() throws DelegateException {
        try {
            return getFacade().listarEntradaPreregistrosUsuario();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    */
    
    /*
    public List listarEntradaPreregistrosUsuarioNoConfirmados(String usua) throws DelegateException
    {
    	try 
    	{
    		return getFacade().listarEntradaPreregistrosUsuarioNoConfirmados( usua );
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    */
    

    public List listarEntradaPreregistrosNifModelo(String nif, String modelo, Date fechaInicial, Date fechaFinal, String nivelAutenticacion) throws DelegateException
    {
    	try 
    	{
    		return getFacade().listarEntradaPreregistrosNifModelo( nif, modelo, fechaInicial, fechaFinal, nivelAutenticacion );
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    

    public void backupEntradaPreregistro( EntradaPreregistro preregistro ) throws DelegateException {
    	try {
            getFacade().backupEntradaPreregistro( preregistro );            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void borrarEntradaPreregistro(Long id) throws DelegateException {
        try {
            getFacade().borrarEntradaPreregistro(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    public List listarEntradaPreregistroBackup( Date fecha ) throws DelegateException {
    	try {
           return getFacade().listarEntradaPreregistroBackup( fecha );            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    public void borrarEntradaPreregistroBackup( EntradaPreregistroBackup entradaPreregistroBackup ) throws DelegateException {
    	try {
            getFacade().borrarEntradaPreregistroBackup( entradaPreregistroBackup );            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarEntradaPreregistrosCaducados( Date fecha ) throws DelegateException 
    {
    	try
    	{
    		return getFacade().listarEntradaPreregistrosCaducados( fecha );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarEntradaPreregistrosNoConfirmados( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) throws DelegateException 
    {
    	try
    	{
    		return getFacade().listarEntradaPreregistrosNoConfirmados( fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarEntradaPreregistrosConfirmados( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) throws DelegateException 
    {
    	try
    	{
    		return getFacade().listarEntradaPreregistrosConfirmados( fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarEntradaPreregistros( Date fechaInicial, Date fechaFinal, String modelo, String caducidad, String tipo, String nivel ) throws DelegateException 
    {
    	try
    	{
    		return getFacade().listarEntradaPreregistros( fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
  private EntradaPreregistroFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return EntradaPreregistroFacadeUtil.getHome( ).create();
    }

    protected EntradaPreregistroDelegate() throws DelegateException {       
    }                  
}

