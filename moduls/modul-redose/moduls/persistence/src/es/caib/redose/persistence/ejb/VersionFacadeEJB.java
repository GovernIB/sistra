package es.caib.redose.persistence.ejb;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Modelo;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;

/**
 * SessionBean para mantener y consultar Versiones.
 *
 * @ejb.bean
 *  name="redose/persistence/VersionFacade"
 *  jndi-name="es.caib.redose.persistence.VersionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class VersionFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Version obtenerVersion(Long id) {
        Session session = getSession();
        try {        	
        	Version version = (Version) session.load(Version.class, id);
        	Hibernate.initialize(version.getPlantillas());        	
        	return version;
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
    public Version obtenerVersion(String idModelo,int idVersion){
        Session session = getSession();
        try {
            Query query = session.createQuery("select v FROM Version AS v join v.modelo as m where v.version = :version and m.modelo = :modelo");                        
            query.setParameter("modelo", idModelo);
            query.setParameter("version", new Integer(idVersion));
            query.setCacheable(true);            
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Version version = (Version) result.get(0);            
            Hibernate.initialize(version.getPlantillas());      
            
            return version;

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
    public Version obtenerVersionCompleta(String idModelo,int idVersion){
        Session session = getSession();
        try {
            Query query = session.createQuery("select v FROM Version AS v join v.modelo as m where v.version = :version and m.modelo = :modelo");                        
            query.setParameter("modelo", idModelo);
            query.setParameter("version", new Integer(idVersion));
            query.setCacheable(true);            
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Version version = (Version) result.get(0);            
            Hibernate.initialize(version.getPlantillas());      
            
            // Inicializamos plantillas
            for (Iterator it = version.getPlantillas().iterator();it.hasNext();){
            	Plantilla p = (Plantilla) it.next();
            	Hibernate.initialize(p.getPlantillasIdioma());
            }
            
            return version;

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
    public Long grabarVersion(Version obj,Long idModelo) {    	
        Session session = getSession();
        try {
            if (obj.getCodigo() == null) {
                Modelo modelo = (Modelo) session.load(Modelo.class, idModelo);
                modelo.addVersion(obj);
                session.flush();
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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Set listarVersionesModelo(Long id) {    	
    	Session session = getSession();
        try {
            Modelo modelo = (Modelo) session.load(Modelo.class, id);
            Hibernate.initialize(modelo.getVersiones());
            return modelo.getVersiones();            
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
    public void borrarVersion(Long id) {
    	Session session = getSession();
        try {
            Version version = (Version) session.load(Version.class, id);
            version.getModelo().removeVersion(version);            
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
    public boolean puedoBorrarVersion(Long id) {
    	
    	Session session = getSession();    	    	    	
        try {        	        	
        	/*
        	// Comprueba si hay plantillas
        	Version version = (Version) session.load(Version.class, id);
        	if (version.getPlantillas() != null && version.getPlantillas().size() > 0) return false;
        	*/
        	
        	// Comprueba si hay documentos que utilizan la version        	
        	Query query = session.createQuery("SELECT COUNT(*) FROM Documento AS d WHERE d.version.codigo = :id");            
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