package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.ehcache.CacheException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.plugins.PluginDominio;

/**
 * EJB que sirve para obtener la configuracion del modulo
 *
 * @ejb.bean
 *  name="sistra/persistence/GestorFormularioFacade"
 *  jndi-name="es.caib.sistra.persistence.GestorFormularioFacade"
 *  type="Stateless"
 *  view-type="remote"
 */
public abstract class GestorFormularioFacadeEJB extends HibernateEJB  {
	
	protected static Log log = LogFactory.getLog(GestorFormularioFacadeEJB.class);
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
	
	/**
	 * 
	 * Obtiene lista de gestores de formularios
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public List listar() {
    	 Session session = getSession();    	 
         try {
         	List gestores = session.createQuery("from GestorFormulario").list();
        	return gestores;
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
         
    }
    
    
    /**
	 * 
	 * Obtiene lista de gestores de formularios
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public List listar(String filtro) {
    	 Session session = getSession();    	 
         try {
         	Query query = session.createQuery( "FROM GestorFormulario c WHERE upper(c.descripcion) like :filtroDesc order by c.descripcion");
    		query.setParameter("filtroDesc", "%" + StringUtils.defaultString(filtro).toUpperCase() + "%");
    		query.setCacheable( true );
    		return query.list();         	
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
         
    }
	
	/**
	 * 
	 * Obtiene gestor
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public GestorFormulario obtener(String id) {
    	 Session session = getSession();
         try {         	
        	 GestorFormulario gestor = (GestorFormulario) session.load(GestorFormulario.class, id);        	        	      	
             return gestor;
         } catch(ObjectNotFoundException onfe){
         	log.debug("El gestor de formularios "+id+" no se encuentra en la BBDD.");
         	return null;
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
         
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public String grabarFormularioAlta(GestorFormulario obj) {        
    	Session session = getSession();
        try {        	
        	session.save(obj);                    	
            return obj.getIdentificador();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public String grabarFormularioUpdate(GestorFormulario obj) {        
    	Session session = getSession();
        try {        	
        	session.update(obj);                    	
            return obj.getIdentificador();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarFormulario(String id) {
        Session session = getSession();
        try {
            GestorFormulario formulario = (GestorFormulario) session.load(GestorFormulario.class, id);
            session.delete(formulario);
        } catch(ObjectNotFoundException onfe){
        	log.debug("El gestor de formularios "+id+" no se encuentra en la BBDD.");
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
