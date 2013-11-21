package es.caib.zonaper.persistence.util;

import java.io.ByteArrayInputStream;
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
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.util.ConvertUtil;
import es.caib.util.StringUtil;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.ElementoExpedienteItf;
import es.caib.zonaper.model.EventoExpediente;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.ConfiguracionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * Contiene la logica de generacion de avisos  de movilidad para un expediente
 * 
 * Utilizado desde el proceso auto de aviso de creacion elemento expediente y se
 * ejecutaran con el usuario auto
 *
 */
public class AvisosMovilidad {

	private static AvisosMovilidad avisos;
	private static String cuentaSistra;
	private static boolean confirmacionEnvioNotificacionesEmail;
	private static boolean confirmacionEnvioNotificacionesSms;
	private static boolean confirmacionEnvioEventosEmail;
	private static boolean confirmacionEnvioEventosSms;
	private static boolean alertasSmsZonaPersonal;
	private static Log log = LogFactory.getLog(AvisosMovilidad.class);
	private static Map plantillas = new HashMap();
	
	private AvisosMovilidad(){
		
	}
	
	public static AvisosMovilidad getInstance() throws Exception{		
		if (avisos == null){
			avisos = new AvisosMovilidad();
			cuentaSistra = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.cuentaEnvio");
			if (StringUtils.isEmpty(cuentaSistra)) throw new Exception("No se ha especificado cuenta envío");
			String confNotifEmail = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.confirmacionEnvio.notificaciones.email");
			if (StringUtils.isNotBlank(confNotifEmail) && "true".equals(confNotifEmail)) {
				confirmacionEnvioNotificacionesEmail = true;
			} else {
				confirmacionEnvioNotificacionesEmail = false;
			}
			String confNotifSms = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.confirmacionEnvio.notificaciones.sms");
			if (StringUtils.isNotBlank(confNotifSms) && "true".equals(confNotifSms)) {
				confirmacionEnvioNotificacionesSms = true;
			} else {
				confirmacionEnvioNotificacionesSms = false;
			}
			String confEnvEmail = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.confirmacionEnvio.eventosExpediente.email");
			if (StringUtils.isNotBlank(confEnvEmail) && "true".equals(confEnvEmail)) {
				confirmacionEnvioEventosEmail = true;
			} else {
				confirmacionEnvioEventosEmail = false;
			}
			String confEnvSms = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.confirmacionEnvio.eventosExpediente.sms");
			if (StringUtils.isNotBlank(confEnvSms) && "true".equals(confEnvSms)) {
				confirmacionEnvioEventosSms = true;
			} else {
				confirmacionEnvioEventosSms = false;
			}
			
			String confAlertasSmsZonaPersonal = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.smsAlertas");
			if (StringUtils.isNotBlank(confAlertasSmsZonaPersonal) && "true".equals(confAlertasSmsZonaPersonal)) {
				alertasSmsZonaPersonal = true;
			} else {
				alertasSmsZonaPersonal = false;
			}			
		}
		return avisos;
	}			
	
