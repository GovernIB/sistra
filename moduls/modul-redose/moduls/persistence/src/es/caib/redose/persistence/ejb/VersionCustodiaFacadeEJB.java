package es.caib.redose.persistence.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Modelo;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;
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
     * @ejb.permission role-name="${role.redose}"
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.redose}"
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
     * @ejb.permission role-name="${role.redose}"
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
     * @ejb.permission role-name="${role.redose}"
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
     * @ejb.permission role-name="${role.redose}"
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
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.redose}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String obtenerNumeroVersionCustodia(Long id) {
    	Session session = getSession();
    	String version = "";
        try {
        	Query query = session.createQuery("Select version.codigo FROM VersionCustodia AS version WHERE version.documento.codigo = :idDocumento");            
        	query.setParameter("idDocumento", id);
        	List versionesCustodia = query.list();
        	String versionCustodia = "";
        	if(versionesCustodia != null){
        		for(int i=0;i<versionesCustodia.size();i++){
        			if("".equals(version)){
        				versionCustodia = (String)versionesCustodia.get(i);
        				if(versionCustodia.indexOf("_") != -1){
            				version =versionCustodia.substring(versionCustodia.lastIndexOf("_")+1);
            			}
        			}else{
        				versionCustodia = (String)versionesCustodia.get(i);
        				if(versionCustodia.indexOf("_") != -1){
            				versionCustodia =versionCustodia.substring(versionCustodia.lastIndexOf("_")+1);
            				if(new Integer(versionCustodia).intValue() > new Integer(version).intValue()){
            					version = versionCustodia;
            				}
            			}
        			}
        		}
        		if(!"".equals(version)){
    				int ver = new Integer(version).intValue() + 1;
    				version = ver+"";
    			}else{
    				version = "1";
    			}
        		return version;
        	}else{
        		version = "1";
        		return version;
        	}
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}