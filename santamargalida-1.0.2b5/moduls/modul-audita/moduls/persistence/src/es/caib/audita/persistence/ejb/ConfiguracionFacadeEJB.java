package es.caib.audita.persistence.ejb;

import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.persistence.util.ConfigurationUtil;

/**
 * EJB que sirve para obtener la configuracion del modulo
 *
 * @ejb.bean
 *  name="audita/persistence/ConfiguracionFacade"
 *  local-jndi-name = "es.caib.audita.persistence.ConfiguracionFacade"
 *  type="Stateless"
 *  view-type="local"
 */
public abstract class ConfiguracionFacadeEJB implements SessionBean  {
	
	protected static Log log = LogFactory.getLog(ConfiguracionFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		
	}
		
	/**
	 * 
	 * Recibe formularios y obtiene lista de documentos
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

}
