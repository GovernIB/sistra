package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.OrganismoInfo;
import org.ibit.rol.form.persistence.intf.ConfiguracionFacadeLocal;
import org.ibit.rol.form.persistence.util.ConfiguracionFacadeUtil;


/**
 * Business delegate para operar con formularios.
 */
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
	
	public OrganismoInfo obtenerOrganismoInfo() throws DelegateException
	{
		try
		{			
			return getFacade().obtenerOrganismoInfo();				
		} catch (Exception e) {
	        throw new DelegateException(e);
	    }	 	 
	 }
	
	public OrganismoInfo obtenerOrganismoInfo(String entidad) throws DelegateException
	{
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
    private ConfiguracionFacadeLocal getFacade() throws NamingException,CreateException,RemoteException {
        return ConfiguracionFacadeUtil.getLocalHome().create();
    }

    protected ConfiguracionDelegate() throws DelegateException {        
    }

}
