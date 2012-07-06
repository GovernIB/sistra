package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.NifCif;
import es.caib.xml.ConstantesXML;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.ParametrosSubsanacion;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.IndiceElementoDelegate;
import es.caib.zonaper.persistence.util.ConfigurationUtil;
import es.caib.zonaper.persistence.util.GeneradorId;

/**
 * SessionBean para mantener y consultar NotificacionTelematica
 *
 * @ejb.bean
 *  name="zonaper/persistence/NotificacionTelematicaFacade"
 *  jndi-name="es.caib.zonaper.persistence.NotificacionTelematicaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class NotificacionTelematicaFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public NotificacionTelematica obtenerNotificacionTelematicaAuto(Long id) {
        Session session = getSession();
        try {
        	
        	// Cargamos notificacionTelematica        	
        	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, id);
        	        
        	// Cargamos documentos
        	Hibernate.initialize(notificacionTelematica.getDocumentos());        	
            return notificacionTelematica;
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
    public NotificacionTelematica obtenerNotificacionTelematicaAutenticada(Long id) {
        Session session = getSession();
        
        NotificacionTelematica notificacionTelematica = null;
        try {
        	
        	// Cargamos notificacionTelematica        	
        	notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, id);
        	
        	
        	// Comprobamos que accede el usuario o si es un delegado
        	Principal sp =this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
        		throw new HibernateException("Acceso solo permitido para autenticado");
        	}
    		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(notificacionTelematica.getNifRepresentante())){
    			// Si no es el usuario quien accede miramos si es un delegado
            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(notificacionTelematica.getNifRepresentante());
            	if (StringUtils.isEmpty(permisos)){
            		throw new Exception("Acceso no permitido a notificacion telematica " + id + " - usuario " + sp.getName());
            	}
    		}
        	        	
        	// Cargamos documentos
    		Hibernate.initialize(notificacionTelematica.getDocumentos());
        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
        // En caso de que no este firmado el acuse no devolvemos ficheros asociados
        try{
	    	if (notificacionTelematica.getFechaAcuse() == null){
	    		return notificacionSinAbrir(notificacionTelematica);
	    	}else{
	    		return notificacionTelematica;
	    	}
        }catch (Exception ex){
        	throw new EJBException(ex);
        }
        
        
    }
    
    /**
     * Devuelve notificacion con informaci贸n exclusiva de aviso
     * @param notificacionTelematica
     * @return
     */
    private NotificacionTelematica notificacionSinAbrir(NotificacionTelematica notificacionTelematica) throws Exception {
		
    	NotificacionTelematica not = new NotificacionTelematica();
    	BeanUtils.copyProperties(not,notificacionTelematica);

    	// Eliminamos referencia rds a oficio
    	not.setCodigoRdsOficio(0);
    	not.setClaveRdsOficio(null);
    	
    	// Eliminamos documentos
    	not.getDocumentos().clear();
    	
    	// Eliminamos tramite de subsanacion
    	not.setTramiteSubsanacionDescripcion(null);
    	not.setTramiteSubsanacionIdentificador(null);
    	not.setTramiteSubsanacionVersion(null);
    	not.setTramiteSubsanacionParametros(null);
    	
		return not;
	}


	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public NotificacionTelematica obtenerNotificacionTelematicaAnonima(Long id,String idPersistencia) {
    	Session session = getSession();
    	NotificacionTelematica notificacionTelematica = null;
        try {
        	
        	// Cargamos notificacionTelematica        	
        	notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, id);
        	
        	// Cargamos documentos
        	Hibernate.initialize(notificacionTelematica.getDocumentos());        	
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
        
        // En caso de que no este firmado el acuse no devolvemos ficheros asociados
        try{
	    	if (notificacionTelematica.getFechaAcuse() == null){
	    		return notificacionSinAbrir(notificacionTelematica);
	    	}else{
	    		return notificacionTelematica;
	    	}
        }catch (Exception ex){
        	throw new EJBException(ex);
        }
        
    }
    
    /**
     * @ejb.interface-method     
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public NotificacionTelematica obtenerNotificacionTelematica(String numeroRegistro) {
        Session session = getSession();
        try {
        	// Cargamos notificacionTelematica        	
        	Query query = session
            .createQuery("FROM NotificacionTelematica AS m WHERE m.numeroRegistro = :numeroRegistro")
            .setParameter("numeroRegistro",numeroRegistro);
            //query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe tr谩mite con id " + id);
            }
            NotificacionTelematica notificacionTelematica = (NotificacionTelematica)  query.uniqueResult();
                        
        	// Cargamos documentos
        	Hibernate.initialize(notificacionTelematica.getDocumentos());        	
            return notificacionTelematica;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }        
    
   
    
 	/**
     * @ejb.interface-method
     *  @ejb.permission role-name="${role.gestor}"
     *  @ejb.permission role-name="${role.auto}"
     */
    public Long grabarNotificacionTelematica(NotificacionTelematica obj) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null){
        		session.save(obj);
        	}else{
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
     * @ejb.permission role-name="${role.todos}"
     */
    public List listarNotificacionesTelematicasUsuario() {
        Session session = getSession();
        try {     
        	String usua = this.ctx.getCallerPrincipal().getName();
        	if (usua == null) return null;
        	
            Query query = session
            .createQuery("FROM NotificacionTelematica AS m WHERE m.usuarioSeycon = :usuario ORDER BY m.fechaRegistro DESC")
            .setParameter("usuario",usua);
            //query.setCacheable(true);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) it.next();
            	Hibernate.initialize(notificacionTelematica.getDocumentos());
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
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarNotificacionesTelematicasFueraPlazo() {
        Session session = getSession();
        try {     
        	
        	Date ahora = new Date();
        	
        	Query query = session
            .createQuery("FROM NotificacionTelematica AS m WHERE m.fechaFinPlazo < :ahora and m.rechazada = false ORDER BY m.fechaRegistro ASC")
            .setParameter("ahora",ahora);
            List tramites = query.list();
            
            // Cargamos documentos
            for (Iterator it=tramites.iterator();it.hasNext();){
            	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) it.next();
            	Hibernate.initialize(notificacionTelematica.getDocumentos());
            }
            
            return tramites;
            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     *	 no se utiliza
     * ejb.interface-method
     * ejb.permission role-name="${role.todos}" 
     
    public Page busquedaPaginadaNotificacionesUsuario(int pagina, int longitudPagina )
    {
    	
    	String seyconCiudadano = this.ctx.getCallerPrincipal().getName();
    	
		Session session = getSession();
        try 
        {
        	if( StringUtils.isEmpty( seyconCiudadano ) )
        	{
        		throw new Exception ( "Para la consulta de notificaciones el identificador seycon del ciudadano no puede ser ni nulo ni vacio" );
        	}
        	Query query = 
        		//session.createQuery( "FROM NotificacionTelematica AS n WHERE n.usuarioSeycon = :seyconCiudadano ORDER BY n.fechaAcuse DESC, n.fechaRegistro DESC" )
        		session.createQuery( "FROM NotificacionTelematica AS n WHERE n.usuarioSeycon = :seyconCiudadano ORDER BY n.fechaRegistro DESC" )
        		.setParameter("seyconCiudadano",seyconCiudadano);
        	//query.setCacheable(true);
            Page page = new Page( query, pagina, longitudPagina );
            return page;
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
    */
    
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public int numeroNotificacionesUsuario()
	{
		String seyconCiudadano = this.ctx.getCallerPrincipal().getName();
    	
		Session session = getSession();
        try 
        {
        	Query query = session
            .createQuery("SELECT count(*) FROM NotificacionTelematica AS e WHERE e.usuarioSeycon = :seyconCiudadano")
            .setParameter("seyconCiudadano",seyconCiudadano);
            return ( (Integer) query.iterate().next() ).intValue();
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
     * @ejb.permission role-name="${role.todos}" 
     */
	public int numeroNotificacionesNuevasUsuario()
	{
		String seyconCiudadano = this.ctx.getCallerPrincipal().getName();
    	
		Session session = getSession();
        try 
        {
        	Query query = session
            .createQuery("SELECT count(*) FROM NotificacionTelematica AS e WHERE e.usuarioSeycon = :seyconCiudadano and e.fechaAcuse is null")
            .setParameter("seyconCiudadano",seyconCiudadano);
            return ( (Integer) query.iterate().next() ).intValue();
        }
        catch( HibernateException he )
        {
        	throw new EJBException( he );
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
	 * Realiza la firma del acuse de la notificacion (en caso de que la notificacion tenga acuse) y marca la notificacion como entregada
	 * actualizando el estado del expediente.
	 * 
	 * Devuelve true si ok y false si el firmante no es el adecuado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public boolean firmarAcuseReciboNotificacionAutenticada(Long codigo,String asientoAcuse,FirmaIntf firmaAcuse)
	{
		 try {
			// Obtenemos notificacion (no tiene docs asociados pq no esta firmada)
			NotificacionTelematica notificacion = obtenerNotificacionTelematicaAutenticada(codigo);
			
			// Comprobamos que si se accede de forma delegada, el delegado tenga permisos para recibir notificaciones
        	Principal sp =this.ctx.getCallerPrincipal();
    		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    		if (!plgLogin.getNif(this.ctx.getCallerPrincipal()).equals(notificacion.getNifRepresentante())){
    			// Si no es el usuario quien accede miramos si es un delegado
            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(notificacion.getNifRepresentante());
            	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) == -1){
            		log.debug("Acceso no permitido a abrir notificacion telematica " + codigo + " - usuario " + sp.getName());
            		return false;
            	}
    		}
			
	    	// Realizamos acuse de recibo
			boolean res = realizarFirmaAcuse(notificacion,asientoAcuse,firmaAcuse,null);
			
			// En caso correcto, generamos indices de busqueda 
			if (res) {
				
				// Volvemos a cargar la notificacion para que esten los documentos asociados
				notificacion = obtenerNotificacionTelematicaAutenticada(codigo);
				
				// Obtenemos documento de oficio
				FactoriaObjetosXMLOficioRemision factoriaOR = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
				OficioRemision oficioRemision = factoriaOR.crearOficioRemision (new ByteArrayInputStream (consultarDocumentoRDS(notificacion.getCodigoRdsOficio(),notificacion.getClaveRdsOficio())));
				
				// Generamos indices
				crearIndicesSalida(notificacion, oficioRemision);
			}
			
			// Retornamos resultado
			return res;			
	    }catch( Exception exc ){
        	throw new EJBException( exc );
        }
			
	}
	
	/**
	 * Marca la notificacion como rechazada generando el acuse.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}" 
     */
	public void rechazarNotificacion(Long codigo)
	{
		 try {
			// Recuperamos notificacion 
			NotificacionTelematica notif = obtenerNotificacionTelematicaAuto(codigo);
			
			// Comprobamos fecha fin plazo por si acaso
			if (notif.getFechaFinPlazo() == null || notif.getFechaFinPlazo().after(new Date())) {
				throw new Exception("La notificaci髇 no tiene fin de plazo o todavia no ha pasado");
			}
			 
			// Generamos acuse rechazo
			AsientoRegistral acuseRecibo = generarAcuseReciboNotificacion(codigo, true);
			String xmlAcuseRecibo = ServicioRegistroXML.crearFactoriaObjetosXML().guardarAsientoRegistral(acuseRecibo);
			
			// Insertamos acuse en RDS
			RdsDelegate rdsDelegate =  DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS docAcuseRDS = crearDocumentoRDS( xmlAcuseRecibo.getBytes(ConstantesXML.ENCODING ), true,
					acuseRecibo.getDatosAsunto().getCodigoUnidadAdministrativa(),
					this.ctx.getCallerPrincipal().getName(), "Acuse recibo notificacion rechazada", "AcuseRecibo.xml", "xml",
					ConstantesRDS.MODELO_ASIENTO_REGISTRAL, ConstantesRDS.VERSION_ASIENTO,
					acuseRecibo.getDatosAsunto().getIdiomaAsunto());
			ReferenciaRDS refAcuse = rdsDelegate.insertarDocumento( docAcuseRDS );
			
			// Creamos uso para el acuse
			UsoRDS uso = new UsoRDS();
			uso.setReferenciaRDS(refAcuse);
			uso.setTipoUso(ConstantesRDS.TIPOUSO_REGISTROSALIDA);
			uso.setReferencia(notif.getNumeroRegistro());
			uso.setFechaSello(notif.getFechaRegistro());
			
			// Actualizamos notificacion
			notif.setCodigoRdsAcuse(refAcuse.getCodigo());
			notif.setClaveRdsAcuse(refAcuse.getClave());
			notif.setRechazada(true);
			grabarNotificacionTelematica(notif);
	
			// Actualizamos estado expediente
			ElementoExpediente elemento = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(ElementoExpediente.TIPO_NOTIFICACION, notif.getCodigo());
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(elemento.getExpediente().getCodigo());
			
		 }catch( Exception exc ){
        	throw new EJBException( exc );
        }
		 
	}
	/**
	 * Realiza la firma del acuse de la notificacion (en caso de que la notificacion tenga acuse) y marca la notificacion como entregada
	 * actualizando el estado del expediente.
	 * 
	 * Devuelve true si ok y false si el firmante no es el adecuado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public boolean firmarAcuseReciboNotificacionAnonima(Long codigo,String idPersistencia,String asientoAcuse,FirmaIntf firmaAcuse)
	{
		 try {
			// Obtenemos notificacion
			NotificacionTelematica notificacion = obtenerNotificacionTelematicaAnonima(codigo,idPersistencia);
			// Realizamos acuse de recibo
			return realizarFirmaAcuse(notificacion,asientoAcuse,firmaAcuse,idPersistencia);	    	
	    }catch( Exception exc ){
        	throw new EJBException( exc );
        }		
	}
		
	/**
	 * Prepara parametros para iniciar tr谩mite de subsanaci贸n indicado en la notificaci贸n
	 * Devuelve el id a pasar c贸mo par谩metro al asistente de tramitaci贸n
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String iniciarTramiteSubsanacionNotificacionAnonima(Long codigoNotificacion,String idPersistencia) {
    	// Recuperamos datos notificacion
    	NotificacionTelematica notificacionTelematica =  this.obtenerNotificacionTelematicaAnonima(codigoNotificacion,idPersistencia);
    	if (notificacionTelematica.getFechaAcuse() == null){
    		throw new EJBException("La notificaci贸n no ha sido abierta todav铆a");
    	}
    	// Preparamos parametros tramite de subsanacion
    	String key = generaParametrosInicioSubsanacion(notificacionTelematica);
    	return key;        
    }
    
    /**
	 * Prepara parametros para iniciar tr谩mite de subsanaci贸n indicado en la notificaci贸n
	 * Devuelve el id a pasar c贸mo par谩metro al asistente de tramitaci贸n
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String iniciarTramiteSubsanacionNotificacionAutenticada(Long codigoNotificacion) {
    	// Recuperamos datos notificacion
    	NotificacionTelematica notificacionTelematica =  this.obtenerNotificacionTelematicaAutenticada(codigoNotificacion);
    	if (notificacionTelematica.getFechaAcuse() == null){
    		throw new EJBException("La notificaci贸n no ha sido abierta todav铆a");
    	}
    	// Preparamos parametros tramite de subsanacion
    	String key = generaParametrosInicioSubsanacion(notificacionTelematica);
    	return key;        
    }
    
    
    /**
	 * Obtiene parametros inicio tramite subsanacion a partir de la key de acceso
	 * 
	 * Devuele null si no existe o ha caducado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public ParametrosSubsanacion recuperarParametrosTramiteSubsanacion(String key) {
    	Session session = getSession();
        try {
        	// Recuperamos todos los inicios y borramos los caducados
        	Query query = session.createQuery("FROM ParametrosSubsanacion");
        	List listaParams = query.list();
        	if (listaParams == null){
        		return null;
        	}
        	ParametrosSubsanacion ps,psRes = null;
        	long ahora = (new Date()).getTime();
        	for (Iterator it=listaParams.iterator();it.hasNext();){
        		ps = (ParametrosSubsanacion) it.next();
        		// Comprobamos si ha cumplido plazo (5 min)
        		if (ahora > ps.getFecha().getTime() + 300000){
        			session.delete(ps);
        			continue;
        		}
        		// Comprobamos si son los parametros requeridos
        		if (ps.getCodigo().equals(key)){
        			psRes=ps;
        			// Seguimos comprobando caducados
        		}
        	}
        	
        	return psRes;            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}   
    
    
    /**
     * Genera xml de acuse de recibo
     * @param idNotificacion
     * @param rechazada
     * @return
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public AsientoRegistral generarAcuseReciboNotificacion( Long idNotificacion, boolean rechazada)
	{
		// Obtenemos notificacion
		Session session = getSession();
		NotificacionTelematica notificacionTelematica = null;
        try {
        	notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, idNotificacion);
        	Hibernate.initialize(notificacionTelematica.getDocumentos());        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
		
        // Devolvemos acuse
		return generarAcuseReciboNotificacion(notificacionTelematica, rechazada);
	}
	
	
  	//--------------------------------------------------------
    //	FUNCIONES AUXILIARES
    //---------------------------------------------------------
    private Expediente getExpediente(Session session,NotificacionTelematica notificacion) throws HibernateException{
    	Query query = 
			session.createQuery( "SELECT ee.expediente FROM ElementoExpediente AS ee where ee.tipoElemento = :tipoElemento and ee.codigoElemento = :codigoElemento" ).
			setParameter("tipoElemento", ElementoExpediente.TIPO_NOTIFICACION).				
			setParameter("codigoElemento", notificacion.getCodigo());
		Expediente expediente = ( Expediente ) query.uniqueResult();			
    	return expediente;    
    }
    
    
    /**
     * Marca la notificacion como recibida y actualiza estado de expediente
     * @param codigo
     * @param refAcuse 
     * @param idPersistenciaAnonimo 
     */
	private void marcarRecibidaNotificacionTelematica(Long codigo, ReferenciaRDS refAcuse, String idPersistenciaAnonimo, Date fechaActual) {        
    	
		// Marcamos notificacion como recibida
    	Session session = getSession();
        try {        	
        	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, codigo);
        	notificacionTelematica.setFechaAcuse(fechaActual);
        	notificacionTelematica.setCodigoRdsAcuse(refAcuse.getCodigo());
        	notificacionTelematica.setClaveRdsAcuse(refAcuse.getClave());        	
        	session.update(notificacionTelematica);        	
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	close(session);
        }
        
        // Actualizamos estado expediente
		try{
			// Obtenemos elemento expediente asociado
			ElementoExpediente elemento;
			if (idPersistenciaAnonimo != null){
				elemento = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_NOTIFICACION,codigo,idPersistenciaAnonimo);
			}else{
				elemento = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(ElementoExpediente.TIPO_NOTIFICACION,codigo);
			}
				
			// Actualizamos estado expediente
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(elemento.getExpediente().getCodigo());
		}catch(Exception ex){
			throw new EJBException("Error actualizando estado expediente",ex);
		}
    }

	/**
	 * Obtiene asiento
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private AsientoRegistral getAsientoRegistral( String acuse ) throws Exception
	{
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(acuse.getBytes(ConstantesXML.ENCODING));
		try{
			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			AsientoRegistral asiento = factoria.crearAsientoRegistral(bis);			
			return asiento;
		}catch (Exception ex){
			throw new Exception("Error parseando asiento registral",ex);
		}finally{
			bis.close();
		}
	}    		
    
	/**
	 * Inicializa instancia DocumentoRDS
	 * 
	 * @param contenidoFichero
	 * @param isEstructurado
	 * @param unidadAdministrativa
	 * @param usuarioSeycon
	 * @param titulo
	 * @param nombre
	 * @param extensionFichero
	 * @param modeloRDS
	 * @param version
	 * @return
	 * @throws Exception
	 */
	private DocumentoRDS crearDocumentoRDS( byte[] contenidoFichero, boolean isEstructurado, 
			String unidadAdministrativa, String usuarioSeycon, 
			String titulo, String nombre, 
			String extensionFichero, 
			String modeloRDS, int version,
			String idioma) throws Exception
	{
		DocumentoRDS docRDS = new DocumentoRDS();
		docRDS.setDatosFichero( contenidoFichero );
		docRDS.setEstructurado( isEstructurado );
		docRDS.setFechaRDS( new Date() );
		docRDS.setUnidadAdministrativa( Long.parseLong( unidadAdministrativa, 10 ) );
		docRDS.setUsuarioSeycon( usuarioSeycon );
		docRDS.setTitulo( titulo );
		docRDS.setNombreFichero( nombre );
		docRDS.setExtensionFichero( extensionFichero );
		// Se establece un modelo por defecto para todos aquellos documentos que no tienen modelo en la pad
		if ( modeloRDS == null )
		{
			docRDS.setModelo( ConstantesRDS.MODELO_ANEXO_GENERICO  ); 
			docRDS.setVersion( version );
		}
		else
		{
			docRDS.setVersion( version );
			docRDS.setModelo( modeloRDS );
		}
		docRDS.setIdioma(idioma);
		return docRDS;			
	}
	
	/**
	 * Realiza el proceso que involucra la firma del acuse de recibo: comprobacion firma, insercion acuse en rds y marcar notif como recibida
	 * @param notificacion
	 * @param asientoAcuse
	 * @param firmaAcuse
	 * @return
	 * @throws Exception
	 */
	private boolean realizarFirmaAcuse(NotificacionTelematica notificacion,String asientoAcuse,FirmaIntf firmaAcuse, String idPersistenciaAnonimo) throws Exception{		
	
		Date fechaActual = new Date();
		
		// Comprobamos que no se haya firmado anteriormente
		if (notificacion.getFechaAcuse() != null){
			throw new Exception("El acuse de recibo ya ha sido firmado anteriormente");
		}		
				
		// En caso de que se controle la entrega de notificaciones comprobamos que no haya pasado el plazo		
		if ("true".equals(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.controlEntrega"))) {			
			if (notificacion.getFechaFinPlazo() != null &&  fechaActual.after(notificacion.getFechaFinPlazo())) {
				throw new Exception("El plazo de entrega ha finalizado");
			}
		}
		if (notificacion.isRechazada()) {
			throw new Exception("La notificacion esta rechazada");
		}
		
		// Parseamos acuse
		AsientoRegistral asiento = getAsientoRegistral(asientoAcuse);
		
		// Comprobamos que concuerden los nifs de la notificacion y del acuse
		String nifRepresentanteNotificacion = notificacion.getNifRepresentante();		
		String nifRepresentanteAcuse = getRepresentanteAsiento(asiento);
		if  (!nifRepresentanteNotificacion.equals(nifRepresentanteAcuse)){
			throw new Exception("El acuse de recibo no es correcto, no concuerda el nif del acuse no el nif de la notificacion");
		}
		
		// En caso de que haya que firmar el acuse comprobamos que concuerde el nif de la firma
		// En caso de notificacion autenticada comprobamos si se accede en modo delegado
		if (notificacion.isFirmarAcuse()){		
			
			// Obtenemos nif firmante
			String nifFirmante = getNifFirma(firmaAcuse);
			
			if (!nifFirmante.equals(nifRepresentanteNotificacion)){
				// Si es autenticada, comprobamos si se esta accediendo en modo delegado
				if  (notificacion.getUsuarioSeycon() != null){
					// Si no es el usuario quien firma el acuse es un delegado con permiso para abrir notificaciones
	            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(notificacion.getNifRepresentante());
	            	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) == -1){
	            		return false;
	            	}
				}else{				
					return false;
				}
			}
			
			
			// Si el nif firmante no concuerda con el destinatario comprobamos si se esta accediendo en modo delegado
			if  (notificacion.getUsuarioSeycon() != null && !nifFirmante.equals(nifRepresentanteNotificacion)){
				// Si no es el usuario quien firma el acuse es un delegado con permiso para abrir notificaciones
            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(notificacion.getNifRepresentante());
            	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) == -1){
            		return false;
            	}								
			}
			
		}
			
		// Crear en RDS el acuse de recibo
		RdsDelegate rdsDelegate =  DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS docAcuseRDS = crearDocumentoRDS( asientoAcuse.getBytes( ConstantesXML.ENCODING ), true,
				asiento.getDatosAsunto().getCodigoUnidadAdministrativa(),
				this.ctx.getCallerPrincipal().getName(), "Acuse recibo notificacion entregada", "AcuseRecibo.xml", "xml",
				ConstantesRDS.MODELO_ASIENTO_REGISTRAL, ConstantesRDS.VERSION_ASIENTO,
				asiento.getDatosAsunto().getIdiomaAsunto());
		ReferenciaRDS refAcuse = rdsDelegate.insertarDocumento( docAcuseRDS );
		
		// Creamos uso para el acuse
		UsoRDS uso = new UsoRDS();
		uso.setReferenciaRDS(refAcuse);
		uso.setTipoUso(ConstantesRDS.TIPOUSO_REGISTROSALIDA);
		uso.setReferencia(notificacion.getNumeroRegistro());
		uso.setFechaSello(notificacion.getFechaRegistro());
			
		// Asociar firma al documento rds de acuse
		if (notificacion.isFirmarAcuse()){		
			rdsDelegate.asociarFirmaDocumento(refAcuse,firmaAcuse);								
		}    	
    	
    	// Marcamos notificacion como recibida y actualizamos estado expediente    	
    	marcarRecibidaNotificacionTelematica(notificacion.getCodigo(),refAcuse,idPersistenciaAnonimo, fechaActual);
    	
    	return true;
	}
	
	


	
	/**
	 * Obtiene nif de una firma
	 * @param firma
	 * @return
	 * @throws Exception
	 */
	private String getNifFirma(FirmaIntf firma) throws Exception{
		// Obtenemos nif de la firma
		String nifFirma = "";
		try
		{
        	nifFirma = firma.getNif();
			nifFirma = NifCif.normalizarDocumento(nifFirma);
		}
		catch( Exception exc )
		{
			throw new Exception ( "Error obteniendo NIF a partir de la firma", exc );
		}
		return nifFirma;
	}
	
	/**
	 * Obtiene representante del asiento
	 * @param asiento
	 * @return
	 * @throws Exception
	 */
	private String getRepresentanteAsiento(AsientoRegistral asiento) throws Exception{   	
    	Iterator it =asiento.getDatosInteresado().iterator();     	
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){    			
    			return di.getNumeroIdentificacion();
    		}
    	}    	
    	throw new Exception("No hay representante en el asiento");
	}
	
	/**
	 * Genera parametros inicio para tramite de subsanacion
	 * @param not
	 * @return
	 */
	private String generaParametrosInicioSubsanacion(NotificacionTelematica not){
		Session session = getSession();
        try {
        	// Obtenemos expediente asociado
        	Expediente exp = this.getExpediente(session,not);
        	
        	// Generamos key
        	String key = GeneradorId.generarId();
        	
        	// Almacenamos parametros
        	ParametrosSubsanacion ps = new ParametrosSubsanacion();
        	ps.setCodigo(key);
        	ps.setExpedienteCodigo(exp.getIdExpediente());
        	ps.setExpedienteUnidadAdministrativa(exp.getUnidadAdministrativa());
        	ps.setParametros(not.getTramiteSubsanacionParametros());
        	ps.setFecha(new Date());
        	session.save(ps);
        	
            return key;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
     * Crea indices para una notificacion.
     * @param notificacion Notificacion
     * @param oficioRemision 
     * @throws Exception 
     */
    private void crearIndicesSalida(NotificacionTelematica notificacion, OficioRemision oficioRemision) throws Exception {

		// Establecemos indices a crear
    	Map indices = new HashMap();
    	indices.put("N煤mero registro",  notificacion.getNumeroRegistro());
    	indices.put("T铆tulo oficio", oficioRemision.getTitulo());
    	indices.put("Texto oficio", oficioRemision.getTexto());
    	crearIndicesBusqueda(notificacion.getNifRepresentante(), IndiceElemento.TIPO_NOTIFICACION, notificacion.getCodigo(), indices);
		
	}

    /**
     * Da de alta los indices de busqueda.
     * @param indices
     */
	private void crearIndicesBusqueda(String nif, String tipoElemento, Long idElemento, Map indices) throws Exception {
		
		IndiceElementoDelegate dlg = DelegateUtil.getIndiceElementoDelegate();
		
		for (Iterator it = indices.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String valor = (String) indices.get(key);
			
			// Si el valor es nulo, no tiene sentido crear indice
			if (StringUtils.isBlank(valor)) {
				continue;
			}
			
			IndiceElemento indiceElemento = new IndiceElemento();
			indiceElemento.setNif(nif);
			indiceElemento.setTipoElemento(tipoElemento);
			indiceElemento.setCodigoElemento(idElemento);
			indiceElemento.setDescripcion(key);
			indiceElemento.setValor(valor);
			dlg.grabarIndiceElemento(indiceElemento);
		}		
	}
	
	/**
	 * Consulta documento en el RDS
	 * @param codigo
	 * @param clave
	 * @return
	 * @throws DelegateException
	 */
	private byte[] consultarDocumentoRDS( long codigo, String clave ) throws DelegateException
	{
		ReferenciaRDS referenciaRDS = new ReferenciaRDS(codigo, clave);
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );
		return documentoRDS.getDatosFichero();
	}
	
	/**
	 * Genera acuse de recibo para una notificacion
	 * @param notificacion
	 * @param rechazada
	 * @return
	 */
	private AsientoRegistral generarAcuseReciboNotificacion(NotificacionTelematica notificacion, boolean rechazada)
	{
		try {
			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			factoria.setEncoding(ConstantesXML.ENCODING);
			
			// Usuario autenticado
			Principal sp =this.ctx.getCallerPrincipal();
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
			
			// Parseamos asiento notificacion
			byte[] docAsiento = consultarDocumentoRDS(notificacion.getCodigoRdsAsiento(), notificacion.getClaveRdsAsiento());
			ByteArrayInputStream bis = new ByteArrayInputStream(docAsiento);
			AsientoRegistral asientoNotificacion = factoria.crearAsientoRegistral( bis );			
					
			// Crear asiento registral para acuse recibo
			AsientoRegistral asiento = factoria.crearAsientoRegistral();
			asiento.setVersion("1.0");
			
			// Crear datos origen
			DatosOrigen dOrigen = factoria.crearDatosOrigen();
			dOrigen.setCodigoEntidadRegistralOrigen ( asientoNotificacion.getDatosOrigen().getCodigoEntidadRegistralOrigen() );				
			dOrigen.setTipoRegistro( new Character( ConstantesAsientoXML.TIPO_ACUSE_RECIBO )  );
			dOrigen.setNumeroRegistro (notificacion.getNumeroRegistro() );
			dOrigen.setFechaEntradaRegistro( notificacion.getFechaRegistro() );
			asiento.setDatosOrigen (dOrigen);
			
			// Crear datos representante
			DatosInteresado dInteresadoRpte,dInteresadoRpdo=null;
			dInteresadoRpte = factoria.crearDatosInteresado();
			if (rechazada) {
				if (StringUtils.isNotBlank(notificacion.getUsuarioSeycon())) {
					dInteresadoRpte.setNivelAutenticacion(new Character(ConstantesAsientoXML.NIVEL_AUTENTICACION_CERTIFICADO));
				} else {
					dInteresadoRpte.setNivelAutenticacion(new Character(ConstantesAsientoXML.NIVEL_AUTENTICACION_ANONIMO));
				}
				
			} else {
				dInteresadoRpte.setNivelAutenticacion( new Character( plgLogin.getMetodoAutenticacion(sp) ) );
			}
			dInteresadoRpte.setUsuarioSeycon( notificacion.getUsuarioSeycon() );						
			dInteresadoRpte.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE);		
			dInteresadoRpte.setTipoIdentificacion (obtenerTipoIdentificacion( notificacion.getNifRepresentante() ) );
			dInteresadoRpte.setNumeroIdentificacion (notificacion.getNifRepresentante());
			dInteresadoRpte.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
			dInteresadoRpte.setIdentificacionInteresado (notificacion.getNombreRepresentante());
			
			DireccionCodificada  direccionRpte = factoria.crearDireccionCodificada();
			direccionRpte.setCodigoProvincia( direccionRpte.getCodigoProvincia() );
			direccionRpte.setCodigoMunicipio( direccionRpte.getCodigoMunicipio() );
			direccionRpte.setPaisOrigen( direccionRpte.getPaisOrigen() );
			direccionRpte.setNombreMunicipio( direccionRpte.getNombreMunicipio() );
			direccionRpte.setNombreProvincia( direccionRpte.getNombreProvincia() );
			dInteresadoRpte.setDireccionCodificada( direccionRpte );
			
			asiento.getDatosInteresado().add(dInteresadoRpte);
			
			// Crear datos de representado si necesario
			if ( !StringUtils.isEmpty( notificacion.getNifRepresentado() ) )
			{
				dInteresadoRpdo = factoria.crearDatosInteresado();
				dInteresadoRpdo.setTipoInteresado( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO );
				dInteresadoRpdo.setTipoIdentificacion( obtenerTipoIdentificacion( notificacion.getNifRepresentado() ));
				dInteresadoRpdo.setNumeroIdentificacion( notificacion.getNifRepresentado() );
				dInteresadoRpdo.setFormatoDatosInteresado( ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM );
				dInteresadoRpdo.setIdentificacionInteresado( notificacion.getNombreRepresentado() );
				asiento.getDatosInteresado().add(dInteresadoRpdo);
			}
			
			// Crear datos asunto
			DatosAsunto dAsunto = asientoNotificacion.getDatosAsunto();		
			dAsunto.setFechaAsunto(new Date());
			dAsunto.setIdiomaAsunto (asientoNotificacion.getDatosAsunto().getIdiomaAsunto());
			if (rechazada) {
				dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_RECHAZADA);
				dAsunto.setExtractoAsunto ("Acuse recibo notificacion - NOTIFICACION RECHAZADA");
			} else {
				dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_ENTREGADA);
				dAsunto.setExtractoAsunto ("Acuse recibo notificacion - NOTIFICACION ENTREGADA");
			}
			dAsunto.setCodigoOrganoDestino ( asientoNotificacion.getDatosAsunto().getCodigoOrganoDestino()  );
			dAsunto.setDescripcionOrganoDestino( asientoNotificacion.getDatosAsunto().getDescripcionOrganoDestino() );
			dAsunto.setCodigoUnidadAdministrativa( asientoNotificacion.getDatosAsunto().getCodigoUnidadAdministrativa() );		
			asiento.setDatosAsunto (dAsunto);
			
			// Crear Anexo con el aviso		
			DatosAnexoDocumentacion anexoAvisoNotificacion = obtenerAnexoAsientoDeTipo( asientoNotificacion, ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION );
			asiento.getDatosAnexoDocumentacion().add( anexoAvisoNotificacion );
			
			return asiento;
		}catch (Exception ex) {
			throw new EJBException(ex);
		}
	}
	
	private Character obtenerTipoIdentificacion( String numeroDocumento ) throws Exception
	{
		char tipoDocumento = NifCif.TIPO_DOCUMENTO_NIF;
		if (StringUtils.isNotEmpty(numeroDocumento))
		{
			switch (NifCif.validaDocumento(numeroDocumento))
			{
				case NifCif.TIPO_DOCUMENTO_NIF:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF;
					break;
				case NifCif.TIPO_DOCUMENTO_CIF:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF;
					break;
				case NifCif.TIPO_DOCUMENTO_NIE:
					tipoDocumento = ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE;
					break;
				default:
					throw new Exception("El n鷐ero de identificaci髇 del representante ni es nif, ni cif, ni nie");
			}
		}
		return new Character( tipoDocumento );
	}
	
	public DatosAnexoDocumentacion obtenerAnexoAsientoDeTipo( AsientoRegistral asiento, char tipoAnexo )
	{
		for( Iterator it = asiento.getDatosAnexoDocumentacion().iterator(); it.hasNext(); )
		{
			DatosAnexoDocumentacion tmp = ( DatosAnexoDocumentacion ) it.next();
			if ( tmp.getTipoDocumento().charValue() == tipoAnexo )
			{
				return tmp;
			}
		}
		return null;
	}
}
