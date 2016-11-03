package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.MensajePlataforma;

/**
 * SessionBean para mantener y consultar MensajePlataforma
 *
 * @ejb.bean
 *  name="sistra/persistence/MensajePlataformaFacade"
 *  jndi-name="es.caib.sistra.persistence.MensajePlataformaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class MensajePlataformaFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     *  @ejb.permission role-name="${role.todos}"
     */
    public MensajePlataforma obtenerMensajePlataforma(Long id) {
        Session session = getSession();
        try {
        	// Cargamos mensajePlataforma        	
        	MensajePlataforma mensajePlataforma = (MensajePlataforma) session.load(MensajePlataforma.class, id);        	      
            return mensajePlataforma;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     *  @ejb.permission role-name="${role.todos}"
     */
    public MensajePlataforma obtenerMensajePlataforma(String id){
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM MensajePlataforma AS m WHERE m.identificador = :mensajePlataforma");
            query.setParameter("mensajePlataforma", id);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            MensajePlataforma mensajePlataforma = (MensajePlataforma) result.get(0);                    	        
            return mensajePlataforma;

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
    public Long grabarMensajePlataforma(MensajePlataforma obj) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
            	throw new HibernateException("No se pueden dar de alta mensajes de plataforma (se dan de alta por script)");
            } else {
                session.update(obj);
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
     *  @ejb.permission role-name="${role.todos}"
     */
    public List listarMensajePlataformas() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM MensajePlataforma AS m ORDER BY m.identificador ASC");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
}
