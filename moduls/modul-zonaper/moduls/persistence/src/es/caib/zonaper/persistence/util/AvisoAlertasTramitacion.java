package es.caib.zonaper.persistence.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioSms;
import es.caib.mobtratel.persistence.delegate.DelegateMobTraTelUtil;
import es.caib.mobtratel.persistence.delegate.MobTraTelDelegate;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.ConvertUtil;
import es.caib.util.StringUtil;
import es.caib.util.ValidacionesUtil;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * Contiene la logica de generacion de avisos de alertas de tramitacion. 
 * Se ejecutaran con el usuario auto. 
 * 
 * Utilizado desde AvisosDelegacionFacadeEJB 
 *
 */
public class AvisoAlertasTramitacion {

	private static AvisoAlertasTramitacion avisos;
	private static String cuentaSistra;
	private static Log log = LogFactory.getLog(AvisoAlertasTramitacion.class);
	private static Map plantillas = new HashMap();
	
	private AvisoAlertasTramitacion(){
		
	}
	
	public static AvisoAlertasTramitacion getInstance() throws Exception{		
		if (avisos == null){
			avisos = new AvisoAlertasTramitacion();
			cuentaSistra = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.cuentaEnvio.avisosExpediente");
			if (StringUtils.isEmpty(cuentaSistra)) throw new Exception("No se ha especificado cuenta envío para avisos de expediente");			
		}
		return avisos;
	}			
	
	/**
    * Realiza aviso de que ha registrado un trámite (solo se genera mail, sms no). 
    */
	public void avisarTramiteRealizado(Entrada entrada, String email) throws Exception
	{
		MensajeEnvioEmail mensEmail = null;
		if (StringUtils.isNotEmpty(email)) {
			String msgFinRegistro = "aviso.alertasTramitacion.registroFinalizado";
			boolean preregistro = entrada instanceof EntradaPreregistro;
			if (preregistro) {
				msgFinRegistro = "aviso.alertasTramitacion.preregistroFinalizado";
			}
			String mensaje=LiteralesAvisosMovilidad.getLiteral(entrada.getIdioma(),msgFinRegistro);
			mensaje=StringUtil.replace(mensaje,"{0}",entrada.getDescripcionTramite());			
			if (entrada.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO) {
				String  mensajeAnonimo = LiteralesAvisosMovilidad.getLiteral(entrada.getIdioma(),"aviso.alertasTramitacion.idPersistenciaAnonimo");
				mensajeAnonimo = StringUtil.replace(mensajeAnonimo,"{0}",entrada.getIdPersistencia());
				mensaje = mensaje + "\n\n" + mensajeAnonimo;
			}
			if (preregistro && ((EntradaPreregistro) entrada).getFechaCaducidad() != null) {
				String mensajeFcCaducidad=LiteralesAvisosMovilidad.getLiteral(entrada.getIdioma(),"aviso.alertasTramitacion.fechaCaducidad");
				mensajeFcCaducidad=StringUtil.replace(mensajeFcCaducidad,"{0}",StringUtil.fechaACadena(((EntradaPreregistro) entrada).getFechaCaducidad(), "dd/MM/yyyy HH:mm"));
				mensaje = mensaje + "\n\n" + mensajeFcCaducidad;
			}
			mensEmail = crearMensajeEmail(email, entrada.getIdioma(), mensaje);			
		}		
		enviarMensaje(entrada.getIdPersistencia(), entrada.getProcedimiento(), mensEmail, null);
	
	}

	
	/**
    * Realiza aviso de que un tramite tiene un pago telematico realizado y no se ha finalizado 
    */
	public void avisarPagoRealizadoTramitePendiente(TramitePersistente tramite) throws Exception
	{
		MensajeEnvioEmail mensEmail = null;
		if (StringUtils.isNotEmpty(tramite.getAlertasTramitacionEmail())) {
			String mensaje=LiteralesAvisosMovilidad.getLiteral(tramite.getIdioma(),"aviso.alertasTramitacion.pagoRealizadoTramitePendiente");
			mensaje=StringUtil.replace(mensaje,"{0}",tramite.getDescripcion());
			mensaje=StringUtil.replace(mensaje,"{1}",StringUtil.fechaACadena(tramite.getFechaCreacion(), "dd/MM/yyyy HH:mm"));
			if (tramite.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO) {
				String  mensajeAnonimo = LiteralesAvisosMovilidad.getLiteral(tramite.getIdioma(),"aviso.alertasTramitacion.idPersistenciaAnonimo");
				mensajeAnonimo = StringUtil.replace(mensajeAnonimo,"{0}",tramite.getIdPersistencia());
				mensaje = mensaje + "\n\n" + mensajeAnonimo;
			}
			if (tramite.getFechaCaducidad() != null) {
				String mensajeFcCaducidad=LiteralesAvisosMovilidad.getLiteral(tramite.getIdioma(),"aviso.alertasTramitacion.fechaCaducidad");
				mensajeFcCaducidad=StringUtil.replace(mensajeFcCaducidad,"{0}",StringUtil.fechaACadena(tramite.getFechaCaducidad(), "dd/MM/yyyy HH:mm"));
				mensaje = mensaje + "\n\n" + mensajeFcCaducidad;
			}
			mensEmail = crearMensajeEmail(tramite.getAlertasTramitacionEmail(), tramite.getIdioma(), mensaje);			
		}
		MensajeEnvioSms mensSms = null;
		if (StringUtils.isNotEmpty(tramite.getAlertasTramitacionSms()) && ValidacionesUtil.validarMovil(tramite.getAlertasTramitacionSms().trim())) {
			String mensaje = LiteralesAvisosMovilidad.getLiteral(tramite.getIdioma(),"aviso.sms.alertaTramitacion.pagoFinalizadoTramitePendiente");
			OrganismoInfo oi = ConfigurationUtil.getInstance().obtenerOrganismoInfo();
			mensaje=StringUtil.replace(mensaje,"{0}",oi.getNombre().toUpperCase());
			mensSms = crearMensajeSms(tramite.getAlertasTramitacionSms().trim(), mensaje);			
		}
		enviarMensaje(tramite.getIdPersistencia(), tramite.getIdProcedimiento(), mensEmail, mensSms);
	
	}
	
