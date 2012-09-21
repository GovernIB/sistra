package es.caib.mobtratel.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.model.Cuenta;

/**
 * SessionBean para mantener y consultar Cuentas.
 *
 * @ejb.bean
 *  name="mobtratel/persistence/CuentaFacade"
 *  jndi-name="es.caib.mobtratel.persistence.CuentaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class CuentaFacadeEJB extends HibernateEJB {

	protected static Log log = LogFactory.getLog(CuentaFacadeEJB.class);
	
    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor},${role.admin},${role.auto}"
     */
    public Cuenta obtenerCuenta(String codigo) {
        Session session = getSession();
        try {
        	// Cargamos cuenta        	
        	Cuenta cuenta = (Cuenta) session.load(Cuenta.class, codigo);
        	
            return cuenta;
            
        } catch (ObjectNotFoundException onfe) {
            return null;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto},${role.admin},${role.gestor}"
     */
    public Cuenta obtenerCuentaDefectoEmail() {
        Session session = getSession();
        Cuenta cuenta = null;
        try {
        	
        	Query query = session.createQuery("FROM Cuenta AS c where c.email is not null and c.defecto = 1 ");
        	List resultado = query.list();
        	if(resultado.size() >= 1) cuenta = (Cuenta) resultado.get(0);
        	if(resultado.size() > 1) log.debug("Hay mas de una cuenta por defecto para envios de email");
            return cuenta;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } 
        finally 
        {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto},${role.admin},${role.gestor}"
     */
    public Cuenta obtenerCuentaDefectoSMS() {
        Session session = getSession();
        Cuenta cuenta = null;
        try {
        	
        	Query query = session.createQuery("FROM Cuenta AS c where c.sms is not null and c.defecto = 1 ");
        	List resultado = query.list();
        	if(resultado.size() >= 1) cuenta = (Cuenta) resultado.get(0);
        	if(resultado.size() > 1) log.debug("Hay mas de una cuenta por defecto para envios de SMS");
            return cuenta;
        } catch (HibernateException he) {
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
    public List listarCuentas()
    {
    	Session session = getSession();
    	try
    	{
    		Query query = session.createQuery( "FROM Cuenta c");
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
     */
    public Cuenta findCuenta(String id) 
    {
    	Session session = getSession();
        try 
        {
        	List lstResult = session.find( "FROM Cuenta c WHERE c.codigo = ?", id, Hibernate.STRING );
        	if ( lstResult.isEmpty() )
        	{
        		return null;
        	}
        	return  ( Cuenta ) lstResult.get( 0 );
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
    public String grabarCuenta(Cuenta obj) {        
    	Session session = getSession();
        try {        	
        	// Obtenemos cuenta dentro de la sesión (si no da una excepción por objeto duplicado)
        	boolean nuevo = false;
        	Cuenta cuenta;
        	if (findCuenta(obj.getCodigo()) != null){
        		cuenta = (Cuenta) session.load(Cuenta.class,obj.getCodigo());
        		cuenta.setDefecto(obj.getDefecto());
        		cuenta.setEmail(obj.getEmail());
        		cuenta.setSms(obj.getSms());
        		cuenta.setNombre(obj.getNombre());
        	}else{        	
        		cuenta = obj;
        		nuevo = true;
        	}
        	
        	// Salvamos cambios
        	if (nuevo){
        		session.save(cuenta);
        	}else{
        		session.update(cuenta);
        	}

        	                    	
            return obj.getCodigo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    

    
    /**
     * Comprueba si hay envios pendientes 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean puedoBorrarCuenta(String id) {
		Session session = getSession();    	    	    	
    	try {        	        	
	    	
	    	// Comprueba si hay envios pendientes de esta cuenta        	
	    	Query query = session.createQuery("SELECT COUNT(*) FROM Envio AS e WHERE e.cuenta.codigo = :id and e.fechaEnvio is null and e.cancelado != 1");            
	        query.setParameter("id", id);
	        if (Long.parseLong(query.uniqueResult().toString()) > 0) return false;            

	    	query = session.createQuery("SELECT COUNT(*) FROM Permiso AS p WHERE p.cuenta.codigo = :id");            
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
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarCuenta(String id) {
        Session session = getSession();
        try {
        	Cuenta cuenta = (Cuenta) session.load(Cuenta.class, id);
        	session.delete(cuenta);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


}