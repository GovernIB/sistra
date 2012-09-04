package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.ParametrosSubsanacion;
import es.caib.zonaper.persistence.intf.NotificacionTelematicaFacade;
import es.caib.zonaper.persistence.util.NotificacionTelematicaFacadeUtil;

/**
 * Business delegate para operar con NotificacionTelematica.
 */
public class NotificacionTelematicaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarNotificacionTelematica(NotificacionTelematica tramite) throws DelegateException {
        try {
            return getFacade().grabarNotificacionTelematica(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public NotificacionTelematica obtenerNotificacionTelematicaAutenticada(Long idNotificacionTelematica) throws DelegateException {
        try {
            return getFacade().obtenerNotificacionTelematicaAutenticada(idNotificacionTelematica);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public NotificacionTelematica obtenerNotificacionTelematicaAuto(Long idNotificacionTelematica) throws DelegateException {
        try {
            return getFacade().obtenerNotificacionTelematicaAuto(idNotificacionTelematica);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public NotificacionTelematica obtenerNotificacionTelematicaAnonima(Long idNotificacionTelematica, String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerNotificacionTelematicaAnonima(idNotificacionTelematica,idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public NotificacionTelematica obtenerNotificacionTelematica(String numeroRegistro) throws DelegateException {
        try {
            return getFacade().obtenerNotificacionTelematica(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
        
    public List listarNotificacionTelematicasUsuario() throws DelegateException {
        try {
            return getFacade().listarNotificacionesTelematicasUsuario();
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    public int numeroNotificacionesUsuario()  throws DelegateException
	{
    	try 
        {
    		return getFacade().numeroNotificacionesUsuario();
        }
    	catch (Exception e) 
        {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
	}
    
    public int numeroNotificacionesNuevasUsuario()  throws DelegateException
	{
    	try 
        {
    		return getFacade().numeroNotificacionesNuevasUsuario();
        }
    	catch (Exception e) 
        {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
	}
    
    
    public boolean firmarAcuseReciboNotificacionAutenticada(Long codigo,String asientoAcuse,FirmaIntf firmaAcuse) throws DelegateException {
        try {
            return getFacade().firmarAcuseReciboNotificacionAutenticada(codigo, asientoAcuse, firmaAcuse);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    public boolean firmarAcuseReciboNotificacionAnonima(Long codigo,String idPersistencia,String asientoAcuse,FirmaIntf firmaAcuse) throws DelegateException {
        try {
            return getFacade().firmarAcuseReciboNotificacionAnonima(codigo, idPersistencia, asientoAcuse, firmaAcuse);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }                  

    public String iniciarTramiteSubsanacionNotificacionAnonima(Long codigoNotificacion,String idPersistencia) throws DelegateException {
        try {
            return getFacade().iniciarTramiteSubsanacionNotificacionAnonima(codigoNotificacion,idPersistencia);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public String iniciarTramiteSubsanacionNotificacionAutenticada(Long codigoNotificacion) throws DelegateException {
        try {
            return getFacade().iniciarTramiteSubsanacionNotificacionAutenticada(codigoNotificacion);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public ParametrosSubsanacion recuperarParametrosTramiteSubsanacion(String key) throws DelegateException {
        try {
            return getFacade().recuperarParametrosTramiteSubsanacion(key);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public AsientoRegistral generarAcuseReciboNotificacion( Long idNotificacion, boolean rechazada) throws DelegateException {
        try {
            return getFacade().generarAcuseReciboNotificacion(idNotificacion, rechazada);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public List listarNotificacionesTelematicasFueraPlazo() throws DelegateException {
        try {
            return getFacade().listarNotificacionesTelematicasFueraPlazo();            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public void rechazarNotificacion(Long codigo)throws DelegateException {
        try {
            getFacade().rechazarNotificacion(codigo);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private NotificacionTelematicaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return NotificacionTelematicaFacadeUtil.getHome( ).create();
    }

    protected NotificacionTelematicaDelegate() throws DelegateException {     
    }                  
}

