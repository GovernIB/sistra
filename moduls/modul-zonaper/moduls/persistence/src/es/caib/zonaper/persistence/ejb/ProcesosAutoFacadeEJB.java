package es.caib.zonaper.persistence.ejb;

import java.rmi.RemoteException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.mobtratel.persistence.delegate.MobTraTelDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.persistence.delegate.SistraDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.sistra.plugins.pagos.ConstantesPago;
import es.caib.sistra.plugins.pagos.EstadoSesionPago;
import es.caib.sistra.plugins.pagos.PluginPagosIntf;
import es.caib.util.CredentialUtil;
import es.caib.util.StringUtil;
import es.caib.xml.pago.XmlDatosPago;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.RegistroExternoPreparado;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.UsuarioAutenticadoInfoPAD;
import es.caib.zonaper.persistence.delegate.BackupDelegate;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.ExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.LogRegistroDelegate;
import es.caib.zonaper.persistence.delegate.PadDelegate;
import es.caib.zonaper.persistence.delegate.ProcesoRechazarNotificacionDelegate;
import es.caib.zonaper.persistence.delegate.ProcesoRevisarRegistrosDelegate;
import es.caib.zonaper.persistence.delegate.RegistroExternoPreparadoDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.AvisoAlertasTramitacion;
import es.caib.zonaper.persistence.util.AvisosExpediente;
import es.caib.zonaper.persistence.util.ConfigurationUtil;
import es.caib.zonaper.persistence.util.GeneradorId;
import es.caib.zonaper.persistence.util.UsernamePasswordCallbackHandler;

