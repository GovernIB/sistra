package es.caib.bantel.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.bantel.model.Entidad;

/**
 * SessionBean para mantener y consultar Entidad
 *
 * @ejb.bean
 *  name="bantel/persistence/EntidadFacade"
 *  jndi-name="es.caib.bantel.persistence.EntidadFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class EntidadFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     */
    public Entidad obtenerEntidad(String id) {
    	 Session session = getSession();
         try {       	
         	Query query = session
             .createQuery("FROM Entidad AS t WHERE t.identificador = :identificador")
             .setParameter("identificador",id);
         	//query.setCacheable(true);
             if (query.list().isEmpty()){
             	return null;             	
             }
             Entidad entidad = ( Entidad ) query.uniqueResult();
             return entidad;
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
    }    
    
   /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String grabarEntidad(Entidad obj) {        
    	Session session = getSession();
        try {
        	
        	Entidad tramite = obtenerEntidad(obj.getIdentificador()); 
        	if (tramite == null)
        	{
        		session.save( obj );
        	}
        	else
        	{
        		session.update( obj );
        	}        	
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
     */
    public void borrarEntidad(String id) {
        Session session = getSession();
        try {
        	Entidad tramite = (Entidad) session.load(Entidad.class, id);
            session.delete(tramite);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarEntidades()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Entidad o order by o.identificador");
    		query.setCacheable( true );
    		return query.list();
    	}
	    catch (HibernateException he) 
	    {
	        throw new EJBException(he);
	    } 
	    finally 
	    {
	        close(session);
	    }    	
    }
    
    /**
     * Comprueba si se puede borrar entidad
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean puedoBorrarEntidad(String id) {
		Session session = getSession();    	    	    	
    	try {        	        	
	    	
	    	// Comprueba si hay entradas de este tramtie        	
	    	Query query = session.createQuery("SELECT COUNT(*) FROM Procedimiento AS t WHERE t.entidad.identificador = :id");            
	        query.setParameter("id", id);
	        if (Long.parseLong(query.uniqueResult().toString()) > 0) return false;            
	                    
	        // Puede borrarse
	        return true;
		        
	    } catch (Exception he) {
	        throw new EJBException(he);
	    } finally {        	        
	        close(session);
	        
	    }
	}    
    
}

