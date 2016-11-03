package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.persistence.intf.GestorFormularioFacade;
import es.caib.sistra.persistence.util.GestorFormularioFacadeUtil;

public class GestorFormularioDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	 public List listar() throws DelegateException
	{
		try
		{			
			return getFacade().listar();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	 
	 public List listar(String filtro) throws DelegateException
		{
			try
			{			
				return getFacade().listar(filtro);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	 	 
		 }

	 public GestorFormulario obtener(String id) throws DelegateException
		{
			try
			{			
				return getFacade().obtener(id);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	 	 
		 }
	 
	 
	 public String grabarFormularioAlta(GestorFormulario obj) throws DelegateException { 
		 try
			{			
				return getFacade().grabarFormularioAlta(obj);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	
	 }
	    
	 public String grabarFormularioUpdate(GestorFormulario obj) throws DelegateException { 
		 try
			{			
				return getFacade().grabarFormularioUpdate(obj);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	
	 }
	 
	 public void borrarFormulario(String id) throws DelegateException {
		 try
			{			
				getFacade().borrarFormulario(id);				
			} catch (Exception e) {
		        throw new DelegateException(e);
		    }	
	 }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private GestorFormularioFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return GestorFormularioFacadeUtil.getHome().create();
    }

    protected GestorFormularioDelegate() throws DelegateException {        
    }                  
}

