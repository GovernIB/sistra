package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.form.BorrarAnexoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="borrarAnexoForm"
 *  path="/protected/borrarAnexo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class BorrarAnexoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		BorrarAnexoForm formulario = ( BorrarAnexoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		this.setRespuestaFront( request, delegate.borrarAnexo( formulario.getIdentificador(), formulario.getInstancia() ));
		return mapping.findForward("success");
    }
}
