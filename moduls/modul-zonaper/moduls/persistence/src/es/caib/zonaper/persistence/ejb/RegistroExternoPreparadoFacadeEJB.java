package es.caib.zonaper.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.LogRegistroId;
import es.caib.zonaper.model.RegistroExternoPreparado;

/**
 * SessionBean para mantener y consultar RegistroExternoPreparado
 * 
 *  @ejb.bean
 *  name="zonaper/persistence/RegistroExternoPreparadoFacade"
 *  jndi-name="es.caib.zonaper.persistence.RegistroExternoPreparadoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 */
public abstract class RegistroExternoPreparadoFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();	
	}
 
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public RegistroExternoPreparado obtenerRegistroExternoPreparado(Long id) {
        Session session = getSession();
        try {
        	// Cargamos RegistroExternoPreparado        	
        	RegistroExternoPreparado registro = (RegistroExternoPreparado) session.load(RegistroExternoPreparado.class, id);                               	
            return registro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }           
    
 	/**
 	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public Long grabarRegistroExternoPreparado(RegistroExternoPreparado registro) {        
    	Session session = getSession();
        try {     
        	session.save(registro);        	      	                    
            return registro.getCodigoRdsAsiento();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void borrarRegistroExternoPreparado(Long id) {        
    	Session session = getSession();
        try {     
        	RegistroExternoPreparado registro = (RegistroExternoPreparado) session.load(RegistroExternoPreparado.class, id);
        	session.delete(registro);
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarCaducados() {
    	Session session = getSession();
		try
		{
			Query query = session
			.createQuery( "FROM RegistroExternoPreparado e where e.fechaEliminacion < :hoy" )
			.setParameter("hoy",new Date());
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
