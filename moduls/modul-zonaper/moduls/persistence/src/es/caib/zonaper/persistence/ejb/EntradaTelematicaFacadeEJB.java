package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

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
     * @ejb.permission role-name="${role.todos}"
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
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAutenticada(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	EntradaTelematica entradaTelematica = (EntradaTelematica) session.load(EntradaTelematica.class, id);
        	
        	// Comprobamos que accede el usuario o si es un delegado
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Acceso solo permitido para autenticado");
        	}
    		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(entradaTelematica.getNifRepresentante())){
    			// Si no es el usuario quien accede miramos si es un delegado
            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(entradaTelematica.getNifRepresentante());
            	if (StringUtils.isEmpty(permisos)){
            		throw new Exception("Acceso no permitido a entrada telematica " + id + " - usuario " + sp.getName());
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
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAnonima(Long id,String idPersistencia) {
        Session session = getSession();
        try {
        	// Acceso solo anonimo
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Solo se permite acceso anonimo");
        	}
        	
        	// Cargamos entradaTelematica        	
        	EntradaTelematica entradaTelematica = (EntradaTelematica) session.load(EntradaTelematica.class, id);
        	
        	// Control acceso: 
        	if (!entradaTelematica.getIdPersistencia().equals(idPersistencia)){
        		// En caso de pertenecer a un expediente se debera controlar el acceso a traves del expediente
            	// ya que se podra acceder con otros idPersistencia de tramites asociados al expediente
            	// Buscamos si es un elemento de un expediente (en caso afirmativo se controlaria el acceso al expe)
            	ElementoExpediente ee = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_ENTRADA_TELEMATICA,id,idPersistencia);
            	if (ee == null){
            		throw new HibernateException("Acceso anonimo no permitido a entrada telematica " + id + "- idPersistencia " + idPersistencia);
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
     * @ejb.permission role-name="${role.todos}"
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
		    	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
		    		if (entradaTelematica.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO ){
		    		throw new HibernateException("Acceso anonimo a entrada telematica autenticada " + idPersistencia);
		    	}
		    	}else{
		    		// Para autenticados comprobamos si es el usuario o es un delegado	        	
	        		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(entradaTelematica.getNifRepresentante())){
	        			// Si no es el usuario quien accede miramos si es un delegado
	                	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(entradaTelematica.getNifRepresentante());
	                	if (StringUtils.isEmpty(permisos)){
	                		throw new Exception("Acceso no permitido a entrada telematica " + idPersistencia + " no pertenece al usuario ni es delegado con permiso de presentar - usuario " + sp.getName());	                		
	                	}
	        		}		    		
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
     * @ejb.permission role-name="${role.todos}"
     */
    public Long grabarEntradaTelematica(EntradaTelematica obj) {        
    	Session session = getSession();
        try {     
        	// Control acceso 
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) != 'A'){
        		// Para autenticados comprobamos si es el usuario o es un delegado con permiso para presentar	        	
        		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(obj.getNifRepresentante())){
        			// Si no es el usuario quien accede miramos si es un delegado
                	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(obj.getNifRepresentante());
                	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
                		throw new Exception("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia()  + " no pertenece al usuario ni es delegado con permiso de presentar - usuario " + sp.getName());	                		
                	}
        		}	
        	}else{	        		
        		// Para anonimos vale con el id persistencia
        		if (obj.getNivelAutenticacion() != 'A'){
        			throw new HibernateException("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia() + " - usuario " + sp.getName());
        		}
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
            .createQuery("FROM EntradaTelematica AS m WHERE "+ 
            			((nivelAutenticacion != null && "A".equals(nivelAutenticacion)) ? " m.nifRepresentante like :nif " : " m.nifRepresentante = :nif ")+
            		     ((modelo != null) ? " and m.tramite = :modelo " : "")+
            		     " and  m.fecha >= :fechaInicial and m.fecha <= :fechaFinal "+
            		     ((nivelAutenticacion != null) ? " and m.nivelAutenticacion = :nivel" : "" ) +
            		     " ORDER BY m.fecha DESC");
            if(modelo != null) query.setParameter("modelo",modelo);
            if(nivelAutenticacion != null) query.setParameter("nivel",nivelAutenticacion);
            if(nivelAutenticacion != null && "A".equals(nivelAutenticacion)){
            	query.setParameter("nif","%"+nif+"%");
            }else{
            query.setParameter("nif",nif);
            }
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
