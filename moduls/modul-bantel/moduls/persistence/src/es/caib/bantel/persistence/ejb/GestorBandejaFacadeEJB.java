package es.caib.bantel.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Procedimiento;

/**
 * SessionBean para mantener y consultar GestorBandeja
 *
 * @ejb.bean
 *  name="bantel/persistence/GestorBandejaFacade"
 *  jndi-name="es.caib.bantel.persistence.GestorBandejaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class GestorBandejaFacadeEJB extends HibernateEJB
{
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public GestorBandeja obtenerGestorBandeja(String id) {
        Session session = getSession();
        try {
        	// Cargamos GestorBandeja        	
        	GestorBandeja gestorBandeja = (GestorBandeja) session.load(GestorBandeja.class, id);
        	Hibernate.initialize( gestorBandeja.getProcedimientosGestionados() ); 
            return gestorBandeja;
        } catch (ObjectNotFoundException onf){
        	// No encontrado
        	return null;        	
        }catch (HibernateException he) {        
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public GestorBandeja findGestorBandeja(String id) 
    {
    	Session session = getSession();
        try 
        {
        	List lstResult = session.find( "FROM GestorBandeja gb  WHERE gb.seyconID = ?", id, Hibernate.STRING );
        	if ( lstResult.isEmpty() )
        	{
        		return null;
        	}
        	return  ( GestorBandeja ) lstResult.get( 0 );
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
        
	/* 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     
    public String grabarGestorBandeja(GestorBandeja obj) {        
    	Session session = getSession();
        try 
        {
        	Query query = session
            .createQuery("FROM GestorBandeja gb  WHERE gb.seyconID = :id")
            .setParameter("id",obj.getSeyconID());
        	List result = query.list();
        	if ( result.isEmpty() )
        	{
        		session.save( obj );
        	}
        	else
        	{
        		session.update( obj );
        	}
            return obj.getSeyconID();
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
    */
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public String grabarGestorBandeja(GestorBandeja obj, String[] identificadoresTramites ) {        
    	Session session = getSession();
        try 
        {
        	// Obtenemos gestor bandeja dentro de la sesión (si no da una excepción por objeto duplicado)
        	boolean nuevo = false;
        	GestorBandeja gest;
        	if (findGestorBandeja(obj.getSeyconID()) != null){
        		gest = (GestorBandeja) session.load(GestorBandeja.class,obj.getSeyconID());
        		gest.setEmail(obj.getEmail());
	        	gest.setPermitirCambioEstado(obj.getPermitirCambioEstado());
	        	gest.setPermitirCambioEstadoMasivo(obj.getPermitirCambioEstadoMasivo());
	        	gest.setPermitirGestionExpedientes(obj.getPermitirGestionExpedientes());
	        	gest.setAvisarNotificaciones(obj.getAvisarNotificaciones());
	        	gest.setAvisarEntradas(obj.getAvisarEntradas());
	        	gest.setAvisarMonitorizacion(obj.getAvisarMonitorizacion());
        	}else{        	
        		gest = obj;
        		nuevo = true;
        	}
        	
        	// Adecuamos lista de tramites gestionados: borramos anteriores y establecemos nueva lista
        	// - Borramos anteriores
        	gest.removeTramitesGestionados();    		        
        	// - Establecemos nueva lista        	
        	if ( identificadoresTramites != null )
        	{        		                		
        		for ( int i = 0; i < identificadoresTramites.length; i++  )
        		{
        			Query query = session.createQuery("FROM Procedimiento AS m WHERE m.identificador = :idProcedimiento");
       			 	query.setParameter("idProcedimiento", identificadoresTramites[i]);
       			 	Procedimiento tramite = (Procedimiento) query.uniqueResult();
        			gest.addTramiteGestionado( tramite );     
        		}
        	}               	
        	
        	// Salvamos cambios
        	if (nuevo){
        		session.save(gest);
        	}else{
        		session.update(gest);
        	}
        	        	   
            return obj.getSeyconID();
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
    public void borrarGestorBandeja(String id) {
        Session session = getSession();
        try {
            GestorBandeja gestorBandeja = (GestorBandeja) session.load(GestorBandeja.class, id);
            session.delete(gestorBandeja);
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
    public List listarGestoresBandeja()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM GestorBandeja g order by g.seyconID");
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
     */
    public List listarGestoresBandeja(String filtro)
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM GestorBandeja g  WHERE upper(g.seyconID) like :filtroDesc order by g.seyconID");
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
    
    

}