/**
 * SessionBean que realiza procesos auto.
 * Los metodos se ejecutaran con el usuario auto
 *
 *
 * @ejb.bean
 *  name="zonaper/persistence/ProcesosAutoFacade"
 *  jndi-name="es.caib.zonaper.persistence.ProcesosAutoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ProcesosAutoFacadeEJB implements SessionBean
{
	private static Log backupLog = LogFactory.getLog( ProcesosAutoFacadeEJB.class );
	private String cuentaSistra;
	private String avisosGestorHabilitado;
	private String queryTiquet;
	private static String URL_CONTINUACION_TRAMITACION = "/sistrafront/protected/init.do";

	private SessionContext context = null;

	public void setSessionContext(SessionContext ctx)
			throws EJBException,
			RemoteException {
					context = ctx;
		}

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {

	}


	/**
	 * Procesa tramites caducados
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void procesaTramitesCaducados() {

		backupLog.debug("Procesa tramites caducados");

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Actualizamos estado expediente
			doProcesaTramitesCaducados();

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}

	/**
	 * Procesa tramites caducados
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void procesaEliminarTramitesBackup() {

		backupLog.debug("Elimina tramites backup");

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Actualizamos estado expediente
			doProcesaEliminarTramitesBackup();

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}



	/**
	 * Actualiza estado de un expediente
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void actualizaEstadoExpediente(Long id)
	{
		backupLog.debug("actualiza estado expediente " + id);

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Actualizamos estado expediente
			doActualizaEstadoExpediente(id);

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}

	/**
	 * Actualiza estado de un expediente a partir de un elemento del expediente.
	 * Si el elemento no pertenece a un expediente no hace nada.
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void actualizaEstadoExpedienteDelElementoExpediente(String tipoElementoExpediente,Long codigoElementoExpediente)
	{
		backupLog.debug("actualiza estado expediente del elemento expediente " + tipoElementoExpediente + " - " + codigoElementoExpediente );

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Obtenemos elemento expediente
			ElementoExpediente elementoExpe = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(tipoElementoExpediente,codigoElementoExpediente);

			// Actualizamos estado expediente asociado
			if (elementoExpe != null){
				doActualizaEstadoExpediente(elementoExpe.getExpediente().getCodigo());
			}

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}


	/**
	 * Genera aviso de creacion de un elemento de un expediente
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public String avisoCreacionElementoExpediente(ElementoExpediente ele) {

		backupLog.debug("aviso creacion elemento expediente");

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Realizamos aviso
			String idEnvio = AvisosExpediente.getInstance().avisoCreacionElementoExpediente(ele);

			// Asociamos aviso al elemento del expediente
			DelegateUtil.getElementoExpedienteDelegate().establecerAvisoElementoExpediente(ele.getCodigo(), idEnvio);

			return idEnvio;
		}catch (LoginException le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}catch (Exception e){
			throw new EJBException("Error realizando aviso creacion elemento expediente con usuario auto",e);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}

	}


	/**
	 * Revisa si los registros efectuados se han consolidado y registros externos que se han preparado para firmar a ver si se han relizado
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void revisarRegistrosEfectuados()
	{
		backupLog.debug("Revisar registros efectuados");

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Revisar registros efectuados
			doRevisarRegistrosEfectuados();

			// Revisar registros externos
			doRevisarRegistrosExternosPreparados();

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}


	/**
	 * Control entrega de las notificaciones
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void controlEntregaNotificaciones()
	{
		backupLog.debug("Control entrega notificaciones");

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Recuperamos notificaciones fuera de plazo
			List notificaciones = DelegateUtil.getNotificacionTelematicaDelegate().listarNotificacionesTelematicasFueraPlazo();

			// Marcamos como rechazada cada notificacion
			ProcesoRechazarNotificacionDelegate dlg = DelegateUtil.getProcesoRechazarNotificacionDelegate();
			for (Iterator it = notificaciones.iterator(); it.hasNext();) {
				NotificacionTelematica not = (NotificacionTelematica) it.next();
				try {
					dlg.rechazarNotificacion(not.getCodigo());
				}catch (Exception e){
					backupLog.error("Error rechazando notificacion " + not.getCodigo() + " :" + e.getMessage(), e);
				}
			}
		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}
	}


	/**
     * Actualiza estado expediente con la informacion de un tramite de subsanacion
     * @param entrada
     * @param tramiteSubsanacion
     * @throws Exception
     *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void actualizarExpedienteTramiteSubsanacion(Long codigoEntrada, String tipoEntrada) throws Exception{
    	backupLog.debug("Actualizar expediente con tramite subsanacion");
		LoginContext lc = null;
		try{

			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			doActualizarExpedienteTramiteSubsanacion(codigoEntrada, tipoEntrada);

    }catch (Exception le){
		throw new EJBException("Excepcion al ejecutar proceso",le);
	}finally{
		// Hacemos el logout
		if ( lc != null ){
			try{lc.logout();}catch(Exception exl){}
		}
	}
	}


    /**
	 * Alertas tramitacion
	 *
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     *
     */
	public void alertasTramitacion()
	{
		backupLog.debug("Alertas tramitacion");

		Principal p = this.context.getCallerPrincipal();

		try{

			// Realizamos alertas tramitacion pago telematico finalizado sin finalizar tramite
			realizarAlertasTramitacionPagoFinalizado();

			// Realizamos alertas tramitacion preregistro sin confirmar
			realizarAlertasTramitacionPreregistroSinConfirmar();

		}catch (Exception le){
			throw new EJBException("Excepcion al ejecutar proceso",le);
		}


	}

		/**
	    * Realiza aviso de que ha registrado un trámite (solo se genera mail, sms no).
	    *  @ejb.interface-method
	    *  @ejb.permission unchecked = "true"
	    */
		public void alertaTramitacionTramiteRealizado(Entrada entrada, String email)
		{
			backupLog.debug("Alerta tramitacion tramite realizado");

			LoginContext lc = null;
			try{
				// Realizamos login JAAS con usuario para proceso automatico
				Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
				String user = props.getProperty("auto.user");
				String pass = props.getProperty("auto.pass");
				CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
				lc = new LoginContext("client-login", handler);
				lc.login();

				// Capturamos posible excepcion xa q no interfiera en proceso registro
	    		AvisoAlertasTramitacion.getInstance().avisarTramiteRealizado(entrada, email);

			}catch (Exception le){
				throw new EJBException("Excepcion al ejecutar proceso",le);
			}finally{
				// Hacemos el logout
				if ( lc != null ){
					try{lc.logout();}catch(Exception exl){}
				}
			}
		}
		
		/**
		 * Crea y devuelve ticket de acceso para tramite persistente recuperado de la carpeta ciudadana
		 * 
	     * @ejb.interface-method
	     * @ejb.permission role-name = "${role.auto}"
	     * 
	     */
		public String obtenerTiquetAcceso(String idSesionTramitacion,
				UsuarioAutenticadoInfoPAD usuarioAutenticadoInfo) throws DelegateException
		{
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	        	
	        	// Buscamos tramite de persistencia
				
				TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
				final TramitePersistente tp = td.obtenerTramitePersistente(idSesionTramitacion);
				
				if (tp == null){
					throw new EJBException("No existe ningún trámite persistente con identificador de sesión " + idSesionTramitacion);
				}
	            
	        	conn = getConnection();
	            
	            // Genera ticket y almacena en sesion
	            final String ticketId = GeneradorId.generarId();
	            
	            final String contextoRaiz = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.contextoRaiz.front");
	            
	            // Calculamos URL de redireccion
	            String urlRedir = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.url") + contextoRaiz + "/sistrafront/protected/redireccionClave.jsp";
	            
	            // Calculamos URL destino
	            String urlDest =  contextoRaiz + URL_CONTINUACION_TRAMITACION + "?language=" + tp.getIdioma()  +"&modelo=" + tp.getTramite() + "&version=" + tp.getVersion() + "&perfilAF=CIUDADANO&idPersistencia=" + tp.getIdPersistencia() ;
	            
	            final Calendar calendar = Calendar.getInstance();
	            calendar.setTime(new Date());        
	            
	            ps = conn
	                    .prepareStatement(getQueryTiquet());
	            ps.setString(1, tp.getIdioma());
	            ps.setString(2, usuarioAutenticadoInfo.getMetodoAutenticacion());
	            ps.setString(3, urlRedir);
	            ps.setString(4, urlDest);
	            ps.setString(5, ticketId);
	            ps.setTimestamp(6,
	                    new java.sql.Timestamp(calendar.getTimeInMillis()));            
	            ps.setString(7, usuarioAutenticadoInfo.getAutenticacion());
	            ps.setString(8, usuarioAutenticadoInfo.getNif());
	            ps.setString(9, StringUtil.formatearNombreApellidos(
	            		"NA",
	    				usuarioAutenticadoInfo.getNombre(),
	    				usuarioAutenticadoInfo.getApellido1(),
	    				usuarioAutenticadoInfo.getApellido2()));
	            ps.setTimestamp(10,
	                    new java.sql.Timestamp(calendar.getTimeInMillis()));	            
	            ps.executeUpdate();
	            return urlRedir + "?ticket=" + ticketId;
	        }catch (Exception e) {
	        	throw new EJBException( "No se ha podido crear ticket de acceso para la sesión : " + idSesionTramitacion, e);    
	        } finally {
	            if (ps != null)
	                try {
	                    ps.close();
	                } catch (final SQLException e) {
	                }
	            if (conn != null)
	                try {
	                    conn.close();
	                } catch (final SQLException ex) {
	                }
	        }
		}

	// ----------------------------------------------------------------------------------------------
	//	FUNCIONES AUXILIARES
	// ----------------------------------------------------------------------------------------------
	private ElementoExpediente obtenerUltimoElementoExpediente(Long id) throws Exception
	{
		Expediente expediente =  DelegateUtil.getExpedienteDelegate().obtenerExpedienteAuto(id);
		if (!expediente.getElementos().isEmpty()){
			ElementoExpediente e = null;
			for (Iterator it = expediente.getElementos().iterator();it.hasNext();){
				e = (ElementoExpediente) it.next();
			}
			return e;
		}
		return null;
	}


	private void doActualizaEstadoExpediente(Long id) throws Exception{
		// Obtenemos ultimo elemento del expediente y obtenemos su detalle
		ElementoExpedienteItf de = null;
		ElementoExpediente e = obtenerUltimoElementoExpediente(id);
		de = DelegateUtil.getElementoExpedienteDelegate().obtenerDetalleElementoExpediente(e.getCodigo());

		// Calculamos estado y fecha fin
		String estado = null;
		Date fechaFin = null;
		if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)){
			estado = ConstantesZPE.ESTADO_SOLICITUD_ENVIADA;
			fechaFin = ((EntradaTelematica) de).getFecha();
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)){
			if ( ((EntradaPreregistro) de).getFechaConfirmacion() != null ){
			estado = ConstantesZPE.ESTADO_SOLICITUD_ENVIADA;
			}else{
				estado = ConstantesZPE.ESTADO_SOLICITUD_ENVIADA_PENDIENTE_DOCUMENTACION_PRESENCIAL;
			}
			fechaFin = ((EntradaPreregistro) de).getFecha();
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){
			if ( ((EventoExpediente) de).getFechaConsulta() != null){
				estado 	 = ConstantesZPE.ESTADO_AVISO_RECIBIDO;
				fechaFin = ((EventoExpediente) de).getFecha();
			}else{
				estado = ConstantesZPE.ESTADO_AVISO_PENDIENTE;
				fechaFin = ((EventoExpediente) de).getFecha();
			}
		}else if (e.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){
			NotificacionTelematica notificacionTelematica = (NotificacionTelematica) de;
			if ( notificacionTelematica.isRechazada()) {
				estado = ConstantesZPE.ESTADO_NOTIFICACION_RECHAZADA;
				fechaFin = notificacionTelematica.getFechaRegistro();
			} else if ( notificacionTelematica.getFechaAcuse() != null){
				estado = ConstantesZPE.ESTADO_NOTIFICACION_RECIBIDA;
				fechaFin = notificacionTelematica.getFechaRegistro();
			}else{
				estado = ConstantesZPE.ESTADO_NOTIFICACION_PENDIENTE;
				fechaFin = notificacionTelematica.getFechaRegistro();
			}
		}

		// Actualizamos expediente
		DelegateUtil.getExpedienteDelegate().actualizaEstadoExpedienteAuto(id, estado, fechaFin);

	}
    /**
     * Revisa registros efectuados para ver si se han consolidado
     *
     */
	private void doRevisarRegistrosEfectuados() throws Exception{
		ProcesoRevisarRegistrosDelegate procRevDlg = DelegateUtil.getProcesoRevisarRegistrosDelegate();
		LogRegistroDelegate dlg = DelegateUtil.getLogRegistroDelegate();
		List logRegs = dlg.listarLogRegistro();
		if(logRegs != null){
			for(int i=0;i<logRegs.size();i++){
				LogRegistro logReg = (LogRegistro)logRegs.get(i);
				try {
					procRevDlg.revisarRegistro(logReg);
				} catch (Exception ex) {
					// Pasamos a siguiente registro
					backupLog.debug("Excepcion al revisar registro " + logReg.getId().getNumeroRegistro() + ": " + ex.getMessage());
				}
			}
		}
	}


	private void doActualizarExpedienteTramiteSubsanacion(Long codigoEntrada, String tipoEntrada) throws Exception{
		Entrada entrada = null;
		if (tipoEntrada.equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)){
			entrada = DelegateUtil.getEntradaTelematicaDelegate().obtenerEntradaTelematica(codigoEntrada);
		}else{
			entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro(codigoEntrada);
		}
		if (entrada == null){
			throw new Exception("No se encuentra entrada con codigo " + tipoEntrada + "-" + codigoEntrada);
		}

		ExpedienteDelegate ed = DelegateUtil.getExpedienteDelegate();
		Expediente expe = ed.obtenerExpedienteAuto(entrada.getSubsanacionExpedienteUA().longValue(),entrada.getSubsanacionExpedienteCodigo());
		if (expe == null){
			throw new Exception("No existe expediente indicado en datos propios: " + entrada.getSubsanacionExpedienteUA()+ " - " + entrada.getSubsanacionExpedienteCodigo());
		}
    	ElementoExpediente el = new ElementoExpediente();
    	el.setExpediente(expe);
    	el.setTipoElemento(entrada instanceof EntradaTelematica?ElementoExpediente.TIPO_ENTRADA_TELEMATICA:ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);
    	el.setFecha(entrada.getFecha());
    	el.setCodigoElemento(entrada.getCodigo());
    	el.setIdentificadorPersistencia(entrada.getIdPersistencia());
    	el.setAccesoAnonimoExpediente(entrada.getNivelAutenticacion() == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO);
    	expe.addElementoExpediente(el,entrada);
    	DelegateUtil.getExpedienteDelegate().grabarExpedienteReal(expe);
	}

	/**
	 * Comprueba si hay registros externos preparados caducados y los elimina.
	 */
	private void doRevisarRegistrosExternosPreparados() throws Exception{
		ProcesoRevisarRegistrosDelegate procRevDlg = DelegateUtil.getProcesoRevisarRegistrosDelegate();
		RegistroExternoPreparadoDelegate dlg = DelegateUtil.getRegistroExternoPreparadoDelegate();
		List regs = dlg.listarCaducados();
		if (regs != null) {
			for (Iterator it = regs.iterator();it.hasNext();){
				RegistroExternoPreparado r = (RegistroExternoPreparado) it.next();
				try {
					procRevDlg.revisarRegistroExternoPreparado(r);
				} catch (Exception ex) {
					// Pasamos a siguiente registro
					backupLog.debug("Excepcion al revisar registro externo preparado " + r.getIdPersistencia() + ": " + ex.getMessage());
				}
			}
		}
	}


	private void doProcesaTramitesCaducados() throws Exception {
		BackupDelegate delegate = DelegateUtil.getBackupDelegate();
		String borradoPreregistro = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.backup.schedule.borradoPreregistro");
		boolean scheduleBorradoPreregistro = Boolean.valueOf(borradoPreregistro).booleanValue();
		backupLog.debug( "Job borrado tramites caducados [borrado prerregistros = " + scheduleBorradoPreregistro + "]");
		Date fechaEjecucion = new Date();
		delegate.procesaTramitesCaducados( fechaEjecucion, scheduleBorradoPreregistro );
	}



	private void doProcesaEliminarTramitesBackup() throws Exception {
		BackupDelegate delegate = DelegateUtil.getBackupDelegate();
		backupLog.debug( "Job borrado tramites backup");
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String meses = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.borradoBackup.meses");
        cal.add(Calendar.MONTH,  (Integer.parseInt(meses) * -1) );
		delegate.procesaEliminarTramitesBackup( cal.getTime() );
	}


	private void realizarAlertasTramitacionPreregistroSinConfirmar() throws Exception{

		LoginContext lc = null;
		try{
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Recuperamos preregistros pendientes de avisar
			EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
			List preregistros = delegate.obtenerTramitesPendienteAvisoPreregistroSinConfirmar();
			MobTraTelDelegate mob = DelegateMobTraTelUtil.getMobTraTelDelegate();

			// Generamos aviso para los tramites
			for (Iterator it = preregistros.iterator(); it.hasNext();) {
				EntradaPreregistro preregistro = (EntradaPreregistro) it.next();
				if (StringUtils.isBlank(preregistro.getAlertasTramitacionEmail()) &&
						StringUtils.isBlank(preregistro.getAlertasTramitacionSms())) {
					continue;
				}

				try {
					// Generamos aviso a traves de mobtratel
					AvisoAlertasTramitacion.getInstance().avisarPreregistroPendiente(preregistro);
					// Marcamos como avisado
					delegate.avisoPreregistroSinConfirmar(preregistro.getIdPersistencia());
				} catch (Exception exc) {
					// Mostramos error en log y continuamos con siguiente tramite
					backupLog.error("Error realizando alerta de tramitacion", exc);
				}
			}

		}finally{
			// Hacemos el logout
			if ( lc != null ){
				try{lc.logout();}catch(Exception exl){}
			}
		}

	}


	private void realizarAlertasTramitacionPagoFinalizado() throws Exception {

		// Obtenemos tramites pagados pendientes finalizar
		Map tramitesPagadosPendientesFinalizar = obtenerTramitesPagadosPendienteFinalizar();

		// Intentamos finalizar los marcados para finalizar automaticamente
		List tramsFinalizados = finalizarTramitesAnonimosAutomaticamente(tramitesPagadosPendientesFinalizar);

		// Realiza avisos a ciudadano y a gestor
		realizarAvisos(tramitesPagadosPendientesFinalizar, tramsFinalizados);


	}

	private void realizarAvisos(Map tramitesPagadosPendientesFinalizar,
			List tramsFinalizados)  throws Exception {

		if (tramitesPagadosPendientesFinalizar.size() == 0) {
			return;
		}

		LoginContext lc = null;
		try {
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate()
					.obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler(user,
					pass);
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Realizamos envio a los ciudadanos
			List tramitesAvisados = realizarAvisosCiudadanos(tramitesPagadosPendientesFinalizar, tramsFinalizados);

			// Realizar avisos gestores
			realizarAvisosGestores(tramitesPagadosPendientesFinalizar, tramsFinalizados, tramitesAvisados);

		} finally {
			// Hacemos el logout
			if (lc != null) {
				try {
					lc.logout();
				} catch (Exception exl) {
				}
			}
		}

	}

	private void realizarAvisosGestores(Map tramitesPagadosPendientesFinalizar,
			List tramsFinalizados, List tramitesAvisados) throws Exception {

		// Verificamos si esta habilitado el aviso a gestores
		if (!isAvisosGestorHabilitado()) {
			return;
		}

		// Obtenemos procedimientos asociados a los tramites parar obtener mail gestores
		Map procedimientosInfo = new HashMap();
		Map procedimientosTramite = new HashMap();
		for (Iterator it = tramitesPagadosPendientesFinalizar.keySet().iterator(); it.hasNext();) {
			TramitePersistente tp = (TramitePersistente) tramitesPagadosPendientesFinalizar.get(it.next());
			// Añadimos info procedimiento
			if (!procedimientosInfo.containsKey(tp.getIdProcedimiento())) {
				ProcedimientoBTE proc = es.caib.bantel.persistence.delegate.DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(tp.getIdProcedimiento());
				procedimientosInfo.put(tp.getIdProcedimiento(), proc);
				procedimientosTramite.put(tp.getIdProcedimiento(), new ArrayList());
			}
			// Añadimos tramite al procedimiento
			((List) procedimientosTramite.get(tp.getIdProcedimiento())).add(tp);
		}


		// Realizamos avisos a gestores por procedimiento
		MobTraTelDelegate mob = DelegateMobTraTelUtil.getMobTraTelDelegate();
		PadDelegate padDelegate = DelegatePADUtil.getPadDelegate();

		boolean enviadoAvisoCiudadano = false;
		boolean finalizadoAutomaticamente = false;

		for (Iterator it = procedimientosInfo.keySet().iterator(); it.hasNext();) {
			String idProc = (String) it.next();
			ProcedimientoBTE proc = (ProcedimientoBTE) procedimientosInfo.get(idProc);
			List trams = (List) procedimientosTramite.get(idProc);

			if (proc.getEmailGestores().size() <= 0) {
				continue;
			}

			String textoEmail = StringEscapeUtils.escapeHtml("Trámites pendientes de finalizar con pagos realizados:") + "</br>";
			textoEmail += "<ul>";
			for (Iterator itTram = trams.iterator(); itTram.hasNext();){
				TramitePersistente tram = (TramitePersistente) itTram.next();

				enviadoAvisoCiudadano = tramitesAvisados.contains(tram.getCodigo());
				finalizadoAutomaticamente = tramsFinalizados.contains(tram.getCodigo());

				textoEmail += "<li>";
				if (tram.getNivelAutenticacion() == 'A') {
					textoEmail += StringEscapeUtils.escapeHtml("Acceso anónimo - Identificador trámite: " + tram.getIdPersistencia());
				} else {
					PersonaPAD pers = padDelegate.obtenerDatosPersonaPADporUsuario(tram.getUsuario());
					String nomPers = "";
					if (pers != null) {
						nomPers = pers.getNif() + " - " + pers.getNombreCompleto() ;
					} else {
						nomPers = tram.getUsuario() ;
					}
					textoEmail += StringEscapeUtils.escapeHtml("Acceso autenticado (" + nomPers + ") - Identificador trámite: " + tram.getIdPersistencia());
				}

				if (enviadoAvisoCiudadano) {
					textoEmail += StringEscapeUtils.escapeHtml(" (avisado a ciudadano)");
				}
				if (finalizadoAutomaticamente) {
					textoEmail += StringEscapeUtils.escapeHtml(" (finalizado automáticamente)");
				}

				textoEmail += "</li>";
			}
			textoEmail += "</ul>";

			MensajeEnvioEmail mensEmail = new MensajeEnvioEmail();
			
			String emailsInc = proc.getEmailIncidencias();
			String [] dest;
			
			if (!StringUtils.isEmpty(emailsInc)){
				dest = emailsInc.split(";");
			} else {
				dest = new String[0];
			}
			
			mensEmail.setDestinatarios(dest);
			mensEmail.setTitulo(proc.getDescripcion() + ": existen trámites pendientes de finalizar con pagos realizados");
			mensEmail.setHtml(true);
			mensEmail.setTexto(textoEmail);

			MensajeEnvio mens = new MensajeEnvio();
			mens.setNombre("Aviso trámite pendiente y pago realizado (" + proc.getIdentificador() + ")");
			mens.setCuentaEmisora(getCuentaSistra());
			mens.setInmediato(true);
			mens.addEmail(mensEmail);
			try {
				mob.envioMensaje(mens );
			} catch (Exception exc) {
				// Mostramos error en log y continuamos con siguiente tramite
				backupLog.error("Error realizando alerta de tramitacion", exc);
			}
		}

	}

	private List realizarAvisosCiudadanos(
			Map tramitesPagadosPendientesFinalizar, List tramsFinalizados) throws Exception {

		List tramitesAvisados = new ArrayList();
		TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();

		for (Iterator it = tramitesPagadosPendientesFinalizar.keySet().iterator(); it.hasNext();) {

			TramitePersistente tramite = (TramitePersistente) tramitesPagadosPendientesFinalizar.get(it.next());

			// Comprobamos si se ha finalizado automaticamente
			if (tramsFinalizados.contains(tramite.getCodigo())) {
				continue;
			}

			// Verificamos si tiene activada las alertas
			boolean avisarCiudadano =  StringUtils.isNotBlank(tramite.getAlertasTramitacionEmail()) || StringUtils.isNotBlank(tramite.getAlertasTramitacionSms());

			if (avisarCiudadano) {
				try {
					// Generamos aviso a traves de mobtratel
					AvisoAlertasTramitacion.getInstance().avisarPagoRealizadoTramitePendiente(tramite);
					// Marcamos como avisado
					delegate.avisoPagoTelematicoFinalizado(tramite.getIdPersistencia());
					tramitesAvisados.add(tramite.getCodigo());
				} catch (Exception exc) {
					// Mostramos error en log y continuamos con siguiente tramite
					backupLog.error("Error realizando alerta de tramitacion", exc);
				}
			}
		}

		return tramitesAvisados;

	}

	private List finalizarTramitesAnonimosAutomaticamente(
			Map tramitesPagadosPendientesFinalizar) {

		List tramsFinalizados = new ArrayList();

		if (tramitesPagadosPendientesFinalizar != null) {

			SistraDelegate sistraDlg = DelegateSISTRAUtil.getSistraDelegate();

			for (Iterator it = tramitesPagadosPendientesFinalizar.keySet().iterator(); it.hasNext();) {
				Long codTramite = (Long) it.next();
				TramitePersistente tp = (TramitePersistente) tramitesPagadosPendientesFinalizar.get(codTramite);

				// Si esta marcado para finalizar automaticamente y es anonimo intentamos finalizarlo
				if (tp.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO && "S".equals(tp.getAlertasTramitacionFinAuto())) {
					boolean finalizadoOk;
					try {
						finalizadoOk = sistraDlg.finalizarTramitePagadoAnonimo(tp.getIdPersistencia());
					} catch (Exception e) {
						backupLog.equals("Excepcion finalizando tramite " + tp.getIdPersistencia() + " automaticamente: " + e.getMessage());
						finalizadoOk = false;
					}
					if (finalizadoOk) {
						tramsFinalizados.add(codTramite);
					}
				}
			}

		}

		return tramsFinalizados;
	}

	private Map obtenerTramitesPagadosPendienteFinalizar()
			throws DelegateException, LoginException, Exception {
		Map tramitesPagadosPendientesFinalizar = new HashMap();
		LoginContext lc = null;
		try {
			// Realizamos login JAAS con usuario para proceso automatico
			Properties props = DelegateUtil.getConfiguracionDelegate()
					.obtenerConfiguracion();
			String user = props.getProperty("auto.user");
			String pass = props.getProperty("auto.pass");
			CallbackHandler handler = new UsernamePasswordCallbackHandler(user,
					pass);
			lc = new LoginContext("client-login", handler);
			lc.login();

			// Recuperamos tramites pendientes de avisar
			TramitePersistenteDelegate delegate = DelegateUtil
					.getTramitePersistenteDelegate();
			List tramites = delegate
					.obtenerTramitesPendienteAvisoPagoTelematicoFinalizado();

			// Obtenemos que tramites deben avisarse
			for (Iterator it = tramites.iterator(); it.hasNext();) {

				TramitePersistente tramite = (TramitePersistente) it.next();

				// Verificamos si tiene un pago telematico realizado
				boolean avisar = false;
				for (Iterator it2 = tramite.getDocumentos().iterator(); it2
						.hasNext();) {
					DocumentoPersistente dp = (DocumentoPersistente) it2.next();
					if (DocumentoPersistentePAD.TIPO_PAGO.equals(dp
							.getTipoDocumento())) {
						// Si esta pagado, hay que avisar
						if (dp.getEstado() == DocumentoPersistentePAD.ESTADO_CORRECTO
								&& "S".equals(dp.getEsPagoTelematico())) {
							avisar = true;
						}
						// Si esta iniciado, hay que verificar si esta pagado
						if (dp.getEstado() == DocumentoPersistentePAD.ESTADO_INCORRECTO) {
							avisar = isPagoTelematicoPendienteConfirmado(dp);
						}
						// Si hay que avisar, no seguimos mirando
						if (avisar) {
							break;
						}
					}
				}

				if (avisar) {
					tramitesPagadosPendientesFinalizar.put(tramite.getCodigo(),
							tramite);
				}
			}

		} finally {
			// Hacemos el logout
			if (lc != null) {
				try {
					lc.logout();
				} catch (Exception exl) {
				}
			}
		}
		return tramitesPagadosPendientesFinalizar;
	}


	/**
	 * Verifica contra el plugin de pago si es un pago telematico y esta confirmado.
	 * @param dp
	 * @return
	 * @throws Exception
	 */
	private boolean isPagoTelematicoPendienteConfirmado(DocumentoPersistente dp) throws Exception {

		boolean res = false;

		// Obtenemos datos pago para obtener el localizador
		RdsDelegate rdsDlg = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS pagoRds = rdsDlg.consultarDocumento(new ReferenciaRDS(dp.getRdsCodigo().longValue(), dp.getRdsClave()));
		XmlDatosPago xmlPago = new XmlDatosPago();
		xmlPago.setBytes(pagoRds.getDatosFichero());

		// Invocamos al plugin de pago para verificar estado sesion pago
		if (StringUtils.isNotBlank(xmlPago.getLocalizador())) {
			String pluginId = xmlPago.getPluginId();
			if (StringUtils.isBlank(pluginId)) {
				pluginId = PluginFactory.ID_PLUGIN_DEFECTO;
			}
			PluginPagosIntf pluginPagos = PluginFactory.getInstance().getPluginPagos(pluginId);
			EstadoSesionPago estadoSesionPago = pluginPagos.comprobarEstadoSesionPago(xmlPago.getLocalizador());

			// Debe ser telematico y estar confirmado
			res = (estadoSesionPago.getTipo() == ConstantesPago.TIPOPAGO_TELEMATICO) &&
					(estadoSesionPago.getEstado() == ConstantesPago.SESIONPAGO_PAGO_CONFIRMADO);

		}

		return res;
	}


	private String getCuentaSistra(){
		if (StringUtils.isEmpty(cuentaSistra)) {
			try {
				cuentaSistra = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.cuentaEnvio.avisosExpediente");
			} catch (DelegateException e) {
				backupLog.error("Error obteniendo cuenta sistra", e);
			}
		}
		return cuentaSistra;
	}

	private boolean isAvisosGestorHabilitado(){
		if (avisosGestorHabilitado == null) {
			try {
				avisosGestorHabilitado = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("scheduler.alertasTramitacion.pagoFinalizado.avisarGestores");
			} catch (DelegateException e) {
				avisosGestorHabilitado = "false";
			}
		}
		return "true".equals(avisosGestorHabilitado);
	}
	
	private String getQueryTiquet(){
		if (StringUtils.isEmpty(queryTiquet)) {
			try {
				queryTiquet = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("ticketAutenticacion.query");
			} catch (DelegateException e) {
				backupLog.error("Error obteniendo query para la creación de tiquet de autenticacion", e);
			}
		}
		return queryTiquet;
	}
	
    private Connection getConnection() throws Exception {
        Connection conn;
        final InitialContext ctx = new InitialContext();
    	Properties props = ConfigurationUtil.getInstance().obtenerPropiedades();
    	String datasourceTicketClave = props.getProperty("ticketAutenticacion.datasource");

    	if (StringUtils.isBlank(datasourceTicketClave)) {
    		throw new Exception("No se ha configurado el datasource para acceder a la tabla de tickets de autenticación en zonaper.properties");
    	}
        final DataSource ds = (DataSource) ctx.lookup(datasourceTicketClave);
        conn = ds.getConnection();
        return conn;
    }

}
