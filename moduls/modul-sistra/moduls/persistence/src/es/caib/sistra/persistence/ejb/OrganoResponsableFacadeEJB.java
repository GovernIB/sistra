package es.caib.sistra.persistence.ejb;

import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.*;

/**
 * SessionBean para mantener y consultar OrganoResponsable
 *
 * @ejb.bean
 *  name="sistra/persistence/OrganoResponsableFacade"
 *  jndi-name="es.caib.sistra.persistence.OrganoResponsableFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class OrganoResponsableFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public OrganoResponsable obtenerOrganoResponsable(Long id) {
        Session session = getSession();
        try {
        	// Cargamos organoResponsable        	
        	OrganoResponsable organoResponsable = (OrganoResponsable) session.load(OrganoResponsable.class, id);        	
        	// Cargamos versiones
        	Hibernate.initialize(organoResponsable.getTramites());        	
            return organoResponsable;
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
    public Long grabarOrganoResponsable(OrganoResponsable obj) {        
    	Session session = getSession();
        try {        	
            session.saveOrUpdate(obj);                    	
            return obj.getCodigo();
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
    public List listarOrganoResponsables() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM OrganoResponsable AS m ORDER BY m.descripcion ASC");
            query.setCacheable(true);
            return query.list();
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
    public List listarOrganoResponsables(String filtro) {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM OrganoResponsable AS m WHERE upper(m.descripcion) like :filtroDesc ORDER BY m.descripcion ASC");
            query.setParameter("filtroDesc", "%" + StringUtils.defaultString(filtro).toUpperCase() + "%");
            query.setCacheable(true);
            return query.list();
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
    public void borrarOrganoResponsable(Long id) {
        Session session = getSession();
        try {
            OrganoResponsable organoResponsable = (OrganoResponsable) session.load(OrganoResponsable.class, id);
            session.delete(organoResponsable);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
  	
}
