package es.caib.redose.persistence.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Formateador;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.model.Version;

/**
 * SessionBean para mantener y consultar Formateadores
 *
 * @ejb.bean
 *  name="redose/persistence/FormateadorFacade"
 *  jndi-name="es.caib.redose.persistence.FormateadorFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class FormateadorFacadeEJB extends HibernateEJB {

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
     * Obtiene el formateador identificado por el parametro id
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Formateador obtenerFormateador(Long id) {
        Session session = getSession();
        try {        	
        	Formateador formateador = (Formateador) session.load(Formateador.class, id);
        	return formateador;
        } catch (ObjectNotFoundException onfe){
        	log.debug("No existe el elemento "+id+" en la base de datos.");
        	return null;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
   
    /**
     * Obtiene el formateador identificado por el parametro id
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Formateador obtenerFormateador(String clase) {
        Session session = getSession();
        try {        	
        	Query query = session.createQuery("FROM Formateador AS f WHERE f.clase = :formateador");
            query.setParameter("formateador", clase);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

        	Formateador formateador = (Formateador) result.get(0);
        	return formateador;
        } catch (ObjectNotFoundException onfe){
        	log.debug("No existe el elemento "+clase+" en la base de datos.");
        	return null;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
	 * 
	 * Obtiene una lista de formateadores
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public List listar() {
    	 Session session = getSession();    	 
         try {
         	List formateadores = session.createQuery("from Formateador").list();
        	return formateadores;
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
    public Long grabarFormateadorAlta(Formateador obj) {    	
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarFormateadorUpdate(Formateador obj) {    	
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarFormateador(Long id) {
    	Session session = getSession();
        try {        	
        	Formateador formateador = (Formateador) session.load(Formateador.class, id);            
            session.delete(formateador);            
        } catch (ObjectNotFoundException onfe){
        	log.debug("No existe el formateador "+id+" en la base de datos.");
        	throw new EJBException(onfe);
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
    public boolean puedoBorrarFormateador(Long id) {
    	
    	Session session = getSession();    	    	    	
        try {        	        	
        	// Comprueba si hay plantillas que utilizan el formateador        	
        	Query query = session.createQuery("SELECT COUNT(*) FROM Plantilla AS d WHERE d.formateador.identificador = :id");            
            query.setParameter("id", id);
            if (Long.parseLong(query.uniqueResult().toString()) > 0){
            	// Hay plantillas que lo utilizan y no puede borrarse
            	return false;            
            }else{
                // Puede borrarse
                return true;
            }
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {        	        
            close(session);
        }                
    }
    
}