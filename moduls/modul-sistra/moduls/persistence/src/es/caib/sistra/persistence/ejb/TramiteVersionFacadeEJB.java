package es.caib.sistra.persistence.ejb;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.*;

/**
 * SessionBean para mantener y consultar TramiteVersion
 *
 * @ejb.bean
 *  name="sistra/persistence/TramiteVersionFacade"
 *  jndi-name="es.caib.sistra.persistence.TramiteVersionFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class TramiteVersionFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public TramiteVersion obtenerTramiteVersion(Long id) {
        Session session = getSession();
        try {
        	// Cargamos tramiteVersion        	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, id);
        	
        	// Cargamos mensajes,niveles y documentos
        	Hibernate.initialize(tramiteVersion.getNiveles());
        	Hibernate.initialize(tramiteVersion.getDocumentos());
        	Hibernate.initialize(tramiteVersion.getMensajes());
        	
        	// Inicializamos Datos justificante de especificaciones
        	Hibernate.initialize(tramiteVersion.getEspecificaciones().getDatosJustificante());

        	
            return tramiteVersion;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public TramiteVersion obtenerTramiteVersion(String idTramite,int idVersion){
        Session session = getSession();
        try {        	        	
        	Query query = session.createQuery("select v FROM TramiteVersion AS v join v.tramite as m where v.version = :version and m.identificador = :tramite");
            query.setParameter("version", new Integer(idVersion));
            query.setParameter("tramite", idTramite);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            TramiteVersion tramiteVersion = (TramiteVersion) result.get(0);            
        	
            // Cargamos mensajes,niveles y documentos
        	Hibernate.initialize(tramiteVersion.getNiveles());
        	Hibernate.initialize(tramiteVersion.getDocumentos());
        	Hibernate.initialize(tramiteVersion.getMensajes());
            
        	// Inicializamos Datos justificante de especificaciones
        	Hibernate.initialize(tramiteVersion.getEspecificaciones().getDatosJustificante());
        	
            return tramiteVersion;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public TramiteVersion obtenerTramiteVersionCompleto(String idTramite,int idVersion){
        Session session = getSession();
        try {        	        	
        	Query query = session.createQuery("select v FROM TramiteVersion AS v join v.tramite as m where v.version = :version and m.identificador = :tramite");
            query.setParameter("version", new Integer(idVersion));
            query.setParameter("tramite", idTramite);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            TramiteVersion tramiteVersion = (TramiteVersion) result.get(0);            
        	         
            // Cargamos mensajes,niveles y documentos
        	Hibernate.initialize(tramiteVersion.getNiveles());
        	Hibernate.initialize(tramiteVersion.getDocumentos());
        	Hibernate.initialize(tramiteVersion.getMensajes());
        	
        	// Inicializamos datos justificante especificacion para version
        	Hibernate.initialize(tramiteVersion.getEspecificaciones().getDatosJustificante());
        	
        	// Para cada nivel inicializamos datos justificante especificacion
        	for (Iterator it = tramiteVersion.getNiveles().iterator();it.hasNext();){
        		TramiteNivel tn  = (TramiteNivel) it.next();         		
        		Hibernate.initialize(tn.getEspecificaciones().getDatosJustificante());
        	}
        	
        	// Para cada documento inicializamos niveles
        	for (Iterator it = tramiteVersion.getDocumentos().iterator();it.hasNext();){
        		Documento d = (Documento) it.next();
        		Hibernate.initialize(d.getNiveles());
        	}
        	
        	return tramiteVersion;

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
    public Long grabarTramiteVersion(TramiteVersion obj,Long idTramite) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null) {
                Tramite tramite = (Tramite) session.load(Tramite.class, idTramite);
                
                // Comprobamos que tenga instancia de especificaciones
                if (obj.getEspecificaciones() == null){
                	EspecTramiteNivel esp = new EspecTramiteNivel();
                	esp.setHabilitarNotificacionTelematica("N");
                	esp.setHabilitarAlertasTramitacion("N");
                	esp.setOcultarClaveTramitacionJustif("N");
                	esp.setOcultarNifNombreJustif("N");
                	obj.setEspecificaciones(esp);
                }
                
                tramite.addVersion(obj);
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
    public void bloquearTramiteVersion(Long idTramiteVersion,String bloquear,String usuario) {        
    	Session session = getSession();
        try {        	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
            
        	// Comprobamos que si bloqueamos no este previamente bloqueado
        	if (bloquear.equals("S") && tramiteVersion.getBloqueadoModificacion().equals(bloquear)){
        		return; // No dejamos bloquear
        	}
        	        	
        	// Bloqueo: establecemos bloqueo e indicamos usuario
        	if (bloquear.equals("S")){
        		// Si bloqueamos debe especificarse el usuario
            	if (StringUtils.isEmpty(usuario)){
            		return; // No dejamos bloquear
            	}
        		tramiteVersion.setBloqueadoModificacion(bloquear);
        		tramiteVersion.setBloqueadoModificacionPor(usuario);
        	}
        	
        	// Desbloqueo: quitamos bloqueo y reseteamos usuario
        	if (bloquear.equals("N")){
        		tramiteVersion.setBloqueadoModificacion(bloquear);
        		tramiteVersion.setBloqueadoModificacionPor(null);
        	}
        	
        	// Realizamos update
        	session.update(tramiteVersion);
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
    public Set listarTramiteVersiones(Long idTramite) {
        Session session = getSession();
        try {       	
        	Tramite tramite = (Tramite) session.load(Tramite.class, idTramite);
            Hibernate.initialize(tramite.getVersiones());
            return tramite.getVersiones();   
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
    public void borrarTramiteVersion(Long id) {
        Session session = getSession();
        try {
            TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, id);
            tramiteVersion.getTramite().removeVersion(tramiteVersion);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
