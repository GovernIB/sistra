package es.caib.redose.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.VersionCustodia;
import es.caib.redose.persistence.delegate.DelegateException;

/**
 * SessionBean para mantener y consultar Versiones.
 *
 * @ejb.bean
 *  name="redose/persistence/VersionCustodiaFacade"
 *  jndi-name="es.caib.redose.persistence.VersionCustodiaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class VersionCustodiaFacadeEJB extends HibernateEJB {

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
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public VersionCustodia obtenerVersion(String id) {
        Session session = getSession();
        try {        	
        	VersionCustodia custodia = (VersionCustodia) session.get(VersionCustodia.class, id);
        	return custodia;
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
    public void borrarVersion(String id) {
    	Session session = getSession();
        try {
            VersionCustodia version = obtenerVersion(id);
            session.delete(version);
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
    public void marcarBorrarVersion(String id) {
    	Session session = getSession();
        try {
            VersionCustodia version = obtenerVersion(id);
            version.setBorrar('S');
            version.setFecha(new Date());
            session.update(version);
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
    public List listarVersionesCustodiaParaBorrar() throws DelegateException {
        Session session = getSession();    	    	    	
        try {        	        	
        	Query query = session.createQuery("FROM VersionCustodia AS version WHERE version.borrar = 'S'");            
            return query.list();
        } catch (Exception he) {
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
    public String obtenerCodigoVersionCustodia(Long id) {
    	Session session = getSession();
    	String version = "";
        try {
        	Query query = session.createQuery("Select version.codigo FROM VersionCustodia AS version WHERE version.documento.codigo = :idDocumento and version.borrar = 'N'");            
        	query.setParameter("idDocumento", id);
        	List versionesCustodia = query.list();
        	if(versionesCustodia != null && versionesCustodia.size() > 0){
        		return (String)versionesCustodia.get(0);
        	}else{
        		return null;
        	}
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}