package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.modelInterfaz.ReferenciaEntradaBTE;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.intf.BteFacade;
import es.caib.bantel.persistence.util.BteFacadeUtil;

/**
 * Interfaz para operar con la BTE
 */
public class BteDelegate implements StatelessDelegate {
	
    /* ========================================================= */
    /* ==        INTEGRACIÓN CON BACKOFFICES			 	 === */
    /* ========================================================= */   
    public TramiteBTE obtenerEntrada(String id) throws DelegateException {
        try {
            return getFacade().obtenerEntrada(id);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public TramiteBTE obtenerEntrada(ReferenciaEntradaBTE refEntrada)  throws DelegateException {
        try {
            return getFacade().obtenerEntrada(refEntrada);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public String[] obtenerNumerosEntradas(String identificadorTramite) throws DelegateException {
        try {
            return getFacade().obtenerNumerosEntradas(identificadorTramite);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public String[] obtenerNumerosEntradas(String identificadorTramite,String procesada,Date desde,Date hasta) throws DelegateException {
        try {
            return getFacade().obtenerNumerosEntradas(identificadorTramite,procesada,desde,hasta);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public ReferenciaEntradaBTE[] obtenerReferenciasEntradas(String identificadorTramite,String procesada,Date desde,Date hasta) throws DelegateException {
        try {
            return getFacade().obtenerReferenciasEntradas(identificadorTramite,procesada,desde,hasta);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public void procesarEntrada(String numeroEntrada,String procesada)throws DelegateException {
        try {
            getFacade().procesarEntrada(numeroEntrada,procesada);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    	
    
    public void procesarEntrada(String numeroEntrada,String procesada,String resultadoProcesamiento)throws DelegateException {
        try {
            getFacade().procesarEntrada(numeroEntrada,procesada,resultadoProcesamiento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }    
    
    public void procesarEntrada(ReferenciaEntradaBTE referenciaEntrada,String procesada,String resultadoProcesamiento) throws DelegateException {
        try {
            getFacade().procesarEntrada(referenciaEntrada,procesada,resultadoProcesamiento);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }     

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private BteFacade getFacade() throws NamingException,CreateException,RemoteException {      	    	
    	return BteFacadeUtil.getHome( ).create();
    }

    protected BteDelegate() throws DelegateException {      
    }                  
}

