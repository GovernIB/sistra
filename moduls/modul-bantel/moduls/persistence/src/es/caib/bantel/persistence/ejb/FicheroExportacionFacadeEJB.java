package es.caib.bantel.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.bantel.model.ArchivoFicheroExportacion;
import es.caib.bantel.model.FicheroExportacion;

/**
 * SessionBean para mantener y consultar Fichero exportacion
 *
 * @ejb.bean
 *  name="bantel/persistence/FicheroExportacionFacade"
 *  jndi-name="es.caib.bantel.persistence.FicheroExportacionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class FicheroExportacionFacadeEJB extends HibernateEJB {

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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     */
    public FicheroExportacion obtenerFicheroExportacion(String id) {
        Session session = getSession();
        try {       	
        	FicheroExportacion fic = (FicheroExportacion) session.load(FicheroExportacion.class, id); 
        	Hibernate.initialize(fic);
            return fic;
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
    public String guardarFicheroExportacion(FicheroExportacion obj) {
    	
    	boolean nuevo =  (findFicheroExportacion(obj.getIdentificadorTramite()) == null);
    	
    	Session session = getSession();
        try {        	
        	if ( nuevo )
        	{
        		session.save( obj );
        	}
        	else
        	{        	        		
        		session.update( obj );
        	}
        	return obj.getIdentificadorTramite();
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
    public void borrarFicheroExportacion(String id) {
        Session session = getSession();
        try {
        	FicheroExportacion ficExp = (FicheroExportacion) session.load(FicheroExportacion.class, id);
            session.delete(ficExp);
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
    public void borrarArchivoFicheroExportacion(String id) {
        Session session = getSession();
        try {
        	FicheroExportacion ficExp = (FicheroExportacion) session.load(FicheroExportacion.class, id);
        	ArchivoFicheroExportacion archivoFicheroExportacion = ficExp.getArchivoFicheroExportacion();
        	
        	// Quitamos referencia
        	ficExp.setArchivoFicheroExportacion(null);
        	session.update(ficExp);
        	
        	// Eliminamos archivo
			session.delete(archivoFicheroExportacion);
        	
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
    public List listarFicherosExportacion()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM FicheroExportacion o order by o.identificadorTramite");
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
     * @ejb.permission role-name="${role.gestor}"
     */
    public FicheroExportacion findFicheroExportacion(String id) 
    {
    	Session session = getSession();
        try 
        {
        	FicheroExportacion ficExp = (FicheroExportacion) session.get(FicheroExportacion.class, id);
        	return  ficExp;
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
    
}

