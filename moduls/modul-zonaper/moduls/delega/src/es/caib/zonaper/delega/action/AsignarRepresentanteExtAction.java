package es.caib.zonaper.delega.action;


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
 *  path="/asignarRepresentanteExt"
 *  
* @struts.action-forward
 *  name="success" path=".delegacionesManejarExt"
 * 
 */
public class AsignarRepresentanteExtAction extends Action
{
	private static Log _log = LogFactory.getLog( AsignarRepresentanteExtAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String nifDelegado = request.getParameter("nifDelegado");
		String nifDelegante = request.getParameter("nifDelegante");
		String codigoRDS = request.getParameter("codigoRDS");
		String claveRDS = request.getParameter("claveRDS");
		String firmaJSP = request.getParameter("firmaJSP");
		
		request.setAttribute("nifDelegado", nifDelegado);
		request.setAttribute("nifDelegante", nifDelegante);
		request.setAttribute("codigoRDS", codigoRDS);
		request.setAttribute("claveRDS", claveRDS);
		request.setAttribute("firmaJSP", firmaJSP);
		
		return mapping.findForward("success");
		
	}
	
}
