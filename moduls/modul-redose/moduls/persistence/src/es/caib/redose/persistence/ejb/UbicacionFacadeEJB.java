package es.caib.redose.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Ubicacion;

/**
 * SessionBean para mantener y consultar Ubicacions.
 *
 * @ejb.bean
 *  name="redose/persistence/UbicacionFacade"
 *  jndi-name="es.caib.redose.persistence.UbicacionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class UbicacionFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Ubicacion obtenerUbicacion(Long id) {
        Session session = getSession();
        try {
        	// Cargamos ubicacion        	
        	Ubicacion ubicacion = (Ubicacion) session.load(Ubicacion.class, id);        	        	
            return ubicacion;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Ubicacion obtenerUbicacion(String id){
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Ubicacion AS m WHERE m.codigoUbicacion = :ubicacion");
            query.setParameter("ubicacion", id);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Ubicacion ubicacion = (Ubicacion) result.get(0);        	
            return ubicacion;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Ubicacion obtenerUbicacionDefecto(){
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Ubicacion AS m WHERE m.defecto = 'S'");
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }
            
            if (result.size() > 1) {
            	throw new HibernateException("Existe configurada mas de una ubicacion por defecto");
            }

            Ubicacion ubicacion = (Ubicacion) result.get(0);        	
            return ubicacion;

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
    public Long grabarUbicacion(Ubicacion obj) {        
    	Session session = getSession();
        try {
        	// Guardamos ubicacion
            session.saveOrUpdate(obj);
            
            // Metemos control para si establecemos por defecto la ubicacion, resetear el resto
            if ("S".equals(obj.getDefecto())) {
            	Query query = session.createQuery("FROM Ubicacion");
                List result = query.list();
                for (Iterator it = result.iterator(); it.hasNext();) {
                	Ubicacion u = (Ubicacion) it.next();
                	if (u.getCodigo().longValue() != obj.getCodigo().longValue()) {
                		u.setDefecto("N");
                		session.update(u);
                	}
                }
            }
            
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
    public List listarUbicaciones() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Ubicacion AS m ORDER BY m.codigoUbicacion ASC");
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
    public void borrarUbicacion(Long id) {
        Session session = getSession();
        try {
            Ubicacion ubicacion = (Ubicacion) session.load(Ubicacion.class, id);
            session.delete(ubicacion);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
