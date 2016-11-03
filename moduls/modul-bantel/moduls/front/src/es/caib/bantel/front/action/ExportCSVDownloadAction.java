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

import es.caib.bantel.front.Constants;
import es.caib.bantel.model.CSVExport;
import es.caib.bantel.model.CSVExportWorks;

/**
 * @struts.action
 *  path="/exportCSVDownloadAction"
 *  scope="session"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 *
 *	COMO PASO FINAL DE LA EXPORTACION SE SIRVE EL FICHERO CSV GENERADO
 */
public class ExportCSVDownloadAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVDownloadAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		try{			
			String id = request.getParameter("id");
			
			// Recogemos trabajo de sesion y procesamos siguientes entradas
			CSVExportWorks works = (CSVExportWorks) request.getSession().getAttribute(CSVExportWorks.KEY_CSV_WORKS);
			CSVExport csv = works.getCSVExport(id);
			
			// Comprobamos que haya acabado
			if (!csv.terminado()) throw new Exception("Trabajo de exportacion todavía no acabado");
			
			// Devolvemos csv
			request.setAttribute( Constants.NOMBREFICHERO_KEY, csv.getIdentificadorTramite()+".csv" );		
			request.setAttribute( Constants.DATOSFICHERO_KEY, csv.toCSV());		
			
			// Borramos trabajo de sesion
			works.removeCSVExport(id);
			
			return mapping.findForward("success");
		}catch (Exception ex){
			log.error("Excepcion obteniendo fichero csv: " + ex.getMessage(),ex);
			request.setAttribute("message",resources.getMessage( getLocale( request ), "exportCSV.excepcion"));
			return mapping.findForward( "fail" );
		}
    }	
}
