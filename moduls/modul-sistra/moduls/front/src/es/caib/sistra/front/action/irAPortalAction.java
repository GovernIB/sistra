package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.OrganismoInfo;

/**
 * @struts.action 
 *  path="/irAPortal"
 *  
 */
public class irAPortalAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		OrganismoInfo oi = getOrganismoInfo(request);
		
		request.getSession().setAttribute(Constants.URL_REDIRECCION_SESSION_KEY, oi.getUrlPortal());
		response.sendRedirect(request.getSession().getServletContext().getAttribute(Constants.CONTEXTO_RAIZ) + "/sistrafront/redireccion.jsp");
 		return null;
	}

}
