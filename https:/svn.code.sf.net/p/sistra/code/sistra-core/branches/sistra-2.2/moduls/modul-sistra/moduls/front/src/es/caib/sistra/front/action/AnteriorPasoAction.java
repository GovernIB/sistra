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
 *  path="/protected/anteriorPaso"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class AnteriorPasoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		if ( delegate != null )
		{
			this.setRespuestaFront( request, delegate.anteriorPaso() );
		}
		// Obtener paso actual y decrementar en uno
		return mapping.findForward("success");
    }
}
