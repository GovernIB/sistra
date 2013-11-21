package es.caib.zonaper.back.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.StringUtil;

/**
 * @struts.action
 *  path="/calcularDC"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/calcularDC.jsp"
 *
 */
public class CalcularDCAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {		
		String numeroPreregistro = request.getParameter("numeroPreregistro");
		String dc = StringUtil.calculaDC(numeroPreregistro);			
		request.setAttribute("digitoControl",dc);		
		return mapping.findForward( "success" );
    }

}
