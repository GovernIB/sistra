package es.caib.redose.persistence.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.TipoOperacion;
import es.caib.redose.model.Page;

/**
 * SessionBean para mantener y consultar LogOperacions.
 *
 * @ejb.bean
 *  name="redose/persistence/LogOperacionFacade"
 *  jndi-name="es.caib.redose.persistence.LogOperacionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class LogOperacionFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.redose}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.redose}"
     */
    public LogOperacion obtenerLogOperacion(Long id) {
        Session session = getSession();
        try {
        	// Cargamos logOperacion        	
        	LogOperacion logOperacion = (LogOperacion) session.load(LogOperacion.class, id);
        
            return logOperacion;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
              
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.redose}"
     */
    public Long grabarLogOperacion(LogOperacion obj) {        
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
     * @ejb.permission role-name="${role.redose}"
     */
    public List listarLogOperaciones(Date startDate, Date endDate, String idUsuario, String idTipoOperacion) {
        Session session = getSession();
        try {              	
        	Criteria criteria = session.createCriteria(LogOperacion.class);
        	if (startDate != null) {
        		criteria.add(Expression.ge("fecha",startDate));
        	}
        	if (endDate != null) {
        		criteria.add(Expression.le("fecha",endDate));
        	}
        	if (idUsuario != null){
        		criteria.add(Expression.eq("usuarioSeycon",idUsuario));
        	}
        	if (idTipoOperacion != null){
        		TipoOperacion tipoOperacion = (TipoOperacion) session.load(TipoOperacion.class,idTipoOperacion);	        	  	
            	criteria.add(Expression.eq("tipoOperacion",tipoOperacion));
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
     * @ejb.permission role-name="${role.redose}"
     */
    public void borrarLogOperacion(Long id) {
        Session session = getSession();
        try {        	
            LogOperacion logOperacion = (LogOperacion) session.load(LogOperacion.class, id);
            session.delete(logOperacion);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.redose}"
     */
    public void borrarLogOperaciones(Date startDate, Date endDate, String idAplicacion, String idTipoOperacion) {
        
    	//TODO: Implementar borrado con session.delete(Str,Param,Types)
    	
    	// Obtenemos lista logs
    	List logs = this.listarLogOperaciones(startDate,endDate,idAplicacion,idTipoOperacion);
    	
    	// Eliminamos logs
    	Session session = getSession();
        try {              	
        	for (Iterator it=logs.iterator();it.hasNext();){
        		session.delete((LogOperacion)it.next());
        	}        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        } 
    }
    
    
    /**
	 * Realiza búsqueda paginada del log
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.redose}" 
     */
	public Page busquedaPaginada(int pagina, int longitudPagina )
	{
		Session session = getSession();
        try 
        {
        	Query query = 
        		session.createQuery( "FROM LogOperacion o ORDER BY o.fecha DESC, o.codigo DESC" );
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
