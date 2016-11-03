package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

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
 */
public abstract class EntradaTelematicaFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();	
	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAutenticada(Long id) {
    	// Cargamos entradaTelematica        	
    	EntradaTelematica entradaTelematica = this.recuperarEntradaTelematicaPorCodigo(id);
    	
    	// Control acceso expediente
        controlAccesoAutenticadoExpediente(entradaTelematica); 
        
        return entradaTelematica;
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAutenticada(String idPersistencia) {
    	// Cargamos entradaTelematica        	
    	EntradaTelematica entradaTelematica = this.recuperarEntradaTelematicaPorIdPersistencia(idPersistencia);
    	
    	// Control acceso expediente
        controlAccesoAutenticadoExpediente(entradaTelematica); 
        
        return entradaTelematica;
    }

	/**
     * Obtiene entrada de forma anonima
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAnonima(Long id,String claveAcceso) {
    	
    	// Cargamos entradaTelematica        	
    	EntradaTelematica entradaTelematica = this.recuperarEntradaTelematicaPorCodigo(id);
    	
    	// Control acceso expediente 
    	controlAccesoAnonimoExpediente(claveAcceso, entradaTelematica);
     
        return entradaTelematica;
    }

	
    
    /**
     * Helpdesk: obtiene entrada telematica por id persistencia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaTelematica obtenerEntradaTelematica(String idPersistencia) {
    	EntradaTelematica entradaTelematica = recuperarEntradaTelematicaPorIdPersistencia(idPersistencia);   
    	return entradaTelematica;
    }
    
    /**
     * Comprueba si existe entrada con un id persistencia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public boolean existeEntradaTelematica(String idPersistencia) {
    	EntradaTelematica entradaTelematica = recuperarEntradaTelematicaPorIdPersistencia(idPersistencia);   
    	return (entradaTelematica != null);
    }
       
    /**
     * Busca entrada telematica anonima por su id persistencia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaTelematica obtenerEntradaTelematicaAnonima(String idPersistencia) {
    	EntradaTelematica entradaTelematica = recuperarEntradaTelematicaPorIdPersistencia(idPersistencia);   
        if (entradaTelematica != null && entradaTelematica.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO) {
        	entradaTelematica = null;
        }
        return entradaTelematica;        	       
    }
    
    /**
     * Obtiene entrada telematica por id (para gestores y role auto).
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaTelematica obtenerEntradaTelematica(Long id) {
        return recuperarEntradaTelematicaPorCodigo(id);
    }

	
    
    
    /**
     * Obtiene entrada telematica por numero registro
     * 
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
 	 * Guarda nueva entrada telematica
 	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public Long grabarNuevaEntradaTelematica(EntradaTelematica obj) {        
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
                		throw new Exception("Acceso no permitido a entrada telematica " + obj.getIdPersistencia()  + " no pertenece al usuario ni es delegado con permiso de presentar - usuario " + sp.getName());	                		
                	}
        		}	
        	}else{	        		
        		// Para anonimos vale con el id persistencia
        		if (obj.getNivelAutenticacion() != 'A'){
        			throw new HibernateException("Acceso no permitido a entrada telematica " + obj.getIdPersistencia() + " - usuario " + sp.getName());
        		}
        	}
        	
        	// Guardamos nueva entrada
        	if (obj.getCodigo() == null){
        		session.save(obj);
        	}else{        		
        		throw new Exception("No se permite modificar entrada existente");
        	}
        	                    	
            return obj.getCodigo();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     *  Helpdesk: lista entradas por nif y tipo tramite.
     * 
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
   
    
    // ---------------------------------------------------------------------------------------------
    // 	FUNCIONES PRIVADAS
    // ---------------------------------------------------------------------------------------------
    
    private EntradaTelematica recuperarEntradaTelematicaPorIdPersistencia(
			String idPersistencia) {
		EntradaTelematica entradaTelematica = null;
    	Session session = getSession();
        try {
        	// Cargamos entradaTelematica        	
        	Query query = session
            .createQuery("FROM EntradaTelematica AS m WHERE m.idPersistencia = :id")
            .setParameter("id",idPersistencia);
            //query.setCacheable(true);
            if (!query.list().isEmpty()){            	
            	entradaTelematica = (EntradaTelematica)  query.uniqueResult();                        
            	// Cargamos documentos
            	Hibernate.initialize(entradaTelematica.getDocumentos());
            }
        } catch (Exception he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
		return entradaTelematica;
	}    
    
    private EntradaTelematica recuperarEntradaTelematicaPorCodigo(Long id) {
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
    
    private void controlAccesoAnonimoExpediente(String claveAcceso,
			EntradaTelematica entradaTelematica) {
		try {
    		if (entradaTelematica != null) {
	        	Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_ENTRADA_TELEMATICA, entradaTelematica.getCodigo());
	        	if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(idExpediente, claveAcceso)) {
	        		throw new Exception("No tiene acceso a entrada telematica");
	        	}
    		}
        } catch (Exception he) {
            throw new EJBException(he);
        }
	}
    
    private void controlAccesoAutenticadoExpediente(
			EntradaTelematica entradaTelematica) {
		try {
        	if (entradaTelematica != null) { 
	        	Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_ENTRADA_TELEMATICA, entradaTelematica.getCodigo());
	        	if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(idExpediente)) {
	        		throw new Exception("No tiene acceso a entrada telematica");
	        	}       	
        	}            
        } catch (Exception he) {
            throw new EJBException(he);
        }
	}
    
}
