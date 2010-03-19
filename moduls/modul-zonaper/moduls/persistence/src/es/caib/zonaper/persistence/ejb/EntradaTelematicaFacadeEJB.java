package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.EntradaTelematica;

/**
 * SessionBean para mantener y consultar EntradaTelematica
 *
 * @ejb.bean
 *  name="zonaper/persistence/EntradaTelematicaFacade"
 *  jndi-name="es.caib.zonaper.persistence.EntradaTelematicaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleHelpDesk" type="java.lang.String" value="${role.helpdesk}"
 */
public abstract class EntradaTelematicaFacadeEJB extends HibernateEJB {

	private String roleHelpDesk;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
		try{
			InitialContext initialContext = new InitialContext();			 
			roleHelpDesk = (( String ) initialContext.lookup( "java:comp/env/roleHelpDesk" ));			
		}catch(Exception ex){
			log.error(ex);
		}
	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAutenticada(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	EntradaTelematica entradaTelematica = (EntradaTelematica) session.load(EntradaTelematica.class, id);
        	
        	// Control acceso
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO || !sp.getName().equals(entradaTelematica.getUsuario())){
        		throw new HibernateException("Acceso no permitido a entrada telematica " + id + " - usuario " + sp.getName());
        	}
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaTelematica.getDocumentos());        	
            return entradaTelematica;
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAnonima(Long id,String idPersistencia) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	EntradaTelematica entradaTelematica = (EntradaTelematica) session.load(EntradaTelematica.class, id);
        	
        	// Control acceso
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO ||  !entradaTelematica.getIdPersistencia().equals(idPersistencia)){
        		throw new HibernateException("Acceso anonimo no permitido a entrada telematica " + id + " - usuario " + sp.getName());
        	}
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaTelematica.getDocumentos());        	
            return entradaTelematica;
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public EntradaTelematica obtenerEntradaTelematica(String idPersistencia) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	Query query = session
            .createQuery("FROM EntradaTelematica AS m WHERE m.idPersistencia = :id")
            .setParameter("id",idPersistencia);
            //query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            EntradaTelematica entradaTelematica = (EntradaTelematica)  query.uniqueResult(); 
                        
            // Control acceso (role helpdesk salta comprobacion)
            //  - En caso de estar autenticado puede acceder el usuario iniciador o el que tiene el flujo
            if (!this.ctx.isCallerInRole(roleHelpDesk)){
		    	Principal sp = this.ctx.getCallerPrincipal();
		    	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		    	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO 
		    			&& entradaTelematica.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO ){
		    		throw new HibernateException("Acceso anonimo a entrada telematica autenticada " + idPersistencia);
		    	}
		    	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO && !sp.getName().equals(entradaTelematica.getUsuario())){
		    		throw new HibernateException("Acceso no permitido a entrada telematica " + idPersistencia + " - usuario " + sp.getName());
		    	}
            }
            
        	// Cargamos documentos
        	Hibernate.initialize(entradaTelematica.getDocumentos());        	
            return entradaTelematica;
        } catch (Exception he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaTelematica obtenerEntradaTelematica(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	EntradaTelematica entradaTelematica = (EntradaTelematica) session.load(EntradaTelematica.class, id);                       
        	// Cargamos documentos
        	Hibernate.initialize(entradaTelematica.getDocumentos());        	
            return entradaTelematica;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaTelematica obtenerEntradaTelematicaPorNumero(String numeroRegistro) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	Query query = session
            .createQuery("FROM EntradaTelematica AS m WHERE m.numeroRegistro = :numeroRegistro")
            .setParameter("numeroRegistro",numeroRegistro);
            //query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            EntradaTelematica entradaTelematica = (EntradaTelematica)  query.uniqueResult(); 
                        
        	// Cargamos documentos
        	Hibernate.initialize(entradaTelematica.getDocumentos());        	
            return entradaTelematica;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
 	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public Long grabarEntradaTelematica(EntradaTelematica obj) {        
    	Session session = getSession();
        try {     
        	// Control acceso 
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO 
        			&& obj.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Acceso anonimo a entrada telematica autenticada " + obj.getIdPersistencia());
        	}
        	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO 
        			&&  !sp.getName().equals(obj.getUsuario())){
        		throw new HibernateException("Acceso no permitido a entrada telematica " + obj.getIdPersistencia() + " - usuario " + sp.getName());
        	}
        	
        	// Updateamos
        	if (obj.getCodigo() == null){
        		session.save(obj);
        	}else{        		
        		session.update(obj);
        	}
        	                    	
            return obj.getCodigo();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
   
    public List listarEntradaTelematicasUsuario(String usua) {
        Session session = getSession();
        try {     
        	
        	if (usua == null) return null;
        	
            Query query = session
            .createQuery("FROM EntradaTelematica AS m WHERE m.usuario = :usuario ORDER BY m.fecha DESC")
            .setParameter("usuario",usua);
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	EntradaTelematica entradaTelematica = (EntradaTelematica) it.next();
            	Hibernate.initialize(entradaTelematica.getDocumentos());
            }
            
            return tramites;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
   */
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaTelematicasNifModelo(String nif, String modelo, Date fechaInicial, Date fechaFinal, String nivelAutenticacion) {
        Session session = getSession();
        try {     
        	
        	
            Query query = session
            .createQuery("FROM EntradaTelematica AS m WHERE m.nifRepresentante = :nif " +
            		     ((modelo != null) ? " and m.tramite = :modelo " : "")+
            		     " and  m.fecha >= :fechaInicial and m.fecha <= :fechaFinal "+
            		     ((nivelAutenticacion != null) ? " and m.nivelAutenticacion = :nivel" : "" ) +
            		     " ORDER BY m.fecha DESC");
            if(modelo != null) query.setParameter("modelo",modelo);
            if(nivelAutenticacion != null) query.setParameter("nivel",nivelAutenticacion);
            query.setParameter("nif",nif);
            query.setParameter("fechaInicial",fechaInicial);
            query.setParameter("fechaFinal",fechaFinal);
            
            return query.list();
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
   
}
