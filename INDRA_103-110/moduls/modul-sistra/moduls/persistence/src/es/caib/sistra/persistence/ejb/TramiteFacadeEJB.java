package es.caib.sistra.persistence.ejb;

import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.*;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;

/**
 * SessionBean para mantener y consultar Tramite
 *
 * @ejb.bean
 *  name="sistra/persistence/TramiteFacade"
 *  jndi-name="es.caib.sistra.persistence.TramiteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class TramiteFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.helpdesk}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Tramite obtenerTramite(Long id) {
        Session session = getSession();
        try {
        	// Cargamos tramite        	
        	Tramite tramite = (Tramite) session.load(Tramite.class, id);
        	
        	// Cargamos versiones
        	Hibernate.initialize(tramite.getVersiones());        	
            return tramite;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
    public Tramite obtenerTramite(String id){
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Tramite AS m WHERE m.identificador = :tramite");
            query.setParameter("tramite", id);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Tramite tramite = (Tramite) result.get(0);            
        	Hibernate.initialize(tramite.getVersiones());        	
            return tramite;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Long grabarTramite(Tramite obj,Long idOrg) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
            	OrganoResponsable org = (OrganoResponsable) session.load(OrganoResponsable.class,idOrg);
            	org.addTramite(obj);   
                session.flush();
            } else {
                session.update(obj);
            }                    	
            return obj.getCodigo();            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarTramites() {
        Session session = getSession();
        try {       	
            Query query = session.createQuery("FROM Tramite AS m ORDER BY m.identificador ASC");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Set listarTramitesOrganoResponsable(Long id) {
        Session session = getSession();
        try {       	
        	OrganoResponsable organoResponsable = (OrganoResponsable) session.load(OrganoResponsable.class, id);
            Hibernate.initialize(organoResponsable.getTramites());
            return organoResponsable.getTramites(); 
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public void borrarTramite(Long id) {
    	// Eliminamos permisos acceso tramite
    	borrarPermisosAccesoTramite(id);
    	
    	// Eliminamos tramite
        Session session = getSession();
        try {
            Tramite tramite = (Tramite) session.load(Tramite.class, id);
            tramite.getOrganoResponsable().removeTramite(tramite);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Elimina permisos de acceso al tramite (grupos y usuarios) 
     * @param id
     */
    private void borrarPermisosAccesoTramite(Long id) {
        try {
        	GruposDelegate gdlg = DelegateUtil.getGruposDelegate();
        	gdlg.borrarTramiteGrupos(id);
        	gdlg.borrarTramiteUsuarios(id);
        } catch (DelegateException de) {
            throw new EJBException(de);
        }
    }
}
