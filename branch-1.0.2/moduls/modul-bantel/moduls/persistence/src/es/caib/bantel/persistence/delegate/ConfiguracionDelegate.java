package es.caib.bantel.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.bantel.persistence.intf.ConfiguracionFacadeLocal;
import es.caib.bantel.persistence.util.ConfiguracionFacadeUtil;

public class ConfiguracionDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */
	public Properties obtenerConfiguracion() throws DelegateException
	{
		try
		{			
			return getFacade().obtenerPropiedades();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ConfiguracionFacadeLocal getFacade() throws NamingException,RemoteException,CreateException {      	    	
    	return ConfiguracionFacadeUtil.getLocalHome().create();
    }

    protected ConfiguracionDelegate() throws DelegateException {        
    }                  
}

