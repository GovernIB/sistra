package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.ParametrosSubsanacion;
import es.caib.zonaper.modelInterfaz.DetalleNotificacionesProcedimiento;
import es.caib.zonaper.persistence.intf.NotificacionTelematicaFacade;
import es.caib.zonaper.persistence.util.NotificacionTelematicaFacadeUtil;

/**
 * Business delegate para operar con NotificacionTelematica.
 */
public class NotificacionTelematicaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarNuevaNotificacionTelematica(NotificacionTelematica tramite) throws DelegateException {
        try {
            return getFacade().grabarNuevaNotificacionTelematica(tramite);
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

    public NotificacionTelematica obtenerNotificacionTelematica(String entidad, String numeroRegistro) throws DelegateException {
        try {
            return getFacade().obtenerNotificacionTelematica(entidad, numeroRegistro);
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
    
    public AsientoRegistral generarAcuseReciboNotificacion( Long idNotificacion, boolean rechazada, String tipoFirma) throws DelegateException {
        try {
            return getFacade().generarAcuseReciboNotificacion(idNotificacion, rechazada, tipoFirma);            
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
    
    public void rechazarNotificacion(Long codigo) throws DelegateException {
        try {
            getFacade().rechazarNotificacion(codigo);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public boolean firmarAcuseReciboNotificacionAutenticada(Long codigo,String asientoAcuse,FirmaIntf firmaAcuse, String firmaClave) throws DelegateException {
        try {
            return getFacade().firmarAcuseReciboNotificacionAutenticada(codigo, asientoAcuse, firmaAcuse, firmaClave);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    public boolean firmarAcuseReciboNotificacionAnonima(Long codigo,String idPersistencia,String asientoAcuse,FirmaIntf firmaAcuse, String firmaClave) throws DelegateException {
        try {
            return getFacade().firmarAcuseReciboNotificacionAnonima(codigo, idPersistencia, asientoAcuse, firmaAcuse, firmaClave);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }      
    
    public DetalleNotificacionesProcedimiento obtenerDetalleNotificacionesProcedimiento(String idProc, Date desde, Date hasta) throws DelegateException {
        try {
            return getFacade().obtenerDetalleNotificacionesProcedimiento(idProc, desde, hasta);            
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

