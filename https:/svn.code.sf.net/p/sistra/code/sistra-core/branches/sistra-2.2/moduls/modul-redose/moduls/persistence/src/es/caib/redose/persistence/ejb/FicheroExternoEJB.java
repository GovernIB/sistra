package es.caib.redose.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Documento;
import es.caib.redose.model.FicheroExterno;
import es.caib.redose.model.Ubicacion;

/**
 * SessionBean para mantener la transaccionalidad con los ficheros externos.
 *
 * @ejb.bean
 *  name="redose/persistence/FicheroExternoFacade"
 *  jndi-name="es.caib.redose.persistence.FicheroExternoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class FicheroExternoEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	
	/**
	 * Obtiene fichero externo por referencia.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"     
     */
	public FicheroExterno obtenerFicheroExterno(String referenciaExterna) {
		Session session = getSession();
        try {        	
        	FicheroExterno ficheroExterno = (FicheroExterno) session.load(FicheroExterno.class, referenciaExterna);
        	return ficheroExterno;       	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Obtiene lista ficheros documento.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public List obtenerListaFicherosExterno(Long id) {
        Session session = getSession();
        try {        	
        	Query query = session.createQuery("FROM FicheroExterno AS m WHERE m.idDocumento = :idDocumento order by m.fechaReferencia DESC");
        	query.setParameter("idDocumento", id);
            List result = query.list();
            return result; 
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.operador}"
     */
    public void grabarFicheroExterno(FicheroExterno ficheroExterno){
        Session session = getSession();
        try {
        	session.save(ficheroExterno);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
	 * Obtiene lista ficheros documento para borrar.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List obtenerListaFicherosExternoBorrar() {
        Session session = getSession();
        try {        	
        	Query query = session.createQuery("FROM FicheroExterno AS m order by m.fechaReferencia DESC where m.borrar = 'S'");
            List result = query.list();
            return result; 
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
	 * Marca fichero para borrar.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
    public void marcarBorrarFicheroExterno(String referenciaExterna) {
        Session session = getSession();
        try {        	
        	FicheroExterno ficheroExterno = (FicheroExterno) session.load(FicheroExterno.class, referenciaExterna);
        	ficheroExterno.setBorrar("S");
        	session.update(ficheroExterno);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
	 * Elimina fichero externo.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void eliminarFicheroExterno(String referenciaExterna) {
        Session session = getSession();
        try {        	
        	FicheroExterno ficheroExterno = (FicheroExterno) session.load(FicheroExterno.class, referenciaExterna);
        	if ("N".equals(ficheroExterno.getBorrar())) {
        		throw new HibernateException("Se esta intentando borrar un fichero externo que no esta marcado para borrar");
        	}
        	session.delete(ficheroExterno);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
	 * Elimina fichero externo.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public Ubicacion obtenerUbicacionFicheroExterno(String referenciaExterna) {
    	Session session = getSession();
        try {        	
        	FicheroExterno ficheroExterno = (FicheroExterno) session.load(FicheroExterno.class, referenciaExterna);
        	Documento documento = (Documento) session.load(Documento.class, ficheroExterno.getIdDocumento());
        	return documento.getUbicacion();        	       
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
