package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.modelInterfaz.ValoresDominio;

/**
 * SessionBean que sirve para generar las interfaces que deberan cumplir los ejbs de dominios remotos
 *
 * @ejb.bean
 *  name="sistra/persistence/DominioEJB"
 *  jndi-name="es.caib.sistra.persistence.Dominios"
 *  type="Stateless"
 *  view-type="remote"
 */
public abstract class DominioEJB implements SessionBean {
	
	protected static Log log = LogFactory.getLog(DominioEJB.class);
	
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
    public ValoresDominio obtenerDominio(String id, List parametros) {    	
    	return null;
    }

}
