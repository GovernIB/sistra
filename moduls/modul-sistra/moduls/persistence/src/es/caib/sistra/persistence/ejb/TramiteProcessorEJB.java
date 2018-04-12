package es.caib.sistra.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.Principal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.TransformacionRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ConstantesRegtel;
import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.ConfiguracionFormulario;
import es.caib.sistra.model.ConfiguracionGestorFlujoFormulario;
import es.caib.sistra.model.ConfiguracionTramiteDinamica;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.DatosDesglosadosInteresado;
import es.caib.sistra.model.DatosFormulario;
import es.caib.sistra.model.DatosPago;
import es.caib.sistra.model.DatosRepresentanteCertificado;
import es.caib.sistra.model.DatosSesion;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.InstanciaBean;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.MensajePlataforma;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.ResultadoRegistrar;
import es.caib.sistra.model.TraDocumento;
import es.caib.sistra.model.TraDocumentoNivel;
import es.caib.sistra.model.TraEspecTramiteNivel;
import es.caib.sistra.model.TraMensajePlataforma;
import es.caib.sistra.model.TraMensajeTramite;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.DocumentoConsulta;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.sistra.persistence.plugins.CalculoPago;
import es.caib.sistra.persistence.plugins.ConfiguracionDinamica;
import es.caib.sistra.persistence.plugins.ConfiguracionDinamicaTramitePlugin;
import es.caib.sistra.persistence.plugins.DatosIniciales;
import es.caib.sistra.persistence.plugins.DestinatarioTramite;
import es.caib.sistra.persistence.plugins.PluginDatosInteresadoDesglosado;
import es.caib.sistra.persistence.plugins.PluginFormularios;
import es.caib.sistra.persistence.plugins.PluginPagos;
import es.caib.sistra.persistence.plugins.ProcedimientoDestinoTramite;
import es.caib.sistra.persistence.util.GeneradorAsiento;
import es.caib.sistra.persistence.util.Literales;
import es.caib.sistra.persistence.util.ScriptUtil;
import es.caib.sistra.persistence.util.UtilDominios;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.sistra.plugins.pagos.ConstantesPago;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.sistra.plugins.pagos.PluginPagosIntf;
import es.caib.sistra.plugins.pagos.SesionPago;
import es.caib.sistra.plugins.pagos.SesionSistra;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;
import es.caib.sistra.plugins.sms.PluginSmsIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.DataUtil;
import es.caib.util.NifCif;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.ParametrosTramiteSubsanacionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;
import es.caib.sistra.persistence.util.UsernamePasswordCallbackHandler;
import es.indra.util.pdf.UtilPDF;

// TODO FLUJO TRAMITACION PARA PAGOS
// TODO CONTROLAR XA CUANDO AUTENTICACION Y NO HAY FLUJO, Q EL PASO DE FORMULARIO DE FORMULARIOS NO ESTE COMPLETADO HASTA Q EL REPRESENTANTE NO SEA EL USUARIO Q INICIA SESIÓN
// TODO REVISAR EL PROCESO DE FLUJO, PARA ORDENARLO MEJOR. DATOS INICIADOR, ETC
// TODO FLUJO TRAMITACION: CONTROLAR CUANDO LOS PAGOS SEAN OPCIONALES Y CAMBIE CUMPLIMENTADOR
// TODO FLUJO TRAMITACION: CUANDO UN USUARIO Q NO SEA EL INICIADOR LLEGUE A UN PUNTO QUE SEA PENDIENTE FLUJO, DEBE PASARLO AL INICIADOR PARA Q ESTE EN SU CASO LO REMITA
/**
 * @ejb.bean name="sistra/persistence/TramiteProcessor"
 *           display-name="Name for TramiteProcessor"
 *           description="Description for TramiteProcessor"
 *           jndi-name="es.caib.sistra.persistence.TramiteProcessor"
 *           type="Stateful"
 *           view-type="both"
 *           transaction-type="Container"
 * @ejb.transaction type="Required"
 *
 * @jboss.container-configuration name="InstanciaProcessor Stateful SessionBean"
 *
 *
 */
public class TramiteProcessorEJB implements SessionBean {

	private static Log log = LogFactory.getLog(TramiteProcessorEJB.class);
	private SessionContext context = null;
	private static String URL_CONSULTA = null;

	// Indica si hay debug
	private boolean debugEnabled = false;

	// Datos de inicio de sesión
	private DatosSesion datosSesion = null;

    // Configuración trámite (realizamos accesos a los formularios,anexos y pagos del nivel de autenticación)
    private TramiteVersion tramiteVersion = null ;
    private ArrayList formularios = new ArrayList();
    private ArrayList anexos = new ArrayList();
    private ArrayList pagos = new ArrayList();

    // Pasos trámite
    private int pasoActual=0;
    private ArrayList pasosTramitacion;
	char tipoTramitacion='T'; // Indica tipo de tramitación (en función de firma, pagos y compulsa): telemática (T)/ presencial (P)/ depende (D)

	// Datos instancia trámite en persistencia en la PAD
	private TramitePersistentePAD tramitePersistentePAD;
	private boolean borradoPersistencia; // Indica si se ha borrado de persistencia

	// Datos y configuración de los formularios
	private HashMap datosFormularios = new HashMap();	// Contiene los datos de los formularios cacheados para no acudir al RDS
	private HashMap configuracionFormularios = new HashMap(); // Contiene la configuración dinámica de los formularios. Se recupera al iniciar/cargar trámite
	// Datos de los pagos cacheados para no acceder al RDS
	private HashMap datosPagos = new HashMap();
	// Cacheamos si los documentos han sido firmados para evitar accesos al RDS
	private HashMap firmaDocumentos = new HashMap(); // key/value: referencia rds/signature

	// Resultado Registro: Datos con el resultado del registro del trámite
	//TODO Repasar resultado registro xa consulta
	private ResultadoRegistrar resultadoRegistro = null;

	// Estado tramite para pasarselo al front (inicializado según nivel de autenticación)
	private TramiteFront tramiteInfo=null;

	// Parametros de inicio del trámite (se almacenarán en tramitePersistentePAD al iniciar un nuevo trámite)
	private Map parametrosInicio;

	// Permite indicar configuración dinamicamente para un trámite (se calcula en script de validacionInicio)
	private ConfiguracionTramiteDinamica configuracionDinamica = new ConfiguracionTramiteDinamica();

	// Indicador que nos servira para controlar que solo se audite una vez el tipo asistente
	private boolean auditadoAsistente=false;

	// Indica si aceptamos notificacion telematica (en caso de que se permita)
	private Boolean habilitarNotificacionTelematica;

	// Indica email de aviso
	private String emailAviso;

	// Indica sms de aviso
	private String smsAviso;

	// Indica si es entorno de desarrollo
	private boolean entornoDesarrollo;

	// Indica si es un tramite de subsanacion
	private boolean subsanacion=false;

	// Indica si son obligatorios los avisos para las notificaciones
	private boolean avisosObligatoriosNotif = false;

	// Upload temporal de documentos en paso anexar
	private Map uploadAnexos = new HashMap();

	// Codigo enviado por SMS para verificacion de movil
	private String codigoSmsVerificarMovil = null;

	// Indica si se ha verificado el movil
	private boolean verificadoMovil;
	
