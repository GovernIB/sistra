package es.caib.redose.persistence.plugin;

import java.util.List;
import javax.ejb.EJBException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import es.caib.redose.model.Fichero;
import es.caib.redose.persistence.util.HibernateLocator;

/**
 * Plugin de almacenamiento por defecto en el RDS
 * Almacena en BBDD los ficheros
 *
 */
public class PluginDefaultRDS implements PluginAlmacenamientoRDS {

	public void guardarFichero(Long id, byte[] datos) throws Exception {
		Session session = getSession();
        try {        	
        	// Buscamos si existe el fichero
        	Query query = session.createQuery("FROM Fichero AS f WHERE f.codigo = :codigo");
            query.setParameter("codigo", id);
            query.setCacheable(true);
            List result = query.list();
            
            // Insertamos/Actualizamos fichero
            Fichero fichero;
            if (result.isEmpty()) {
            	fichero = new Fichero();   
            	fichero.setCodigo(id);
            	fichero.setDatos(datos);
                session.save(fichero);
            }else{
            	fichero = (Fichero) result.get(0);
            	fichero.setDatos(datos);
                session.update(fichero);
            }
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}

	public byte[] obtenerFichero(Long id) throws Exception {
		 Session session = getSession();
	        try {
	        	// Cargamos modelo        	
	        	Fichero fichero = (Fichero) session.load(Fichero.class, id);	        		        	  
	            return fichero.getDatos();
	        } catch (HibernateException he) {
	            throw new EJBException(he);
	        } finally {
	            close(session);
	        }
	}	

	public void eliminarFichero(Long id) throws Exception {
		Session session = getSession();
        try {
        	// Cargamos modelo        	
        	Fichero fichero = (Fichero) session.load(Fichero.class, id);	        		        	  
            session.delete(fichero);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
		
	// Funciones auxiliares
	protected static Log log = LogFactory.getLog(PluginDefaultRDS.class);	

    protected Session getSession() {
    	SessionFactory sf = null;
        try {        	
        	sf = HibernateLocator.getSessionFactory();
            Session sessio = sf.openSession();
            return sessio;
        } catch (HibernateException e) {        
            throw new EJBException(e);
        }finally{
        	sf = null;
        }
    }

    protected void close(Session sessio) {
        if (sessio != null && sessio.isOpen()) {
            try {
                if (sessio.isDirty()) {
                    sessio.flush();
                }
                sessio.close();
            } catch (HibernateException e) {
                throw new EJBException(e);
            }
        }
    }

}
