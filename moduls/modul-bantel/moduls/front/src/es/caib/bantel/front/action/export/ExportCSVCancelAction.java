package es.caib.bantel.front.action.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.action.BaseAction;
import es.caib.bantel.model.CSVExportWorks;

/**
 * @struts.action
 *  path="/exportCSVCancelAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	CANCELA PROCESO DE EXPORTACION: OBTIENE TRABAJO EN SESION Y LO CANCELA
 *
 *
 */
public class ExportCSVCancelAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVCancelAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		String id = request.getParameter("id");
		if (id!=null){
			CSVExportWorks works = (CSVExportWorks) request.getSession().getAttribute(CSVExportWorks.KEY_CSV_WORKS);
			if (works != null) {
				works.removeCSVExport(id);				
			}
		}
		
		response.getWriter().print("OK");
        return null;
		
    }
	
	
}
