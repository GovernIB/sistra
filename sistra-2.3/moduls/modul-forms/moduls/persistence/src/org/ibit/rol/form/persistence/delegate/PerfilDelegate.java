package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.PerfilUsuario;
import org.ibit.rol.form.persistence.intf.PerfilFacade;
import org.ibit.rol.form.persistence.util.PerfilFacadeUtil;

/**
 * Business delegate para manipular perfiles
 */
public class PerfilDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarPerfil(PerfilUsuario perfil) throws DelegateException {
        try {
            return getFacade().gravarPerfil(perfil);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarPerfiles() throws DelegateException {
        try {
            return getFacade().listarPerfiles();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public PerfilUsuario obtenerPerfil(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPerfil(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public PerfilUsuario obtenerPerfil(String codigo) throws DelegateException {
        try {
            return getFacade().obtenerPerfil(codigo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPerfil(Long id) throws DelegateException {
        try {
            getFacade().borrarPerfil(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PerfilFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PerfilFacadeUtil.getHome().create();
    }

    protected PerfilDelegate() throws DelegateException {
        
    }

}
