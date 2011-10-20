package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Mascara;
import org.ibit.rol.form.persistence.intf.MascaraFacade;
import org.ibit.rol.form.persistence.util.MascaraFacadeUtil;

/**
 * Business delegate para manipular mascaras.
 */
public class MascaraDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public void gravarMascara(Mascara mascara) throws DelegateException {
        try {
            getFacade().gravarMascara(mascara);
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }

    public List listarMascaras() throws DelegateException {
        try {
            return getFacade().listarMascaras();
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }

    public Mascara obtenerMascara(Long id) throws DelegateException {
        try {
            return getFacade().obtenerMascara(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }
    
    public Mascara obtenerMascara(String nombre) throws DelegateException {
        try {
            return getFacade().obtenerMascara(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }

    public void borrarMascara(Long id) throws DelegateException {
        try {
            getFacade().borrarMascara(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private MascaraFacade getFacade() throws NamingException,CreateException,RemoteException {
        return MascaraFacadeUtil.getHome().create();
    }

    protected MascaraDelegate() throws DelegateException {
       
    }

}
