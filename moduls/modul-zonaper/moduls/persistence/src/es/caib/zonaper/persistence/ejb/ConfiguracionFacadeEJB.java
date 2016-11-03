package es.caib.zonaper.persistence.ejb;

import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.persistence.util.ConfigurationUtil;

/**
 * EJB que sirve para obtener la configuracion del modulo
 *
 * @ejb.bean
 *  name="zonaper/persistence/ConfiguracionFacade"
 *  local-jndi-name = "es.caib.zonaper.persistence.ConfiguracionFacade"
 *  
 *  type="Stateless"
 *  view-type="local"
 */
public abstract class ConfiguracionFacadeEJB extends HibernateEJB  {
	
	protected static Log log = LogFactory.getLog(ConfiguracionFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
	
	/**
	 * 
	 * Obtiene configuracion
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public Properties obtenerPropiedades() {
    	try{
    		return ConfigurationUtil.getInstance().obtenerPropiedades();
    	}catch(Exception ex){
    		throw new EJBException(ex);
    	}         
    }

    /**
	 * 
	 * Obtiene las propiedades de configuracion referentes al organismo y las empaqueta en 
	 * una clase de modelo
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public OrganismoInfo obtenerOrganismoInfo() {
    	try{
    		return ConfigurationUtil.getInstance().obtenerOrganismoInfo();
    	}catch(Exception ex){
    		throw new EJBException(ex);
    	}   
    }
}
