package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.BteDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvio;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvioEmail;
import es.caib.mobtratel.modelInterfaz.EstadoMensajeEnvioSms;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.util.StringUtil;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.model.DocumentoEventoExpediente;
import es.caib.zonaper.model.DocumentoNotificacionTelematica;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.DetalleAcuseRecibo;
import es.caib.zonaper.modelInterfaz.DetalleAviso;
import es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ElementoExpedientePAD;
import es.caib.zonaper.modelInterfaz.EventoExpedientePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.TramiteExpedientePAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.ElementoExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.EventoExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.ExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.IndiceElementoDelegate;
import es.caib.zonaper.persistence.delegate.PadDelegate;
import es.caib.zonaper.persistence.util.ConfigurationUtil;


/**
 * SessionBean con funciones a ser invocadas desde los backoffices
 *
 * @ejb.bean
 *  name="zonaper/persistence/PadBackOfficeFacade"
 *  jndi-name="es.caib.zonaper.persistence.PadBackOfficeFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 *
 */
public abstract class PadBackOfficeFacadeEJB implements SessionBean
{
	
	private Log log = LogFactory.getLog( PadBackOfficeFacadeEJB.class );
	
	private javax.ejb.SessionContext ctx;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException 
	{
		
	}
	
    public void setSessionContext(javax.ejb.SessionContext ctx) 
    {
	   this.ctx = ctx;
    }
	
