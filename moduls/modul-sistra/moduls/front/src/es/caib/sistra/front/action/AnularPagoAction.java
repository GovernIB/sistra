package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.form.AnularPagoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;


/**
 * @struts.action
 *  name="anularPagoForm"
 *  path="/protected/anularPago"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class AnularPagoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		AnularPagoForm formulario =  (AnularPagoForm) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		this.setRespuestaFront( request, delegate.anularPago( formulario.getIdentificador(), formulario.getInstancia() ));
		return mapping.findForward("success");
    }
}
