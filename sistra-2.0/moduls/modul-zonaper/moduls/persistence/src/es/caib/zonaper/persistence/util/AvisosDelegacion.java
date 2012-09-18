package es.caib.zonaper.persistence.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.mobtratel.modelInterfaz.MensajeEnvio;
import es.caib.mobtratel.modelInterfaz.MensajeEnvioEmail;
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
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.persistence.delegate.ConfiguracionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * Contiene la logica de generacion de avisos de delegacion de un tramite. 
 * Se ejecutaran con el usuario auto. 
 * 
 * Utilizado desde AvisosDelegacionFacadeEJB 
 *
 */
public class AvisosDelegacion {

	private static AvisosDelegacion avisos;
	private static String cuentaSistra;
	private static Log log = LogFactory.getLog(AvisosDelegacion.class);
	private static Map plantillas = new HashMap();
	
	private AvisosDelegacion(){
		
	}
	
	public static AvisosDelegacion getInstance() throws Exception{		
		if (avisos == null){
			avisos = new AvisosDelegacion();
			cuentaSistra = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("avisos.cuentaEnvio");
			if (StringUtils.isEmpty(cuentaSistra)) throw new Exception("No se ha especificado cuenta envío");
			
		}
		return avisos;
	}			
	
	/**
    * Realiza aviso de que un tramite esta pendiente de registrarse   
    */
	public void avisarPendientePresentacionTramite(String entidadMail, String idioma,  String entidadDesc, String tramiteDesc) throws Exception
	{
		String mensajeDelega=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.pendientePresentacionTramite");
		mensajeDelega=StringUtil.replace(mensajeDelega,"{0}",tramiteDesc);
		mensajeDelega=StringUtil.replace(mensajeDelega,"{1}",entidadDesc);		
		enviarAvisoDelegacion(entidadMail, idioma, mensajeDelega);			
	}
	
	/**
     * Realiza aviso de que un tramite esta pendiente de firmar documentos
	 * @throws Exception 
     */
	public void avisarPendienteFirmarDocumentos(String entidadMail, String idioma,String entidadDesc, String tramiteDesc,List documentos,List firmantes) throws Exception
	{
		String mensajeDelega=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.pendienteFirmarDocumentos.inicio");
		mensajeDelega=StringUtil.replace(mensajeDelega,"{0}",tramiteDesc);
		mensajeDelega=StringUtil.replace(mensajeDelega,"{1}",entidadDesc);	
		
		StringBuffer sb = new StringBuffer(documentos.size() * 200);
		sb.append("<ul>");
		for (int i=0;i < documentos.size();i++){
			String documento = (String) documentos.get(i);
			sb.append("<li>");
			sb.append(documento).append(" - ").append(firmantes.get(i));
			sb.append("</li>");
		}
		sb.append("</ul>");
		
		mensajeDelega += sb.toString() + LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.pendienteFirmarDocumentos.fin");
		
		enviarAvisoDelegacion(entidadMail, idioma, mensajeDelega);		
	}
	
	/**
     * Realiza aviso de que un delegado ha rechazado un documento para firmar
	 * @throws Exception 
     */
	public void avisarRechazoDocumento(String entidadMail, String idioma,String entidadDesc, String tramiteDesc, String documentoDesc, String firmante) throws Exception
	{
		String mensajeDelega=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.rechazoFirmaDocumento");
		mensajeDelega=StringUtil.replace(mensajeDelega,"{0}",firmante);
		mensajeDelega=StringUtil.replace(mensajeDelega,"{1}",documentoDesc);	
		mensajeDelega=StringUtil.replace(mensajeDelega,"{2}",tramiteDesc);
		mensajeDelega=StringUtil.replace(mensajeDelega,"{3}",entidadDesc);
		enviarAvisoDelegacion(entidadMail, idioma, mensajeDelega);		
	}
	
	/**
     * Realiza aviso de que ya se han firmado/rechazado todos los documentos pendientes y se puede
     * retomar el tramite
	 * @throws Exception 
     */
	public void avisarContinuarTramite(String entidadMail, String idioma,String entidadDesc, String tramiteDesc) throws Exception
	{
		String mensajeDelega=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.continuarTramite");
		mensajeDelega=StringUtil.replace(mensajeDelega,"{0}",tramiteDesc);
		mensajeDelega=StringUtil.replace(mensajeDelega,"{1}",entidadDesc);		
		enviarAvisoDelegacion(entidadMail, idioma, mensajeDelega);		
	}
	
	
	
	//--------------------------------------------------------------------------
	// FUNCIONES DE UTILIDAD
	//--------------------------------------------------------------------------
	private void enviarAvisoDelegacion(String entidadMail, String idioma,  String mensajeDelega) throws Exception{
		ConfiguracionDelegate cfd = DelegateUtil.getConfiguracionDelegate();
		OrganismoInfo oi = cfd.obtenerOrganismoInfo();
		String tituloEmail=LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.delegacion.titulo");
		tituloEmail=StringUtil.replace(tituloEmail,"{0}",oi.getNombre().toUpperCase());
		
		String textoEmail = cargarPlantillaMail("mailAvisoDelegacion.html");
		textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.NOMBRE#]",oi.getNombre());
		textoEmail = StringUtil.replace(textoEmail,"[#ORGANISMO.LOGO#]",oi.getUrlLogo());
		textoEmail = StringUtil.replace(textoEmail,"[#AVISODELEGACION.MENSAJE#]",mensajeDelega);
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISOTRAMITACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.avisoTramitacion")));
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.AVISODELEGACION#]",StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.avisoDelegacion")));
		textoEmail = StringUtil.replace(textoEmail,"[#TEXTO.SOPORTE#]",LiteralesAvisosMovilidad.calcularTextoSoporte(oi, idioma));
		
		// Creamos MensajeEnvio
		log.debug("Creamos mensajeEnvio");
		MensajeEnvio mens = new MensajeEnvio();
		mens.setNombre("Aviso automático de aviso delegacion");
		mens.setCuentaEmisora(cuentaSistra);
		mens.setInmediato(true);
		MensajeEnvioEmail mensEmail = new MensajeEnvioEmail();
		String dest [] = {entidadMail};
		mensEmail.setDestinatarios(dest);
		mensEmail.setTitulo(tituloEmail);
		mensEmail.setTexto(textoEmail);
		mensEmail.setHtml(true);
		mens.addEmail(mensEmail);
		enviarMensaje(mens);
	}
	
	/**
	 * Realizamos envio del mensaje al modulo de movilidad con usuario auto  
	 */
	private void enviarMensaje(MensajeEnvio mensaje) throws Exception{
		// Realizamos envio
		MobTraTelDelegate mob = DelegateMobTraTelUtil.getMobTraTelDelegate();
		mob.envioMensaje(mensaje);			
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
			InputStream is = AvisosDelegacion.class.getResourceAsStream(idPlantilla);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();			
			ConvertUtil.copy(is,bos);
			String plantilla = new String(bos.toByteArray());
			plantillas.put(idPlantilla,plantilla);
		}
		return (String) plantillas.get(idPlantilla);
	}
	
	
}
