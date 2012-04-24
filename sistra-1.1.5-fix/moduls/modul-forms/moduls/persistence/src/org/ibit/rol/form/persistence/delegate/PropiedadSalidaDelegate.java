package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.persistence.intf.PropiedadSalidaFacade;
import org.ibit.rol.form.persistence.util.PropiedadSalidaFacadeUtil;

/**
 * Created by IntelliJ IDEA.
 * User: mgonzalez
 * Date: 23-ene-2006
 * Time: 11:09:39
 * To change this template use File | Settings | File Templates.
 */
public class PropiedadSalidaDelegate implements StatelessDelegate {
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

     public Long grabarPropiedadSalida(PropiedadSalida propiedad, Long idSalida) throws DelegateException {
        try {
            return getFacade().grabarPropiedadSalida(propiedad,idSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPropiedadSalida(Long id) throws DelegateException {
        try {
            getFacade().borrarPropiedadSalida(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Set listarPropiedadesSalida(Long idSalida) throws DelegateException {
        try {
            return getFacade().listarPropiedadesSalida(idSalida);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public PropiedadSalida obtenerPropiedadSalida(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPropiedadSalida(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPlantilla(Long id_propiedadSalida, Long id_plantilla) throws DelegateException {
        try {
            getFacade().borrarPlantilla(id_propiedadSalida, id_plantilla);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }


     /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PropiedadSalidaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PropiedadSalidaFacadeUtil.getHome().create();
    }

    protected PropiedadSalidaDelegate() throws DelegateException {        
    }
}
