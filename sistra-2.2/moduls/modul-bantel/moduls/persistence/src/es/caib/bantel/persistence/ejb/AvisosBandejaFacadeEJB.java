package es.caib.bantel.persistence.ejb;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.bantel.model.AvisosBandeja;

/**
 * SessionBean para mantener y consultar AvisosBandeja
 *
 * @ejb.bean
 *  name="bantel/persistence/AvisosBandejaFacade"
 *  jndi-name="es.caib.bantel.persistence.AvisosBandejaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class AvisosBandejaFacadeEJB extends HibernateEJB {

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
     * @ejb.permission role-name="${role.auto}"
     */
    public void actualizarAviso(String idAviso, Date fechaAviso) {        
    	Session session = getSession();
        try {
        	
        	AvisosBandeja avi = (AvisosBandeja) session.get(AvisosBandeja.class, idAviso);
        	
        	if ( avi == null )
        	{
        		avi = new AvisosBandeja();
            	avi.setIdentificador(idAviso);
            	avi.setFechaUltimoAviso(fechaAviso);
        		session.save( avi );
        	}
        	else
        	{
        		avi.setFechaUltimoAviso(fechaAviso);
        		session.update( avi );
        	}        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {        	
            close(session);
        }
    }     
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public Date obtenerFechaUltimoAviso(String idAviso) {        
    	Session session = getSession();
        try {
        	Date res = null;
        	AvisosBandeja avi = (AvisosBandeja) session.get(AvisosBandeja.class, idAviso);
        	if ( avi != null )
        	{
        		res = avi.getFechaUltimoAviso();
        	}
        	return res;        	        
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {        	
            close(session);
        }
    }     
    
        
}

