package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.ValidacionesUtil;

/**
 * @struts.action
 *  path="/redirect"
 *  scope="request"
 *  validate="false"
 */
public class RedirectAction extends Action
{
	 public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
             HttpServletResponse response) throws Exception 
     {
		 String url = request.getParameter("url");
		 
		 
		 // Verificamos url. Si no es valida, redirigimos a portal
		 if (!ValidacionesUtil.esURL(url)) {
			 url = "irAPortal.do";
		 }
		 
		 response.sendRedirect(url);		 
		 return null;
     }
}
