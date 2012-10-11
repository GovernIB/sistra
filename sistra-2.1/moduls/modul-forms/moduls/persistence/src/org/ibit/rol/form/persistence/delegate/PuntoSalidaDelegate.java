package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.PuntoSalida;
import org.ibit.rol.form.persistence.intf.PuntoSalidaFacade;
import org.ibit.rol.form.persistence.util.PuntoSalidaFacadeUtil;

/**
 * Business delegate para manipular Punto Salida
 */
public class PuntoSalidaDelegate implements StatelessDelegate {
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarPuntoSalida(PuntoSalida puntoSalida) throws DelegateException {
        try {
            return getFacade().gravarPuntoSalida(puntoSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarPuntosSalida() throws DelegateException {
        try {
            return getFacade().listarPuntosSalida();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

     public List listarPuntosSalidaFormulario(Long idFormulario) throws DelegateException {
        try {
            return getFacade().listarPuntosSalidaFormulario(idFormulario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public PuntoSalida obtenerPuntoSalida(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPuntoSalida(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPuntoSalida(Long id) throws DelegateException {
        try {
            getFacade().borrarPuntoSalida(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

     /* ========================================================= */
       /* ======================== REFERENCIA AL FACADE  ========== */
       /* ========================================================= */
       private PuntoSalidaFacade getFacade() throws NamingException,CreateException,RemoteException {
           return PuntoSalidaFacadeUtil.getHome().create();
       }

       protected PuntoSalidaDelegate() throws DelegateException {           
       }

}
