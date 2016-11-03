package es.caib.redose.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Modelo;

/**
 * SessionBean para mantener y consultar Modelos.
 *
 * @ejb.bean
 *  name="redose/persistence/ModeloFacade"
 *  jndi-name="es.caib.redose.persistence.ModeloFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ModeloFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Modelo obtenerModelo(Long id) {
        Session session = getSession();
        try {
        	// Cargamos modelo        	
        	Modelo modelo = (Modelo) session.load(Modelo.class, id);
        	
        	// Cargamos versiones
        	Hibernate.initialize(modelo.getVersiones());        	
            return modelo;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Modelo obtenerModelo(String id){
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Modelo AS m WHERE m.modelo = :modelo");
            query.setParameter("modelo", id);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Modelo modelo = (Modelo) result.get(0);            
        	Hibernate.initialize(modelo.getVersiones());        	
            return modelo;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarModelo(Modelo obj) {        
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public List listarModelos() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Modelo AS m ORDER BY m.modelo ASC");
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public List listarModelos(String filtro) {
        Session session = getSession();
        try {       	
        	Query query = session.createQuery("FROM Modelo AS m WHERE upper(m.nombre) like :filtroDesc ORDER BY m.modelo ASC");
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarModelo(Long id) {
        Session session = getSession();
        try {
            Modelo modelo = (Modelo) session.load(Modelo.class, id);
            session.delete(modelo);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
