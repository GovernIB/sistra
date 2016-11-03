package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.CambiarEstadoTramiteForm;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

/**
 * @struts.action
 *  name="cambiarEstadoTramiteForm"
 *  path="/cambiarEstadoTramite"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/detalleTramite.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class CambiarEstadoTramiteAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		CambiarEstadoTramiteForm formulario 	= ( CambiarEstadoTramiteForm ) form;
		TramiteBandejaDelegate tramiteDelegate 	= DelegateUtil.getTramiteBandejaDelegate();		
		TramiteBandeja tramite 					= tramiteDelegate.obtenerTramiteBandeja( formulario.getCodigo() );
		
		// Comprobamos que no haya habido un cambio de estado mientras tanto
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		if (tramite.getProcesada() != formulario.getEstadoOld()){
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.estadoHaCambiado"));			
			return mapping.findForward( "fail" );
		}
		
		tramiteDelegate.procesarEntrada(tramite.getNumeroEntrada(),Character.toString(formulario.getEstado()));
		
		// return mapping.findForward( "success" );
		
		response.sendRedirect("detalleTramite.do?codigo="+formulario.getCodigo().longValue());
		return null;
		
    }

}
