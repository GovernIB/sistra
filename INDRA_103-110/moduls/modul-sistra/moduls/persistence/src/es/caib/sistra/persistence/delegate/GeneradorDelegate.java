package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.persistence.intf.GeneradorFacade;
import es.caib.sistra.persistence.util.GeneradorFacadeUtil;

/**
 * Generador de números de preregistro y envío
 */
public class GeneradorDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	public String generarNumeroPreregistro(char tipoDestino) throws DelegateException {   
	     try {
            return getFacade().generarNumeroPreregistro(tipoDestino);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public String generarNumeroEnvio() throws DelegateException {    
	     try {
           return getFacade().generarNumeroEnvio();
       } catch (Exception e) {
           throw new DelegateException(e);
       }
   }
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private GeneradorFacade getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return GeneradorFacadeUtil.getHome( ).create();
    }

    protected GeneradorDelegate() throws DelegateException {   
    }                  
}

