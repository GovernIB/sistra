package es.caib.mobtratel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.naming.NamingException;

import es.caib.mobtratel.model.Permiso;
import es.caib.mobtratel.persistence.intf.PermisoFacade;
import es.caib.mobtratel.persistence.intf.PermisoFacadeHome;
import es.caib.mobtratel.persistence.util.PermisoFacadeUtil;

/**
 * Business delegate para operar con Permisos.
 */
public class PermisoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    
    
    public Permiso obtenerPermiso(Long codigo) throws DelegateException {
        try {
            return getFacade().obtenerPermiso(codigo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Permiso obtenerPermiso(String usuario, String cuenta) throws DelegateException {
        try {
            return getFacade().obtenerPermiso(usuario,cuenta);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarPermisos(String usuario) throws DelegateException {
        try {
            return getFacade().listarPermisos(usuario);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    
    public List listarPermisos() throws DelegateException {
        try {
            return getFacade().listarPermisos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    
    public void borrarPermiso(Long id) throws DelegateException {
        try {
            getFacade().borrarPermiso(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    

    public Long grabarPermiso(Permiso permiso) throws DelegateException {
        try {
            return getFacade().grabarPermiso(permiso);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    




    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle facadeHandle;

    private PermisoFacade getFacade() throws RemoteException {
        return (PermisoFacade) facadeHandle.getEJBObject();
    }

    protected PermisoDelegate() throws DelegateException {
        try {
            PermisoFacadeHome home = PermisoFacadeUtil.getHome( );
            PermisoFacade remote = home.create();
            facadeHandle = remote.getHandle();
        } catch (NamingException e) {
        	e.printStackTrace();
            throw new DelegateException(e);
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }
}
