package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.model.Page;
import es.caib.bantel.persistence.intf.FuenteDatosFacade;
import es.caib.bantel.persistence.util.FuenteDatosFacadeUtil;

/**
 * Business delegate para operar con FuenteDatos.
 */
public class FuenteDatosDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	public List realizarConsulta(String idFuenteDatos, List filtros, List parametros) throws DelegateException {
        try {
            return getFacade().realizarConsulta(idFuenteDatos, filtros, parametros);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }       
	
	public List listarFuentesDatos() throws DelegateException {
        try {
            return getFacade().listarFuentesDatos();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    } 
        
	public FuenteDatos obtenerFuenteDatos(String identificador)  throws DelegateException {
        try {
            return getFacade().obtenerFuenteDatos(identificador);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	
	public Long altaFuenteDatos(String identificador, String procedimiento, String descripcion) throws DelegateException {
        try {
            return getFacade().altaFuenteDatos(identificador, procedimiento, descripcion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	 public void modificarFuenteDatos(String identificador, String identificadorNew, String procedimientoNew, String descripcionNew) throws DelegateException {
		 try {
            getFacade().modificarFuenteDatos(identificador, identificadorNew, procedimientoNew, descripcionNew);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
	 }
	 
	 public void borrarFuenteDatos(String identificador)  throws DelegateException {
		 try {
	            getFacade().borrarFuenteDatos(identificador);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }
	 
	 public void altaCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampo) throws DelegateException {
		 try {
	            getFacade().altaCampoFuenteDatos(identificadorFuenteDatos, identificadorCampo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	 
	
	 public void borrarCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampo)  throws DelegateException {
		 try {
	            getFacade().borrarCampoFuenteDatos(identificadorFuenteDatos, identificadorCampo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	 
	 
	 public void altaFilaFuenteDatos(String identificadorFuenteDatos) throws DelegateException {
		 try {
	            getFacade().altaFilaFuenteDatos(identificadorFuenteDatos);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	 
	 public void borrarFilaFuenteDatos(String identificadorFuenteDatos, int numFila) throws DelegateException {
		 try {
	            getFacade().borrarFilaFuenteDatos(identificadorFuenteDatos, numFila);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	 
	 
	 public void establecerValorFuenteDatos(String identificadorFuenteDatos, int numFila, String identificadorCampo, String valorCampo)  throws DelegateException {
		 try {
	            getFacade().establecerValorFuenteDatos(identificadorFuenteDatos, numFila, identificadorCampo, valorCampo);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	 
	 public void modificarCampoFuenteDatos(String identificadorFuenteDatos, String identificadorCampoOld, String identificadorCampoNew)  throws DelegateException {
		 try {
	            getFacade().modificarCampoFuenteDatos(identificadorFuenteDatos, identificadorCampoOld, identificadorCampoNew);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	
	 public Page busquedaPaginadaFuenteDatosGestor(int pagina, int longitudPagina )throws DelegateException {
		 try {
	           return getFacade().busquedaPaginadaFuenteDatosGestor(pagina, longitudPagina);
	        } catch (Exception e) {
	            throw new DelegateException(e);
	        }
		 }  
	
	 
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private FuenteDatosFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return FuenteDatosFacadeUtil.getHome( ).create();
    }

    protected FuenteDatosDelegate() throws DelegateException {       
    }                  
}

