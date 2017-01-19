package es.caib.zonaper.front.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @struts.action
 * name="detalleDelegacionForm"
 * path="/protected/crearDelegacionExt"
 * 
 * @struts.action-forward
 *  name="success" path=".delegacionesExt"
 * 
 */
public class CrearDelegacionExtAction extends Action
{
	private static Log _log = LogFactory.getLog( CrearDelegacionExtAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		
		String codigoRDS = request.getParameter("codigoRDS");
		String claveRDS = request.getParameter("claveRDS");
		String firmaJSP = request.getParameter("firmaJSP");
		
		request.setAttribute("codigoRDS", codigoRDS);
		request.setAttribute("claveRDS", claveRDS);
		request.setAttribute("firmaJSP", firmaJSP);
		
		return mapping.findForward("success");
		 
	}
	
}
