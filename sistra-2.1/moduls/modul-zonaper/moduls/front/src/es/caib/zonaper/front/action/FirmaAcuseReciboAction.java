package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.util.ConvertUtil;
import es.caib.zonaper.front.form.AbrirNotificacionForm;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.NotificacionTelematicaDelegate;

/**
 * @struts.action
 *  name="abrirNotificacionForm"
 *  path="/protected/abrirNotificacion"
 *  scope="request"
 *  validate="false"
 *   
 *  @struts.action-forward
 *  	name="fail" path=".detalleNotificacionPendiente"
 *   
 */
public class FirmaAcuseReciboAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( ActualizarAlertasAction.class );

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AbrirNotificacionForm formulario = ( AbrirNotificacionForm ) form;
		
		// Convertimos asiento y firma
		String asientoXML = null;
		FirmaIntf firmaDigital = null;
		if (StringUtils.isNotEmpty(formulario.getAsiento())){
			asientoXML 	= ConvertUtil.base64UrlSafeToCadena( formulario.getAsiento() );
		}
		if ("CERTIFICADO".equals(formulario.getTipoFirma()) && StringUtils.isNotEmpty(formulario.getFirma())){
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			firmaDigital =  plgFirma.parseFirmaFromHtmlForm( formulario.getFirma() );
		}
		
		// Obtenemos notificacion y elemento expediente asociado
		NotificacionTelematicaDelegate notificacionDelegate = DelegateUtil.getNotificacionTelematicaDelegate();
		NotificacionTelematica notificacion;
		ElementoExpediente elementoExpediente;
		if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
			notificacion = notificacionDelegate.obtenerNotificacionTelematicaAnonima(formulario.getCodigo(),this.getIdPersistencia(request));					
			elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAnonimo(ElementoExpediente.TIPO_NOTIFICACION,notificacion.getCodigo(),this.getIdPersistencia(request));
		}else{
			notificacion = notificacionDelegate.obtenerNotificacionTelematicaAutenticada(formulario.getCodigo());			
			elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpedienteAutenticado(ElementoExpediente.TIPO_NOTIFICACION,notificacion.getCodigo());
		}
		
		// Realizamos firma del acuse
		try{						
			boolean result;
			if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
				if ("CERTIFICADO".equals(formulario.getTipoFirma())) {
					result = notificacionDelegate.firmarAcuseReciboNotificacionAnonima(formulario.getCodigo(),this.getIdPersistencia(request),asientoXML,firmaDigital);
				} else {
					result = notificacionDelegate.firmarAcuseReciboNotificacionAnonima(formulario.getCodigo(),this.getIdPersistencia(request),asientoXML,formulario.getFirma());
				}
			}else{
				if ("CERTIFICADO".equals(formulario.getTipoFirma())) {
					result = notificacionDelegate.firmarAcuseReciboNotificacionAutenticada(formulario.getCodigo(),asientoXML,firmaDigital);
				} else {
					result = notificacionDelegate.firmarAcuseReciboNotificacionAutenticada(formulario.getCodigo(),asientoXML,formulario.getFirma());
				}
							
			}		
				
			// Comprobamos si la firma es valida
			if (!result){				
				// Volvemos a mostrar la notificacion
				request.setAttribute("notificacion",notificacion);
				request.setAttribute("elementoExpediente",elementoExpediente);
				request.setAttribute("tipoFirma",formulario.getTipoFirma());
				if ("CERTIFICADO".equals(formulario.getTipoFirma())) {
					if (this.getDatosSesion(request).getPerfilAcceso().equals(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO)){
						request.setAttribute( "messageKey", "detalleNotificacion.firmanteNoAdecuado.delegado" );
					}else{
						request.setAttribute( "messageKey", "detalleNotificacion.firmanteNoAdecuado" );
					}
				} else {
					request.setAttribute( "messageKey", "detalleNotificacion.claveFirmaNoCoincide" );
				}
				return mapping.findForward( "fail" );
			}
		}catch (Exception ex){
			_log.error("Error firmando acuse",ex);
			request.setAttribute("notificacion",notificacion);
			request.setAttribute("elementoExpediente",elementoExpediente);
			request.setAttribute( "messageKey", "detalleNotificacion.firmaNoValida" );
			return mapping.findForward( "fail" );
		}
					
		// Redirigimos a mostrar elemento
		response.sendRedirect(request.getContextPath() + "/protected/mostrarDetalleElemento.do?codigo=" + notificacion.getCodigo() + "&tipo=" + ElementoExpediente.TIPO_NOTIFICACION + "&expediente=true");
		return null;
		
	}
	
	
	
	
	
	
}