	/**
    * Realiza aviso de que un tramite es un preregistro y no se ha confirmado 
    */
	public void avisarPreregistroPendiente(EntradaPreregistro preregistro) throws Exception
	{
		MensajeEnvioEmail mensEmail = null;
		if (StringUtils.isNotEmpty(preregistro.getAlertasTramitacionEmail())) {
			String mensaje=LiteralesAvisosMovilidad.getLiteral(preregistro.getIdioma(),"aviso.alertasTramitacion.preregistroPendienteConfirmar");
			mensaje=StringUtil.replace(mensaje,"{0}",preregistro.getDescripcionTramite());
			mensaje=StringUtil.replace(mensaje,"{1}",StringUtil.fechaACadena(preregistro.getFecha(), "dd/MM/yyyy HH:mm"));
			String mensajeAnonimo = "";
			if (preregistro.getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO) {
				mensajeAnonimo = LiteralesAvisosMovilidad.getLiteral(preregistro.getIdioma(),"aviso.alertasTramitacion.idPersistenciaAnonimo");
				mensajeAnonimo = StringUtil.replace(mensajeAnonimo,"{0}",preregistro.getIdPersistencia());
				mensaje = mensaje + "\n\n" + mensajeAnonimo;
			}
			if (preregistro.getFechaCaducidad() != null) {
				String mensajeFcCaducidad=LiteralesAvisosMovilidad.getLiteral(preregistro.getIdioma(),"aviso.alertasTramitacion.fechaCaducidad");
				mensajeFcCaducidad=StringUtil.replace(mensajeFcCaducidad,"{0}",StringUtil.fechaACadena(preregistro.getFechaCaducidad(), "dd/MM/yyyy HH:mm"));
				mensaje = mensaje + "\n\n" + mensajeFcCaducidad;			
			}
			mensEmail = crearMensajeEmail(preregistro.getAlertasTramitacionEmail(), preregistro.getIdioma(), mensaje);			
		}
		MensajeEnvioSms mensSms = null;
		if (StringUtils.isNotEmpty(preregistro.getAlertasTramitacionSms()) && ValidacionesUtil.validarMovil(preregistro.getAlertasTramitacionSms().trim())) {
			String mensaje = LiteralesAvisosMovilidad.getLiteral(preregistro.getIdioma(),"aviso.sms.alertaTramitacion.preregistroPendienteConfirmar");
			OrganismoInfo oi = ConfigurationUtil.getInstance().obtenerOrganismoInfo();
			mensaje=StringUtil.replace(mensaje,"{0}",oi.getNombre().toUpperCase());
			mensSms = crearMensajeSms(preregistro.getAlertasTramitacionSms().trim(), mensaje);			
		}
		enviarMensaje(preregistro.getIdPersistencia(), preregistro.getProcedimiento(), mensEmail, mensSms);
	
	}
	
