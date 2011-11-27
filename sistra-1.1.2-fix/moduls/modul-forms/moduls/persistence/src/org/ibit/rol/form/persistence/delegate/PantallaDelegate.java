package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.persistence.intf.PantallaFacade;
import org.ibit.rol.form.persistence.util.PantallaFacadeUtil;

/**
 * Business delegate para operar con pantallas.
 */
public class PantallaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarPantalla(Pantalla pantalla, Long formulario_id) throws DelegateException {
        try {
            return getFacade().gravarPantalla(pantalla, formulario_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Pantalla obtenerPantalla(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPantalla(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarPantallasFormulario(Long formulario_id) throws DelegateException {
        try {
            return getFacade().listarPantallasFormulario(formulario_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void cambiarOrden(Long pantalla1_id, Long pantalla2_id) throws DelegateException {
        try {
            getFacade().cambiarOrden(pantalla1_id, pantalla2_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPantalla(Long id) throws DelegateException {
        try {
            getFacade().borrarPantalla(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Pantalla obtenerPantalla(String modeloFormulario,int versionFormulario,String nombrePantalla) throws DelegateException {
        try {
            return getFacade().obtenerPantalla(modeloFormulario,versionFormulario,nombrePantalla);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private PantallaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PantallaFacadeUtil.getHome().create();
    }

    protected PantallaDelegate() throws DelegateException {       
    }

}
