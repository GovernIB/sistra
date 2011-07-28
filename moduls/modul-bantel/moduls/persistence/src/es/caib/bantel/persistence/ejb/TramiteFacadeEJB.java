package es.caib.bantel.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.model.Tramite;

/**
 * SessionBean para mantener y consultar Tramite
 *
 * @ejb.bean
 *  name="bantel/persistence/TramiteFacade"
 *  jndi-name="es.caib.bantel.persistence.TramiteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class TramiteFacadeEJB extends HibernateEJB {

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
    public Tramite obtenerTramite(String id) {
        Session session = getSession();
        try {       	
        	Tramite tramite = (Tramite) session.load(Tramite.class, id); 
            return tramite;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Se distingue del anterior en que devuelve null si no existe el tramite, no hace throw de la excepción
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     */
    public Tramite obtenerTramitePorId(String id) {
        Session session = getSession();
        try {       	
        	Query query = session
            .createQuery("FROM Tramite AS t WHERE t.identificador = :identificador")
            .setParameter("identificador",id);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            Tramite tramite = ( Tramite ) query.uniqueResult();
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
    public String grabarTramite(Tramite obj) {        
    	Session session = getSession();
        try {
        	
        	Tramite tramite = obtenerTramitePorId(obj.getIdentificador()); 
        	if ( tramite == null )
        	{
        		session.save( obj );
        	}
        	else
        	{
        		// Protegemos la fecha del ultimo aviso
        		obj.setUltimoAviso(tramite.getUltimoAviso());
        		// Update
        		session.update( obj );
        	}
        	//session.saveOrUpdate(obj);
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
    public void borrarTramite(String id) {
        Session session = getSession();
        try {
        	Tramite tramite = (Tramite) session.load(Tramite.class, id);
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
    public List listarTramites()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Tramite o order by o.descripcion");
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
            .createQuery("FROM Tramite AS t WHERE t.identificador = :identificador")
            .setParameter("identificador",id);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	throw new HibernateException("No existe trámite con id: " + id);
            }
            Tramite tramite = ( Tramite ) query.uniqueResult();
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
            .createQuery("FROM Tramite AS t WHERE t.identificador = :identificador")
            .setParameter("identificador",id);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	throw new HibernateException("No existe trámite con id: " + id);
            }
            Tramite tramite = ( Tramite ) query.uniqueResult();
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
    public boolean puedoBorrarTramite(String id) {
		Session session = getSession();    	    	    	
    	try {        	        	
	    	
	    	// Comprueba si hay entradas de este tramtie        	
	    	Query query = session.createQuery("SELECT COUNT(*) FROM TramiteBandeja AS t WHERE t.tramite.identificador = :id");            
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
    
    /**
     * Borra fichero guia de exportacion 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarFicheroExportacion(String id) {
		Session session = getSession();    	    	    	
    	try {        	        		    	
    		FicheroExportacion f = (FicheroExportacion) session.load(FicheroExportacion.class,id);
    		f.getTramite().setArchivoFicheroExportacion(null);
    		f.getTramite().setNombreFicheroExportacion(null);
    		session.delete(f);    		   
	    } catch (Exception he) {
	        throw new EJBException(he);
	    } finally {        	        
	        close(session);
	        
	    }
	}
    
    
  	
}