    /**
	 * Alta de expediente
	 * 
	 * @param expediente Expediente a dar de alta
	 * @return Identificador expediente
	 * @throws ExcepcionPAD
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"   
     */   
	public String altaExpediente( ExpedientePAD expediente ) throws ExcepcionPAD
	{
		log.debug( "Alta expediente " + expediente.getIdentificadorExpediente() );
		
		ConsultaPADDelegate consultaPAD = DelegateUtil.getConsultaPADDelegate();
		
		/*
		 * -------------------------------------------------------------------------------------------------
		 * 
		 * - EXPEDIENTES AUTENTICADOS:
		 *  Por compatiblidad con versiones anteriores a la 1.1.0 en las que no se establecia el nif rpte
		 *  si llega un expediente autenticado en el que no se ha establecido el nif, lo alimentamos
		 *  de forma automatica a partir del usuario seycon
		 *  
		 *  A partir de la version 1.1.0 se puede establecer solo el nif, asi que que a partir del nif 
		 *  se establecera el usuario seycon 
		 *    
		 */
		PersonaPAD representante = null;
		try{
		if (expediente.isAutenticado()){
			
			// Obtenemos usuario asociado
			if (expediente.getIdentificadorUsuario() != null){
				representante = consultaPAD.obtenerDatosPADporUsuarioSeycon(expediente.getIdentificadorUsuario());
				if (representante == null){
					 throw new Exception("No existe persona con el usuario: " + expediente.getIdentificadorUsuario());
				}
			}else if (expediente.getNifRepresentante() != null){
				representante = consultaPAD.obtenerDatosPADporNif(expediente.getNifRepresentante());
				 if (representante == null){
					 throw new Exception("No existe persona con el nif: " + expediente.getNifRepresentante());
				 }
			}else{
				throw new Exception("No se ha informado ni el nif ni el usuario del representante");
			}
			
			// Establecemos datos que faltan
			if (expediente.getNifRepresentante() == null){
				expediente.setNifRepresentante(representante.getNif());
			}else if (expediente.getIdentificadorUsuario() == null){
				expediente.setIdentificadorUsuario(representante.getUsuarioSeycon());				
			}
			
			// Comprobamos que concuerdan los datos
			if (!expediente.getNifRepresentante().equals(representante.getNif()) || 
				!expediente.getIdentificadorUsuario().equals(representante.getUsuarioSeycon())){
					throw new Exception("No concuerda la información del nif y el usuario del representante");
			}
			
		}
		}catch (Exception e) {
			log.debug("Excepcion al comprobar representante expediente: " + e.getMessage(),e);
			throw new ExcepcionPAD("Excepcion al comprobar representante expediente: " + e.getMessage(),e);
		}
		
		/*
		 * 
		 * -------------------------------------------------------------------------------------------------
		 * 
		*/
		
		
		
		Entrada entrada  = null;
		
		ExpedienteDelegate delegate = DelegateUtil.getExpedienteDelegate();
		
		// Comprobamos si ya existe el expediente				
		try {
			if (delegate.obtenerExpediente(expediente.getUnidadAdministrativa().longValue(), expediente.getIdentificadorExpediente()) != null){
				throw new ExcepcionPAD("Ya existe un expediente con codigo: " + expediente.getIdentificadorExpediente());
			}
		}catch (DelegateException e1) {
			log.debug("Excepcion al comprobar si existe expediente: " + e1.getMessage(),e1);
			throw new ExcepcionPAD("Excepcion al comprobar si existe expediente",e1);
		}		
		
		
		// Si se indica el tramite que origina el expediente buscamos tramite y chequeamos enlace
		if (StringUtils.isNotEmpty(expediente.getNumeroEntradaBTE())){
			BteDelegate bte = DelegateBTEUtil.getBteDelegate();
			TramiteBTE entradaBTE=null;
			try {
				entradaBTE = bte.obtenerEntrada(expediente.getNumeroEntradaBTE());
			} catch (Exception e1) {
				log.debug("Excepcion al buscar tramite con numero entrada:" + expediente.getNumeroEntradaBTE(),e1);
				throw new ExcepcionPAD("Excepcion al buscar tramite con numero entrada:" + expediente.getNumeroEntradaBTE(),e1);
			}
			
			// Si no existe entrada generamos error
			if (entradaBTE == null){
				log.debug("No existe entrada en bandeja con numero entrada: " + expediente.getNumeroEntradaBTE());
				throw new ExcepcionPAD("No existe entrada en bandeja con numero entrada: " + expediente.getNumeroEntradaBTE());
			}
			
			// Si el expediente es autenticado comprobamos que el trámite sea autenticado y tenga el mismo usuario
			if (expediente.isAutenticado()) {
				if (entradaBTE.getNivelAutenticacion() == 'A'){
					log.debug("Un expediente autenticado no puede hacer referencia a un tramite anonimo");
					throw new ExcepcionPAD("Un expediente autenticado no puede hacer referencia a un tramite anonimo");
				}else{
					// Chequeamos que el nif del usuario este asociado 
					//if (!entradaBTE.getUsuarioSeycon().equals(expediente.getIdentificadorUsuario())) {
					if (!entradaBTE.getUsuarioNif().equals(expediente.getNifRepresentante())) {
						log.debug("El usuario tramitador del expediente no es el mismo que el del tramite");
						throw new ExcepcionPAD("El usuario tramitador del expediente no es el mismo que el del tramite");
					}
				}
			}else{
			// Si el expediente no es autenticado debe hacer referencia a un tramite anonimo	
				if (entradaBTE.getNivelAutenticacion() != 'A') {
					log.debug("Un expediente no autenticado no puede hacer referencia a un tramite autenticado");
					throw new ExcepcionPAD("Un expediente no autenticado no puede hacer referencia a un tramite autenticado");
				}
			}	
			
			// Localizamos la entrada telematica/preregistro asociada
			try{
				if (entradaBTE.getTipo() == es.caib.xml.registro.factoria.ConstantesAsientoXML.TIPO_ENVIO || 
					entradaBTE.getTipo() == es.caib.xml.registro.factoria.ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA){
						// Entrada telematica
						entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematicaPorNumero(entradaBTE.getNumeroRegistro());					
						if (entrada == null) throw new ExcepcionPAD("No se encuentra registro/envio asociado a la entrada en la zona personal - num reg: " + entrada.getNumeroRegistro());
				}else{
						// Entrada preregistro
						entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroPorNumero(entradaBTE.getNumeroPreregistro());
						if (entrada == null) throw new ExcepcionPAD("No se encuentra preregistro/preenvio asociado a la entrada en la zona personal - num prereg: " + entrada.getNumeroPreregistro());
						// El preregistro debe estar confirmado
						if (StringUtils.isEmpty(entrada.getNumeroRegistro())) throw new ExcepcionPAD("No se puede crear un expediente asociado a un preregistro/preenvio no confirmado - num prereg: " + entrada.getNumeroPreregistro());
				}
			} catch (DelegateException e) {
				log.debug("Excepcion al consultar entrada en PAD: " + e.getMessage(),e);
				throw new ExcepcionPAD("Excepcion al consultar entrada en PAD: " + e.getMessage(),e);
			}
			
			// Comprobamos que la entrada no tenga ya un expediente enlazado
			try{
				if (DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(entrada instanceof EntradaTelematica?ElementoExpediente.TIPO_ENTRADA_TELEMATICA:ElementoExpediente.TIPO_ENTRADA_PREREGISTRO,entrada.getCodigo()) != null){
					throw new ExcepcionPAD("La entrada ya tiene un expediente enlazado");
				}
			}catch(DelegateException ex){
				log.debug("No se ha podido comprobar si la entrada ya tiene un expediente enlazado: " + ex.getMessage(),ex);
				throw new ExcepcionPAD("No se ha podido comprobar si la entrada ya tiene un expediente enlazado: " + ex.getMessage(),ex);
			}
			
		}
		
		
		try{
			// Damos de alta el expediente y en caso necesario el enlace entre expediente y tramite
			Expediente exped = this.expedientePADToExpediente( expediente );
			ElementoExpediente el = new ElementoExpediente();
			if (entrada != null) {
				// Creamos elemento expediente asociado a la entrada
				el.setFecha(entrada.getFecha());
				el.setTipoElemento(entrada instanceof EntradaTelematica?ElementoExpediente.TIPO_ENTRADA_TELEMATICA:ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
				el.setCodigoElemento(entrada.getCodigo());
				exped.addElementoExpediente(el,entrada);
				
				// En caso de que el expediente no tenga establecido la configuracion de avisos y la entrada sí, 
				// cogemos automaticamente la configuracion de la entrada
				if (StringUtils.isEmpty(exped.getHabilitarAvisos())){
					if (StringUtils.isNotEmpty(entrada.getHabilitarAvisos())){
						exped.setHabilitarAvisos(entrada.getHabilitarAvisos());
						exped.setAvisoEmail(entrada.getAvisoEmail());
						exped.setAvisoSMS(entrada.getAvisoSMS());
					}
				}
				
			}					
			
			// Creamos los eventos indicados en el expediente y actualizamos expediente
			EventoExpedienteDelegate evd = DelegateUtil.getEventoExpedienteDelegate();
			for ( Iterator it = expediente.getElementos().iterator(); it.hasNext(); )
			{
				// Guardamos evento
				EventoExpediente evento = eventoExpedientePADToEventoExpediente( ( EventoExpedientePAD ) it.next(), expediente );
				
				// QUITAMOS CONTROL NO NULO Y PONEMOS TXT X DEFECTO PARA HACERLO COMPATIBLE CON VERSION ANTERIOR
				if ( StringUtils.isEmpty( evento.getTitulo() ))
				{
					if ("ca".equals(expediente.getIdioma())){
						evento.setTitulo("Avís de tramitació");
					} else if ("en".equals(expediente.getIdioma())) {
						evento.setTitulo("Processing Notice");
					} else{
						evento.setTitulo("Aviso de tramitación");
					}
				}
				
				Long codigoEvento = evd.grabarEventoExpediente(evento);
				evento.setCodigo(codigoEvento);
				
				// Creamos elemento expediente asociado al evento
				ElementoExpediente ela = new ElementoExpediente();
				ela.setFecha(evento.getFecha());
				ela.setTipoElemento(ElementoExpediente.TIPO_AVISO_EXPEDIENTE);
				ela.setCodigoElemento(evento.getCodigo());
				exped.addElementoExpediente(ela,evento);
			}
			
			// Si el expediente no tiene ningun elemento lo inicializamos con fecha inicio y fecha fin
			// para que se ordene correctamente en las listas (cuando se añadan elementos ya se 
			// recalcularan estas fechas)
			if (exped.getElementos().size() == 0){
				exped.setFechaInicio(new Date());
				exped.setFechaFin(exped.getFechaInicio());
			}
			
			// Guardamos expediente
			delegate.grabarExpediente( exped );
						
			// Realizamos aviso de movilidad si en el alta se han generado eventos
			exped = delegate.obtenerExpediente(exped.getUnidadAdministrativa().longValue(),exped.getIdExpediente());
			for ( Iterator it = exped.getElementos().iterator(); it.hasNext(); ){
				ElementoExpediente ele = (ElementoExpediente) it.next();
				if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE))
					DelegateUtil.getProcesosAutoDelegate().avisoCreacionElementoExpediente(ele);					
			}			
			
			// Generamos indices de busqueda para expedientes y avisos (solo autenticados)
			if (StringUtils.isNotEmpty(exped.getSeyconCiudadano())) {
				crearIndicesExpediente(exped);
			}
			
			
			return expediente.getIdentificadorExpediente();
		} catch (Exception e) {
			log.debug("Excepcion al crear expediente: " + e.getMessage(),e);
			throw new ExcepcionPAD("Excepcion al crear expediente: " + e.getMessage(),e);
		}
	}
	
	

	/**
     
     NO SE PERMITEN BAJAS
 
	public void bajaExpediente( long unidadAdministrativa, String identificadorExpediente)
	{
		bajaExpediente(unidadAdministrativa,identificadorExpediente);
	}
		
	public void bajaExpediente( long unidadAdministrativa, String identificadorExpediente , String claveExpediente)
	{
		log.debug( "Baja expediente " + identificadorExpediente );
		try
		{
			Expediente expediente = DelegateUtil.getExpedienteDelegate().obtenerExpediente( unidadAdministrativa, identificadorExpediente );
			
			// En caso de que el expediente este protegido controlamos que se proporcione la clave correcta
			if (StringUtils.isNotEmpty(expediente.getClaveExpediente())){
				if (!expediente.getClaveExpediente().equals(claveExpediente)) 
					throw new Exception("No se ha proporcionado la clave correcta de acceso al expediente");
			}
			
			
			DelegateUtil.getExpedienteDelegate().borrarExpediente( unidadAdministrativa, identificadorExpediente );
		}
		catch( Exception exc )
		{
			throw new EJBException( exc );
		}
	}
	 */
	
	
	 
    /**
	 * Consulta de expediente
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente
	 * @return Expediente
	 * @throws ExcepcionPAD 	 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
	 */
	public ExpedientePAD consultaExpediente( long unidadAdministrativa, String identificadorExpediente ) throws ExcepcionPAD	
	{
		return consultaExpediente( unidadAdministrativa, identificadorExpediente , null);
	}
	
	 
    /**
	 * Consulta de expediente
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente
	 * @param claveExpediente Clave acceso expediente
	 * @return Expediente
	 * @throws ExcepcionPAD 	 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
	 */
	public ExpedientePAD consultaExpediente( long unidadAdministrativa, String identificadorExpediente , String claveExpediente) throws ExcepcionPAD	
	{
		log.debug( "Consulta expediente " + identificadorExpediente );
					
		ExpedientePAD expPAD = null;
		Expediente expediente=null;
		try {
			expediente = DelegateUtil.getExpedienteDelegate().obtenerExpediente( unidadAdministrativa, identificadorExpediente );
		} catch (DelegateException e) {
			throw new ExcepcionPAD("Excepcion obteniendo expediente",e);
		}
		
		if ( expediente != null )
		{
			
			// En caso de que el expediente este protegido controlamos que se proporcione la clave correcta
			if (StringUtils.isNotEmpty(expediente.getClaveExpediente())){
				if (!expediente.getClaveExpediente().equals(claveExpediente)) 
					throw new ExcepcionPAD("No se ha proporcionado la clave correcta de acceso al expediente");
			}
			
			
			expPAD = expedienteToExpedientePAD( expediente );
		}
		return expPAD;
		
	}

	
	/**
	 * Alta evento expediente
     * 
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente
	 * @param claveExpediente Clave acceso expediente
	 * @param eventoPAD Evento expediente
	 * @throws ExcepcionPAD
	 * 
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
	 */
	public void altaEvento( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, EventoExpedientePAD eventoPAD ) throws ExcepcionPAD	
	{
		log.debug( "Alta evento en identificador expediente " + identificadorExpediente);
		
			Expediente expediente=null;
			try {
				expediente = DelegateUtil.getExpedienteDelegate().obtenerExpediente( unidadAdministrativa, identificadorExpediente );
			} catch (DelegateException e) {
				throw new ExcepcionPAD("Excepcion accediendo al expediente: " + e.getMessage(),e);
			}
			
			if (expediente == null) {
				throw new ExcepcionPAD("No existe expediente con codigo " + identificadorExpediente + " para unidad administrativa " + unidadAdministrativa);
			}
			
			// En caso de que el expediente este protegido controlamos que se proporcione la clave correcta
			if (StringUtils.isNotEmpty(expediente.getClaveExpediente())){
				if (!expediente.getClaveExpediente().equals(claveExpediente)) 
					throw new ExcepcionPAD("No se ha proporcionado la clave correcta de acceso al expediente");
			}
			
			// Las siguientes dos lineas son para establecer las propiedades que el documento obtiene del expediente
			// como por ejemplo la unidad administrativa
			ExpedientePAD expedientePAD = expedienteToExpedientePAD( expediente );
			expedientePAD.addEvento( eventoPAD );
			//
			EventoExpediente ev = eventoExpedientePADToEventoExpediente( eventoPAD, expedientePAD );
			
			// QUITAMOS CONTROL NO NULO Y PONEMOS TXT X DEFECTO PARA HACERLO COMPATIBLE CON VERSION ANTERIOR
			if ( StringUtils.isEmpty( ev.getTitulo() ))
			{
				if ("ca".equals(expedientePAD.getIdioma())){
					ev.setTitulo("Avís de tramitació");
				}else{
					ev.setTitulo("Aviso de tramitación");
				}
			}			
			
			// Actualizamos fecha del expediente
			expediente.setFecha( new Timestamp( new Date().getTime()));
			// Y lo establecemos como no consultado.
			expediente.setFechaConsulta( null );
									
			// Añadimos como elemento del expediente y lo guardamos
			try {
				
				// Guardamos evento
				Long codigoEvento = DelegateUtil.getEventoExpedienteDelegate().grabarEventoExpediente(ev);
				ev.setCodigo(codigoEvento);
				
				// Creamos elemento expediente asociado al evento
				ElementoExpediente el = new ElementoExpediente();
				el.setFecha(ev.getFecha());
				el.setTipoElemento(ElementoExpediente.TIPO_AVISO_EXPEDIENTE);
				el.setCodigoElemento(ev.getCodigo());
				expediente.addElementoExpediente(el,ev);
				DelegateUtil.getExpedienteDelegate().grabarExpediente( expediente );
				
				// Aviso de movilidad
				DelegateUtil.getProcesosAutoDelegate().avisoCreacionElementoExpediente(el);				
				
			} catch (Exception e) {
				throw new ExcepcionPAD("Excepcion grabando evento expediente: " + e.getMessage(),e);
			}
			
			
			// Creamos indices de busqueda (solo autenticados)
			try {
				if (StringUtils.isNotEmpty(expediente.getUsuarioSeycon())) {
					crearIndicesEventoExpediente(expediente.getNifRepresentante(), ev);
				}
			} catch (Exception e) {
				throw new ExcepcionPAD("Excepcion creando indices evento expediente: " + e.getMessage(),e);
			}
		
	}	
	
	/**
	 * 
	 * Alta evento expediente
     * 
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente	
	 * @param eventoPAD Evento expediente
	 * @throws ExcepcionPAD
	 *  
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void altaEvento( long unidadAdministrativa, String identificadorExpediente, EventoExpedientePAD eventoPAD ) throws ExcepcionPAD	
	{
		altaEvento(unidadAdministrativa,identificadorExpediente,null,eventoPAD);
	}

	/**
	 * 
	 * Comprueba si un usuario tiene activada su zona personal
     * 
	 * @param nifUsuario Nif usuario
	 * @throws ExcepcionPAD
	 *  
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public boolean existeZonaPersonalUsuario( String nifUsuario ) throws ExcepcionPAD
    {
    	try
    	{
    		PersonaPAD persona = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nifUsuario);
    		return (persona != null);
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error verificando si existe persona en PAD",ex);
    	}
    	
    }	
	
	/**
	 * 
	 * Da de alta un ciudadano en la zona personal
     * 
	 * @param nif Nif
	 * @param nombre Nombre
	 * @param apellido1 Apellido1
	 * @param apellido2 Apellido2 
	 * @throws ExcepcionPAD
	 *  
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String altaZonaPersonalUsuario( String nif, String nombre, String apellido1, String apellido2) throws ExcepcionPAD
    {
    	try
    	{	
    		// Realizamos alta
    		PersonaPAD persona = new PersonaPAD();
    		persona.setNif(nif);
    		persona.setNombre(nombre);
    		persona.setApellido1(apellido1);
    		persona.setApellido2(apellido2);
    		PersonaPAD p = DelegateUtil.getPadAplicacionDelegate().altaPersonaCodigoUsuarioAuto(persona);
    		return p.getUsuarioSeycon();
    		
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error realizando alta usuario en PAD",ex);
    	}
    	
    }	
	
	
	/**
	 * 
	 * Obtiene detalle acuse recibo de una notificacion
     * 
	 * @param numeroRegistro numero registro
	 * @throws ExcepcionPAD
	 *  
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public DetalleAcuseRecibo obtenerDetalleAcuseRecibo(String numeroRegistro) throws ExcepcionPAD
    {
    	try
    	{
    		NotificacionTelematica not = DelegateUtil.getNotificacionTelematicaDelegate().obtenerNotificacionTelematica(numeroRegistro);
    		if (not == null) {
    			return null;
    		}
    		ElementoExpediente elementoExpe = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(ElementoExpediente.TIPO_NOTIFICACION, not.getCodigo());
    		NotificacionExpedientePAD notPAD = (NotificacionExpedientePAD) notificacionExpedienteToNotificacionExpedientePAD(elementoExpe, not);
    		return notPAD.getDetalleAcuseRecibo();
    	}
    	catch( Exception ex )
    	{
    		throw new ExcepcionPAD("Error obteniendo acuse recibo",ex);
    	}
    	
    }	
	
	/**
    
    NO SE PERMITEN BAJAS
	public void bajaEvento( long unidadAdministrativa, String identificadorExpediente, String fechaEvento )	
	{
		bajaEvento(unidadAdministrativa,identificadorExpediente,null,fechaEvento);
	}
	
	
	public void bajaEvento( long unidadAdministrativa, String identificadorExpediente, String claveExpediente, String fechaEvento )	
	{
		log.debug( "Alta de evento " + fechaEvento + " en expediente " + identificadorExpediente );
		try
		{
			Expediente expediente = DelegateUtil.getExpedienteDelegate().obtenerExpediente( unidadAdministrativa, identificadorExpediente );
			
			// En caso de que el expediente este protegido controlamos que se proporcione la clave correcta
			if (StringUtils.isNotEmpty(expediente.getClaveExpediente())){
				if (!expediente.getClaveExpediente().equals(claveExpediente)) 
					throw new Exception("No se ha proporcionado la clave correcta de acceso al expediente");
			}
			
			
			DelegateUtil.getEventoExpedienteDelegate().borrarEventoExpediente( unidadAdministrativa, identificadorExpediente, dateToSqlTimestamp( fechaEvento ) );
		}
		catch( Exception exc )
		{
			throw new EJBException( exc );
		}
	}
	*/
	
	
	/**
	 * Consulta evento expediente
	 * 
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente
	 * @param fechaEvento Fecha evento (formato dd/MM/yyyy HH:mm:ss)
	 * @return Evento expediente
	 * @throws ExcepcionPAD
	 * 
	 * ejb.interface-method
     * ejb.permission role-name="${role.gestor}" 
     * ejb.permission role-name="${role.auto}"
	 
	public EventoExpedientePAD obtenerEventoExpediente( long unidadAdministrativa, String idExpediente, String fechaEvento ) throws ExcepcionPAD
	{
		return obtenerEventoExpediente( unidadAdministrativa, idExpediente, null, fechaEvento );
	}
	*/
	
	/**
	 * 
	 * Consulta evento expediente
	 * 
	 * @param unidadAdministrativa Unidad administrativa al que pertenece el expediente
	 * @param identificadorExpediente Identificador expediente
	 * @param claveExpediente Clave acceso expediente
	 * @param fechaEvento Fecha evento (formato dd/MM/yyyy HH:mm:ss)
	 * @return Evento expediente
	 * @throws ExcepcionPAD
	 * 
	 * ejb.interface-method
     * ejb.permission role-name="${role.gestor}" 
     * ejb.permission role-name="${role.auto}"
     * 
	 
	public EventoExpedientePAD obtenerEventoExpediente( long unidadAdministrativa, String idExpediente,String claveExpediente, String fechaEvento ) throws ExcepcionPAD
	{
		log.debug( "Consulta de evento con fecha " + fechaEvento + " en expediente " + idExpediente );
		
		Expediente expediente=null;
		try {
			expediente = DelegateUtil.getExpedienteDelegate().obtenerExpediente( unidadAdministrativa, idExpediente );
		} catch (DelegateException e) {
			throw new ExcepcionPAD("Excepcion obteniendo expediente",e);
		}
		
		if (expediente == null) {
			throw new ExcepcionPAD("No existe expediente con codigo " + idExpediente + " para unidad administrativa " + unidadAdministrativa);
		}
		
		// En caso de que el expediente este protegido controlamos que se proporcione la clave correcta
		if (StringUtils.isNotEmpty(expediente.getClaveExpediente())){
			if (!expediente.getClaveExpediente().equals(claveExpediente)) 
				throw new ExcepcionPAD("No se ha proporcionado la clave correcta de acceso al expediente");
		}
		
		EventoExpedientePAD eventoPAD = null; 
		EventoExpediente eventoExpediente=null;
		try {
			eventoExpediente = DelegateUtil.getEventoExpedienteDelegate().obtenerEventoExpediente( unidadAdministrativa, idExpediente, dateToSqlTimestamp(  fechaEvento ) );
		} catch (DelegateException e) {
			throw new ExcepcionPAD("Excepcion obteniendo evento expediente",e);
		} 
		if ( eventoExpediente != null )
		{
			eventoPAD = eventoExpedienteToEventoExpedientePAD( eventoExpediente );
		}
		return eventoPAD;
		
	}
	*/
	
	//-----------------------------------------------------------------------------------------------------------
	//
	//				FUNCIONES DE UTILIDAD
	//
	//-----------------------------------------------------------------------------------------------------------
	private Expediente expedientePADToExpediente( ExpedientePAD expPAD ) throws ExcepcionPAD
	{
		validateExpedientePAD( expPAD );
		Expediente expediente = new Expediente();
		expediente.setIdExpediente( expPAD.getIdentificadorExpediente() );
		expediente.setUnidadAdministrativa(expPAD.getUnidadAdministrativa());
		expediente.setClaveExpediente(expPAD.getClaveExpediente());
		expediente.setIdioma(expPAD.getIdioma());
		expediente.setDescripcion( expPAD.getDescripcion() );		
		expediente.setFecha( new java.sql.Timestamp( new java.util.Date().getTime() ) );
		if (expPAD.isAutenticado()){
			expediente.setSeyconCiudadano( expPAD.getIdentificadorUsuario() );
			expediente.setNifRepresentante(expPAD.getNifRepresentante());
		}
		
		expediente.setNifRepresentado(expPAD.getNifRepresentado());
		expediente.setNombreRepresentado(expPAD.getNombreRepresentado());
	
		// Obtener usuario seycon que crea expediente
		expediente.setUsuarioSeycon( getUsuarioSeycon() );
		
		// Entrada que genera el expediente 
		expediente.setNumeroEntradaBTE(expPAD.getNumeroEntradaBTE());	
		
		// Informacion de avisos
		if (expPAD.getConfiguracionAvisos().getHabilitarAvisos() != null){
			expediente.setHabilitarAvisos(expPAD.getConfiguracionAvisos().getHabilitarAvisos().booleanValue()?"S":"N");
			expediente.setAvisoEmail(expPAD.getConfiguracionAvisos().getAvisoEmail());
			expediente.setAvisoSMS(expPAD.getConfiguracionAvisos().getAvisoSMS());
		}
		
		return expediente;
	}
	
	private void validateExpedientePAD ( ExpedientePAD expPAD ) throws ExcepcionPAD
	{
		// - Campos obligatorios
		if ( expPAD == null )
		{
			throw new ExcepcionPAD( "ExpedientePAD es nulo" );
		}
		if ( StringUtils.isEmpty( expPAD.getIdentificadorExpediente() ) )
		{
			error( ExpedientePAD.class, "identificadorExpediente" );
		}
		if ( expPAD.getUnidadAdministrativa() == null )
		{
			error( ExpedientePAD.class, "unidadAdministrativa" );
		}
		if ( StringUtils.isEmpty( expPAD.getDescripcion() ) )
		{
			error( ExpedientePAD.class, "descripcion" );
		}
		
		// - Control autenticacion
		if ( expPAD.isAutenticado() )
		{	
			// Si esta autenticado debe establecer el id usuario
			if (StringUtils.isEmpty( expPAD.getIdentificadorUsuario())){ 
				error( ExpedientePAD.class, "identificadorUsuario" );
			}else{			
				// Comprobamos si existe usuario en la PAD
				try{
					PadDelegate pad = DelegatePADUtil.getPadDelegate();
					if (!pad.existePersonaPADporUsuario(expPAD.getIdentificadorUsuario())){
						throw new ExcepcionPAD("No existe un usuario con identificador '" + expPAD.getIdentificadorUsuario() + "' definido en la PAD");
					}
				}catch(Exception ex){
					throw new ExcepcionPAD("No se ha podido consultar si existe un usuario con identificador '" + expPAD.getIdentificadorUsuario() + "' definido en la PAD",ex);
				}
			}			
		}else{
			// Si no esta autenticado es obligatorio establecer el numero de entrada BTE (sino tendriamos expte inaccesible)
			if (StringUtils.isEmpty( expPAD.getNumeroEntradaBTE())){
				throw new ExcepcionPAD("Si el expediente no es autenticado debe establecer obligatoriamente el tramite de inicio (propiedad numeroEntradaBTE)");
			}
		}
		
		// - Control idioma
		if (expPAD.getIdioma() == null){
			throw new ExcepcionPAD("Debe especificarse el idioma de tramitacion del expediente");
		}
		if (!expPAD.getIdioma().equals("es") && !expPAD.getIdioma().equals("ca") && !expPAD.getIdioma().equals("en")){
			throw new ExcepcionPAD("El idioma de tramitacion del expediente debe ser 'es', 'ca' o 'en'");
		}		
	}
	
	private ExpedientePAD expedienteToExpedientePAD( Expediente expediente ) throws ExcepcionPAD
	{
		ExpedientePAD expPAD = new ExpedientePAD();
		
		expPAD.setIdentificadorExpediente( expediente.getIdExpediente() );
		expPAD.setUnidadAdministrativa( expediente.getUnidadAdministrativa() );
		
		expPAD.setClaveExpediente(expediente.getClaveExpediente());
		expPAD.setIdioma(expediente.getIdioma());
		
		expPAD.setDescripcion( expediente.getDescripcion() );
		
		if (!StringUtils.isEmpty(expediente.getSeyconCiudadano())){
			expPAD.setAutenticado(true);
			expPAD.setIdentificadorUsuario( expediente.getSeyconCiudadano() );
			expPAD.setNifRepresentante( expediente.getNifRepresentante());
		}else{
			expPAD.setAutenticado(false);
		}
		
		expPAD.setNifRepresentado(expediente.getNifRepresentado());
		expPAD.setNombreRepresentado(expediente.getNombreRepresentado());
		
		expPAD.setNumeroEntradaBTE(expediente.getNumeroEntradaBTE());
		
		if (StringUtils.isNotEmpty(expediente.getHabilitarAvisos())){
			expPAD.getConfiguracionAvisos().setHabilitarAvisos(new Boolean("S".equals(expediente.getHabilitarAvisos())));
			expPAD.getConfiguracionAvisos().setAvisoEmail(expediente.getAvisoEmail());
			expPAD.getConfiguracionAvisos().setAvisoSMS(expediente.getAvisoSMS());
		}
		
		ElementoExpedienteDelegate eed = DelegateUtil.getElementoExpedienteDelegate();
		ElementoExpedienteItf dee = null;
		ElementoExpediente ele = null;
		ElementoExpedientePAD elp = null;
		for ( Iterator it = expediente.getElementos().iterator(); it.hasNext();  )
		{
			ele = ( ElementoExpediente ) it.next();
			
			// Obtenenemos detalle elemento expediente
			try{
				dee = eed.obtenerDetalleElementoExpediente(ele.getCodigo());
			}catch(DelegateException de){
				throw new ExcepcionPAD("No se puede obtener el detalle del elemento expediente " + ele.getCodigo().longValue(),de);
			}
			
			// Elemento aviso
			if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)) {
				elp = eventoExpedienteToEventoExpedientePAD((EventoExpediente) dee);				
			}else if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO) || 
					  ele.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)) {
				elp = tramiteExpedienteToTramiteExpedientePAD( (Entrada) dee );
			}else if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)) {
				elp = notificacionExpedienteToNotificacionExpedientePAD(ele, (NotificacionTelematica) dee );
			}else{
				throw new ExcepcionPAD("Tipo de elemento expediente no soportado: " + ele.getTipoElemento());
			}
			
			// Añadimos a lista elementos expediente
			expPAD.getElementos().add(elp);
			
		}
		return expPAD;
	}
	
	private ElementoExpedientePAD notificacionExpedienteToNotificacionExpedientePAD(ElementoExpediente elementoExpediente, NotificacionTelematica notificacion) throws ExcepcionPAD {
		
		// Obtenemos datos del oficio de remision
		OficioRemision oficio = getOficioRemision(notificacion);
		
		NotificacionExpedientePAD nep = new NotificacionExpedientePAD();
		nep.setFecha( StringUtil.fechaACadena(notificacion.getFechaRegistro(),ElementoExpedientePAD.FORMATO_FECHA) );
		nep.setNumeroRegistro(notificacion.getNumeroRegistro());
		nep.setTituloOficio(oficio.getTitulo());
		nep.setTextoOficio(oficio.getTitulo());
		nep.setRequiereAcuse(notificacion.isFirmarAcuse());
		
		// Obtenemos detalle acuse
		DetalleAcuseRecibo detalleAcuse = new DetalleAcuseRecibo();
		detalleAcuse.setFechaAcuseRecibo(notificacion.getFechaAcuse());
		if (notificacion.isRechazada()) {
			detalleAcuse.setEstado(DetalleAcuseRecibo.ESTADO_RECHAZADA);
		} else if (notificacion.getFechaAcuse() != null) {
			detalleAcuse.setEstado(DetalleAcuseRecibo.ESTADO_ENTREGADA);
		} else {
			detalleAcuse.setEstado(DetalleAcuseRecibo.ESTADO_PENDIENTE);
		}
		if (notificacion.getCodigoRdsAcuse() > 0) {
			detalleAcuse.setCodigoRdsAcuseRecibo(new Long(notificacion.getCodigoRdsAcuse()));
			detalleAcuse.setClaveRdsAcuseRecibo(notificacion.getClaveRdsAcuse());
		}
		detalleAcuse.setAvisos(obtenerDetalleAvisosElementoExpediente(elementoExpediente.getCodigoAviso()));
		nep.setDetalleAcuseRecibo(detalleAcuse);
		
		// Establecemos documentos notificacion
		DocumentoNotificacionTelematica docNotif = null;
		DocumentoExpedientePAD docPAD = null;
		for (Iterator it=notificacion.getDocumentos().iterator();it.hasNext();){
			docNotif = (DocumentoNotificacionTelematica) it.next();
			docPAD = new DocumentoExpedientePAD();
			docPAD.setCodigoRDS(new Long(docNotif.getCodigoRDS()));
			docPAD.setClaveRDS(docNotif.getClaveRDS());
			docPAD.setTitulo(docNotif.getDescripcion());
			nep.getDocumentos().add(docPAD);
		}
		
		return nep;
	}

	private List obtenerDetalleAvisosElementoExpediente(String codigoAviso) throws ExcepcionPAD {
		if (StringUtils.isEmpty(codigoAviso)) {
			return null;
		}
		try {
			EstadoMensajeEnvio estadoMensaje = DelegateMobTraTelUtil.getMobTraTelDelegate().estadoMensaje(codigoAviso);
			if (estadoMensaje == null) {
				return null;
			}
			List avisos = new ArrayList();
			if (estadoMensaje.getEstadoEmails() != null) {
				for (Iterator it = estadoMensaje.getEstadoEmails().iterator(); it.hasNext();) {
					EstadoMensajeEnvioEmail eme = (EstadoMensajeEnvioEmail) it.next();
					DetalleAviso da = new DetalleAviso();
					da.setTipo(DetalleAviso.TIPO_EMAIL);
					da.setDestinatario(convertirListaDestinatarios(eme.getDestinatarios()));				
					if (eme.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO) {
						da.setEnviado(true);
						da.setFechaEnvio(eme.getFechaFinEnvio());
						da.setConfirmarEnvio(eme.isVerificacionEnvio());
						if (eme.getEstadoVerificacionEnvio() == ConstantesMobtratel.CONFIRMACION_OK) {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_ENVIADO);
						} else if (eme.getEstadoVerificacionEnvio() == ConstantesMobtratel.CONFIRMACION_KO) {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_NO_ENVIADO);
						} else {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_DESCONOCIDO);
						}						
					} else {
						da.setEnviado(false);
					}
					avisos.add(da);
				}
			}
			if (estadoMensaje.getEstadoSmss() != null) {
				for (Iterator it = estadoMensaje.getEstadoSmss().iterator(); it.hasNext();) {
					EstadoMensajeEnvioSms ems = (EstadoMensajeEnvioSms) it.next();
					DetalleAviso da = new DetalleAviso();
					da.setTipo(DetalleAviso.TIPO_SMS);
					if (ems.getEstado() == ConstantesMobtratel.ESTADOENVIO_ENVIADO) {
						da.setEnviado(true);
						da.setFechaEnvio(ems.getFechaFinEnvio());
						da.setConfirmarEnvio(ems.isVerificacionEnvio());
						if (ems.getEstadoVerificacionEnvio() == ConstantesMobtratel.CONFIRMACION_OK) {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_ENVIADO);
						} else if (ems.getEstadoVerificacionEnvio() == ConstantesMobtratel.CONFIRMACION_KO) {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_NO_ENVIADO);
						} else {
							da.setConfirmadoEnvio(DetalleAviso.CONFIRMADO_DESCONOCIDO);
						}					
						da.setDestinatario(convertirListaDestinatarios(ems.getDestinatarios()));	
					} else {
						da.setEnviado(false);
					}
					avisos.add(da);
				}
			}
			return avisos;
		} catch (es.caib.mobtratel.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("No se puede recuperar el estado del mensaje envio " + codigoAviso + " asociado al elemento de expediente");
		}
		
	}
	
	private String convertirListaDestinatarios(String[] destinatarios) {
		if (destinatarios == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(destinatarios.length * 25);
		for(int i = 0; i < destinatarios.length; i++)
		{
			if (i > 0) {
				sb.append(",");
			}
			sb.append(destinatarios[i]);
						
		}
		String result = sb.toString();
		return result;
	}

	private ElementoExpedientePAD tramiteExpedienteToTramiteExpedientePAD(Entrada entrada) {
		TramiteExpedientePAD tep = new TramiteExpedientePAD();
		tep.setFecha( StringUtil.fechaACadena(entrada.getFecha(),ElementoExpedientePAD.FORMATO_FECHA) );
		tep.setTipo(entrada.getTipo());
		tep.setNumeroRegistro((tep.getTipo() == ConstantesAsientoXML.TIPO_PREENVIO || tep.getTipo() == ConstantesAsientoXML.TIPO_PREREGISTRO)?entrada.getNumeroPreregistro():entrada.getNumeroRegistro() );
		tep.setDescripcion(entrada.getDescripcionTramite());
		return tep;
	}

	private EventoExpedientePAD eventoExpedienteToEventoExpedientePAD( EventoExpediente evento ) throws ExcepcionPAD
	{
		EventoExpedientePAD eventoPAD = new EventoExpedientePAD();
		eventoPAD.setEnlaceConsulta( evento.getEnlaceConsulta() );
		eventoPAD.setFecha(StringUtil.fechaACadena(new Date(evento.getFecha().getTime()),ElementoExpedientePAD.FORMATO_FECHA));
		eventoPAD.setTitulo( evento.getTitulo() );
		eventoPAD.setTexto( evento.getTexto() );
		eventoPAD.setTextoSMS( evento.getTextoSMS() );
		
		DocumentoEventoExpediente documento = null;
		DocumentoExpedientePAD docPAD = null;
		for ( Iterator it = evento.getDocumentos().iterator(); it.hasNext();  )
		{
			// Consulta de documento expediente: sólo devolvemos título y código/clave RDS
			documento = ( DocumentoEventoExpediente ) it.next();
			docPAD = new DocumentoExpedientePAD();
			docPAD.setTitulo( documento.getTitulo() );
			docPAD.setClaveRDS( documento.getRdsClave() );
			docPAD.setCodigoRDS( documento.getRdsCodigo() );
			eventoPAD.addDocumento(docPAD);
		}
		
		return eventoPAD;
	}
	
	private EventoExpediente eventoExpedientePADToEventoExpediente( EventoExpedientePAD eventoPAD, ExpedientePAD expe ) throws ExcepcionPAD
	{
		validateEventoExpedientePAD( eventoPAD );
		EventoExpediente evento = new EventoExpediente();
		evento.setFecha(new Timestamp(System.currentTimeMillis()));
		evento.setEnlaceConsulta( eventoPAD.getEnlaceConsulta() );		
		evento.setTitulo(eventoPAD.getTitulo());
		evento.setTexto( eventoPAD.getTexto() );
		evento.setTextoSMS( eventoPAD.getTextoSMS() );
		evento.setUsuarioSeycon( getUsuarioSeycon() );
		int index=0;
		for ( Iterator it = eventoPAD.getDocumentos().iterator(); it.hasNext();  )
		{
			evento.addDocumento( documentoExpedientePADToDocumentoEventoExpediente( index, ( DocumentoExpedientePAD ) it.next() ,expe, evento.getFecha()));
			index++;
		}
		return evento;
	}
	
	private void validateEventoExpedientePAD( EventoExpedientePAD eventoPAD ) throws ExcepcionPAD
	{
		if ( eventoPAD == null )
		{
			throw new ExcepcionPAD( "EventoExpedientePAD es nulo" );
		}
		if ( StringUtils.isEmpty( eventoPAD.getTexto() ))
		{
			error( EventoExpedientePAD.class, "texto" );
		}
	}
	
	private DocumentoEventoExpediente documentoExpedientePADToDocumentoEventoExpediente( int index, DocumentoExpedientePAD docPAD, ExpedientePAD expe, Timestamp fechaEvento ) throws ExcepcionPAD
	{
		// Validamos campos documento
		validateDocumentoExpedientePAD( docPAD );
		
		DocumentoEventoExpediente documento = new DocumentoEventoExpediente();
		DocumentoRDS documentoRDS;
		
		// Comprobamos si nos pasan un documento ya existente en el RDS o debemos insertarlo
		if ( docPAD.getCodigoRDS() == null )
		{			
			documentoRDS = crearDocumentoRDS( docPAD, expe );			
		}
		else
		{
			documentoRDS = obtenerDocumentoRDS( docPAD.getCodigoRDS(), docPAD.getClaveRDS(), false );
		}
		
		// Establecemos propiedades documento evento
		documento.setOrden( new Integer(index));
		documento.setTitulo( documentoRDS.getTitulo());
		documento.setRdsCodigo( new Long( documentoRDS.getReferenciaRDS().getCodigo() ));
		documento.setRdsClave( documentoRDS.getReferenciaRDS().getClave() );
		
		// Comprobamos que el documento tenga el uso del evento expediente asociado, si no lo creamos		
		crearUsoEventoExpediente(documentoRDS,expe,fechaEvento);
		
		return documento;
	}
	
	/**
	 * Comprueba si el documento asociado al evento tiene un uso del evento de expediente, si no lo tiene lo crea
	 * @param documento Documento RDS
	 * @param expe Expediente
	 * @param fechaEvento 
	 */
	private void crearUsoEventoExpediente(DocumentoRDS documentoRDS, ExpedientePAD expe, Timestamp fechaEvento) throws ExcepcionPAD{
		try {
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			List usos = rds.listarUsos(documentoRDS.getReferenciaRDS());
			
			// El documento no debe tener usos, de ser exclusivo para el evento
			if (usos.size() > 0){
				throw new Exception("El documento con codigo " + documentoRDS.getReferenciaRDS().getCodigo() + " ya se esta utilizando en el sistema (existen usos asociados)");
			}
			
			// Creamos usos asociados
			UsoRDS uso = new UsoRDS();
			uso.setReferenciaRDS(documentoRDS.getReferenciaRDS());
			uso.setTipoUso(ConstantesRDS.TIPOUSO_EXPEDIENTE);
			uso.setReferencia( expe.getIdentificadorExpediente() + "-" + StringUtil.fechaACadena(fechaEvento,StringUtil.FORMATO_REGISTRO));
			rds.crearUso(uso);
			
			/*	
			 *    MODIFICACION: Anteriormente permitiamos reusar documentos, para no liar la cosa el tipo uso EXPEDIENTE
			 *    se refiere a evento expediente y no se permite reusar documentos 
			 * 		
			boolean existe = false;
			for (Iterator it=usos.iterator();it.hasNext();){
				UsoRDS uso = (UsoRDS) it.next();
				if (uso.getReferencia().equals( expe.getIdentificadorExpediente())) {
					existe = true;
					break;
				}
			}
			
			if (!existe){
				UsoRDS uso = new UsoRDS();
				uso.setReferenciaRDS(documentoRDS.getReferenciaRDS());
				uso.setTipoUso(ConstantesRDS.TIPOUSO_EXPEDIENTE);
				uso.setReferencia( expe.getIdentificadorExpediente());
				rds.crearUso(uso);
			}
			*/
			
			
		}catch (Exception ex){
			throw new ExcepcionPAD("Excepcion al crear uso para documento evento expediente: " + ex.getMessage(),ex);
		}
	}

	private void validateDocumentoExpedientePAD( DocumentoExpedientePAD docPAD ) throws ExcepcionPAD
	{
		if ( docPAD == null )
		{
			throw new ExcepcionPAD( "DocumentoExpedientePAD es nulo" );
		}
		if ( docPAD.getCodigoRDS() != null )
		{
			if ( docPAD.getClaveRDS() == null )
			{
				error( DocumentoExpedientePAD.class, "claveRDS" );
			}
		}
		else
		{
			if ( docPAD.getContenidoFichero() == null )
			{
				error( DocumentoExpedientePAD.class, "contenidoFichero" );
			}
			if ( StringUtils.isEmpty( docPAD.getNombre() ) )
			{
				error( DocumentoExpedientePAD.class, "nombre" );
			}
			if ( StringUtils.isEmpty( docPAD.getTitulo() ) )
			{
				error( DocumentoExpedientePAD.class, "titulo" );
			}
			/*
			if ( StringUtils.isEmpty( docPAD.getModeloRDS() ) )
			{
				error( DocumentoExpedientePAD.class, "modeloRDS" );
			}
			*/
		}
	}
	
	private void error( Class clazz, String propiedad ) throws ExcepcionPAD
	{
		throw new ExcepcionPAD( "La propiedad [" + propiedad+ "] de la clase [" + clazz.getName() + "] no puede ser nula" ); 
	}
	
	private DocumentoRDS crearDocumentoRDS( DocumentoExpedientePAD documento, ExpedientePAD expe ) throws ExcepcionPAD
	{		
		try {
			RdsDelegate rdsDelegate = getRDSDelegate();
			
			DocumentoRDS docRDS = new DocumentoRDS();
			docRDS.setDatosFichero( documento.getContenidoFichero() );
			docRDS.setEstructurado( documento.isEstructurado() );
			docRDS.setFechaRDS( new Date() );
			docRDS.setUnidadAdministrativa( expe.getUnidadAdministrativa().longValue() );
			//docRDS.setNif( getNifUsuarioSeycon() );
			docRDS.setUsuarioSeycon( expe.getIdentificadorUsuario() );
			docRDS.setTitulo( documento.getTitulo() );
			docRDS.setNombreFichero( documento.getNombre() );
			docRDS.setExtensionFichero( obtenerExtensionFichero( documento.getNombre() ) );
			// Se establece un modelo por defecto para todos aquellos documentos que no tienen modelo en la pad
			if ( documento.getModeloRDS() == null )
			{
				docRDS.setModelo( ConstantesRDS.MODELO_ANEXO_GENERICO  ); 
				docRDS.setVersion( 1 );
			}
			else
			{	
				docRDS.setVersion( documento.getVersionRDS() );
				docRDS.setModelo( documento.getModeloRDS() );
			}
			docRDS.setIdioma(expe.getIdioma());
			
			ReferenciaRDS refRDS;		
			refRDS = rdsDelegate.insertarDocumento( docRDS );
			docRDS.setReferenciaRDS( refRDS );
			
			return docRDS;
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("Excepcion creando documento en el RDS",e);
		}

	}
	
	private String getUsuarioSeycon() throws ExcepcionPAD
	{
		Principal callerPrincipal = ctx.getCallerPrincipal(); 
		if ( callerPrincipal == null )
		{	
			throw new ExcepcionPAD( "No existe caller principal java.security.Principal" );
		}
		return  callerPrincipal.getName();
	}
	
	private RdsDelegate getRDSDelegate()
	{
		return DelegateRDSUtil.getRdsDelegate();
	}
	
	private String obtenerExtensionFichero( String nombreFichero )
	{
		if ( nombreFichero != null )
			return nombreFichero.substring( nombreFichero.lastIndexOf( '.' ) + 1 ) ;
		return null;	
	}

	private DocumentoRDS obtenerDocumentoRDS( Long codigoRDS, String claveRDS, boolean recuperarContenidoFichero ) throws ExcepcionPAD
	{
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setClave( claveRDS );
		refRDS.setCodigo( codigoRDS.longValue() );
		try {
			return getRDSDelegate().consultarDocumento( refRDS, recuperarContenidoFichero );
		} catch (es.caib.redose.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("Error consultado documento en el RDS",e);
		}
	}
	
	private OficioRemision getOficioRemision( NotificacionTelematica notificacion ) throws ExcepcionPAD
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			docRDS = obtenerDocumentoRDS(new Long(notificacion.getCodigoRdsOficio()),notificacion.getClaveRdsOficio(),true);
		}catch (Exception ex){
			throw new ExcepcionPAD("Error consultando del RDS el oficio de remision",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLOficioRemision factoria = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			bis = new ByteArrayInputStream(docRDS.getDatosFichero());
			OficioRemision oficio = factoria.crearOficioRemision(bis);
			return oficio;
		}catch (Exception ex){
			throw new ExcepcionPAD("Error parseando oficio de remision",ex);
		}finally{
			try {bis.close();}catch(Exception ex){}
		}
	}
	
	/**
	 * Crea indices de busqueda para el expediente
	 * @param expediente
	 * @throws Exception 
	 */
	private void crearIndicesExpediente(Expediente expediente) throws Exception {
		// Creamos indices expedientes
		Map indices = new HashMap();
    	indices.put("Número expediente",  expediente.getIdExpediente());
    	indices.put("Título", expediente.getDescripcion());
    	if (expediente.getNifRepresentado() != null) {
    		indices.put("Nif representado", expediente.getNifRepresentado());
    		indices.put("Nombre representado", expediente.getNombreRepresentado());
    	}
    	crearIndicesBusqueda(expediente.getNifRepresentante(),IndiceElemento.TIPO_EXPEDIENTE,expediente.getCodigo(), indices);
    	
		// Creamos indices avisos creados con el expediente
    	if (expediente.getElementos() != null) {
    		EventoExpedienteDelegate dlg = DelegateUtil.getEventoExpedienteDelegate();
    		for (Iterator it = expediente.getElementos().iterator(); it.hasNext();) {
    			ElementoExpediente ee = (ElementoExpediente) it.next();
    			if (ee.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE) ) {
    				EventoExpediente eve = dlg.obtenerEventoExpediente(ee.getCodigoElemento());
    				indices = new HashMap();
    				indices.put("Título", eve.getTitulo());
    				indices.put("Texto", eve.getTexto());
    				crearIndicesBusqueda(expediente.getNifRepresentante(),IndiceElemento.TIPO_AVISO_EXPEDIENTE,eve.getCodigo(), indices);
    			}
    		}
    	}
	}
	
	/**
	 * Crea indices de busqueda para el expediente
	 * @param nif 
	 * @param expediente
	 * @throws Exception 
	 */
	private void crearIndicesEventoExpediente(String nif, EventoExpediente evento) throws Exception {
		Map indices = new HashMap();
		indices.put("Título", evento.getTitulo());
		indices.put("Texto", evento.getTexto());
		crearIndicesBusqueda(nif,IndiceElemento.TIPO_AVISO_EXPEDIENTE,evento.getCodigo(), indices);
		
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
	
}
