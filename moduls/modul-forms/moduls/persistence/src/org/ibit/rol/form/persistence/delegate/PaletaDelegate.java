package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Paleta;
import org.ibit.rol.form.persistence.intf.PaletaFacade;
import org.ibit.rol.form.persistence.util.PaletaFacadeUtil;

/**
 * Business delegate para operar con paletas.
 */
public class PaletaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarPaleta(Paleta paleta) throws DelegateException {
        try {
            return getFacade().gravarPaleta(paleta);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Paleta obtenerPaleta(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPaleta(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarPaletas() throws DelegateException {
        try {
            return getFacade().listarPaletas();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarPaleta(Long id) throws DelegateException {
        try {
            getFacade().borrarPaleta(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PaletaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PaletaFacadeUtil.getHome().create();
    }

    protected PaletaDelegate() throws DelegateException {       
    }

}
