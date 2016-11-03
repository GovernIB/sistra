package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.persistence.intf.PatronFacade;
import org.ibit.rol.form.persistence.util.PatronFacadeUtil;

/**
 * Business delegate para manipular mascaras.
 */
public class PatronDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long gravarPatron(Patron patron) throws DelegateException {
        try {
            return getFacade().gravarPatron(patron);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarPatrones() throws DelegateException {
        try {
            return getFacade().listarPatrones();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Patron obtenerPatron(Long id) throws DelegateException {
        try {
            return getFacade().obtenerPatron(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    public Patron obtenerPatron(String nombre) throws DelegateException{
    	try {
            return getFacade().obtenerPatron(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    public void borrarPatron(Long id) throws DelegateException {
        try {
            getFacade().borrarPatron(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private PatronFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PatronFacadeUtil.getHome().create();
    }

    protected PatronDelegate() throws DelegateException {        
    }

}
