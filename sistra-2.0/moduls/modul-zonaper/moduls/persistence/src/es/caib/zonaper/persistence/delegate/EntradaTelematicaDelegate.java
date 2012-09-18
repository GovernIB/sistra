package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.persistence.intf.EntradaTelematicaFacade;
import es.caib.zonaper.persistence.util.EntradaTelematicaFacadeUtil;

/**
 * Business delegate para operar con EntradaTelematica.
 */
public class EntradaTelematicaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarEntradaTelematica(EntradaTelematica tramite) throws DelegateException {
        try {
            return getFacade().grabarEntradaTelematica(tramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public EntradaTelematica obtenerEntradaTelematicaAutenticada(Long idEntradaTelematica) throws DelegateException {
        try {
            return getFacade().obtenerEntradaTelematicaAutenticada(idEntradaTelematica);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaTelematica obtenerEntradaTelematicaAnonima(Long idEntradaTelematica,String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEntradaTelematicaAnonima(idEntradaTelematica,idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaTelematica obtenerEntradaTelematica(Long id) throws DelegateException {
        try {
            return getFacade().obtenerEntradaTelematica(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaTelematica obtenerEntradaTelematica(String idPersistencia) throws DelegateException {
        try {
            return getFacade().obtenerEntradaTelematica(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public EntradaTelematica obtenerEntradaTelematicaPorNumero(String numeroRegistro) throws DelegateException {
        try {
            return getFacade().obtenerEntradaTelematicaPorNumero(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarEntradaTelematicasNifModelo(String nif, String modelo, Date fechaInicial, Date fechaFinal, String nivelAutenticacion) throws DelegateException {
        try {
            return getFacade().listarEntradaTelematicasNifModelo(nif,modelo,fechaInicial,fechaFinal,nivelAutenticacion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
        

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private EntradaTelematicaFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return EntradaTelematicaFacadeUtil.getHome( ).create();
    }

    protected EntradaTelematicaDelegate() throws DelegateException {       
    }                  
}

