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
import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaPreregistroBackup;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistroBackup;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

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
     * @ejb.permission role-name="${role.todos}"
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
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAutenticada(Long id) {
        Session session = getSession();
        try {
        	// Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Comprobamos que accede el usuario o si es un delegado
        	Principal sp =this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Acceso solo permitido para autenticado");
        	}
    		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(entradaPreregistro.getNifRepresentante())){
    			// Si no es el usuario quien accede miramos si es un delegado
            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(entradaPreregistro.getNifRepresentante());
            	if (StringUtils.isEmpty(permisos)){
            		throw new Exception("El preregistro " + id + " no pertenece al usuario ni es delegado - usuario " + sp.getName());
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
     * @ejb.permission role-name="${role.todos}"
     */
    public EntradaPreregistro obtenerEntradaPreregistroAnonima(Long id,String idPersistencia) {
        Session session = getSession();
        try {
        	// Acceso solo anonimo
        	Principal sp = this.ctx.getCallerPrincipal();
        	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
        	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Solo se permite acceso anonimo");
        	}
        	// Cargamos entradaPreregistro        	
        	EntradaPreregistro entradaPreregistro = (EntradaPreregistro) session.load(EntradaPreregistro.class, id);
        	
        	// Control acceso
        	if (!entradaPreregistro.getIdPersistencia().equals(idPersistencia)){
        		// En caso de pertenecer a un expediente se debera controlar el acceso a traves del expediente
            	// ya que se podra acceder con otros idPersistencia de tramites asociados al expediente
            	// Buscamos si es un elemento de un expediente (en caso afirmativo se controlaria el acceso al expe)
            	ElementoExpediente ee = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO,id,idPersistencia);
            	if (ee == null){
            		throw new HibernateException("Acceso anonimo no permitido a entrada preregistro " + id + " - idPersistencia " + idPersistencia);
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
     * @ejb.permission role-name="${role.todos}"
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
	        	// Para anonimos, vale con el id de persistencia
	        	if (plgLogin.getMetodoAutenticacion(sp) == 'A') {
	        		if (entradaPreregistro.getNivelAutenticacion() != 'A'){
	        		throw new HibernateException("Acceso anonimo a entrada preregistro autenticada " + idPersistencia);
	        	}
	        	}else{
	        	// Para autenticados comprobamos si es el usuario o es un delegado	        	
	        		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(entradaPreregistro.getNifRepresentante())){
	        			// Si no es el usuario quien accede miramos si es un delegado
	                	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(entradaPreregistro.getNifRepresentante());
	                	if (StringUtils.isEmpty(permisos)){
	                		throw new Exception("Acceso no permitido a entrada preregistro " + idPersistencia + " no pertenece al usuario ni es delegado - usuario " + sp.getName());	                		
	                	}
	        		}	        		
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
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.registro}"
     */
    public Long grabarEntradaPreregistro(EntradaPreregistro obj) {        
    	Session session = getSession();
        try {        	
        	
        	// Control acceso: role registro salta check para actualizar la confirmación del preregistro
        	if (!this.ctx.isCallerInRole(roleRegistro) && !this.ctx.isCallerInRole(roleGestor)){
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
     * ejb.permission role-name="${role.todos}"
     
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
     * ejb.permission role-name="${role.todos}"
     
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
            .createQuery("FROM EntradaPreregistro AS m WHERE " +
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
     * @param tramitePersistenteBackup
     * @return
     */
    public void borrarEntradaPreregistroBackup( EntradaPreregistroBackup entradaPreregistroBackup )
    {
    	Session session = getSession();
    	try
    	{
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		if(entradaPreregistroBackup != null && entradaPreregistroBackup.getDocumentosBackup() != null){
    			for(Iterator it = entradaPreregistroBackup.getDocumentosBackup().iterator(); it.hasNext();){
    				DocumentoEntradaPreregistroBackup backup = ( DocumentoEntradaPreregistroBackup ) it.next();
    				ReferenciaRDS ref = new ReferenciaRDS();
    				ref.setCodigo(backup.getCodigoRDS());
    				ref.setClave(backup.getClaveRDS());
    				List usos = rds.listarUsos(ref);
    				for(int i=0;i<usos.size();i++){
    					rds.eliminarUso((UsoRDS)usos.get(i));
    				}
    			}
    			entradaPreregistroBackup.getDocumentosBackup().removeAll(entradaPreregistroBackup.getDocumentosBackup());
    		}
    		session.delete(entradaPreregistroBackup);
    	}
    	catch( Exception exc )
    	{
    		throw new EJBException( exc );
        } finally {
            close(session);
        }
    	
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarEntradaPreregistroBackup( Date fecha ) {
    	Session session = getSession();
        try {      
            Query query = session
            .createQuery("FROM EntradaPreregistroBackup AS m WHERE m.fechaCaducidad < :fecha")
            .setParameter("fecha", fecha );
            //query.setCacheable(true);
            List entradas = query.list();
            
            // Cargamos documentos
            for (Iterator it=entradas.iterator();it.hasNext();){
            	EntradaPreregistroBackup entradaPreregistroBackup = (EntradaPreregistroBackup) it.next();
            	Hibernate.initialize(entradaPreregistroBackup.getDocumentosBackup());
            }
            
            return entradas;
            
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
    	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.helpdesk}"
     */
    public List listarEntradaPreregistrosNoConfirmados( String modelo, char caducidad, Date fechaInicial, Date fechaFinal ) {

    	Session session = getSession();
        try {
        	String hql_from = "FROM EntradaPreregistro AS m ";
        	String hql_where = "WHERE m.fechaConfirmacion IS NULL AND m.fecha >= :fechaInicial AND m.fecha <= :fechaFinal ";
        	String hql_order = "ORDER BY m.tramite ";
        	
        	String hql_modelo;
        	
        	if ("0".equals(modelo)) {
        		hql_modelo = " AND :modelo = :modelo ";
        	} else {
        		hql_modelo = " AND m.tramite = :modelo ";
        	}
        	
        	String hql_caducidad;
        	
        	switch(caducidad){
        		case 'S' : 
        			hql_caducidad = " AND m.fechaCaducidad <= :fechaActual ";
        			break;
        		case 'N' : 
        			hql_caducidad = " AND m.fechaCaducidad > :fechaActual ";
        			break;
        		default: 
        			hql_caducidad = " AND :fechaActual = :fechaActual ";
        			break;
        	}
        	int size = hql_from.length() + hql_where.length() + hql_caducidad.length() + hql_modelo.length() + hql_order.length();
        	StringBuilder hql = new StringBuilder(size);
        	hql.append(hql_from);
        	hql.append(hql_where);
        	hql.append(hql_modelo);
        	hql.append(hql_caducidad);
        	hql.append(hql_order);
        	Query query = session.createQuery(hql.toString());
            query.setParameter("fechaInicial", fechaInicial );
            query.setParameter("fechaFinal", fechaFinal);
            query.setParameter("modelo", modelo);
            query.setParameter("fechaActual", new Date());
            
            List entradas = query.list();
            
            return entradas;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
  	
}
