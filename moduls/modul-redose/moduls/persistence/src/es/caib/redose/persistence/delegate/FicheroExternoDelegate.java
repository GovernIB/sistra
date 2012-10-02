package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import es.caib.redose.model.FicheroExterno;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.persistence.intf.FicheroExternoFacade;
import es.caib.redose.persistence.util.FicheroExternoFacadeUtil;

public class FicheroExternoDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	
	 public List obtenerListaFicherosExterno(Long id) throws DelegateException {
	        try {
	            return getFacade().obtenerListaFicherosExterno(id);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }

	    public void grabarFicheroExterno(FicheroExterno ficheroExterno) throws DelegateException {
	        try {
	            getFacade().grabarFicheroExterno(ficheroExterno);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public List obtenerListaFicherosExternoBorrar() throws DelegateException {
	        try {
	            return getFacade().obtenerListaFicherosExternoBorrar();
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public void marcarBorrarFicheroExterno(String referenciaExterna) throws DelegateException {
	        try {
	             getFacade().marcarBorrarFicheroExterno(referenciaExterna);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	
	    
	    public void eliminarFicheroExterno(String referenciaExterna) throws DelegateException {
	        try {
	             getFacade().eliminarFicheroExterno(referenciaExterna);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public FicheroExterno obtenerFicheroExterno(String referencia) throws DelegateException {
	        try {
	            return getFacade().obtenerFicheroExterno(referencia);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public Ubicacion obtenerUbicacionFicheroExterno(String referencia) throws DelegateException {
	        try {
	            return getFacade().obtenerUbicacionFicheroExterno(referencia);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private FicheroExternoFacade getFacade() throws NamingException,CreateException,RemoteException {
        return FicheroExternoFacadeUtil.getHome( ).create();
    }

    protected FicheroExternoDelegate() throws DelegateException {        
    }       

}
