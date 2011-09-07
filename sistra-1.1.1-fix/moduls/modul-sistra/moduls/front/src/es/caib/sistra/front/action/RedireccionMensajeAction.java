package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @struts.action
 *  name="redireccionMensaje"
 *  path="/protected/redireccionMensaje"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/redirectMensaje.jsp"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class RedireccionMensajeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		  return mapping.findForward("success");
	} 	
}