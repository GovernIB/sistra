package es.caib.redose.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.model.Page;

/**
 * SessionBean para mantener y consultar LogGestorDocumentalError.
 *
 * @ejb.bean
 *  name="redose/persistence/LogGestorDocumentalErroresFacade"
 *  jndi-name="es.caib.redose.persistence.LogGestorDocumentalErroresFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class LogGestorDocumentalErroresFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public LogGestorDocumentalError obtenerLogGestorDocumentalError(Long id) {
        Session session = getSession();
        try {
        	// Cargamos logGestorDocumentalError        	
        	LogGestorDocumentalError logGestorDocumentalError = (LogGestorDocumentalError) session.load(LogGestorDocumentalError.class, id);
        
            return logGestorDocumentalError;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Realiza el log de los errores del gestor documental.
     * Este log se ejecuta en una nueva transaccion para asegurar que se apunta el error
     * aunque se haga un rollback global del proceso
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.transaction type = "RequiresNew"
     * 
     * @param obj El log del documento a guardar
     */
    public Long grabarError(LogGestorDocumentalError obj) {        
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
    public List listarLogGestorDocumentalErrores(Date startDate, Date endDate, String idUsuario) {
        Session session = getSession();
        try {              	
        	Criteria criteria = session.createCriteria(LogGestorDocumentalError.class);
        	if (startDate != null) {
        		criteria.add(Expression.ge("fecha",startDate));
        	}
        	if (endDate != null) {
        		criteria.add(Expression.le("fecha",endDate));
        	}
        	if (idUsuario != null){
        		criteria.add(Expression.eq("usuarioSeycon",idUsuario));
        	}
        	List results = criteria.list();
            return results;
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
    public void borrarLogGestorDocumentalError(Long id) {
        Session session = getSession();
        try {        	
        	LogGestorDocumentalError logGestorDocumentalError = (LogGestorDocumentalError) session.load(LogGestorDocumentalError.class, id);
            session.delete(logGestorDocumentalError);
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
    public void borrarLogGestorDocumentalErrores(){
        // Eliminamos logs
    	Session session = getSession();
        try {      
        	//session.delete("from LogGestorDocumentalError");
        	session.connection().prepareStatement("delete from RDS_LOGEGD").executeUpdate();        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        } 
    }
    
    
    /**
	 * Realiza búsqueda paginada del log
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}" 
     * @ejb.permission role-name="${role.operador}"
     */
	public Page busquedaPaginada(int pagina, int longitudPagina )
	{
		Session session = getSession();
        try 
        {
        	Query query = 
        		session.createQuery( "FROM LogGestorDocumentalError o ORDER BY o.fecha DESC, o.codigo DESC" );
            Page page = new Page( query, pagina, longitudPagina );
            return page;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
        }
        catch( Exception exc )
        {
        	throw new EJBException( exc );
        }
        finally
        {
        	close( session );
        }
	}
	
    
}
