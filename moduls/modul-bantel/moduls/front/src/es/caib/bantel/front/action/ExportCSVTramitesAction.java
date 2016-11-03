package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
		return mapping.findForward("success");
    }
	
	
}
