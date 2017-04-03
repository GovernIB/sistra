package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
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

import es.caib.bantel.modelInterfaz.EntidadBTE;
import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.FirmaUtil;
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
import es.caib.zonaper.modelInterfaz.DetalleNotificacionesProcedimiento;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.IndiceElementoDelegate;
import es.caib.zonaper.persistence.util.ConfigurationUtil;
import es.caib.zonaper.persistence.util.GeneradorId;
import es.caib.zonaper.persistence.util.LiteralesAvisosMovilidad;

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
        return recuperarNotificacionPorId(id);
    }

	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public NotificacionTelematica obtenerNotificacionTelematicaAutenticada(Long id) {
        return obtenerNotificacionTelematicaImpl(id, true, null);        
    }


   
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public NotificacionTelematica obtenerNotificacionTelematicaAnonima(Long id,String idPersistencia) {
    	 return obtenerNotificacionTelematicaImpl(id, false, idPersistencia);   
    }
    
    /**
     * @ejb.interface-method     
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public NotificacionTelematica obtenerNotificacionTelematica(String entidad, String numeroRegistro) {
        Session session = getSession();
        try {
        	
        	// Verificamos que entidad no es nulo
        	if (entidad == null) {
        		throw new Exception("No se ha indicado código entidad");
        	}
        	
        	// Cargamos notificacionTelematica        	
        	Query query = session
            .createQuery("FROM NotificacionTelematica AS m WHERE m.numeroRegistro = :numeroRegistro")
            .setParameter("numeroRegistro",numeroRegistro);
            //query.setCacheable(true);
            
        	List res = query.list();
        	
        	if (res.isEmpty()){
            	return null;            	
            }
            
            // El num de registro puede no ser unico por entidad. Recuperamos y verificamos que el codigo entidad pertenezca al procedimiento
        	NotificacionTelematica resultado = null;
            for (Iterator it = res.iterator(); it.hasNext();) {
            	 NotificacionTelematica notificacionTelematica = (NotificacionTelematica)  it.next();
            	 
            	 // Obtenemos procedimiento a partir del expediente
            	 ElementoExpediente eex = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(ElementoExpediente.TIPO_NOTIFICACION, notificacionTelematica.getCodigo());
            	 String idProc = eex.getExpediente().getIdProcedimiento();
            	 ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(idProc);
            	 
            	 // Verificamos que sea la misma entidad
            	 if (proc.getEntidad().equals(entidad)) {
            		 resultado = notificacionTelematica;
            		 break;
            	 }
            	 
            }
                                    
        	// Cargamos documentos
            if (resultado != null) {
            	Hibernate.initialize(resultado.getDocumentos());
            }
            
            return resultado;
            
        } catch (Exception he) {
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
    public Long grabarNuevaNotificacionTelematica(NotificacionTelematica obj) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null){
        		session.save(obj);
        	}else{
        		throw new Exception("No se permite actualizar notificacion telematica");
        	}
        	                    	
            return obj.getCodigo();
        } catch (Exception he) {
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
            .createQuery("FROM NotificacionTelematica AS m WHERE m.firmarAcuse = true and m.fechaFinPlazo < :ahora and m.rechazada = false and m.fechaAcuse is null ORDER BY m.fechaRegistro ASC")
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
	 * Realiza la firma del acuse de la notificacion con certificado (en caso de que la notificacion tenga acuse) y marca la notificacion como entregada
	 * actualizando el estado del expediente.
	 * 
	 * Devuelve true si ok y false si el firmante no es el adecuado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}" 
     */
	public boolean firmarAcuseReciboNotificacionAutenticada(Long codigo,String asientoAcuse,FirmaIntf firmaDigital, String firmaClave)
	{
		 return firmarAcuseReciboNotificacionAutenticadaImpl(codigo,
				asientoAcuse, firmaDigital, firmaClave);		
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
			NotificacionTelematica notif = recuperarNotificacionPorId(codigo);
			
			// Solo se puede rechazar si hay que firmar acuse
			if (!notif.isFirmarAcuse()) {
				throw new Exception("La notificación no debe firmarse");	
			}
			
			// Comprobamos fecha fin plazo por si acaso
			if (notif.getFechaFinPlazo() == null || notif.getFechaFinPlazo().after(new Date())) {
				throw new Exception("La notificación no tiene fin de plazo o todavia no ha pasado");
			}
			 
			// Comprobamos que no se haya firmado el acuse
			if (notif.getFechaAcuse() != null) {
				throw new Exception("No se puede rechazar porque ya se ha firmado el acuse de la notificacion");
			}
			
			// Generamos acuse rechazo
			Date fechaRechazo = new Date();
			AsientoRegistral acuseRecibo = generarAcuseReciboNotificacionImpl(notif, true, null, fechaRechazo);
			String xmlAcuseRecibo = ServicioRegistroXML.crearFactoriaObjetosXML().guardarAsientoRegistral(acuseRecibo);
			
			// Insertamos acuse en RDS
			RdsDelegate rdsDelegate =  DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS docAcuseRDS = crearDocumentoRDS( xmlAcuseRecibo.getBytes(ConstantesXML.ENCODING ), true,
					acuseRecibo.getDatosAsunto().getCodigoUnidadAdministrativa(),
					notif.getUsuarioSeycon(), notif.getNifRepresentante(), "Acuse recibo notificacion rechazada", "AcuseRecibo.xml", "xml",
					ConstantesRDS.MODELO_ASIENTO_REGISTRAL, ConstantesRDS.VERSION_ASIENTO,
					acuseRecibo.getDatosAsunto().getIdiomaAsunto());
			ReferenciaRDS refAcuse = rdsDelegate.insertarDocumento( docAcuseRDS );
			
			// Creamos uso para el acuse
			UsoRDS uso = new UsoRDS();
			uso.setReferenciaRDS(refAcuse);
			uso.setTipoUso(ConstantesRDS.TIPOUSO_REGISTROSALIDA);
			uso.setReferencia(notif.getNumeroRegistro());
			uso.setFechaSello(notif.getFechaRegistro());
			rdsDelegate.crearUso(uso);
			
			// Actualizamos notificacion como rechazada
			marcarNotificacionRechazada(codigo, refAcuse, fechaRechazo);
			
			// Actualizamos estado expediente
			Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_NOTIFICACION, notif.getCodigo());
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(idExpediente);
			
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
	public boolean firmarAcuseReciboNotificacionAnonima(Long codigo,String idPersistencia,String asientoAcuse,FirmaIntf firmaDigital, String firmaClave)
	{
		return firmarAcuseReciboNotificacionAnonimaImpl(codigo, idPersistencia,
				asientoAcuse, firmaDigital, firmaClave);		
	}

	/**
	 * Prepara parametros para iniciar tramite de subsanacion indicado en la notificacion
	 * Devuelve el id a pasar como parametro al asistente de tramitación
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String iniciarTramiteSubsanacionNotificacionAnonima(Long codigoNotificacion,String idPersistencia) {
    	return iniciarTramiteSubsanacionNotificacionImpl(codigoNotificacion,
				idPersistencia);
    }

    /**
	 * Prepara parametros para iniciar trÃ¡mite de subsanación indicado en la notificación
	 * Devuelve el id a pasar cómo parÃ¡metro al asistente de tramitación
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String iniciarTramiteSubsanacionNotificacionAutenticada(Long codigoNotificacion) {
    	return iniciarTramiteSubsanacionNotificacionImpl(codigoNotificacion,
				null);
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
	public AsientoRegistral generarAcuseReciboNotificacion( Long idNotificacion, boolean rechazada, String tipoFirma)
	{
		// Obtenemos notificacion
		NotificacionTelematica notificacionTelematica = this.recuperarNotificacionPorId(idNotificacion);
        
        // Devolvemos acuse
		return generarAcuseReciboNotificacionImpl(notificacionTelematica, rechazada, tipoFirma, new Date());
	}
	
	 /**
     * Obtiene detalle notificaciones para un procedimiento en un intervalo.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
	public DetalleNotificacionesProcedimiento obtenerDetalleNotificacionesProcedimiento(String idProc, Date desde, Date hasta) {
		
		DetalleNotificacionesProcedimiento dn = new DetalleNotificacionesProcedimiento();
		
		// Notificaciones con acuse
		dn.getNotificacionesConAcuse().setNuevas(buscarNotificacionesProcedimiento(idProc, "NUEVA",
				true, desde, hasta));
		dn.getNotificacionesConAcuse().setAceptadas(buscarNotificacionesProcedimiento(idProc, "ACEPTADA",
				true, desde, hasta));
		dn.getNotificacionesConAcuse().setRechazadas(buscarNotificacionesProcedimiento(idProc, "RECHAZADA",
				true, desde, hasta));
		
		// Notificaciones sin acuse
		dn.getNotificacionesSinAcuse().setNuevas(buscarNotificacionesProcedimiento(idProc, "NUEVA",
				false, desde, hasta));
		dn.getNotificacionesSinAcuse().setAceptadas(buscarNotificacionesProcedimiento(idProc, "ACEPTADA",
				false, desde, hasta));
		dn.getNotificacionesSinAcuse().setRechazadas(buscarNotificacionesProcedimiento(idProc, "RECHAZADA",
				false, desde, hasta));
			
		return dn;
			
	}


	
	
  	//--------------------------------------------------------
    //	FUNCIONES AUXILIARES
    //---------------------------------------------------------

	/**
	 * Busca notificaciones procedimiento
	 * @param idProc id proc
	 * @param tipo NUEVA/ACEPTADA/RECHAZADA
	 * @param conAcuse con o sin acuse
	 * @param desde Desde 
	 * @param hasta Hasta
	 * @return Devuelve lista con id expediente de las notificaciones
	 */
	private List buscarNotificacionesProcedimiento(String idProc,
			String tipo, boolean conAcuse, Date desde, Date hasta) {
		List notifs = new ArrayList();
		Session session = getSession();
		try {
			String hql = "select e FROM ElementoExpediente AS e, NotificacionTelematica AS m WHERE " + 
			"e.expediente.idProcedimiento = :idProcedimiento AND " +
			"e.tipoElemento = '" + ElementoExpediente.TIPO_NOTIFICACION + "' AND " +
			"e.codigoElemento = m.codigo AND m.firmarAcuse = " + Boolean.toString(conAcuse) + " ";
			
			if ("NUEVA".equals(tipo)) {
				if (desde != null) {
					hql = hql + "and m.fechaRegistro >= :desde ";
				}
				hql = hql + "and m.fechaRegistro <= :hasta ";
			}
			
			if ("ACEPTADA".equals(tipo)) {
				hql = hql + "and m.rechazada = false and m.fechaAcuse is not null and m.fechaAcuse <= :hasta ";
				if (desde != null) {
					hql = hql + "and m.fechaAcuse >= :desde ";
				}				
			}
			
			if ("RECHAZADA".equals(tipo)) {
				hql = hql + "and m.rechazada = true and m.fechaRechazada is not null and m.fechaRechazada <= :hasta ";
				if (desde != null) {
					hql = hql + "and m.fechaRechazada >= :desde ";
				}	
			}
			
			Query query = session
				        	.createQuery(hql)
				        	.setString("idProcedimiento",idProc)				        	
				        	.setTimestamp("hasta",hasta);
			if (desde != null) {
				query.setTimestamp("desde",desde);
			}
			
			
			List result = query.list();
			if (result != null) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					ElementoExpediente ee =  (ElementoExpediente) it.next();
					notifs.add(ee.getExpediente().getIdExpediente());
				}
			}
			
			return notifs;
			
		} catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }		
	}
	
	private String iniciarTramiteSubsanacionNotificacionImpl(
			Long codigoNotificacion, String idPersistencia) {
		try {
			// Autenticado
			boolean autenticado = (idPersistencia == null);
	    	// Recuperamos datos notificacion
	    	NotificacionTelematica notificacionTelematica;
	    	if (!autenticado) {	    	
	    		notificacionTelematica = this.obtenerNotificacionTelematicaAnonima(codigoNotificacion,idPersistencia);
	    	} else {
	    		notificacionTelematica = this.obtenerNotificacionTelematicaAutenticada(codigoNotificacion);
	    	}
	    	if (notificacionTelematica.getFechaAcuse() == null || notificacionTelematica.isRechazada()){
	    		throw new Exception("La notificacion no ha sido abierta todavia");
	    	}
	    	// Preparamos parametros tramite de subsanacion
	    	String key = generaParametrosInicioSubsanacion(notificacionTelematica, autenticado, idPersistencia);
	    	return key;
    	}catch( Exception exc ){
        	throw new EJBException( exc );
        }
	}	

	private boolean firmarAcuseReciboNotificacionAutenticadaImpl(Long codigo,
			String asientoAcuse, FirmaIntf firmaDigital, String firmaClave) {
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
			boolean res = realizarFirmaAcuse(notificacion,asientoAcuse,firmaDigital, firmaClave);
			
			// Retornamos resultado
			return res;			
	    }catch( Exception exc ){
        	throw new EJBException( exc );
        }
	}

	private boolean firmarAcuseReciboNotificacionAnonimaImpl(Long codigo,
			String idPersistencia, String asientoAcuse, FirmaIntf firmaDigital, String firmaClave) {
		try {
			// Obtenemos notificacion
			NotificacionTelematica notificacion = obtenerNotificacionTelematicaAnonima(codigo,idPersistencia);
			// Realizamos acuse de recibo
			return realizarFirmaAcuse(notificacion,asientoAcuse,firmaDigital, firmaClave);	    	
	    }catch( Exception exc ){
        	throw new EJBException( exc );
        }
	}
	
	 /**
     * Devuelve notificacion con informacion exclusiva de aviso
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
     * Marca la notificacion como recibida y actualiza estado de expediente
     * @param codigo
     * @param refAcuse 
     * @param tipoFirmaAcuse 
     * @param idPersistenciaAnonimo 
     */
	private void marcarRecibidaNotificacionTelematica(Long codigo, ReferenciaRDS refAcuse, Date fechaActual, String tipoFirmaAcuse) {        
    	
		// Marcamos notificacion como recibida
    	Session session = getSession();
        try {        	
        	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, codigo);
        	notificacionTelematica.setFechaAcuse(fechaActual);
        	notificacionTelematica.setCodigoRdsAcuse(refAcuse.getCodigo());
        	notificacionTelematica.setClaveRdsAcuse(refAcuse.getClave()); 
        	notificacionTelematica.setTipoFirmaAcuse(tipoFirmaAcuse);
        	session.update(notificacionTelematica);        	
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	close(session);
        }
        
        try {    
	        // Actualizamos estado expediente
	        Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_NOTIFICACION, codigo);
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpediente(idExpediente);
		} catch (Exception he) {
	        throw new EJBException(he);
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
	 * @param nif
	 * @param titulo
	 * @param nombre
	 * @param extensionFichero
	 * @param modeloRDS
	 * @param version
	 * @return
	 * @throws Exception
	 */
	private DocumentoRDS crearDocumentoRDS( byte[] contenidoFichero, boolean isEstructurado, 
			String unidadAdministrativa, String usuarioSeycon, String nif,
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
		docRDS.setNif(nif);
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
	 * Realiza el proceso que involucra la firma del acuse de recibo: comprobacion firma, insercion acuse en rds y marcar notif como recibida.
	 * Si no hay que firmar acuse tb se llamara a esta funcion, pero sin los parametros de firma.
	 * @param notificacion
	 * @param asientoAcuse
	 * @param firmaDigital
	 * @return
	 * @throws Exception
	 */
	private boolean realizarFirmaAcuse(NotificacionTelematica notificacion,String asientoAcuse,FirmaIntf firmaDigital, String firmaClave) throws Exception{		
	
		Date fechaActual = new Date();
		String tipoFirmaAcuse = null;
		FirmaIntf selladoAcuse = null;
		
		// Comprobamos que no se haya firmado anteriormente
		if (notificacion.isRechazada()) {
			throw new Exception("La notificacion esta rechazada");
		}
		if (notificacion.getFechaAcuse() != null){
			throw new Exception("El acuse de recibo ya ha generado anteriormente");
		}		
				
		// En caso de que se controle la entrega de notificaciones comprobamos que no haya pasado el plazo
		// (solo para notifs con firma de acuse)
		boolean controlEntregaHabilitada = "true".equals(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.controlEntrega.habilitar"));		
		if (notificacion.isFirmarAcuse() && controlEntregaHabilitada) {			
			if (notificacion.getFechaFinPlazo() != null &&  fechaActual.after(notificacion.getFechaFinPlazo())) {
				throw new Exception("El plazo de entrega ha finalizado");
			}
		}
		
				
		// Parseamos acuse
		AsientoRegistral asiento = getAsientoRegistral(asientoAcuse);
		
		// Comprobamos que concuerden los nifs de la notificacion y del acuse
		String nifRepresentanteNotificacion = notificacion.getNifRepresentante();		
		String nifRepresentanteAcuse = getRepresentanteAsiento(asiento);
		if  (!nifRepresentanteNotificacion.equals(nifRepresentanteAcuse)){
			throw new Exception("El acuse de recibo no es correcto, no concuerda el nif del acuse no el nif de la notificacion");
		}
		
		
		// Verificacion de firma si hay que firmar acuse
		if (notificacion.isFirmarAcuse()) {
			// Debe alimentarse una de las 2 firmas
			if (firmaClave == null && firmaDigital == null) {
				throw new Exception("No se ha indicado firma");
			}
			
			// Verificacion firma por clave
			if (firmaClave != null) {
				// Si se intenta firmar por clave validamos que este permitido y que la clave sea la correcta
				if (!notificacion.isAccesiblePorClave()) {
					throw new Exception("El acuse de recibo no puede ser firmado por clave");
				}
				if (notificacion.isAccesiblePorClave() && !notificacion.getIdentificadorPersistencia().equals(firmaClave)) {
					return false;
				}		
				tipoFirmaAcuse = ConstantesZPE.TIPOFIRMAACUSE_CLAVE;
				
				// Si esta configurado que se selle con una firma de sistra, generamos firma de sellado
				if ("true".equals(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.sellarAcuses.firmaClave.habilitar"))) {
					selladoAcuse = sellarAcuseRecibo(asientoAcuse);
				}
				
				
			}
			// Verificacion firma por certificado
			if (firmaDigital != null){		
				// Obtenemos nif firmante
				String nifFirmante = getNifFirma(firmaDigital);
				// Si no es el destinatario quien firma el acuse, comprobamos si es un delegado con permiso para abrir notificaciones
				if (!nifFirmante.equals(nifRepresentanteNotificacion)){
	            	String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(notificacion.getNifRepresentante());
	            	if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_ABRIR_NOTIFICACION) == -1){
	            		return false;
	            	}
				}
				tipoFirmaAcuse = ConstantesZPE.TIPOFIRMAACUSE_CERTIFICADO;
			}						
		}
		
		
		
		// Crear en RDS el acuse de recibo
		RdsDelegate rdsDelegate =  DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS docAcuseRDS = crearDocumentoRDS( asientoAcuse.getBytes( ConstantesXML.ENCODING ), true,
				asiento.getDatosAsunto().getCodigoUnidadAdministrativa(),
				notificacion.getUsuarioSeycon(), notificacion.getNifRepresentante(), "Acuse recibo notificacion entregada", "AcuseRecibo.xml", "xml",
				ConstantesRDS.MODELO_ASIENTO_REGISTRAL, ConstantesRDS.VERSION_ASIENTO,
				asiento.getDatosAsunto().getIdiomaAsunto());
		ReferenciaRDS refAcuse = rdsDelegate.insertarDocumento( docAcuseRDS );
		
		// Creamos uso para el acuse
		UsoRDS uso = new UsoRDS();
		uso.setReferenciaRDS(refAcuse);
		uso.setTipoUso(ConstantesRDS.TIPOUSO_REGISTROSALIDA);
		uso.setReferencia(notificacion.getNumeroRegistro());
		uso.setFechaSello(notificacion.getFechaRegistro());
		rdsDelegate.crearUso(uso);
			
		// Asociar firma ciudadano al documento rds de acuse
		if (notificacion.isFirmarAcuse() && firmaDigital != null){		
			rdsDelegate.asociarFirmaDocumento(refAcuse,firmaDigital);								
		}    	
    	
		// Asociar sellado acuse por parte de sistra
		if (selladoAcuse != null) {
			rdsDelegate.asociarFirmaDocumento(refAcuse,selladoAcuse);
		}
		
    	// Marcamos notificacion como recibida y actualizamos estado expediente    	
    	marcarRecibidaNotificacionTelematica(notificacion.getCodigo(),refAcuse, fechaActual, tipoFirmaAcuse);
    	
    	// Generamos indices de busqueda 
		generarIndicesBusqueda(notificacion.getCodigo());
		    	
    	return true;
	}

	
	private FirmaIntf sellarAcuseRecibo(String asientoAcuse) throws Exception {
		// Obtenemos plugin firma
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		// Según implementación tendrá parámetros específicos
		Map params = paramsPluginFirma(plgFirma);
		// Firmamos acuse
		String certificadoName = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.sellarAcuses.certificado.name");
		InputStream is = FirmaUtil.cadenaToInputStream(asientoAcuse);
		FirmaIntf firmaJustificante = plgFirma.firmar(is,certificadoName,params);
		return firmaJustificante;
	}

	private Map paramsPluginFirma(PluginFirmaIntf plgFirma)
			throws Exception {
		Map params = new HashMap();
		if (plgFirma.getProveedor().equals(PluginFirmaIntf.PROVEEDOR_CAIB)) {
			// Establecemos content type correspondiente
			params.put(	FirmaUtil.CAIB_PARAMETER_CONTENT_TYPE,
						FirmaUtil.obtenerContentTypeCAIB(FirmaUtil.CAIB_ACUSE_NOTIFICACIO_CONTENT_TYPE));
			// Establecemos pin certificado
			String certificadoPin = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.sellarAcuses.certificado.pin");
			params.put(FirmaUtil.CAIB_PARAMETER_PIN, certificadoPin);
		} else if (plgFirma.getProveedor().equals(
				PluginFirmaIntf.PROVEEDOR_AFIRMA)) {
			params.put(FirmaUtil.AFIRMA_PARAMETER_ARCHIVO, "AcuseRecibo.xml");
		}
		return params;
	}
	
	
	private void generarIndicesBusqueda(Long codigo)
			throws Exception {
		NotificacionTelematica notificacion = recuperarNotificacionPorId(codigo);
		// Obtenemos documento de oficio
		FactoriaObjetosXMLOficioRemision factoriaOR = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
		OficioRemision oficioRemision = factoriaOR.crearOficioRemision (new ByteArrayInputStream (consultarDocumentoRDS(notificacion.getCodigoRdsOficio(),notificacion.getClaveRdsOficio())));
		// Generamos indices		
    	Map indices = new HashMap();
    	indices.put("Numero registro",  notificacion.getNumeroRegistro());
    	indices.put("Titulo oficio", oficioRemision.getTitulo());
    	indices.put("Texto oficio", oficioRemision.getTexto());
    	crearIndicesBusqueda(notificacion.getNifRepresentante(), IndiceElemento.TIPO_NOTIFICACION, notificacion.getCodigo(), indices);
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
	 * @param accesoAutenticado 
	 * @param idPersistencia 
	 * @return
	 */
	private String generaParametrosInicioSubsanacion(NotificacionTelematica not, boolean accesoAutenticado, String idPersistencia) throws Exception {
		
		// Obtenemos expediente asociado
		Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_NOTIFICACION, not.getCodigo());
		Expediente exp = null;
		if (accesoAutenticado) {
    	 exp = DelegateUtil.getExpedienteDelegate().obtenerExpedienteAutenticado(idExpediente);
		} else {
		 exp = DelegateUtil.getExpedienteDelegate().obtenerExpedienteAnonimo(idExpediente, idPersistencia);	
		}
		
		// Genera parametros de inicio
		Session session = getSession();
        try {
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
	 * @param tipoFirma Puede ser CERT, CLAVE o vacio si no requiere firma
	 * @return
	 */
	private AsientoRegistral generarAcuseReciboNotificacionImpl(NotificacionTelematica notificacion, boolean rechazada, String tipoFirma, Date fechaAcuse)
	{
		try {
			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			factoria.setEncoding(ConstantesXML.ENCODING);
			
			// Obtiene entidad expediente
			String entidad = DelegateUtil.getExpedienteDelegate().obtenerEntidadExpediente(notificacion.getCodigo());
			
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
			dOrigen.setCodigoEntidad(entidad);
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
			DatosAsunto dAsunto = factoria.crearDatosAsunto(); // asientoNotificacion.getDatosAsunto();		
			dAsunto.setFechaAsunto(fechaAcuse);
			dAsunto.setIdiomaAsunto (asientoNotificacion.getDatosAsunto().getIdiomaAsunto());
			if (rechazada) {
				dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_RECHAZADA);
				dAsunto.setExtractoAsunto (LiteralesAvisosMovilidad.getLiteral(asientoNotificacion.getDatosAsunto().getIdiomaAsunto(), "acuse.rechazada"));
			} else {
				if ("CLAVE".equals(tipoFirma)) {
					dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_ENTREGADA_CLAVE);
					dAsunto.setExtractoAsunto (LiteralesAvisosMovilidad.getLiteral(asientoNotificacion.getDatosAsunto().getIdiomaAsunto(), "acuse.entregada.firmaClave"));
				} else if ("CERT".equals(tipoFirma))  {
					dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_ENTREGADA);
					dAsunto.setExtractoAsunto (LiteralesAvisosMovilidad.getLiteral(asientoNotificacion.getDatosAsunto().getIdiomaAsunto(), "acuse.entregada.firmaCertificado"));
				} else {
					dAsunto.setTipoAsunto (ConstantesAsientoXML.ACUSERECIBO_ENTREGADA);
					dAsunto.setExtractoAsunto (LiteralesAvisosMovilidad.getLiteral(asientoNotificacion.getDatosAsunto().getIdiomaAsunto(), "acuse.entregada.noFirmada"));					
				}
			}
			dAsunto.setCodigoOrganoDestino ( asientoNotificacion.getDatosAsunto().getCodigoOrganoDestino()  );
			dAsunto.setDescripcionOrganoDestino( asientoNotificacion.getDatosAsunto().getDescripcionOrganoDestino() );
			dAsunto.setCodigoUnidadAdministrativa( asientoNotificacion.getDatosAsunto().getCodigoUnidadAdministrativa() );		
			asiento.setDatosAsunto (dAsunto);
			
			// Adjuntamos los anexos de la notificacion, incluidos el aviso y la comunicacion
			for( Iterator it = asientoNotificacion.getDatosAnexoDocumentacion().iterator(); it.hasNext(); )
			{
				DatosAnexoDocumentacion anexoDoc = ( DatosAnexoDocumentacion ) it.next();
				asiento.getDatosAnexoDocumentacion().add( anexoDoc );
			}
			
			
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
					throw new Exception("El número de identificación del representante ni es nif, ni cif, ni nie");
			}
		}
		return new Character( tipoDocumento );
	}
	
	private DatosAnexoDocumentacion obtenerAnexoAsientoDeTipo( AsientoRegistral asiento, char tipoAnexo )
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
	

	private NotificacionTelematica recuperarNotificacionPorId(Long id) {
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
	

	private NotificacionTelematica obtenerNotificacionTelematicaImpl(Long id, boolean accesoAutenticado, String idPersistencia) {
		try {
	        // Verificamos acceso expediente
	        Long idExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerCodigoExpedienteElemento(ElementoExpediente.TIPO_NOTIFICACION, id);
	        if (accesoAutenticado) {
		        if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAutenticado(idExpediente)) {
		        	throw new Exception("No tiene acceso a expediente");
		        }
	        } else {
	        	if (!DelegateUtil.getExpedienteDelegate().verificarAccesoExpedienteAnonimo(idExpediente, idPersistencia)) {
		        	throw new Exception("No tiene acceso a expediente");
		        }
	        }
	        
	        // Devolvemos notificacion
	        NotificacionTelematica notificacionTelematica = recuperarNotificacionPorId(id);
	        
	        // En caso de que no este firmado el acuse o este rechazada no devolvemos ficheros asociados
	        if (notificacionTelematica.getFechaAcuse() == null || notificacionTelematica.isRechazada()){
	    		return notificacionSinAbrir(notificacionTelematica);
	    	}else{
	    		return notificacionTelematica;
	    	}	        
        } catch (Exception he) {
            throw new EJBException(he);
        }
	}
	 
   private void marcarNotificacionRechazada(Long id, ReferenciaRDS refAcuse, Date fechaRechazo) {
	   Session session = getSession();
       try {
       	NotificacionTelematica notificacionTelematica = (NotificacionTelematica) session.load(NotificacionTelematica.class, id);
       	notificacionTelematica.setCodigoRdsAcuse(refAcuse.getCodigo());
       	notificacionTelematica.setClaveRdsAcuse(refAcuse.getClave());
       	notificacionTelematica.setRechazada(true);
       	notificacionTelematica.setFechaRechazada(fechaRechazo);
       	notificacionTelematica.setFechaAcuse(null);
       
       	session.update(notificacionTelematica);
       	
       } catch (HibernateException he) {
           throw new EJBException(he);
       } finally {
           close(session);
       }	   
   }
      
   
   
}
