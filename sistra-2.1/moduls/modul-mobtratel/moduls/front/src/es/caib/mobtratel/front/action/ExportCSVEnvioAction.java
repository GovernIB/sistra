package es.caib.mobtratel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.mobtratel.front.Constants;
import es.caib.mobtratel.front.form.ExportCSVEnvioForm;
import es.caib.mobtratel.front.util.CSVExport;
import es.caib.mobtratel.model.Envio;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.EnvioDelegate;

/**
 * @struts.action
 *	name="exportCSVEnvioForm"
 *  path="/exportCSVEnvioAction"
 *  scope="request"
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
public class ExportCSVEnvioAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ExportCSVEnvioAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		try{
			
			EnvioDelegate ed = DelegateUtil.getEnvioDelegate();
			
			ExportCSVEnvioForm f  = ( ExportCSVEnvioForm ) form;
			
			// Comprobamos que no se este enviando
			if(ed.isEnviando(f.getCodigo())) {
				request.setAttribute(Constants.MENSAJE_KEY, "error.exportar.envioBloqueado" );
				return mapping.findForward( "fail" );
			}    		
	    	
			// Generamos csv
			Envio envio = ed.obtenerEnvio(f.getCodigo());
			CSVExport csv = new CSVExport(envio);
			
			// Devolvemos csv
			request.setAttribute( Constants.NOMBREFICHERO_KEY,  "envio" + csv.getCodigo() + ".csv");		
			request.setAttribute( Constants.DATOSFICHERO_KEY, csv.toCSV());		

			return mapping.findForward("success");
			
		}catch(Exception ex){
			log.error("Excepcion obteniendo fichero csv: " + ex.getMessage(),ex);
			request.setAttribute(Constants.MENSAJE_KEY,resources.getMessage( getLocale( request ), "exportCSV.excepcion"));
			return mapping.findForward( "fail" );
		}
		
    }
	
	
}
