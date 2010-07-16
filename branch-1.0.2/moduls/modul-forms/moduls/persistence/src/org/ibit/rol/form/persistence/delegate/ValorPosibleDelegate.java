package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.intf.ValorPosibleFacade;
import org.ibit.rol.form.persistence.util.ValorPosibleFacadeUtil;

/**
 * Business delegate para operar con valors posibles de campos.
 */
public class ValorPosibleDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarValorPosible(ValorPosible valorPosible, Long campo_id) throws DelegateException {
        try {
            return getFacade().gravarValorPosible(valorPosible, campo_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarValoresPosiblesCampo(Long campo_id) throws DelegateException {
        try {
            return getFacade().listarValoresPosiblesCampo(campo_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public ValorPosible obtenerValorPosible(Long vp_id) throws DelegateException {
        try {
            return getFacade().obtenerValorPosible(vp_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Archivo obtenerImagenValorPosible(Long vp_id, String lang) throws DelegateException {
        try {
            return getFacade().obtenerImagenValorPosible(vp_id, lang);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void cambiarOrden(Long vp1_id, Long vp2_id) throws DelegateException {
        try {
            getFacade().cambiarOrden(vp1_id, vp2_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarValorPosible(Long id) throws DelegateException {
        try {
            getFacade().borrarValorPosible(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ValorPosibleFacade getFacade() throws NamingException,CreateException,RemoteException {
        return ValorPosibleFacadeUtil.getHome().create();
    }

    protected ValorPosibleDelegate() throws DelegateException {        
    }

}
