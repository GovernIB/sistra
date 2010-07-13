package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * @struts.action
 *  path="/exportCSVTramitesAction"
 *  scope="session"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 * @struts.action-forward
 *  name="success" path=".exportarCSV"
 *
 *	MUESTRA PAGINA DE EXPORTACION CSV
 *
 */
public class ExportCSVTramitesAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVTramitesAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		// Obtenemos lista de tramites
		
			
		return mapping.findForward("success");
		
    }
	
	
}
