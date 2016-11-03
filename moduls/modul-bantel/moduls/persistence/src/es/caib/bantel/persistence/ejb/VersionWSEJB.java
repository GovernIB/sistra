package es.caib.bantel.persistence.ejb;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.persistence.util.VersionesWSBantel;

/**
 * SessionBean que sirve para coger las versiones de los ws que invoca sistra
 *
 * @ejb.bean
 *  name="bantel/persistence/VersionWSFacade"
 *  jndi-name="es.caib.bantel.persistence.VersionWSFacade"
 *  type="Stateless"
 *  view-type="remote"
 */
public abstract class VersionWSEJB implements SessionBean {
	
	protected static Log log = LogFactory.getLog(VersionWSEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		log.info("ejbCreate: " + this.getClass());
	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public List obtenerVersiones() {    	
    	List versiones = new ArrayList();
    	HashMap ver = new HashMap();
    	ver.put("CODIGO","");
    	ver.put("DESCRIPCION","");
    	versiones.add(ver);
    	try {
			Properties props = VersionesWSBantel.getInstance().obtenerPropiedades();
			if(props != null){
				Enumeration keys = props.keys(); 
				while (keys.hasMoreElements()) { 
					String key = (String)keys.nextElement();
					ver = new HashMap();
					ver.put("CODIGO",key);
			    	ver.put("DESCRIPCION",props.getProperty(key));
			    	versiones.add(ver);
				}
			}
		} catch (Exception e) {
			log.debug("No se ha podido obtener el fichero de propiedades versionesWSBantel.properties");
		}
    	return versiones;
    }
}
