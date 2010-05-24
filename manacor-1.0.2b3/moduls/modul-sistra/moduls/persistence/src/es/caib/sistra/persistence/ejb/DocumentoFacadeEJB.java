package es.caib.sistra.persistence.ejb;

import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.TramiteVersion;

/**
 * SessionBean para mantener y consultar Documento
 *
 * @ejb.bean
 *  name="sistra/persistence/DocumentoFacade"
 *  jndi-name="es.caib.sistra.persistence.DocumentoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class DocumentoFacadeEJB extends HibernateEJB {

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
    public Documento obtenerDocumento(Long id) {
        Session session = getSession();
        try {
        	// Cargamos documento        	
        	Documento documento = (Documento) session.load(Documento.class, id);
 
        	// Cargamos niveles
        	Hibernate.initialize(documento.getNiveles());
        	
            return documento;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /*
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"     
    public Documento obtenerDocumento(String idTramite,int idVersion,String idDocumento){
        Session session = getSession();
        try {        	        	
        	Query query = session.createQuery("select d FROM Documento AS d join d.tramiteVersion as v join v.tramite as t where d.identificador = :idDocumento and v.version = :version and t.identificador = :tramite");
        	query.setParameter("documento", idDocumento);
            query.setParameter("version", new Integer(idVersion));
            query.setParameter("tramite", idTramite);            
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            Documento documento = (Documento) result.get(0);            
        	
            // Cargamos niveles
        	Hibernate.initialize(documento.getNiveles());
            
            return documento;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    */
    
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
    public Long grabarDocumento(Documento obj,Long idTramiteVersion) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
                TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
                tramiteVersion.addDocumento(obj);
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
     */
    public Set listarDocumentos(Long idTramiteVersion) {
        Session session = getSession();
        try {       	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
            Hibernate.initialize(tramiteVersion.getDocumentos());
            return tramiteVersion.getDocumentos();   
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
    public void borrarDocumento(Long id) {
        Session session = getSession();
        try {
            Documento documento = (Documento) session.load(Documento.class, id);
            documento.getTramiteVersion().removeDocumento(documento);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
