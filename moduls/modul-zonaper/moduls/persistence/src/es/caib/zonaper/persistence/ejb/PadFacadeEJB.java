package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import net.sf.hibernate.SessionFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.EntidadBTE;
import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.BteSistraDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.StringUtil;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.AlertasTramitacion;
import es.caib.xml.datospropios.factoria.impl.Dato;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.datospropios.factoria.impl.TramiteSubsanacion;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.Justificante;
import es.caib.zonaper.model.DocumentoEntrada;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaTelematica;
import es.caib.zonaper.model.DocumentoNotificacionTelematica;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.DocumentoRegistro;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.LogRegistroId;
import es.caib.zonaper.model.LogVerificacionMovil;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.Page;
import es.caib.zonaper.model.ParametrosSubsanacion;
import es.caib.zonaper.model.RegistroExterno;
import es.caib.zonaper.model.RegistroExternoPreparado;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.DetalleNotificacionesProcedimiento;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.FiltroBusquedaExpedientePAD;
import es.caib.zonaper.modelInterfaz.PaginaPAD;
import es.caib.zonaper.modelInterfaz.ParametrosTramiteSubsanacionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.PreregistroPAD;
import es.caib.zonaper.modelInterfaz.ResumenExpedientePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.ExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.IndiceElementoDelegate;
import es.caib.zonaper.persistence.delegate.LogRegistroDelegate;
import es.caib.zonaper.persistence.delegate.NotificacionTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;
import es.caib.zonaper.persistence.delegate.RegistroExternoDelegate;
import es.caib.zonaper.persistence.delegate.RegistroExternoPreparadoDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.AvisoAlertasTramitacion;
import es.caib.zonaper.persistence.util.CalendarioUtil;
import es.caib.zonaper.persistence.util.ConfigurationUtil;
import es.caib.zonaper.persistence.util.GeneradorId;
import es.caib.zonaper.persistence.util.LoggerRegistro;

/**
 * SessionBean con funciones a ser invocadas desde los demás módulos de la plataforma
 * Nota: el control de acceso a tramites se implementa en los ejbs de acceso a datos
 *
 * @ejb.bean
 *  name="zonaper/persistence/PadFacade"
 *  jndi-name="es.caib.zonaper.persistence.PadFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 *
 * @ejb.env-entry name="roleAuto" type="java.lang.String" value="${role.auto}"
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 *
 * @ejb.security-role-ref role-name="${role.gestor}" role-link="${role.gestor}"
 * @ejb.security-role-ref role-name="${role.auto}" role-link="${role.auto}"
 *
 */
public abstract class PadFacadeEJB implements SessionBean{

	protected static Log log = LogFactory.getLog(PadFacadeEJB.class);

    protected SessionFactory sf = null;
    protected SessionContext ctx = null;

	private String ROLE_AUTO,ROLE_GESTOR;

	private boolean AVISOS_OBLIG_NOTIF = false;

    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException {
		try
		{
			javax.naming.InitialContext initialContext = new javax.naming.InitialContext();

			ROLE_AUTO = (String) initialContext.lookup( "java:comp/env/roleAuto" );
			ROLE_GESTOR = (String) initialContext.lookup( "java:comp/env/roleGestor" );

			String strAvisosNotif = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.avisoObligatorioNotificaciones");
			if (StringUtils.isNotBlank(strAvisosNotif)) {
				AVISOS_OBLIG_NOTIF = Boolean.parseBoolean(strAvisosNotif);
			}
		}
		catch( Exception exc )
		{
			log.error( exc );
			throw new CreateException( exc.getLocalizedMessage() );
		}
	}

	 /**
     * Obtiene estado tramite.
     * @return N: No existe / P: Pendiente persistencia / T: Terminado / C: Pendiente confirmacion
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public String obtenerEstadoTramite(String idPersistencia) throws ExcepcionPAD {
		try {
			boolean existePersistente = DelegateUtil.getTramitePersistenteDelegate().existeTramitePersistente(idPersistencia);
			String estado = "N";
			if (existePersistente) {
				estado = "P";
			} else {
				estado = DelegateUtil.getEntradaPreregistroDelegate().obtenerEstadoEntradaPreregistro(idPersistencia);
				if ("N".equals(estado)) {
					if (DelegateUtil.getEntradaTelematicaDelegate().existeEntradaTelematica(idPersistencia)) {
						estado = "T";
					}
				}
			}
			return estado;
		}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido comprobar si existe trámite persistente con id: " + idPersistencia,ex);
    	}

	}

    /**
     * Obtiene tramite persistencia por identificador de persistencia
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public TramitePersistentePAD obtenerTramitePersistente(String idPersistencia) throws ExcepcionPAD{
    	// Cargamos tramitePersistente
    	TramitePersistente tramitePersistente;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramitePersistente = td.obtenerTramitePersistente(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámite persistente con id: " + idPersistencia,ex);
    	}

        // Pasamos a TramitePersistentePAD
    	return tramitePersistenteToTramitePersistentePAD(tramitePersistente);
    }

    

    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario(boolean filtrarPersistentes) throws ExcepcionPAD{
    	// Cargamos tramitePersistente
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesUsuario();
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + this.ctx.getCallerPrincipal().getName(),ex);
    	}

        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		if ("S".equals(tramitePersistente.getPersistente())) {
    			tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    		}
    	}
    	return tramitesPAD;
    }
    
    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * con una fecha límite
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario(Date fecha) throws ExcepcionPAD{
    	// Cargamos tramitePersistente
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesUsuario(fecha);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + this.ctx.getCallerPrincipal().getName(),ex);
    	}

        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		if ("S".equals(tramitePersistente.getPersistente())) {
    			tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    		}
    	}
    	return tramitesPAD;
    }
    

    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario() throws ExcepcionPAD{
    	return obtenerTramitesPersistentesUsuario(false);
    }


    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesEntidadDelegada(String nifEntidad, boolean filtroPersistentes) throws ExcepcionPAD{

    	// Cargamos tramitePersistente
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesEntidadDelegada(nifEntidad);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + nifEntidad,ex);
    	}

        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		if ("S".equals(tramitePersistente.getPersistente())) {
    			tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    		}
    	}
    	return tramitesPAD;
    }
    
    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesEntidadDelegada(String nifEntidad) throws ExcepcionPAD{
    	return obtenerTramitesPersistentesEntidadDelegada(nifEntidad, true); 
    }

    /**
     * Obtiene lista de tramites persistentes (de un determinado tramite/version) que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario(String modelo,int version) throws ExcepcionPAD{
    	// Cargamos tramitePersistente
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesUsuario(modelo,version);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + this.ctx.getCallerPrincipal().getName(),ex);
    	}

        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    	}
    	return tramitesPAD;
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String grabarTramitePersistente(TramitePersistentePAD obj)  throws ExcepcionPAD{

    	TramitePersistenteDelegate td;
    	TramitePersistente tramitePersistente;

    	// Realizamos comprobaciones
    	comprobarTramitePersistentePAD(obj);

    	// Construimos objeto tramite
    	tramitePersistente = tramitePersistentePADToTramitePersistente(obj);

    	// Guardamos tramite
    	try{
    		td = DelegateUtil.getTramitePersistenteDelegate();
    		String idPersistencia = td.grabarTramitePersistente(tramitePersistente);
    		return idPersistencia;
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido guardar trámite persistente",ex);
    	}
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void borrarTramitePersistente(String idPersistencia, boolean backup) throws ExcepcionPAD{
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		if (backup) {
    			log.debug("Realizando backup tramite " + idPersistencia);
    			TramitePersistente tramitePersistente = td.obtenerTramitePersistente(idPersistencia);
    			td.backupTramitePersistente(tramitePersistente);
    		} else {
    			// Eliminamos referencias en el RDS (mantenemos si hacemos backup)
    			log.debug("Borrando usos documentos tramite " + idPersistencia);
    			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    			rds.eliminarUsos(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE, idPersistencia );
    		}
    		log.debug("Borrando tramite " + idPersistencia);
    		td.borrarTramitePersistente(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido borrar trámite persistente con id: " + idPersistencia,ex);
    	}
    }


    /**
     * Realiza apuntes de entradas (trámites telemáticos) y salidas (notificaciones telemáticas) en la
     * zona personal del usuario
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
    public void logPad(ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws ExcepcionPAD{
    	try{

	    	// Obtenemos asiento del RDS
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();

	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();

	    	// Parseamos asiento
	    	DocumentoRDS docRDSAsiento = rds.consultarDocumento(refAsiento);
	    	AsientoRegistral asiento = factoria.crearAsientoRegistral(new ByteArrayInputStream (docRDSAsiento.getDatosFichero()));

			// Para acuse de recibo no hay justificante, ya que no se registra
	    	Justificante justificante = null;
			if (asiento.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_ACUSE_RECIBO){
				DocumentoRDS docRDS = rds.consultarDocumento(refJustificante);
		    	justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (docRDS.getDatosFichero()));
			}

	    	// Según el tipo de asiento realizamos log
	    	switch (asiento.getDatosOrigen().getTipoRegistro().charValue()){
	    		case ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA:
	    			if(StringUtils.isNotEmpty(asiento.getDatosAsunto().getIdentificadorTramite())){
	    				logPadEntradaTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			}else{
	    				logPadRegistroExterno(justificante,refAsiento,refJustificante,refDocumentos);
	    			}
	    			break;
	    		case ConstantesAsientoXML.TIPO_ENVIO:
	    			logPadEntradaTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;
	    		case ConstantesAsientoXML.TIPO_PREREGISTRO:
	    		case ConstantesAsientoXML.TIPO_PREENVIO:
	    			logPadEntradaPreregistro(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;
	    		case ConstantesAsientoXML.TIPO_REGISTRO_SALIDA:
	    			logPadNotificacionTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;
	    		default:
	    			throw new Exception("El tipo asiento no es válido para actualizar el log de la Pad: " + asiento.getDatosOrigen().getTipoRegistro());
	    	}

    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido realizar el log",ex);
    	}
    }

    /**
     * Metodo para confirmar un prerregistro en el modulo de preregistro
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public void confirmarPreregistro( Long codigoPreregistro, String oficina, String codProvincia,String codMunicipio, String descMunicipio) throws ExcepcionPAD
    {
    		// Obtenemos entrada preregistro
    		EntradaPreregistro entrada = null;
    		String entidad = null;
			try {
				entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro( codigoPreregistro );
				ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(entrada.getProcedimiento());
				entidad = proc.getEntidad().getIdentificador();
			} catch (Exception e) {
				throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
			}

			// Realizamos confirmacion preregistro
	    	confirmarPreregistroImpl(entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO,entidad,oficina,codProvincia, codMunicipio, descMunicipio,null,null);

    }

    /**
     * Metodo para confirmar un preenvio que anteriormente se habia confirmado automaticamente
     *
     * Unicamente debe realizar el registro y actualizar el numero/fecha registro
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public void confirmarPreenvioAutomatico( Long codigoPreregistro, String oficina, String codProvincia,String codMunicipio, String descMunicipio) throws ExcepcionPAD
    {
    	EntradaPreregistro entrada = null;
    	String entidad = null;
		try {
			entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro( codigoPreregistro );
			ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(entrada.getProcedimiento());
			entidad = proc.getEntidad().getIdentificador();
		} catch (Exception e) {
			throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
		}
    	confirmarPreregistroImpl( entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO, entidad, oficina, codProvincia, codMunicipio, descMunicipio,null,null);
    }


    /**
     * Metodo para obtener los datos de un preregistro no confirmado
     * Lo invocara el gestor desde la BTE para ver los datos de un preregistro no confirmado que le ha llegado.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public PreregistroPAD obtenerInformacionPreregistro( String numeroPreregistro) throws ExcepcionPAD
    {
    	try
    	{
	    	EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
	    	EntradaPreregistro e = epd.obtenerEntradaPreregistroPorNumero(numeroPreregistro);

	    	// Si no existe devolvemos nulo
	    	if (e == null) return null;

	    	// Obtenemos asiento para ver el codigo del tramite
	    	ReferenciaRDS refRDSAsiento = construirReferenciaRDS( e.getCodigoRdsAsiento(), e.getClaveRdsAsiento() );

	    	// Leemos asiento para obtener código / versión trámite
	    	// TODO SI SE METE EN LAS TABLAS EL IDENTIFICADOR DEL TRAMITE NO HACE FALTA PARSEAR ASIENTO
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRDS = rds.consultarDocumento(refRDSAsiento);
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
	    	AsientoRegistral asiento = factoria.crearAsientoRegistral(new ByteArrayInputStream (docRDS.getDatosFichero()));
			String identificadorTramite = asiento.getDatosAsunto().getIdentificadorTramite();

	    	// Devolvemos informacion preregistro
	    	PreregistroPAD p = new PreregistroPAD();
	    	p.setCodigoPreregistro(e.getCodigo());
	    	p.setIdentificadorTramite(identificadorTramite);
	    	p.setNumeroPreregistro(e.getNumeroPreregistro());
	    	p.setFechaPreregistro(e.getFecha());
	    	p.setFechaCaducidad(e.getFechaCaducidad());
	    	p.setNif(e.getNifRepresentante());
	    	p.setNombre(e.getNombreRepresentante());
	    	p.setAsunto(e.getDescripcionTramite());
	    	p.setFechaConfirmacion(e.getFechaConfirmacion());
	    	p.setNumeroRegistro(e.getNumeroRegistro());
	    	p.setIdentificadorProcedimiento(e.getProcedimiento());

	    	return p;
    	}
    	catch( Exception exc )
    	{
    		throw new ExcepcionPAD( "No se ha podido obtener datos del  preregistro", exc);
    	}
    }

    /**
     * Metodo para confirmar un preeenvio de forma automatica tras enviar el tramite.
     * Se comprobará el usuario de acceso y el id de persistencia
     * Lo único que realiza es realizar el pase a Bandeja.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void confirmacionAutomaticaPreenvio(String idPersistencia, String numeroPreregistro,Timestamp fechaPreregistro) throws ExcepcionPAD
    {
    	// Obtenemos entrada preregistro
    	EntradaPreregistro ep = null;
    	try{
    		ep = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se puede acceder al tramite");
    	}

    	if (ep == null)
    		throw new ExcepcionPAD("El tramite no existe");

    	if (!ep.getNumeroPreregistro().equals(numeroPreregistro))
    		throw new ExcepcionPAD("Numero de preregistro no coincide");

    	confirmarPreregistroImpl(ep,ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA,null,null,null,null,null,numeroPreregistro,fechaPreregistro);
    }


    /**
     * Metodo para confirmar un prerregistro que no se ha confirmado en punto de registro
     * (se ha presentado por registro normal o en otro lugar) y ha llegado al gestor
     *
     * Lo único que realiza es realizar el pase a Bandeja. En caso de que se haya eliminado por
     * fecha de caducidad lo restaura.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public void confirmarPreregistroIncorrecto(Long codigoPreregistro,String numeroRegistro,Timestamp fechaRegistro) throws ExcepcionPAD
    {
    	EntradaPreregistro entrada = null;
		try {
			entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro( codigoPreregistro );
		} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
		}
    	confirmarPreregistroImpl( entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR,null,null,null,null,null,numeroRegistro,fechaRegistro);
    }


    /**
     * Obtiene datos persona almacenados en PAD buscando por usuario
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
    		return padAplic.obtenerDatosPersonaPADporUsuario(usuario);
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    }

    /**
     * Verifica si existe usuario seycon en la PAD
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public boolean existePersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
    		return padAplic.existePersonaPADporUsuario(usuario);
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    }

    /**
     * Obtiene datos persona almacenados en PAD buscando por nif
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporNif( String nif ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
    		return padAplic.obtenerDatosPersonaPADporNif(nif);
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}

    }

    /* NO SE USA
     *
     * Da de alta una persona en la PAD
     *
     * ejb.interface-method
     * ejb.permission role-name="${role.todos}"
     *
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD

    public PersonaPAD altaPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	try
    	{
	    	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
	    	return padAplic.altaPersona( personaPAD );
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error modificando datos de la PAD",ex);
    	}
    }
    */

