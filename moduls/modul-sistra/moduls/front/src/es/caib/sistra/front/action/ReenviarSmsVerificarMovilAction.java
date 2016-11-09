package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="verificarMovilForm"
 *  validate="true"
 *  path="/protected/reenviarSmsVerificarMovil"
 *  scope="request"
 *  input=".mainLayout"
 *
 *  @struts.action-forward
 *  name="success" path="/protected/irAPaso.do"
 *  
 * @struts.action-forward
 *  name="fail" path="/index.jsp"
 */
public class ReenviarSmsVerificarMovilAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		delegate.resetCodigoSmsVerificarMovil();
		request.setAttribute("reenviadoVerificacionMovil", "true");
		return mapping.findForward("success");
    }
	
}
