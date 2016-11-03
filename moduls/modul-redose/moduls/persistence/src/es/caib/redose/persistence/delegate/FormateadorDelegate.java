package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import es.caib.redose.model.Formateador;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.ejb.HibernateEJB;
import es.caib.redose.persistence.intf.FormateadorFacade;
import es.caib.redose.persistence.intf.PlantillaFacade;
import es.caib.redose.persistence.util.FormateadorFacadeUtil;
import es.caib.redose.persistence.util.PlantillaFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class FormateadorDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

		public Formateador obtenerFormateador(Long id) throws DelegateException  {
		   	try {
		        return getFacade().obtenerFormateador(id);            
		    } catch (Exception e) {
		        throw new DelegateException(e);
		    }
		}
		
		public Formateador obtenerFormateador(String clase) throws DelegateException  {
		   	try {
		        return getFacade().obtenerFormateador(clase);            
		    } catch (Exception e) {
		        throw new DelegateException(e);
		    }
		}
	
	    public List listar() throws DelegateException  {
	    	try {
	            return getFacade().listar();            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
		 
	    public Long grabarFormateadorAlta(Formateador obj) throws DelegateException  {    	
	    	try {
	            return getFacade().grabarFormateadorAlta(obj);            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public Long grabarFormateadorUpdate(Formateador obj)  throws DelegateException {    	
	    	try {
	            return getFacade().grabarFormateadorUpdate(obj);            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public void borrarFormateador(Long id)  throws DelegateException {
	    	try {
	            getFacade().borrarFormateador(id);            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
	    }
	    
	    public boolean puedoBorrarFormateador(Long id)  throws DelegateException {
	    	try {
	            return getFacade().puedoBorrarFormateador(id);            
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        } 
	    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private FormateadorFacade getFacade() throws NamingException,CreateException,RemoteException {
        return FormateadorFacadeUtil.getHome( ).create();
    }

    protected FormateadorDelegate() throws DelegateException {        
    }       

}
