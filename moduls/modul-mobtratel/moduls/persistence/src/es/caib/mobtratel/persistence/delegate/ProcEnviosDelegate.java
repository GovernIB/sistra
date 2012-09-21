package es.caib.mobtratel.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.naming.NamingException;

import es.caib.mobtratel.persistence.intf.ProcEnviosFacade;
import es.caib.mobtratel.persistence.intf.ProcEnviosFacadeHome;
import es.caib.mobtratel.persistence.util.ProcEnviosFacadeUtil;

/**
 * Business delegate para operar con envios.
 */
public class ProcEnviosDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    
	public void enviar() throws DelegateException {
        try {
            getFacade().enviar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public void enviarInmediatos() throws DelegateException {
        try {
            getFacade().enviarInmediatos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }		

	public void verificar() throws DelegateException {
        try {
            getFacade().verificar();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }		
	
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle facadeHandle;

    private ProcEnviosFacade getFacade() throws RemoteException {
        return (ProcEnviosFacade) facadeHandle.getEJBObject();
    }

    protected ProcEnviosDelegate() throws DelegateException {
        try {
        	ProcEnviosFacadeHome home = ProcEnviosFacadeUtil.getHome( );
        	ProcEnviosFacade remote = home.create();
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
