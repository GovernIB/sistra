package es.caib.zonaper.persistence.ejb;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.BeanUtils;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaPreregistroBackup;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistroBackup;

/**
 * SessionBean para mantener y consultar EntradaPreregistro
 *
 * @ejb.bean
 *  name="zonaper/persistence/EntradaPreregistroFacade"
 *  jndi-name="es.caib.zonaper.persistence.EntradaPreregistroFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleHelpDesk" type="java.lang.String" value="${role.helpdesk}"
 * @ejb.env-entry name="roleRegistro" type="java.lang.String" value="${role.registro}"
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 * 
 */
public abstract class EntradaPreregistroFacadeEJB extends HibernateEJB {

	private String roleHelpDesk;
	private String roleRegistro;
	private String roleGestor;
	
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
			roleRegistro = (( String ) initialContext.lookup( "java:comp/env/roleRegistro" ));		
			roleGestor = (( String ) initialContext.lookup( "java:comp/env/roleGestor" ));
		}catch(Exception ex){
			log.error(ex);
		}
	}
	  
    /**
     * Obtener entrada preregistro desde modulo de confirmacion
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroReg(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
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
    public EntradaPreregistro obtenerEntradaPreregistroAutenticada(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Control acceso
        	Principal sp =this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) == 'A' || !sp.getName().equals(entradaPreregistro.getUsuario())){
        		throw new HibernateException("Acceso no permitido a entrada preregistro autenticada " + id + " - usuario " + sp.getName());
        	}
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
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
    public EntradaPreregistro obtenerEntradaPreregistroAnonima(Long id,String idPersistencia) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Control acceso
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) != 'A' || !entradaPreregistro.getIdPersistencia().equals(idPersistencia)){
        		throw new HibernateException("Acceso no permitido a entrada preregistro anonima " + idPersistencia + " - usuario " + sp.getName());
        	}
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
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
    public EntradaPreregistro obtenerEntradaPreregistro(Long id) {
        Session session = getSession();
        try {
        	// 	Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
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
    public EntradaPreregistro obtenerEntradaPreregistro(String idPersistencia) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.idPersistencia = :id")
            .setParameter("id",idPersistencia);
            //query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            EntradaPreregistro entradaPreregistro = (EntradaPreregistro)  query.uniqueResult();             
            
            // Control acceso  (role helpdesk salta comprobacion)
            if (!this.ctx.isCallerInRole(roleHelpDesk)){
	        	Principal sp = this.ctx.getCallerPrincipal();	  
	        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
	        	if (plgLogin.getMetodoAutenticacion(sp) == 'A' && entradaPreregistro.getNivelAutenticacion() != 'A'){
	        		throw new HibernateException("Acceso anonimo a entrada preregistro autenticada " + idPersistencia);
	        	}
	        	if (plgLogin.getMetodoAutenticacion(sp) != 'A' &&  !sp.getName().equals(entradaPreregistro.getUsuario())){
	        		throw new HibernateException("Acceso no permitido a entrada preregistro " + idPersistencia + " - usuario " + sp.getName());
	        	}
            }
            
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
        } catch (Exception he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroPorNumero(String numeroPreregistro) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.numeroPreregistro = :numeroPreregistro")
            .setParameter("numeroPreregistro",numeroPreregistro);
        	//query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            EntradaPreregistro entradaPreregistro = (EntradaPreregistro)  query.uniqueResult();             
            
        	// Cargamos documentos
        	Hibernate.initialize(entradaPreregistro.getDocumentos());        	
            return entradaPreregistro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
 	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.registro}"
     */
    public Long grabarEntradaPreregistro(EntradaPreregistro obj) {        
    	Session session = getSession();
        try {        	
        	
        	// Control acceso: role registro salta check para actualizar la confirmación del preregistro
        	if (!this.ctx.isCallerInRole(roleRegistro) && !this.ctx.isCallerInRole(roleGestor)){
        		Principal sp = this.ctx.getCallerPrincipal();
        		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
	        	if (plgLogin.getMetodoAutenticacion(sp) != 'A' && !sp.getName().equals(obj.getUsuario())){
	        		throw new HibernateException("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia() + " - usuario " + sp.getName());
	        	}
	        	if (plgLogin.getMetodoAutenticacion(sp) == 'A' && obj.getNivelAutenticacion() != 'A'){
	        		throw new HibernateException("Acceso no permitido a entrada preregistro " + obj.getIdPersistencia() + " - usuario " + sp.getName());
	        	}
        	}
        	
        	// updateamos
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
     * Recupera lista de trámites de preregistro en los que el usuario los ha registrado
     * o bien aparece como su nif como representado
     * 
     * ejb.interface-method
     * ejb.permission role-name="${role.user}"
     
    public List listarEntradaPreregistrosUsuario() {
        Session session = getSession();
        try {     
        	String usua = this.ctx.getCallerPrincipal().getName();
        	if (usua == null) return null;
        	
        	// Obtenemos datos usuario PAD
        	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
        	PersonaPAD pers = padAplic.obtenerDatosPersonaPADporUsuario(usua);         	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.usuario = :usuario or (m.nifRepresentado is not null and m.nifRepresentado = :nifRepresentado) ORDER BY m.fecha DESC")
            .setParameter("usuario",usua)
            .setParameter("nifRepresentado",pers.getNif());
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) it.next();
            	Hibernate.initialize(entradaPreregistro.getDocumentos());
            }
            
            return tramites;
            
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    */
    
    /**
     * ejb.interface-method
     * ejb.permission role-name="${role.user}"
     
    public List listarEntradaPreregistrosUsuarioNoConfirmados(String usua) 
    {
        Session session = getSession();
        try {     
        	
        	if (usua == null) return null;
        	
        	//  Obtenemos datos usuario PAD
        	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
        	PersonaPAD pers = padAplic.obtenerDatosPersonaPADporUsuario(usua);         	
        	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE (m.usuario = :usuario  or (m.nifRepresentado is not null and m.nifRepresentado = :nifRepresentado)) and m.fechaConfirmacion is null ORDER BY m.fecha DESC")
            .setParameter("usuario",usua)
            .setParameter("nifRepresentado",pers.getNif());;
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) it.next();
            	Hibernate.initialize(entradaPreregistro.getDocumentos());
            }
            
            return tramites;
            
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    */
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarEntradaPreregistrosCaducados( Date fecha ) 
    {
        Session session = getSession();
        try {     
        	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.fechaCaducidad < :fecha and m.fechaConfirmacion is null ORDER BY m.fecha ASC")
            .setParameter("fecha", fecha );
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) it.next();
            	Hibernate.initialize(entradaPreregistro.getDocumentos());
            }
            
            return tramites;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistrosNifModelo(String nif, String modelo, Date fechaInicial, Date fechaFinal, String nivelAutenticacion) {
        Session session = getSession();
        try {     
        	
        	
            Query query = session
            .createQuery("FROM EntradaPreregistro AS m WHERE m.nifRepresentante = :nif " +
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
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void borrarEntradaPreregistro(Long id) {
        Session session = getSession();
        try {
            EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
            session.delete(entradaPreregistro);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    	
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @param entradaPreregistro
     */
    public void backupEntradaPreregistro( EntradaPreregistro entradaPreregistro )
    {
    	Session session = getSession();
    	try
    	{
	    	EntradaPreregistroBackup entradaBackup = new EntradaPreregistroBackup();
	    	BeanUtils.copyProperties( entradaBackup, entradaPreregistro );
			Set setDocumentos = entradaPreregistro.getDocumentos(); 
			for ( Iterator it = setDocumentos.iterator(); it.hasNext(); )
			{
				DocumentoEntradaPreregistro documento = ( DocumentoEntradaPreregistro ) it.next();
				DocumentoEntradaPreregistroBackup docBackup = new DocumentoEntradaPreregistroBackup();
				BeanUtils.copyProperties( docBackup, documento );
				entradaBackup.addDocumentoBackup( docBackup );
			}
			session.save( entradaBackup );
    	}
    	catch( Exception exc )
    	{
    		throw new EJBException( exc );
    	}
    	finally
    	{
    		close( session );
    	}
    }
    	
    

    
    
  	
}
