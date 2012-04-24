package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;

/**
 * @struts.action
 *  path="/confirmacionPreregistros"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".confirmacionPreregistros"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ConfirmacionPreregistros extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"2");		
		return mapping.findForward( "success" );
    }
}
