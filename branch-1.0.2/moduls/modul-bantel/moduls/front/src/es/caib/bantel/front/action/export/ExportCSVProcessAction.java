package es.caib.bantel.front.action.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.action.BaseAction;
import es.caib.bantel.model.CSVExport;
import es.caib.bantel.model.CSVExportWorks;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;

/**
 * @struts.action
 *  path="/exportCSVProcessAction"
 *  scope="request"
 *  validate="false"
 *
 *
 *	CONTINUA PROCESO DE EXPORTACION: OBTIENE TRABAJO EN SESION Y PROCESA SIGUIENTES ENTRADAS
 *	DEVUELVE "NUM ENTRADAS PROCESADAS - TOTAL ENTRADAS" O "ERROR" SI HAY ALGUN FALLO
 *
 */
public class ExportCSVProcessAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVProcessAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		String res = "ERROR";
		try{
			String id = request.getParameter("id");
			if (id!=null){				
				// Recogemos trabajo de sesion y procesamos siguientes entradas
				CSVExportWorks works = (CSVExportWorks) request.getSession().getAttribute(CSVExportWorks.KEY_CSV_WORKS);
				CSVExport csv = works.getCSVExport(id);
				TramiteBandejaDelegate delegate = DelegateUtil.getTramiteBandejaDelegate();
				String [] entradas = csv.nextEntradas();
				for (int i=0;i<entradas.length;i++){
				    csv.addFilaCSV(delegate.exportarCSV(entradas[i],csv.getPropiedadesExport()));
				}				
				// Devolvemos estado trabajo
				res = "PROCESS:" + csv.getIndex() + "-"+ csv.getEntradas().length;				
			}						
		}catch(Exception ex){
			log.error("Excepcion procesando csv: " + ex.getMessage(),ex);
		}
		
		// Devolvemos respuesta
		response.getWriter().print(res);
        return null;
		
    }
	
	
}
