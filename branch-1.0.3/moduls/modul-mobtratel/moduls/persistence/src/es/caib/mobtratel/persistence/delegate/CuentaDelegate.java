package es.caib.mobtratel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.naming.NamingException;

import es.caib.mobtratel.model.Cuenta;
import es.caib.mobtratel.persistence.intf.CuentaFacade;
import es.caib.mobtratel.persistence.intf.CuentaFacadeHome;
import es.caib.mobtratel.persistence.util.CuentaFacadeUtil;

/**
 * Business delegate para operar con Cuentas.
 */
public class CuentaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    
    
    public Cuenta obtenerCuenta(String codigo) throws DelegateException {
        try {
            return getFacade().obtenerCuenta(codigo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Cuenta obtenerCuentaDefectoEmail() throws DelegateException {
        try {
            return getFacade().obtenerCuentaDefectoEmail();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Cuenta obtenerCuentaDefectoSMS() throws DelegateException {
        try {
            return getFacade().obtenerCuentaDefectoSMS();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public List listarCuentas() throws DelegateException {
        try {
            return getFacade().listarCuentas();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public boolean puedoBorrarCuenta(String id) throws DelegateException {
        try {
            return getFacade().puedoBorrarCuenta(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public void borrarCuenta(String id) throws DelegateException {
        try {
            getFacade().borrarCuenta(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Cuenta findCuenta(String id) throws DelegateException
    {
    	try 
    	{
            return getFacade().findCuenta(id);
        } 
    	catch (Exception e) 
    	{
            throw new DelegateException(e);
        }
    }

    
    public String grabarCuenta(Cuenta cuenta) throws DelegateException {
        try {
            return getFacade().grabarCuenta(cuenta);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }



    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle facadeHandle;

    private CuentaFacade getFacade() throws RemoteException {
        return (CuentaFacade) facadeHandle.getEJBObject();
    }

    protected CuentaDelegate() throws DelegateException {
        try {
            CuentaFacadeHome home = CuentaFacadeUtil.getHome( );
            CuentaFacade remote = home.create();
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
