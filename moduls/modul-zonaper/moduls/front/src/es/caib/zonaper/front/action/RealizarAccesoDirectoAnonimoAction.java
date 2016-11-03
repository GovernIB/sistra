package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.AccesoDirectoAnonimoForm;

/**
 * @struts.action
 *  path="/protected/realizarAccesoDirectoAnonimo"
 *  name="accesoDirectoAnonimoForm"
 *  
 *  @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class RealizarAccesoDirectoAnonimoAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Cargamos id persistencia en sesion e intentamos acceder al elemento
		AccesoDirectoAnonimoForm formulario = ( AccesoDirectoAnonimoForm ) form;
		if ( StringUtils.isEmpty( formulario.getIdPersistencia() ) )
		{
			request.setAttribute(Constants.MENSAJE_TEXTO, "errors.tramiteNoExiste" );
			return mapping.findForward( "fail" );
		}
		this.setIdPersistencia(request,formulario.getIdPersistencia());		
		
		// Reenviamos a elemento
		response.sendRedirect(request.getContextPath() + "/protected/mostrarDetalleElemento.do?codigo=" + formulario.getCodigo() + "&tipo=" + formulario.getTipo() + "&expediente=true");
		return null;
	}

}
