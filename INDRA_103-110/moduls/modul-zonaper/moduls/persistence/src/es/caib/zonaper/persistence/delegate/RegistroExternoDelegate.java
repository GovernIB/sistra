package es.caib.zonaper.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.RegistroExterno;
import es.caib.zonaper.persistence.intf.RegistroExternoFacade;
import es.caib.zonaper.persistence.util.RegistroExternoFacadeUtil;

/**
 * Business delegate para operar con RegistrosExternos.
 */
public class RegistroExternoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarRegistroExterno(RegistroExterno registro) throws DelegateException {
        try {
            return getFacade().grabarRegistroExterno(registro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public RegistroExterno obtenerRegistroExterno(Long id) throws DelegateException {
        try {
            return getFacade().obtenerRegistroExterno(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public RegistroExterno obtenerRegistroExternoPorNumero(String numeroRegistro) throws DelegateException {
        try {
            return getFacade().obtenerRegistroExternoPorNumero(numeroRegistro);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private RegistroExternoFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return RegistroExternoFacadeUtil.getHome( ).create();
    }

    protected RegistroExternoDelegate() throws DelegateException {       
    }                  
}

