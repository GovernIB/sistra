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
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;

/**
 * SessionBean para mantener y consultar Plantillas.
 *
 * @ejb.bean
 *  name="redose/persistence/PlantillaFacade"
 *  jndi-name="es.caib.redose.persistence.PlantillaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PlantillaFacadeEJB extends HibernateEJB {

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
     * @ejb.permission role-name="${role.operador}"
     */
    public Plantilla obtenerPlantilla(Long id) {
        Session session = getSession();
        try {        	
        	Plantilla plantilla = (Plantilla) session.load(Plantilla.class, id);
        	Hibernate.initialize(plantilla.getPlantillasIdioma());
        	return plantilla;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
   
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Plantilla obtenerPlantilla(Version version,String id) {
    	Session session = getSession();
        try {
            Query query = session.createQuery("FROM Plantilla AS p WHERE p.version = :version and  p.tipo = :id");
            query.setParameter("version", version);
            query.setParameter("id", id);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Plantilla plantilla = (Plantilla) result.get(0);        	
            return plantilla;

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
    public Long grabarPlantilla(Plantilla obj,Long idVersion) {    	
        Session session = getSession();
        try {
            if (obj.getCodigo() == null) {
                Version version = (Version) session.load(Version.class, idVersion);
                version.addPlantilla(obj);
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
    public Set listarPlantillasVersion(Long id) {    	
    	Session session = getSession();
        try {
        	Version version = (Version) session.load(Version.class, id);
        	Hibernate.initialize(version.getPlantillas());
        	
        	for (Iterator it = version.getPlantillas().iterator(); it.hasNext();) {
        		Plantilla p = (Plantilla) it.next();
        		Hibernate.initialize(p.getTraducciones());
        	}
        	
            return version.getPlantillas();            
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
    public void borrarPlantilla(Long id) {
    	Session session = getSession();
        try {        	
            Plantilla plantilla = (Plantilla) session.load(Plantilla.class, id);            
            plantilla.getVersion().removePlantilla(plantilla);            
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
    public boolean puedoBorrarPlantilla(Long id) {
    	
    	Session session = getSession();    	    	    	
        try {        	        	
        	/*
        	// Comprueba si hay plantillas idioma
        	Plantilla plantilla = (Plantilla) session.load(Plantilla.class, id); 
        	if (plantilla.getPlantillasIdioma() != null && plantilla.getPlantillasIdioma().size() > 0) return false;
        	*/
        	
        	// Comprueba si hay documentos que utilizan la plantilla        	
        	Query query = session.createQuery("SELECT COUNT(*) FROM Documento AS d WHERE d.plantilla.codigo = :id");            
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