	//--------------------------------------------------------------------------
	// FUNCIONES DE UTILIDAD
	//--------------------------------------------------------------------------
	private MensajeEnvioEmail crearMensajeEmail(String email, String idioma,  String mensaje) throws Exception{
		OrganismoInfo oi = ConfigurationUtil.getInstance().obtenerOrganismoInfo();
		String tituloEmail=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.alertasTramitacion.titulo");
		tituloEmail=StringUtil.replace(tituloEmail,"{0}",oi.getNombre().toUpperCase());
		
		String textoEmail = cargarPlantillaMail("mailAvisoAlertaTramitacion.html");
		textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.NOMBRE#]",oi.getNombre());
		textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.LOGO#]",oi.getUrlLogo());
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISOTRAMITACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.avisoAlertaTramitacion")));
		textoEmail = StringUtil.replace(textoEmail,"[#ALERTA.TITULO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.avisoAlertaTramitacion")));
		textoEmail = StringUtil.replace(textoEmail,"[#ALERTA.MENSAJE#]",StringUtil.replace(StringEscapeUtils.escapeHtml(mensaje),"\n","<br/>"));
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.SOPORTE#]",LiteralesAvisosMovilidad.calcularTextoSoporte(oi, idioma));
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AUTO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"email.correoAutomatico")));
		
		// Reemplazamos texto "Mi portal" por enlace
		String textoMiPortal = (String) oi.getReferenciaPortal().get(idioma);
		String urlMiPortal = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.url") + "/zonaperfront";
		textoEmail = StringUtil.replace(textoEmail, textoMiPortal, "<a href=\"" + urlMiPortal + "\">" + textoMiPortal + "</a>");
		
		// Creamos MensajeEnvio
		MensajeEnvioEmail mensEmail = new MensajeEnvioEmail();
		String dest [] = {email};
		mensEmail.setDestinatarios(dest);
		mensEmail.setTitulo(tituloEmail);
		mensEmail.setTexto(textoEmail);
		mensEmail.setHtml(true);
		return mensEmail;
	}
	
	private MensajeEnvioSms crearMensajeSms(String sms, String mensaje) {
		// Creamos MensajeEnvio
		MensajeEnvioSms mensSms = new MensajeEnvioSms();
		String dest [] = {sms};
		mensSms.setDestinatarios(dest);
		mensSms.setTexto(mensaje);
		return mensSms;
	}
	
	/**
	 * Realizamos envio del mensaje al modulo de movilidad con usuario auto  
	 * @param idProcedimiento 
	 */
	private void enviarMensaje(String idPersistencia, String idProcedimiento, MensajeEnvioEmail mensajeEmail, MensajeEnvioSms mensajeSms) throws Exception{
		if (mensajeEmail != null || mensajeSms != null) {
			log.debug("Creamos mensajeEnvio");
			MensajeEnvio mens = new MensajeEnvio();
			mens.setNombre("Aviso automático de alertas de tramitacion: " + idPersistencia);
			mens.setCuentaEmisora(cuentaSistra);
			mens.setInmediato(true);
			mens.setIdProcedimiento(idProcedimiento);
			if (mensajeEmail != null) {
				mens.addEmail(mensajeEmail);
			}			
			if (mensajeSms != null) {
				mens.addSMS(mensajeSms);
			}
			MobTraTelDelegate mob = DelegateMobTraTelUtil.getMobTraTelDelegate();
			mob.envioMensaje(mens);
		}
	}
	
	/**
	 * Carga plantillas de mail
	 * @param idioma
	 * @return
	 * @throws Exception
	 */
	private static String cargarPlantillaMail(String idPlantilla) throws Exception{
		if (!plantillas.containsKey(idPlantilla)){
			InputStream is = AvisoAlertasTramitacion.class.getResourceAsStream(idPlantilla);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();			
			ConvertUtil.copy(is,bos);
			String plantilla = new String(bos.toByteArray());
			plantillas.put(idPlantilla,plantilla);
		}
		return (String) plantillas.get(idPlantilla);
	}
		
}
