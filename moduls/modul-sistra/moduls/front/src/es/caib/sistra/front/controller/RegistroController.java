package es.caib.sistra.front.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.ConvertUtil;
import es.caib.util.StringUtil;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;

public class RegistroController extends FinalizacionController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		//super.execute( tileContext, request, response, servletContext );
		TramiteFront tramite 			= this.getTramiteFront( request );
		Map params = this.getParametros(request);
	
		
		//
		// 	COMPROBAMOS SI HAY FLUJO DE TRAMITACION Y HAY QUE PASAR EL TRAMITE
		//
		if (tramite.isFlujoTramitacion()) {			
			// Si hay que pasarlo a algún Nif lo indicamos:
			String nifFlujo=tramite.getFlujoTramitacionNif();
			if (!StringUtils.isEmpty(nifFlujo)){
				request.setAttribute("pasarFlujoTramitacion",nifFlujo);
				return;
			}				
		}
		
		//
		// 	COMPROBAMOS SI HAY DELEGACION Y HAY QUE PASAR EL TRAMITE
		//
		if (tramite.getPasoTramitacion().getCompletado().equals(PasoTramitacion.ESTADO_PENDIENTE_DELEGACION_PRESENTACION)) {
			request.setAttribute("pendienteDelegacionPresentacion","true");
			return;
		}
		
		//
		// 	COMPROBAMOS SI TENEMOS QUE PEDIR CONFIRMACION PARA NOTIFICACION TELEMATICA
		//
		String avisosObligNotif = (String) request.getSession().getServletContext().getAttribute(Constants.AVISOS_OBLIGATORIOS_NOTIFICACIONES);
		
		if (!ConstantesSTR.NOTIFICACIONTELEMATICA_NOPERMITIDA.equals(tramite.getHabilitarNotificacionTelematica())){
			
			// Pendiente de confirmar notificacion
			if (tramite.getSeleccionNotificacionTelematica() == null){
				request.setAttribute("seleccionNotificacionTelematica","true");
				request.setAttribute("confirmarSeleccionNotificacionTelematica","true");				

				// Indicamos si la notificacion es obligatoria
				request.setAttribute("notificacionObligatoria",ConstantesSTR.NOTIFICACIONTELEMATICA_OBLIGATORIA.equals(tramite.getHabilitarNotificacionTelematica())?"true":"false");
				
				// Si son obligatorios los avisos establecemos email/sms por defecto 				
				if ("true".equals(avisosObligNotif)) {
					request.setAttribute("seleccionAvisos","true");
					request.setAttribute( "permitirAvisoSMS",Boolean.toString(tramite.isPermiteSMS()) );
					request.setAttribute("emailAvisoDefault", params.get("emailAvisoDefault"));
					request.setAttribute("smsAvisoDefault", params.get("smsAvisoDefault"));
				} else {
					request.setAttribute("seleccionAvisos","false");
				}
				
				// Evitamos que continue hasta que se confirme la notificacion
				return;
				
			} else {
			// Notificacion confirmada	
				request.setAttribute("seleccionNotificacionTelematica",tramite.getSeleccionNotificacionTelematica().toString());
				if ("true".equals(avisosObligNotif)) {
					request.setAttribute("seleccionAvisos","true");
					if (StringUtils.isNotBlank(tramite.getSeleccionEmailAviso())) {
						request.setAttribute("seleccionEmailAviso",tramite.getSeleccionEmailAviso());
					}
					if (StringUtils.isNotBlank(tramite.getSeleccionSmsAviso())) {
						request.setAttribute("seleccionSmsAviso",tramite.getSeleccionSmsAviso());
					}
				} else {
					request.setAttribute("seleccionAvisos","false");
				}
			}
		} else {
			request.setAttribute("seleccionNotificacionTelematica", "false");
		}			
		
		//
		// 	COMPROBAMOS SI TENEMOS QUE PEDIR VERIFICACION MOVIL 
		//	(antes debe ir la confirmacion de notificacion telematica)
		// 
		if (tramite.isVerificarMovil() && !tramite.isVerificadoMovil()) {
			// Marcamos para que pida la confirmacion			
			request.setAttribute("verificarMovil","true");
			// Evitamos que continue hasta que se confirme la notificacion
			return;			
		}
		
		
		//
		//	EXTRAEMOS ETIQUETAS SEGUN CIRCUITO
		//
		
		char tipoTramitacion 			= tramite.getTipoTramitacion();
		char tipoTramitacionDependiente = tramite.getTipoTramitacionDependiente();
		boolean registro 				= tramite.getRegistrar();
		
		String tituloKey 		= registro ? "registro.titulo.registro" : "registro.titulo.envio";
		String botonKey 		= registro ? "registro.boton.registro" 	: "registro.boton.envio";
		String instruccionesKey = registro ? "registro.instrucciones.registro" : "registro.instrucciones.envio" ;
		String importanteKey 	= registro ? "registro.importante.registro" : "registro.importante.envio" ;
		String suffix = "";
			
		if ( Constants.TIPO_CIRCUITO_TRAMITACION_DEPENDE == tipoTramitacion  )
		{
			suffix = Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacionDependiente ? ".presencial" : ".telematico";
			 
		}
		else
		{
			suffix = Constants.TIPO_CIRCUITO_TRAMITACION_PRESENCIAL == tipoTramitacion ?  ".presencial" : ".telematico";
		}
		
		instruccionesKey += suffix;
		importanteKey 	 += suffix;
		
		request.setAttribute( "tituloKey", tituloKey );
		request.setAttribute( "instruccionesKey", instruccionesKey );
		request.setAttribute( "importanteKey", importanteKey );
		request.setAttribute( "botonKey", botonKey );
						
		//
		// 	EXTRAEMOS INFO PARA MOSTRAR RESUMEN TRAMITE
		//		
		
		AsientoCompleto asiento = (AsientoCompleto) this.getParametros( request ).get( "asiento" );
		String asientoB64 = ConvertUtil.cadenaToBase64UrlSafe( asiento.getAsiento() );
	  			
	  	Instrucciones instrucciones = obtenerInstrucciones( asiento );
	  	request.setAttribute( "instrucciones", instrucciones );
		request.setAttribute( "asiento", asientoB64 );
		request.setAttribute( "fechaTopeEntrega", StringUtil.obtenerCadenaPorDefecto( StringUtil.fechaACadena( instrucciones.getFechaTopeEntrega()), "" ) );				
		
		DatosInteresado representante = obtenerRepresentante( asiento );
		if ( representante != null )
		{
			request.setAttribute( "representante", representante );
		}
		
		// Controlamos si el trámite debe firmarse digitalmente:
		// - inicio de sesión con certificado, el tramite requiere firma y el tipo de tramitación es telemática
		String mostrarFirmaDigital="N";
		if  (	tramite.getDatosSesion().getNivelAutenticacion() == 'C' &&
				tramite.getFirmar() &&
				((tramite.getTipoTramitacion() == 'T' || (tramite.getTipoTramitacion() == 'D' && tramite.getTipoTramitacionDependiente() != 'P')))
			){
			mostrarFirmaDigital="S";
		}		
		request.setAttribute(Constants.MOSTRAR_FIRMA_DIGITAL,mostrarFirmaDigital);
		// - Nif firmante para plugin firma web
		String nifFirmante = "";
		if (mostrarFirmaDigital.equals("S")) {
			if (tramite.getDatosSesion().getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
				String perfilAcceso = tramite.getDatosSesion().getPerfilAcceso();
				if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(perfilAcceso)) {
					nifFirmante = tramite.getDatosSesion().getNifDelegado();
				} else {
					nifFirmante = tramite.getDatosSesion().getNifUsuario();
				}
			}
		}
		request.setAttribute("nifFirmante",nifFirmante);
	
				
		// Comprobamos si:
		//	- existe algún pago realizado	
		String existenPagos = "N";		
		for (Iterator it=tramite.getPagos().iterator();it.hasNext();){			
			DocumentoFront pago = (DocumentoFront) it.next();
			if (pago.getEstado() == 'S') {
				existenPagos = "S";
				break;
			}
		}		
		request.setAttribute( "existenPagos", existenPagos);
		
		//  Comprobamos si:
		//	- existe algún documento anexado	
		String existenAnexosTelematicos = "N";		
		for (Iterator it=tramite.getAnexos().iterator();it.hasNext();){			
			DocumentoFront anexo = (DocumentoFront) it.next();
			if (anexo.getEstado() == 'S' && anexo.isAnexoPresentarTelematicamente() && anexo.getObligatorio() != 'D') {
				existenAnexosTelematicos = "S";
				break;
			}
		}	
		request.setAttribute( "existenAnexosTelematicos", existenAnexosTelematicos);
		
		
		// Controlamos si el trámite es presencial	
		String presencial = "false";
		if  (  (tramite.getTipoTramitacion() == 'P') ||
			   (tramite.getTipoTramitacion() == 'D' && tramite.getTipoTramitacionDependiente() == 'P')
			)
		{
				presencial="true";
		}
		request.setAttribute( "presencial", presencial );
				
		// Indicamos que permitimos registrar (no hay que flujo ni hay que confirmar la notificacion)
		request.setAttribute( "permitirRegistrar", "true" );
	
		// Registro automatico
		if (tramite.isRegistroAutomatico()) {
			request.setAttribute( "registroAutomatico", "true" );
		}
		
	}
	
	
	
}