    /**
     *
     * Actualiza usuario de una persona en la PAD
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     *
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void actualizarCodigoUsuario( String usuOld, String usuNew) throws ExcepcionPAD
    {
    	try
    	{
	    	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
	    	padAplic.actualizarCodigoUsuario( usuOld, usuNew );
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error modificando datos de la PAD",ex);
    	}
    }


    /**
     * Obtiene fecha de acuse de recibo (para notificaciones entregadas)
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public Date obtenerAcuseRecibo(String entidad, String numeroRegistro) throws ExcepcionPAD
    {
    	try{
    		
    		// Por compatibilidad con versiones anteriores el codigo entidad puede ser nulo. Solo permitido si solo existe 1 entidad.
        	if (entidad == null) {
        		List entidades = DelegateBTEUtil.getBteSistraDelegate().obtenerEntidades();
        		if (entidades.size() != 1) {
        			throw new Exception("No se ha indicado el código de entidad");
        		}
        		entidad = ( (EntidadBTE) entidades.get(0)).getIdentificador();
        	}
    		
    		NotificacionTelematicaDelegate td = DelegateUtil.getNotificacionTelematicaDelegate();
    		NotificacionTelematica not = td.obtenerNotificacionTelematica(entidad, numeroRegistro);
    		if (not == null || not.isRechazada()) return null;
    		return not.getFechaAcuse();
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido obtener notificacion con numero de registro: " + numeroRegistro,ex);
    	}

    }

    /**
     * Obtiene estado notificaciones procedimiento (avisos a gestores)
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public DetalleNotificacionesProcedimiento obtenerDetalleNotificacionesProcedimiento(String idProcedimiento, Date desde, Date hasta) throws ExcepcionPAD
    {
    	try{
    		NotificacionTelematicaDelegate td = DelegateUtil.getNotificacionTelematicaDelegate();
    		return td.obtenerDetalleNotificacionesProcedimiento(idProcedimiento, desde, hasta);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido obtener estado notificaciones procedimiento",ex);
    	}

    }

    /**
     * Realiza el log de los registros efectuados para despues comprobar si estan enlazados a elementos de
     * la plataforma.
     * Este log se ejecuta en una nueva transaccion para asegurar que se apunta el numero de registro aunque
     * se haga un rollback global del proceso
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.transaction type = "RequiresNew"
     *
     * @param tipo Entrada (E) / Salida (S)
     * @param numeroRegistro Numero de registro
     * @param fechaRegistro Fecha de registro
     */
    public void logRegistro(String entidad, char tipo, String numeroRegistro, Date fechaRegistro, String error) throws ExcepcionPAD{
    	try{
    		LogRegistroDelegate dlg = DelegateUtil.getLogRegistroDelegate();
    		LogRegistro logReg = new LogRegistro();
    		LogRegistroId logRegId = new LogRegistroId(entidad, tipo+"",numeroRegistro);
    		logReg.setId(logRegId);
    		logReg.setFechaRegistro(fechaRegistro);
    		logReg.setDescripcionError(error);
    		logReg.setAnulado("N");
    		dlg.grabarLogRegistro(logReg);
    	}catch(Exception e){
    		throw new ExcepcionPAD("No se ha podido guardar el log de Registro con numero: " + numeroRegistro,e);
    	}

    }


