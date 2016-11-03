package es.caib.sistra.persistence.ejb;

import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.sistra.model.*;

/**
 * SessionBean para mantener y consultar DatoJustificante
 *
 * @ejb.bean
 *  name="sistra/persistence/DatoJustificanteFacade"
 *  jndi-name="es.caib.sistra.persistence.DatoJustificanteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class DatoJustificanteFacadeEJB extends HibernateEJB {

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
    public DatoJustificante obtenerDatoJustificante(Long id) {
        Session session = getSession();
        try {
        	// Cargamos datoJustificante        	
        	DatoJustificante datoJustificante = (DatoJustificante) session.load(DatoJustificante.class, id);
        	return datoJustificante;
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
    public Long grabarDatoJustificante(DatoJustificante obj,Long idEspecTramiteNivel) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
                EspecTramiteNivel espec = (EspecTramiteNivel) session.load(EspecTramiteNivel.class, idEspecTramiteNivel);
                espec.addDatoJustificante(obj);
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
    public List listarDatosJustificante(Long idEspecTramiteNivel) {
        Session session = getSession();
        try {       	
        	EspecTramiteNivel espec = (EspecTramiteNivel) session.load(EspecTramiteNivel.class, idEspecTramiteNivel);
            Hibernate.initialize(espec.getDatosJustificante());
            return espec.getDatosJustificante();   
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
    public void borrarDatoJustificante(Long id) {
        Session session = getSession();
        try {
            DatoJustificante datoJustificante = (DatoJustificante) session.load(DatoJustificante.class, id);
            datoJustificante.getEspecTramiteNivel().removeDatoJustificante(datoJustificante);
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
    public void upDatoJustificante(Long id) {
        Session session = getSession();
        try {
            DatoJustificante datoJustificante = (DatoJustificante) session.load(DatoJustificante.class, id);
            datoJustificante.getEspecTramiteNivel().upOrdenDatoJustificante(datoJustificante);
            session.flush();
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
    public void downDatoJustificante(Long id) {
        Session session = getSession();
        try {
            DatoJustificante datoJustificante = (DatoJustificante) session.load(DatoJustificante.class, id);
            datoJustificante.getEspecTramiteNivel().downOrdenDatoJustificante(datoJustificante);
            session.flush();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