	public TramiteProcessorEJB() {
		super();
	}

	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
				context = ctx;
	}

	/**
	 * Default create method
	 *
	 * @ejb.permission role-name="${role.todos}"
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		debug("ejbRemove");
	}

	public void ejbActivate() throws EJBException, RemoteException {
		String id="";
    	try
    	{
    		if (tramitePersistentePAD != null) {
    			id = tramitePersistentePAD.getIdPersistencia();
    		}
    	}catch(Exception ex){
    		log.error("Error obteniendo id persistencia: " + ex.getMessage(),ex);
    	}
		debug("ejbActivate  - [Id Persistencia: " + id + "]");

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		String id="";
    	try
    	{
    		if (tramitePersistentePAD != null) {
    			id = tramitePersistentePAD.getIdPersistencia();
    		}
    	}catch(Exception ex){
    		log.error("Error obteniendo id persistencia: " + ex.getMessage(),ex);
    	}
		debug("ejbPassivate - [Id Persistencia: " + id + "]");
	}

	/**
	 * Default create method
	 *
	 * @throws CreateException
	 * @ejb.create-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public void ejbCreate(String tramite, int version, char nivelAutenticacion, Locale idioma, Map parametrosInicio,
						  String perfilAcceso,String nifEntidadDelegada) throws CreateException {

		debug("ejbCreate");
		try{
			// Obtenemos datos usuario
			obtenerDatosSesion(nivelAutenticacion,idioma,perfilAcceso,nifEntidadDelegada);

			// Cargamos definición trámite
			TramiteVersionDelegate td = DelegateUtil.getTramiteVersionDelegate();
			tramiteVersion = td.obtenerTramiteVersionCompleto(tramite,version);
			if (tramiteVersion == null) throw new Exception("No existe tramite '" + tramite + "' version " + version);

			// Debug
			this.debugEnabled = "S".equals(tramiteVersion.getDebugEnabled());

			// Establecemos como idioma el de la sesion, si no esta soportado establecemos por defecto el primer idioma soportado
			if (StringUtils.isEmpty(tramiteVersion.getIdiomasSoportados())){
				throw new Exception("No esta soportado ningun idioma para el tramite");
			}
			if (tramiteVersion.getIdiomasSoportados().indexOf(idioma.getLanguage()) == -1) {
				StringTokenizer st = new StringTokenizer(tramiteVersion.getIdiomasSoportados(),",");
				String idiomaNew = st.nextToken();
				tramiteVersion.setCurrentLang(idiomaNew);
				datosSesion.setLocale(new Locale(idiomaNew));
			}else{
				tramiteVersion.setCurrentLang(idioma.getLanguage());
			}

			//  Parámetros de inicio (sólo se almacenarán si se inicia un nuevo trámite)
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();

			// Comprobamos si los parametros de inicio referencian a parametros de inicio de un tramite de subsanacion
			if (parametrosInicio.containsKey(ConstantesZPE.SUBSANACION_PARAMETER_KEY)){
				this.subsanacion = true;
				String key = (String) parametrosInicio.get(ConstantesZPE.SUBSANACION_PARAMETER_KEY);
				ParametrosTramiteSubsanacionPAD pts = DelegatePADUtil.getPadDelegate().recuperaParametrosTramiteSubsanacion(key);
				this.parametrosInicio = pts.getParametros();
				if (this.parametrosInicio == null){
					this.parametrosInicio = new HashMap();
				}
				this.parametrosInicio.put(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_ID,pts.getExpedienteCodigo());
				this.parametrosInicio.put(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_UA,pts.getExpedienteUnidadAdministrativa().toString());
			}else{
				this.parametrosInicio = parametrosInicio;
			}
			this.URL_CONSULTA = props.getProperty("backoffice.url");

			// Indica si es entorno de desarrollo o pruebas
			this.entornoDesarrollo = "DESARROLLO".equals(props.getProperty("entorno"));

			// Indica si son obligatorios los avisos para las notificaciones
			this.avisosObligatoriosNotif = "true".equals(props.getProperty("sistra.avisoObligatorioNotificaciones"));

		} catch (Exception e) {
            throw new EJBException("Excepcion al iniciar tramite: " + e.getMessage(), e);
        }
	}

	/**
	 * Obtiene InstanciaBean para poder serializarlo en la capa de front
	 * Para deserializarlo basta con crear EJB y si se ha guardado en la Pad cargarlo
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public InstanciaBean obtenerInstanciaBean(){
		InstanciaBean instancia = new InstanciaBean();
		instancia.setIdTramite(this.tramiteVersion.getTramite().getIdentificador());
		instancia.setVersion(this.tramiteVersion.getVersion());
		instancia.setNivelAutenticacion(datosSesion.getNivelAutenticacion());
		instancia.setLocale(datosSesion.getLocale());
		if (this.tramitePersistentePAD != null){
			 instancia.setIdPersistencia(tramitePersistentePAD.getIdPersistencia());
		}
		return instancia;
	}

	/**
	 * Obtiene info tramite.
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public RespuestaFront obtenerInfoTramite(){
		return generarRespuestaFront(null, null);
	}
	
	

	/**
	 * Obtiene InstanciaBean para poder serializarlo en la capa de front
	 * Para deserializarlo basta con crear EJB y si se ha guardado en la Pad cargarlo
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public RespuestaFront pasoActual(){
		// Vamos a paso actual
		return this.irAPaso(this.pasoActual);
	}


	/**
	 * Obtiene informacion inicial previa a iniciar o cargar el trámite.
	 * Devuelve los siguientes parámetros:
	 * 	- descripcion: Descripcion del trámite
	 *  - niveles: Niveles de autenticación soportados ( cadena compuesta por los carácteres CUA)
	 *  - diaspersistencia: Dias persistencia
	 *  - datossesion: Datos de sesion
	 *  - isTramiteReducido : Booleano que indica si es un trámite reducido
	 * @ejb.interface-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public RespuestaFront informacionInicial(){
		try{
    		debug(mensajeLog("Obtiene información para mostrar el login"));

    		// Nivel con el que se ha iniciado sesión
    		char nivel = this.datosSesion.getNivelAutenticacion();

    		// Obtenemos niveles soportados
    		String ls_niveles="";
    		for (Iterator it = this.tramiteVersion.getNiveles().iterator();it.hasNext();){
    			ls_niveles += ((TramiteNivel) it.next()).getNivelAutenticacion();
    		}

    		// Devolvemos como parámetros la descripción del trámite y los niveles
    		HashMap param = new HashMap();
    		RespuestaFront res = new RespuestaFront();
    		param.put("identificador", this.tramiteVersion.getTramite().getIdentificador());
    		param.put("descripcion", ((TraTramite) this.tramiteVersion.getTramite().getTraduccion( this.datosSesion.getLocale().getLanguage())).getDescripcion());
    		param.put("niveles",ls_niveles);
    		// En caso de pasar un nivel obtenemos dias persistencia del nivel
    		String ls_dias = Integer.toString(((EspecTramiteNivel) this.tramiteVersion.getEspecificaciones()).getDiasPersistencia()) ;
    		if (nivel == 'C' || nivel == 'A' || nivel == 'U'){
    			int li_diasNivel = ((EspecTramiteNivel) this.tramiteVersion.getEspecificaciones()).getDiasPersistencia();
    			if (li_diasNivel > 0)
    				ls_dias = Integer.toString(li_diasNivel);
    		}
    		param.put("diaspersistencia",ls_dias);
    		param.put("datossesion",this.datosSesion);
    		param.put( "isTramiteReducido", new Boolean( this.isCircuitoReducido( this.tramiteVersion, nivel ) ) );
    		res.setParametros(param);
	    	return res;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception"),e);
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront res = new RespuestaFront();
    		res.setMensaje(mens);

    		setRollbackOnly();
    		return res;
    	}
	}


	/**
	 * Obtiene lista de trámites que el usuario tiene en persistencia pendientes de completar
	 *
	 * 	- Descripcion del trámite
	 *  - Niveles de autenticación soportados
	 *  - En caso de que exista flujo
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name="${role.todos}"
	 */
	public RespuestaFront obtenerTramitesPersistencia(String tramite,int version){
		try{
			debug(mensajeLog("Obtener lista de trámites en zona persistencia"));

			// Comprobamos que el caller no sea anónimo
    		if (datosSesion.getNivelAutenticacion() == 'A') throw new Exception("Usuario anónimo no puede cargar tramites de persistencia");

    		// Obtenemos los trámites en persistencia para el usuario
    		PadDelegate pad = DelegatePADUtil.getPadDelegate();
    		List tramitesPersistentes = pad.obtenerTramitesPersistentesUsuario(tramite,version);

    		// Para los trámites que son remitidos por/a otro usuario calculamos datos del usuario Seycon
    		HashMap usuariosPAD = new HashMap();
    		String nomUsua;
    		PersonaPAD pers;
    		for (Iterator it=tramitesPersistentes.iterator();it.hasNext();){
    			TramitePersistentePAD t = (TramitePersistentePAD) it.next();
    			if (t.getNivelAutenticacion() != 'A' && !t.getUsuario().equals(t.getUsuarioFlujoTramitacion())){
    				if (!usuariosPAD.containsKey(t.getUsuario())){
    					pers=this.obtenerDatosPADporUsuarioSeycon(t.getUsuario());
    					if (pers != null) nomUsua = pers.getNombreCompleto();
    					else nomUsua = "Usuario no registrado";
    					usuariosPAD.put(t.getUsuario(),nomUsua);
    				}
    				if (!usuariosPAD.containsKey(t.getUsuarioFlujoTramitacion())){
    					pers=this.obtenerDatosPADporUsuarioSeycon(t.getUsuarioFlujoTramitacion());
    					if (pers != null) nomUsua = pers.getNombreCompleto();
    					else nomUsua = "Usuario no registrado";
    					usuariosPAD.put(t.getUsuarioFlujoTramitacion(),nomUsua);
    				}
    			}
    		}

    		// Devolvemos como parámetros la descripción del trámite y los niveles
    		HashMap param = new HashMap();
    		RespuestaFront res = new RespuestaFront();
    		param.put("tramites",tramitesPersistentes);
    		param.put("usuariosPAD",usuariosPAD);
    		res.setParametros(param);
	    	return res;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception"),e);
    		setRollbackOnly();
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront res = new RespuestaFront();
    		res.setMensaje(mens);
    		return res;
    	}
	}

    /**
     * Inicia trámite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront iniciarTramite() {
    	String res = "N";
    	String  mensAudit="";
    	try{
    		// Protegemos en caso de que el trámite ya ha sido inicializado
    		if (tramitePersistentePAD != null) return this.irAPaso(pasoActual);
    		debug(mensajeLog("Iniciar tramite nivel autenticacion " +  datosSesion.getNivelAutenticacion()));
    		// Comprobamos si el idioma esta soportado, si no esta disponible el idioma que se pide se intentará en es o ca.
    		ajustarIdiomaIniciar();
	    	// Obtenemos pasos
	    	calcularPasos();
	    	// Inicializamos formularios guardando en PAD y RDS
	    	inicializarTramite();
	    	// Validar acceso
    		validarAcceso();
	    	// Vamos a primer paso
	    	res = "S";
	    	return irAPaso(0);
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al iniciar tramite",pe);
    		//log.error(mensajeLog("ProcessorException al iniciar tramite"),pe);
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		if (MensajeFront.MENSAJE_TRAMITEINACTIVO.equals(pe.getCodigoError()))
    		{
    			mensAudit = getMensajeInactividad( MensajeFront.MENSAJE_TRAMITEINACTIVO );

    		}
    		else
    		{
    			mensAudit = traducirMensaje(pe);
    		}
    		mens.setMensaje(mensAudit);
    		mens.setMensajeExcepcion(pe.getMessage());
    		RespuestaFront resFront =  generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al iniciar tramite"),e);
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());
    		mensAudit = e.getMessage();
    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		logAuditoria(ConstantesAuditoria.EVENTO_INICIO_TRAMITE,res,mensAudit,null, null, true);
    	}
    }

    /**
     * Carga un trámite de la zona de persistencia
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront cargarTramite(String idPersistencia) {
    	String res = "N";
    	String  mensAudit="";
    	try{
    		// Protegemos en caso de que el trámite ya ha sido inicializado
    		if (tramitePersistentePAD != null) return this.irAPaso(pasoActual);

    		debug(mensajeLog("Cargar tramite persistente " + idPersistencia + " con nivel autenticacion " + datosSesion.getNivelAutenticacion()));

    		// Comprobamos si existe tramite en la PAD
    		existeTramitePad(idPersistencia);
    		// Ajustamos idioma sesion en funcion del idioma del tramite
    		ajustarIdiomaCargar(idPersistencia);
	    	// Obtenemos pasos
	    	calcularPasos();
	    	// Inicializamos formularios guardando en PAD y RDS
	    	cargarTramitePad(idPersistencia);
    		// Validar acceso
	    	validarAcceso();

	    	// Evaluamos estado del trámite y establecemos paso actual
	    	// -- Recorremos los demás pasos evaluando estado
	    	int paso = 0;
	    	String estado;
	    	boolean pendienteFlujo = false;
	    	for (int i=0;i<pasosTramitacion.size();i++){
	    		estado = this.evaluarEstadoPaso(i);
	    		((PasoTramitacion) pasosTramitacion.get(i)).setCompletado(estado);
	    		if (!estado.equals(PasoTramitacion.ESTADO_COMPLETADO)){

	    			// Si estamos en paso rellenar  y esta pendiente de flujo
    				// y el siguiente es anexar pasamos a siguiente paso
	    			if (((PasoTramitacion) pasosTramitacion.get(i)).getTipoPaso() == PasoTramitacion.PASO_RELLENAR &&
	    				 estado.equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO) &&
	    				 ((PasoTramitacion) pasosTramitacion.get(i + 1)).getTipoPaso() == PasoTramitacion.PASO_ANEXAR ){
	    				pendienteFlujo=true;
	    				continue;
	    			}

	    			//  En caso contrario lo damos como paso actual
	    			paso = i;
	    			break;
	    		}else{
	    			// Si el paso Anexar esta completado, comprobamos que el paso de rellenar no
	    			// este pendiente de flujo
	    			if (((PasoTramitacion) pasosTramitacion.get(i)).getTipoPaso() == PasoTramitacion.PASO_ANEXAR &&
	    					pendienteFlujo){
	    				paso = i;
		    			break;
	    			}
	    		}
	    	}

	    	// Vamos a paso actual
	    	RespuestaFront respuesta =  irAPaso(paso);
	    	if (existeError(respuesta)){
	    		res = "N";
	    		mensAudit = respuesta.getMensaje().getMensajeExcepcion();
	    	}
	    	res = "S";

	    	// Validar firmas documentos
	    	if (!validarFirmasDocumentos()){
	    		throw new ProcessorException("No coincide la lista de firmantes en los documentos. Se ha cambiado la especificacion del tramite",MensajeFront.MENSAJE_ERRORCAMBIOESPECIFICACIONES);
	    	}

	    	return respuesta;

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al cargar tramite",pe);
    		// log.error(mensajeLog("ProcessorException al cargar tramite"),pe);
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);

    		if (MensajeFront.MENSAJE_TRAMITEINACTIVO.equals(pe.getCodigoError()))
    		{
    			String mensajeTramiteInactivo = getMensajeInactividad( MensajeFront.MENSAJE_TRAMITEINACTIVO );
    			mens.setMensaje( mensajeTramiteInactivo );

    		}
    		else if(MensajeFront.MENSAJE_TRAMITETERMINADO.equals(pe.getCodigoError()))
    		{
    			mens.setMensaje(pe.getCodigoError());
    		}
    		else
    		{
    			mens.setMensaje(traducirMensaje(pe));
    		}

    		mens.setMensajeExcepcion(pe.getMessage());
    		res = "N";
    		mensAudit = mens.getMensajeExcepcion();
    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al cargar tramite"),e);
    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());
    		res = "N";
    		mensAudit = mens.getMensajeExcepcion();
    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		logAuditoria(ConstantesAuditoria.EVENTO_CARGA_TRAMITE,res,mensAudit,null,null, true);
    	}
    }


	/**
     * Pasa trámite a siguiente usario del flujo de tramitación
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront remitirFlujoTramite() {
    	String res = "N";
    	try{
    		// Actualizamos información del trámite
    		this.actualizarTramiteInfo();

    		// Comprobamos si el trámite tiene flujo
    		if (!this.tramiteInfo.isFlujoTramitacion()){
    			throw new Exception("No se puede pasar el trámite ya que no permite flujo de tramitación");
    		}

    		// Comprobamos que este en estado de pasar
    		if (StringUtils.isEmpty(this.tramiteInfo.getFlujoTramitacionNif())){
    			throw new Exception("No se puede pasar el trámite ya que esta en estado de pasarlo");
    		}

    		// Comprobamos que el usuario a quien se pase tenga usuario Seycon
    		PersonaPAD persona = this.obtenerDatosPADporNif(this.tramiteInfo.getFlujoTramitacionNif());
    		if (persona == null || StringUtils.isEmpty(persona.getUsuarioSeycon())){
    			MensajeFront mens = new MensajeFront();
        		mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
        		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_FLUJO_NO_USUARIO_SEYCON));
        		return generarRespuestaFront(mens,null);
    		}

    		// Pasamos trámite a usuario. Actualizamos PAD indicando que ha habido cambio de flujo.
    		tramitePersistentePAD.setUsuarioFlujoTramitacion(persona.getUsuarioSeycon());
    		this.actualizarPAD(true);

	    	// Mostramos mensaje de que el trámite ha sido remitido
	    	res = "S";
	    	MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_INFO);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_FLUJO_TRAMITE_ENVIADO));
    		return generarRespuestaFront(mens,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al iniciar tramite",pe);
    		//log.error(mensajeLog("ProcessorException al iniciar tramite"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());
    		RespuestaFront resFront = generarRespuestaFront(mens,null);

    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al iniciar tramite"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		//TODO MENSAJE AUDITORIA PARA PASE DE TRAMITE
    		if (res.equals("S")){
    			// logAuditoria(ConstantesAuditoria.EVENTO_INICIO_TRAMITE,res,mensAudit,null);
    		}
    	}
    }

    /**
     * Remite tramite para que otro delegado presente el tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront remitirDelegacionPresentacionTramite() {
    	String res = "N";
    	try{
    		// Actualizamos información del trámite
    		this.actualizarTramiteInfo();

    		// Comprobamos si el trámite esta de forma delegada
    		if (!this.tramiteInfo.getDatosSesion().getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
    			throw new Exception("No se puede pasar el trámite ya que no se ha accedido de forma delegada");
    		}

    		// Establecemos que el tramite esta pendiente de entregarse
    		this.tramitePersistentePAD.setEstadoDelegacion(TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_PRESENTACION);
    		this.actualizarPAD();
    		DelegatePADUtil.getPadDelegate().avisarPendientePresentacionTramite(this.tramitePersistentePAD.getIdPersistencia());

	    	// Mostramos mensaje de que el trámite ha sido remitido
	    	res = "S";
	    	MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_INFO);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_DELEGADO_TRAMITE_PENDIENTE_PRESENTAR));
    		return generarRespuestaFront(mens,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al iniciar tramite",pe);
    		//log.error(mensajeLog("ProcessorException al iniciar tramite"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());
    		RespuestaFront resFront = generarRespuestaFront(mens,null);

    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al iniciar tramite"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		/* 	PENDIENTE MENSAJE AUDITORIA PARA PASE DE TRAMITE
    		if (res.equals("S")){
    			// logAuditoria(ConstantesAuditoria.EVENTO_INICIO_TRAMITE,res,mensAudit,null);
    		}
    		*/
    	}
    }

    /**
     * Remite tramite para que otro delegado firme documentos pendientes de firma
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront remitirDelegacionFirmaDocumentos() {
    	String res = "N";
    	try{
    		// Actualizamos información del trámite
    		this.actualizarTramiteInfo();

    		// Comprobamos si la entidad permite delegacion
    		if (this.datosSesion.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO
					|| !this.tramiteInfo.getDatosSesion().getPersonaPAD().isHabilitarDelegacion()){
    			throw new Exception("No se puede remitir el trámite ya que la entidad no permite delegacion");
    		}

    		// Comprobamos que el estado sea el correcto
    		if (!this.tramiteInfo.isRemitirDelegacionFirma()){
    			throw new Exception("El tramite no esta en estado de remitir a firma");
    		}

    		// Comprobamos si existen docs pendientes de firma
    		/*
    		if (!this.tramiteInfo.getDatosSesion().getPersonaPAD().isHabilitarDelegacion()){
    			throw new Exception("No se puede remitir el trámite ya que la entidad no permite delegacion");
    		}
    		*/

    		// Establecemos que el tramite esta pendiente de firmar documentos
    		this.tramitePersistentePAD.setEstadoDelegacion(TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA);
    		this.actualizarPAD();
    		DelegatePADUtil.getPadDelegate().avisarPendienteFirmarDocumentos(this.tramitePersistentePAD.getIdPersistencia());

	    	// Mostramos mensaje de que el trámite ha sido remitido
	    	res = "S";
	    	MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_INFO);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_DELEGADO_TRAMITE_PENDIENTE_FIRMA));
    		return generarRespuestaFront(mens,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al remitir a firma",pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());
    		RespuestaFront resFront = generarRespuestaFront(mens,null);

    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("ProcessorException al remitir a firma"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		/* 	PENDIENTE MENSAJE AUDITORIA PARA PASE DE TRAMITE
    		if (res.equals("S")){
    			// logAuditoria(ConstantesAuditoria.EVENTO_INICIO_TRAMITE,res,mensAudit,null);
    		}
    		*/
    	}
    }


    /**
     * Avanza paso tramitación
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront siguientePaso() {
    	return irAPaso(pasoActual + 1);
    }

    /**
     * Retorna paso tramitación
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront anteriorPaso() {
    	return irAPaso(pasoActual - 1);
    }

    /**
     * Ir a paso tramitación. Esta es la única función en la que se modifica el contador de paso actual.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront irAPaso(int paso) {
    	boolean auditarAsistente = false;
    	try{
	    	// Comprobamos que el tramite este inicializado
	    	if (this.tramitePersistentePAD == null){
	    		throw new Exception("Tramite no esta inicializado");
	    	}

	    	// Evaluamos que no se salga de los límites
	    	if (paso >= pasosTramitacion.size()) paso = pasosTramitacion.size() - 1;
	    	if (paso < 0) paso = 0;

	    	// Si ya hemos registrado no dejamos volver hacia atrás
	    	int pasoOrigen = ((PasoTramitacion) pasosTramitacion.get(pasoActual)).getTipoPaso();
	    	int pasoDestino= ((PasoTramitacion) pasosTramitacion.get(paso)).getTipoPaso();
	    	if (pasoOrigen == PasoTramitacion.PASO_FINALIZAR)
	    	{
	    		paso = pasoActual;
	    	}

	    	// Evaluamos estado pasos anteriores:
	    	// - Si no hay flujo tramitación: todos los pasos anteriores deben estar completados
	    	// - Si hay flujo tramitación: entre el paso de formularios y anexar se puede navegar si tienen estado PENDIENTE_FLUJO
	    	// - Si hay delegacion:  entre el paso de formularios y anexar se puede navegar si tienen estado DELEGACION_PENDIENTE_FIRMA
	    	PasoTramitacion pasoAct=null;
	    	boolean completados = true;
	    	for (int i=0;i<paso;i++){
	    		pasoAct = (PasoTramitacion) pasosTramitacion.get(i);
	    		if (!pasoAct.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO)) {

	    			// Si vamos a paso anexar, dejamos pasar si el paso rellenar esta en estado PENDIENTE_FLUJO o ESTADO_PENDIENTE_DELEGACION_FIRMA
	    			if ( pasoAct.getTipoPaso() == PasoTramitacion.PASO_RELLENAR &&
	    				 paso == (i+1) &&
	    				 pasoDestino == PasoTramitacion.PASO_ANEXAR &&
	    				 (
	    						 pasoAct.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO) ||
	    						 pasoAct.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)
	    				  )
	    				 ){
	    					continue;
	    			}

	    			// No dejamos pasar
	    			completados = false;
	    			break;
	    		}
	    	}

	    	// Solo podremos ir a el paso indicado si todos los anteriores están completados
	    	if (completados){
	    		// Establecemos nuevo paso
	    		pasoActual = paso;
	    		// Evaluamos estado nuevo paso
				pasoAct = (PasoTramitacion) pasosTramitacion.get(pasoActual);
				pasoAct.setCompletado(evaluarEstadoPaso(pasoActual));
	    	}

	    	// Establecemos parametros paso al que vamos
	    	HashMap param = evaluarParametrosPaso(pasoActual);

	    	// Establecemos respuesta Front
	    	RespuestaFront resp = this.generarRespuestaFront(null,param);

	    	// Comprobamos si estamos en circuito de asistente y en paso de imprimir -> auditamos
	    	if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_ASISTENTE && !auditadoAsistente &&
	    		pasoAct != null &&  pasoAct.getTipoPaso() == PasoTramitacion.PASO_IMPRIMIR)
	    		auditarAsistente = true;

	    	return resp;
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al ir a paso",pe);
    		//log.error(mensajeLog("ProcessorException al ir a paso"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch(Exception e){
    		log.error(mensajeLog("Exception al ir a paso"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		if (auditarAsistente) {
    			auditadoAsistente = true;
    			logAuditoria(ConstantesAuditoria.EVENTO_ENVIO_TRAMITE,"S","",Character.toString(ConstantesSTR.DESTINO_ASISTENTE), this.tramiteVersion.getTramite().getProcedimiento(), false);
    		}
    	}
    }

    /**
     * Ir a rellenar formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront irAFormulario(String identificador,int instancia) {
    	try{
    		// Comprobamos que estamos en el paso de rellenar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_RELLENAR){
    			throw new Exception("Se ha invocado a ir a formulario desde un paso distinto a rellenar");
    		}

    		// Obtenemos configuracion formulario
	    	Documento doc = obtenerDocumentoFormulario(identificador);
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Recuperamos datos actuales y configuracion
	    	String ls_id = identificador + "-" + instancia;
	    	DatosFormulario datosForm = (DatosFormulario) datosFormularios.get(ls_id);
	    	if (datosForm == null){
	    		throw new Exception("No se encuentran datos formulario " + ls_id);
	    	}
	    	ConfiguracionDinamica confDin = (ConfiguracionDinamica) configuracionFormularios.get(identificador);
	    	if (confDin == null){
	    		throw new Exception("No se encuentran configuracion formulario " + identificador);
	    	}

	    	// Actualizamos tramiteInfo
	    	this.actualizarTramiteInfo();

	    	// Si es dependientes no permitimos acceder a formulario
	    	if ( tramiteInfo.getFormulario(identificador,instancia).getObligatorio() == DocumentoFront.DEPENDIENTE ){
	    		throw new Exception("No se puede acceder a un formulario con estado dependiente");
	    	}


	    	/*  NO LO HACEMOS, CONTROLAMOS AL GUARDAR BORRAR LAS FIRMAS
	    	 *
	    	// Si hay otros documentos pendientes de firma delegada que su script de firma depende de este formulario
	    	// no dejamos modificarlo
	    	if (getDocumentoPendienteFirmaDelegada(tramiteInfo.getFormulario(identificador,instancia))){
	    		MensajeFront mens = new MensajeFront();
    	    	mens.setTipo(MensajeFront.TIPO_WARNING);
    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_MODIFICACION_FORM_POR_PENDIENTE_FIRMA));
    	    	mens.setMensajeExcepcion("No se puede modificar este formulario si otros formularios que estan como firma delegada tienen en su script de firma referencias al formulario");
    	    	return generarRespuestaFront(mens,null);
	    	}
	    	*/

	    	// Establecemos configuracion formulario a partir de la configuracion dinámica y
	    	// del estado actual del trámite
	    	ConfiguracionFormulario confForm = new ConfiguracionFormulario();
	    	confForm.setCamposReadOnly(confDin.getReadOnly());
	    	confForm.setPropiedades(confDin.getPropiedades());

	    	//	Si se ha iniciado proceso de pagos no dejamos modificar
	    	if (tramiteInfo.iniciadoPagos()){
	    		MensajeFront mens = new MensajeFront();
    	    	mens.setTipo(MensajeFront.TIPO_WARNING);
    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORMODIFICACIONFORMPORPAGO));
    	    	mens.setMensajeExcepcion("No se ha puede modificar formularios si se ha iniciado pagos");
    	    	return generarRespuestaFront(mens,null);
	    	}

	    	//confForm.setReadOnly(tramiteInfo.iniciadoPagos());

	    	// En función de si hay flujo de tramitación, comprobamos si puede modificar el formulario
	    	if (tramiteInfo.isFlujoTramitacion()){
	    		DocumentoFront docInfo = tramiteInfo.getFormulario(identificador,instancia);
	    		if (!docInfo.getNifFlujo().equals(this.datosSesion.getNifUsuario())){
	    			MensajeFront mens = new MensajeFront();
	    	    	mens.setTipo(MensajeFront.TIPO_WARNING);
	    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORMODIFICACIONPORFLUJO));
	    	    	mens.setMensajeExcepcion("El usuario actual no puede modificar documento");
	    	    	return generarRespuestaFront(mens,null);
	    		}
	    	}

	    	// Configuramos el gestor de formularios
	    	ConfiguracionGestorFlujoFormulario confGestForm = new ConfiguracionGestorFlujoFormulario();
	    	confGestForm.setGestorFormulario(DelegateUtil.getGestorFormularioDelegate().obtener(docNivel.getFormularioGestorFormulario()));
	    	confGestForm.setPropiedad("tituloAplicacion",Literales.getLiteral(datosSesion.getLocale().getLanguage(),"aplicacion.titulo"));
	    	confGestForm.setPropiedad("modelo",docNivel.getFormularioFormsModelo());
	    	confGestForm.setPropiedad("version",docNivel.getFormularioFormsVersion().toString());
	    	confGestForm.setConfiguracionFormulario(confForm);
	    	confGestForm.setDatosActualesFormulario(datosForm.getString());

	    	// Establecemos respuesta front
	    	HashMap param = new HashMap();
	    	param.put("configuracionGestorForm",confGestForm);

	    	return this.generarRespuestaFront(null,param);
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al ir a formulario",pe);
    		//log.error(mensajeLog("ProcessorException al ir a formulario"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al ir a formulario"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }

    /**
     * Ir a pago
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront irAPago(String identificador,int instancia,String urlRetorno,String urlMantenimientoSesion) {
    	try{
    		// Comprobamos que estamos en el paso de pagar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_PAGAR){
    			throw new Exception("Se ha invocado a ir a pago desde un paso distinto a pagar");
    		}

    		// Obtenemos configuracion pago
    		Documento doc = obtenerDocumentoPago(identificador);

    		// Obtensmos configuracion nivel
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Comprobamos que el pago no sea dependiente tras ejecutar el script de obligatoriedad (es decir obligatorio/opcional)
	    	if (tramiteInfo.getPago(identificador,instancia).getObligatorio() == DocumentoFront.DEPENDIENTE  ){
	    		throw new Exception("El script de obligatoriedad indica que el pago no se puede realizar");
	    	}

	    	// Obtenemos datos actuales del pago
	    	HashMap param = new HashMap();
	    	String ls_id = identificador + "-" + instancia;
	    	DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(ls_id);
	    	DatosPago datosPago = (DatosPago) datosPagos.get(ls_id);

	    	// Procedemos según el estado del pago
	    	switch (docPAD.getEstado()){
	    		// Pago realizado: devolvemos error ya que no se puede acceder al pago
	    		case DocumentoPersistentePAD.ESTADO_CORRECTO:
	    			if (datosPagos == null) throw new Exception("El pago se ha finalizado y los datos del pago son nulos");
    	    		throw new Exception("No se puede acceder a la sesion de pago, ha finalizado: " + identificador);

	    		// Pago iniciado pero no finalizado:
	    	    //  - Retomamos sesion de pago
	    		case DocumentoPersistentePAD.ESTADO_INCORRECTO:
	    			if (datosPagos == null) throw new Exception("El pago se ha iniciado y los datos del pago son nulos");

	    			// Comprobamos estado sesion de pago
	    			EstadoSesionPago estado = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin()).comprobarEstadoSesionPago(datosPago.getLocalizador());
					switch (estado.getEstado()){
	    				// Pago confirmado: redirigimos al paso de confirmacion
	    				case ConstantesPago.SESIONPAGO_PAGO_CONFIRMADO:
	    					return confirmarPago(identificador,instancia);

	    				// Excedido tiempo pago: anulamos pago
	    				case ConstantesPago.SESIONPAGO_PAGO_EXCEDIDO_TIEMPO_PAGO:
	    					// Borramos pago
	    					borrarPago(identificador,instancia);
	    					// Devolvemos mensaje error
	    					MensajeFront mensTiempoExcedido = new MensajeFront();
	    					mensTiempoExcedido.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
	    					mensTiempoExcedido.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORSESIONPAGOTIEMPOEXCEDIDO));
	    					mensTiempoExcedido.setMensajeExcepcion("Se ha excedido tiempo de pago.");
	    	    	    	return generarRespuestaFront(mensTiempoExcedido,null);

	    				// Pago no existe: borramos datos pago y mostramos mensaje de error
	    				case ConstantesPago.SESIONPAGO_NO_EXISTE_SESION:
	    					// Borramos pago
	    					borrarPago(identificador,instancia);
	    					// Devolvemos mensaje error
	    					MensajeFront mens = new MensajeFront();
	    	    	    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
	    	    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORSESIONPAGONOEXISTE));
	    	    	    	mens.setMensajeExcepcion("Sesion de pagos no existe. Debe iniciar una sesion nueva.");
	    	    	    	return generarRespuestaFront(mens,null);

	    				// Pago en curso o pendiente de confirmacion: redirigimos al asistente de pago
	    				case ConstantesPago.SESIONPAGO_EN_CURSO:
	    				case ConstantesPago.SESIONPAGO_PAGO_PENDIENTE_CONFIRMAR:
	    					// Retomamos sesion a traves del plugin
	    					SesionSistra ss = new SesionSistra();
	    	        		ss.setUrlMantenimientoSesionSistra(urlMantenimientoSesion);
	    	        		ss.setUrlRetornoSistra(urlRetorno);
	    	        		ss.setNivelAutenticacion(Character.toString(this.datosSesion.getNivelAutenticacion()));
	    	        		ss.setNifUsuario(this.datosSesion.getNifUsuario());
	    	        		ss.setNombreCompletoUsuario(this.datosSesion.getNombreCompletoUsuario());
	    	        		ss.setCodigoUsuario(this.datosSesion.getCodigoUsuario());
	    	        		SesionPago sesionPago = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin()).reanudarSesionPago(datosPago.getLocalizador(),ss);
	    	        		// Devolvemos indicando la url de redireccion
	    	        		param.put("urlsesionpago",sesionPago.getUrlSesionPago());
	    	    	    	return this.generarRespuestaFront(null,param);

	    				// Otro estado: error
	    				default:
	    					throw new Exception("Estado sesion pago desconocido: " + estado.getEstado());
	    			}


     		    // Pago no se ha iniciado:
	    	    //  - Calculamos datos pago e iniciamos sesion de pago
	    		case DocumentoPersistentePAD.ESTADO_NORELLENADO:
	    			// Calculamos datos pago (precargamos modelo,concepto,fecha,nif y nombre)
	    			CalculoPago calc = new CalculoPago(docNivel.getPagoPlugin());
	    			calc.setModelo("046");
	    			calc.setConcepto( ((TraDocumento) doc.getTraduccion(datosSesion.getLocale().getLanguage())).getDescripcion());
	    			calc.setFechaDevengo( StringUtil.fechaACadena(new Date(),CalculoPago.FORMATO_FECHA_DEVENGO));
	    			DatosDesglosadosInteresado datosRpte = this.calcularDatosRepresentante();
	    			calc.setNif(datosRpte.getNif());
					calc.setNombre(datosRpte.getApellidosNombre());
	    			HashMap params = new HashMap();
	        		params.put("DATOSPAGO",calc);
	        		evaluarScript(docNivel.getPagoCalcularPagoScript(),params);

	        		// Validamos resultado script
	        		if (StringUtils.isEmpty(calc.getModelo()) || StringUtils.isEmpty(calc.getIdTasa()) ||
	        			StringUtils.isEmpty(calc.getConcepto()) || StringUtils.isEmpty(calc.getFechaDevengo()) ||
	        			StringUtils.isEmpty(calc.getImporte())){
	        				throw new Exception("No se han establecido mediante script todos los datos del pago: " + identificador);
	        		}

	        		// Redondeamos importe tasa por si tiene decimales
	        		double impDec = Double.parseDouble(calc.getImporte());
	        		long impInt = Math.round(impDec);

	        		// Si no es positiva es que existe un error en el calculo
	        		if (impInt < 0) {
	        			throw new Exception("Error al calcular el importe de la tasa");
	        		}

	        		// Verificamos si se ha excedido el tiempo para realizar el pago
	        		Date fechaLimitePago = StringUtil.cadenaAFecha(calc.getFechaLimitePago(), StringUtil.FORMATO_TIMESTAMP);
	        		if (fechaLimitePago != null && fechaLimitePago.before(new Date())) {
						// Devolvemos mensaje error
						MensajeFront mensTiempoExcedido = new MensajeFront();
						mensTiempoExcedido.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
						mensTiempoExcedido.setMensaje(StringUtils.isNotEmpty(calc.getMensajeFechaLimitePago())? calc.getMensajeFechaLimitePago() : traducirMensaje(MensajeFront.MENSAJE_ERRORSESIONPAGOTIEMPOEXCEDIDO));
						mensTiempoExcedido.setMensajeExcepcion("Se ha excedido tiempo de pago.");
		    	    	return generarRespuestaFront(mensTiempoExcedido,null);
	        		}

	        		String entidad = obtenerEntidadProcedimiento(tramitePersistentePAD.getIdProcedimiento());
	    			if (entidad == null)
	    				throw new Exception("El codigo de procedimiento no es valido");
	        		
	        		// Creamos los datos del pago
	        		datosPago = new DatosPago();
	        		datosPago.setPluginId(PluginFactory.ID_PLUGIN_DEFECTO.equals(docNivel.getPagoPlugin())?null:docNivel.getPagoPlugin());
	        		datosPago.setCodigoEntidad(entidad);
	        		datosPago.setTipoPago(docNivel.getPagoMetodos());
	        		datosPago.setEstado(DocumentoPersistentePAD.ESTADO_INCORRECTO);
	        		datosPago.setOrganoEmisor(calc.getOrganoEmisor());
	        		datosPago.setModelo(calc.getModelo());
	        		datosPago.setIdTasa(calc.getIdTasa());
	        		datosPago.setImporte(Long.toString(impInt));
	        		datosPago.setFechaDevengo(StringUtil.cadenaAFecha(calc.getFechaDevengo(),CalculoPago.FORMATO_FECHA_DEVENGO));
	        		datosPago.setConcepto(calc.getConcepto());
	        		datosPago.setCodigoPostal(calc.getCodigoPostal());
	        		datosPago.setDomicilioEscalera(calc.getDomicilioEscalera());
	        		datosPago.setDomicilioLetra(calc.getDomicilioLetra());
	        		datosPago.setDomicilioNumero(calc.getDomicilioNumero());
	        		datosPago.setDomicilioPiso(calc.getDomicilioPiso());
	        		datosPago.setDomicilioPuerta(calc.getDomicilioPuerta());
	        		datosPago.setDomicilioSiglas(calc.getDomicilioSiglas());
	        		datosPago.setDomicilioVia(calc.getDomicilioVia());
	        		datosPago.setFax(calc.getFax());
	        		datosPago.setLocalidad(calc.getLocalidad());
	        		datosPago.setNif( NifCif.normalizarDocumento(calc.getNif()));
	        		datosPago.setNombre(calc.getNombre());
	        		datosPago.setProvincia(calc.getProvincia());
	        		datosPago.setTelefono(calc.getTelefono());

	    	    	// Iniciamos sesion de pago contra el plugin de pagos
	        		es.caib.sistra.plugins.pagos.DatosPago dp = new es.caib.sistra.plugins.pagos.DatosPago();	        		
	        		dp.setConcepto(datosPago.getConcepto());
	        		dp.setFechaDevengo(datosPago.getFechaDevengo());
	        		dp.setIdioma(datosSesion.getLocale().getLanguage());
	        		dp.setIdTasa(datosPago.getIdTasa());
	        		dp.setImporte(datosPago.getImporte());
	        		dp.setIdentificadorOrganismo(datosPago.getOrganoEmisor());
	        		dp.setModelo(datosPago.getModelo());
	        		dp.setNifDeclarante(datosPago.getNif());
	        		dp.setNombreDeclarante(datosPago.getNombre());
	        		dp.setTelefonoDeclarante(datosPago.getTelefono());
	        		dp.setModeloTramite(this.tramiteInfo.getModelo());
	        		dp.setVersionTramite(this.tramiteInfo.getVersion());
	        		dp.setIdentificadorTramite(this.tramiteInfo.getIdPersistencia());
	        		dp.setNombreTramite(this.tramiteInfo.getDescripcion());
	        		dp.setNombreUsuario(this.datosSesion.getNombreCompletoUsuario());
	        		dp.setTipoPago(datosPago.getTipoPago());
					dp.setFechaMaximaPago(fechaLimitePago);
					dp.setMensajeTiempoMaximoPago(calc.getMensajeFechaLimitePago());
					dp.setFechaInicioTramite(this.tramitePersistentePAD.getFechaCreacion());
					dp.setIdProcedimiento(this.tramiteInfo.getIdProcedimiento());
					dp.setEntidad(this.tramiteInfo.getEntidad());

	        		SesionSistra ss = new SesionSistra();
	        		ss.setUrlMantenimientoSesionSistra(urlMantenimientoSesion);
	        		ss.setUrlRetornoSistra(urlRetorno);
	        		ss.setNivelAutenticacion(Character.toString(this.datosSesion.getNivelAutenticacion()));
	        		ss.setNifUsuario(this.datosSesion.getNifUsuario());
	        		ss.setNombreCompletoUsuario(this.datosSesion.getNombreCompletoUsuario());
	        		ss.setCodigoUsuario(this.datosSesion.getCodigoUsuario());

	        		SesionPago sesionPago = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin()).iniciarSesionPago(dp,ss);

	        		/*
				    // ---------------------------------------------------------------------------------------------------------------------------

	        		// TEST: PARA METER PAGO AGRUPADO A PIÑON
	        		es.caib.sistra.plugins.pagos.DatosPago[] listaDatosPagos = new es.caib.sistra.plugins.pagos.DatosPago[2];
	        		listaDatosPagos[0] = dp;
	        		es.caib.sistra.plugins.pagos.DatosPago dp2 = new es.caib.sistra.plugins.pagos.DatosPago();
	        		dp2.setConcepto(datosPago.getConcepto() + "-2");
	        		dp2.setFechaDevengo(datosPago.getFechaDevengo());
	        		dp2.setIdioma(datosSesion.getLocale().getLanguage());
	        		dp2.setIdTasa(datosPago.getIdTasa());
	        		dp2.setImporte(datosPago.getImporte());
	        		dp2.setIdentificadorOrganismo(datosPago.getOrganoEmisor());
	        		dp2.setModelo(datosPago.getModelo());
	        		dp2.setNifDeclarante(datosPago.getNif());
	        		dp2.setNombreDeclarante(datosPago.getNombre());
	        		dp2.setTelefonoDeclarante(datosPago.getTelefono());
	        		dp2.setModeloTramite(this.tramiteInfo.getModelo());
	        		dp2.setVersionTramite(this.tramiteInfo.getVersion());
	        		dp2.setIdentificadorTramite(this.tramiteInfo.getIdPersistencia());
	        		dp2.setNombreTramite(this.tramiteInfo.getDescripcion());
	        		dp2.setNombreUsuario(this.datosSesion.getNombreCompletoUsuario());
	        		dp2.setTipoPago(datosPago.getTipoPago());
	        		listaDatosPagos[1] = dp2;
	        		SesionPago sesionPago = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin()).iniciarSesionPagoDiferido(listaDatosPagos ,ss);

				    // ---------------------------------------------------------------------------------------------------------------------------
	        	*/
	        		// Metemos datos pago en la lista de datos del pago
	        		datosPago.setLocalizador(sesionPago.getLocalizador());
	        		this.datosPagos.put(ls_id,datosPago);

	        		// Almacenamos datos pago en RDS y PAD
	    	    	// -- Guardamos en RDS
	        		DocumentoRDS docRds = new DocumentoRDS();
	        		docRds.setModelo(doc.getModelo());
	        		docRds.setVersion(docNivel.getVersion());
	        		docRds.setDatosFichero(datosPago.getBytes());
	        		docRds.setTitulo(((TraDocumento) doc.getTraduccion()).getDescripcion());
	        		docRds.setNombreFichero("pago.xml");
	        		docRds.setExtensionFichero("xml");
	        		docRds.setUnidadAdministrativa(tramiteVersion.getUnidadAdministrativa().longValue());
	        		docRds.setNif(datosSesion.getNifUsuario());
	        		docRds.setUsuarioSeycon(datosSesion.getCodigoUsuario());
	        		docRds.setIdioma(this.datosSesion.getLocale().getLanguage());

	        		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	        		ReferenciaRDS ref = rds.insertarDocumento(docRds);
	        		UsoRDS uso = new UsoRDS();
	        		uso.setReferencia(this.tramitePersistentePAD.getIdPersistencia());
	        		uso.setReferenciaRDS(ref);
	        		uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
	        		rds.crearUso(uso);

	        		// Actualizamos PAD
	        		docPAD.setEstado(DocumentoPersistentePAD.ESTADO_INCORRECTO);
	        		docPAD.setReferenciaRDS(ref);
	        		actualizarPAD();

	        		// Devolvemos indicando la url de redireccion
	        		param.put("urlsesionpago",sesionPago.getUrlSesionPago());
	    	    	return this.generarRespuestaFront(null,param);

	    	    default:
	    	    	throw new Exception("Estado del pago no es correcto");
	    	}
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al ir a pago",pe);
    		//log.error(mensajeLog("ProcessorException al ir a pago"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al ir a pago"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }

    /**
     *	Metodo que se invocara tras volver del asistente de pago.
     *  Verifica contra el plugin de pagos si un pago ha sido realizado
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront confirmarPago(String identificador,int instancia) {
    	boolean confirmado = false;
    	DatosPago datosPago = null;
    	try{
    		String id =  identificador + "-" + instancia;

    		// Comprobamos que estamos en el paso de pagar
    		PasoTramitacion pasoPagar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoPagar.getTipoPaso() != PasoTramitacion.PASO_PAGAR){
    			throw new Exception("Se ha invocado a pagar desde un paso distinto a pagar");
    		}

    		// Obtenemos configuracion pago
    		Documento doc = obtenerDocumentoPago(identificador);

    		// Obtensmos configuracion nivel
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

    		// Obtenemos datos pago
    		datosPago = (DatosPago) this.datosPagos.get(id);

    		// Verificamos estado pago contra el plugin de pago
    		EstadoSesionPago estadoSesionPago = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin()).comprobarEstadoSesionPago(datosPago.getLocalizador());

    		// Si no se ha podido pagar porque se ha excedido tiempo, anulamos pago
    		if (estadoSesionPago.getEstado() == ConstantesPago.SESIONPAGO_PAGO_EXCEDIDO_TIEMPO_PAGO){
    			// Borramos pago
				borrarPago(identificador,instancia);
				// Devolvemos mensaje error
				MensajeFront mensTiempoExcedido = new MensajeFront();
				mensTiempoExcedido.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
				mensTiempoExcedido.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORSESIONPAGOTIEMPOEXCEDIDO));
				mensTiempoExcedido.setMensajeExcepcion("Se ha excedido tiempo de pago.");
    	    	return generarRespuestaFront(mensTiempoExcedido,null);
    		}

    		// Si se ha confirmado actualizamos documento y estado tramite
    		if (estadoSesionPago.getEstado() == ConstantesPago.SESIONPAGO_PAGO_CONFIRMADO){
    				// Actualizamos datos pago
    		    	datosPago.setTipoPago(estadoSesionPago.getTipo());
    		    	datosPago.setNumeroDUI(estadoSesionPago.getIdentificadorPago());
    		    	datosPago.setJustificantePago(estadoSesionPago.getReciboB64PagoTelematico());
    		    	datosPago.setFechaPago(estadoSesionPago.getFechaPago());
    		    	datosPago.setEstado(DocumentoPersistentePAD.ESTADO_CORRECTO);

    				// Actualizamos documento de pago en PAD y RDS
    		    	DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(id);
    		    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		    	rds.actualizarFichero(docPAD.getRefRDS(),datosPago.getBytes());
    		    	docPAD.setEstado(DocumentoPersistentePAD.ESTADO_CORRECTO);
    		    	docPAD.setEsPagoTelematico(estadoSesionPago.getTipo() == ConstantesPago.TIPOPAGO_TELEMATICO?"S":"N");
    		    	actualizarPAD();

    		    	// Actualizamos estado paso Pagar
		        	pasoPagar.setCompletado(evaluarEstadoPaso(pasoActual));

		        	// Indicamos que el pago se ha confirmado para indicarlo en la auditoria
		        	confirmado = true;
    		}

    		// Si el paso pagar esta completado y no hay pagos opcionales, pasamos al siguiente paso
    		boolean pagosOpcionalesNoRellenados = existenPagosOpcionalesNoRellenados();
	    	if (pasoPagar.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO)  &&
	    			!pagosOpcionalesNoRellenados){
	    		return this.siguientePaso();
	    	}

    		// Devolvemos estado actual tramite
    		return this.generarRespuestaFront(null,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al confirmar pago",pe);
    		//log.error(mensajeLog("ProcessorException al confirmar Pago"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al confirmar Pago"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}finally{
    		// Apuntamos en auditoria
    		if (confirmado){
    			logAuditoria(ConstantesAuditoria.EVENTO_PAGO,"S","Pago confirmado",Character.toString(datosPago.getTipoPago()), null, false);
    		}
    	}
    }

	/**
     * Anular pago:
     * 	Desde el asistente de tramitación sólo se permite anular pagos presenciales finalizados.
     *  En realidad no se anula, sino que se deshecha el pago anterior y se crea otro nuevo.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront anularPago(String identificador,int instancia) {
    	DatosPago datosPago = null; // Datos del pago

    	try{

    		// Comprobamos que estamos en el paso de pagar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_PAGAR){
    			throw new Exception("Se ha invocado anular pago desde un paso distinto a pagar");
    		}

    		// Obtenemos datos actuales del pago
	    	String ls_id = identificador + "-" + instancia;
	    	DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(ls_id);
	    	datosPago = (DatosPago) this.datosPagos.get(ls_id);
	    	if (datosPago == null){
	    		throw new Exception("No existen datos pago");
	    	}

	    	// Solo se admite anular desde asistente tramitacion para presencial y pago finalizado
			if (docPAD.getEstado() != DocumentoPersistentePAD.ESTADO_CORRECTO ||
				datosPago.getTipoPago() != 'P'){
	    		throw new Exception("Desde el asistente de tramitación sólo se permite anular pagos presenciales finalizados");
	    	}

			// Anulamos pago: borramos pago del RDS, actualizamos PAD y borrar de listas pagos
	    	borrarPago(identificador,instancia);

	    	// Evaluamos paso
	    	pasoRellenar.setCompletado(this.evaluarEstadoPaso(pasoActual));

	    	// Devolvemos estado tramite
	    	RespuestaFront res =  this.generarRespuestaFront(null,null);

	    	return res;

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al anular pago",pe);
    		//log.error(mensajeLog("ProcessorException al anular pago"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al anular pago"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }



    /**
     * 	Indica al plugin de pagos que se da por finalizada la sesión de pago para que
     *  pueda liberar recursos. Esta función no tiene efectos sobre los datos del
     *  trámite, sino que simplemente es un aviso hacia el plugin de pagos.
     *  No se reintentará en caso de que se produzca un error al notificar al plugin de pagos.
     *
     *  Sólo se permitirá finalizar la sesión de pago si el pago se ha realizado correctamente
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void finalizarSesionPago(String identificador,int instancia) {

    	DatosPago datosPago = null; // Datos del pago

    	debug("Finalizar sesion pago");

    	try{


    		// Obtenemos configuracion pago
    		Documento doc = obtenerDocumentoPago(identificador);

    		// Obtensmos configuracion nivel
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());


    		// Obtenemos datos actuales del pago
	    	String ls_id = identificador + "-" + instancia;
	    	DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(ls_id);
	    	datosPago = (DatosPago) this.datosPagos.get(ls_id);
	    	if (datosPago == null){
	    		throw new Exception("No existen datos pago");
	    	}

	    	// Sea presencial o telemático sólo se podrá anular si ha sido iniciado
	    	if (docPAD.getEstado() != DocumentoPersistentePAD.ESTADO_CORRECTO){
	    		debug("No se puede finalizar sesión de pago si el pago no se ha finalizado");
	    		return;
	    	}

	    	// Obtenemos plugin de pagos
	    	PluginPagosIntf pluginPago = PluginFactory.getInstance().getPluginPagos(docNivel.getPagoPlugin());
	    	pluginPago.finalizarSesionPago(datosPago.getLocalizador());

	    	debug("Sesion pago finalizada");

    	}catch (Exception e){
    		log.error(mensajeLog("Exception al finalizar sesion de pago"),e);
    		return;
    	}
    }

    /**
     * Guardar formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront guardarFormulario(String identificador,int instancia,String datosAnteriores,String datosNuevos, boolean guardadoSinFinalizar) {
    	try{

    		// Comprobamos que estamos en el paso de rellenar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_RELLENAR){
    			throw new Exception("Se ha invocado a guardar formulario desde un paso distinto a rellenar");
    		}

    		// Si ya se han comenzado los pagos no se deja salvar
    		if (tramiteInfo.iniciadoPagos()){
    			throw new ProcessorException("No se puede salvar formulario si se han iniciado los pagos",MensajeFront.MENSAJE_PAGOSINICIADOS);
    		}

    		/*
    		 CONTROLAMOS AL GUARDAR
    		// Si hay otros documentos pendientes de firma delegada que su script de firma depende de este formulario
	    	// no dejamos modificarlo
	    	if (getDocumentoPendienteFirmaDelegada(tramiteInfo.getFormulario(identificador,instancia))){
	    		throw new ProcessorException("No se puede modificar este formulario si otros formularios que estan como firma delegada tienen en su script de firma referencias al formulario",MensajeFront.MENSAJE_ERROR_MODIFICACION_FORM_POR_PENDIENTE_FIRMA);
	    	}
	    	*/

	    	// Obtenemos configuracion formulario
    		Documento doc = obtenerDocumentoFormulario(identificador);
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Comprobamos que no han variado los datos anteriores
	    	String idFormularioAGuardar = identificador + "-" + instancia;
	    	DatosFormulario datosForm = (DatosFormulario) datosFormularios.get(idFormularioAGuardar);
	    	if (datosForm == null){
	    		throw new Exception("No se encuentran datos formulario " + idFormularioAGuardar);
	    	}
	    	if (!datosForm.getString().equals(datosAnteriores)){
	    		throw new ProcessorException("Los datos almacenados para el formulario " + idFormularioAGuardar + " no coinciden",MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    	}

	    	// Obtenemos documento PAD
	    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idFormularioAGuardar);

	    	// Actualizamos los datos de los formularios
	    	DatosFormulario datosFormNuevos = new DatosFormulario(doc.getModelo(),docNivel.getVersion());
	    	datosFormNuevos.setString(datosNuevos);

    		// Cacheamos datos formulario
	    	datosFormularios.put(idFormularioAGuardar,datosFormNuevos);

	    	// Evaluamos plantilla especifica
	    	String plantilla=null;
	    	if (docNivel.getFormularioPlantillaScript() != null && docNivel.getFormularioPlantillaScript().length >0){
	    		plantilla = this.evaluarScript(docNivel.getFormularioPlantillaScript(),null);
	    		if (StringUtils.isEmpty(plantilla)) plantilla = null;
	    	}
	    	datosFormNuevos.setPlantilla(plantilla);

	    	// Realizamos validacion post (blindamos para que en caso de fallo no impida proceso salvado)
	    	// No se debe realizar si se guarda sin finalizar
	    	ProcessorException errorValidacion = null;
    		if (!guardadoSinFinalizar && docNivel.getFormularioValidacionPostFormScript() != null && docNivel.getFormularioValidacionPostFormScript().length > 0){
	    		try{
	    			this.evaluarScript(docNivel.getFormularioValidacionPostFormScript(),null);
	    		}catch(ProcessorException pe){
	    			// Guardamos código de error
	    			errorValidacion = pe;
	    		}
    		}

	    	// Establecemos estado formulario
	    	docPad.setEstado((errorValidacion != null || guardadoSinFinalizar)?DocumentoPersistentePAD.ESTADO_INCORRECTO:DocumentoPersistentePAD.ESTADO_CORRECTO);

	    	// Una vez establecidos datos documento actual, si se finalizado el formulario y si no hay error de validación ejecutamos script
	    	// de modificación de otros formularios
    		List formulariosModificados = new ArrayList();
    		if (!guardadoSinFinalizar && errorValidacion == null &&
    			docNivel.getFormularioModificacionPostFormScript() != null && docNivel.getFormularioModificacionPostFormScript().length > 0){
    			this.evaluarScript(docNivel.getFormularioModificacionPostFormScript(),null,formulariosModificados);
    		}

    		// Después de modificar formularios, actualizamos tramiteInfo
    		this.actualizarTramiteInfo();

	    	// Establecemos:
	    	// - Estado formularios dependientes: si se produce cambio de estado a dependiente marcamos como incorrecto
	    	// - Estado formularios con script de flujo: si en el script de flujo esta el formulario marcamos como incorrecto
	    	// - Marcamos si hay algun formulario opcional no rellenado
	    	boolean formulariosOpcionalesNoRellenados = false;
	    	for (Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext();){
	    		DocumentoFront docInfo = (DocumentoFront) it.next();
	    		// Formularios correctos que se han quedado en estado Dependiente -> marcamos como incorrectos
	    		if (docInfo.getEstado() == DocumentoFront.ESTADO_CORRECTO && docInfo.getObligatorio() == DocumentoFront.DEPENDIENTE){
	    			DocumentoPersistentePAD docDepPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(docInfo.getIdentificador() + "-" + docInfo.getInstancia());
	    			docDepPAD.setEstado(DocumentoPersistentePAD.ESTADO_INCORRECTO);
	    		}
	    		// Formularios correctos en cuyo script de flujo interviene este formulario
	    		if (tramiteInfo.isFlujoTramitacion()){
		    		if (docInfo.getEstado() == DocumentoFront.ESTADO_CORRECTO && docInfo.getFormulariosScriptFlujo().indexOf("["+identificador +"]") != -1){
		    			DocumentoPersistentePAD docDepPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(docInfo.getIdentificador() + "-" + docInfo.getInstancia());
		    			docDepPAD.setEstado(DocumentoPersistentePAD.ESTADO_INCORRECTO);
		    		}
	    		}
	    		// Formulario opcional no rellenado
	    		if (docInfo.getObligatorio() == DocumentoFront.OPCIONAL && docInfo.getEstado() != DocumentoFront.ESTADO_CORRECTO ){
	    			formulariosOpcionalesNoRellenados = true;
	    		}
	    	}

	    	// Comprobamos si hay anexis que deberían borrarse
	    	// (despues de actualizar formulario para aprovechar logica de borrado de documentos)
	    	List anexosParaBorrar = new ArrayList();
	    	for (Iterator it = tramiteInfo.getAnexos().iterator();it.hasNext();){
	    		DocumentoFront docInfo = (DocumentoFront) it.next();
	    		// Anexos correctos en cuyo script de flujo interviene este formulario
	    		if (tramiteInfo.isFlujoTramitacion()){
		    		if (docInfo.getEstado() == DocumentoFront.ESTADO_CORRECTO && docInfo.getFormulariosScriptFlujo().indexOf("["+identificador +"]") != -1){
		    			anexosParaBorrar.add(docInfo.getIdentificador() + "-" + docInfo.getInstancia());
		    		}
	    		}
	    		// Documentos firmados o en caso de que se haya modificado el firmante
	    		if (docInfo.isFirmar() && (docInfo.isFirmado() || docInfo.isPendienteFirmaDelegada())){
	    			if (!verificarFirmantesDocumento(docInfo)){
	    				anexosParaBorrar.add(docInfo.getIdentificador() + "-" + docInfo.getInstancia());
	    			}
	    		}
	    	}

	    	// Guardamos en el RDS y actualizamos PAD
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRds = rds.consultarDocumento(docPad.getRefRDS(),false);
	    	docRds.setPlantilla(plantilla);
	    	docRds.setDatosFichero(datosFormNuevos.getBytes());
	    	docRds.setFirmas( new FirmaIntf[]{} );
	    	rds.actualizarDocumento(docRds);

	    	// Reseteamos info de firma delegada
	    	docPad.setDelegacionEstado(null);
	    	docPad.setDelegacionFirmantes(null);
	    	docPad.setDelegacionFirmantesPendientes(null);

	    	// Quitamos cacheo de firma
	    	if (this.firmaDocumentos.containsKey(docPad.getRefRDS().toString())) this.firmaDocumentos.remove(docPad.getRefRDS().toString());

	    	// Evaluamos si se han modificado otros formularios en el script de modificación y
	    	// guardamos documentos
	    	if (formulariosModificados.size() > 0){
	    		for (int i=0;i<formulariosModificados.size();i++){
	    			String formModifKey = (String) formulariosModificados.get(i);
	    			DocumentoPersistentePAD formModifPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(formModifKey);
	    			DatosFormulario formModifDatos = (DatosFormulario) this.datosFormularios.get(formModifKey);

	    			// Actualizamos documento eliminando firmas
	    			DocumentoRDS formModifRDS = rds.consultarDocumento(formModifPAD.getRefRDS(),false);
	    			formModifRDS.setFirmas(new FirmaIntf[]{});
	    			formModifRDS.setDatosFichero(formModifDatos.getBytes());
	    			rds.actualizarDocumento(formModifRDS);

	    			// Reseteamos info de firma delegada
	    			formModifPAD.setDelegacionEstado(null);
	    			formModifPAD.setDelegacionFirmantes(null);
	    			formModifPAD.setDelegacionFirmantesPendientes(null);

	    			// Quitamos cacheo de firma
	    			if (this.firmaDocumentos.containsKey(formModifPAD.getRefRDS().toString())) this.firmaDocumentos.remove(formModifPAD.getRefRDS().toString());
	    		}
	    	}

	    	// Evaluamos si hay otros formularios dependientes del script de firma y vemos si ha variado el firmante
	    	for (Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext();){
	    		DocumentoFront docInfo = (DocumentoFront) it.next();
	    		String keyDoc = docInfo.getIdentificador() + "-" + docInfo.getInstancia();

	    		// Si ya se ha quitado la firma en el paso anterior continuamos
	    		if (formulariosModificados.contains(keyDoc) || keyDoc.equals(idFormularioAGuardar)){
	    			continue;
	    		}

	    		// Documentos firmados en caso de que se haya modificado el firmante
	    		if (docInfo.isFirmar() && (docInfo.isFirmado() ||  docInfo.isPendienteFirmaDelegada())){
	    			if (!verificarFirmantesDocumento(docInfo)){

	    				DocumentoPersistentePAD formModifPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(keyDoc);

	    				// Actualizamos documento eliminando firmas
	    				DatosFormulario formModifDatos = (DatosFormulario) this.datosFormularios.get(keyDoc);
			    		DocumentoRDS formModifRDS = rds.consultarDocumento(formModifPAD.getRefRDS(),false);
			    		formModifRDS.setFirmas(new FirmaIntf[]{});
			    		formModifRDS.setDatosFichero(formModifDatos.getBytes());
			    		rds.actualizarDocumento(formModifRDS);

			    		// Quitamos cacheo de firma
			    		if (this.firmaDocumentos.containsKey(formModifPAD.getRefRDS().toString())){
			    			this.firmaDocumentos.remove(formModifPAD.getRefRDS().toString());
			    		}

			    		// Reseteamos info firma delegada
	    				formModifPAD.setDelegacionEstado(null);
	    				formModifPAD.setDelegacionFirmantes(null);
	    				formModifPAD.setDelegacionFirmantesPendientes(null);
	    			}
	    		}

	    	}


	    	// Si pasa la validación damos el formulario como rellenado y actualizamos PAD
	    	actualizarPAD();

	    	// Actualizamos tramiteInfo antes de borrar anexos
    		this.actualizarTramiteInfo();

	    	// Después de actualizar formulario borramos anexos afectados por script flujo
	    	// HAY QUE BORRAR LOS DOCUMENTOS POR ORDEN DE INSTANCIA YA QUE LA PAD REORDENA
	    	borrarAnexos(anexosParaBorrar);

	    	// En caso de que se haya generado un mensaje de validación establecemos respuesta front
	    	MensajeFront mens = null;
	    	if (errorValidacion != null){
	    		mens = new MensajeFront();
	    		mens.setMensaje(traducirMensaje(errorValidacion));
	    		mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
	    	}

	    	// Actualizamos estado paso rellenar formularios
	    	pasoRellenar.setCompletado(evaluarEstadoPaso(pasoActual));

	    	// Si estan rellenados los formularios calculamos email/sms de alertas
	    	if (pasoRellenar.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO) ||
	    			pasoRellenar.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)) {
	    		actualizarInfoAlertasTramitacion();
	    	}

	    	// Si el paso rellenar esta completado, no hay documentos opcionales por rellenar ni mensajes a mostrar
	    	// vamos directamente a siguiente paso
	    	if (pasoRellenar.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO)  &&
	    			mens==null && !formulariosOpcionalesNoRellenados){
	    		return this.siguientePaso();
	    	}

	    	// Devolvemos respuesta
	    	return this.generarRespuestaFront(mens,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al guardar formulario",pe);
    		//log.error(mensajeLog("ProcessorException al guardar formulario"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al guardar formulario"),e);

    		System.out.println("EXCEPTION GUARDAR FORMULARIO");
    		e.printStackTrace();

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }

    /**
     * Establece datos de alertas de tramitacion tras que se complete el paso de formularios.
     * @throws Exception
     */
	private void actualizarInfoAlertasTramitacion() throws Exception {
		EspecTramiteNivel espTramite = (EspecTramiteNivel) tramiteVersion.getEspecificaciones();
		EspecTramiteNivel espNivel = (EspecTramiteNivel) tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();
		String alertasTramitacionGenerar = espTramite.getHabilitarAlertasTramitacion();
		String alertasTramitacionFinAuto = espTramite.getFinalizarTramiteAuto();
		boolean permitirSms = "S".equals(espTramite.getPermitirSMSAlertasTramitacion());
		if ( !ConstantesSTR.ALERTASTRAMITACION_SINESPECIFICAR.equals(espNivel.getHabilitarAlertasTramitacion())){
			alertasTramitacionGenerar = espNivel.getHabilitarAlertasTramitacion();
			alertasTramitacionFinAuto = espNivel.getFinalizarTramiteAuto();
			permitirSms = "S".equals(espNivel.getPermitirSMSAlertasTramitacion());
		}
		String emailAlertas = null;
		String smsAlertas = null;
		if (ConstantesSTR.ALERTASTRAMITACION_PERMITIDA.equals(alertasTramitacionGenerar)) {
			emailAlertas = calcularEmailAvisoDefecto();
			if (permitirSms) {
				smsAlertas = calcularSmsAvisoDefecto();
			}
		}

		tramitePersistentePAD.setAlertasTramitacionGenerar(alertasTramitacionGenerar);
		tramitePersistentePAD.setAlertasTramitacionFinAuto(alertasTramitacionFinAuto);
		tramitePersistentePAD.setAlertasTramitacionEmail(emailAlertas);
		tramitePersistentePAD.setAlertasTramitacionSms(smsAlertas);
		actualizarPAD();
	}

    /**
     * Obtiene los datos del formulario para ir a firmar
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront irAFirmarFormulario(String identificador,int instancia) {
    	try{
    		// Comprobamos que estamos en el paso de rellenar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_RELLENAR){
    			throw new Exception("Se ha invocado a ir a formulario desde un paso distinto a rellenar");
    		}

    		// Recuperamos datos actuales y configuracion
	    	String ls_id = identificador + "-" + instancia;
	    	DatosFormulario datosForm = (DatosFormulario) datosFormularios.get(ls_id);
	    	if (datosForm == null){
	    		throw new Exception("No se encuentran datos formulario " + ls_id);
	    	}

	    	// Establecemos respuesta front
	    	HashMap param = new HashMap();
	    	param.put("datos",datosForm.getString());

	    	return this.generarRespuestaFront(null,param);
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al ir a firmar formulario",pe);
    		//log.error(mensajeLog("ProcessorException al ir a firmar formulario"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al ir a firmar formulario"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }



    /**
     * Guardar formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront firmarFormulario(String identificador,int instancia,FirmaIntf firma,boolean firmaDelegada) {
    	try{

    		// Comprobamos que estamos en el paso de rellenar
    		int pasoRellenarIndice = pasoActual;
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoRellenarIndice);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_RELLENAR){
    			throw new Exception("Se ha invocado a guardar formulario desde un paso distinto a rellenar");
    		}

    		// Si ya se han comenzado los pagos no se deja salvar
    		if (tramiteInfo.iniciadoPagos()){
    			throw new ProcessorException("No se puede salvar formulario si se han iniciado los pagos",MensajeFront.MENSAJE_PAGOSINICIADOS);
    		}

    		// Obtenemos configuracion formulario
    		Documento doc = obtenerDocumentoFormulario(identificador);
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Comprobamos que el formulario debe firmarse
	    	if (docNivel.getFirmar() != 'S'){
	    		throw new Exception("El documento no esta configurado para firmar");
	    	}

	    	// Obtenemos documento PAD
	    	String ls_id = identificador + "-" + instancia;
	    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_id);

	    	// Comprobamos si hay que realizar la firma delegada a través del buzon de firma
			if (firmaDelegada){
				// Solo se podra realizar firma delegada si la entidad tiene habilitada la delegacion
				if (this.datosSesion.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO
						|| !this.datosSesion.getPersonaPAD().isHabilitarDelegacion()){
					throw new ProcessorException("No puede indicarse firma delegada si la entidad no admite delegacion",MensajeFront.MENSAJE_ERRORDESCONOCIDO);
				}

				// Modificamos estado delegacion documento
				docPad.setDelegacionEstado(DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA);
				docPad.setDelegacionFirmantes(tramiteInfo.getFormulario(identificador,instancia).getFirmante());
				docPad.setDelegacionFirmantesPendientes(docPad.getDelegacionFirmantes());

				// Actualizamos PAD
				this.actualizarPAD();

			}else{
				// En caso de firma digital verificamos firmante
		    	if ( !verificaFirmanteAdecuado( tramiteInfo.getFormulario( identificador, instancia ).getFirmante(), firma ) )
	    		{
	    			MensajeFront mens = new MensajeFront();
	    	    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
	    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_FIRMANTE_INCORRECTO ));
	    	    	mens.setMensajeExcepcion( "Firmante incorrecto" );
	    	    	return generarRespuestaFront(mens,null);
	    		}

		    	// Guardamos la firma
		    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		    	rds.asociarFirmaDocumento(docPad.getRefRDS(),firma);

		    	// Cacheamos firma
		    	List firmas = new ArrayList();
		    	firmas.add(firma);
		    	this.firmaDocumentos.put(docPad.getRefRDS().toString(),firmas);
			}

	    	// Actualizamos estado paso rellenar formularios
	    	pasoRellenar.setCompletado(evaluarEstadoPaso(pasoActual));

	    	// Si estan rellenados los formularios calculamos email/sms de alertas
	    	if (pasoRellenar.getCompletado().equals(PasoTramitacion.ESTADO_COMPLETADO) ||
	    			pasoRellenar.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)) {
	    		actualizarInfoAlertasTramitacion();
	    	}

	    	// Vamos a paso actual
	    	return irAPaso(pasoActual);



    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al guardar formulario",pe);
    		//log.error(mensajeLog("ProcessorException al guardar formulario"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al guardar formulario"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }



    /**
     * Borrar anexo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront borrarAnexo(String identificador,int instancia) {
    	try{
    		// TODO: Blindar para q no puedan anexarse docs q pertenecen a otro flujo

    		// Comprobamos que estamos en el paso de anexar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_ANEXAR){
    			throw new Exception("Se ha invocado a borrar anexo desde un paso distinto a anexar");
    		}

	    	// Borramos anexo actualizando RDS y PAD, y evaluando de nuevo el estado del paso de Anexar
    		borrarAnexoImpl(identificador,instancia);

	    	// Establecemos respuesta front
	    	return this.generarRespuestaFront(null,null);
    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al borrar anexo",pe);
    		//log.error(mensajeLog("ProcessorException al borrar anexo"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al borrar anexo"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }



    /**
     *
     * Anexar documento
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param identificador Identificador documento
     * @param instancia	Número de instancia
     * @param datosDocumento Contenido del documento
     * @param nomFichero Nombre del fichero
     * @param extension Extension del fichero
     * @param descPersonalizada	Descripción personalizada (para documentos genéricos)
     * @return
     */
    public RespuestaFront uploadAnexo(String identificador,int instancia,byte[] datosDocumento,String nomFichero,String extension,String descPersonalizada) {

    	try{

    		// Obtenemos estado persistencia documento
	    	String idAnexo = identificador + "-" + instancia;


    		// Comprobamos que estamos en el paso de anexar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_ANEXAR){
    			throw new Exception("Se ha invocado a anexar documento desde un paso distinto a anexar");
    		}

    		// Actualizamos tramite info
    		this.actualizarTramiteInfo();

    		// Obtenemos configuracion anexo
	    	Documento doc = obtenerDocumentoAnexo(identificador);
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Comprobamos que se deba anexar telematicamente
	    	if ( doc.getAnexoPresentarTelematicamente() != 'S' ){
	    		throw new Exception("El documento " + identificador + " no debe anexarse telematicamente");
	    	}

	    	// Comprobamos que exista documento y que cumpla las restricciones de extensión y tamaño
	    	// 		- Documento con datos
    		 if ((datosDocumento == null || datosDocumento.length <= 0 || StringUtils.isEmpty(nomFichero))){
    			throw new Exception("El fichero anexado no contiene datos");
    		 }
    		// 		- Extensión
    		if ((doc.getAnexoExtensiones().toLowerCase() + ",").indexOf(extension.toLowerCase() + ",") == -1){
    			throw new Exception("El fichero anexado no tiene extensión válida: " + extension + " (Permitidas: " + doc.getAnexoExtensiones() + ")");
    		}

    		// 		- Tamaño
    		if (doc.getAnexoTamanyoMax().intValue() < (datosDocumento.length / 1024) ) {
    			throw new Exception("El fichero anexado excede el tamaño permitido (" + doc.getAnexoTamanyoMax().intValue() + "): " + (datosDocumento.length / 1024));
    		}



	    	// Insertamos en el RDS
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	ReferenciaRDS refRds;
	    	DocumentoRDS docRds = new DocumentoRDS();
    		docRds.setModelo(doc.getModelo());
    		docRds.setVersion(docNivel.getVersion());
    		docRds.setDatosFichero(datosDocumento);
    		if (doc.getGenerico() == 'S'){
    			docRds.setTitulo(descPersonalizada);
    		}else{
    			docRds.setTitulo(((TraDocumento) doc.getTraduccion()).getDescripcion());
    		}
    		docRds.setNombreFichero(nomFichero);
    		docRds.setExtensionFichero(extension);
    		docRds.setUnidadAdministrativa(tramiteVersion.getUnidadAdministrativa().longValue());
    		if (datosSesion.getNivelAutenticacion() != 'A'){
    			docRds.setNif(datosSesion.getNifUsuario());
    			docRds.setUsuarioSeycon(datosSesion.getCodigoUsuario());
    		}
    		docRds.setIdioma(this.datosSesion.getLocale().getLanguage());

    		// Verificamos si se debe transformar a PDF
    		TransformacionRDS transf = null;
    		if (doc.getAnexoConversionPDF() == 'S' &&
    				esConvertibleAPdf(extension)){
    			transf = new TransformacionRDS();
    			transf.setConvertToPDF(true);
    			transf.setBarcodePDF(true);
    		}

    		// Insertamos en RDS
    		if (transf == null) {
    			refRds = rds.insertarDocumento(docRds);
    		} else {
    			refRds = rds.insertarDocumento(docRds, transf);
    		}

    		// Cacheamos referencia en map de upload
    		this.uploadAnexos.put(idAnexo, refRds);

	    	// Devolvemos respuesta front
	    	return this.generarRespuestaFront(null,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al upload anexo: " + pe.getMessage(),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al upload anexo: " + e.getMessage()),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }

	private boolean esConvertibleAPdf(String extension) {
		return (("doc,docx,odt,").indexOf(extension.toLowerCase() + ",") != -1);
	}

    /**
     *
     * Descarga documento para poder firmarlo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param identificador Identificador documento
     * @param instancia	Número de instancia
     * @return
     */
    public RespuestaFront downloadAnexo(String identificador,int instancia) {
    	try{
    		// Obtenemos estado persistencia documento
	    	String idAnexo = identificador + "-" + instancia;

    		// Comprobamos que estamos en el paso de anexar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_ANEXAR){
    			throw new Exception("Se ha invocado a anexar documento desde un paso distinto a anexar");
    		}

    		// Buscamos anexo en el map temporal
    		ReferenciaRDS refRds = (ReferenciaRDS) this.uploadAnexos.get(idAnexo);
    		if (refRds == null) {
    			throw new Exception("No se ha uploadeado anexo " + idAnexo);
    		}

    		// Obtenemos anexo del RDS
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		DocumentoRDS docRds = rds.consultarDocumento(refRds);

	    	// Devolvemos nombre y datos del fichero
			HashMap param = new HashMap();
			param.put("nombrefichero",docRds.getNombreFichero());
			param.put("datosfichero",docRds.getDatosFichero());
			return this.generarRespuestaFront(null,param);

	    }catch (Exception e){
			log.error(mensajeLog("Exception al download anexo"),e);

			MensajeFront mens = new MensajeFront();
			mens.setTipo(MensajeFront.TIPO_ERROR);
			mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
			mens.setMensajeExcepcion(e.getMessage());

			RespuestaFront resFront = generarRespuestaFront(mens,null);
			setRollbackOnly();
			return resFront;
		}
    }

    /**
     *
     * Anexar documento
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param identificador Identificador documento
     * @param instancia	Número de instancia
     * @param descPersonalizada	Descripcion personalizada para genéricos
     * @param FirmaIntf firma del documento
     * @param boolean Firma delegada, indica que el documento debera firmarse por delegado de la entidad a traves de la bandeja de firma
     * @return
     */
    public RespuestaFront anexarDocumento(String identificador,int instancia, String descPersonalizada, FirmaIntf firma, boolean firmaDelegada) {

    	// byte[] datosDocumento,String nomFichero,String extension,String descPersonalizada,

    	boolean conversionPDF = false;
    	boolean nuevoGenerico = false;

    	try{
    		// En caso de documento telematico, sera el documento insertado
    		DocumentoRDS docRdsNuevo = null;

    		// Delegate del RDS
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();

    		String ls_id = identificador + "-" + instancia;

    		// Comprobamos que estamos en el paso de anexar
    		PasoTramitacion pasoRellenar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRellenar.getTipoPaso() != PasoTramitacion.PASO_ANEXAR){
    			throw new Exception("Se ha invocado a anexar documento desde un paso distinto a anexar");
    		}

    		// Actualizamos tramite info
    		this.actualizarTramiteInfo();

    		// Obtenemos configuracion anexo
	    	Documento doc = obtenerDocumentoAnexo(identificador);
	    	DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    	// Obtenemos estado persistencia documento
	    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_id);
	    	if (docPad == null){
		    	// En caso de no existir en la zona de persistencia comprobamos si es genérico
		    	// y si es correcto creamos documento persistente
	    		if (doc.getGenerico() != 'S'){
	    			throw new Exception("El anexo" + doc.getIdentificador() + " no existe en la zona de persistencia y no es genérico");
	    		}
	    		// El número de instancia debe ser correlativo
	    		if (instancia != tramitePersistentePAD.getMaximoNumeroInstanciaDocumento(doc.getIdentificador()) + 1)  throw new Exception("El número de instancia indicada para el anexo " +doc.getIdentificador()+ "no es la siguiente");
	    		// No debe sobrepasar el máximo de instancias
	    		if (instancia > doc.getMaxGenericos() ) throw new Exception("No se pueden anexar más instancias del anexo" + doc.getIdentificador());
	    		// Añadimos documento persistente
	    		docPad = new DocumentoPersistentePAD();
	    		docPad.setIdentificador(identificador);
	    		docPad.setNumeroInstancia(instancia);
	    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
	    		tramitePersistentePAD.getDocumentos().put(identificador + "-" + instancia,docPad);
	    		nuevoGenerico = true;
	    	}

	    	// Si el documento es genérico establecemos descripcion personalizada
	    	if (doc.getGenerico() == 'S'){
    			docPad.setDescripcionGenerico(descPersonalizada);
	    	}

	    	// Insertamos / actualizamos los documentos en el RDS
	    	// (si no hay que presentarlo telemáticamente no es necesario)
	    	if (doc.getAnexoPresentarTelematicamente() == 'S'){

	    		// Obtenemos anexo uploadeado anteriormente y lo quitamos de la cache
	    		ReferenciaRDS refRds =  (ReferenciaRDS) this.uploadAnexos.get(ls_id);
	    		this.uploadAnexos.remove(ls_id);
	    		if (refRds == null) {
	    			throw new Exception("No se ha uploadeado anexo " + ls_id);
	    		}
	    		docRdsNuevo = rds.consultarDocumento(refRds);

	    		// Actualizamos el RDS:
	    		// ---- Si no es la primera vez que se anexa, eliminamos anterior
		    	if (docPad.getEstado() != DocumentoPersistentePAD.ESTADO_NORELLENADO){
		    		UsoRDS usoOld = new UsoRDS();
		    		usoOld.setReferenciaRDS(docPad.getRefRDS());
		    		usoOld.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
		    		usoOld.setReferencia(tramitePersistentePAD.getIdPersistencia());
		    		rds.eliminarUso(usoOld);
		    	}
		    	// ---- Comprobamos si hay que firmar
	    		if (docNivel.getFirmar() == 'S'){
	    			// Si la firma es delegada actualizamos estado documento y firmantes
		    		if (firmaDelegada){
		    			// Solo se podra realizar firma delegada si la entidad tiene habilitada la delegacion
	    				if (this.datosSesion.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO
	    						|| !this.datosSesion.getPersonaPAD().isHabilitarDelegacion()){
	    					throw new ProcessorException("No puede indicarse firma delegada si la entidad no admite delegacion",MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    				}
		    			// Indicamos que el documento se debe firmar mediante la bandeja de firma
		    			docPad.setDelegacionEstado(DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA);
		    			// Controlamos en caso de que sea una nueva instancia de generico coger los firmantes de la anterior instancia
		    			// ya que todavia no existira en tramiteInfo
		    			if (nuevoGenerico){
		    				docPad.setDelegacionFirmantes(this.tramiteInfo.getAnexo(identificador,instancia - 1).getFirmante());
		    			}else{
		    				docPad.setDelegacionFirmantes(this.tramiteInfo.getAnexo(identificador,instancia).getFirmante());
		    			}
		    			docPad.setDelegacionFirmantesPendientes(docPad.getDelegacionFirmantes());
		    		}else{
		    		// Si la firma no es delegada, asociamos firma al nuevo documento
		    			// Verificamos que se haya firmado
		    			if ( firma == null )
		    			{
		    				MensajeFront mens = new MensajeFront();
			    	    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
			    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_DOCUMENTO_NO_FIRMADO ));
			    	    	mens.setMensajeExcepcion("No se ha firmado el documento" );
			    	    	return generarRespuestaFront(mens,null);
			    	    }
		    			// Verificamos quien lo firma
		    			//   (cogemos info del firmante de la primera instancia, asi nos aseguramos para los genericos ya que las nueva instancias a anexar todavia no esta en tramiteInfo)
		    			if ( !verificaFirmanteAdecuado( tramiteInfo.getAnexo( identificador, 1 ).getFirmante(), firma ) )
			    		{
			    			MensajeFront mens = new MensajeFront();
			    	    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
			    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_FIRMANTE_INCORRECTO ));
			    	    	mens.setMensajeExcepcion( "Firmante incorrecto" );
			    	    	return generarRespuestaFront(mens,null);
			    		}
		    			// Asociamos firma a nuevo documento
		    			rds.asociarFirmaDocumento(docRdsNuevo.getReferenciaRDS(), firma);
		    			// Cacheamos firma
		    			List firmas = new ArrayList();
				    	firmas.add(firma);
			    		this.firmaDocumentos.put(docRdsNuevo.getReferenciaRDS().toString(),firmas);
		    		}
	    		}
	    		// ----- Creamos uso de persistencia
	    		UsoRDS uso = new UsoRDS();
	    		uso.setReferenciaRDS(docRdsNuevo.getReferenciaRDS());
	    		uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
	    		uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
	    		rds.crearUso(uso);

	    		// Establecemos referencia en doc persistencia
	    		docPad.setReferenciaRDS(docRdsNuevo.getReferenciaRDS());
	    		docPad.setNombreFicheroAnexo(docRdsNuevo.getNombreFichero());

	    		// Verificamos si se ha convertido a PDF
	    		if (doc.getAnexoConversionPDF() == 'S' && docRdsNuevo.getExtensionFichero().toUpperCase().equals("PDF")) {
	    			conversionPDF = true;
	    		}

	    	}

	    	// Actualizamos PAD dando por correcto el documento
	    	docPad.setEstado(DocumentoPersistentePAD.ESTADO_CORRECTO);
	    	actualizarPAD();

	    	// Actualizamos estado paso anexar documentos
	    	pasoRellenar.setCompletado(evaluarEstadoPaso(pasoActual));

	    	// Establecemos respuesta front
	    	//  (Si se ha convertido el doc a PDF mostramos mensaje al usuario para que lo verifique)
	    	MensajeFront mensFinal = null;
	    	if (conversionPDF){
	    		mensFinal = new MensajeFront();
	    		mensFinal.setTipo(MensajeFront.TIPO_INFO);
	    		mensFinal.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ANEXOVERIFICARCONVERSION));
	    	}
	    	return this.generarRespuestaFront(mensFinal,null);

    	}catch (ProcessorException pe){
    		logProcessorException("ProcessorException al anexar",pe);
    		//log.error(mensajeLog("ProcessorException al anexar"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al anexar"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    }

    /**
     * Finalizar tramite. Borra tramite si no se ha borrado todavía.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront finalizarTramite() {
    	return borrarTramitePersistenciaImpl(true);
    }


    /**
     * Elimina trámite de la zona de persistencia
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront borrarTramitePersistencia() {
    	return borrarTramitePersistenciaImpl(false);
    }


    /**
     * Elimina tramite de persistencia.
     * @param finalizado Indica si el tramite esta finalizado
     * @return
     */
	private RespuestaFront borrarTramitePersistenciaImpl(boolean finalizado) {
		String res = "N";
    	String  mensAudit="";

    	boolean yaBorrado = this.borradoPersistencia;
    	try{

    		// Quitamos trámite de la zona de persistencia
    		if (!yaBorrado) {
    			boolean pagoIniciado = isPagoIniciado( tramiteInfo );
    			boolean pagoTelematicoFinalizado = isPagoTelematicoFinalizado( tramiteInfo );

    			// Si no esta finalizado, no permitimos borrar si tiene pagos telematicos finalizados
    			if (!finalizado && pagoTelematicoFinalizado) {
    				RespuestaFront resp = new RespuestaFront();
    		    	resp.setInformacionTramite(this.tramiteInfo);
    		    	MensajeFront mens = new MensajeFront();
    		    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
    		    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_NO_BORRAR_SI_PAGOS));
    		    	resp.setMensaje(mens);
    		    	resp.setParametros(null);
    		    	return resp;
    			}

    			// Borramos de persistencia (hacemos backup si tiene pagos iniciados/finalizados)
    			borrarPersistencia( pagoIniciado );

    		}

	    	// Establecemos respuesta front
	    	RespuestaFront resp = new RespuestaFront();
	    	this.tramiteInfo = null; // Anulamos información trámite
	    	resp.setInformacionTramite(this.tramiteInfo);
	    	MensajeFront mens = new MensajeFront();
	    	mens.setTipo(MensajeFront.TIPO_WARNING);
	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_TRAMITEBORRADO));
	    	resp.setMensaje(mens);
	    	resp.setParametros(null);
	    	res = "S";
	    	return resp;
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al borrar tramite"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());
    		mensAudit = e.getMessage();

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	} finally{
    		if (!yaBorrado && "S".equals(res))
    			logAuditoria(ConstantesAuditoria.EVENTO_BORRADO_TRAMITE,res,mensAudit,null,null,false);
    	}
	}


    /**
     * Realiza el registro/preregistro/envio del trámite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public RespuestaFront registrarTramite(String asiento,FirmaIntf firma) {

    	ResultadoRegistrar res = null;
    	String result = "N";
    	String mensAudit=null;

    	try{

    		// Comprobamos que se llama a este paso desde el paso de REGISTRAR
    		PasoTramitacion pasoRegistrar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRegistrar.getTipoPaso() != PasoTramitacion.PASO_REGISTRAR){
    			// Vamos al paso actual (F5 en justificante)
    			result = ""; // No auditamos
    			return this.irAPaso(pasoActual);
    			// throw new Exception("Se ha invocado a registrar trámite desde un paso distinto a registrar");
    		}

    		// Comprobamos plazo
    		try{
    			validarPlazo();
    		}catch (ProcessorException pex){
    			// Eliminamos trámite
    			//borrarPersistencia();
    			MensajeFront mens = new MensajeFront();
        		mens.setTipo(MensajeFront.TIPO_ERROR);
        		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_PLAZOCERRADO));
        		mens.setMensajeExcepcion("El plazo de tramitación ha concluido");
        		return generarRespuestaFront(mens,null);
    		}

    		// Proceso de registrar trámite
    		// Antes comprobamos que el firmante es el adecuado

			// Comprobamos quien firma el asiento
	    	FactoriaObjetosXMLRegistro factoriaRT = ServicioRegistroXML.crearFactoriaObjetosXML();
			AsientoRegistral asientoRegistral = factoriaRT.crearAsientoRegistral (
					new ByteArrayInputStream(asiento.getBytes(ConstantesXML.ENCODING)));

			if (  firma != null && !verificaFirmanteAdecuado( asientoRegistral, firma ) )
    		{
    			MensajeFront mens = new MensajeFront();
    	    	mens.setTipo(MensajeFront.TIPO_ERROR_CONTINUABLE);
    	    	mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERROR_FIRMANTE_INCORRECTO ));
    	    	mens.setMensajeExcepcion( "Firmante incorrecto" );
    	    	return generarRespuestaFront(mens,null);
				//throw new ProcessorException ( "Firmante incorrecto. Debe firmar el representante ", MensajeFront.MENSAJE_ERROR_FIRMANTE_INCORRECTO );
    		}


			// Ejecutamos script para chequeo de envio
			try
			{
				byte [] scriptCheckEnvio = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones().getCheckEnvio();
				if (scriptCheckEnvio == null || scriptCheckEnvio.length <= 0)
					scriptCheckEnvio = tramiteVersion.getEspecificaciones().getCheckEnvio();

				if (scriptCheckEnvio != null && scriptCheckEnvio.length > 0){
					this.evaluarScript(scriptCheckEnvio,null);
				}
			}
			catch( ProcessorException pexc )
			{
				 // Mostramos mensaje error
				String mensajeError=traducirMensaje(pexc);
				MensajeFront mens = new MensajeFront();
	    		mens.setTipo(MensajeFront.TIPO_WARNING);
	    		mens.setMensaje(mensajeError);
	    		mens.setMensajeExcepcion("");
	    		// Establecemos parametros paso al que vamos
		    	HashMap param = evaluarParametrosPaso(pasoActual);
	    		return generarRespuestaFront(mens,param);
			}

    		// Realizamos proceso de registro/consulta
    		res = procesoRegistrar(asiento,firma);
    		this.resultadoRegistro = res;

    		// En caso de ser un preenvio comprobamos si esta activada la confirmacion automatica
    		if (asientoRegistral.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREENVIO &&
    			tramiteVersion.getPreenvioConfirmacionAutomatica() == 'S'){
    				// Realizamos la confirmacion automatica de la entrada como si fuera un registro incorrecto confirmado por el gestor
    				PadDelegate pad = DelegatePADUtil.getPadDelegate();
    				pad.confirmacionAutomaticaPreenvio(tramitePersistentePAD.getIdPersistencia(),resultadoRegistro.getNumero(),new Timestamp(resultadoRegistro.getFecha().getTime()));
    		}

    		// Eliminamos trámite de la zona de persistencia, sin realizar back-up
    		borrarPersistencia( false );

    		// Damos el paso como completado
    		pasoRegistrar.setCompletado(PasoTramitacion.ESTADO_COMPLETADO);

    		// Vamos a siguiente paso
    		RespuestaFront respuesta = irAPaso(pasoActual + 1);
    		if (existeError(respuesta)){
    			result = "N";
	    		mensAudit = respuesta.getMensaje().getMensajeExcepcion();
	    	}
    		result = "S";
    		return respuesta;
    	}
    	catch ( RegistroTelematicoException re )
    	{
    		log.error(mensajeLog("Excepción en el proceso de registro telemático"),re);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(re.getCodigoError()));
    		mens.setMensajeExcepcion(re.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    	catch (ProcessorException pe)
    	{
    		logProcessorException("ProcessorException al registrar",pe);
    		//log.error(mensajeLog("ProcessorException al registrar"),pe);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(pe));
    		mens.setMensajeExcepcion(pe.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    	catch (Exception e)
    	{
    		log.error(mensajeLog("Exception al registrar"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}
    	finally
    	{
    		// Realizamos log auditoria solo para tramites de tipo consulta (para el resto se auditan en la zona personal)
    		if (result.equals("S") && this.resultadoRegistro != null && this.resultadoRegistro.getTipo() == ResultadoRegistrar.CONSULTA){
	    		logAuditoria(ConstantesAuditoria.EVENTO_ENVIO_TRAMITE,result,mensAudit,Character.toString(tramiteVersion.getDestino()), this.resultadoRegistro.getProcedimiento(), false);
    		}
    	}
    }

    /**
     * Muestra documento para ser impreso. En caso de formularios y pagos se generará el pdf asociado
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param idDocumento
     * @param instancia
     * @return
     */
    public RespuestaFront mostrarDocumento( String idDocumento, int instancia )
    {
    	try
    	{
    		// Consultamos documento formateado al RDS
    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
    		ReferenciaRDS refRds = docPAD.getRefRDS();

    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		DocumentoRDS docRds = rds.consultarDocumentoFormateado(refRds,this.datosSesion.getLocale().getLanguage());

    		// Devolvemos nombre y datos del fichero
    		HashMap param = new HashMap();
    		param.put("nombrefichero",docRds.getNombreFichero());
    		param.put("datosfichero",docRds.getDatosFichero());
    		return this.generarRespuestaFront(null,param);

    	}catch (Exception e){
    		log.error(mensajeLog("Exception al registrar"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}

    }
    
    /**
     * Recupera DocumentoRDS a partir del idDocumento y el número de instancia
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param idDocumento
     * @param instancia
     * @return
     */
    public DocumentoRDS recuperaInfoDocumento( String idDocumento, int instancia )
    {
    	try
    	{
    		// Consultamos documento formateado al RDS
    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
    		ReferenciaRDS refRds = docPAD.getRefRDS();

    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		DocumentoRDS docRds = rds.consultarDocumento(refRds,false);
    		return docRds;

    	}catch (Exception e){
    		log.error("Excepción al recuperar documento " + idDocumento + " instancia " + instancia,e);
    		throw new EJBException("Excepción al recuperar documento " + idDocumento + " instancia " + instancia,e);
    	}

    }

    /**
     * Muestra XML del formulario para debug
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param idDocumento
     * @param instancia
     * @return
     */
    public RespuestaFront mostrarFormularioDebug( String idDocumento, int instancia )
    {
    	try
    	{
    		// Solo para entorno desarrollo
    		if (!this.entornoDesarrollo){
    			throw new Exception("Esta funcion es solo para entornos de desarrollo");
    		}

    		// Consultamos documento formateado al RDS
    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
    		ReferenciaRDS refRds = docPAD.getRefRDS();

    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		DocumentoRDS docRds = rds.consultarDocumento(refRds);

    		// Devolvemos nombre y datos del fichero
    		HashMap param = new HashMap();
    		param.put("nombrefichero",docRds.getNombreFichero());
    		param.put("datosfichero",docRds.getDatosFichero());
    		return this.generarRespuestaFront(null,param);

    	}catch (Exception e){
    		log.error(mensajeLog("Exception al registrar"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}

    }

    /**
     * Muestra justificante para ser impreso
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @return
     */
    public RespuestaFront mostrarJustificante()
    {
    	try
    	{
    		// Comprobamos que se llama a este paso desde el paso de FINALIZAR
    		PasoTramitacion pasoRegistrar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRegistrar.getTipoPaso() != PasoTramitacion.PASO_FINALIZAR){
    			throw new Exception("Se ha invocado a guardar justificante desde un paso distinto a finalizar");
    		}

    		// Calculamos si la tramitacion es presencial o telematica
    		boolean tramitacionPresencial = tramiteInfo.getTipoTramitacion() == ConstantesSTR.TIPO_TRAMITACION_PRESENCIAL ||
			( tramiteInfo.getTipoTramitacion() == ConstantesSTR.TIPO_TRAMITACION_DEPENDIENTE &&
			  tramiteInfo.getTipoTramitacionDependiente() == ConstantesSTR.TIPO_TRAMITACION_PRESENCIAL);
    		
    		// Si es tramitacion presencial mostramos justificante generado por Sistra.     		
    		byte[] content = null;
    		if (tramitacionPresencial) {
    			content = generarJustificante(true);
    		} else {
    			if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO){
    				// Si es tramite telematico intentamos mostrar justificante generado por Registro
        			PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
        			content = plgRegistro.obtenerJustificanteRegistroEntrada(tramiteInfo.getEntidad(), resultadoRegistro.getNumero(), resultadoRegistro.getFecha());
    			}
    			
    			// Si registro no muestra justificante, mostramos el de la plataforma
    			if (content == null) {
    				content = generarJustificante(false);
    			}
    		}
    		    		

    		// Devolvemos nombre y datos del fichero
    		HashMap param = new HashMap();
    		// Normalizamos nombre justificante
    		String nomfic = StringUtil.generarNombreFicheroJustificante(this.datosSesion.getLocale().getLanguage(), resultadoRegistro.getNumero(), resultadoRegistro.getFecha());
    		param.put("nombrefichero",nomfic);
    		param.put("datosfichero",content);
    		return this.generarRespuestaFront(null,param);

    	}catch (Exception e){
    		log.error(mensajeLog("Exception al registrar"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}

    }

    
    // Genera justificante prereregistro
	private byte[] generarJustificante(boolean tramitacionPresencial) throws Exception {
		// Comprobamos si mostramos justificante standard o formulario justificante
		// y en el caso de justificante standard si se deben anexar al justificante
		String identificadorFJ = null;
		int instanciaFJ=1;
		List formsJustif = new ArrayList();
		for (Iterator it=this.tramiteInfo.getFormularios().iterator();it.hasNext();){
			DocumentoFront doc = (DocumentoFront) it.next();

			// Formulario justificante (nos quedamos con el primero)
			if (identificadorFJ == null && doc.getEstado() == DocumentoFront.ESTADO_CORRECTO && doc.isFormularioJustificante()){
				identificadorFJ=doc.getIdentificador();
				instanciaFJ=doc.getInstancia();
			}

			// Formularios a anexar al justificante standard
			if (doc.isFormularioAnexarJustificante() && doc.getEstado() == DocumentoFront.ESTADO_CORRECTO) {
				formsJustif.add(doc.getIdentificador() + "-" + doc.getInstancia());
			}

		}

		String nomfic=null;
		byte[] content=null;
		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		if (identificadorFJ==null){
			// Mostramos justificante standard
    		ReferenciaRDS refRds = this.resultadoRegistro.getRdsJustificante();
    		DocumentoRDS docRds = rds.consultarDocumentoFormateado(refRds,this.datosSesion.getLocale().getLanguage());
    		nomfic=docRds.getNombreFichero();
    		content=docRds.getDatosFichero();

    		// En caso de que la tramitacion sea telematica y que el justificante sea estandar comprobamos si hay que anexar formularios al justificante.
    		if (!tramitacionPresencial && formsJustif.size() > 0) {
    			InputStream [] pdfIn = new ByteArrayInputStream[formsJustif.size() + 1];
    			pdfIn[0] = new ByteArrayInputStream(content);
    			int i = 1;
    			for (Iterator it = formsJustif.iterator(); it.hasNext();) {
    				String refDoc = (String) it.next();
    				DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(refDoc);
    				DocumentoRDS docRdsForJus = rds.consultarDocumentoFormateado(docPAD.getRefRDS(),this.datosSesion.getLocale().getLanguage());
    				pdfIn[i] = new ByteArrayInputStream(docRdsForJus.getDatosFichero());
    				i++;
    			}
    			ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
    			UtilPDF.concatenarPdf(bos, pdfIn);
    			content = bos.toByteArray();
    		}

		}else{
			// Consultamos documento formateado al RDS generando copias para interesado y administracion
    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(identificadorFJ + "-" + instanciaFJ);
    		ReferenciaRDS refRds = docPAD.getRefRDS();

    		DocumentoRDS docRds;

    		// En caso de que no sea completamente telematico mostramos 2 copias
    		if (tramitacionPresencial)
    		{
    			docRds = rds.consultarDocumentoFormateadoCopiasInteresadoAdmon(refRds,this.datosSesion.getLocale().getLanguage());
    		}else{
    			docRds = rds.consultarDocumentoFormateado(refRds,this.datosSesion.getLocale().getLanguage());
    		}
    		nomfic=docRds.getNombreFichero();
    		content = docRds.getDatosFichero();
		}
		return content;
	}


    /**
     * Muestra documento resultado de una consulta
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param idDocumento
     * @param instancia
     * @return
     */
    public RespuestaFront mostrarDocumentoConsulta( int numDoc )
    {
    	try
    	{
    		// Comprobamos que se llama a este paso desde el paso de finalizar
    		PasoTramitacion pasoRegistrar = (PasoTramitacion) pasosTramitacion.get(pasoActual);
    		if (pasoRegistrar.getTipoPaso() != PasoTramitacion.PASO_FINALIZAR){
    			// Vamos al paso actual (F5 en justificante)
    			return this.irAPaso(pasoActual);
    		}

    		// Obtenemos documento consulta
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		HashMap param = new HashMap();
    		DocumentoRDS docRds;
    		DocumentoConsulta doc = (DocumentoConsulta) resultadoRegistro.getDocumentos().get(numDoc);
    		switch (doc.getTipoDocumento()){
    			case DocumentoConsulta.TIPO_DOCUMENTO_BIN:
    				param.put("nombrefichero",doc.getNombreFichero());
    				param.put("datosfichero",doc.getContenidoFichero());
    				break;
    			case DocumentoConsulta.TIPO_DOCUMENTO_RDS:
    				ReferenciaRDS ref = new ReferenciaRDS();
    				ref.setCodigo(doc.getCodigoRDS());
    				ref.setClave(doc.getClaveRDS());
    				docRds = rds.consultarDocumentoFormateado(ref,this.datosSesion.getLocale().getLanguage());
    				param.put("nombrefichero",docRds.getNombreFichero());
    				param.put("datosfichero",docRds.getDatosFichero());
    				break;
    			case DocumentoConsulta.TIPO_DOCUMENTO_XML:
    				docRds = new DocumentoRDS();
    				docRds.setTitulo(doc.getNombreDocumento());
    				docRds.setNombreFichero(doc.getNombreDocumento()+ ".xml");
    				docRds.setDatosFichero(doc.getXml().getBytes(ConstantesXML.ENCODING));

    				docRds = rds.formatearDocumento(docRds,doc.getModelo(),doc.getVersion(),doc.getPlantilla(),this.datosSesion.getLocale().getLanguage());
    				param.put("nombrefichero",docRds.getNombreFichero());
    				param.put("datosfichero",docRds.getDatosFichero());
    				break;
    			case DocumentoConsulta.TIPO_DOCUMENTO_URL:
    				param.put("nombrefichero",doc.getNombreFichero());
    				param.put("urlfichero",doc.getUrlAcceso());
    				break;
    		}

    		return this.generarRespuestaFront(null,param);

    	}catch (Exception e){
    		log.error(mensajeLog("Exception al mostrar documento consulta"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    		return resFront;
    	}

    }

    /**
     * Devuelve si esta habilitado debug.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     */
    public boolean isDebugEnabled()
    {
    	return this.debugEnabled;
    }


    /**
     * Obtiene url finalización trámite a partir del script de finalización
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @return Url fin
     */
    public String obtenerUrlFin()
    {
    	try
    	{
    		byte[] scriptUrlFin;
    		String urlFin=null;

    		// Ejecutamos script para calcular url de fin
        	EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
        	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();
        	if (especNivel.getUrlFin() != null && especNivel.getUrlFin().length > 0){
        		scriptUrlFin = especNivel.getUrlFin();
        	}else{
        		scriptUrlFin = especVersion.getUrlFin();
        	}
        	if (scriptUrlFin != null && scriptUrlFin.length > 0){
	        	// Ejecutamos script
	        	urlFin = this.evaluarScript(scriptUrlFin,null);
	        	if (!StringUtils.isEmpty ( urlFin )){
	        		// Validamos construccion url
	        		urlFin = urlFin.trim();
	            	try{
	            		if (!("[ZONAPER]".equals(urlFin))){
	            			new URL(urlFin);
	            		}
	            	}catch(MalformedURLException mue){
	            		log.error("Url de finalizacion '" + urlFin + "' no valida para trámite " + this.tramiteVersion.getTramite().getIdentificador() + "-" + this.tramiteVersion.getVersion() + ": "  + mue.getMessage(),mue);
	            		urlFin = null;
	            	}
	            }
        	}

        	// Si la url es nula devolvemos url de portal
        	if (urlFin == null){
        		urlFin = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo().getUrlPortal();
        	}

        	return urlFin;

    	}catch(Exception ex){
    		log.error("Error obteniendo url de finalizacion: " + ex.getMessage(),ex);
    		return null;
    	}
    }


    /**
     * Obtiene id persistencia. Este metodo se irá llamando para ir manteniendo desde forms el ejb de session vivo.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @return Id persistencia
     */
    public String obtenerIdPersistencia()
    {
    	String id="";
    	try
    	{
    		if (tramitePersistentePAD != null) {
    			id = tramitePersistentePAD.getIdPersistencia();
    		}
    	}catch(Exception ex){
    		log.error("Error obteniendo id persistencia: " + ex.getMessage(),ex);
    		return null;
    	}

		debug("obtenerIdPersistencia: " + id);
    	return id;
    }

    /**
     * Establece los valores de seleccion de notificacion.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     */
    public void habilitarNotificacion(boolean habilitarNotificacion,String emailAviso, String smsAviso)
    {
    	this.habilitarNotificacionTelematica = new Boolean(habilitarNotificacion);
    	this.emailAviso = emailAviso;
    	if (tramiteInfo.isPermiteSMS()) {  
    		this.smsAviso = smsAviso;
    	}
    }

    /**
     * Resetea los valores de seleccion de confirmacion de notificaciones y avisos.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     */
    public void resetHabilitarNotificacion() {
    	this.habilitarNotificacionTelematica = null;
    	this.emailAviso = null;
    	if (tramiteInfo.isPermiteSMS()) {    			
    		this.smsAviso = null;
    	}
    }

    
    /**
     * Verificacion movil.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     */
    public boolean verificarMovil(String smsCodigo)
    {
    	
    	boolean verificado = false;
    	
    	try {
    		if (StringUtils.equalsIgnoreCase(this.codigoSmsVerificarMovil, smsCodigo) ) {
	    		// Registramos en log
		    	DelegatePADUtil.getPadDelegate().logSmsVerificarMovil(this.tramitePersistentePAD.getIdPersistencia(), this.smsAviso, this.codigoSmsVerificarMovil);
		    	// Indicamos que se ha verificado
		    	verificado = true;
	    	}	    	
    	}catch (Exception e){
    		log.error(mensajeLog("Exception al mostrar documento consulta"),e);

    		MensajeFront mens = new MensajeFront();
    		mens.setTipo(MensajeFront.TIPO_ERROR);
    		mens.setMensaje(traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
    		mens.setMensajeExcepcion(e.getMessage());

    		RespuestaFront resFront = generarRespuestaFront(mens,null);
    		setRollbackOnly();
    	}
    	
    	this.verificadoMovil = verificado;
    	return this.verificadoMovil;
    }
    
    /**
     * Enviar codigo de nuevo.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     */
    public void resetCodigoSmsVerificarMovil()
    {
    	// Reseteamos codigo para que se vuelva a enviar SMS al ir a paso
    	this.codigoSmsVerificarMovil = null;    	
    }
    
    

    
    //  -------------------------------------------------------
    // 	---------- Funciones auxiliares -----------------------
    //  -------------------------------------------------------

//  Borramos anexo actualizando RDS y PAD, y evaluando de nuevo el estado del paso de Anexar
    //  (PREVIAMENTE A INVOCAR ESTA FUNCION, TRAMITEINFO DEBE ESTAR ACTUALIZADO)
    private void borrarAnexoImpl(String identificador,int instancia) throws Exception {

    	// Obtenemos paso Anexar
    	PasoTramitacion pasoAnexar=null;
    	int indexPasoAnexar=0;
    	for (int i=0;i<pasosTramitacion.size();i++){
    		pasoAnexar = (PasoTramitacion) pasosTramitacion.get(i);
    		if (pasoAnexar.getTipoPaso() == PasoTramitacion.PASO_ANEXAR) {
    			indexPasoAnexar = i;
    			break;
    		}
    		pasoAnexar=null;
    	}
    	if (pasoAnexar == null) {
    		throw new Exception("No se ha econtrado paso Anexar");
    	}

    	// Obtenemos configuracion anexo
    	Documento doc = obtenerDocumentoAnexo(identificador);
    	//DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

    	// Obtenemos estado persistencia documento
    	String ls_id = identificador + "-" + instancia;
    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_id);

    	// Actualizamos los datos en RDS y PAD
    	// --- Eliminamos documento en RDS (=> Eliminar uso y no el documento ya que puede estar referenciado por alguien más)
    	// Si es anexo y no se presenta telemáticamente no hace falta borrar RDS
    	if (doc.getAnexoPresentarTelematicamente() == 'S'){
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	UsoRDS uso = new UsoRDS();
	    	uso.setReferenciaRDS(docPad.getRefRDS());
	    	uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
	    	uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
	    	rds.eliminarUso(uso);
    	}

    	//  En caso de estar firmado lo quitamos de cache (en el próximo paso se pone a null la referencia)
    	if (docPad.getRefRDS() != null && this.firmaDocumentos.containsKey(docPad.getRefRDS().toString())) this.firmaDocumentos.remove(docPad.getRefRDS().toString());

    	// Preparamos actualización PAD
    	// -- Sólo borramos el documento de la PAD si es genérico y hay más de una instancia
    	// -- sino sólo eliminamos referencia RDS y actualizamos estado
    	// -- si el documento es obligatorio y no hay que presentarlo telemáticamente, no cambiamos estado
    	if (tramitePersistentePAD.getNumeroInstanciasDocumento(identificador) > 1){
    		tramitePersistentePAD.borrarDocumento(identificador,instancia);
    	}else{
    		docPad.setReferenciaRDS(null);
    		if (doc.getAnexoPresentarTelematicamente() == 'N' && doc.getGenerico() == 'N' && tramiteInfo.getAnexo(identificador,instancia).getObligatorio() == 'S'){
    			// NO CAMBIAMOS ESTADO, YA QUE LOS DOCUMENTOS PRESENCIALES OBLIGATORIOS AUTOMATICAMENTE SE DAN POR ENTREGADOS (EXCEPTO GENERICOS)
    		}else{
    			docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
    			docPad.setDelegacionEstado(null);
    			docPad.setDelegacionFirmantes(null);
    			docPad.setDelegacionFirmantesPendientes(null);
    		}
    	}

    	// Actualizamos estado paso anexar documentos
    	pasoAnexar.setCompletado(evaluarEstadoPaso(indexPasoAnexar));

    	// Actualizamos PAD
    	actualizarPAD();
    }



    // Valida acceso
    private void validarAcceso() throws Exception{

    	// Validamos que exista nivel de autenticación
    	if (tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()) == null){
    		debug(mensajeLog("No existe nivel de autenticación " + datosSesion.getNivelAutenticacion()));
    		throw new ProcessorException("No existe nivel de autenticación",MensajeFront.MENSAJE_NOEXISTENIVEL);
    	}

    	// Valida si el trámite esta activo
    	if (tramiteVersion.getEspecificaciones().getActivo().equals("N")) {
    		debug(mensajeLog("Tramite inactivo"));
    		throw new ProcessorException("Tramite inactivo",MensajeFront.MENSAJE_TRAMITEINACTIVO);
    	}
    	if (tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones().getActivo().equals("N")) {
    		debug(mensajeLog("Tramite inactivo"));
    		throw new ProcessorException("Tramite inactivo",MensajeFront.MENSAJE_TRAMITEINACTIVO);
    	}

    	//  Comprobamos restricciones en modo delegado
    	if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(this.datosSesion.getPerfilAcceso())){
    		// No esta permitido modo delegado y flujo tramitacion
    		if (tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones().getFlujoTramitacion().equals("S")){
    			debug(mensajeLog("No puede establecerse flujo de tramitacion para modo delegado"));
        		throw new ProcessorException("No puede establecerse flujo de tramitacion para modo delegado",MensajeFront.MENSAJE_ERRORDESCONOCIDO);
    		}
    		// Permiso para rellenar tramites
    		if (this.datosSesion.getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_RELLENAR_TRAMITE) == -1){
    			debug(mensajeLog("Delegado no tiene permiso para rellenar tramite"));
        		throw new ProcessorException("Delegado no tiene permiso para rellenar tramite",MensajeFront.MENSAJE_DELEGADO_NO_PERMISO_RELLENAR);
    		}
        	// Permiso para presentacion
        	if ( TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_PRESENTACION.equals(this.tramitePersistentePAD.getEstadoDelegacion())){
        		if (this.datosSesion.getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
        			debug(mensajeLog("El tramite no puede modificarse, esta pendiente de presentacion"));
            		throw new ProcessorException("El tramite no puede modificarse, esta pendiente de presentacion",MensajeFront.MENSAJE_DELEGADO_TRAMITE_NO_MODIFICAR_PENDIENTE_PRESENTAR);
        		}
        	}
    	}
    		// No se puede entrar si esta pendiente de firmar documentos
    	if ( TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA.equals(this.tramitePersistentePAD.getEstadoDelegacion())){
    		debug(mensajeLog("El tramite no puede modificarse, esta pendiente de firmar documentos"));
        	throw new ProcessorException("El tramite no puede modificarse, esta pendiente de firmar documentos",MensajeFront.MENSAJE_DELEGADO_TRAMITE_NO_MODIFICAR_PENDIENTE_FIRMA);
    	}

    	// Valida script inicio tramite: control de acceso y carga de propiedades dinamicas
		byte[] script = null;
		script = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones().getValidacionInicioScript();
		if (script == null || script.length <= 0){
			script = tramiteVersion.getEspecificaciones().getValidacionInicioScript();
		}
		if (script != null && script.length > 0){
			// Evaluamos script (en caso de generarse error se producirá una excepción)
			debug(mensajeLog("Acceso denegado por no pasar validación inicio"));
			ConfiguracionDinamicaTramitePlugin confPlugin = new ConfiguracionDinamicaTramitePlugin();
			HashMap params = new HashMap();
    		params.put("CONFIGURACION_DINAMICA",confPlugin);
			evaluarScript(script,params);

			// Establecemos propiedades tramite dinamicas
			this.configuracionDinamica.setPlazoDinamico(confPlugin.isPlazoDinamico());
			this.configuracionDinamica.setPlazoInicio((confPlugin.getPlazoInicio()!=null?StringUtil.cadenaAFecha(confPlugin.getPlazoInicio(),"yyyyMMddHHmmss"):null));
			this.configuracionDinamica.setPlazoFin((confPlugin.getPlazoFin()!=null?StringUtil.cadenaAFecha(confPlugin.getPlazoFin(),"yyyyMMddHHmmss"):null));

			// Si se ha cambiado el plazo mediante script puede haber cambiado la fecha de caducidad desde
			// la inicialización (o carga) de tramitePAD con lo que volvemos a actualizar la PAD
			actualizarPAD();

    	}

    	// Valida el plazo de presentacion del trámite
    	validarPlazo();

    }

    // Valida plazo
    private void validarPlazo()throws ProcessorException{

    	Date fecIni,fecFin;

    	// Obtenemos hora actual
    	Timestamp ahora = new Timestamp(System.currentTimeMillis());

    	// Comprobamos si se ha especificado un plazo dinámicamente
		if (this.configuracionDinamica.isPlazoDinamico()){
    		// Plazo dinámico
			fecIni = this.configuracionDinamica.getPlazoInicio();
			fecFin = this.configuracionDinamica.getPlazoFin();
    	}else{
    		// Plazo fijo
    		fecIni = tramiteVersion.getInicioPlazo();
    		fecFin = tramiteVersion.getFinPlazo();
    	}

    	// Comprobamos plazo
	    if (fecIni != null && fecIni.after(ahora)) {
	    	throw new ProcessorException("Plazo no abierto",MensajeFront.MENSAJE_PLAZONOABIERTO);
	    }
	    if (fecFin != null && fecFin.before(ahora)) {
	    	throw new ProcessorException("Plazo cerrado",MensajeFront.MENSAJE_PLAZOCERRADO);
	    }

    }

    // Establecemos circuito en función configuración del trámite
    private void calcularPasos() throws ProcessorException{
    	pasosTramitacion = new ArrayList();
    	PasoTramitacion paso;

    	// Descomponemos la lista de documentos en formularios, anexos y pagos
    	for (Iterator it = tramiteVersion.getDocumentos().iterator();it.hasNext();){
    		Documento doc = (Documento) it.next();
    		DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());
    		if (docNivel == null) continue;

    		switch (doc.getTipo()){
	    		case Documento.TIPO_FORMULARIO:
	    			// Calculamos configuracion dinámica formulario
	    			ConfiguracionDinamica conf = new ConfiguracionDinamica();
	    			if (docNivel.getFormularioConfiguracionScript() != null && docNivel.getFormularioConfiguracionScript().length > 0){
	    				HashMap param = new HashMap();
	    				param.put("CONFIGURACIONFORMULARIO",conf);
	    				evaluarScript(docNivel.getFormularioConfiguracionScript(),param);
	    			}
					this.formularios.add(doc);
					this.configuracionFormularios.put(doc.getIdentificador(),conf);
					break;
    			case Documento.TIPO_ANEXO:
    				// Si el trámite es de consulta no se permiten anexos
    				if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_CONSULTA){
    					throw new ProcessorException("Si el trámite es de tipo consulta no se permiten anexos",
    		    				MensajeFront.MENSAJE_ERRORDESCONOCIDO);
    				}

    				// Calculamos si el tramite es presencial/telematico
    				if (doc.getAnexoPresentarTelematicamente() == 'N' ||
    					doc.getAnexoCompulsarPreregistro() == 'S' ) {
    					if (tipoTramitacion != 'P'){
    						if (docNivel.getObligatorio() == 'S') {
    							tipoTramitacion = 'P';
    						}else{
    							tipoTramitacion = 'D';
    						}
    					}
    				}
    				this.anexos.add(doc);
    				break;
    			case Documento.TIPO_PAGO:
    				// Si el trámite es de consulta no se permiten anexos
    				if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_CONSULTA){
    					throw new ProcessorException("Si el trámite es de tipo consulta no se permiten anexos",
    		    				MensajeFront.MENSAJE_ERRORDESCONOCIDO);
    				}
    				// Calculamos si el tramite es presencial/telematico
    				if (tipoTramitacion != 'P') {
    					tipoTramitacion = 'D';
    				}
    				this.pagos.add(doc);
    				break;
    		}
    	}

    	// Calculamos tipo tramitacion (Telematica/Presencial/Depende) según nivel autenticación
    	// --- Si hay que firmar el trámite y el nivel de autenticación no es certificado el tipo de tramitación será presencial
    	if (tramiteVersion.getFirmar() == 'S' && datosSesion.getNivelAutenticacion() != TramiteNivel.AUTENTICACION_CERTIFICADO){
    		tipoTramitacion = 'P';
    	}

    	// Si el trámite es de tipo consulta el tipo de tramitación debe ser telemática
    	if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_CONSULTA && tipoTramitacion != 'T'){
    		throw new ProcessorException("Si el trámite es de tipo consulta la tramitación debe ser telemática",
    				MensajeFront.MENSAJE_ERRORDESCONOCIDO);
    	}

    	//TramiteNivel tn = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion());
    	//EspecTramiteNivel espTramite = (EspecTramiteNivel) tramiteVersion.getEspecificaciones();
    	//EspecTramiteNivel espNivel = (EspecTramiteNivel) tn.getEspecificaciones();

    	// MOSTRAR PASOS
    	paso = new PasoTramitacion();
		paso.setTipoPaso(PasoTramitacion.PASO_PASOS);
		this.pasosTramitacion.add(paso);

    	// PASO INFORMACION PREVIA Y PLANTILLAS
		/*
		 * MODIFICACION: SIEMPRE HABRA "DEBE SABER"
		 */
		/*
    	boolean previa = false;
    	// --- comprobamos que existan instrucciones de inicio
    	if ( ( (espTramite != null && espTramite.getTraduccion() != null && StringUtils.isNotEmpty(((TraEspecTramiteNivel) espTramite.getTraduccion()).getInstruccionesInicio())) ||
    		   (espNivel != null && espNivel.getTraduccion() != null && StringUtils.isNotEmpty(((TraEspecTramiteNivel) espNivel.getTraduccion()).getInstruccionesInicio()))) ){
    		previa = true;
    	}else{
    	// --- o bien existan plantillas para descargar
    		for (int i=0;i<this.anexos.size();i++){
    			if ( ((Documento) this.anexos.get(i)).getAnexoDescargarPlantilla() == 'S' ) {
    				previa = true;
    				break;
    			}
    		}
    	}
    	if (previa){
    		paso = new PasoTramitacion();
    		paso.setTipoPaso(PasoTramitacion.PASO_DEBESABER);
    		this.pasosTramitacion.add(paso);
    	}
    	*/
		paso = new PasoTramitacion();
		paso.setTipoPaso(PasoTramitacion.PASO_DEBESABER);
		this.pasosTramitacion.add(paso);

    	// PASO FORMULARIOS
    	if (formularios.size() > 0){
    		paso = new PasoTramitacion();
    		paso.setTipoPaso(PasoTramitacion.PASO_RELLENAR);
    		this.pasosTramitacion.add(paso);
    	}

    	// PASO ANEXAR
    	if (anexos.size()>0){
    		paso = new PasoTramitacion();
    		paso.setTipoPaso(PasoTramitacion.PASO_ANEXAR);
    		this.pasosTramitacion.add(paso);
    	}

    	// PASO PAGOS
    	if (pagos.size() > 0){
    		paso = new PasoTramitacion();
    		paso.setTipoPaso(PasoTramitacion.PASO_PAGAR);
    		this.pasosTramitacion.add(paso);
    	}

    	// Si es tramite de tipo asistente no hay ningún envio
    	if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_ASISTENTE){
    		// PASO IMPRIMIR
	    	paso = new PasoTramitacion();
			paso.setTipoPaso(PasoTramitacion.PASO_IMPRIMIR);
			this.pasosTramitacion.add(paso);
    	}else{
	    	// PASO REGISTRAR
	    	paso = new PasoTramitacion();
			paso.setTipoPaso(PasoTramitacion.PASO_REGISTRAR);
			this.pasosTramitacion.add(paso);

			// PASO FINALIZAR
			paso = new PasoTramitacion();
			paso.setTipoPaso(PasoTramitacion.PASO_FINALIZAR);
			this.pasosTramitacion.add(paso);

    	}

    }

    // Inicializar Tramite (sólo para nuevos trámites)
    private void inicializarTramite() throws Exception{
    	try{
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	
	    	tramitePersistentePAD = new TramitePersistentePAD();
	    	tramitePersistentePAD.setTramite(tramiteVersion.getTramite().getIdentificador());
	    	tramitePersistentePAD.setVersion(tramiteVersion.getVersion());
	    	tramitePersistentePAD.setDescripcion( ( ( TraTramite ) tramiteVersion.getTramite().getTraduccion(datosSesion.getLocale().getLanguage()) ).getDescripcion() );
	    	tramitePersistentePAD.setNivelAutenticacion(datosSesion.getNivelAutenticacion());
	    	tramitePersistentePAD.setIdioma(this.datosSesion.getLocale().getLanguage());
	    	if (datosSesion.getNivelAutenticacion() != 'A'){
	    		tramitePersistentePAD.setUsuario(datosSesion.getCodigoUsuario());
	    		tramitePersistentePAD.setUsuarioFlujoTramitacion(datosSesion.getCodigoUsuario());
	    		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
	    			tramitePersistentePAD.setDelegado(datosSesion.getCodigoDelegado());
	    		}
	    	}
	    	
	    	// Almacenamos los parametros de inicio del trámite
	    	tramitePersistentePAD.setParametrosInicio(this.parametrosInicio);
	    	
	    	// Indica si es persistente (si no es circuito reducido)
	    	tramitePersistentePAD.setPersistente(isCircuitoReducido( tramiteVersion, this.datosSesion.getNivelAutenticacion())?"N":"S");
	    	
	    	// Fecha de caducidad
	    	tramitePersistentePAD.setFechaCaducidad(this.getFechaCaducidad());

	    	// Calculamos destinatario tramite por si se especifica procedimiento dinamicamente
	    	ProcedimientoDestinoTramite dt = calcularProcedimientoDestinoTramite();
	    	tramitePersistentePAD.setIdProcedimiento(dt.getProcedimiento());

	    	// Guardamos documentos
	    	HashMap docs = new HashMap();
	    	tramitePersistentePAD.setDocumentos(docs);
	    	// ------ Formularios
	    	for (Iterator it=formularios.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		DocumentoNivel docNivel = (DocumentoNivel) doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());
	    		DocumentoPersistentePAD docPad = new DocumentoPersistentePAD();

	    		// Generamos datos iniciales
	    		DatosFormulario datosIniciales = generarDatosInicialesFormulario(docNivel);

	    		// Guardamos en RDS
	    		DocumentoRDS docRds = new DocumentoRDS();
	    		docRds.setModelo(doc.getModelo());
	    		docRds.setVersion(docNivel.getVersion());
	    		docRds.setDatosFichero(datosIniciales.getBytes());
	    		docRds.setTitulo(((TraDocumento) doc.getTraduccion()).getDescripcion());
	    		docRds.setNombreFichero("formulario.xml");
	    		docRds.setExtensionFichero("xml");
	    		docRds.setUnidadAdministrativa(tramiteVersion.getUnidadAdministrativa().longValue());

	    		if (this.datosSesion.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
	    			docRds.setNif(this.datosSesion.getNifUsuario());
	    			docRds.setUsuarioSeycon(this.datosSesion.getCodigoUsuario());
	    		}
	    		docRds.setIdioma(this.datosSesion.getLocale().getLanguage());

	    		ReferenciaRDS refRds = rds.insertarDocumento(docRds);

	    		// Establecemos en PAD
	    		docPad.setIdentificador(doc.getIdentificador());
	    		docPad.setNumeroInstancia(1);
	    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
	    		docPad.setReferenciaRDS(refRds);
	    		docPad.setTipoDocumento(DocumentoPersistentePAD.TIPO_FORMULARIO);
	    		tramitePersistentePAD.getDocumentos().put(docPad.getIdentificador() + "-" + docPad.getNumeroInstancia(),docPad);

	    		// Cacheamos datos de formulario para evitar acceder al RDS
	    		this.datosFormularios.put(docPad.getIdentificador() + "-" + docPad.getNumeroInstancia(),datosIniciales);
	    	}
	    	// ------ Pagos
	    	for (Iterator it=pagos.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		DocumentoPersistentePAD docPad = new DocumentoPersistentePAD();

	    		// Guardamos en PAD
	    		docPad.setIdentificador(doc.getIdentificador());
	    		docPad.setNumeroInstancia(1);
	    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
	    		docPad.setTipoDocumento(DocumentoPersistentePAD.TIPO_PAGO);
	    		tramitePersistentePAD.getDocumentos().put(docPad.getIdentificador() + "-" + docPad.getNumeroInstancia(),docPad);

	    	}

	    	// ------ Anexos
	    	for (Iterator it=anexos.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		DocumentoPersistentePAD docPad = new DocumentoPersistentePAD();

	    		// Guardamos en PAD
	    		docPad.setIdentificador(doc.getIdentificador());
	    		docPad.setNumeroInstancia(1);
	    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
	    		docPad.setTipoDocumento(DocumentoPersistentePAD.TIPO_ANEXO);
	    		tramitePersistentePAD.getDocumentos().put(docPad.getIdentificador() + "-" + docPad.getNumeroInstancia(),docPad);
	    	}

	    	// Realizamos salvado en PAD
	    	actualizarPAD();

	    	// Guardamos usos documentos generados
	    	UsoRDS uso = new UsoRDS();
			uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
			uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
	    	for (Iterator it=tramitePersistentePAD.getDocumentos().keySet().iterator();it.hasNext();){
	    		String ls_key = (String) it.next();
	    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_key);
	    		if (docPAD.getRefRDS() != null){
	    			uso.setReferenciaRDS(docPAD.getRefRDS());
	    			rds.crearUso(uso);
	    		}
	    	}
    	}catch (Exception e){
    		// Damos el tramite como no inicializado
    		this.tramitePersistentePAD = null;
    		this.datosFormularios.clear();
    		throw (e);
    	}

    }

	/**
     * Comprueba si existe tramite en la PAD
     * @param idPersistencia
     * @throws ProcessorException
     */
    private void existeTramitePad(String idPersistencia) throws ProcessorException{
	    try{
	    	PadDelegate pad = DelegatePADUtil.getPadDelegate();

	    	String existe = pad.obtenerEstadoTramite(idPersistencia);

	    	// ----- Tramite no existe o pertenece a otro usuario
	    	if (existe.equals("N")){
	    		throw new ProcessorException("El trámite no existe",
	    				MensajeFront.MENSAJE_NOEXISTETRAMITE);
	    	}
	    	// ----- Tramite ya se ha terminado
	    	if (existe.equals("T")){
	    		throw new ProcessorException("El trámite ya ha terminado",
	    				MensajeFront.MENSAJE_TRAMITETERMINADO);
	    	}
	    	// ----- Tramite queda pendiente de confirmación
	    	if (existe.equals("C")){
	    		throw new ProcessorException("El trámite está pendiente de confirmarse",
	    				MensajeFront.MENSAJE_TRAMITEPENDIENTECONFIRMACION);
	    	}
	    }catch (ProcessorException pe){
			throw pe;
		}catch (Exception e){
			throw (new ProcessorException("Error al cargar el trámite persistente",MensajeFront.MENSAJE_ERRORDESCONOCIDO,e));
		}
    }

    /**
     * Carga trámite persistente desde la Pad
     * @param idPersistencia
     */
    private void cargarTramitePad(String idPersistencia) throws ProcessorException {
    	try{
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	PadDelegate pad = DelegatePADUtil.getPadDelegate();

	    	// Recuperamos trámite desde la Pad
	    	tramitePersistentePAD = pad.obtenerTramitePersistente(idPersistencia);
	    	if (tramitePersistentePAD == null){
	    		throw new ProcessorException("No se ha podido cargar tramite",
	    				MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    	}

	    	// Comprobamos que la sesión tenga el mismo nivel de autenticación
	    	if (tramitePersistentePAD.getNivelAutenticacion() != datosSesion.getNivelAutenticacion()){
	    		throw new ProcessorException("El trámite persistente tiene diferente nivel de autenticación que la sesión iniciada",
	    				MensajeFront.MENSAJE_DIFERENTENIVEL);
	    	}

	    	// Establecemos como parametros de inicio los originales del tramite
	    	this.parametrosInicio = tramitePersistentePAD.getParametrosInicio();

	    	// Comprobamos si es un tramite de subsanacion (parametros de inicio tramite subsanacion)
	    	if (tramitePersistentePAD.getParametrosInicio() != null &&
	    		tramitePersistentePAD.getParametrosInicio().get(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_ID) != null){
	    			this.subsanacion = true;
	    	}

	    	// Recuperamos datos de los formularios
	    	for (Iterator it=formularios.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		DocumentoNivel docNivel = (DocumentoNivel) doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());
	    		String ls_key = doc.getIdentificador() + "-1";
	    		DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_key);
	    		if (docPad == null){
	    			throw new ProcessorException("El trámite almacenado en persistencia no concuerda con su definición: faltan documentos",
	    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    		}
	    		if (docPad.getRefRDS() != null){
	    			// Leemos datos del RDS
	    			DocumentoRDS docRds = rds.consultarDocumento(docPad.getRefRDS());
	    			String xml = new String(docRds.getDatosFichero(),ConstantesXML.ENCODING) ;

	    			// Creamos objeto DatosFormulario
	    			DatosFormulario datosForm = new DatosFormulario(doc.getModelo(),docNivel.getVersion());
	    				//  - Establecemos datos
	    			datosForm.setBytes(docRds.getDatosFichero());
	    				//	- Establecemos plantilla
	    			if (StringUtils.isNotEmpty(docRds.getPlantilla()))
	    				datosForm.setPlantilla(docRds.getPlantilla());

	    			// Verificamos que los datos son equivalentes con los leídos
	    			if (!doc.getModelo().equals(docRds.getModelo())){
	    				debug("XML RDS - modelo \n" + docRds.getModelo());
	    				debug("XML GENERADO - modelo: \n" + doc.getModelo() );
	    				throw new ProcessorException("El documento almacenado no concuerda con el documento procesado: ha variado el modelo del documento",
		    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    			}
	    			if (docNivel.getVersion() != docRds.getVersion()){
	    				debug("XML RDS - version \n" + docRds.getVersion());
	    				debug("XML GENERADO - version: \n" + docNivel.getVersion() );
	    				throw new ProcessorException("El documento almacenado no concuerda con el documento procesado: ha variado la version del documento",
		    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    			}
	    			String xmlGen = datosForm.getString();
	    			if (xml != null && !xml.equals(xmlGen)){
	    				debug("XML RDS: \n" + xml);
	    				debug("XML GENERADO: \n" + xmlGen );
	    				throw new ProcessorException("El documento almacenado no concuerda con el documento procesado: variacion modelo/version/plantilla",
		    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    			}

	    			// Cacheamos datos formulario
	    			this.datosFormularios.put(ls_key,datosForm);

	    			// Cacheamos estado firma
	    			if (docNivel.getFirmar() == 'S'){
						if ( docRds.getFirmas() != null && docRds.getFirmas().length > 0 )
						{
							List firmas = new ArrayList();
							for (int i=0;i<docRds.getFirmas().length;i++){
								firmas.add(docRds.getFirmas()[i]);
							}
							this.firmaDocumentos.put(docRds.getReferenciaRDS().toString(),firmas);
						}
	    			}

	    		}
	    	}

	    	//  Recuperamos datos de anexos
	    	for (Iterator it=anexos.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		DocumentoNivel docNivel = (DocumentoNivel) doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());

	    		int numInst = tramitePersistentePAD.getMaximoNumeroInstanciaDocumento(doc.getIdentificador());

	    		for (int i=1;i<=numInst;i++){

		    		String ls_key = doc.getIdentificador() + "-" + i;

		    		DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_key);
		    		if (docPad == null){
		    			throw new ProcessorException("El trámite almacenado en persistencia no concuerda con su definición: faltan documentos",
		    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
		    		}

		    		if (docPad.getRefRDS() != null){
		    			// Cacheamos estado firma
		    			if (docNivel.getFirmar() == 'S'){
		    				DocumentoRDS docRds = rds.consultarDocumento(docPad.getRefRDS());
							if ( docRds.getFirmas() != null && docRds.getFirmas().length > 0 )
							{
								List firmas = new ArrayList();
								for (int x=0;x<docRds.getFirmas().length;x++){
									firmas.add(docRds.getFirmas()[x]);
								}
								this.firmaDocumentos.put(docPad.getRefRDS().toString(),firmas);
							}
		    			}

		    		}
	    		}
	    	}

	    	// Recuperamos datos pagos
	    	for (Iterator it=pagos.iterator();it.hasNext();){
	    		Documento doc = (Documento) it.next();
	    		String ls_key = doc.getIdentificador() + "-1";
	    		DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_key);
	    		if (docPad == null){
	    			throw new ProcessorException("El trámite almacenado en persistencia no concuerda con su definición: faltan documentos",
	    					MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    		}
	    		if (docPad.getRefRDS() != null){
	    			DocumentoRDS docRds = rds.consultarDocumento(docPad.getRefRDS());
	    			DatosPago datosPago = new DatosPago();
	    			datosPago.setBytes(docRds.getDatosFichero());
	    			this.datosPagos.put(ls_key,datosPago);
	    		}
	    	}

	    	// Actualizamos datos de delegacion
	    	if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
	    		tramitePersistentePAD.setDelegado(datosSesion.getCodigoDelegado());
	    	}else{
	    		tramitePersistentePAD.setDelegado(null);
	    	}

	    	// Realizamos salvado en PAD (para actualizar acceso)
	    	actualizarPAD();

    	}catch (ProcessorException pe){
    		// Damos el tramite como no inicializado
    		this.tramitePersistentePAD = null;
    		throw pe;
    	}catch (Exception e){
    		String infoDebug = "idPersistencia: " + idPersistencia;
            try {
                   if (this.tramitePersistentePAD != null && this.tramitePersistentePAD.getDocumentos() != null) {
                          infoDebug += " / Documentos:";
                          for (Iterator it = this.tramitePersistentePAD.getDocumentos().keySet().iterator(); it.hasNext();) {
                                 infoDebug += " " + it.next().toString(); 
                          }
                   }
            } catch (Exception e1){
                   // No hacemos nada
            }
            log.error("Error al cargar el trámite persistente: " + infoDebug);
            
            // Damos el tramite como no inicializado
            this.tramitePersistentePAD = null;
            throw (new ProcessorException("Error al cargar el trámite persistente", MensajeFront.MENSAJE_ERRORDESCONOCIDO,e));
    	}

    }

    // Establecemos estado tramite para pasarselo al front
    private void actualizarTramiteInfo() throws Exception{

    	// Comprobamos si el tramite ha sido inicializado
    	if (tramitePersistentePAD == null) return;

    	// Inicializamos
    	TramiteNivel tn = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion());
    	EspecTramiteNivel espTramite = (EspecTramiteNivel) tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel espNivel = (EspecTramiteNivel) tn.getEspecificaciones();


    	// Establecemos propiedades generales tramite (sólo hace falta la primera vez)
    	if (tramiteInfo == null){
    		tramiteInfo = new TramiteFront();
    		tramiteInfo.setFechaCreacion(tramitePersistentePAD.getFechaCreacion());
    		tramiteInfo.setIdProcedimiento(tramitePersistentePAD.getIdProcedimiento());
    		tramiteInfo.setEntidad(obtenerEntidadProcedimiento(tramitePersistentePAD.getIdProcedimiento()));
    		tramiteInfo.setModelo( this.tramiteVersion.getTramite().getIdentificador() );
    		tramiteInfo.setVersion( this.tramiteVersion.getVersion() );
    		tramiteInfo.setDatosSesion(this.datosSesion);

    		// Protegemos por si falta el literal del tramite en el idioma actual
    		TraTramite tratra = (TraTramite) this.tramiteVersion.getTramite().getTraduccion(datosSesion.getLocale().getLanguage());
    		if (tratra == null){
    			tratra = (TraTramite) this.tramiteVersion.getTramite().getTraduccion("es");
    		}
    		tramiteInfo.setDescripcion(tratra.getDescripcion());

    		tramiteInfo.setDebugEnabled(this.debugEnabled);


    		tramiteInfo.setRegistrar(tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO);
    		tramiteInfo.setConsultar(tramiteVersion.getDestino() ==  ConstantesSTR.DESTINO_CONSULTA);
    		tramiteInfo.setAsistente(tramiteVersion.getDestino() ==  ConstantesSTR.DESTINO_ASISTENTE);
    		tramiteInfo.setFirmar(tramiteVersion.getFirmar() == 'S');
    		tramiteInfo.setIdPersistencia(tramitePersistentePAD.getIdPersistencia());
    		tramiteInfo.setTipoTramitacion(this.tipoTramitacion);
    		tramiteInfo.setPasos(this.pasosTramitacion);

    		// Plazo presentacion
    		if (this.configuracionDinamica.isPlazoDinamico()){
    			tramiteInfo.setFechaInicioPlazo(this.configuracionDinamica.getPlazoInicio());
    			tramiteInfo.setFechaFinPlazo(this.configuracionDinamica.getPlazoFin());
    		}else{
    			tramiteInfo.setFechaInicioPlazo(tramiteVersion.getInicioPlazo());
    			tramiteInfo.setFechaFinPlazo(tramiteVersion.getFinPlazo());
    		}

    		// Comprobamos flujo de tramitación (sólo para niveles autenticados)
    		if (this.datosSesion.getNivelAutenticacion() != 'A'){
    			tramiteInfo.setFlujoTramitacion(espNivel.getFlujoTramitacion().equals("S"));
        		// Obtenemos datos persona para iniciador tramite (Este dato no puede ser nulo ya que indicaría esta persona no esta registrada)
        		tramiteInfo.setFlujoTramitacionDatosPersonaIniciador(obtenerDatosPADporUsuarioSeycon(this.tramitePersistentePAD.getUsuario()));
        		if (tramiteInfo.getFlujoTramitacionDatosPersonaIniciador() == null){
        			throw new Exception("El iniciador del trámite (usuario " + this.tramitePersistentePAD.getUsuario() + ") no esta registrado en el sistema");
        		}
    		}

    		// Establecemos dias hasta fecha de caducidad
    		int	days = DataUtil.distancia(	StringUtil.fechaACadena(new Date(),"dd/MM/yyyy"),
    										StringUtil.fechaACadena(tramitePersistentePAD.getFechaCaducidad(),"dd/MM/yyyy"),
    										"dd/MM/yyyy");
    		tramiteInfo.setDiasPersistencia(days);


    		TraEspecTramiteNivel traEspTramite= (TraEspecTramiteNivel) espTramite.getTraduccion();
        	TraEspecTramiteNivel traEspNivel=(TraEspecTramiteNivel) espNivel.getTraduccion();

    		if (traEspNivel != null && StringUtils.isNotEmpty(traEspNivel.getInstruccionesInicio()))
    			tramiteInfo.setInformacionInicio(traEspNivel.getInstruccionesInicio());
    		else{
    			if (traEspTramite != null && StringUtils.isNotEmpty(traEspTramite.getInstruccionesInicio()))
    				tramiteInfo.setInformacionInicio(traEspTramite.getInstruccionesInicio());
    		}

    		// Para las instrucciones de inicio permitimos tags especiales html
    		tramiteInfo.setInformacionInicio(convertHtmlTags(tramiteInfo.getInformacionInicio()));

    		if (traEspNivel != null && StringUtils.isNotEmpty(traEspNivel.getInstruccionesFin()))
    			tramiteInfo.setInstruccionesFin(traEspNivel.getInstruccionesFin());
    		else
    		{
    			if (traEspTramite != null && StringUtils.isNotEmpty(traEspTramite.getInstruccionesFin()))
    				tramiteInfo.setInstruccionesFin(traEspTramite.getInstruccionesFin());
    		}

    		if (traEspNivel != null && StringUtils.isNotEmpty(traEspNivel.getMensajeFechaLimiteEntregaPresencial()))
    			tramiteInfo.setMensajeFechaLimiteEntregaPresencial(traEspNivel.getMensajeFechaLimiteEntregaPresencial());
    		else
    		{
    			if (traEspTramite != null && StringUtils.isNotEmpty(traEspTramite.getMensajeFechaLimiteEntregaPresencial()))
    				tramiteInfo.setMensajeFechaLimiteEntregaPresencial(traEspTramite.getMensajeFechaLimiteEntregaPresencial());
    		}


    		if (traEspNivel != null &&  StringUtils.isNotEmpty(traEspNivel.getInstruccionesEntrega()))
    			tramiteInfo.setInstruccionesEntrega(traEspNivel.getInstruccionesEntrega());
    		else
    		{
    			if (traEspTramite != null &&  StringUtils.isNotEmpty(traEspTramite.getInstruccionesEntrega()))
    				tramiteInfo.setInstruccionesEntrega(traEspTramite.getInstruccionesEntrega());
    		}

    		// Evaluamos si puede haber documentos para compulsar y para descargar plantillas
    		for (Iterator it=this.anexos.iterator();it.hasNext();){
    			Documento doc = (Documento) it.next();
    			if (doc.getAnexoDescargarPlantilla() == 'S') {
    				tramiteInfo.setDescargarPlantillas(true);
    			}
    			if (doc.getAnexoCompulsarPreregistro() == 'S'){
    				DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());
    				switch (docNivel.getObligatorio()){
    					case DocumentoNivel.OBLIGATORIO:
    						tramiteInfo.setCompulsarDocumentos('S');
    						break;
    					case DocumentoNivel.DEPENDIENTE:
    					case DocumentoNivel.OPCIONAL:
    						if (tramiteInfo.getCompulsarDocumentos() == 'N')
    							tramiteInfo.setCompulsarDocumentos('D');
    						break;
    				}
    			}
    		}

    		// Establecemos informacion cuaderno de carga
    		tramiteInfo.setTagCuadernoCarga(tramiteVersion.getCuadernoCargaTag());
    		tramiteInfo.setFechaExportacion(tramiteVersion.getFechaExportacion());


    		// Comprobamos mensajes de plataforma
    		List mensajesPlataforma = DelegateUtil.getMensajePlataformaDelegate().listarMensajePlataformas();
    		for (Iterator it=mensajesPlataforma.iterator();it.hasNext();){
    			MensajePlataforma mp = (MensajePlataforma) it.next();
    			if (mp.getActivo() == 'S') {
    				if ( mp.getIdentificador().equals(ConstantesSTR.MENSAJEPLATAFORMA_TODOS) ||
    					(mp.getIdentificador().equals(ConstantesSTR.MENSAJEPLATAFORMA_ANONIMOS) && datosSesion.getNivelAutenticacion() == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO )||
    					(mp.getIdentificador().equals(ConstantesSTR.MENSAJEPLATAFORMA_AUTENTICADOS) && datosSesion.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO )||
    					(mp.getIdentificador().equals(ConstantesSTR.MENSAJEPLATAFORMA_PAGOS) && this.pagos.size()>0 )
    					)
    						if (mp.getTraduccion(datosSesion.getLocale().getLanguage()) != null)
    							tramiteInfo.getMensajesPlataforma().put(mp.getIdentificador(),
    										((TraMensajePlataforma) mp.getTraduccion(datosSesion.getLocale().getLanguage())).getDescripcion());
    			}
    		}

    		// Establecemos info de si el tramite permite notificacion telematica
    		if ( !ConstantesSTR.NOTIFICACIONTELEMATICA_SINESPECIFICAR.equals(espNivel.getHabilitarNotificacionTelematica())){
    			tramiteInfo.setHabilitarNotificacionTelematica(espNivel.getHabilitarNotificacionTelematica());
    			tramiteInfo.setPermiteSMS("S".equals(espNivel.getPermitirSMS()));
    		}else if ( !ConstantesSTR.NOTIFICACIONTELEMATICA_SINESPECIFICAR.equals(espTramite.getHabilitarNotificacionTelematica())){
    			tramiteInfo.setHabilitarNotificacionTelematica(espTramite.getHabilitarNotificacionTelematica());
    			tramiteInfo.setPermiteSMS("S".equals(espTramite.getPermitirSMS()));
    		}else{
    			tramiteInfo.setHabilitarNotificacionTelematica(ConstantesSTR.NOTIFICACIONTELEMATICA_NOPERMITIDA);
    		}
    		// En caso de que sea obligatoria la seleccion y no sean obligatorios los avisos la damos por hecha
    		if (tramiteInfo.getHabilitarNotificacionTelematica().equals(ConstantesSTR.NOTIFICACIONTELEMATICA_OBLIGATORIA) &&
    				!this.avisosObligatoriosNotif){
    			this.habilitarNotificacionTelematica = new Boolean(true);
    		}

    		// Indicamos si son obligatorias los avisos para las notificaciones
    		tramiteInfo.setObligatorioAvisosNotificaciones(this.avisosObligatoriosNotif);
    		
    		// Verificacion movil en paso registro
    		if ( !ConstantesSTR.VERIFICARMOVIL_SINESPECIFICAR.equals(espNivel.getVerificarMovil())){
    			tramiteInfo.setVerificarMovil(ConstantesSTR.VERIFICARMOVIL_HABILITADA.equals(espNivel.getVerificarMovil()));
    		}else { 
    			tramiteInfo.setVerificarMovil(ConstantesSTR.VERIFICARMOVIL_HABILITADA.equals(espTramite.getVerificarMovil()));
    		}

    	}

    	// Si el tipo de tramitación es dependiente habrá que calcular el estado actual de esta dependencia
    	// Establecemos en principio que sea telemático y vamos comprobando esa dependencia
    	if (tramiteInfo.getTipoTramitacion() == 'D') tramiteInfo.setTipoTramitacionDependiente('T');

    	// Establecemos paso actual
    	tramiteInfo.setPasoActual(this.pasoActual);

    	// Establecemos documentos
    	// ----  Establecemos formularios
    	tramiteInfo.getFormularios().clear();
    	for (Iterator it = formularios.iterator();it.hasNext();){
    		Documento doc = (Documento) it.next();
    		DocumentoFront docInfo = actualizarDocumentoInfo(doc,1,tramiteInfo);
    		tramiteInfo.getFormularios().add(docInfo);
    	}

    	// ----  Establecemos pagos
    	tramiteInfo.getPagos().clear();
    	for (Iterator it = pagos.iterator();it.hasNext();){
    		Documento doc = (Documento) it.next();
    		DocumentoFront docInfo = actualizarDocumentoInfo(doc,1,tramiteInfo);
    		tramiteInfo.getPagos().add(docInfo);

    		// Calculamos dependencia tramitación:
    		if (tramiteInfo.getTipoTramitacion() == 'D' && tramiteInfo.getTipoTramitacionDependiente() != 'P'){
    			if (docInfo.getEstado() == 'S'){
    				//--- Si el pago ha sido efectuado y es presencial, marcamos el tipo de tramitacion como presencial
    				if (docInfo.getPagoTipo() == 'P'){
    					tramiteInfo.setTipoTramitacionDependiente('P');
    				}
    			}else{
    				//--- Si el pago no ha sido efectuado marcamos el tipo de tramitacion como dependiente
	    			tramiteInfo.setTipoTramitacionDependiente('D');
    			}
    		}
    	}

    	// ----  Establecemos anexos
    	tramiteInfo.getAnexos().clear();
    	for (Iterator it = anexos.iterator();it.hasNext();){
    		Documento doc = (Documento) it.next();

    		if (doc.getGenerico() == 'S'){
    			int max = tramitePersistentePAD.getMaximoNumeroInstanciaDocumento(doc.getIdentificador());
    			for (int i=1;i<=max;i++){
    				// Actualizamos datos documento
    				DocumentoFront docInfo = actualizarDocumentoInfo(doc,i,tramiteInfo);
    				tramiteInfo.getAnexos().add(docInfo);
    				// Calculamos dependencia tipo tramitación según anexo
    				if (tramiteInfo.getTipoTramitacion() == 'D') {
        				tramiteInfo.setTipoTramitacionDependiente(calcularDependenciaTramitacionAnexo(docInfo));
        			}
    			}
    		}else{
    			DocumentoFront docInfo = actualizarDocumentoInfo(doc,1,tramiteInfo);
    			tramiteInfo.getAnexos().add(docInfo);

    			// Calculamos dependencia tipo tramitación según anexo
				if (tramiteInfo.getTipoTramitacion() == 'D') {
    				tramiteInfo.setTipoTramitacionDependiente(calcularDependenciaTramitacionAnexo(docInfo));
    			}

    		}
    	}

		// Establecemos si se trata de un trámite de circuito reducido.
    	// Para ello, debe estar configurado como reducido y además, cumplir las siguientes condiciones:
    	// nº de formularios = 1
    	// nº de anexos = 0
    	// nº de pagos = 0
    	// tipo de trámite = envio
    	//
    	tramiteInfo.setCircuitoReducido( isCircuitoReducido( tramiteVersion, this.datosSesion.getNivelAutenticacion() ) );

    	// Establecemos si se debe saltar a la url de fin tras enviar un trámite
    	tramiteInfo.setRedireccionFin(tramiteVersion.getRedireccionFin()== 'S');

    	// Establecesmos si se registra automaticamente
    	tramiteInfo.setRegistroAutomatico(tramiteVersion.getRegistroAutomatico() == 'S');

    	// Establece seleccion de notificacion telematica
    	if (tramiteInfo.getHabilitarNotificacionTelematica() != ConstantesSTR.NOTIFICACIONTELEMATICA_NOPERMITIDA) {
    		tramiteInfo.setSeleccionNotificacionTelematica(this.habilitarNotificacionTelematica);
    	}
    	
    	// Establece email / sms
    	tramiteInfo.setSeleccionEmailAviso(this.emailAviso);
	    tramiteInfo.setSeleccionSmsAviso(this.smsAviso);
	    
    	// Establece en caso de flujo de tramitación si esta en estado de pasar y a quien
    	// Si esta pendiente de pasar establece el campo flujoTramitacionNif
    	calcularFlujoTramitacion(tramiteInfo);

    	// En caso de delegacion calcula si:
    	//  - esta pendiente de presentar
    	//  - en caso de que haya documentos pendientes de firma se puede enviar a bandeja de firma
    	//
    	calcularDelegacion(tramiteInfo);
    	
    	// Indica si se ha verificado el movil
    	tramiteInfo.setVerificadoMovil(verificadoMovil);
    }

    /**
     * Comprueba si puede configurarse como circuito reducido:
     * 	- no firma en tramite
     * 	- un único formulario sin firma
     *  - sin anexos ni pagos
     *  - no configurado para notificacion telematica
     *
     * @param tramiteVersion
     * @param nivelAutenticacion
     * @return
     */
    private boolean isCircuitoReducido( TramiteVersion tramiteVersion, char nivelAutenticacion )
    {
    	TramiteNivel tn = tramiteVersion.getTramiteNivel(nivelAutenticacion);

    	return ('S' == tramiteVersion.getReducido())
		    	&& tramiteVersion.getDocumentos().size() == 1
		    	&& tramiteVersion.getFirmar() == 'N'
		    	&& !(
		    			(
		    					tn.getEspecificaciones().getHabilitarNotificacionTelematica().equals("S") ||
		    					tn.getEspecificaciones().getHabilitarNotificacionTelematica().equals("O")
		    			)
		    			||
		    			(
		    					tn.getEspecificaciones().getHabilitarNotificacionTelematica().equals("X") &&
		    					(
		    					 tramiteVersion.getEspecificaciones().getHabilitarNotificacionTelematica().equals("S") ||
		    					 tramiteVersion.getEspecificaciones().getHabilitarNotificacionTelematica().equals("O"))
		    					)
		    			)
		    	&& ( ( Documento ) tramiteVersion.getDocumentos().iterator().next() ).getTipo() == Documento.TIPO_FORMULARIO
		    	&& ( ( Documento ) tramiteVersion.getDocumentos().iterator().next() ).getDocumentoNivel( nivelAutenticacion ).getFirmar() == 'N' 
		    	&& (  !ConstantesSTR.VERIFICARMOVIL_HABILITADA.equals(tramiteVersion.getEspecificaciones().getVerificarMovil()) 
		    			&& !ConstantesSTR.VERIFICARMOVIL_HABILITADA.equals(tn.getEspecificaciones().getVerificarMovil())) ;
    }

    // En caso de flujo de tramitación establece si esta en estado de pasar y a quien
    private void calcularFlujoTramitacion(TramiteFront tramite) throws Exception{

    	tramite.setFlujoTramitacionNif("");

    	if (tramite.isFlujoTramitacion()) {
			//int numPasos = tramite.getPasos().size();
			boolean pendienteFlujo = false;
			int ultimoPasoCompletado=tramite.getPasos().size() - 1;
			for (int i = 0;i<tramite.getPasos().size();i++){
				PasoTramitacion pas = (PasoTramitacion) tramite.getPasos().get(i);
				if (pas.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE)){
					ultimoPasoCompletado = (i-1);
					break;
				}
				if (pas.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO)) pendienteFlujo = true;
			}

			// Si todavía no se ha enviado y hay algo pendiente de flujo
			if (ultimoPasoCompletado <= tramite.getPasoNoRetorno() && pendienteFlujo){
				PasoTramitacion paso = (PasoTramitacion) tramite.getPasos().get(ultimoPasoCompletado);
				PasoTramitacion pasoSiguiente = (PasoTramitacion) tramite.getPasos().get(ultimoPasoCompletado + 1);
				String nifFlujo = "";

				// Si hay paso anexar, se debe llegar hasta paso anexar. Sino vale con llegar hasta rellenar.
				if ( (paso.getTipoPaso() == PasoTramitacion.PASO_ANEXAR)
						||
					(paso.getTipoPaso() == PasoTramitacion.PASO_RELLENAR && pasoSiguiente.getTipoPaso() != PasoTramitacion.PASO_ANEXAR)
					){
					// Buscamos al siguiente a quien debemos pasar
					// ¿Hay formularios pendientes?
					for (int i=0;i<tramite.getFormularios().size();i++){
						DocumentoFront doc = (DocumentoFront) tramite.getFormularios().get(i);
						if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO &&
							 doc.getObligatorio() == DocumentoFront.OBLIGATORIO &&
							 !doc.getNifFlujo().equals(tramite.getDatosSesion().getNifUsuario())
							 ){
								nifFlujo =  doc.getNifFlujo();
								break;
						}
					}

					// Si no hay formularios pendientes, ¿Hay anexos pendientes?
					if (StringUtils.isEmpty(nifFlujo)){
						for (int i=0;i<tramite.getAnexos().size();i++){
							DocumentoFront doc = (DocumentoFront) tramite.getAnexos().get(i);
							if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO &&
								 doc.getObligatorio() == DocumentoFront.OBLIGATORIO &&
								 !doc.getNifFlujo().equals(tramite.getDatosSesion().getNifUsuario())
								 ){
									nifFlujo =  doc.getNifFlujo();
									break;
							}
						}
					}

				}

				// Se ha llegado hasta pagar  -> Permitimos el pase
				if (paso.getTipoPaso() == PasoTramitacion.PASO_PAGAR){
					// TODO PENDIENTE DE IMPLEMENTAR (ADECUAR TB FRONT)
				}

				// Se ha llegado hasta registrar -> Permitimos el pase
				if (paso.getTipoPaso() == PasoTramitacion.PASO_REGISTRAR){
					// Si esta pendiente de flujo, es que los demas anteriores estan completados
					if (paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO)){
						// Quién debe registrar es el que aparece como representante
						DatosDesglosadosInteresado datosRpte = this.calcularDatosRepresentante();
						String nifRpte = datosRpte.getNif();
						if (!tramite.getDatosSesion().getNifUsuario().equals(nifRpte)){
							nifFlujo = nifRpte;
						}
					}
				}

				// Si hay que pasarlo a algún Nif lo indicamos y calculamos los datos de la persona
				// (si los datos de la persona son nulos querra decir que no esta registrada en la pad y
				// por tanto se indicara al usuario que antes de remitir debera ser registrada)
				if (!StringUtils.isEmpty(nifFlujo)){
					tramite.setFlujoTramitacionNif(nifFlujo);
					tramite.setFlujoTramitacionDatosPersonaActual(obtenerDatosPADporNif(nifFlujo));
				}

			}
		}
    }


    //  En caso de delegacion establece si esta en estado de pasar a bandeja de firma
    private void calcularDelegacion(TramiteFront tramite) throws Exception{

    	tramite.setRemitirDelegacionFirma(false);
    	tramite.setRemitirDelegacionPresentacion(false);

    	// Delegacion solo para tramites autenticados
    	if (tramite.getDatosSesion().getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO){
    		return;
    	}

    	if (tramite.getDatosSesion().getPersonaPAD().isHabilitarDelegacion()) {
			//int numPasos = tramite.getPasos().size();
			boolean pendienteDelegacionFirma = false;
			int ultimoPasoCompletado=tramite.getPasos().size() - 1;

			// Buscamos ultimo paso completado y comprobamos si existe
			// algun paso pendiente de enviar a firma
			for (int i = 0;i<tramite.getPasos().size();i++){
				PasoTramitacion pas = (PasoTramitacion) tramite.getPasos().get(i);

				if (pas.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE)){
					ultimoPasoCompletado = (i-1);
					break;
				}

				if (pas.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA)){
					pendienteDelegacionFirma = true;
				}
			}

			// Si todavía no se ha enviado
			if (ultimoPasoCompletado > 0 && ultimoPasoCompletado <= tramite.getPasoNoRetorno()) {

				PasoTramitacion paso = (PasoTramitacion) tramite.getPasos().get(ultimoPasoCompletado);
				PasoTramitacion pasoSiguiente = (PasoTramitacion) tramite.getPasos().get(ultimoPasoCompletado + 1);

				// Si hay algo pendiente de enviar a firmar miramos que se deje llegar hasta paso anexar
				if (pendienteDelegacionFirma){
					if ( (paso.getTipoPaso() == PasoTramitacion.PASO_ANEXAR)
							||
						(paso.getTipoPaso() == PasoTramitacion.PASO_RELLENAR &&
						 pasoSiguiente.getTipoPaso() != PasoTramitacion.PASO_ANEXAR)
						){
							// Indicamos que se puede enviar a firmar
							tramite.setRemitirDelegacionFirma(true);
					}
				}else{
				// Si se esta pendiente de enviar indicamos que se puede marcar para presentacion
					if (paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION)) {
						// Indicamos que se puede remitir a presentar
						tramite.setRemitirDelegacionPresentacion(true);
					}
				}
			}
		}
    }


    // Calcula dependencia tramitación según anexo cuando el tipo de tramitación es dependiente
    private char calcularDependenciaTramitacionAnexo(DocumentoFront docInfo){
		// Calculamos dependencia tipo tramitación:
		// si el anexo es obligatorio y hay que entregarlo presencialmente, hay que compulsar o entregar fotocopia
    	// el tipo de tramitación será presencial
		if (
			(!docInfo.isAnexoPresentarTelematicamente() || docInfo.isAnexoCompulsar()) &&
			( docInfo.getObligatorio() == 'S' ||
			 (docInfo.getObligatorio() == 'N' && docInfo.getEstado() != DocumentoFront.ESTADO_NORELLENADO)
			 )
			){
				return 'P';
		} // Si el anexo es opcional y no esta rellenado o es dependiente, el tipo de tramitación será dependiente
    	else if (tramiteInfo.getTipoTramitacionDependiente() != 'P' &&
				  (!docInfo.isAnexoPresentarTelematicamente() || docInfo.isAnexoCompulsar() ) &&
				  ( docInfo.getObligatorio() == 'D' ) &&
				   (docInfo.getObligatorio() == 'N' && docInfo.getEstado() == DocumentoFront.ESTADO_NORELLENADO)
				  )
				  {
				return 'D';
		}

		// Si no se cambia se queda como está
		return tramiteInfo.getTipoTramitacionDependiente();
    }


    // Establece informacion necesaria de un documento para el front
    private DocumentoFront actualizarDocumentoInfo(Documento doc,int instancia,TramiteFront tramiteInfo) throws Exception{

    	String ls_id = doc.getIdentificador() + "-" + instancia;

		DocumentoFront docInfo = new DocumentoFront();
		DocumentoNivel docNivel = doc.getDocumentoNivel(datosSesion.getNivelAutenticacion());
		DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ls_id);

		docInfo.setEstado(docPad.getEstado());
		docInfo.setIdentificador(doc.getIdentificador());
		docInfo.setInstancia(instancia);
		docInfo.setModelo(doc.getModelo());
		docInfo.setVersion(docNivel.getVersion());
		docInfo.setDescripcion( ((TraDocumento) doc.getTraduccion()).getDescripcion());
		docInfo.setInformacion( ((TraDocumento) doc.getTraduccion()).getInformacion());

		docInfo.setFirmar(docNivel.getFirmar() == 'S');
		docInfo.setPrerregistro( doc.getFormularioPreregistro() == 'S' );
		docInfo.setFormularioJustificante( doc.getFormularioJustificante() == 'S' );
		docInfo.setFormularioAnexarJustificante( doc.getFormularioAnexarJustificante() == 'S' );
		docInfo.setFormularioGuardarSinTerminar(docNivel.getFormularioGuardarSinTerminar() == 'S' );

		// Evaluamos quien debe completar el documento
		String nifFlujo="",formulariosFlujo="";
		if (tramiteInfo.isFlujoTramitacion()){
				// Evaluamos script flujo
				nifFlujo=evaluarScriptNifFlujo(docNivel.getFlujoTramitacionScript(),tramiteInfo);
				// Obtenemos lista de formularios que intervienen en el flujo
				formulariosFlujo=obtenerFormulariosScript(docNivel.getFlujoTramitacionScript());
		}
		docInfo.setNifFlujo(nifFlujo);
		docInfo.setFormulariosScriptFlujo(formulariosFlujo);

		// Evaluamos quien debe firmar el documento y si esta firmado
		if (docInfo.isFirmar())
		{
			// Content type (solo para firma CAIB)
			docInfo.setContentType( StringUtils.isEmpty( docNivel.getContentType() ) ? "altres" : docNivel.getContentType() );

			// Por defecto indicamos que no se compruebe el firmante
			docInfo.setFirmante("");
			docInfo.setNombreFirmante("");

			// Evaluamos script firmante: si la entidad permite delegacion, permitimos varios nifs
			int numFirmantes = 1;
			String scriptFirmante = docNivel.getFirmante();
			if ( !StringUtils.isEmpty( scriptFirmante ) )
			{
				try
				{
					// Evalua script firmante
					String firmante = this.evaluarScript(scriptFirmante.getBytes( ConstantesXML.ENCODING ),null);
					String firmantesRes = "";
					String nomFirmante;
					String nomFirmantes = "";
					if (StringUtils.isNotEmpty(firmante)){
						String[] firmantes = firmante.split("#");

						// Controlamos duplicados
						Set listaFirmantes = new HashSet();
						for (int i=0;i<firmantes.length;i++){
							if (!listaFirmantes.contains(firmantes[i])){
								listaFirmantes.add(firmantes[i]);
							}
						}

						numFirmantes = listaFirmantes.size();
						if (numFirmantes > 1){
							// Firma multiple solo para delegacion (y delegacion solo para autenticado)
							if (this.datosSesion.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO
								|| !this.datosSesion.getPersonaPAD().isHabilitarDelegacion()){
								throw new Exception("Solo se permite firma multiple para delegacion");
							}
						}

						boolean primerFmte = true;
						for (Iterator it = listaFirmantes.iterator();it.hasNext();){
							String fmte = (String) it.next();
							if (!primerFmte) {
								firmantesRes += "#";
								nomFirmantes += "#";
							}else{
								primerFmte = false;
							}
							fmte = NifCif.normalizarDocumento(fmte);
							int validarNif = NifCif.validaDocumento(fmte);
							if (validarNif != NifCif.TIPO_DOCUMENTO_NIF && validarNif != NifCif.TIPO_DOCUMENTO_CIF && validarNif != NifCif.TIPO_DOCUMENTO_NIE && validarNif != NifCif.TIPO_DOCUMENTO_PASAPORTE ){
								throw new Exception("El valor '" + fmte + "' no es un NIF/CIF/NIE/Pasaporte valido");
							}
							PersonaPAD persona = obtenerDatosPADporNif(fmte);
							if (persona != null){
								nomFirmante = persona.getNombreCompleto();
							}else{
								nomFirmante = "";
							}

							firmantesRes += fmte;
							nomFirmantes += nomFirmante;
						}

						docInfo.setFirmante(firmantesRes);
						docInfo.setNombreFirmante(nomFirmantes);
					}

					//  Obtenemos lista de formularios que intervienen en el script de firma
					docInfo.setFormulariosScriptFirma(obtenerFormulariosScript(ScriptUtil.scriptToBytes(scriptFirmante)));
				}
				catch( Exception exc )
				{
					throw new ProcessorException( "Error evaluando script de firmante", MensajeFront.MENSAJE_EXCEPCIONSCRIPT, exc );
				}
			}

			// En caso de que mediante script no se haya indicado firmante y el acceso no sea anónimo
			// el firmante será el iniciador del trámite
			if (StringUtils.isEmpty(docInfo.getFirmante()) && CredentialUtil.NIVEL_AUTENTICACION_ANONIMO != tramiteInfo.getDatosSesion().getNivelAutenticacion()){
				docInfo.setFirmante( tramiteInfo.getFlujoTramitacionDatosPersonaIniciador().getNif());
			}

			// Evaluamos estado firma documentos y que firmas tienen representacion
			if (docInfo.getEstado() == DocumentoFront.ESTADO_CORRECTO){
				
				List firmas = (List) this.firmaDocumentos.get(docPad.getRefRDS().toString());
				
				docInfo.setFirmado(firmas != null);
				
				if (firmas != null) {
					 for (Iterator it=firmas.iterator();it.hasNext();){
			    		 FirmaIntf f = (FirmaIntf) it.next();
			    		 if (StringUtils.isNotBlank(f.getNifRepresentante())) {
			    			 docInfo.getRepresentantesFirmas().put(f.getNif(), f.getNombreApellidosRepresentante() + " (" + f.getNifRepresentante() + ")");
			    		 }		    		 
			    	 }
				}		
			}

			// Comprobamos si te tiene que firmar de forma delegada
			if (this.datosSesion.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO
					&& this.datosSesion.getPersonaPAD().isHabilitarDelegacion()) {
				boolean firmaDelegada = false;
				if (numFirmantes > 1){
					firmaDelegada = true;
				}else{
					if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(this.datosSesion.getPerfilAcceso())){
						// Accede como delegado
						//  - mostramos firma delegada si el delegado no es el que tiene que firmar
						if (!docInfo.getFirmante().equals(this.datosSesion.getNifDelegado())) {
							firmaDelegada = true;
						}
					}else{
						// Acceso como entidad
						// - si accede como entidad y la entidad no es el que tiene que firmar
						if (!docInfo.getFirmante().equals(this.datosSesion.getNifUsuario())){
							firmaDelegada = true;
						}
					}
				}
				docInfo.setFirmaDelegada(firmaDelegada);
			}

			// Indicamos si esta pendiente de que la firme un delegado
			docInfo.setPendienteFirmaDelegada(DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA.equals(docPad.getDelegacionEstado()));
			// Indicamos si la ha rechazado un delegado
			docInfo.setRechazadaFirmaDelegada(DocumentoPersistentePAD.ESTADO_RECHAZADO_DELEGACION_FIRMA.equals(docPad.getDelegacionEstado()));
						
		}

		if (docNivel.getTraduccion() != null)
			docInfo.setInformacion(((TraDocumentoNivel) docNivel.getTraduccion()).getInformacion());
		docInfo.setPad(doc.getIdPad() != null);

		// Ejecutar script para establecer obligatoriedad documento
		char oblig =  docNivel.getObligatorio();
		docInfo.setDependiente(oblig == 'D');
		if (docNivel.getObligatorio() == 'D' && docNivel.getObligatorioScript() != null && docNivel.getObligatorioScript().length > 0){
			oblig = this.evaluarScript(docNivel.getObligatorioScript(),null).charAt(0);;
			if ( oblig != 'S' && oblig != 'N' && oblig != 'D'){
				throw new ProcessorException("El script de obligatoriedad devuelve un estado incorrecto: " + oblig,MensajeFront.MENSAJE_EXCEPCIONSCRIPT);
			}
		}
		docInfo.setObligatorio(oblig);

		switch (doc.getTipo()){
			case Documento.TIPO_ANEXO:
				// --- Establecemos propiedades específicas anexos
				docInfo.setAnexoPresentarTelematicamente(doc.getAnexoPresentarTelematicamente() == 'S');
				docInfo.setAnexoExtensiones(doc.getAnexoExtensiones());
				docInfo.setAnexoCompulsar(doc.getAnexoCompulsarPreregistro() == 'S');
				docInfo.setAnexoFotocopia(doc.getAnexoFotocopia() == 'S');
				docInfo.setAnexoGenerico(doc.getGenerico() == 'S');
				if (docInfo.isAnexoGenerico()){
					docInfo.setAnexoGenericoDescripcion(docPad.getDescripcionGenerico());
				}
				docInfo.setAnexoGenericoMax(doc.getMaxGenericos());
				docInfo.setAnexoMostrarPlantilla(doc.getAnexoDescargarPlantilla() == 'S');
				docInfo.setAnexoPlantilla(doc.getAnexoUrlPlantilla());
				if (doc.getAnexoTamanyoMax() != null)
					docInfo.setAnexoTamanyo(doc.getAnexoTamanyoMax().intValue());
				docInfo.setAnexoFichero(docPad.getNombreFicheroAnexo());

				// ---- Para los anexos obligatorios que no son genéricos y no se presentan telemáticamente los damos como entregado
				if (!docInfo.isAnexoPresentarTelematicamente() && !docInfo.isAnexoGenerico() && docInfo.getObligatorio() == 'S') {
					docInfo.setEstado('S');
				}

				break;
			case Documento.TIPO_PAGO:
				// --- Establecemos propiedades específicas anexos: tipo de pago
				if (docPad.getEstado() != DocumentoPersistentePAD.ESTADO_NORELLENADO) {
					DatosPago datosPago = (DatosPago) this.datosPagos.get(doc.getIdentificador() + "-" + instancia);
	    			docInfo.setPagoTipo(datosPago.getTipoPago());
				}
				docInfo.setPagoMetodos(docNivel.getPagoMetodos());
		}

		return docInfo;
    }

    // Evalua si el estado de un paso puede darse como completado
    // suponiendo que los anteriores están completados
    private String evaluarEstadoPaso(int numpaso) throws Exception{
    	// Actualizamos información trámite para actualizar obligatoriedad documentos
    	this.actualizarTramiteInfo();

    	// Evaluamos paso (Completado / Pendiente / Pendiente Flujo / Pendiente delegacion )
    	String completado=PasoTramitacion.ESTADO_PENDIENTE;
    	boolean pendienteFirmaDelegacion = false;
    	PasoTramitacion paso = (PasoTramitacion) pasosTramitacion.get(numpaso);
    	switch (paso.getTipoPaso()){
    		case PasoTramitacion.PASO_PASOS:
    		case PasoTramitacion.PASO_DEBESABER:
    		case PasoTramitacion.PASO_FINALIZAR:
    			// En estos paso simplemente llegar a él se considera completado
    			completado = PasoTramitacion.ESTADO_COMPLETADO;
    			break;
    		case PasoTramitacion.PASO_RELLENAR:
    			// Sólo se puede dar por completado si están rellenados correctamente:
    			//	- los formularios obligatorios
    			//  - los formularios opcionales rellenados
    			// Además todos los documentos que deban ir firmados deberán estarlo
    			completado = PasoTramitacion.ESTADO_COMPLETADO;
    			for (Iterator it=this.tramiteInfo.getFormularios().iterator();it.hasNext();){
    				DocumentoFront doc = (DocumentoFront) it.next();

    				// Obligatorios deben estar completados
    				if (doc.getObligatorio() == DocumentoFront.OBLIGATORIO &&
    					doc.getEstado() != DocumentoFront.ESTADO_CORRECTO ){
    						// El documento debe rellenarlo el usuario actual
    						if (!tramiteInfo.isFlujoTramitacion() || doc.getNifFlujo().equals(tramiteInfo.getDatosSesion().getNifUsuario())){
    							completado = PasoTramitacion.ESTADO_PENDIENTE;
    							break;
    						}else{
    						// El documento debe rellenarlo otro usuario (comprobamos si el usuario actual ya ha acabado)
    							if (!completado.equals(PasoTramitacion.ESTADO_PENDIENTE)) {
    								completado = PasoTramitacion.ESTADO_PENDIENTE_FLUJO;
    								continue; // Seguimos buscando por si existen docs a ser rellenados por usuario actual
    							}
    						}
    				}

    				// Opcionales empezados deben estar terminados correctamente
    				// (No controlamos quien los debe rellenar ya que si se han empezado es que tenía que hacerlos)
    				if (doc.getObligatorio() == DocumentoFront.OPCIONAL &&
        				doc.getEstado() == DocumentoFront.ESTADO_INCORRECTO){
    						completado = PasoTramitacion.ESTADO_PENDIENTE;
    						break;
        			}

    				// Obligatorios y opcionales correctos que tengan que firmarse deben estar firmados
    				if (doc.getObligatorio() != DocumentoFront.DEPENDIENTE &&
    					doc.getEstado() == DocumentoFront.ESTADO_CORRECTO &&
	    					doc.isFirmar() && !doc.isFirmado()){
			    					if (doc.isPendienteFirmaDelegada()){
										pendienteFirmaDelegacion = true;
									}else{
										completado = PasoTramitacion.ESTADO_PENDIENTE;
										break;
									}
            		}
    			}
    			break;
    		case PasoTramitacion.PASO_PAGAR:
    			// Sólo se puede dar por completado si están los pagos obligatorios se han efectuado correctamente
    			// y no hay iniciados
    			completado = PasoTramitacion.ESTADO_COMPLETADO;

    			for (Iterator it=this.tramiteInfo.getPagos().iterator();it.hasNext();){
    				DocumentoFront doc = (DocumentoFront) it.next();

    				// Obligatorios deben estar completados
    				if (doc.getObligatorio() == DocumentoFront.OBLIGATORIO &&
    					doc.getEstado() != DocumentoFront.ESTADO_CORRECTO ){

    					// El documento debe rellenarlo el usuario actual
						if (!tramiteInfo.isFlujoTramitacion() || doc.getNifFlujo().equals(tramiteInfo.getDatosSesion().getNifUsuario())){
							completado = PasoTramitacion.ESTADO_PENDIENTE;
							break;
						}else{
						// El documento debe rellenarlo otro usuario (comprobamos si el usuario actual ya ha acabado)
							if (!completado.equals(PasoTramitacion.ESTADO_PENDIENTE)){
								completado = PasoTramitacion.ESTADO_PENDIENTE_FLUJO;
								continue; // Seguimos buscando por si existen docs a ser rellenados por usuario actual
							}
						}
    				}

    				// Opcionales empezados deben estar terminados correctamente
    				// (No controlamos quien los debe rellenar ya que si se han empezado es que tenía que hacerlos)
    				if (doc.getObligatorio() == DocumentoFront.OPCIONAL &&
        				doc.getEstado() == DocumentoFront.ESTADO_INCORRECTO){
    						completado = PasoTramitacion.ESTADO_PENDIENTE;
    						break;
        			}
    			}
    			break;
    		case PasoTramitacion.PASO_ANEXAR:
    			// Sólo se puede dar por completado si se han anexado los documentos obligatorios
    			// y los documentos que se han anexado y tengan que ir firmados se hayan firmado
    			completado = PasoTramitacion.ESTADO_COMPLETADO;
    			for (Iterator it=this.tramiteInfo.getAnexos().iterator();it.hasNext();){

    				DocumentoFront doc = (DocumentoFront) it.next();

    				// Obligatorios deben estar completados
    				if (doc.getObligatorio() == DocumentoFront.OBLIGATORIO &&
    					doc.getEstado() != DocumentoFront.ESTADO_CORRECTO ){
    					// El documento debe rellenarlo el usuario actual
						if (  (!tramiteInfo.isFlujoTramitacion()) ||
							  (tramiteInfo.isFlujoTramitacion() &&	doc.getNifFlujo().equals(tramiteInfo.getDatosSesion().getNifUsuario()))
							){
							completado = PasoTramitacion.ESTADO_PENDIENTE;
							break;
						}else{
						// El documento debe rellenarlo otro usuario (comprobamos si el usuario actual ya ha acabado)
							if (!completado.equals(PasoTramitacion.ESTADO_PENDIENTE)){
								completado = PasoTramitacion.ESTADO_PENDIENTE_FLUJO;
								continue; // Seguimos buscando por si existen docs a ser rellenados por usuario actual
							}
						}
    				}

    				// Opcionales empezados deben estar terminados correctamente
    				// (No controlamos quien los debe rellenar ya que si se han empezado es que tenía que hacerlos)
    				if (doc.getObligatorio() == DocumentoFront.OPCIONAL &&
        				doc.getEstado() == DocumentoFront.ESTADO_INCORRECTO){
    						completado = PasoTramitacion.ESTADO_PENDIENTE;
    						break;
        			}

    				// Obligatorios y opcionales correctos que tengan que firmarse deben estar firmados
    				// (o bien en caso de delegacion estar establecidos para que se tengan que firmar)
    				if (doc.getObligatorio() != DocumentoFront.DEPENDIENTE &&
    					doc.getEstado() == DocumentoFront.ESTADO_CORRECTO &&
    					doc.isFirmar() && !doc.isFirmado()){
    							if (doc.isPendienteFirmaDelegada()){
    								pendienteFirmaDelegacion = true;
    							}else{
    								completado = PasoTramitacion.ESTADO_PENDIENTE;
    								break;
    							}
            		}

    				// Para documento genéricos obligatorio comprobamos que al menos exista uno
    				// ( Ya se comprueba en la primera comprobación de obligatorios)
        			if (doc.getObligatorio() == DocumentoFront.OBLIGATORIO && doc.isAnexoGenerico()){
        				if (this.tramitePersistentePAD.getNumeroInstanciasDocumentoEstado(doc.getIdentificador(),DocumentoPersistentePAD.ESTADO_CORRECTO) == 0) {
        					completado = PasoTramitacion.ESTADO_PENDIENTE;
        					break;
        				}
        			}

    			}
    			break;
    		case PasoTramitacion.PASO_REGISTRAR:
    			// Este paso se marcará como correcto en el paso de firma/envío
    			if (PasoTramitacion.ESTADO_COMPLETADO.equals(paso.getCompletado())){
    				completado = PasoTramitacion.ESTADO_COMPLETADO;
    			}else{

    				// Si esta activado el flujo de tramitacion
    				if (tramiteInfo.isFlujoTramitacion()){
    					DatosDesglosadosInteresado datosRpte = this.calcularDatosRepresentante();
    					if (tramiteInfo.getDatosSesion().getNifUsuario().equals(datosRpte.getNif())){
        					completado = PasoTramitacion.ESTADO_PENDIENTE;
        				}else{
       						completado = PasoTramitacion.ESTADO_PENDIENTE_FLUJO;
        				}
    				// Si esta en modo delegado comprobamos si el usuario tiene permiso para presentar el tramite
    				// si no tiene permisos debera abandonar el tramite
    				}else if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(tramiteInfo.getDatosSesion().getPerfilAcceso())){
    					if (tramiteInfo.getDatosSesion().getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
    						completado = PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION;
    					}
    				}else{
    					// Si no hay flujo ni delegacion entonces esta en estado pendiente
    					completado = PasoTramitacion.ESTADO_PENDIENTE;
    				}

    			}
    			break;
    	}


		// Si se da por completado este paso, y esta pendiente de firma por delegados
    	// indicamos que el paso esta pendiente de delegacion
		if (completado.equals(PasoTramitacion.ESTADO_COMPLETADO) && pendienteFirmaDelegacion ){
			completado = PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_FIRMA;
		}

    	return completado;
    }


    // Evalua si un paso requiere parámetros específicos. Esta función se llama desde irAPaso.
    private HashMap evaluarParametrosPaso(int numpaso) throws Exception{
    	HashMap param = new HashMap();

    	// Para paso de registrar generamos asiento
    	PasoTramitacion paso = (PasoTramitacion) pasosTramitacion.get(numpaso);
    	switch (paso.getTipoPaso()){
    		case PasoTramitacion.PASO_REGISTRAR:
    			// Limpiamos pagos no finalizados
    			// CASO NO POSIBLE, NO SE PUEDE  NO SE PUEDE PASAR DEL PASO DE PAGOS
    			// SI HAY PAGOS CON ESTADO ES INCORRECTO
    			//eliminarPagosNoFinalizados();
    			
    			boolean pendienteFlujo = paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_FLUJO);
    			boolean pendienteDelegacion = paso.getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION);
    			boolean pendienteSeleccionarNotif = (!tramiteInfo.getHabilitarNotificacionTelematica().equals(ConstantesSTR.NOTIFICACIONTELEMATICA_NOPERMITIDA) && tramiteInfo.getSeleccionNotificacionTelematica() == null);
    			boolean pendienteEnvioSmsVerificacion = tramiteInfo.isVerificarMovil() && StringUtils.isBlank(codigoSmsVerificarMovil);

    			// No generar asiento si:
    			//		- esta pendiente de flujo
    			//	    - esta pendiente de seleccionar notificacion telematica o selecciona avisos
    			//		- se esta tramitando en forma delegada y el usuario no tiene permiso para presentar el tramite


    			if (  !(pendienteDelegacion || pendienteFlujo || pendienteSeleccionarNotif) ) {
    						// Calculamos destinatario tramite (por si se especifica dinamicamente)
		    				DestinatarioTramite dt = this.calcularDestinatarioTramite();
							// Generamos asiento (le pasamos destinatario tramite por si se especifica dinamicamente)
		    				AsientoCompleto asiento = generaAsientoRegistral(dt);
			    			// En caso de que el destinatario se haya especificado dinamicamente
		    				// cambiamos de UA todos los docs en el RDS
		    				if (dt.isCalculado()) cambiarUADocs(dt);
		    				// Establecemos el asiento como parametro del paso
		    				param.put("asiento",asiento);		    				
    			}
    			
    			// Calculamos email/movil contacto por defecto (script datos contacto o info zona personal)
    		    // 	- si esta pendiente de seleccionar la notificacion
    		    //  - si hay que verificar movil
    		    if ( pendienteSeleccionarNotif || pendienteEnvioSmsVerificacion) {
    		    	this.emailAviso = calcularEmailAvisoDefecto();
    		    	this.smsAviso = calcularSmsAvisoDefecto();	
    		    }			    			
    		    			
    		    // Si hay que verificar movil enviamos SMS
    		    if (pendienteEnvioSmsVerificacion) {
    		    	codigoSmsVerificarMovil = enviarCodigoSmsVerificarMovil(this.smsAviso);    				
    		    }
    		    			
    		    param.put("emailAvisoDefault", this.emailAviso);
    			param.put("smsAvisoDefault", this.smsAviso);

    			break;
    		case PasoTramitacion.PASO_FINALIZAR :
    			// Establecemos resultado registro
    			if (resultadoRegistro.getTipo() == ResultadoRegistrar.CONSULTA){
    				param.put("documentosConsulta",resultadoRegistro.getDocumentosConsultaFront());
    			}else{
    				param.put("resultado",resultadoRegistro.getJustificanteRegistro());
    			}
    			break;
    	}
    	return param;
    }
    
    /**
     * Envia código de verificación al móvil.
     * @param movil numero movil
     * @return
     */
    private String enviarCodigoSmsVerificarMovil(String movil) throws Exception {
    	LoginContext lc = null;		
		try{					
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String caducidad = props.getProperty("envio.verificarMovil.minutosCaducidad");
			int minutosCaducidad = 0;
			if (StringUtils.isNotBlank(caducidad)) {
				minutosCaducidad = Integer.parseInt(caducidad);
			}
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			String codigo = generateCodigoSms();
	    	DelegatePADUtil.getPadDelegate().enviarSmsVerificarMovil(this.tramitePersistentePAD.getIdPersistencia(), this.tramitePersistentePAD.getIdProcedimiento(),
	    				movil, codigo, this.tramitePersistentePAD.getIdioma(), minutosCaducidad);    			
			return codigo;
			
		}finally{				
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}			    
	}

    /**
     * Calcula email por defecto para los avisos. Si esta rellenado el script de avisos lo ejecutara y si no
     * devolvera el email del usuario que esta intentando registrar.
     * @return Email por defecto
     * @throws ProcessorException
     */
    private String calcularEmailAvisoDefecto() throws ProcessorException {
		String emailDefecto="";
    	byte[] scriptEmail = null;

    	EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();

    	if (especNivel.getAvisoEmail() != null && especNivel.getAvisoEmail().length > 0){
    		scriptEmail = especNivel.getAvisoEmail();
    	}else {
    		scriptEmail = especVersion.getAvisoEmail();
    	}

    	if (scriptEmail != null && scriptEmail.length > 0 ){
    		emailDefecto = this.evaluarScript(scriptEmail,null);
    	}

    	if (StringUtils.isBlank(emailDefecto) && datosSesion.getNivelAutenticacion() != TramiteNivel.AUTENTICACION_ANONIMO) {
    		emailDefecto = tramiteInfo.getFlujoTramitacionDatosPersonaIniciador().getEmail();
    	}

    	if (emailDefecto == null) {
    		emailDefecto = "";
    	}

    	return emailDefecto;
	}

    /**
     * Calcula sms por defecto para los avisos. Si esta rellenado el script de avisos lo ejecutara y si no
     * devolvera el sms del usuario que esta intentando registrar.
     * @return sms por defecto
     * @throws ProcessorException
     */
    private String calcularSmsAvisoDefecto() throws ProcessorException {
		String smsDefecto="";
    	byte[] scriptSms = null;

    	EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();


    	if (especNivel.getAvisoSMS() != null && especNivel.getAvisoSMS().length > 0 ){
    		scriptSms = especNivel.getAvisoSMS();
        }else {
        	scriptSms = especVersion.getAvisoSMS();
        }

    	if (scriptSms != null && scriptSms.length > 0 ){
    		smsDefecto = this.evaluarScript(scriptSms,null);
    	}

    	if (StringUtils.isBlank(smsDefecto) && datosSesion.getNivelAutenticacion() != TramiteNivel.AUTENTICACION_ANONIMO) {
    		smsDefecto = tramiteInfo.getFlujoTramitacionDatosPersonaIniciador().getTelefonoMovil();
    	}

    	if (smsDefecto == null) {
    		smsDefecto = "";
    	}

    	return smsDefecto;
	}

	/**
     * Cambia de UA los documentos del tramite en caso de que se haya calculado
     * dinamicamente el destinatario
     * @param dt Destinatario tramite
     * @throws Exception
     */
    private void cambiarUADocs(DestinatarioTramite dt) throws Exception {
		try{
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	String key;
	    	DocumentoPersistentePAD doc;
	    	Long codUA = Long.parseLong(dt.getUnidadAdministrativa());
	    	for (Iterator it = tramitePersistentePAD.getDocumentos().keySet().iterator();it.hasNext();){
	    		key = (String) it.next();
	    		doc = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(key);
	    		if (doc.getRefRDS()!=null){
	    			rds.cambiarUnidadAdministrativa(doc.getRefRDS(),codUA);
	    		}
	    	}
		}catch (Exception ex){
			throw new Exception("Excepcion actualizando unidad administrativa de los documentos",ex);
		}

	}

	// Establece respuesta
    private RespuestaFront generarRespuestaFront(MensajeFront mensaje,HashMap parametros){
    	try{
	    	// Actualizamos estado tramite
	    	actualizarTramiteInfo();

	    	// Generamos respuesta
	    	RespuestaFront resp = new RespuestaFront();
	    	resp.setInformacionTramite(this.tramiteInfo);
	    	resp.setMensaje(mensaje);
	    	resp.setParametros(parametros);

	    	return resp;
    	}catch (Exception e){
    		log.error(this.mensajeLog("Error generando respuesta al actualizar TramiteInfo"),e);
    		RespuestaFront resp = new RespuestaFront();
	    	resp.setInformacionTramite(this.tramiteInfo);
	    	MensajeFront mens = new MensajeFront();
	    	mens.setTipo(MensajeFront.TIPO_ERROR);
	    	mens.setMensaje(this.traducirMensaje(MensajeFront.MENSAJE_ERRORDESCONOCIDO));
	    	mens.setMensajeExcepcion("Error generando respuesta al actualizar TramiteInfo");
	    	resp.setMensaje(mens);
	    	return resp;
    	}
    }

    // Genera asiento registral y datos propios y los almacena en el RDS y la PAD
    private AsientoCompleto generaAsientoRegistral(DestinatarioTramite dt) throws Exception{

    	// Generamos datos propios almacenandolos en el RDS
    	String xmlDatosPropios = generarDatosPropios(dt);

    	// Generamos asiento a partir información trámite y lo almacenamos en el RDS
    	String xmlAsiento =  generarAsiento(dt);

    	// Actualizamos PAD para que recoger los documentos generados
    	actualizarPAD();

    	// Devolvemos asiento y datos propios
    	AsientoCompleto asientoCompleto = new AsientoCompleto();
    	asientoCompleto.setAsiento(xmlAsiento);
    	asientoCompleto.setDatosPropios(xmlDatosPropios);
    	return asientoCompleto;
    }

    // Genera XML de asiento y lo almacena en RDS
    private String generarAsiento(DestinatarioTramite dt) throws Exception{

    	// Calculamos datos representante y representado
    	DatosDesglosadosInteresado datosRpte = this.calcularDatosRepresentante();
    	DatosDesglosadosInteresado datosRpdo = this.calcularDatosRepresentado();

    	// Generamos asiento (si hay autenticación, comprueba que quién envia es el usuario autenticado en la sesión)
    	String xmlAsiento = GeneradorAsiento.generarAsientoRegistral(tramiteInfo,tramiteVersion,tramitePersistentePAD, datosRpte, datosRpdo, dt, this.debugEnabled);

    	// Almacenamos asiento en el RDS y creamos documento en PAD
    	byte [] datosAsiento = GeneradorAsiento.XMLtoBytes(xmlAsiento);

    	// Comprobamos si existe en la PAD, si no lo creamos
    	String id = ConstantesAsientoXML.IDENTIFICADOR_ASIENTO;
    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(id + "-1");
    	if (docPad == null){
    		docPad = new DocumentoPersistentePAD();
    		docPad.setIdentificador(id);
    		docPad.setNumeroInstancia(1);
    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
    		tramitePersistentePAD.getDocumentos().put(id + "-1",docPad);
    	}

    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    	DocumentoRDS docRds;
    	// Si existe actualizamos documento en RDS
    	if (docPad.getRefRDS() != null){
    		rds.actualizarFichero(docPad.getRefRDS(),datosAsiento);
    	}else{
    		// Si no existe creamos documento y uso en RDS
    		docRds = new DocumentoRDS();
    		docRds.setModelo(ConstantesRDS.MODELO_ASIENTO_REGISTRAL);
    		docRds.setVersion(ConstantesRDS.VERSION_ASIENTO);
    		docRds.setDatosFichero(datosAsiento);
    		docRds.setTitulo("Asiento registral");
    		docRds.setNombreFichero("Asiento.xml");
    		docRds.setExtensionFichero("xml");
    		docRds.setUnidadAdministrativa(tramiteVersion.getUnidadAdministrativa().longValue());
    		if (datosSesion.getNivelAutenticacion() != 'A'){
    			docRds.setNif(datosSesion.getNifUsuario());
    			docRds.setUsuarioSeycon(datosSesion.getCodigoUsuario());
    		}
    		docRds.setIdioma(this.datosSesion.getLocale().getLanguage());
    		ReferenciaRDS ref = rds.insertarDocumento(docRds);
    		docPad.setReferenciaRDS(ref);
    		// Creamos uso
    		UsoRDS uso = new UsoRDS();
    		uso.setReferenciaRDS(ref);
    		uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
    		uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
    		rds.crearUso(uso);
    	}

    	// Devolvemos XML de datos propios
    	return xmlAsiento;


    }


    // Genera XML de datos propios (instrucciones + datos solicitud) y lo almacena en la PAD y RDS
    private String generarDatosPropios(DestinatarioTramite dt) throws Exception{

    	// Generamos XML Datos Propios
    	PluginFormularios plgForms = new PluginFormularios(true);
		plgForms.setDatosFormularios(this.datosFormularios);
		plgForms.setEstadoPersistencia(this.tramitePersistentePAD);
		PluginPagos plgPagos = new PluginPagos();
		plgPagos.setDatosPagos(this.datosPagos);
		plgPagos.setEstadoPersistencia(this.tramitePersistentePAD);

		// Comprobamos si es un tramite de subsanacion para recoger en los datos propios el expe asociado
		String expeId=null;
		Long expeUA=null;
		if (this.subsanacion){
			expeId = (String) this.parametrosInicio.get(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_ID);
			if (StringUtils.isEmpty(expeId)){
				throw new Exception("El tramite es de subsanacion pero no esta informado el expeId");
			}
			if (this.parametrosInicio.get(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_UA) == null){
				throw new Exception("El tramite es de subsanacion pero no esta informado la expeUA");
			}
			expeUA = new Long((String) this.parametrosInicio.get(ConstantesSTR.SUBSANACION_PARAMETER_EXPEDIENTE_UA));
		}

    	String strDatosPropios = GeneradorAsiento.generarDatosPropios(tramiteInfo,tramiteVersion,tramitePersistentePAD,plgForms,plgPagos,expeId , expeUA, dt, this.debugEnabled);
    	byte [] datosPropios = GeneradorAsiento.XMLtoBytes(strDatosPropios);

    	// Comprobamos si existe en la PAD, si no lo creamos
    	String id = ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS;
    	DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(id + "-1");
    	if (docPad == null){
    		docPad = new DocumentoPersistentePAD();
    		docPad.setIdentificador(id);
    		docPad.setNumeroInstancia(1);
    		docPad.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
    		tramitePersistentePAD.getDocumentos().put(id + "-1",docPad);
    	}


    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    	DocumentoRDS docRds;
    	// Si existe actualizamos documento en RDS
    	if (docPad.getRefRDS() != null){
    		rds.actualizarFichero(docPad.getRefRDS(),datosPropios);
    	}else{
    	// Si no existe creamos documento y uso en RDS
    		docRds = new DocumentoRDS();
    		docRds.setModelo(ConstantesRDS.MODELO_DATOS_PROPIOS);
    		docRds.setVersion(ConstantesRDS.VERSION_DATOS_PROPIOS);
    		docRds.setDatosFichero(datosPropios);
    		docRds.setTitulo("Datos propios trámite");
    		docRds.setNombreFichero("DatosPropios.xml");
    		docRds.setExtensionFichero("xml");
    		docRds.setUnidadAdministrativa( tramiteVersion.getUnidadAdministrativa().longValue());
    		if (datosSesion.getNivelAutenticacion() != 'A'){
    			docRds.setNif(datosSesion.getNifUsuario());
    			docRds.setUsuarioSeycon(datosSesion.getCodigoUsuario());
    		}
    		docRds.setIdioma(this.datosSesion.getLocale().getLanguage());
    		ReferenciaRDS ref = rds.insertarDocumento(docRds);
    		docPad.setReferenciaRDS(ref);
    		// Creamos uso
    		UsoRDS uso = new UsoRDS();
    		uso.setReferenciaRDS(ref);
    		uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
    		uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
    		rds.crearUso(uso);
    	}

    	// Devolvemos XML de datos propios
    	return strDatosPropios;

    }


    // Comprueba que el asiento es correcto:
    //		- comprueba que es el que debe ser
    //		- en caso de estar firmado añade firma al asiento
    //
    private AsientoCompleto comprobarAsiento(String asiento,FirmaIntf firma) throws ProcessorException{

    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    	AsientoCompleto asientoOriginal;

    	// Recuperamos asiento original
    	try{
			// Recuperamos xml datos propios del RDS
    		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS+"-1");
    		DocumentoRDS docRDS = rds.consultarDocumento(docPAD.getRefRDS());
    		String xmlDatosPropios = new String(docRDS.getDatosFichero(),ConstantesXML.ENCODING);
			// Recuperamos xml asiento del RDS
    		docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ConstantesAsientoXML.IDENTIFICADOR_ASIENTO+"-1");
    		docRDS = rds.consultarDocumento(docPAD.getRefRDS());
    		String xmlAsiento = new String(docRDS.getDatosFichero(),ConstantesXML.ENCODING);

    		asientoOriginal = new AsientoCompleto();
    		asientoOriginal.setAsiento(xmlAsiento);
    		asientoOriginal.setDatosPropios(xmlDatosPropios);

	    	// Comprobamos que el asiento recibido es el mismo que el original
			if (!asiento.equals(asientoOriginal.getAsiento())){
				throw new ProcessorException("El asiento no coincide",MensajeFront.MENSAJE_ASIENTOINCORRECTO);
			}

			// Añadimos firma del asiento al RDS
			if ( firma != null )
			{
				rds.asociarFirmaDocumento(docPAD.getRefRDS(),firma);
			}

			// Devolvemos asiento y datos propios
			return asientoOriginal;

    	}catch(Exception ex){
    		throw new ProcessorException("No se puede comprobar el asiento",MensajeFront.MENSAJE_ASIENTOINCORRECTO,ex);
    	}
    }

    // Realiza proceso de registro / preregistro
    // Recupera información del trámite en persistencia y realiza proceso registro
    private ResultadoRegistrar procesoRegistrar(String asiento,FirmaIntf firma) throws Exception{

    	// Comprobamos asiento:
		// - si corresponde con el original
		// - añade firma al asiento en el RDS)
    	// - si se ha enviado por el representante
		AsientoCompleto asientoCompleto = comprobarAsiento(asiento,firma);

		// Obtenemos toda la lista de Referencias RDS
		Map referenciasRDS = new HashMap();
		for (Iterator it = tramitePersistentePAD.getDocumentos().keySet().iterator();it.hasNext();){
			DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(it.next());
			if (docPAD.getRefRDS() != null){
				referenciasRDS.put(docPAD.getIdentificador() + "-" + docPAD.getNumeroInstancia(),docPAD.getRefRDS());
			}
		}

    	// Realizamos registro / envío / consulta
    	RegistroController dest = new RegistroController(this.debugEnabled);
    	ResultadoRegistrar res;
    	if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_CONSULTA) {
    		String urlConsulta = tramiteVersion.getConsultaUrl();
    		if(urlConsulta != null && !"".equals(urlConsulta)){
    			urlConsulta = StringUtil.replace(urlConsulta,"@backoffice.url@",URL_CONSULTA);
    		}
    		res = dest.consultar(tramiteVersion.getConsultaTipoAcceso(),tramiteVersion.getConsultaEJB(),tramiteVersion.getConsultaLocalizacion() == TramiteVersion.EJB_LOCAL ,urlConsulta,
    				tramiteVersion.getConsultaAuth(),tramiteVersion.getConsultaAuthUser(),tramiteVersion.getConsultaAuthPwd(), this.tramiteVersion.getTramite().getProcedimiento(), this.tramiteVersion.getTramite().getIdentificador(),this.datosFormularios,this.tramiteVersion.getConsultaWSVersion());
    	}else {
    		res = dest.registrar(tramiteVersion.getDestino(),tramitePersistentePAD.getIdPersistencia(),asientoCompleto,referenciasRDS);

    		// Cacheamos justificante para mejorar accesos
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		DocumentoRDS docRDS;
    		docRDS = rds.consultarDocumento(res.getRdsJustificante());
    		AsientoCompleto justificanteRegistro = new AsientoCompleto();
    		justificanteRegistro.setAsiento(new String(docRDS.getDatosFichero(),ConstantesXML.ENCODING));
    		docRDS = rds.consultarDocumento(((DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS + "-1")).getRefRDS());
    		justificanteRegistro.setDatosPropios(new String(docRDS.getDatosFichero(),ConstantesXML.ENCODING));

    		res.setJustificanteRegistro(justificanteRegistro);

    	}

		// Devolvemos resultado

		return res;
	}

	private String mensajeLog(String mensaje){
		try{
			return "[" + tramiteVersion.getTramite().getIdentificador() + " v" + tramiteVersion.getVersion() + "] " + mensaje;
		}catch (Exception e){
			return mensaje;
		}
	}

	/**
	 * Ejecuta script pasandole los plugins básicos (dominios,datos sesión,...) sin permitir
	 * modificar los datos de los formularios
	 * En caso de producirse un error en script se genera una excepción. Esta excepción se generará si procede con el
	 * mensaje particularizado especificado en el script
	 * @param script
	 * @param params
	 * @return
	 */
	private String evaluarScript(byte[] script,HashMap params) throws ProcessorException{
		return ScriptUtil.evaluarScriptTramitacion(script,params,this.tramiteVersion,this.datosFormularios,this.tramitePersistentePAD,this.datosSesion, this.debugEnabled);
	}

	/**
	 * Ejecuta script pasandole los plugins básicos (dominios,datos sesión,...) permitiendo indicar
	 * que se permita modificar los datos de los formularios
	 * En caso de producirse un error en script se genera una excepción. Esta excepción se generará si procede con el
	 * mensaje particularizado especificado en el script
	 * @param script
	 * @param params
	 * @param modificados Lista de ids de formularios modificados
	 * @return
	 */
	private String evaluarScript(byte[] script,HashMap params, List modificados) throws ProcessorException{
		return ScriptUtil.evaluarScriptTramitacion(script,params,this.tramiteVersion,this.datosFormularios,this.tramitePersistentePAD,this.datosSesion,false,modificados, this.debugEnabled);
	}

	private String traducirMensaje(ProcessorException pe){
		if (StringUtils.isNotEmpty(pe.getMensajeError())){
			return pe.getMensajeError();
		}else{
			return traducirMensaje(pe.getCodigoError());
		}
	}


	// Realiza traducción del mensaje teniendo en cuenta el idioma
	// y los mensajes particulares y genéricos
	private String traducirMensaje(String codMensaje){
		try{
			// Obtenemos idioma
			String idioma = datosSesion.getLocale().getLanguage();

			// Intentamos buscar mensaje particular
			MensajeTramite mens = (MensajeTramite) tramiteVersion.getMensajes().get(codMensaje);
			if (mens != null){
				return ((TraMensajeTramite) mens.getTraduccion()).getMensaje();
			}
			// Intentamos buscar mensaje genérico
			return Literales.getLiteral(idioma,codMensaje);
		}catch (Exception e){
			log.error(mensajeLog("Excepcion al traducir mensaje " + codMensaje),e);
			return "Mensaje '" + codMensaje + "' no traducido";
		}
	}

	// Genera los datos iniciales del formulario
	private DatosFormulario generarDatosInicialesFormulario(DocumentoNivel docNivel) throws Exception{
		DatosFormulario datosForm = new DatosFormulario(docNivel.getDocumento().getModelo(),docNivel.getVersion());
		DatosIniciales dat = new DatosIniciales();

		// Ejecutamos script para generar datos iniciales
		if (docNivel.getFormularioDatosInicialesScript() != null && docNivel.getFormularioDatosInicialesScript().length > 0){
			HashMap param = new HashMap();
			param.put("DATOSINICIALES",dat);
			this.evaluarScript(docNivel.getFormularioDatosInicialesScript(),param);
		}

		// Generamos DatosFormulario a partir de datos iniciales
		datosForm.inicializar(dat.getDatosIniciales());

		// Devolvemos formulario
		return datosForm;
	}


	// En el paso de enviar se revisan si existen pagos inicializados
	// opcionales que no están finalizados para eliminarlos
	// (si son telemáticos previamente se comprobara el estado y se actualizarán
	// a finalizados o se eliminarán)
	// TODO ESTE CASO YA NO SE PUEDE DAR, YA QUE NO SE PUEDE PASAR DEL PASO DE PAGOS SI ESTADO ES INCORRECTO
	/*
	private void eliminarPagosNoFinalizados() throws Exception{
		boolean act = false;
		for (Iterator it = tramiteInfo.getPagos().iterator();it.hasNext();){
			DocumentoFront doc = (DocumentoFront) it.next();
			if ( doc.getObligatorio() == DocumentoFront.OPCIONAL &&	doc.getEstado() == DocumentoFront.ESTADO_INCORRECTO	){
					act = true; // Marcamos para actualizar informacion de estado de tramite
					String ls_id = doc.getIdentificador() + "-" + doc.getInstancia();
					DatosPago dat = (DatosPago) this.datosPagos.get(ls_id);
					if (dat.getTipoPago() == 'T'){
						// Comprobamos contra plataforma de pagos si se ha realizado el pago.
						// Si se ha realizado actualizamos estado PAD y RDS
						// TODO Pendiente comprobacion pagado
					}
					// Anulamos pago: borramos pago del RDS, actualizamos PAD y borrar de listas pagos
			    	borrarPago(doc.getIdentificador(),doc.getInstancia());
			}
		}

		// En caso de haber modificado algún pago actualizamos información de estado del trámite
		// ya que se utiliza en funciones posteriores
		if (act) actualizarTramiteInfo();

	}
	*/

	// Borra pago del RDS, actualiza la PAD y borra datos pago de lista datos pago
	private void borrarPago(String identificador,int instancia) throws Exception{

		String ls_id = identificador + "-" + instancia;
    	DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(ls_id);

    		// --- Borramos pago del RDS
    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    	UsoRDS uso = new UsoRDS();
    	uso.setReferenciaRDS(docPAD.getRefRDS());
    	uso.setReferencia(tramitePersistentePAD.getIdPersistencia());
    	uso.setTipoUso(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE);
    	rds.eliminarUso(uso);
    		// --- Actualizamos la PAD
    	docPAD.setReferenciaRDS(null);
    	docPAD.setEstado(DocumentoPersistentePAD.ESTADO_NORELLENADO);
    	actualizarPAD();
    		// --- Borramos datos pago de lista datos pago
    	this.datosPagos.remove(ls_id);
	}


	/**
	 * Borra tramite actual
	 * @param backup Indica si se debe realizar backup del tramite
	 * @throws Exception
	 */
	private void borrarPersistencia( boolean backup ) throws Exception
	{
		borrarPersistencia( tramitePersistentePAD.getIdPersistencia(), backup );
	}

	/**
	 * Borra tramite de persistencia
	 * @param idPersistencia  Id persistencia del tramite a borrar
	 * @param backup Indica si se debe realizar backup del tramite
	 * @throws Exception
	 */
	private void borrarPersistencia( String idPersistencia, boolean backup ) throws Exception{

		// Comprobamos si ha sido borrado
		if (this.borradoPersistencia) return;

		if ( log.isDebugEnabled() )
		{
			debug( "Eliminando tramite " + idPersistencia + " ( backup: " + backup + ")");
		}

		// Eliminamos de la zona de persistencia
		PadDelegate pad = DelegatePADUtil.getPadDelegate();
    	pad.borrarTramitePersistente( idPersistencia, backup );

    	// Marcamos como borrado
    	this.borradoPersistencia = true;

	}

	/**
	 * Obtiene datos de la sesion
	 * @param nivAut Nivel autenticacion
	 * @param idioma Idioma
	 * @param nifEntidadDelegada
	 * @param perfilAcceso
	 * @throws DelegateException
	 */
	private void obtenerDatosSesion(char nivAut,Locale idioma, String perfilAcceso, String nifEntidadDelegada) throws Exception
	{
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		Principal sp = this.context.getCallerPrincipal();
		datosSesion = new DatosSesion();
		datosSesion.setNivelAutenticacion(nivAut);
		datosSesion.setPerfilAcceso(perfilAcceso);
		datosSesion.setLocale(idioma);

		if ( plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO )
		{

			PadDelegate delegate = DelegatePADUtil.getPadDelegate();
			PersonaPAD personaPAD = delegate.obtenerDatosPersonaPADporUsuario( sp.getName() );

			datosSesion.setPersonaPAD( personaPAD );

			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(perfilAcceso)){
				// Buscamos entidad delegada
				PersonaPAD entidadPAD = delegate.obtenerDatosPersonaPADporNif(nifEntidadDelegada);
				if (entidadPAD == null){
					throw new Exception("No se encuentra entidad con nif/cif:" + nifEntidadDelegada);
				}
				// Buscamos permisos de delegacion
				PadDelegate pad = DelegatePADUtil.getPadDelegate();
				String permisos = pad.obtenerPermisosDelegacion(nifEntidadDelegada);
				// En caso de delegacion obtenemos entidad delegada y establecemos como usuario
				// del tramite la entidad y como delegado al usuario autenticado
				datosSesion.setCodigoUsuario(entidadPAD.getUsuarioSeycon());
				datosSesion.setPersonaPAD(entidadPAD);
				datosSesion.setDelegadoPAD(personaPAD);
				datosSesion.setPermisosDelegacion(permisos);
			}else if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO.equals(perfilAcceso)){
				// Si se accede como ciudadano ponemos como usuario el usuario autenticado
				datosSesion.setCodigoUsuario(sp.getName());
				datosSesion.setPersonaPAD(personaPAD);
				
				// Establecemos datos representante certificado si lo admite el plugin
				if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_CERTIFICADO) {
					DatosRepresentanteCertificado drc = obtenerDatosRepresentanteCertificado(plgLogin, sp);
					datosSesion.setDatosRepresentanteCertificado(drc);
				}
				
			}else{
				throw new Exception("Perfil de acceso no valido: " + perfilAcceso);
			}
		}else{
			// Acceso anonimo
			datosSesion.setCodigoUsuario(sp.getName());
		}
	}

	/**
	 * Realiza un log en la auditoria
	 * @param tipoEvento Tipo evento
	 * @param resultado Resultado evento
	 * @param descripcion Descripcion evento
	 * @param clave Clave evento
	 * @param procedimiento
	 */
	private void logAuditoria(String tipoEvento,String resultado,String descripcion,String clave, String procedimiento, boolean txNew){
		try{
			// Creamos evento
			Evento evento = new Evento();
			evento.setTipo(tipoEvento);
			evento.setResultado(resultado);
			evento.setDescripcion(descripcion);
			evento.setModeloTramite(this.tramiteVersion.getTramite().getIdentificador());
			evento.setVersionTramite(this.tramiteVersion.getVersion());
			evento.setIdioma(this.datosSesion.getLocale().getLanguage());
			evento.setClave(clave);
			evento.setProcedimiento(procedimiento);
			if (this.tramitePersistentePAD != null) evento.setIdPersistencia(this.tramitePersistentePAD.getIdPersistencia());
			evento.setNivelAutenticacion(Character.toString(this.datosSesion.getNivelAutenticacion()));
			if (this.datosSesion.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
				evento.setUsuarioSeycon(this.datosSesion.getCodigoUsuario());
				evento.setNumeroDocumentoIdentificacion(this.datosSesion.getNifUsuario());
				evento.setNombre(this.datosSesion.getNombreCompletoUsuario());
			}
			// Auditamos evento
			DelegateAUDUtil.getAuditaDelegate().logEvento(evento, txNew);
		}catch(Exception ex){
			log.error("Excepción auditoria en TramiteProcessor: " + ex.getMessage(),ex);
		}
	}

	/**
	 * Detecta si la respuesta es de error
	 * @param res
	 * @return
	 */
	private boolean existeError(RespuestaFront res){
		if (res.getMensaje() != null && res.getMensaje().getTipo() == MensajeFront.TIPO_ERROR){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Calcula destinatario tramite dinamico según el script
	 * @return
	 */
	private DestinatarioTramite calcularDestinatarioTramite() throws Exception{
		EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();

    	byte scriptDestinatario[];
    	if (especNivel.getDestinatarioTramite() != null && especNivel.getDestinatarioTramite().length > 0 ){
    		scriptDestinatario = especNivel.getDestinatarioTramite();
    	}else{
    		scriptDestinatario = especVersion.getDestinatarioTramite();
    	}

    	DestinatarioTramite destTra = new DestinatarioTramite();
    	ProcedimientoDestinoTramite procDest = this.calcularProcedimientoDestinoTramite();
    	
    	destTra.setCalculado(false);
    	destTra.setOficinaRegistral(tramiteVersion.getRegistroOficina());
    	destTra.setOrganoDestino(tramiteVersion.getOrganoDestino());
    	destTra.setUnidadAdministrativa(tramiteVersion.getUnidadAdministrativa().toString());
    	destTra.setProcedimiento(procDest.getProcedimiento());

		if (scriptDestinatario != null && scriptDestinatario.length>0){
			// Realizamos calculo destinatario
			HashMap params = new HashMap();
			params.put("DESTINATARIO_TRAMITE",destTra);
			this.evaluarScript(scriptDestinatario,params);

			// Validamos resultado calculo
			destTra.setCalculado(true);
			
			// Obtenemos entidad procedimiento
			String entidad = obtenerEntidadProcedimiento(destTra.getProcedimiento());
			if (entidad == null)
				throw new Exception("El codigo de procedimiento no es valido");
			if (!this.validarCodigoOficina(entidad, destTra.getOficinaRegistral()))
				throw new Exception("El codigo de oficina establecido por el script no es valido");
			if (!this.validarCodigoOrgano(entidad, destTra.getOrganoDestino()))
				throw new Exception("El codigo de organo destino establecido por el script no es valido");
			if (!this.validarCodigoUA(destTra.getUnidadAdministrativa()))
				throw new Exception("El codigo de unidad administrativa establecido por el script no es valido");

		}

		return destTra;
	}
	
	/**
	 * Calcula dinámicamente el procedimiento destino tramite según script
	 * @return
	 */
	private ProcedimientoDestinoTramite calcularProcedimientoDestinoTramite() throws Exception{
		EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();

    	byte scriptProcedimientoDestino[];
    	if (especNivel.getDestinatarioTramite() != null && especNivel.getDestinatarioTramite().length > 0 ){
    		scriptProcedimientoDestino = especNivel.getProcedimientoDestinoTramite();
    	}else{
    		scriptProcedimientoDestino = especVersion.getProcedimientoDestinoTramite();
    	}

    	ProcedimientoDestinoTramite procDestTra = new ProcedimientoDestinoTramite();
    	procDestTra.setCalculado(false);
    	procDestTra.setProcedimiento(tramiteVersion.getTramite().getProcedimiento());

		if (scriptProcedimientoDestino != null && scriptProcedimientoDestino.length>0){
			// Realizamos calculo destinatario
			HashMap params = new HashMap();
			params.put("PROCEDIMIENTO_DESTINO_TRAMITE",procDestTra);
			this.evaluarScript(scriptProcedimientoDestino,params);

			// Validamos resultado calculo
			procDestTra.setCalculado(true);

		}

		return procDestTra;
	}

	private String obtenerEntidadProcedimiento(String idProcedimiento) throws Exception {
		String entidad = null;
		ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(idProcedimiento);
		if (proc != null) {
			entidad = proc.getEntidad().getIdentificador();
		}
		return entidad;
	}


	/**
	 * Calcula datos representado.
	 * @return datos representado, nulo si no existe
	 * @throws Exception
	 */
	private DatosDesglosadosInteresado calcularDatosRepresentado() throws Exception{

		DatosDesglosadosInteresado datosRpdo = null;

		EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
		EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();

		// Comprobamos si se ha de ejecutar script de nombre o de datos desglosados
		byte [] scriptDatosDesglosados = ScriptUtil.getScriptVersionOrNivel(especVersion.getDatosRpdoScript(),especNivel.getDatosRpdoScript());
		boolean datosDesglosados = ScriptUtil.existeScript(scriptDatosDesglosados) ;

		// Si tiene script de datos desglosados, ejecutamos script unico
		if (datosDesglosados) {
			// Script de datos desglosados (new style)
			debug("Calculando datos representado mediante script datos desglosados");
			datosRpdo = ejecutarScriptDatosDesglosadosInteresado(scriptDatosDesglosados);
		} else {
			// Si no, ejecutamos los distintos scripts (nif, nombre,...)
			debug("Calculando datos representado mediante scripts independientes");
			// - Nif
			String rpdoNif="";
			byte [] scriptRpdoNif = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoRdoNif(),especNivel.getCampoRdoNif());
			if (ScriptUtil.existeScript(scriptRpdoNif)){
				rpdoNif = this.evaluarScript(scriptRpdoNif,null);
	    		// El script puede devolver un nif/cif valido o la cadena "NO-VALOR" para especificar que no se especificará NIF.
	    		if (StringUtils.isNotBlank(rpdoNif)) {
	    			// Normalizamos documento de identificación
	    			datosRpdo = new DatosDesglosadosInteresado();
	    			datosRpdo.setAnonimo(false);
	    			datosRpdo.setNif(NifCif.normalizarDocumento(rpdoNif));
	    		}
			}
			// - Nombre
			byte [] scriptRpdoNom = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoRdoNom(),especNivel.getCampoRdoNom());
			if (datosRpdo != null && ScriptUtil.existeScript(scriptRpdoNom)){
				String rpdoNom = this.evaluarScript(scriptRpdoNom,null);
				datosRpdo.setApellidosNombre(rpdoNom);
			}
		}

		// Validaciones
		// - Si no es anonimo,
		if (datosRpdo != null) {
			// debe devolver un nif valido
			if (StringUtils.isBlank(datosRpdo.getNif()) || !(NifCif.esNIF(datosRpdo.getNif()) || NifCif.esCIF(datosRpdo.getNif()) || NifCif.esNIE(datosRpdo.getNif())  || NifCif.esPasaporte(datosRpdo.getNif()))) {
				throw new Exception("Los scripts de representado no devuelven un nif/cif/nie/pasaporte valido");
			}
			// debe devolver un nombre (completo o desglosado)
			if (StringUtils.isBlank(datosRpdo.getApellidosNombre()) && StringUtils.isBlank(datosRpdo.getNombre())){
				throw new Exception("Los scripts de representado no establecen el nombre");
			}
		}

		return datosRpdo;

	}


	/**
	 * Calcula datos representante.
	 * @return datos representante
	 * @throws Exception
	 */
	private DatosDesglosadosInteresado calcularDatosRepresentante() throws Exception{

		DatosDesglosadosInteresado datosRpte = null;

		EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
		EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion()).getEspecificaciones();

		// Comprobamos si se ha de ejecutar script de nombre o de datos desglosados
		byte [] scriptDatosDesglosados = ScriptUtil.getScriptVersionOrNivel(especVersion.getDatosRpteScript(),especNivel.getDatosRpteScript());
		boolean datosDesglosados = ScriptUtil.existeScript(scriptDatosDesglosados) ;

		// Si tiene script de datos desglosados, ejecutamos script unico
		if (datosDesglosados) {
			// Script de datos desglosados (new style)
			debug("Calculando datos representante mediante script datos desglosados");
			datosRpte = ejecutarScriptDatosDesglosadosInteresado(scriptDatosDesglosados);
		} else {
			debug("Calculando datos representante mediante scripts independientes");
			// Si no, ejecutamos los distintos scripts (nif, nombre,...)
			datosRpte = new DatosDesglosadosInteresado();
			datosRpte.setAnonimo(true);
			// - Nif
			String rpteNif="";
			byte [] scriptRpteNif = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoRteNif(),especNivel.getCampoRteNif());
			if (ScriptUtil.existeScript(scriptRpteNif)){
				rpteNif = this.evaluarScript(scriptRpteNif,null);
	    		// El script puede devolver un nif/cif valido o la cadena "NO-VALOR" para especificar que no se especificará NIF.
	    		if (!("NO-VALOR".equals(rpteNif))) {
	    			// Normalizamos documento de identificación
	    			datosRpte.setAnonimo(false);
	    			datosRpte.setNif(NifCif.normalizarDocumento(rpteNif));
	    		}
			}
			// - Nombre
			byte [] scriptRpteNom = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoRteNom(),especNivel.getCampoRteNom());
			if (ScriptUtil.existeScript(scriptRpteNom)){
				String rpteNom = this.evaluarScript(scriptRpteNom,null);
				if (!("NO-VALOR".equals(rpteNom))){
					datosRpte.setApellidosNombre(rpteNom);
				}
			}
			// - Procedencia geografica
			byte [] scriptPais = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoCodigoPais(),especNivel.getCampoCodigoPais());
			if (ScriptUtil.existeScript(scriptPais)){
				String pais = this.evaluarScript(scriptPais,null);
				datosRpte.setCodigoPais(pais);
			}
			byte [] scriptProvincia = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoCodigoProvincia(),especNivel.getCampoCodigoProvincia());
			if (ScriptUtil.existeScript(scriptProvincia)){
				String codigoProvincia = this.evaluarScript(scriptProvincia,null);
				datosRpte.setCodigoProvincia(codigoProvincia);
			}
			byte [] scriptMunicipio = ScriptUtil.getScriptVersionOrNivel(especVersion.getCampoCodigoLocalidad(),especNivel.getCampoCodigoLocalidad());
			if (ScriptUtil.existeScript(scriptMunicipio)){
				String codigoMunicipio = this.evaluarScript(scriptMunicipio,null);
				datosRpte.setCodigoMunicipio(codigoMunicipio);
			}
		}

		// Validaciones
		// - Si no es anonimo,
		if (!datosRpte.isAnonimo()) {
			// debe devolver un nif valido
			if (StringUtils.isBlank(datosRpte.getNif()) || !(NifCif.esNIF(datosRpte.getNif()) || NifCif.esCIF(datosRpte.getNif()) || NifCif.esNIE(datosRpte.getNif()) || NifCif.esPasaporte(datosRpte.getNif()))) {
				throw new Exception("Los scripts de representante no devuelven un nif/cif/nie/pasaporte valido");
			}
			// debe devolver un nombre (completo o desglosado)
			/* Quitamos este control ya que antes no se comprobaba si existia nombre rpte
			if (StringUtils.isBlank(datosRpte.getApellidosNombre()) && StringUtils.isBlank(datosRpte.getNombre())){
				throw new Exception("Los scripts de representante no establecen el nombre");
			}
			*/
		}

		return datosRpte;

	}

	private DatosDesglosadosInteresado ejecutarScriptDatosDesglosadosInteresado(byte[] scriptDatosDesglosados)
			throws ProcessorException, Exception {
		DatosDesglosadosInteresado datosInt = new DatosDesglosadosInteresado();
		HashMap params = new HashMap();
		PluginDatosInteresadoDesglosado resultScript = new PluginDatosInteresadoDesglosado();
		params.put("INTERESADO",resultScript);
		this.evaluarScript(scriptDatosDesglosados,params);
		datosInt.setAnonimo(resultScript.isAnonimo());
		if (StringUtils.isNotBlank(resultScript.getNif())) {
			datosInt.setNif(NifCif.normalizarDocumento(resultScript.getNif()));
		} else if (StringUtils.isNotBlank(resultScript.getPasaporte())) {
			datosInt.setNif(NifCif.normalizarDocumento(resultScript.getPasaporte()));
		}
		datosInt.setNombre(resultScript.getNombre());
		datosInt.setApellido1(resultScript.getApellido1());
		datosInt.setApellido2(resultScript.getApellido2());
		if (!resultScript.isAnonimo() && StringUtils.isBlank(resultScript.getApellidosNombre())) {
			datosInt.setApellidosNombre(StringUtil.formatearNombreApellidos(
				ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM,
				resultScript.getNombre(),
				resultScript.getApellido1(),
				resultScript.getApellido2()));
		}
		datosInt.setCodigoPais(resultScript.getCodigoPais());
		datosInt.setCodigoProvincia(resultScript.getCodigoProvincia());
		datosInt.setCodigoMunicipio(resultScript.getCodigoLocalidad());
		datosInt.setDireccion(resultScript.getDireccion());
		datosInt.setCodigoPostal(resultScript.getCodigoPostal());
		datosInt.setTelefono(resultScript.getTelefono());
		datosInt.setEmail(resultScript.getEmail());
		return datosInt;
	}

	/**
	 * Obtiene fecha caducidad del trámite en función de los dias de persistencia
	 *
	 * La fecha de caducidad no podra sobrepasar la fecha de fin de plazo
	 *
	 * @return
	 */
	private Timestamp getFechaCaducidad(){
		TramiteNivel tn = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion());
    	EspecTramiteNivel espTramite = (EspecTramiteNivel) tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel espNivel = (EspecTramiteNivel) tn.getEspecificaciones();
		int diasPersistencia = espNivel.getDiasPersistencia();
		if (diasPersistencia <= 0) {
			diasPersistencia = espTramite.getDiasPersistencia();
		}

		// Si no se indica persistencia le damos un año
		if (diasPersistencia <= 0) {
			diasPersistencia = 365;
		}
		
		// Si es circuito reducido damos 1 dia para que termine
		if (isCircuitoReducido( tramiteVersion, this.datosSesion.getNivelAutenticacion())) {
			diasPersistencia = 1;
		}

		Date fechaCaducidad = (new Timestamp(System.currentTimeMillis() + (diasPersistencia * 24 * 60 * 60 * 1000L)));
		Date fechaFinPlazo = tramiteVersion.getFinPlazo();
		if (this.configuracionDinamica.isPlazoDinamico()){
			fechaFinPlazo = this.configuracionDinamica.getPlazoFin();
		}
		if (fechaFinPlazo != null){
			if (fechaCaducidad.after(fechaFinPlazo)) fechaCaducidad = fechaFinPlazo;
		}
		return new Timestamp(fechaCaducidad.getTime());
	}

	/**
	 * Actualiza la PAD con la información de persistencia calculando la fecha de caducidad del trámite
	 * y actualizando fecha de modificacion
	 *
	 * La fecha de caducidad del trámite no podrá sobrepasar la fecha de fin de plazo
	 *
	 * @throws Exception
	 */
	private String actualizarPAD() throws Exception{
		return actualizarPAD(false);
	}

	/**
	 * Actualiza la PAD con la información de persistencia calculando la fecha de caducidad del trámite
	 * y actualizando fecha de modificacion.
	 * La fecha de caducidad del trámite no podrá sobrepasar la fecha de fin de plazo
	 *
	 * @param cambioFlujo Actualiza PAD teniendo en cuenta de que se ha producido un cambio de flujo.
	 *
	 * @throws Exception
	 */
	private String actualizarPAD(boolean cambioFlujo) throws Exception{
		// Actualizamos fecha de caducidad
		tramitePersistentePAD.setFechaCaducidad(getFechaCaducidad());
		// Actualizamos PAD
		PadDelegate pad = DelegatePADUtil.getPadDelegate();
		String idPersistencia = pad.grabarTramitePersistente(this.tramitePersistentePAD);
		tramitePersistentePAD.setIdPersistencia(idPersistencia);
		// Recargamos tramite desde la PAD para obtener la fecha de modificacion
		// que servira para evitar accesos concurrentes (comprobamos que no haya habido un cambio de flujo,
		// con lo que no podriamos acceder al tramite persistente)
		if (!cambioFlujo) {
			tramitePersistentePAD = pad.obtenerTramitePersistente(idPersistencia);
	    	if (tramitePersistentePAD == null){
	    		throw new ProcessorException("No se ha podido cargar tramite",
	    				MensajeFront.MENSAJE_ERRORDESCONOCIDO);
	    	}
		}
		return idPersistencia;
	}

	/**
	 * Comprueba si existen pagos iniciados
	 * @param tramiteInfo
	 * @return true/false
	 */
	private boolean isPagoIniciado( TramiteFront tramiteInfo )
	{
		List lstPagos = ( List ) tramiteInfo.getPagos();
		for ( int i = 0; i < lstPagos.size(); i++ )
		{
			DocumentoFront documento = ( DocumentoFront ) lstPagos.get( i );
			if ( documento.getEstado() != DocumentoFront.ESTADO_NORELLENADO )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si existen pagos finalizados
	 * @param tramiteInfo
	 * @return true/false
	 */
	private boolean isPagoTelematicoFinalizado( TramiteFront tramiteInfo )
	{
		List lstPagos = ( List ) tramiteInfo.getPagos();
		for ( int i = 0; i < lstPagos.size(); i++ )
		{
			DocumentoFront documento = ( DocumentoFront ) lstPagos.get( i );
			if ( documento.getEstado() == DocumentoFront.ESTADO_CORRECTO && documento.getPagoTipo() == ConstantesPago.TIPOPAGO_TELEMATICO )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica la firma del asiento registral. Debe estar firmado por el representante o por un delegado con permiso de presentacion
	 * @param asientoRegistral Asiento registral
	 * @param firma Firma
	 * @return true/false
	 * @throws ProcessorException
	 */
	private boolean verificaFirmanteAdecuado( AsientoRegistral asientoRegistral,FirmaIntf firma ) throws ProcessorException
	{
		DatosInteresado datosInteresado = obtenerRepresentante( asientoRegistral );

		String firmanteNecesario;
		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(this.datosSesion.getPerfilAcceso()) &&
			this.datosSesion.getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) != -1 ){
			firmanteNecesario = this.datosSesion.getNifDelegado();
		}else{
			firmanteNecesario = datosInteresado.getNumeroIdentificacion();
		}

		return verificaFirmanteAdecuado( firmanteNecesario, firma );
	}

	/**
	 * Comprueba si una firma esta firmada con un certificado que tiene asociado determinado nif/cif
	 * @param firmanteNecesario Nif/Cif firmante
	 * @param firma Firma
	 * @return true/false
	 * @throws ProcessorException
	 */
	private boolean verificaFirmanteAdecuado( String firmanteNecesario, FirmaIntf firma ) throws ProcessorException
	{
		if ( !StringUtils.isEmpty( firmanteNecesario ) )
		{
			String nifFirma = "";
			try
			{
				nifFirma =  firma.getNif();
				nifFirma = NifCif.normalizarDocumento(nifFirma);
			}
			catch( Exception exc )
			{
				throw new ProcessorException ( "Error obteniendo NIF a partir de la firma", MensajeFront.MENSAJE_ERROR_FIRMANTE_INCORRECTO, exc );
			}

			// El firmante debera ser el propio interesado o si se tramita de forma delegada un delegado con permiso de presentacion
			boolean firmanteOk = nifFirma.equalsIgnoreCase( firmanteNecesario );
			if ( !firmanteOk )
			{
				debug( "Firmante incorrecto. Requerido [" + firmanteNecesario+ "]; Firmado con [" + nifFirma + "]" );
			}
			return firmanteOk;
		}
		return true;
	}

	/**
	 * Obtiene datos representante del asiento
	 * @param asiento Asiento registral
	 * @return Datos representante
	 */
	 private DatosInteresado obtenerRepresentante( AsientoRegistral asiento )
	 {
		DatosInteresado datosInteresado = null;
		for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
		{
			datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				return datosInteresado;
			}
		}
		return datosInteresado;
	 }

	 /**
	  * Devuelve lista de formularios que intervienen en el script separados con formato [FORM1][FORM2]...
	  * @param script
	  * @return
	  */
	 private String obtenerFormulariosScript(byte[] script) throws Exception{
		 String lista = "",form;
		 String accesoForm = "PLUGIN_FORMULARIOS.getDatoFormulario(";

		 if (script == null || script.length <=0) return lista;

		 String ls_script = ScriptUtil.scriptToString(script);
		 ls_script = ls_script.replaceAll(" ","");
		 ls_script = ls_script.replaceAll("\n","");
		 ls_script = ls_script.replaceAll("\r","");
		 int pos=-1,posFin;
		 while ( (pos=ls_script.indexOf(accesoForm,pos+1))!=-1){
			 posFin = ls_script.indexOf(",",pos);
			 form = "[" + ls_script.substring(pos + accesoForm.length() + 1,posFin - 1) + "]";
			 if (lista.indexOf(form) != -1 ) continue;
			 lista +=  form ;
		 }
		 return lista;
	 }


	 /**
	  * Evalua script dni flujo de documento.
	  * Si script es nulo o devuelve vacío, es el iniciador del tramite
	  * @param byte[] script
	  * @return dni
	  */
	 private String evaluarScriptNifFlujo(byte[] script,TramiteFront tramite) throws Exception{
		String nifFlujo="";
		if (script != null && script.length > 0){
			nifFlujo = this.evaluarScript(script,null);
		}
		if (StringUtils.isEmpty(nifFlujo)) {
			nifFlujo = tramiteInfo.getFlujoTramitacionDatosPersonaIniciador().getNif();
		}

		// Normalizamos documento
		nifFlujo = NifCif.normalizarDocumento(nifFlujo);

		return nifFlujo;
	 }


	 /**
	  * Calcula datos persona a partir de un nif
	  * @param nif
	  * @return
	  */
	 private PersonaPAD obtenerDatosPADporNif(String nif) throws Exception{
		 PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nif);
		 return p;
	 }

	 /**
	  * Calcula datos persona a partir de usuario Seycon
	  * @param nif
	  * @return
	  */
	 private PersonaPAD obtenerDatosPADporUsuarioSeycon(String usuarioSeycon)throws Exception{
		 PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(usuarioSeycon);
		 return p;
	 }


	 private String getMensajeInactividad( String codigoErrorExcepcionInactividad )
	 {
    	// Inicializamos
    	TramiteNivel tn = tramiteVersion.getTramiteNivel(datosSesion.getNivelAutenticacion());
    	EspecTramiteNivel espTramite = (EspecTramiteNivel) tramiteVersion.getEspecificaciones();
    	EspecTramiteNivel espNivel = (EspecTramiteNivel) tn.getEspecificaciones();

    	TraEspecTramiteNivel traEspTramite = ( TraEspecTramiteNivel ) espTramite.getTraduccion( datosSesion.getLocale().getLanguage() );
    	TraEspecTramiteNivel traEspTramiteNivel = ( TraEspecTramiteNivel ) espNivel.getTraduccion( datosSesion.getLocale().getLanguage() );

    	if (traEspTramiteNivel != null && StringUtils.isNotEmpty(traEspTramiteNivel.getMensajeInactivo())){
    		return traEspTramiteNivel.getMensajeInactivo() ;
    	}

    	if (traEspTramite != null && StringUtils.isNotEmpty(traEspTramite.getMensajeInactivo())){
    		return traEspTramite.getMensajeInactivo() ;
    	}

    	return traducirMensaje( codigoErrorExcepcionInactividad );
	 }

	 /**
	  *		Borra varios anexos a la vez.
	  *  	Al borrar un documento de la Pad esta los reordena, por tanto borrar en orden por mayor número de instancia
	  */
	 private void borrarAnexos(List anexosParaBorrar) throws Exception{

		 // Obtenemos mayor número de instancia
		 int max = 1,instancia;
		 for (Iterator it=anexosParaBorrar.iterator();it.hasNext();){
	 		String idAnexo = (String) it.next();
	 		instancia = StringUtil.getVersion(idAnexo);
	 		if ( instancia > max) max = instancia;
	 	}

		// Borramos por orden de instancia
		 for (int i=max;i>0;i--){
			 // Borramos los anexos que tengan este numero de instancia
			 for (Iterator it=anexosParaBorrar.iterator();it.hasNext();){
		 		String idAnexo = (String) it.next();
		 		instancia = StringUtil.getVersion(idAnexo);
		 		if (instancia == i){
		 			this.borrarAnexoImpl(StringUtil.getModelo(idAnexo),instancia);
		 		}
		 	}
		 }
	 }


	 /**
	  * Ajusta el idioma del trámite al iniciar un trámite, si no esta disponible el idioma que se pide se intentará en es o ca.
	  *
	  * @param idPersistencia
	  */
	 private void ajustarIdiomaIniciar() throws ProcessorException{

		// Si no esta disponible el idioma solicitado se intentará con otro
		String idioma = this.datosSesion.getLocale().getLanguage();
		if (tramiteVersion.getIdiomasSoportados().indexOf(idioma) == -1) {
			StringTokenizer st = new StringTokenizer(tramiteVersion.getIdiomasSoportados(),",");
			idioma = st.nextToken();
		}

		 // Comprobamos si el idioma esta soportado (lo hacemos porque tb se comprueba si esta establecido los literales a nivel de tramite)
		 comprobarIdioma(idioma);

		 // Establecemos idioma a nivel de version y de sesion
		 tramiteVersion.setCurrentLang(idioma);
		 if (!this.datosSesion.getLocale().getLanguage().equals(idioma)){
			 datosSesion.setLocale(new Locale(idioma));
		 }
	 }

	 /**
	  * Ajusta el idioma del trámite al cargar un trámite persistente con el idioma con el que se había tramitado
	  *
	  * @param idPersistencia
	  */
	 private void ajustarIdiomaCargar(String idPersistencia) throws ProcessorException{
		 // Cargamos tramite de persistencia
		 TramitePersistentePAD t = null;
		 try {
			 PadDelegate pad = DelegatePADUtil.getPadDelegate();
			 t = pad.obtenerTramitePersistente(idPersistencia);
		 }catch(DelegateException dex){
			 throw new ProcessorException("Error cargando de persistencia el tramite con id " + idPersistencia + ": " + dex.getMessage(), MensajeFront.MENSAJE_ERRORDESCONOCIDO, dex);
		 }

		 // Comprobamos si el idioma esta soportado
		 comprobarIdioma(t.getIdioma());

  		 // Establecemos idioma a nivel de version y de sesion
		 tramiteVersion.setCurrentLang(t.getIdioma());
		 if (!this.datosSesion.getLocale().getLanguage().equals(t.getIdioma())){
			 datosSesion.setLocale(new Locale(t.getIdioma()));
		 }
	 }

	 /**
	  * Comprueba si el idioma esta soportado y si esta establecida la traducción a nivel del trámite
	  * @param idioma
	  */
	 private void comprobarIdioma(String idioma) throws ProcessorException{

		 if (StringUtils.isEmpty(idioma)) {
			 throw new ProcessorException("Idioma no soportado para " +
					 "tramite '" +   this.tramiteVersion.getTramite().getIdentificador() + "' " +
					 "version " +  this.tramiteVersion.getVersion(),
					 MensajeFront.MENSAJE_ERROR_IDIOMA_NO_SOPORTADO);
		 }

		 if (tramiteVersion.getIdiomasSoportados().indexOf(idioma) == -1) {
			 throw new ProcessorException("Idioma '" + idioma + "' no soportado para " +
					 "tramite '" +   this.tramiteVersion.getTramite().getIdentificador() + "' " +
					 "version " +  this.tramiteVersion.getVersion(),
					 MensajeFront.MENSAJE_ERROR_IDIOMA_NO_SOPORTADO);
		 }

		 if (this.tramiteVersion.getTramite().getTraduccion(idioma) == null) {
			 throw new ProcessorException("Falta la tradución para el idioma '" + idioma + "' a nivel de tramite '" +
					 this.tramiteVersion.getTramite().getIdentificador() ,
					 MensajeFront.MENSAJE_ERROR_IDIOMA_NO_SOPORTADO);
		 }

	 }

	 /**
	  * Convierte un texto html en el que se pueden haber establecido tags especiales.
	  * Los tags disponibles son:
	  *
	  * 	[##TRAMITE.IDPERSISTENCIA##]
	  *
	  * @param html
	  * @return
	  */
	 private String convertHtmlTags(String html) throws Exception{
		 String htmlConverted=html;
		 htmlConverted = StringUtil.replace(htmlConverted,"[##TRAMITE.IDPERSISTENCIA##]",this.tramitePersistentePAD.getIdPersistencia());
		 return htmlConverted;

	 }

	 /**
	  * Obtiene configuracion del documento de pago
	  * @param identificador
	  * @return
	  * @throws Exception
	  */
	 private Documento obtenerDocumentoPago(String identificador) throws Exception{
		Documento doc = null;
	 	for (Iterator it = pagos.iterator();it.hasNext();){
	 		doc = (Documento) it.next();
	 		if (doc.getIdentificador().equals(identificador)){
	 			break;
	 		}
	 		doc = null;
	 	}
	 	if (doc == null){
	 		throw new Exception("No se encuentra pago " + identificador);
	 	}
	 	return doc;
	 }

	 /**
	  * Obtiene configuracion del documento de formulario
	  * @param identificador
	  * @return
	  * @throws Exception
	  */
	 private Documento obtenerDocumentoFormulario(String identificador) throws Exception{
		 Documento doc = null;
		 	for (Iterator it = formularios.iterator();it.hasNext();){
		 		doc = (Documento) it.next();
		 		if (doc.getIdentificador().equals(identificador)){
		 			break;
		 		}
		 		doc = null;
		 	}
		 	if (doc == null){
		 		throw new Exception("No se encuentra formulario " + identificador);
		 	}
		 	return doc;
	 }

	 /**
	  * Obtiene configuracion del documento de anexo
	  * @param identificador
	  * @return
	  * @throws Exception
	  */
	 private Documento obtenerDocumentoAnexo(String identificador) throws Exception{
		 Documento doc = null;
	    	for (Iterator it = anexos.iterator();it.hasNext();){
	    		doc = (Documento) it.next();
	    		if (doc.getIdentificador().equals(identificador)){
	    			break;
	    		}
	    		doc = null;
	    	}
	    	if (doc == null){
	    		throw new Exception("No se encuentra anexo " + identificador);
	    	}
	    	return doc;
	 }

	 /**
	  * Valida si la oficina de registro es válida
	 * @param entidad 
	  * @param codOrgano Codigo oficina
	  * @return true/false
	  * @throws Exception
	  */
	 private boolean validarCodigoOficina( String entidad, String codOficina) throws Exception{
		List oficinas = DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerOficinasRegistro(entidad, ConstantesRegtel.REGISTRO_ENTRADA);
		for (Iterator it = oficinas.iterator();it.hasNext();){
			ValorOrganismo vo = (ValorOrganismo) it.next();
			if (vo.getCodigo().equals(codOficina)){
				return true;
			}
		}
		return false;
	}


	 /**
	  * Valida si el codigo de organo destino es válido
	  * @param codOrgano Codigo organos destino
	 * @param entidad 
	  * @return true/false
	  * @throws Exception
	  */
	 private boolean validarCodigoOrgano(String entidad, String codOrgano) throws Exception{
		List organos = DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerServiciosDestino(entidad);
		for (Iterator it = organos.iterator();it.hasNext();){
			ValorOrganismo vo = (ValorOrganismo) it.next();
			if (vo.getCodigo().equals(codOrgano)){
				return true;
			}
		}
		return false;
	}

	 /**
	  * Valida si el codigo de unidad administrativa es válido
	  * @param codUA Codigo UA
	  * @return true/false
	  * @throws Exception
	  */
	 private boolean validarCodigoUA(String codUA) throws Exception{

		// Comprobamos q es un long
		try{
			Long.parseLong(codUA);
		}catch (Exception ex){
			return false;
		}

		// Buscamos en la lista
		List lstParametros = new ArrayList();
		lstParametros.add(codUA);
		List lstValores = UtilDominios.obtenerValoresDominio(
				ConstantesDominio.DOMINIO_SAC_NOMBRE_UNIDAD_ADMINISTRATIVA,
				lstParametros);
		if (lstValores.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	 /**
	  * Realiza log de una ProcessorException controlando que errores deben salir como debug o como error
	  * @param string Mensaje de log
	  * @param pe Procesor exception
	  */
	 private void logProcessorException(String logMessage, ProcessorException pe) {

		 boolean debug = false;

		 // Codigos de error que se mostraran como debug
		 if (
				 	MensajeFront.MENSAJE_NOEXISTETRAMITE.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_PLAZOCERRADO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_PLAZONOABIERTO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_TRAMITEBORRADO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_TRAMITEINACTIVO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_TRAMITEPENDIENTECONFIRMACION.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_TRAMITETERMINADO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_DELEGADO_NO_PERMISO_RELLENAR.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_ERROR_IDIOMA_NO_SOPORTADO.equals(pe.getCodigoError()) ||
				 	MensajeFront.MENSAJE_TRAMITETERMINADO.equals(pe.getCodigoError())
			){
			 		debug = true;
		 }

		 if (debug) {
			 debug(mensajeLog(logMessage));
		 }else{
			 log.error(mensajeLog(logMessage),pe);
		 }

	 }

	 /**
	  * Comprueba si existe algun documento que este pendiente de firma delegada
	  * y que su script de firmante dependa del formulario
	  * @param formulario
	  * @return Documento si hay alguno o nulo si no hay ninguno

	 private boolean getDocumentoPendienteFirmaDelegada(DocumentoFront formulario ){

		 for (Iterator it =this.tramiteInfo.getFormularios().iterator();it.hasNext();){
			 DocumentoFront df = (DocumentoFront) it.next();

			 // Comprobamos si es el mismo formulario
			 if (df.getIdentificador().equals(formulario.getIdentificador())){
				 continue;
			 }

			 // Si hay docs con firma delegada comprobamos que no sean dependientes del formulario
			 if (df.isPendienteFirmaDelegada() &&
				 df.getFormulariosScriptFirma().indexOf("["+formulario.getIdentificador() +"]") != -1){
				 	return true;
			 }
		 }

		 return false;
	 }
	 */

	 /**
	  * Verifica si los firmantes del documento coinciden con las firmas del documento
	  * @param doc
	  * @return
	  */
	 private boolean verificarFirmantesDocumento(DocumentoFront doc) throws Exception{

		 if (StringUtils.isEmpty(doc.getFirmante())){
			 return true;
		 }

		 String[] firmantes = doc.getFirmante().split("#");
		 String ls_id = doc.getIdentificador() + "-" + doc.getInstancia();
	     DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) this.tramitePersistentePAD.getDocumentos().get(ls_id);
	     boolean enc;

	     // Si es un doc firmado comprobamos las firmas realizadas
	     if (doc.isFirmado()){
		     List firmantesRDS = (List) this.firmaDocumentos.get(docPAD.getRefRDS().toString());

		     if (firmantesRDS.size() != firmantes.length){
		    	 return false;
		     }

		    for (int i=0;i<firmantes.length;i++){
		    	 enc = false;
		    	 for (Iterator it=firmantesRDS.iterator();it.hasNext();){
		    		 FirmaIntf firmaRDS = (FirmaIntf) it.next();
		    		 if (firmantes[i].equals(firmaRDS.getNif())){
		    			 enc=true;
		    			 break;
		    		 }
		    	 }
		    	 if (!enc){
		    		 return false;
		    	 }
		     }
		 // Si esta firmado como pendiente de firma delegada comprobamos si la lista de firmantes coincide
	     }else if (doc.isPendienteFirmaDelegada()){
	    	String [] firmantesDelegados = docPAD.getDelegacionFirmantes().split("#");

	    	if (firmantesDelegados.length != firmantes.length){
		    	 return false;
		     }

		    for (int i=0;i<firmantes.length;i++){
		    	 enc = false;
		    	 for (int j=0;j<firmantesDelegados.length;j++){
		    		 if (firmantes[i].equals(firmantesDelegados[j])){
		    			 enc=true;
		    			 break;
		    		 }
		    	 }
		    	 if (!enc){
		    		 return false;
		    	 }
		     }
	     }else{
	    	 throw new Exception("Estado no contemplado");
	     }

	    return true;

	 }

	 /**
	  * A partir de la info de TramiteInfo calcula si los firmantes son los correctos.
	  * Si los firmantes no son los correctos significa que ha habido cambios en la especificacion
	  * del tramite
	 * @throws Exception
	  */
	 private boolean validarFirmasDocumentos() throws Exception {
		 DocumentoFront docInfo;

		 // Formularios
		 for (Iterator it=tramiteInfo.getFormularios().iterator();it.hasNext();){
			 docInfo = (DocumentoFront) it.next();
			 if (docInfo.isFirmar() && (docInfo.isFirmado() || docInfo.isPendienteFirmaDelegada())){
				 if (!verificarFirmantesDocumento(docInfo)){
					 return false;
				 }
			 }
		 }

		 // Anexos
		 for (Iterator it=tramiteInfo.getAnexos().iterator();it.hasNext();){
			 docInfo = (DocumentoFront) it.next();
			 if (docInfo.isFirmar() && (docInfo.isFirmado() || docInfo.isPendienteFirmaDelegada())){
				 if (!verificarFirmantesDocumento(docInfo)){
					 return false;
				 }
			 }
		 }

		 return true;
	 }

	 /**
     * Comprueba si existen pagos opcionales no rellenados.
     * @return true si existen
     */
    private boolean existenPagosOpcionalesNoRellenados() {
    	boolean res = false;
    	for (Iterator it=this.tramiteInfo.getPagos().iterator();it.hasNext();){
			DocumentoFront doc = (DocumentoFront) it.next();
			// Obligatorios deben estar completados
			if (doc.getObligatorio() == DocumentoFront.OPCIONAL &&
				doc.getEstado() == DocumentoFront.ESTADO_NORELLENADO ){
				res = true;
				break;
			}
    	}
    	return res;
	}

   private void debug(String mensaje) {
    	if (this.debugEnabled) {
    		log.debug(mensaje);
    	}
    }

   private void setRollbackOnly() {
	   if (!this.context.getRollbackOnly()) {
		   this.context.setRollbackOnly();
	   }
   }
   
   private String generateCodigoSms(){
	   SecureRandom sr = new SecureRandom();
		String rn = "" + sr.nextInt(9999);
		rn = StringUtils.leftPad(rn, 4, '0');
		return rn;
   }
   
   
   private DatosRepresentanteCertificado obtenerDatosRepresentanteCertificado(PluginLoginIntf plgLogin, Principal sp) {
	   	
	    DatosRepresentanteCertificado res = null;
	   
		try{
			// Detectamos si el plugin lo admite
			Method m = null;
			Class[] params = new Class[] {Principal.class};
			m = plgLogin.getClass().getDeclaredMethod("getRepresentanteNif", params);

			// Obtenemos datos del principal
			if (plgLogin.getRepresentanteNif(sp) != null) {
				res = new DatosRepresentanteCertificado();
				res.setNif(plgLogin.getRepresentanteNif(sp));
				res.setNombre(plgLogin.getRepresentanteNombre(sp));
				res.setApellido1(plgLogin.getRepresentanteApellido1(sp));
				res.setApellido2(plgLogin.getRepresentanteApellido2(sp));
			}
			
		}catch (java.lang.NoSuchMethodException ex){
			res = null;
		}
		
		return res;
   }
    
}