	/**
	 * Realiza el aviso de la creacion de un elemento de un expediente (aviso expediente y notificación) 
	 * Ejecutado con usu auto
	 * @throws Exception 
	 */
	public String avisoCreacionElementoExpediente(ElementoExpediente ele) throws Exception{
		
		String emailZP=null;
		String smsZP=null;
		String emailExpe=null;
		String smsExpe=null;
		

		// Los tipos de elementos de los que se avisa son los eventos y las notificaciones
		if (!ele.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE) && 
			!ele.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION) ){			
				return null;
		}
		
		Expediente expe = ele.getExpediente();

		log.debug("Aviso creacion elemento expediente: " + expe.getUnidadAdministrativa() + " - " + expe.getIdExpediente());
		
		// Obtenemos configuracion avisos zona personal
		
		// Config a nivel de zona personal (si expe autenticado)
		log.debug("Buscamos configuracion a nivel de zona personal");
		if (StringUtils.isNotEmpty(expe.getSeyconCiudadano())){
			PadAplicacionDelegate pad = DelegateUtil.getPadAplicacionDelegate();
			PersonaPAD ciud = pad.obtenerDatosPersonaPADporUsuario(expe.getSeyconCiudadano());			
			if (ciud.isHabilitarAvisosExpediente()){
				emailZP=ciud.getEmail();
				if (alertasSmsZonaPersonal) {
					smsZP=ciud.getTelefonoMovil();
				}
			}
		}
		
		// Config a nivel de expediente
		if ("S".equals(expe.getHabilitarAvisos())){
			// Config a nivel de expe
			log.debug("Existe configuracion a nivel de expediente");
			emailExpe = expe.getAvisoEmail();
			smsExpe = expe.getAvisoSMS();
		}
		
		// Comprobamos si hay que enviar
		if (StringUtils.isEmpty(emailZP) && StringUtils.isEmpty(smsZP) && 
				StringUtils.isEmpty(emailExpe) && StringUtils.isEmpty(smsExpe)	){
			log.debug("No hay que enviar, no existen direcciones de envio");
			return null;
		}
				
		// Obtenemos detalle elemento: aviso expediente o notificacion
		ElementoExpedienteItf detalleEle = DelegateUtil.getElementoExpedienteDelegate().obtenerDetalleElementoExpediente(ele.getCodigo());
				
		// Obtenemos propiedades organismo
		ConfiguracionDelegate cfd = DelegateUtil.getConfiguracionDelegate();
		OrganismoInfo oi = cfd.obtenerOrganismoInfo();
		String urlSistra = cfd.obtenerConfiguracion().getProperty("sistra.url");
		
		// Establecemos textos de email y SMS 
		String tituloEmail,textoEmail,textoSMS;		
		boolean verificarEnvioEmail = false;
		boolean verificarEnvioSms = false;
		if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){
			
			verificarEnvioEmail = confirmacionEnvioEventosEmail;
			verificarEnvioSms = confirmacionEnvioEventosSms;
			
			EventoExpediente evento = (EventoExpediente) detalleEle;
			
			// Obtenemos textos de ficheros de messages sustituyendo variables
			String urlEventoExpediente = LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.urlAcceso.eventoExpediente");
			urlEventoExpediente = StringUtil.replace(urlEventoExpediente,"{0}",urlSistra);
			tituloEmail=LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.titulo.eventoExpediente");
			tituloEmail=StringUtil.replace(tituloEmail,"{0}",oi.getNombre().toUpperCase());
			String literalTextoAccesoAviso;
			String paramsLiteralTextoAccesoAviso[] = null;
			if (ele.isAccesoAnonimoExpediente()) {
				paramsLiteralTextoAccesoAviso  = new String[1];
				paramsLiteralTextoAccesoAviso[0] = ele.getIdentificadorPersistencia();
				if (expe.getNifRepresentante() != null) {
					literalTextoAccesoAviso = "aviso.email.textoAcceso.eventoExpediente.certificadoclave";					
				} else {
					literalTextoAccesoAviso = "aviso.email.textoAcceso.eventoExpediente.clave";
				}	
			} else {
				literalTextoAccesoAviso = "aviso.email.textoAcceso.eventoExpediente.certificado";				
			}
			
			// Generamos mail a partir de plantilla
			textoEmail = cargarPlantillaMail("mailAviso.html");
			textoEmail = StringUtil.replace(textoEmail,"[#EXPEDIENTE#]",StringEscapeUtils.escapeHtml(expe.getIdExpediente() + " - " + expe.getDescripcion() ));
			textoEmail = StringUtil.replace(textoEmail,"[#UNIDAD_ADMINISTRATIVA#]", StringEscapeUtils.escapeHtml(Dominios.obtenerDescripcionUA(expe.getUnidadAdministrativa().toString())));
			textoEmail = StringUtil.replace(textoEmail,"[#FECHA#]", StringUtil.fechaACadena(evento.getFecha(),StringUtil.FORMATO_FECHA));
			textoEmail = StringUtil.replace(textoEmail,"[#TITULO#]",StringEscapeUtils.escapeHtml(evento.getTitulo()));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO#]",StringUtil.replace(StringEscapeUtils.escapeHtml(evento.getTexto()),"\n","</br>"));						
			textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.NOMBRE#]",oi.getNombre());
			textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.LOGO#]",oi.getUrlLogo());
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISOTRAMITACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.avisoTramitacion")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ORGANO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.organo")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.EXPEDIENTE#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.expediente")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.FECHAAVISO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.fechaAviso")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ASUNTO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.asunto")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.DESCRIPCION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.descripcion")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDERAVISO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.accederAviso")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.SOPORTE#]",LiteralesAvisosMovilidad.calcularTextoSoporte(oi, expe.getIdioma()));		
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDER#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),literalTextoAccesoAviso, paramsLiteralTextoAccesoAviso)));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDERCERTIFICADO#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.accederAviso.certificado")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDERCLAVE#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.accederAviso.clave")));
			if (expe.getNifRepresentante() != null) {
				textoEmail = StringUtil.replace(textoEmail,"[#URL_ACCESO_CERTIFICADO#]",urlEventoExpediente + evento.getIdentificadorPersistencia() + "&autenticacion=CU");
			}
			if (ele.isAccesoAnonimoExpediente()) {
				textoEmail = StringUtil.replace(textoEmail,"[#URL_ACCESO_CLAVE#]",urlEventoExpediente + evento.getIdentificadorPersistencia() + "&autenticacion=A");
			}
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AUTO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"email.correoAutomatico")));
			
			// Generamos SMS
			if (StringUtils.isNotEmpty(evento.getTextoSMS())){
				textoSMS = evento.getTextoSMS();
			}else{
				textoSMS = LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.sms.eventoExpediente");
				textoSMS = StringUtil.replace(textoSMS,"{0}",oi.getNombre().toUpperCase());
			}
					
		}else if (ele.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){
			
			verificarEnvioEmail = confirmacionEnvioNotificacionesEmail;
			verificarEnvioSms = confirmacionEnvioNotificacionesSms;
			
			NotificacionTelematica notif = (NotificacionTelematica) detalleEle;
			AvisoNotificacion aviso = getAvisoNotificacion(notif);
			
			// Obtenemos textos de ficheros de messages sustituyendo variables
			String urlNotifExpediente = LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.urlAcceso.notificacion");
			urlNotifExpediente = StringUtil.replace(urlNotifExpediente,"{0}",urlSistra);
			tituloEmail=LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.titulo.notificacion");
			tituloEmail=StringUtil.replace(tituloEmail,"{0}",oi.getNombre().toUpperCase());
			String literalTextoAccesoNotificacion;
			String paramsLiteralTextoNotificacion[] = null;
			if (ele.isAccesoAnonimoExpediente()) {
				paramsLiteralTextoNotificacion  = new String[1];
				paramsLiteralTextoNotificacion[0] = ele.getIdentificadorPersistencia();
				if (expe.getNifRepresentante() != null) {
					literalTextoAccesoNotificacion = "aviso.email.textoAcceso.notificacion.certificadoclave";
				} else {
					literalTextoAccesoNotificacion = "aviso.email.textoAcceso.notificacion.clave";
				}	
			} else {
				literalTextoAccesoNotificacion = "aviso.email.textoAcceso.notificacion.certificado";				
			}
			
			String controlEntregaNotif = cfd.obtenerConfiguracion().getProperty("notificaciones.controlEntrega.habilitar");
        	if (StringUtils.isBlank(controlEntregaNotif)) {
        		controlEntregaNotif = "false";
        	}
        	boolean controlEntregaHabilitado = Boolean.parseBoolean(controlEntregaNotif);        	
        	
        	String notaLegalNotificacion = "";
        	if (notif.isFirmarAcuse()) {
        		if (controlEntregaHabilitado) {
    				notaLegalNotificacion = "aviso.email.notaLegal.notificacion.controlEntregaHabilitado";			
    			} else {
    				notaLegalNotificacion = "aviso.email.notaLegal.notificacion.controlEntregaDeshabilitado";
    			}
        	} 
        				
			// Textos Email
			textoEmail = cargarPlantillaMail("mailNotificacion.html");
			textoEmail = StringUtil.replace(textoEmail,"[#EXPEDIENTE#]",StringEscapeUtils.escapeHtml(expe.getIdExpediente() + " - " + expe.getDescripcion() ));
			textoEmail = StringUtil.replace(textoEmail,"[#UNIDAD_ADMINISTRATIVA#]", StringEscapeUtils.escapeHtml(Dominios.obtenerDescripcionUA(expe.getUnidadAdministrativa().toString())));
			textoEmail = StringUtil.replace(textoEmail,"[#FECHA#]", StringUtil.fechaACadena(notif.getFechaRegistro(),StringUtil.FORMATO_FECHA));
			textoEmail = StringUtil.replace(textoEmail,"[#TITULO#]",StringEscapeUtils.escapeHtml(aviso.getTitulo()));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO#]",StringUtil.replace(StringEscapeUtils.escapeHtml(aviso.getTexto()),"\n","</br>"));									
			textoEmail = StringUtil.replace(textoEmail,"[#NOTA_LEGAL#]",notaLegalNotificacion.equals("")?"":StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),notaLegalNotificacion)));			
			textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.NOMBRE#]",oi.getNombre());
			textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.LOGO#]",oi.getUrlLogo());
			
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISOTRAMITACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.avisoTramitacion")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISONOTIFICACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.avisoNotificacion")));			
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ORGANO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.organo")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.EXPEDIENTE#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.expediente")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.FECHAAVISO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.fechaAviso")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ASUNTO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.asunto")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.DESCRIPCION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.descripcion")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.SOPORTE#]",LiteralesAvisosMovilidad.calcularTextoSoporte(oi, expe.getIdioma()));
			
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.DESTINATARIO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.destinatario")));
			textoEmail = StringUtil.replace(textoEmail,"[#DESTINATARIO#]",StringEscapeUtils.escapeHtml(notif.getNifRepresentante() + " - " + notif.getNombreRepresentante()));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDER#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),literalTextoAccesoNotificacion, paramsLiteralTextoNotificacion)));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDERCERTIFICADO#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.accederNotificacion.certificado")));
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.ACCEDERCLAVE#]", StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.email.cuerpo.accederNotificacion.clave")));
			if (expe.getNifRepresentante() != null) {
				textoEmail = StringUtil.replace(textoEmail,"[#URL_ACCESO_CERTIFICADO#]",urlNotifExpediente + notif.getIdentificadorPersistencia() + "&autenticacion=CU");
			}
			if (ele.isAccesoAnonimoExpediente()) {
				textoEmail = StringUtil.replace(textoEmail,"[#URL_ACCESO_CLAVE#]",urlNotifExpediente + notif.getIdentificadorPersistencia() + "&autenticacion=A");
			}
			textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AUTO#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"email.correoAutomatico")));
			// Textos SMS
			if (StringUtils.isNotEmpty(aviso.getTextoSMS())){
				textoSMS = aviso.getTextoSMS();
			}else{
				textoSMS = LiteralesAvisosMovilidad.getLiteral(expe.getIdioma(),"aviso.sms.notificacion");				
				textoSMS = StringUtil.replace(textoSMS,"{0}",oi.getNombre().toUpperCase());
			}
 		}else{
			throw new Exception("Tipo de elemento " + ele.getTipoElemento() + " no debe generar aviso");
		}
		
		// Quitamos parte sobrante
		if (ele.isAccesoAnonimoExpediente()) {
			if (expe.getNifRepresentante() != null) {
				textoEmail = mantenerSeccion(textoEmail, "[#IF.CERTIFICADOCLAVE#]");
				textoEmail = eliminarSeccion(textoEmail, "[#IF.CERTIFICADO#]");
				textoEmail = eliminarSeccion(textoEmail, "[#IF.CLAVE#]");
			} else {
				textoEmail = mantenerSeccion(textoEmail, "[#IF.CLAVE#]");
				textoEmail = eliminarSeccion(textoEmail, "[#IF.CERTIFICADOCLAVE#]");
				textoEmail = eliminarSeccion(textoEmail, "[#IF.CERTIFICADO#]");;
			}	
		} else {
			textoEmail = mantenerSeccion(textoEmail, "[#IF.CERTIFICADO#]");
			textoEmail = eliminarSeccion(textoEmail, "[#IF.CLAVE#]");
			textoEmail = eliminarSeccion(textoEmail, "[#IF.CERTIFICADOCLAVE#]");							
		}
		
		//	Creamos MensajeEnvio para informar de la creacion del expediente
		log.debug("Creamos mensajeEnvio");
		MensajeEnvio mens = new MensajeEnvio();
		mens.setNombre("Aviso automático de creacion elemento expediente " + expe.getUnidadAdministrativa() + " - " + expe.getIdExpediente());
		mens.setCuentaEmisora(cuentaSistra);
		mens.setInmediato(true);
		
		// Generamos en mensajes distintos para poder verificar envio
		if (StringUtils.isNotEmpty(emailZP)){
			log.debug("Creamos mensaje email para " + emailZP);
			MensajeEnvioEmail mensEmail = new MensajeEnvioEmail();
			String dest [] = {emailZP};
			mensEmail.setDestinatarios(dest);
			mensEmail.setTitulo(tituloEmail);
			mensEmail.setTexto(textoEmail);
			mensEmail.setHtml(true);
			mensEmail.setVerificarEnvio(verificarEnvioEmail);
			mens.addEmail(mensEmail);			
		}
		if (StringUtils.isNotEmpty(emailExpe) && !emailExpe.equals(emailZP)){
			log.debug("Creamos mensaje email para " + emailExpe);
			MensajeEnvioEmail mensEmail = new MensajeEnvioEmail();
			String dest [] = {emailExpe};
			mensEmail.setDestinatarios(dest);
			mensEmail.setTitulo(tituloEmail);
			mensEmail.setTexto(textoEmail);
			mensEmail.setHtml(true);
			mensEmail.setVerificarEnvio(verificarEnvioEmail);
			mens.addEmail(mensEmail);			
		}
		if (StringUtils.isNotEmpty(smsZP)){
			log.debug("Creamos mensaje sms para " + smsZP);
			MensajeEnvioSms mensSMS = new MensajeEnvioSms();
			String dest [] = {smsZP};
			mensSMS.setDestinatarios(dest);
			mensSMS.setTexto(textoSMS);
			mensSMS.setVerificarEnvio(verificarEnvioSms);
			mens.addSMS(mensSMS);
		}
		if (StringUtils.isNotEmpty(smsExpe) && !smsExpe.equals(smsZP)){
			log.debug("Creamos mensaje sms para " + smsExpe);
			MensajeEnvioSms mensSMS = new MensajeEnvioSms();
			String dest [] = {smsExpe};
			mensSMS.setDestinatarios(dest);
			mensSMS.setTexto(textoSMS);
			mensSMS.setVerificarEnvio(verificarEnvioSms);
			mens.addSMS(mensSMS);
		}
		
		// Realizamos envio del mensaje
		return enviarMensaje(mens);
		
	}
	

	//--------------------------------------------------------------------------
	// FUNCIONES DE UTILIDAD
	//--------------------------------------------------------------------------

	private String eliminarSeccion(String textoEmail, String seccion) {
		int pos = textoEmail.indexOf(seccion);
		if (pos == -1) return textoEmail;
		int posFin = textoEmail.indexOf(seccion, pos + seccion.length());
		
		textoEmail = textoEmail.substring(0, pos) + textoEmail.substring(posFin + seccion.length());
		
		return textoEmail;
	}
	
	private String mantenerSeccion(String textoEmail, String seccion) throws Exception {
		return StringUtil.replace(textoEmail, seccion, "");
	}
	
	/**
	 * Realizamos envio del mensaje al modulo de movilidad con usuario auto  
	 */
	private String enviarMensaje(MensajeEnvio mensaje) throws Exception{
		// Realizamos envio
		MobTraTelDelegate mob = DelegateMobTraTelUtil.getMobTraTelDelegate();
		return mob.envioMensaje(mensaje);			
	}
	
	/**
	 * Obtiene aviso de notificacion
	 * @param notificacion
	 * @return
	 * @throws Exception
	 */
	private AvisoNotificacion getAvisoNotificacion( NotificacionTelematica notificacion ) throws Exception
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			ReferenciaRDS referenciaRDS = new ReferenciaRDS();
			referenciaRDS.setCodigo( notificacion.getCodigoRdsAviso() );
			referenciaRDS.setClave( notificacion.getClaveRdsAviso() );
			
			RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
			docRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );						
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS el aviso de notificacion",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLAvisoNotificacion factoriaAvisoNotificacion = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
			bis = new ByteArrayInputStream(docRDS.getDatosFichero());
			AvisoNotificacion avisoNotificacion = factoriaAvisoNotificacion.crearAvisoNotificacion(bis);
			return avisoNotificacion;
		}catch (Exception ex){
			throw new Exception("Error parseando aviso notificacion",ex);
		}finally{
			bis.close();
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
			InputStream is = AvisosMovilidad.class.getResourceAsStream(idPlantilla);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();			
			ConvertUtil.copy(is,bos);
			String plantilla = new String(bos.toByteArray());
			plantillas.put(idPlantilla,plantilla);
		}
		return (String) plantillas.get(idPlantilla);
	}
	
	
}
