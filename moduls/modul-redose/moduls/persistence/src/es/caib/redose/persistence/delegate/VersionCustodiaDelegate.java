package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.VersionCustodia;
import es.caib.redose.persistence.ejb.VersionCustodiaFacadeEJB;
import es.caib.redose.persistence.intf.VersionCustodiaFacade;
import es.caib.redose.persistence.util.VersionCustodiaFacadeUtil;

public class VersionCustodiaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public VersionCustodia obtenerVersion(String idVersion) throws DelegateException {
        try {
            return getFacade().obtenerVersion(idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public List listarVersionesCustodiaParaBorrar() throws DelegateException {
        try {
            return getFacade().listarVersionesCustodiaParaBorrar();            
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarVersion(String id) throws DelegateException {
        try {
            getFacade().borrarVersion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void marcarBorrarVersion(String id) throws DelegateException {
        try {
            getFacade().borrarVersion(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
    
    public String obtenerNumeroVersionCustodia(Long id) throws DelegateException{
    	try {
            return getFacade().obtenerNumeroVersionCustodia(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        } 
    }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private VersionCustodiaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return VersionCustodiaFacadeUtil.getHome( ).create();
    }

    protected VersionCustodiaDelegate() throws DelegateException {        
    }       

}
