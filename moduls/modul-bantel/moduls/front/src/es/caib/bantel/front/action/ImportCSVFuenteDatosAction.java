package es.caib.bantel.front.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.ImportarCSVFuenteDatosForm;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.util.CsvDocumento;
import es.caib.util.CsvUtil;

/**
 * @struts.action
 *  path="/importCSVFuenteDatosAction"
 *  name="importarCSVFuenteDatosForm"
 *  scope="session"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 *
 */
public class ImportCSVFuenteDatosAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ImportCSVFuenteDatosAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));

		ByteArrayInputStream bis = null;
		try{			
			
			ImportarCSVFuenteDatosForm formulario = (ImportarCSVFuenteDatosForm) form;
			byte csvContent[] = formulario.getCsv().getFileData();
			
			bis = new ByteArrayInputStream(csvContent);
			CsvDocumento csv = CsvUtil.importar(bis);
			
			DelegateUtil.getFuenteDatosDelegate().importarCsv(formulario.getIdentificador(), csv);
			
			response.sendRedirect("detalleFuenteDatos.do?identificador=" + formulario.getIdentificador());
			return null;
		}catch (Exception ex){
			log.error("Excepcion importando fichero csv: " + ex.getMessage(),ex);
			
			String keyError = "importCSV.excepcion";
			
			// Controlamos si es una excepcion de PK
			if (ExceptionUtils.getRootCauseMessage(ex).indexOf("PK-Exception") != -1) {
				keyError = "importCSV.errorPK";
			}
			
			request.setAttribute("message",resources.getMessage( getLocale( request ), keyError));
			return mapping.findForward( "fail" );
		} finally {
			IOUtils.closeQuietly(bis);
		}
    }	
}
