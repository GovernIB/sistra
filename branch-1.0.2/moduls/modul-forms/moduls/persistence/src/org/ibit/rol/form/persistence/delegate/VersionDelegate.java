package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Version;
import org.ibit.rol.form.persistence.intf.VersionFacade;
import org.ibit.rol.form.persistence.util.VersionFacadeUtil;

/**
 * Business delegate para manipular mascaras.
 */
public class VersionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
    public List listar() throws DelegateException {
        try {
            return getFacade().listar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }
    
    public Version obtener(Long id) throws DelegateException {
        try {
            return getFacade().obtener( id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }

    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private VersionFacade getFacade() throws NamingException,CreateException,RemoteException {
        return VersionFacadeUtil.getHome().create();
    }

    protected VersionDelegate() throws DelegateException {
       
    }

}
