package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.ehcache.CacheException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.plugins.PluginDominio;

/**
 * SessionBean para mantener y consultar Dominio
 *
 * @ejb.bean
 *  name="sistra/persistence/DominioFacade"
 *  jndi-name="es.caib.sistra.persistence.DominioFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class DominioFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Dominio obtenerDominio(Long id) {
        Session session = getSession();
        try {
        	// Cargamos dominio        	
        	Dominio dominio = (Dominio) session.load(Dominio.class, id);        	        	      	
            return dominio;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public Dominio obtenerDominio(String id) {
    	Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Dominio AS m where m.identificador=:id").setParameter("id",id);
            query.setCacheable(true);
            return (Dominio) query.uniqueResult();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Long grabarDominio(Dominio obj, Long idOrg) {        
    	Session session = getSession();
        try {        	
        	
        	if(obj.getCacheable() == 'N')
        	{
            	PluginDominio.limpiarCache(obj.getIdentificador());
        	}
        	
        	if ( obj.getCodigo() == null )
        	{
        		OrganoResponsable org = (OrganoResponsable) session.load(OrganoResponsable.class,idOrg);
        		org.addDominio( obj );
        		session.flush();
        	}
        	else
        	{
        		//if ( !obj.getOrganoResponsable().getCodigo().equals( idOrg  ) )
       			session.update( obj );
        	}
        	
            //session.saveOrUpdate(obj);                    	
            return obj.getCodigo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } catch (CacheException e) {
            throw new EJBException(e);
		} finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public List listarDominios() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Dominio AS m ORDER BY m.identificador ASC");
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
     * @ejb.permission role-name="${role.sistra}"
     */    
    public List listarDominios( Long idOrganoOrigen )
    {
    	Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Dominio AS m WHERE m.organoResponsable.codigo=:codigo ORDER BY m.identificador ASC");
            query.setParameter( "codigo", idOrganoOrigen );
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
     * @ejb.permission role-name="${role.sistra}"
     */
    public void borrarDominio(Long id) {
        Session session = getSession();
        try {
            Dominio dominio = (Dominio) session.load(Dominio.class, id);
            session.delete(dominio);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
