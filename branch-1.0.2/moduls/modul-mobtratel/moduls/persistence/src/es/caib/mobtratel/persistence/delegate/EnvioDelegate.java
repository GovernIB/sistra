package es.caib.mobtratel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.naming.NamingException;

import es.caib.mobtratel.model.CriteriosBusquedaEnvio;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.model.Page;
import es.caib.mobtratel.persistence.intf.EnvioFacade;
import es.caib.mobtratel.persistence.intf.EnvioFacadeHome;
import es.caib.mobtratel.persistence.util.EnvioFacadeUtil;

/**
 * Business delegate para operar con envios.
 */
public class EnvioDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    
    public List listarPendientes() throws DelegateException {
        try {
            return getFacade().listarPendientes();
        } catch (RemoteException e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }
    
    public List listarInmediatosPendientes() throws DelegateException {
        try {
            return getFacade().listarPendientes();
        } catch (RemoteException e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public Long grabarEnvio(Envio envio) throws DelegateException {
        try {
            return getFacade().grabarEnvio(envio);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Envio obtenerEnvio(Long codigo) throws DelegateException {
        try {
            return getFacade().obtenerEnvio(codigo);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public boolean cancelarEnvio(Long idEnvio)throws DelegateException {
        try {
            return getFacade().cancelarEnvio(idEnvio);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Page busquedaPaginadaEnvios( CriteriosBusquedaEnvio criteriosBusqueda, int pagina, int longitudPagina ) throws DelegateException
    {
    	try 
    	{
    		return getFacade().busquedaPaginadaEnvios( criteriosBusqueda, pagina, longitudPagina );
    	} 
    	catch (Exception e) 
    	{
    		throw new DelegateException(e);
    	}
    
    }
    
    public boolean isEnviando(Long idEnvio) throws DelegateException {
        try {
            return getFacade().isEnviando(idEnvio);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle facadeHandle;

    private EnvioFacade getFacade() throws RemoteException {
        return (EnvioFacade) facadeHandle.getEJBObject();
    }

    protected EnvioDelegate() throws DelegateException {
        try {
            EnvioFacadeHome home = EnvioFacadeUtil.getHome( );
            EnvioFacade remote = home.create();
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
