package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Componente;
import org.ibit.rol.form.persistence.intf.ComponenteFacade;
import org.ibit.rol.form.persistence.util.ComponenteFacadeUtil;

/**
 * Business delegate para operar con componentes.
 */
public class ComponenteDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public void gravarComponentePantalla(Componente componente, Long pantalla_id) throws DelegateException {
        try {
            getFacade().gravarComponentePantalla(componente, pantalla_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void gravarComponentePaleta(Componente componente, Long paleta_id) throws DelegateException {
        try {
            getFacade().gravarComponentePaleta(componente, paleta_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarComponentesPantalla(Long pantalla_id) throws DelegateException {
        try {
            return getFacade().listarComponentesPantalla(pantalla_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarComponentesPaleta(Long paleta_id) throws DelegateException {
        try {
            return getFacade().listarComponentesPaleta(paleta_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Componente obtenerComponente(Long componente_id) throws DelegateException {
        try {
            return getFacade().obtenerComponente(componente_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Long copiarComponente(Long componente_id, Long pantalla_id) throws DelegateException {
        try {
            return getFacade().copiarComponente(componente_id, pantalla_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void cambiarOrden(Long comp1_id, Long comp2_id) throws DelegateException {
        try {
            getFacade().cambiarOrden(comp1_id, comp2_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarComponente(Long id) throws DelegateException {
        try {
            getFacade().borrarComponente(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarValidacionesCampo(Long campo_id) throws DelegateException {
        try {
            getFacade().borrarValidacionesCampo(campo_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ComponenteFacade getFacade() throws NamingException,CreateException,RemoteException {
        return ComponenteFacadeUtil.getHome().create();
    }

    protected ComponenteDelegate() throws DelegateException {        
    }

}
