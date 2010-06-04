package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;

/**
 * SessionBean para mantener y consultar EspecTramiteNivel
 *
 * @ejb.bean
 *  name="sistra/persistence/EspecTramiteNivelFacade"
 *  jndi-name="es.caib.sistra.persistence.EspecTramiteNivelFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class EspecTramiteNivelFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.sistra}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public EspecTramiteNivel obtenerEspecTramiteNivel(Long id) {
        Session session = getSession();
        try {
        	// Cargamos espec        	
        	EspecTramiteNivel espec = (EspecTramiteNivel) session.load(EspecTramiteNivel.class, id);
       
        	return espec;
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
    public Long grabarEspecTramiteNivel(EspecTramiteNivel obj) {        
    	Session session = getSession();
        try {        	
            session.saveOrUpdate(obj);                    	
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
     */
    public void borrarEspecTramiteNivel(Long id) {
        Session session = getSession();
        try {
            EspecTramiteNivel espec = (EspecTramiteNivel) session.load(EspecTramiteNivel.class, id);
            session.delete(espec);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Obtiene tramite version asociado a las especificaciones
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public TramiteVersion obtenerTramiteVersion(Long id) {
        Session session = getSession();
        try {
            EspecTramiteNivel espec = (EspecTramiteNivel) session.load(EspecTramiteNivel.class, id);
            
            // Comprobamos si son las especificaciones genéricas
            Query query = session.createQuery("FROM TramiteVersion AS tv WHERE tv.especificaciones = :espec");
            query.setParameter("espec", espec);            
            List result = query.list();
            if (!result.isEmpty()) {
                return (TramiteVersion) result.get(0); 
            }
            
            // Comprobamos si son las especificaciones por nivel
            query = session.createQuery("FROM TramiteNivel AS tn WHERE tn.especificaciones = :espec");
            query.setParameter("espec", espec);            
            result = query.list();
            if (!result.isEmpty()) {
                return ((TramiteNivel) result.get(0)).getTramiteVersion(); 
            }    
            
            // Especificaciones no asociadas, caso no posible
            return null;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
