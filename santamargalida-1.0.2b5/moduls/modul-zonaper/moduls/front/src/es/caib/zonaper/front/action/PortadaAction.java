package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.OrganismoInfo;

/**
 * @struts.action 
 *  path="/protected/portada"
 *  
 */
public class PortadaAction extends BaseAction
{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {				 	 	
		OrganismoInfo oi = (OrganismoInfo) request.getSession().getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY);
		response.sendRedirect(oi.getUrlPortal());
 		return null;
	}

}
