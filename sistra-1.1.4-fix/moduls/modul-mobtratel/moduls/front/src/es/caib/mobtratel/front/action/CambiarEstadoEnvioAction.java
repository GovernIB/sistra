package es.caib.mobtratel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.form.CambiarEstadoEnvioForm;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;

/**
 * @struts.action
 *  name="cambiarEstadoEnvioForm"
 *  path="/cambiarEstadoEnvio"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/detalleEnvio.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class CambiarEstadoEnvioAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		CambiarEstadoEnvioForm formulario 	= ( CambiarEstadoEnvioForm ) form;
		EnvioDelegate delegate 	= DelegateUtil.getEnvioDelegate();
		
		// Comprobamos si se esta enviando
		if(delegate.isEnviando(formulario.getCodigo()))
		{
			request.setAttribute( "messageKey", "error.enEnvio" );
			return mapping.findForward( "success" );
		}
		
		// Si no se esta enviando lo marcamos como cancelado
		delegate.cancelarEnvio(formulario.getCodigo());		
		return mapping.findForward( "success" );
    }

}
