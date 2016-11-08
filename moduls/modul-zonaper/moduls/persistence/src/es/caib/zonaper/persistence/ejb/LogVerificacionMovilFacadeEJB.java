package es.caib.zonaper.persistence.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import es.caib.zonaper.model.LogVerificacionMovil;

/**
 * SessionBean para mantener y consultar RegistroExterno
 *
 * @ejb.bean
 *  name="zonaper/persistence/LogVerificacionMovilFacade"
 *  jndi-name="es.caib.zonaper.persistence.LogVerificacionMovilFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class LogVerificacionMovilFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
 
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public LogVerificacionMovil obtenerLogVerificacionMovil(Long id) {
        Session session = getSession();
        try {
        	// Cargamos LogExterno        	
        	LogVerificacionMovil logRegistro = (LogVerificacionMovil) session.load(LogVerificacionMovil.class, id);                       
            return logRegistro;
        } catch(ObjectNotFoundException ex){
        	return null;
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
     */
    public void grabarLogVerificacionMovil(LogVerificacionMovil logRegistro) {        
    	Session session = getSession();
        try {  
        	if(logRegistro.getCodigo() != null){
        		session.update(logRegistro);        		
        	}else{
        		session.save(logRegistro);
        	}
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    
}
