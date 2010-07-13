package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.persistence.intf.TramitePersistenteFacade;
import es.caib.zonaper.persistence.util.TramitePersistenteFacadeUtil;

/**
 * Business delegate para operar con TramitePersistente.
 */
public class TramitePersistenteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public String grabarTramitePersistente(TramitePersistente tramite) throws DelegateException {
        try {
            return getFacade().grabarTramitePersistente(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramitePersistente obtenerTramitePersistenteBackup(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerTramitePersistenteBackup(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramitePersistente obtenerTramitePersistente(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerTramitePersistente(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
        
    public List listarTramitePersistentesUsuario() throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesUsuario();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public int numeroTramitesPersistentesUsuario() throws DelegateException {
        try {
            return getFacade().numeroTramitesPersistentesUsuario();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public int numeroTramitesPersistentesAnonimos(Date fechaInicial, Date fechaFinal, String modelo) throws DelegateException {
        try {
            return getFacade().numeroTramitesPersistentesAnonimos(fechaInicial,fechaFinal,modelo);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public List listarTramitesPersistentes(Date fechaInicial, Date fechaFinal, String modelo, String nivelAutenticacion) throws DelegateException {
        try {
            return getFacade().listarTramitesPersistentes(fechaInicial,fechaFinal,modelo,nivelAutenticacion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public List listarTramitesPersistentesBackup(Date fechaInicial, Date fechaFinal, String modelo, String nivelAutenticacion) throws DelegateException {
        try {
            return getFacade().listarTramitesPersistentesBackup(fechaInicial,fechaFinal,modelo,nivelAutenticacion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public List listarTramitePersistentesUsuario(String tramite,int version) throws DelegateException {
        try {
            return getFacade().listarTramitePersistentesUsuario(tramite,version);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        
    public void borrarTramitePersistente(String id) throws DelegateException {
        try {
            getFacade().borrarTramitePersistente(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void borrarDocumentosTramitePersistente(String id) throws DelegateException {
        try {
            getFacade().borrarDocumentosTramitePersistente(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void backupTramitePersistente( TramitePersistente tramitePersistente ) throws DelegateException
    {
    	try
    	{
    		getFacade().backupTramitePersistente( tramitePersistente );
    	}
    	catch (Exception e) 
	    {
	        throw new DelegateException(e);
	    }
    }
    
    public List listarTramitePersistentesCaducados( Date fecha ) throws DelegateException
    {
    	try
    	{
    		return getFacade().listarTramitePersistentesCaducados( fecha );
    	}
    	catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private TramitePersistenteFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return TramitePersistenteFacadeUtil.getHome( ).create();
    }

    protected TramitePersistenteDelegate() throws DelegateException {
        
    }                  
}

