package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.persistence.intf.AyudaFacade;
import org.ibit.rol.form.persistence.util.AyudaFacadeUtil;

/**
 * Business delegate para operar con componentes.
 */
public class AyudaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarAyuda(AyudaPantalla ayuda, Long pantalla_id, Long perfil_id) throws DelegateException {
        try {
            return getFacade().gravarAyuda(ayuda, pantalla_id, perfil_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public AyudaPantalla obtenerAyuda(Long id) throws DelegateException {
        try {
            return getFacade().obtenerAyuda(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public AyudaPantalla obtenerAyudaPantallaPerfil(Long pantalla_id, Long perfil_id) throws DelegateException {
        try {
            return getFacade().obtenerAyudaPantallaPerfil(pantalla_id, perfil_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarAyudasPantalla(Long pantalla_id) throws DelegateException {
        try {
            return getFacade().listarAyudasPantalla(pantalla_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarAyuda(Long id) throws DelegateException {
        try {
            getFacade().borrarAyuda(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private AyudaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return AyudaFacadeUtil.getHome().create();
    }

    protected AyudaDelegate() throws DelegateException {        
    }

}
