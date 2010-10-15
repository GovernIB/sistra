package es.caib.sistra.persistence.ejb;

import java.util.Iterator;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.persistence.util.ConfigurationUtil;

/**
 * EJB que sirve para obtener la configuracion del modulo
 *
 * @ejb.bean
 *  name="sistra/persistence/ConfiguracionFacade"
 *  local-jndi-name = "es.caib.sistra.persistence.ConfiguracionFacade"
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
	 * Obtiene propiedades de configuracion
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
    		Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
    		OrganismoInfo oi = new  OrganismoInfo();
    		oi.setNombre(props.getProperty("organismo.nombre"));
    		oi.setUrlLogo(props.getProperty("organismo.logo"));
    		oi.setUrlPortal(props.getProperty("organismo.portal.url"));
    		oi.setPieContactoHTML(props.getProperty("organismo.footer.contacto"));
    		oi.setTelefonoIncidencias(props.getProperty("organismo.soporteTecnico.telefono"));
    		oi.setUrlSoporteIncidencias(props.getProperty("organismo.soporteTecnico.url"));    		
    		oi.setUrlCssCustom(props.getProperty("organismo.cssCustom"));
    		
    		// Obtenemos titulo y referencia a la zona personal
    		for (Iterator it=props.keySet().iterator();it.hasNext();){
    			String key = (String) it.next();
    			if (key.startsWith("organismo.zonapersonal.titulo.")){
    				oi.getTituloPortal().put(key.substring(key.lastIndexOf(".") +1),props.get(key));
    			}
    			if (key.startsWith("organismo.zonapersonal.referencia.")){
    				oi.getReferenciaPortal().put(key.substring(key.lastIndexOf(".") +1),props.get(key));
    			}
    		}
    		
    		return oi;
    	}catch(Exception ex){
    		throw new EJBException(ex);
    	}         
    }
        	

}
