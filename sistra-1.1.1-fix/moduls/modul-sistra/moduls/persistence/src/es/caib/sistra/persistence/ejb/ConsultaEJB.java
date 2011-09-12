package es.caib.sistra.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.modelInterfaz.DocumentoConsulta;
import es.caib.sistra.modelInterfaz.FormularioConsulta;

/**
 * EJB que sirve para generar los interfaces para crear los ejbs que resuelven
 * tramites de tipo consulta 
 *
 * @ejb.bean
 *  name="sistra/persistence/ConsultaEJB"
 *  jndi-name="es.caib.sistra.persistence.ConsultaEJB"
 *  type="Stateless"
 *  view-type="remote"
 */
public abstract class ConsultaEJB implements SessionBean {
	
	protected static Log log = LogFactory.getLog(ConsultaEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		log.info("ejbCreate: " + this.getClass());
	}
	
	
	/**
	 * 
	 * Recibe formularios y obtiene lista de documentos
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public DocumentoConsulta[] realizarConsulta(FormularioConsulta forms[]) {
    	return null;    	
    }

}
