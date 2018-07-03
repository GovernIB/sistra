package es.caib.bantel.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.bantel.model.Procedimiento;

/**
 * SessionBean para mantener y consultar Procedimiento
 *
 * @ejb.bean
 *  name="bantel/persistence/ProcedimientoFacade"
 *  jndi-name="es.caib.bantel.persistence.ProcedimientoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ProcedimientoFacadeEJB extends HibernateEJB {

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
    public Procedimiento obtenerProcedimiento(Long id) {
    	 Session session = getSession();
         try {       	
        	// Cargamos tramite        	
         	Procedimiento tramite = (Procedimiento) session.load(Procedimiento.class, id);

             return tramite;
         } catch (HibernateException he) {
             throw new EJBException(he);
         } finally {
             close(session);
         }
    }  
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     */
    public Procedimiento obtenerProcedimiento(String id) {
    	 Session session = getSession();
         try {       	
         	Query query = session
             .createQuery("FROM Procedimiento AS t WHERE t.identificador = :identificador")
             .setParameter("identificador",id);
         	//query.setCacheable(true);
             if (query.list().isEmpty()){
             	return null;             	
             }
             Procedimiento tramite = ( Procedimiento ) query.uniqueResult();
             return tramite;
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
    public Long grabarProcedimiento(Procedimiento obj) {        
    	Session session = getSession();
        try {
        	
        	if ( obj.getCodigo() == null )
        	{
        		session.save( obj );
        	}
        	else
        	{
        		Procedimiento tramite = obtenerProcedimiento(obj.getCodigo());
        		// Protegemos la fecha del ultimo aviso
        		obj.setUltimoAviso(tramite.getUltimoAviso());
        		// Update
        		session.update( obj );
        	}
        	//session.saveOrUpdate(obj);
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
    public void borrarProcedimiento(Long id) {
        Session session = getSession();
        try {
        	Procedimiento tramite = (Procedimiento) session.load(Procedimiento.class, id);
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
     * @ejb.permission role-name="${role.todos}"
     */
    public List listarProcedimientos()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Procedimiento o order by o.identificador");
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
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
    public List listarProcedimientos(String filtro)
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Procedimiento o WHERE upper(o.descripcion) like :filtroDesc order by o.identificador");
            query.setParameter("filtroDesc", "%" + StringUtils.defaultString(filtro).toUpperCase() + "%");
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
     * Marca como avisado 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void avisoRealizado(String id,Date fecha) {        
        Session session = getSession();
        try {       	
        	Query query = session
            .createQuery("FROM Procedimiento AS t WHERE t.identificador = :identificador")
            .setParameter("identificador",id);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	throw new HibernateException("No existe procedimiento con id: " + id);
            }
            Procedimiento tramite = ( Procedimiento ) query.uniqueResult();
            tramite.setUltimoAviso(fecha);
            session.update(tramite);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * Indica que ha habido un error de conexion
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void errorConexion(String id,byte[] error) {        
        Session session = getSession();
        try {       	
        	Query query = session
            .createQuery("FROM Procedimiento AS t WHERE t.identificador = :identificador")
            .setParameter("identificador",id);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	throw new HibernateException("No existe procedimiento con id: " + id);
            }
            Procedimiento tramite = ( Procedimiento ) query.uniqueResult();
            tramite.setErrores(error);
            session.update(tramite);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Comprueba si hay entradas de este tramite en la bandeja 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean puedoBorrarProcedimiento(Long id) {
		Session session = getSession();    	    	    	
    	try {        	        	
	    	
	    	// Comprueba si hay entradas de este tramtie        	
	    	Query query = session.createQuery("SELECT COUNT(*) FROM TramiteBandeja AS t WHERE t.procedimiento.codigo = :id");            
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

