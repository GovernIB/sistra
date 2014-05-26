package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.ValidadorFirma;
import org.ibit.rol.form.persistence.intf.ValidadorFirmaFacade;
import org.ibit.rol.form.persistence.util.ValidadorFirmaFacadeUtil;

/**
 * Business delegate para manipular Validadores de Firma
 */

public class ValidadorFirmaDelegate implements StatelessDelegate{
    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarValidadorFirma(ValidadorFirma validador) throws DelegateException {
        try {
            return getFacade().gravarValidadorFirma(validador);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarValidadoresFirma() throws DelegateException {
        try {
            return getFacade().listarValidadoresFirma();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarIdsValidadoresFirmaFormulario(Long formulario_id) throws DelegateException {
        try {
            return getFacade().listarIdsValidadoresFirmaFormulario(formulario_id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List obtenerValidadoresFirma(Long[] ids) throws DelegateException {
           try {
               return getFacade().obtenerValidadoresFirma(ids);
           } catch (Exception e) {
               throw new DelegateException(e);
           }
       }


    public ValidadorFirma obtenerValidadorFirma(Long id) throws DelegateException {
        try {
            return getFacade().obtenerValidadorFirma(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarValidadorFirma(Long id) throws DelegateException {
        try {
            getFacade().borrarValidadorFirma(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
       /* ======================== REFERENCIA AL FACADE  ========== */
       /* ========================================================= */
       private ValidadorFirmaFacade getFacade() throws NamingException,CreateException,RemoteException {
           return ValidadorFirmaFacadeUtil.getHome().create();
       }

       protected ValidadorFirmaDelegate() throws DelegateException {           
       }

}
