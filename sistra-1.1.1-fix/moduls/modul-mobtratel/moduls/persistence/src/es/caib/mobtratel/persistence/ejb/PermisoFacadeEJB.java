package es.caib.mobtratel.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Permiso;

/**
 * SessionBean para mantener y consultar Permisos.
 *
 * @ejb.bean
 *  name="mobtratel/persistence/PermisoFacade"
 *  jndi-name="es.caib.mobtratel.persistence.PermisoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PermisoFacadeEJB extends HibernateEJB {

	protected static Log log = LogFactory.getLog(PermisoFacadeEJB.class);
	
    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public Permiso obtenerPermiso(Long codigo) {
        Session session = getSession();
        try {
        	// Cargamos permiso        	
        	Permiso permiso = (Permiso) session.load(Permiso.class, codigo);
        	
            return permiso;
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     *@ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public Permiso obtenerPermiso(String usuario, String cuenta) {
        Session session = getSession();
        try {
        	
        	Query query = session.createQuery("FROM Permiso AS p where p.usuarioSeycon = :usuario and p.cuenta.codigo = :cuenta");
            query.setParameter("usuario", usuario);            
            query.setParameter("cuenta", cuenta);
            List result = query.list();
            if(result.size() != 0) return (Permiso) result.get(0);
            return null;
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public List listarPermisos(String usuario) {
        Session session = getSession();
        try {
        	
        	Query query = session.createQuery("FROM Permiso AS p where p.usuarioSeycon = :usuario");
            query.setParameter("usuario", usuario);            
            return query.list();
        } catch (ObjectNotFoundException onfe) {
            return null;
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
    public List listarPermisos()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Permiso p");
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
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long grabarPermiso(Permiso obj) { 
    	
    	Session session = getSession();
        try {
        	if(obj.getCodigo()!=null)
        		session.update(obj);
        	else        	
        		session.save(obj);
        	
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
     */
    public void borrarPermiso(Long codigo) {
        Session session = getSession();
        try {
        	Permiso permiso = (Permiso) session.load(Permiso.class, codigo);
        	session.delete(permiso);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }



}