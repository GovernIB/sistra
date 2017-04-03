package es.caib.zonaper.persistence.delegate;

import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.persistence.intf.ConfiguracionFacadeLocal;
import es.caib.zonaper.persistence.util.ConfiguracionFacadeUtil;

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
	
	public OrganismoInfo obtenerOrganismoInfo() throws DelegateException{
		try
		{			
			return getFacade().obtenerOrganismoInfo();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 
	}
	
	public OrganismoInfo obtenerOrganismoInfo(String entidad) throws DelegateException{
		try
		{			
			return getFacade().obtenerOrganismoInfo(entidad);				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 
	}

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private ConfiguracionFacadeLocal getFacade() throws CreateException, NamingException {      	    	
    	return ConfiguracionFacadeUtil.getLocalHome().create();
    }

    protected ConfiguracionDelegate() throws DelegateException {        
    } 
    
}

