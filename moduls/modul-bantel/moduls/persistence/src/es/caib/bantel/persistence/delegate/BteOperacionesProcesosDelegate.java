package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.model.DetalleEntradasProcedimiento;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.intf.BteOperacionesProcesosFacade;
import es.caib.bantel.persistence.util.BteOperacionesProcesosFacadeUtil;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;

/**
 * Interfaz para operar con procesos de la BTE
 */
public class BteOperacionesProcesosDelegate implements StatelessDelegate {

	public void marcarAvisoRealizado(String idProcedimiento,
			Date ahora) throws DelegateException {
        try {
            getFacade().marcarAvisoRealizado(idProcedimiento,
        			ahora);
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
	public void realizarEnviosGestores(String tipoAviso, MensajeEnvio enviosEmail,
			 Date ahora) throws DelegateException {
        try {
            getFacade().realizarEnviosGestores(tipoAviso, enviosEmail,
        			 ahora);
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
	public void marcarEntradasCaducadas(String idProcedimiento,
			Date fechaLimite) throws DelegateException {
        try {
            getFacade().marcarEntradasCaducadas(idProcedimiento, fechaLimite);
        } catch (Exception e) {
        	throw new DelegateException(e);
        }
    }
	
	 public DetalleEntradasProcedimiento obtenerDetalleEntradasProcedimiento(Procedimiento procedimiento,
				Date desde, Date hasta) throws DelegateException {
		 try {
	           return  getFacade().obtenerDetalleEntradasProcedimiento(procedimiento,
	   				desde, hasta);
	        } catch (Exception e) {
	        	throw new DelegateException(e);
	        }
		 
	 }
	 
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   
    private BteOperacionesProcesosFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return BteOperacionesProcesosFacadeUtil.getHome( ).create();
    }

    protected BteOperacionesProcesosDelegate() throws DelegateException {
        
    }                  
}

