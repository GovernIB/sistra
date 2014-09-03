package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.form.TramiteSubsanacionForm;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.NotificacionTelematicaDelegate;

/**
 * @struts.action
 *  name="tramiteSubsanacionForm"
 *  path="/protected/tramiteSubsanacion"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class TramiteSubsanacionAction extends BaseAction
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		TramiteSubsanacionForm formulario = ( TramiteSubsanacionForm ) form;
		NotificacionTelematica not;
		NotificacionTelematicaDelegate nd = DelegateUtil.getNotificacionTelematicaDelegate();
		Long codNotif = new Long(formulario.getCodigoNotificacion());
		String key = null;
		if (this.getDatosSesion(request).getNivelAutenticacion() == 'A'){
			not = nd.obtenerNotificacionTelematicaAnonima(codNotif,this.getIdPersistencia(request));
			key = nd.iniciarTramiteSubsanacionNotificacionAnonima(codNotif,this.getIdPersistencia(request));
		}else{
			not = nd.obtenerNotificacionTelematicaAutenticada(codNotif);
			key = nd.iniciarTramiteSubsanacionNotificacionAutenticada(codNotif);
		}
		
		if(key != null){
			// Redirigimos a asistente de tramitacion pasandole los parametros de inicio de la notificacion
			StringBuffer sb = new StringBuffer(500);
			sb.append("/sistrafront/inicio?language=").append(this.getLang(request))
					.append("&modelo=").append(not.getTramiteSubsanacionIdentificador())
					.append("&version=").append(not.getTramiteSubsanacionVersion())
					.append("&").append(ConstantesZPE.SUBSANACION_PARAMETER_KEY).append("=").append(key)
					.append("&").append("perfilAF=");
			

			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(this.getDatosSesion(request).getPerfilAcceso())) {
				sb.append(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO).append("&")
					.append("entidadAF=").append(this.getDatosSesion(request).getNifEntidad());													
			}else{
				sb.append(ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO);
			}
			
			response.sendRedirect(sb.toString());
			return null;
		}
		
		return mapping.findForward( "fail" );
		
		
		
	}

}