    /**
     * Realiza el log de la preparación de registros de entrada externos para ser firmados con posterioridad que necesitan persistencia de días.
     * Este log servira para purgar estos registros si no se llegan a registrar.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     *
     * @param referenciaAsiento Referencia RDS del asiento preparado para registrar
     * @param referenciaAnexos Referencias RDS de los anexos
     * @param identificadorPersistencia Identificador de persistencia utilizado para los usos
     * @param diasPersistencia Días que estará disponible el registro para ser registrado
     */
    public void logRegistroExternoPreparado(ReferenciaRDS referenciaAsiento, Map referenciaAnexos, String identificadorPersistencia, int diasPersistencia) throws ExcepcionPAD{
    	try{

    		// Convertimos map de referencias de anexos en una map de string: key=codigo RDS / value= clave  RDS
    		String refAnexos = null;
    		if (referenciaAnexos != null) {
    			Map refs = new HashMap();
	    		for (Iterator it=referenciaAnexos.keySet().iterator(); it.hasNext();){
	    			ReferenciaRDS refAnexo = (ReferenciaRDS) referenciaAnexos.get(it.next());
	    			refs.put( Long.toString(refAnexo.getCodigo()), refAnexo.getClave());
	    		}
	    		refAnexos = StringUtil.serializarMap(refs);
    		}

    		// Calculamos fecha de eliminacion
    		Date fechaHoy = new Date();
    		Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaHoy);
            calendar.add(Calendar.DAY_OF_MONTH, diasPersistencia);
    		Date fechaElim = calendar.getTime();


    		// Realizamos log
    		RegistroExternoPreparado r = new RegistroExternoPreparado();
    		r.setCodigoRdsAsiento(new Long(referenciaAsiento.getCodigo()));
    		r.setClaveRdsAsiento(referenciaAsiento.getClave());
    		r.setReferenciasAnexos(refAnexos);
    		r.setIdPersistencia(identificadorPersistencia);
    		r.setFechaCreacion(fechaHoy);
    		r.setFechaEliminacion(fechaElim);

    		RegistroExternoPreparadoDelegate dlg = DelegateUtil.getRegistroExternoPreparadoDelegate();
    		dlg.grabarRegistroExternoPreparado(r);

    	}catch(Exception e){
    		throw new ExcepcionPAD("No se ha podido guardar el log de registro externo preparado",e);
    	}

    }


    /**
     * Indica si el usuario autenticado es delegado o representante de alguna entidad
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public boolean esDelegado() throws ExcepcionPAD
    {
    	try{
    		return (DelegateUtil.getDelegacionDelegate().obtenerDelegacionesUsuario().size() > 0);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error comprobando si el usuario es delegado de alguna entidad",ex);
    	}

    }


    /**
     * Obtiene permisos de delegacion sobre la entidad del usuario autenticado
     *
     * @param nif nif/cif entidad
     * @return permisos entidad
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad) throws ExcepcionPAD
	{
		// NOTA: Damos permiso al role auto porque puede provenir de una consulta hecha a traves
		//       de ConsultaPADEJB que se ejecuta con role auto
		try{
    		return DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}
	}


	/**
     * Obtiene permisos de delegacion sobre la entidad del nif indicado
     *
     * @param nif nif/cif entidad
     * @param nif nif usuario
     * @return permisos entidad
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad, String nifUsuario) throws ExcepcionPAD
	{
		try{
    		return DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad,nifUsuario);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}
	}


	/**
     * Indica si la entidad tiene habilitada la delegacion
     *
     * @param nif nif/cif entidad
     * @return boolean
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public boolean habilitadaDelegacion(String nifEntidad) throws ExcepcionPAD
	{
		try{
    		return DelegateUtil.getDelegacionDelegate().habilitadaDelegacion(nifEntidad);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}
	}

	/**
     * Recupera parametros tramite subsanacion a partir de la clave de acceso
     *
     * @param key clave acceso
     * @return ParametrosTramiteSubsanacionPAD
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ParametrosTramiteSubsanacionPAD recuperaParametrosTramiteSubsanacion(String key) throws ExcepcionPAD
	{
		try{
    		ParametrosSubsanacion p = DelegateUtil.getNotificacionTelematicaDelegate().recuperarParametrosTramiteSubsanacion(key);

    		ParametrosTramiteSubsanacionPAD res = new ParametrosTramiteSubsanacionPAD();
    		res.setExpedienteCodigo(p.getExpedienteCodigo());
    		res.setExpedienteUnidadAdministrativa(p.getExpedienteUnidadAdministrativa());
    		res.setParametros(StringUtil.deserializarMap(p.getParametros()));
    		return res;

    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo parametros tramite subsanacion",ex);
    	}
	}

	/**
     * Indica que se ha remitido el tramite para la firma de documentos
     *
     *  @ejb.interface-method
     *  @ejb.permission role-name="${role.todos}"
     *
     */
	public void avisarPendienteFirmarDocumentos(String idPersistencia) throws ExcepcionPAD
	{
		try
		{
			DelegateUtil.getAvisosDelegacionDelegate().avisarPendienteFirmarDocumentos(idPersistencia);
		}catch (Exception ex){
			throw new ExcepcionPAD("Error obteniendo parametros tramite subsanacion",ex);
		}
	}


	/**
     * Indica que se ha remitido el tramite para su presentacion
     *
     *  @ejb.interface-method
     *  @ejb.permission role-name="${role.todos}"
     *
     */
	public void avisarPendientePresentacionTramite(String idPersistencia)throws ExcepcionPAD
	{
		try
		{
			DelegateUtil.getAvisosDelegacionDelegate().avisarPendientePresentacionTramite(idPersistencia);
		}catch (Exception ex){
			throw new ExcepcionPAD("Error obteniendo parametros tramite subsanacion",ex);
		}
	}

	/**
     * Busca expedientes gestionados por gestor.
     *
     *  @ejb.interface-method
     *  @ejb.permission role-name="${role.gestor}"
     *
     */
	public PaginaPAD busquedaPaginadaExpedientesGestor(FiltroBusquedaExpedientePAD filtro, int numPagina, int longPagina) throws ExcepcionPAD
	{
		try
		{
			// Realizamos busqueda paginada
			Page page = DelegateUtil.getExpedienteDelegate().busquedaPaginadaExpedientesReales(filtro, numPagina, longPagina);

			// Convertimos a PaginaPAD
			PaginaPAD paginaPAD = new PaginaPAD();
			List results = new ArrayList();
			for (Iterator iterator = page.getList().iterator(); iterator.hasNext();) {
				Expediente exp = (Expediente) iterator.next();
				ResumenExpedientePAD resExp = expedienteToResumenExpedientePAD(exp);
				results.add(resExp);
			}

			paginaPAD.setNumeroPagina(numPagina);
			paginaPAD.setList(results);
			paginaPAD.setNextPage(page.isNextPage());
			paginaPAD.setPreviousPage(page.isPreviousPage());
			paginaPAD.setTotalResults(page.getTotalResults());
			return paginaPAD;

		}catch (Exception ex){
			throw new ExcepcionPAD("Error obteniendo expedientes gestor",ex);
		}
	}


	/**
     * Envia SMS para verificar movil.
     *
     *  @ejb.interface-method
     *  @ejb.permission role-name="${role.auto}"
     *
     */
	public void enviarSmsVerificarMovil(String idPersistencia, String idProcedimiento, String movil, String codigoSms, String idioma, int minutosCaducidad) throws ExcepcionPAD
	{
		try
		{
			AvisoAlertasTramitacion.getInstance().enviarSmsVerificarMovil(idPersistencia, idProcedimiento, movil, codigoSms, idioma, minutosCaducidad);
		}catch (Exception ex){
			throw new ExcepcionPAD("Error enviando sms para verificar movil",ex);
		}
	}

	/**
     * Envia SMS para verificar movil.
     *
     *  @ejb.interface-method
     *  @ejb.permission role-name="${role.todos}"
     *
     */
	
	public void logSmsVerificarMovil(String idPersistencia, String movil, String codigoSms) throws ExcepcionPAD
	{
		try
		{
			LogVerificacionMovil logRegistro = new LogVerificacionMovil();
			logRegistro.setCodigoSms(codigoSms);
			logRegistro.setIdPersistencia(idPersistencia);
			logRegistro.setMovil(movil);
			logRegistro.setFecha(new Date());
			DelegateUtil.getLogVerificacionMovilDelegate().grabarLogVerificacionMovil(logRegistro );				
		}catch (Exception ex){
			throw new ExcepcionPAD("Error enviando sms para verificar movil",ex);
		}
	}
	
	
    /**
     * Obtiene lista de procedimientos en los que ha participado usuario (tramites persistentes y expedientes).
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerProcedimientosUsuario(String lang, Date fecha) throws ExcepcionPAD{
    	try {
	    	List procsId = new ArrayList();
	    	List tramites = new ArrayList();
	    		
	    	// Recuperamos id procedimientos de tramites persistentes
	    	if (fecha == null){
	    		tramites = this.obtenerTramitesPersistentesUsuario();
	    	} else{
	    		tramites = this.obtenerTramitesPersistentesUsuario(fecha);
	    	}
	    	for (Iterator it = tramites.iterator(); it.hasNext();) {
	    		TramitePersistentePAD tp = (TramitePersistentePAD) it.next();
	    		if (!procsId.contains(tp.getIdProcedimiento())) {
	    			procsId.add(tp.getIdProcedimiento());
	    		}
	    	}
	    	
	    	// Recuperamos id procedimientos de expedientes
	    	List procsIdExpe = DelegateUtil.getExpedienteDelegate().obtenerProcedimientosId(fecha);
	    	for (Iterator it = procsIdExpe.iterator(); it.hasNext();) {
	    		String idProcExpe = (String) it.next();
	    		if (!procsId.contains(idProcExpe)) {
	    			procsId.add(idProcExpe);
	    		}
	    	}
	    	
	    	// Recuperamos procedimientos de Bantel y dejamos solo los del usuario
	    	List res = new ArrayList();
	    	List procsBte = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimientos(lang);
	    	for (Iterator it = procsBte.iterator(); it.hasNext();) {
	    		ProcedimientoBTE proc = (ProcedimientoBTE) it.next();
	    		if (procsId.contains(proc.getIdentificador())) {
	    			res.add(proc);
	    		}
	    	}
	    	
	    	return res;
    	
	    }catch (Exception ex){
			throw new ExcepcionPAD("Error consultando procedimientos usuario",ex);
		}
    	
    }
    
    /**
     * Obtiene lista de tramites en los que ha participado usuario.
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesIdUsuario() throws ExcepcionPAD{
    	try {
	    	List tramitesId = new ArrayList();
	    		
	    	// Recuperamos id tramite de tramites persistentes 
	    	List tramites = this.obtenerTramitesPersistentesUsuario();
	    	for (Iterator it = tramites.iterator(); it.hasNext();) {
	    		TramitePersistentePAD tp = (TramitePersistentePAD) it.next();
	    		if (!tramitesId.contains(tp.getTramite())) {
	    			tramitesId.add(tp.getTramite());
	    		}
	    	}
	    	
	    	// Recuperamos id tramite de entradas telematicas  
	    	List tramiteIdsTel = DelegateUtil.getEntradaTelematicaDelegate().listarTramiteIds();
	    	for (Iterator it = tramiteIdsTel.iterator(); it.hasNext();) {
	    		String idTramiteTel = (String) it.next();
	    		if (!tramitesId.contains(idTramiteTel)) {
	    			tramitesId.add(idTramiteTel);
	    		}
	    	}
	    	
	    	// Recuperamos id tramite de entradas preregistro  
	    	List tramiteIdsPre = DelegateUtil.getEntradaPreregistroDelegate().listarTramiteIds();
	    	for (Iterator it = tramiteIdsPre.iterator(); it.hasNext();) {
	    		String idTramitePre = (String) it.next();
	    		if (!tramitesId.contains(idTramitePre)) {
	    			tramitesId.add(idTramitePre);
	    		}
	    	}
	    	
	    	return tramitesId;
    	
	    }catch (Exception ex){
			throw new ExcepcionPAD("Error consultando tramites usuario",ex);
		}
    	
    }

    // ------------------------ Funciones utilidad ----------------------------------------------------------------
    /**
     * Crea entrada en el log de entradas telemáticas
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadEntradaTelematica(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{

    	AsientoRegistral asiento = justificante.getAsientoRegistral();

    	EntradaTelematica entrada = new EntradaTelematica();
    	entrada.setTipo(asiento.getDatosOrigen().getTipoRegistro().charValue());

    	// Obtenemos datos de representante/representado
    	Iterator it =asiento.getDatosInteresado().iterator();
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			entrada.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			entrada.setUsuario(di.getUsuarioSeycon());
    			entrada.setNifRepresentante(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentante(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			entrada.setNifRepresentado(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentado(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
    			entrada.setNifDelegado(di.getNumeroIdentificacion());
    			entrada.setNombreDelegado(di.getIdentificacionInteresado());
    		}
    	}

    	// Obtenemos documentos
    	String idPersistencia = null;
    	String habilitarAvisos= null,avisoSMS= null,avisoEmail= null,habilitarNotificacionTelematica=null;
    	DatosPropios datosPropios = null;
    	it = asiento.getDatosAnexoDocumentacion().iterator();
    	TramiteSubsanacion tramiteSubsanacion = null;
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		DocumentoEntradaTelematica doc = new DocumentoEntradaTelematica();
    		doc.setEntradaTelematica(entrada);
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
    		doc.setDescripcion(da.getExtractoDocumento());
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());
    		doc.setClaveRDS(ref.getClave());

    		// Si el documento es el de datos propios obtenemos id persistencia
    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
    			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
    			datosPropios = factoria.crearDatosPropios(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));
    			idPersistencia = datosPropios.getInstrucciones().getIdentificadorPersistencia();
    			habilitarAvisos = datosPropios.getInstrucciones().getHabilitarAvisos();
    			avisoEmail = datosPropios.getInstrucciones().getAvisoEmail();
    			avisoSMS = datosPropios.getInstrucciones().getAvisoSMS();
    			habilitarNotificacionTelematica = datosPropios.getInstrucciones().getHabilitarNotificacionTelematica();
    			if(datosPropios.getInstrucciones().getTramiteSubsanacion() !=  null){
    				tramiteSubsanacion = datosPropios.getInstrucciones().getTramiteSubsanacion();
    				entrada.setSubsanacionExpedienteCodigo(tramiteSubsanacion.getExpedienteCodigo());
    				entrada.setSubsanacionExpedienteUA(tramiteSubsanacion.getExpedienteUnidadAdministrativa());
    			}
    		}

    		entrada.addDocumento(doc);
    	}

    	// Comprobamos que se haya establecido el identificador de persistencia en el documento
    	// de datos propios
    	if (StringUtils.isEmpty(idPersistencia)){
    		throw new Exception("No se ha establecido el identificador de persistencia en el documento de datos propios");
    	}

    	// Establecemos datos entrada
    	entrada.setIdPersistencia(idPersistencia);
    	entrada.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	entrada.setCodigoRdsAsiento(refAsiento.getCodigo());
    	entrada.setClaveRdsAsiento(refAsiento.getClave());
    	entrada.setCodigoRdsJustificante(refJustificante.getCodigo());
    	entrada.setClaveRdsJustificante(refJustificante.getClave());
    	entrada.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	entrada.setNumeroRegistro(justificante.getNumeroRegistro());
    	entrada.setFecha(justificante.getFechaRegistro());
    	entrada.setProcedimiento(datosPropios.getInstrucciones().getIdentificadorProcedimiento());
    	entrada.setTramite(StringUtil.getModelo(justificante.getAsientoRegistral().getDatosAsunto().getIdentificadorTramite()));
    	entrada.setVersion(new Integer(StringUtil.getVersion(justificante.getAsientoRegistral().getDatosAsunto().getIdentificadorTramite())));
    	entrada.setHabilitarAvisos(habilitarAvisos);
    	entrada.setAvisoEmail(avisoEmail);
    	entrada.setAvisoSMS(avisoSMS);
    	entrada.setHabilitarNotificacionTelematica(habilitarNotificacionTelematica);

    	// Guardamos en log de entrada telematica
    	EntradaTelematicaDelegate td = DelegateUtil.getEntradaTelematicaDelegate();
    	Long codEntrada = td.grabarNuevaEntradaTelematica(entrada);
    	entrada.setCodigo(codEntrada);

    	// Si es un tramite de subsanacion, creamos elemento expediente asociado y actualizamos expediente
    	if (tramiteSubsanacion != null){
    		DelegateUtil.getProcesosAutoDelegate().actualizarExpedienteTramiteSubsanacion(entrada.getCodigo(),ElementoExpediente.TIPO_ENTRADA_TELEMATICA);
    	} else {
    	// Si no esta asociado a expediente, creamos expediente virtual
    		Expediente expe = new Expediente();
    		expe.setTipoExpediente(Expediente.TIPO_EXPEDIENTE_VIRTUAL);
    		expe.setUnidadAdministrativa(new Long(justificante.getAsientoRegistral().getDatosAsunto().getCodigoUnidadAdministrativa()));
    		expe.setIdExpediente(entrada.getIdPersistencia());
    		expe.setIdioma(entrada.getIdioma());
    		expe.setDescripcion(entrada.getDescripcionTramite());
    		expe.setFecha(new Timestamp(entrada.getFecha().getTime()));
    		expe.setFechaConsulta(expe.getFecha());
    		expe.setFechaInicio(expe.getFecha());
    		expe.setFechaFin(expe.getFecha());
    		expe.setHabilitarAvisos("N");
    		expe.setUsuarioSeycon(entrada.getUsuario());
    		expe.setSeyconCiudadano(entrada.getUsuario());
    		expe.setEstado(ConstantesZPE.ESTADO_SOLICITUD_ENVIADA);
    		expe.setNifRepresentante(entrada.getNifRepresentante());
    		expe.setNifRepresentado(entrada.getNifRepresentado());
    		expe.setNombreRepresentado(entrada.getNombreRepresentado());
    		expe.setIdProcedimiento(entrada.getProcedimiento());

    		ElementoExpediente eleExpe = new ElementoExpediente();
    		eleExpe.setFecha(entrada.getFecha());
    		eleExpe.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_TELEMATICA);
    		eleExpe.setCodigoElemento(entrada.getCodigo());
    		eleExpe.setIdentificadorPersistencia(entrada.getIdPersistencia());
    		eleExpe.setAccesoAnonimoExpediente(entrada.getNivelAutenticacion() == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO );
    		expe.addElementoExpediente(eleExpe, entrada);

    		DelegateUtil.getExpedienteDelegate().grabarExpedienteVirtual(expe);

    	}

    	// Generamos indices de busqueda (solo para autenticados)
    	if (StringUtils.isNotBlank(entrada.getNifRepresentante())) {
    		 // Crea los indices para la entrada
    		crearIndicesEntrada(entrada, datosPropios);
    	}

    	// Generamos email de tramite finalizado
    	if (datosPropios.getInstrucciones().getAlertasTramitacion() != null && StringUtils.isNotBlank(datosPropios.getInstrucciones().getAlertasTramitacion().getEmail())) {
    		// Capturamos posible excepcion xa q no interfiera en proceso registro
    		try {
    			DelegateUtil.getProcesosAutoDelegate().alertaTramitacionTramiteRealizado(entrada, datosPropios.getInstrucciones().getAlertasTramitacion().getEmail());
    		} catch (Exception ex) {
    			log.error("No se ha podido realizar el aviso de tramite finalizado. Se continua con proceso registro.", ex);
    		}
    	}

    	// Generamos log de auditoria
    	this.logEvento(ConstantesAuditoria.EVENTO_ENVIO_TRAMITE,String.valueOf(entrada.getNivelAutenticacion()), entrada.getUsuario(), entrada.getNifRepresentante(),
    			entrada.getNombreRepresentante(), entrada.getIdioma(), "S", entrada.getDescripcionTramite(), entrada.getTramite(), entrada.getVersion(), entrada.getProcedimiento(),
    			entrada.getIdPersistencia(), entrada.getTipo() == ConstantesAsientoXML.TIPO_ENVIO?"B":"R");
    }

    /**
     * Realiza log de un registro telematico que no procede de sistra
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws Exception
     */
	private void logPadRegistroExterno(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos) throws Exception{
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	Principal sp = this.ctx.getCallerPrincipal();
    	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    	RegistroExterno registro = new RegistroExterno();

    	// Obtenemos datos de representante/representado
    	Iterator it =asiento.getDatosInteresado().iterator();
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			//Informamos si existe info de nivel de autenticacion de como se ha autenticado el usuario (opcional)
    			if(di.getNivelAutenticacion() != null){
    				registro.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			}
    			registro.setUsuario(di.getUsuarioSeycon());
    			registro.setNifRepresentante(di.getNumeroIdentificacion());
    			registro.setNombreRepresentante(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			registro.setNifRepresentado(di.getNumeroIdentificacion());
    			registro.setNombreRepresentado(di.getIdentificacionInteresado());
    		}
    	}

    	// Obtenemos documentos
    	String idPersistencia = null;
    	String habilitarAvisos= null,avisoSMS= null,avisoEmail= null,habilitarNotificacionTelematica=null;
    	it = asiento.getDatosAnexoDocumentacion().iterator();
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		DocumentoRegistro doc = new DocumentoRegistro();
    		doc.setRegistroExterno(registro);
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
    		doc.setDescripcion(da.getExtractoDocumento());
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());
    		doc.setClaveRDS(ref.getClave());
    		registro.addDocumento(doc);
    	}

    	// Establecemos datos entrada
    	registro.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	registro.setCodigoRdsAsiento(refAsiento.getCodigo());
    	registro.setClaveRdsAsiento(refAsiento.getClave());
    	registro.setCodigoRdsJustificante(refJustificante.getCodigo());
    	registro.setClaveRdsJustificante(refJustificante.getClave());
    	registro.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	registro.setNumeroRegistro(justificante.getNumeroRegistro());
    	registro.setFecha(justificante.getFechaRegistro());

    	// Guardamos en log de entrada telematica
    	RegistroExternoDelegate td = DelegateUtil.getRegistroExternoDelegate();
    	td.grabarRegistroExterno(registro);
    }

    /**
     * Crea entrada en el log de entradas telemáticas (solo para gestores y auto)
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadNotificacionTelematica(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{

    	// Comprobamos que el usuario pertenezca al role gestor o auto
    	if (!this.ctx.isCallerInRole(ROLE_AUTO) && !this.ctx.isCallerInRole(ROLE_GESTOR))
			throw new Exception("El usuario debe tener el role auto o gestor para realizar el apunte de la notificacion");

    	NotificacionTelematica notificacion = new NotificacionTelematica();
    	Expediente expe = null;

    	// Establecemos usuario gestor que da de alta la notificacion
    	notificacion.setGestorSeycon(this.ctx.getCallerPrincipal().getName());

    	// Obtenemos datos de representante/representado
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	Iterator it =asiento.getDatosInteresado().iterator();
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			if (StringUtils.isNotEmpty(di.getUsuarioSeycon())){
    				notificacion.setUsuarioSeycon(di.getUsuarioSeycon());
    			}
    			notificacion.setNifRepresentante(di.getNumeroIdentificacion());
    			notificacion.setNombreRepresentante(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			notificacion.setNifRepresentado(di.getNumeroIdentificacion());
    			notificacion.setNombreRepresentado(di.getIdentificacionInteresado());
    		}
    	}

    	// Obtenemos documentos: datos aviso, datos oficio y documentos anexos
    	OficioRemision oficioRemision = null;
    	int index=0;
    	it = asiento.getDatosAnexoDocumentacion().iterator();
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());

    		switch (da.getTipoDocumento().charValue()){
    			// Datos aviso notificacion: establecemos datos aviso en notificacion
    			case ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION:
    				// Capturamos el titulo de aviso como descripción de la notificación
    				FactoriaObjetosXMLAvisoNotificacion factoria = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
    				AvisoNotificacion avisoNotificacion = factoria.crearAvisoNotificacion(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));

    				// Obtenemos expediente asociado a la notificacion
    				ExpedienteDelegate ed = DelegateUtil.getExpedienteDelegate();
    				expe = ed.obtenerExpedienteReal(Long.parseLong(avisoNotificacion.getExpediente().getUnidadAdministrativa()),avisoNotificacion.getExpediente().getIdentificadorExpediente(), avisoNotificacion.getExpediente().getClaveExpediente());
    				if (expe == null){
    					throw new Exception("No existe expediente indicado en la notificacion: " + avisoNotificacion.getExpediente().getIdentificadorExpediente() + " - " + avisoNotificacion.getExpediente().getUnidadAdministrativa());
    				}
    				if (expe.getClaveExpediente() != null && !expe.getClaveExpediente().equals(avisoNotificacion.getExpediente().getClaveExpediente())){
    					throw new Exception("Clave de acceso al expediente incorrecta: " + avisoNotificacion.getExpediente().getIdentificadorExpediente() + " - " + avisoNotificacion.getExpediente().getUnidadAdministrativa());
    				}

    				// Solamente dejamos generar notificaiones sobre expedientes con nif
    				if (StringUtils.isBlank(expe.getNifRepresentante())) {
    					throw new Exception("No se pueden generar notificaciones sobre expedientes sin nif asociado");
    				}

    				// El nif del expediente y destinatario de la notificacion deben coincidir
    				if (!expe.getNifRepresentante().equals(notificacion.getNifRepresentante())) {
    						throw new Exception("No concuerda nif indicado en la notificacion con el del expediente");
    				}

    				// Establecemos datos
    				notificacion.setCodigoRdsAviso(ref.getCodigo());
    				notificacion.setClaveRdsAviso(ref.getClave());
    				notificacion.setTituloAviso(avisoNotificacion.getTitulo().length()<500?avisoNotificacion.getTitulo():avisoNotificacion.getTitulo().substring(0,500));
    				notificacion.setFirmarAcuse(avisoNotificacion.getAcuseRecibo().booleanValue());
    				notificacion.setDiasPlazo(avisoNotificacion.getPlazo());
    				if (avisoNotificacion.getAccesiblePorClave() != null) {
    					notificacion.setAccesiblePorClave(avisoNotificacion.getAccesiblePorClave().booleanValue());
    				} else {
    					// COMPATIBILIDAD CON VERSIONES ANTERIORES A 2.1: EXPE ANONIMOS GENERAN SIEMPRE NOTIFS ACCESIBLES POR CLAVE
    					// Si no se especifica si es accesible por clave, por compatibilidad será accesible si expe es anónimo
    					notificacion.setAccesiblePorClave(expe.getSeyconCiudadano() == null);
    				}
    				break;

    		    //  Datos oficio remision: establecemos datos oficio en notificacion
    			case ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION:
    				FactoriaObjetosXMLOficioRemision factoriaOR = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
    				oficioRemision = factoriaOR.crearOficioRemision (new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));

    				notificacion.setCodigoRdsOficio(ref.getCodigo());
    				notificacion.setClaveRdsOficio(ref.getClave());
    				if (oficioRemision.getTramiteSubsanacion() != null){
    					// Verificamos datos obligatorios
    					if (StringUtils.isEmpty(oficioRemision.getTramiteSubsanacion().getDescripcionTramite())){
    						throw new Exception("No se ha indicado la descripcion del tramite de subsanacion");
    					}
    					if (StringUtils.isEmpty(oficioRemision.getTramiteSubsanacion().getIdentificadorTramite())){
    						throw new Exception("No se ha indicado el id del tramite de subsanacion");
    					}
    					if (oficioRemision.getTramiteSubsanacion().getVersionTramite() == null){
    						throw new Exception("No se ha indicado la version del tramite de subsanacion");
    					}
    					// Establecemos datos
    					notificacion.setTramiteSubsanacionDescripcion(oficioRemision.getTramiteSubsanacion().getDescripcionTramite());
    					notificacion.setTramiteSubsanacionIdentificador(oficioRemision.getTramiteSubsanacion().getIdentificadorTramite());
    					notificacion.setTramiteSubsanacionVersion(oficioRemision.getTramiteSubsanacion().getVersionTramite());
    					notificacion.setTramiteSubsanacionParametros(StringUtil.serializarMap(oficioRemision.getTramiteSubsanacion().getParametrosTramite()));
    				}

    				break;
    			// Anexo asociado a notificacion
    			default:
    	    		DocumentoNotificacionTelematica doc = new DocumentoNotificacionTelematica();
	        		doc.setOrden(new Integer(index));
	        		doc.setNotificacionTelematica(notificacion);
	        		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
	        		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
	        		doc.setDescripcion(da.getExtractoDocumento());
	        		doc.setCodigoRDS(ref.getCodigo());
	        		doc.setClaveRDS(ref.getClave());

	        		notificacion.addDocumento(doc);
	        		index++;
    		}

    	}

    	// Verificamos que el expediente sea real y no virtual
    	if (!Expediente.TIPO_EXPEDIENTE_REAL.equals(expe.getTipoExpediente())) {
    		throw new Exception("El expediente no existe (es virtual)");
    	}

    	// Verificamos que tenga activados los avisos el expediente si estan activados como obligatorios para las notifs con acuse
    	if (AVISOS_OBLIG_NOTIF && !"S".equals(expe.getHabilitarAvisos())) {
    		throw new Exception("El expediente no tiene habilitado los avisos");
    	}

    	// Establecemos datos notificacion
    	notificacion.setNumeroRegistro(asiento.getDatosOrigen().getNumeroRegistro());
    	notificacion.setFechaRegistro(justificante.getFechaRegistro());

    	notificacion.setCodigoRdsAsiento(refAsiento.getCodigo());
    	notificacion.setClaveRdsAsiento(refAsiento.getClave());
    	notificacion.setCodigoRdsJustificante(refJustificante.getCodigo());
    	notificacion.setClaveRdsJustificante(refJustificante.getClave());

    	notificacion.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	notificacion.setNumeroRegistro(justificante.getNumeroRegistro());

    	// En caso de que se controle la entrega de la notificacioncalculamos fin de plazo
    	boolean controlEntregaHabilitada = "true".equals(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("notificaciones.controlEntrega.habilitar"));
		if ( notificacion.isFirmarAcuse() &&  controlEntregaHabilitada) {
			int plazo = 10; // Plazo por defecto
			if (notificacion.getDiasPlazo() != null) {
				plazo = notificacion.getDiasPlazo().intValue();
			}
			Date finPlazo = CalendarioUtil.getInstance().calcularPlazoFinNotificacion(new Date(), plazo, true, false);
    		notificacion.setFechaFinPlazo(finPlazo);
    	}

    	// Identificador persistencia
    	notificacion.setIdentificadorPersistencia(GeneradorId.generarId());

    	// Guardamos en log de notificacion telematica
    	NotificacionTelematicaDelegate td = DelegateUtil.getNotificacionTelematicaDelegate();
    	Long codigoNotificacion = td.grabarNuevaNotificacionTelematica(notificacion);
    	notificacion.setCodigo(codigoNotificacion);

    	// Creamos elemento expediente asociado a la notificacion y actualizamos expediente
    	ElementoExpediente el = new ElementoExpediente();
    	el.setExpediente(expe);
    	el.setTipoElemento(ElementoExpediente.TIPO_NOTIFICACION);
    	el.setFecha(notificacion.getFechaRegistro());
    	el.setCodigoElemento(notificacion.getCodigo());
    	el.setIdentificadorPersistencia(notificacion.getIdentificadorPersistencia());
    	el.setAccesoAnonimoExpediente(notificacion.isAccesiblePorClave());
    	expe.addElementoExpediente(el,notificacion);
    	expe.setFechaConsulta(null);
    	DelegateUtil.getExpedienteDelegate().grabarExpedienteReal(expe);

    	// Realizamos aviso de movilidad y actualizamos notificacion
    	DelegateUtil.getProcesosAutoDelegate().avisoCreacionElementoExpediente(el);

    	// Si no requiere acuse generamos indices de busqueda. Si requiere acuse se indexara en el momento de firmar el acuse
    	if (StringUtils.isNotEmpty(notificacion.getUsuarioSeycon()) && !notificacion.isFirmarAcuse()) {
    		 // Crea los indices para la entrada
    		crearIndicesSalida(notificacion, oficioRemision);
    	}

    	// Generamos log de auditoria
    	this.logEvento(ConstantesAuditoria.EVENTO_NOTIFICACION, null, expe.getUsuarioSeycon(), expe.getNifRepresentante(),
    			null, notificacion.getIdioma(), "S", null, null, null, expe.getIdProcedimiento(),null, null);

    }


	/**
     * Crea entrada en el log de preregistro
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadEntradaPreregistro(Justificante justificante,ReferenciaRDS refAsiento,  ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{
    	AsientoRegistral asiento = justificante.getAsientoRegistral();

    	EntradaPreregistro entrada = new EntradaPreregistro();
    	entrada.setTipo(asiento.getDatosOrigen().getTipoRegistro().charValue());


    	// Obtenemos informacion de representante/representado
    	Iterator it = asiento.getDatosInteresado().iterator();
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			entrada.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			entrada.setUsuario(di.getUsuarioSeycon());
    			entrada.setNifRepresentante(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentante(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			entrada.setNifRepresentado(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentado(di.getIdentificacionInteresado());
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
    			entrada.setNifDelegado(di.getNumeroIdentificacion());
    			entrada.setNombreDelegado(di.getIdentificacionInteresado());
    		}
    	}

    	// Establecemos datos de la entrada
    	entrada.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	entrada.setCodigoRdsAsiento(refAsiento.getCodigo());
    	entrada.setClaveRdsAsiento(refAsiento.getClave());
    	entrada.setCodigoRdsJustificante(refJustificante.getCodigo());
    	entrada.setClaveRdsJustificante(refJustificante.getClave());
    	entrada.setIdioma(asiento.getDatosAsunto().getIdiomaAsunto());
    	entrada.setTramite(StringUtil.getModelo(asiento.getDatosAsunto().getIdentificadorTramite()));
    	entrada.setVersion(new Integer(StringUtil.getVersion(asiento.getDatosAsunto().getIdentificadorTramite())));

    	entrada.setNumeroPreregistro(justificante.getNumeroRegistro());
    	entrada.setFecha(justificante.getFechaRegistro());

    	// Documentos asiento
    	String idPersistencia = null;
    	Iterator itd = asiento.getDatosAnexoDocumentacion().iterator();
    	DatosPropios datosPropios = null;
    	HashMap docsPre = new HashMap(); // Hash auxiliar para asociar documentos asiento con documentos presenciales
    	while (itd.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) itd.next();
    		DocumentoEntradaPreregistro doc = new DocumentoEntradaPreregistro();
    		doc.setEntradaPreregistro(entrada);
    		doc.setPresencial('N');
    		doc.setDescripcion(da.getExtractoDocumento());
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());
    		doc.setClaveRDS(ref.getClave());

    		entrada.addDocumento(doc);

    		// Si el documento es el de datos propios obtenemos id persistencia

    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
    			// Obtenemos id persistencia
    			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
    			datosPropios = factoria.crearDatosPropios(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));
    			idPersistencia = datosPropios.getInstrucciones().getIdentificadorPersistencia();

    		}

    		docsPre.put(da.getIdentificadorDocumento(),doc);
    	}

    	// Comprobacion de que exista documento de datos propios
    	if (datosPropios == null) throw new Exception("No se encuentra documento de datos propios");

    	// Comprobamos que se haya establecido el identificador de persistencia en el documento de datos propios
    	if (StringUtils.isEmpty(idPersistencia)){
    		throw new Exception("No se ha establecido el identificador de persistencia en el documento de datos propios");
    	}
    	entrada.setIdPersistencia(idPersistencia);

    	// Procedimiento
    	entrada.setProcedimiento(datosPropios.getInstrucciones().getIdentificadorProcedimiento());

    	// Fecha limite de entrega
    	entrada.setFechaCaducidad(datosPropios.getInstrucciones().getFechaTopeEntrega());

    	// Configuracion de avisos
    	entrada.setHabilitarAvisos(datosPropios.getInstrucciones().getHabilitarAvisos());
    	entrada.setAvisoEmail(datosPropios.getInstrucciones().getAvisoEmail());
    	entrada.setAvisoSMS(datosPropios.getInstrucciones().getAvisoSMS());

    	// Configuracion notificacion telematica
    	entrada.setHabilitarNotificacionTelematica(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica());

    	// Documentos presenciales
    	for (it = datosPropios.getInstrucciones().getDocumentosEntregar().getDocumento().iterator();it.hasNext();){
    		Documento docPres = (Documento) it.next();
    		// Obviamos el justificante, ya que en la entrada se establecera la referencia y clave RDS de dicho justificante
    		if ( docPres.getTipo().charValue() ==  ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE )
    		{
    			continue;
    		}

    		// Comprobamos si esta asociado a un documento presentado en el asiento
    		// Si no esta asociado creamos nuevo documento
    		DocumentoEntradaPreregistro doc = null;
    		if (docPres.getIdentificador() != null ) {
    			doc = (DocumentoEntradaPreregistro) docsPre.get(docPres.getIdentificador());
    		}

    		if (doc == null) {
    			doc = new DocumentoEntradaPreregistro();
    			doc.setDescripcion( docPres.getTitulo() );
    			doc.setEntradaPreregistro(entrada);
    			if (docPres.getIdentificador() != null ){
    				doc.setIdentificador(StringUtil.getModelo(docPres.getIdentificador()));
    	    		doc.setNumeroInstancia(StringUtil.getVersion(docPres.getIdentificador()));
    			}
    		}

    		// Establecemos parametros presenciales
    		doc.setPresencial('S');
    		doc.setTipoDocumento(docPres.getTipo());
    		doc.setCompulsarDocumento((docPres.isCompulsar().booleanValue()?new Character('S'):new Character('N')));
    		doc.setFirma((docPres.isFirmar().booleanValue()?new Character('S'):new Character('N')));
    		doc.setFotocopia((docPres.isFotocopia().booleanValue()?new Character('S'):new Character('N')));

    		entrada.addDocumento(doc);
    	}

    	// Si es un tramite de subsanacion, creamos elemento expediente asociado y actualizamos expediente
    	TramiteSubsanacion tramiteSubsanacion = null;
    	if( datosPropios.getInstrucciones().getTramiteSubsanacion() != null){
    		tramiteSubsanacion = datosPropios.getInstrucciones().getTramiteSubsanacion();
    		entrada.setSubsanacionExpedienteCodigo(tramiteSubsanacion.getExpedienteCodigo());
    		entrada.setSubsanacionExpedienteUA(tramiteSubsanacion.getExpedienteUnidadAdministrativa());
    	}

    	// Alertas de tramitacion
    	if( datosPropios.getInstrucciones().getAlertasTramitacion() != null){
    		AlertasTramitacion alertas = datosPropios.getInstrucciones().getAlertasTramitacion();
    		entrada.setAlertasTramitacionGenerar("S");
    		entrada.setAlertasTramitacionEmail(alertas.getEmail());
    		entrada.setAlertasTramitacionSms(alertas.getSms());
    	}

    	// Guardamos en log de entrada preregistro
    	EntradaPreregistroDelegate td = DelegateUtil.getEntradaPreregistroDelegate();
    	Long codEntrada = td.grabarNuevaEntradaPreregistro(entrada);
    	entrada.setCodigo(codEntrada);


    	// Si es un tramite de subsanacion actualizamos expediente
    	if (tramiteSubsanacion != null){
    		DelegateUtil.getProcesosAutoDelegate().actualizarExpedienteTramiteSubsanacion(entrada.getCodigo(),ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
    	} else {
        	// Si no esta asociado a expediente, creamos expediente virtual
    		Expediente expe = new Expediente();
    		expe.setTipoExpediente(Expediente.TIPO_EXPEDIENTE_VIRTUAL);
    		expe.setUnidadAdministrativa(new Long(justificante.getAsientoRegistral().getDatosAsunto().getCodigoUnidadAdministrativa()));
    		expe.setIdExpediente(entrada.getIdPersistencia());
    		expe.setIdioma(entrada.getIdioma());
    		expe.setDescripcion(entrada.getDescripcionTramite());
    		expe.setFecha(new Timestamp(entrada.getFecha().getTime()));
    		expe.setFechaInicio(expe.getFecha());
    		expe.setHabilitarAvisos("N");
    		expe.setUsuarioSeycon(entrada.getUsuario());
    		expe.setSeyconCiudadano(entrada.getUsuario());
    		if (entrada.getFechaConfirmacion() != null) {
    			expe.setFechaFin(new Timestamp(entrada.getFechaConfirmacion().getTime()));
    			expe.setFechaConsulta(new Timestamp(entrada.getFechaConfirmacion().getTime()));
    			expe.setEstado(ConstantesZPE.ESTADO_SOLICITUD_ENVIADA);
    		} else {
    			expe.setFechaFin(expe.getFecha());
    			expe.setFechaConsulta(expe.getFecha());
    			expe.setEstado(ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL);
    		}
    		expe.setNifRepresentante(entrada.getNifRepresentante());
    		expe.setNifRepresentado(entrada.getNifRepresentado());
    		expe.setNombreRepresentado(entrada.getNombreRepresentado());
    		expe.setIdProcedimiento(entrada.getProcedimiento());

    		ElementoExpediente eleExpe = new ElementoExpediente();
    		eleExpe.setFecha(entrada.getFecha());
    		eleExpe.setTipoElemento(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
    		eleExpe.setCodigoElemento(entrada.getCodigo());
    		eleExpe.setIdentificadorPersistencia(entrada.getIdPersistencia());
    		eleExpe.setAccesoAnonimoExpediente(entrada.getNivelAutenticacion() == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO );
    		expe.addElementoExpediente(eleExpe, entrada);

    		DelegateUtil.getExpedienteDelegate().grabarExpedienteVirtual(expe);
    	}

    	// Generamos indices de busqueda (solo para autenticados)
    	if (entrada.getNivelAutenticacion() != 'A') {
    		 // Crea los indices para la entrada
    		crearIndicesEntrada(entrada, datosPropios);
    	}

    	// Generamos email de tramite finalizado
    	if (datosPropios.getInstrucciones().getAlertasTramitacion() != null && StringUtils.isNotBlank(datosPropios.getInstrucciones().getAlertasTramitacion().getEmail())) {
    		// Capturamos posible excepcion xa q no interfiera en proceso registro
    		try {
    			DelegateUtil.getProcesosAutoDelegate().alertaTramitacionTramiteRealizado(entrada, datosPropios.getInstrucciones().getAlertasTramitacion().getEmail());
    		} catch (Exception ex) {
    			log.error("No se ha podido realizar el aviso de tramite finalizado. Se continua con proceso registro.", ex);
    		}
    	}

    	// Generamos log de auditoria
    	this.logEvento(ConstantesAuditoria.EVENTO_PREREGISTRO_TRAMITE, String.valueOf(entrada.getNivelAutenticacion()), entrada.getUsuario(), entrada.getNifRepresentante(),
    			entrada.getNombreRepresentante(), entrada.getIdioma(), "S", entrada.getDescripcionTramite(), entrada.getTramite(), entrada.getVersion(), entrada.getProcedimiento(),
    			entrada.getIdPersistencia(), entrada.getTipo() == ConstantesAsientoXML.TIPO_PREENVIO?"B":"R");

    }

    /**
     * Convierte TramitePersistente en TramitePersistentePAD
     * @param t
     * @return
     */
    private TramitePersistentePAD tramitePersistenteToTramitePersistentePAD(TramitePersistente t) throws ExcepcionPAD{
    	try{
    		if (t == null) {
    			return null;
    		}

	    	TramitePersistentePAD tpad = new TramitePersistentePAD();
	    	tpad.setIdPersistencia(t.getIdPersistencia());
	    	tpad.setTramite(t.getTramite());
	    	tpad.setVersion(t.getVersion());
	    	tpad.setDescripcion( t.getDescripcion() );
	    	tpad.setIdProcedimiento(t.getIdProcedimiento());
	    	tpad.setNivelAutenticacion(t.getNivelAutenticacion());
	    	tpad.setUsuario(t.getUsuario());
	    	tpad.setUsuarioFlujoTramitacion(t.getUsuarioFlujoTramitacion());
	    	tpad.setFechaCreacion(t.getFechaCreacion());
	    	tpad.setFechaModificacion(t.getFechaModificacion());
	    	tpad.setFechaCaducidad(t.getFechaCaducidad());
	    	tpad.setIdioma(t.getIdioma());
	    	tpad.setParametrosInicio(t.getParametrosInicioMap());
	    	tpad.setDelegado(t.getDelegado());
	    	tpad.setEstadoDelegacion(t.getEstadoDelegacion());

	    	tpad.setAlertasTramitacionGenerar(t.getAlertasTramitacionGenerar());
	    	tpad.setAlertasTramitacionFinAuto(t.getAlertasTramitacionFinAuto());
	    	tpad.setAlertasTramitacionEmail(t.getAlertasTramitacionEmail());
	    	tpad.setAlertasTramitacionSms(t.getAlertasTramitacionSms());

	    	for (Iterator it = t.getDocumentos().iterator();it.hasNext();){
	    		DocumentoPersistente d = (DocumentoPersistente) it.next();
	    		DocumentoPersistentePAD dpad = new DocumentoPersistentePAD();

	    		dpad.setIdentificador(d.getIdentificador());
	    		dpad.setNumeroInstancia(d.getNumeroInstancia());
	    		dpad.setEstado(d.getEstado());
	    		dpad.setNombreFicheroAnexo(d.getNombreFicheroAnexo());
	    		dpad.setDescripcionGenerico(d.getDescripcionGenerico());

	    		if (d.getRdsClave() != null && d.getRdsCodigo() != null){
	    			ReferenciaRDS ref = new ReferenciaRDS();
	    			ref.setCodigo(d.getRdsCodigo().longValue());
	    			ref.setClave(d.getRdsClave());
	    			dpad.setReferenciaRDS(ref);
	    		}

	    		dpad.setDelegacionEstado(d.getDelegacionEstado());
	    		dpad.setDelegacionFirmantes(d.getDelegacionFirmantes());
	    		dpad.setDelegacionFirmantesPendientes(d.getDelegacionFirmantesPendientes());

	    		dpad.setTipoDocumento(d.getTipoDocumento());
	    		dpad.setEsPagoTelematico(d.getEsPagoTelematico());

	    		tpad.getDocumentos().put(dpad.getIdentificador() + "-"+d.getNumeroInstancia(),dpad);
	    	}
	    	return tpad;
    	}catch (Exception e){
    		throw new ExcepcionPAD("No se ha podido convertir TramitePersistente en TramitePersistentePAD",e);
    	}
    }


    /**
     * Convierte TramitePersistentePAD en TramitePersistente
     * @param t
     * @return
     */
    private TramitePersistente tramitePersistentePADToTramitePersistente(TramitePersistentePAD tpad) throws ExcepcionPAD{
    	try{

    		TramitePersistenteDelegate td;
    		try{
        		td = DelegateUtil.getTramitePersistenteDelegate();
        	}catch (Exception ex){
        		throw new ExcepcionPAD("No se ha podido obtener delegado",ex);
        	}

        	// Comprobamos si es nuevo
        	TramitePersistente t;
    		Timestamp ahora = new Timestamp(System.currentTimeMillis());
    		if (tpad.getIdPersistencia() != null){
    			// Si no es nuevo recuperamos tramite y eliminamos documentos anteriores
    			td.borrarDocumentosTramitePersistente(tpad.getIdPersistencia());
    			t = td.obtenerTramitePersistente(tpad.getIdPersistencia());

    			// Comprobamos que tramite no haya variado
    			if (!t.getTramite().equals(tpad.getTramite())) throw new ExcepcionPAD("El tramite no coincide");
    			if (t.getVersion() != tpad.getVersion()) throw new ExcepcionPAD("La versión no coincide");
    			if (t.getNivelAutenticacion() != tpad.getNivelAutenticacion()) throw new ExcepcionPAD("El nivel de autenticación no coincide");

    			if (!t.getFechaModificacion().equals(tpad.getFechaModificacion()))  throw new ExcepcionPAD("Se ha modificado el trámite en otra sesión");

    		}else{
    			// Si es nuevo creamos objeto
    			t = new TramitePersistente();
    			// Establecemos datos tramite
    			t.setTramite(tpad.getTramite());
    	    	t.setVersion(tpad.getVersion());
    	    	t.setDescripcion( tpad.getDescripcion());
    	    	t.setNivelAutenticacion(tpad.getNivelAutenticacion());
    	    	t.setUsuario(tpad.getUsuario());
    	    	t.setFechaCreacion(ahora);
    	    	t.setIdioma(tpad.getIdioma());
    	    	t.setParametrosInicioMap(tpad.getParametrosInicio());
    	    	t.setIdProcedimiento(tpad.getIdProcedimiento());
    	    	t.setPersistente(tpad.getPersistente());
    		}

    		// Establecemos usuario que tiene actualmente el trámite
    		t.setUsuarioFlujoTramitacion(tpad.getUsuarioFlujoTramitacion());

    		// En caso de delegacion establecemos delegado
    		t.setDelegado(tpad.getDelegado());
    		t.setEstadoDelegacion(tpad.getEstadoDelegacion());

    		// Establecemos fecha modificacion y caducidad
    		t.setFechaModificacion(ahora);
    		t.setFechaCaducidad(tpad.getFechaCaducidad());

    		// Establecemos alertas
    		t.setAlertasTramitacionGenerar(tpad.getAlertasTramitacionGenerar());
    		t.setAlertasTramitacionFinAuto(tpad.getAlertasTramitacionFinAuto());
    		t.setAlertasTramitacionEmail(tpad.getAlertasTramitacionEmail());
    		t.setAlertasTramitacionSms(tpad.getAlertasTramitacionSms());

    		// Establecemos documentos
	    	for (Iterator it = tpad.getDocumentos().keySet().iterator();it.hasNext();){
	    		String ls_key = (String) it.next();
	    		DocumentoPersistentePAD dpad = (DocumentoPersistentePAD) tpad.getDocumentos().get(ls_key);
	    		DocumentoPersistente d = new DocumentoPersistente();

	    		d.setIdentificador(dpad.getIdentificador());
	    		d.setNumeroInstancia(dpad.getNumeroInstancia());
	    		d.setEstado(dpad.getEstado());
	    		d.setNombreFicheroAnexo(dpad.getNombreFicheroAnexo());
	    		d.setDescripcionGenerico(dpad.getDescripcionGenerico());

	    		if (dpad.getRefRDS() != null ){
	    			d.setRdsCodigo(new Long(dpad.getRefRDS().getCodigo()));
	    			d.setRdsClave(dpad.getRefRDS().getClave());
	    		}

	    		d.setDelegacionEstado(dpad.getDelegacionEstado());
	    		d.setDelegacionFirmantes(dpad.getDelegacionFirmantes());
	    		d.setDelegacionFirmantesPendientes(dpad.getDelegacionFirmantesPendientes());

	    		d.setTipoDocumento(dpad.getTipoDocumento());
	    		d.setEsPagoTelematico(dpad.getEsPagoTelematico());

	    		t.addDocumento(d);
	    	}
	    	return t;
	    }catch (Exception e){
			throw new ExcepcionPAD("No se ha podido convertir TramitePersistente en TramitePersistentePAD",e);
		}
    }




    /**
     * Comprobaciones sobre tramite persistente
     * @param obj
     * @throws ExcepcionPAD
     */
    private void comprobarTramitePersistentePAD(TramitePersistentePAD obj) throws ExcepcionPAD{
	    	// Realizamos comprobaciones
	    	switch (obj.getNivelAutenticacion()){
	    		case TramitePersistentePAD.AUTENTICACION_ANONIMO:
	    			if (obj.getUsuario() != null || obj.getUsuarioFlujoTramitacion() != null){
	    				throw new ExcepcionPAD("Si el nivel de autenticación es anónimo no se puede indicar usuario");
	    			}
	    			break;
	    		case TramitePersistentePAD.AUTENTICACION_CERTIFICADO:
	    		case TramitePersistentePAD.AUTENTICACION_USUARIOPASSWORD:
	    			if (obj.getUsuario() == null || obj.getUsuarioFlujoTramitacion() == null) {
	    				throw new ExcepcionPAD("El nivel de autenticación requiere indicar usuario");
	    			}
	    			break;
	    		default:
	    			throw new ExcepcionPAD("Nivel de autenticación no definido (" + obj.getNivelAutenticacion() + ")");
	    	}
    }

    private ReferenciaRDS construirReferenciaRDS( long codigo, String clave )
    {
    	ReferenciaRDS referenciaRDS = new ReferenciaRDS();
		referenciaRDS.setCodigo( codigo );
		referenciaRDS.setClave( clave );
		return referenciaRDS;
    }

    private Map construirMapReferenciasRDSDocsEntPrerr( Set setDocumentosEntradaPrerregistro )
    {
    	Map mReturn = new HashMap();
    	for ( Iterator it = setDocumentosEntradaPrerregistro.iterator(); it.hasNext(); )
    	{
    		DocumentoEntradaPreregistro documento = ( DocumentoEntradaPreregistro ) it.next();
    		if (documento.getCodigoRDS() > 0 && documento.getClaveRDS() != null){
    			mReturn.put( documento.getIdentificador() + "-" + documento.getNumeroInstancia(), construirReferenciaRDS ( documento.getCodigoRDS(), documento.getClaveRDS()  ) );
    		}
    	}
    	return mReturn;
    }

	private byte[] consultarDocumentoRDS( long codigo, String clave ) throws DelegateException
	{
		ReferenciaRDS referenciaRDS = construirReferenciaRDS( codigo, clave );
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );
		return documentoRDS.getDatosFichero();
	}

	 /**
	  * Realiza log
	  * @param nivelAutenticacion
	  * @param seyconUser
	  * @param idDocumentoIdPersonal
	  * @param nombre
	  * @param lang
	  * @param result
	  * @param descripcion
	  * @throws Exception
	  */
	 private void logEvento(
			 String evento,String nivelAutenticacion, String seyconUser, String idDocumentoIdPersonal,
			 String nombre, String lang, String result, String descripcion,String modeloTramite,Integer versionTramite,
			 String procedimiento, String idPersistencia, String clave ) throws Exception
	{
		try{
			Evento eventoAuditado = new Evento();
			eventoAuditado.setTipo( evento );
			if (nivelAutenticacion != null) {
				eventoAuditado.setNivelAutenticacion(nivelAutenticacion);
			}
			eventoAuditado.setUsuarioSeycon( seyconUser );
			eventoAuditado.setNumeroDocumentoIdentificacion( idDocumentoIdPersonal );
			eventoAuditado.setNombre( nombre );
			eventoAuditado.setDescripcion( descripcion );
			eventoAuditado.setIdioma( lang );
			eventoAuditado.setResultado( result );
			eventoAuditado.setModeloTramite(modeloTramite);
			if (versionTramite != null) {
				eventoAuditado.setVersionTramite(versionTramite.intValue());
			}
			eventoAuditado.setProcedimiento(procedimiento);
			eventoAuditado.setIdPersistencia(idPersistencia);
			eventoAuditado.setClave(clave);
			DelegateAUDUtil.getAuditaDelegate().logEvento( eventoAuditado, false );
		}catch(Exception ex){
			log.error("Excepción auditando evento: " + ex.getMessage(),ex);
		}
	}

	/**
	 * Genera usos RDS en confirmacion preregistro para asiento, justificante y documentos asociados
	 */
	private void generarUsosRDS(String tipoUso,AsientoRegistral asientoXML,Map referenciasRDS,
			ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,
			String numero) throws Exception{

		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		UsoRDS uso;

		// Creamos uso para asiento
		uso = new UsoRDS();
		uso.setReferenciaRDS(refAsiento);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);
		rds.crearUso(uso);

		// Creamos uso para justificante
		uso = new UsoRDS();
		uso.setReferenciaRDS(refJustificante);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);
		rds.crearUso(uso);

		// Creamos usos para documentos asiento
		Iterator it = asientoXML.getDatosAnexoDocumentacion().iterator();
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
			uso.setReferenciaRDS((ReferenciaRDS) referenciasRDS.get(da.getIdentificadorDocumento()));
			uso.setReferencia(numero);
			uso.setTipoUso(tipoUso);
			rds.crearUso(uso);
    	}

	}


	// Realiza proceso de confirmacion de registro teniendo en cuenta si debe hacer registro o no (para confirmacion de incorrectos)
	private void confirmarPreregistroImpl(EntradaPreregistro entrada, String tipoConfirmacionPreregistro, String entidad, String oficina, String codProvincia,String codMunicipio, String descMunicipio, String numeroRegistroPI,Date fechaRegistroPI) throws ExcepcionPAD
    {
    	ResultadoRegistro resultadoRegistro = null;
    	boolean exitoProceso = false;
    	try
    	{
	    	// Creamos referencias RDS
	    	ReferenciaRDS refRDSAsiento 		= construirReferenciaRDS( entrada.getCodigoRdsAsiento(), entrada.getClaveRdsAsiento() );
			ReferenciaRDS refRDSJustificante 	= construirReferenciaRDS( entrada.getCodigoRdsJustificante(), entrada.getClaveRdsJustificante() );
			Map mapRefRDSDocumentos 			= construirMapReferenciasRDSDocsEntPrerr( entrada.getDocumentos() );

	    	// Leemos asiento para obtener código / versión trámite
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRDS = rds.consultarDocumento(refRDSJustificante);
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			Justificante justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (docRDS.getDatosFichero()));
			AsientoRegistral asiento = justificante.getAsientoRegistral();

	    	// En caso de ser una confirmación de un preregistro normal generamos registro de entrada
			String numeroRegistro;
			Date fechaRegistro;
			String oficinaRegistroPresencial = null;

			/**
			 * Confirmacion en registro presencial:
			 * 	- tanto para preregistros como para preenvios, se hará una llamada al sistema de registro
			 *  - tambien para preenvios confirmados automaticamente
			 */
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) ||
				tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO) ){
			    	resultadoRegistro = DelegateRegtelUtil.getRegistroTelematicoDelegate().confirmarPreregistro(entidad, oficina, codProvincia,codMunicipio,descMunicipio,justificante,refRDSJustificante,refRDSAsiento,mapRefRDSDocumentos);
					numeroRegistro = resultadoRegistro.getNumeroRegistro();
					fechaRegistro = StringUtil.cadenaAFecha( resultadoRegistro.getFechaRegistro(), StringUtil.FORMATO_REGISTRO );
					oficinaRegistroPresencial = oficina;
			}else{
					// En caso de ser una confirmación de un preregistro incorrecto no generamos registro de entrada
					// En este caso si se pasa el numero/fecha registro la alimentamos si no establecemos el num. preregistro y
					// la fecha actual
					if (numeroRegistroPI!=null && fechaRegistroPI!=null){
						numeroRegistro= numeroRegistroPI;
						fechaRegistro = fechaRegistroPI;
					}else{
						numeroRegistro= entrada.getNumeroPreregistro();
						fechaRegistro = new Date();
					}
			}

	    	// Creamos la entrada de preregistro en la BTE
			BteSistraDelegate bteDelegate = DelegateBTEUtil.getBteSistraDelegate();
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO)){
				bteDelegate.crearEntradaPreregistro( refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR)){
				bteDelegate.crearEntradaPreregistroIncorrecto(refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA)){
				bteDelegate.crearEntradaPreenvioAutomatico(refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
				bteDelegate.confirmacionEntradaPreenvioAutomatico(entrada.getNumeroPreregistro(), numeroRegistro, fechaRegistro );
			}else{
				throw new Exception("Tipo de confirmacion no soportada");
			}

			//  En caso de ser un preregistro incorrecto la fecha de confirmación es diferente a la que se registro
			if (!tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR)){
				entrada.setFechaConfirmacion( new Date() );
			}else{
				entrada.setFechaConfirmacion( fechaRegistro );
			}
			entrada.setNumeroRegistro( numeroRegistro );
			entrada.setOficinaRegistro(oficinaRegistroPresencial);

			// Si la confirmacion es automatica la marcamos
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA)){
				entrada.setConfirmadoAutomaticamente('S');
			}

			EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
			delegate.confirmarEntradaPreregistro(entrada.getCodigo(), entrada.getNumeroRegistro(),
					entrada.getFechaConfirmacion(), oficinaRegistroPresencial,
					tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA),
					tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR));

			// Generamos usos en RDS en caso de que se genere registro de entrada
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) ||
				tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
					generarUsosRDS(ConstantesRDS.TIPOUSO_REGISTROENTRADA,asiento,mapRefRDSDocumentos,refRDSAsiento,refRDSJustificante,numeroRegistro);
			}
			exitoProceso = true;


			// En caso de que pertenezca a un expediente, acualizamos estado expediente
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpedienteDelElementoExpediente(
							ElementoExpediente.TIPO_ENTRADA_PREREGISTRO,
							entrada.getCodigo());



			// En caso de confirmarse por registro de entrada añadimos el indice de num de registro (solo autenticados)
			if ( entrada.getNivelAutenticacion() != 'A' &&
					(
						tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) ||
						tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)
				 	)
				){
					Map indices = new HashMap();
					indices.put("Número registro", numeroRegistro);
					crearIndicesBusqueda(entrada.getNifRepresentante(), IndiceElemento.TIPO_ENTRADA_PREREGISTRO, entrada.getCodigo(), indices);
			}


			// Realizamos log en AUDITA
			//	Si es la confirmacion de un preenvio con confirmacion automatica no lo auditamos ya que se audito al hacerlo automaticamente
			if (!tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
				PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
				Principal sp = this.ctx.getCallerPrincipal();
				String nif = plgLogin.getNif(sp);
				char metodoAut = plgLogin.getMetodoAutenticacion( sp );
				String nombre = plgLogin.getNombreCompleto(sp);
				logEvento(ConstantesAuditoria.EVENTO_CONFIRMACION_PREREGISTRO, String.valueOf(metodoAut),sp.getName(),
						(StringUtils.isNotEmpty(nif)?nif.toUpperCase():null),nombre,entrada.getIdioma(), "S","Preregistro confirmado: " + entrada.getNumeroPreregistro(),
						StringUtil.getModelo(asiento.getDatosAsunto().getIdentificadorTramite()),new Integer(StringUtil.getVersion(asiento.getDatosAsunto().getIdentificadorTramite())),
						entrada.getProcedimiento(), null, null);
			}

    	}catch (Exception ex){
    		log.error( "Excepcion que causa la no confirmacion del preregistro", ex );
    		throw new ExcepcionPAD("No se ha podido confirmar el preregistro",ex);
    	}
    	finally
    	{
    		// Log de registro de entrada (solo para confirmacion de preregistros normales)
    		if ( 	(tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) || tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO))
    				&& resultadoRegistro != null )
    		{
    			try
    			{
    				LoggerRegistro.logResultadoRegistro( resultadoRegistro.getNumeroRegistro(), resultadoRegistro.getFechaRegistro(), exitoProceso );
    			}
    			catch( Exception exc )
    			{
    				log.error( exc );
    			}
    		}
    	}
    }


	/**
     * Crea los indices de búsqueda para una entrada autenticada (registro, envio, preregistro o preeenvio)
     * @param justificante
     * @param datosPropios
	 * @throws Exception
     */
    private void crearIndicesEntrada(Entrada entrada, DatosPropios datosPropios) throws Exception {

		// Establecemos indices a crear
    	Map indices = new HashMap();

    	// - Número registro / preregistro
    	if (entrada instanceof EntradaTelematica) {
    		indices.put("Número registro",  entrada.getNumeroRegistro());
    	} else if (entrada instanceof EntradaPreregistro) {
    		indices.put("Número preregistro",  entrada.getNumeroPreregistro());
    	}

    	// - Titulo tramite
    	indices.put("Título trámite", entrada.getDescripcionTramite());

    	// - Representado
    	indices.put("Nif representado",  entrada.getNifRepresentado());
    	indices.put("Nombre representado",  entrada.getNombreRepresentado());

    	// - Título documentos aportados
    	Iterator it = entrada.getDocumentos().iterator();
    	while (it.hasNext()){
    		DocumentoEntrada doc = (DocumentoEntrada) it.next();
    		if (!doc.getIdentificador().equals(ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS)) {
    			indices.put(doc.getIdentificador() + " (" + doc.getNumeroInstancia() + ")", doc.getDescripcion());
    		}
    	}

    	// - Datos propios
    	if (datosPropios.getSolicitud() != null &&
        		datosPropios.getSolicitud().getDato() != null ) {
    		it = datosPropios.getSolicitud().getDato().iterator();
    		while (it.hasNext()) {
    			Dato dato = (Dato) it.next ();
    			if (dato.getTipo().charValue() == ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_INDICE ||
    				dato.getTipo().charValue() == ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO) {
    					indices.put(dato.getDescripcion(), dato.getValor());
    			}
    		}
    	}

    	// Creamos indices
    	// 	- Obtenemos nif y tipo elemento
    	String nif = null;
    	String tipoElemento = null;
    	if (entrada instanceof EntradaTelematica) {
    		nif = ((EntradaTelematica) entrada).getNifRepresentante();
    		tipoElemento = IndiceElemento.TIPO_ENTRADA_TELEMATICA;
    	} else if (entrada instanceof EntradaPreregistro) {
    		nif = ((EntradaPreregistro) entrada).getNifRepresentante();
    		tipoElemento = IndiceElemento.TIPO_ENTRADA_PREREGISTRO;
    	}
    	// - Insertamos en bbdd
    	crearIndicesBusqueda(nif, tipoElemento, entrada.getCodigo(), indices);

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
    	indices.put("Número registro",  notificacion.getNumeroRegistro());
    	indices.put("Título oficio", oficioRemision.getTitulo());
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



	private ResumenExpedientePAD expedienteToResumenExpedientePAD( Expediente expediente ) throws ExcepcionPAD
	{
		ResumenExpedientePAD expPAD = new ResumenExpedientePAD();

		expPAD.setIdentificadorExpediente( expediente.getIdExpediente() );
		expPAD.setUnidadAdministrativa( expediente.getUnidadAdministrativa() );
		expPAD.setIdentificadorProcedimiento(expediente.getIdProcedimiento());
		expPAD.setClaveExpediente(expediente.getClaveExpediente());
		expPAD.setIdioma(expediente.getIdioma());
		expPAD.setDescripcion( expediente.getDescripcion() );

		expPAD.setEstado(expediente.getEstado());
		expPAD.setFechaInicio(expediente.getFecha());
		expPAD.setFechaUltimaActualizacion(expediente.getFechaFin());

		if (!StringUtils.isEmpty(expediente.getSeyconCiudadano())){
			expPAD.setAutenticado(true);
			expPAD.setIdentificadorUsuario( expediente.getSeyconCiudadano() );
		}else{
			expPAD.setAutenticado(false);
		}

		expPAD.setNifRepresentante( expediente.getNifRepresentante());
		expPAD.setNifRepresentado(expediente.getNifRepresentado());
		expPAD.setNombreRepresentado(expediente.getNombreRepresentado());

		expPAD.setNumeroEntradaBTE(expediente.getNumeroEntradaBTE());

		expPAD.setIdentificadorGestor(expediente.getUsuarioSeycon());

		if (StringUtils.isNotEmpty(expediente.getHabilitarAvisos())){
			expPAD.getConfiguracionAvisos().setHabilitarAvisos(new Boolean("S".equals(expediente.getHabilitarAvisos())));
			expPAD.getConfiguracionAvisos().setAvisoEmail(expediente.getAvisoEmail());
			expPAD.getConfiguracionAvisos().setAvisoSMS(expediente.getAvisoSMS());
		}


		return expPAD;
	}

}
