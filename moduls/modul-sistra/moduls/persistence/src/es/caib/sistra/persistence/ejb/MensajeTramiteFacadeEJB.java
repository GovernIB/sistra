package es.caib.sistra.persistence.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.TramiteVersion;

/**
 * SessionBean para mantener y consultar MensajeTramite
 *
 * @ejb.bean
 *  name="sistra/persistence/MensajeTramiteFacade"
 *  jndi-name="es.caib.sistra.persistence.MensajeTramiteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class MensajeTramiteFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public MensajeTramite obtenerMensajeTramite(Long id) {
        Session session = getSession();
        try {
        	// Cargamos mensaje        	
        	MensajeTramite mensaje = (MensajeTramite) session.load(MensajeTramite.class, id);
            return mensaje;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public MensajeTramite obtenerMensajeTramite(String idTramite,int idVersion,String idMensajeTramite){
        Session session = getSession();
        try {        	        	
        	Query query = session.createQuery("select m FROM MensajeTramite AS d join m.tramiteVersion as v join v.tramite as t where m.identificador = :mensaje and v.version = :version and t.identificador = :tramite");
        	query.setParameter("mensaje", idMensajeTramite);
            query.setParameter("version", new Integer(idVersion));
            query.setParameter("tramite", idTramite);            
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            MensajeTramite mensaje = (MensajeTramite) result.get(0);            
        	return mensaje;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarMensajeTramite(MensajeTramite obj,Long idTramiteVersion) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
                TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
                //tramiteVersion.addMensaje(obj);
                tramiteVersion.addMensaje(obj.getIdentificador(), obj );
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
     * @ejb.permission role-name="${role.operador}"
     */
    public Map listarMensajesTramite(Long idTramiteVersion) {
        Session session = getSession();
        try {       	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
            Hibernate.initialize(tramiteVersion.getMensajes());
            return tramiteVersion.getMensajes();   
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarMensajeTramite(Long id) {
        Session session = getSession();
        try {
            MensajeTramite mensaje = (MensajeTramite) session.load(MensajeTramite.class, id);
            mensaje.getTramiteVersion().removeMensaje(mensaje);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
