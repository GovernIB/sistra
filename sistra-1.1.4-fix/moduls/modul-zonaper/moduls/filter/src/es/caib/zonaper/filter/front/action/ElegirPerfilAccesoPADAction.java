package es.caib.zonaper.filter.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.filter.AuthFilterServlet;
import es.caib.zonaper.filter.front.form.ElegirPerfilAccesoPADForm;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;

/**
 * @struts.action
 *  name="elegirPerfilAccesoPADForm"
 *  path="/elegirPerfilAccesoPAD"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="failValidation" path="/perfilAccesoPAD.do"
 */
public class ElegirPerfilAccesoPADAction extends Action
{
	private static Log _log = LogFactory.getLog( ElegirPerfilAccesoPADAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ElegirPerfilAccesoPADForm formulario = ( ElegirPerfilAccesoPADForm ) form;
		
		_log.debug("Perfil seleccionado: " + formulario.getPerfil());
		
		StringBuffer sbAuthAction = new StringBuffer(formulario.getUrlOriginal());
		sbAuthAction.append( sbAuthAction.indexOf( "?" ) >= 0 ? "&" : "?" ).append(AuthFilterServlet.PARAMETER_PERFIL_KEY + "=" ).append(formulario.getPerfil());
		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(formulario.getPerfil())){
			sbAuthAction.append("&" + AuthFilterServlet.PARAMETER_ENTIDAD_KEY + "=").append(formulario.getNifEntidad());
			_log.debug("Entidad seleccionada: " + formulario.getNifEntidad());
		}
		
		response.sendRedirect( sbAuthAction.toString() );
		
		return null;
		
	}
	
		
}
