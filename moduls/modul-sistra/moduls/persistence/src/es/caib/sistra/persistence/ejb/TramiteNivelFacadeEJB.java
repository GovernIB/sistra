package es.caib.sistra.persistence.ejb;

import java.util.Iterator;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;

/**
 * SessionBean para mantener y consultar TramiteNivel
 *
 * @ejb.bean
 *  name="sistra/persistence/TramiteNivelFacade"
 *  jndi-name="es.caib.sistra.persistence.TramiteNivelFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class TramiteNivelFacadeEJB extends HibernateEJB {

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
    public TramiteNivel obtenerTramiteNivel(Long id) {
        Session session = getSession();
        try {
        	// Cargamos tramiteNivel        	
        	TramiteNivel tramiteNivel = (TramiteNivel) session.load(TramiteNivel.class, id);
        	
        	// Inicializamos Datos justificante de especificaciones
        	Hibernate.initialize(tramiteNivel.getEspecificaciones().getDatosJustificante());
        	
            return tramiteNivel;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
  
/*    
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
  
    
    public TramiteNivel obtenerTramiteNivel(String idTramite,int idVersion,char idNivelAutenticacion){
        Session session = getSession();
        try {        	        	
        	Query query = session.createQuery("select n FROM TramiteNivel AS n join n.tramiteVersion as v join v.tramite as t where n.nivelAutenticacion = :nivel and v.version = :version and t.identificador = :tramite");
        	query.setParameter("nivel", new Character(idNivelAutenticacion));
            query.setParameter("version", new Integer(idVersion));
            query.setParameter("tramite", idTramite);            
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            TramiteNivel tramiteNivel = (TramiteNivel) result.get(0);            
        	          
            return tramiteNivel;

        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
  */
    
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarTramiteNivel(TramiteNivel obj,Long idTramiteVersion) {        
    	Session session = getSession();
        try {        	        	        	
        	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);       	
        	        	
        	if (obj.getCodigo() == null) {
                
                // Comprobamos que tenga instancia de especificaciones
                if (obj.getEspecificaciones() == null){
                	EspecTramiteNivel esp = new EspecTramiteNivel();
                	obj.setEspecificaciones(esp);
                }
                
                tramiteVersion.addTramiteNivel(obj);
                session.flush();
            } else {
                session.update(obj);
            }                   
        	
        	// Comprobamos que no existan niveles duplicados        	
        	String niveles = "";
        	for (Iterator it = tramiteVersion.getNiveles().iterator();it.hasNext();){
        		String nivelTra = (String) ((TramiteNivel) it.next()).getNivelAutenticacion();        		
        		niveles = niveles + nivelTra;        		
        	}        	
        	if  (
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_ANONIMO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_ANONIMO) ||
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_CERTIFICADO) ||
        			niveles.indexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) != niveles.lastIndexOf(DocumentoNivel.AUTENTICACION_USUARIOPASSWORD) 
        		)
        	{
        		throw new HibernateException("No se pueden duplicar niveles de autenticación");
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
    public Set listarNivelesTramite(Long idTramiteVersion) {	
        Session session = getSession();
        try {       	
        	TramiteVersion tramiteVersion = (TramiteVersion) session.load(TramiteVersion.class, idTramiteVersion);
            Hibernate.initialize(tramiteVersion.getNiveles());
            return tramiteVersion.getNiveles();   
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
    public void borrarTramiteNivel(Long id) {
        Session session = getSession();
        try {
            TramiteNivel tramiteNivel = (TramiteNivel) session.load(TramiteNivel.class, id);
            tramiteNivel.getTramiteVersion().removeNivel(tramiteNivel);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}
