package es.caib.redose.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.front.Constants;
import es.caib.redose.modelInterfaz.DocumentoVerifier;

/**
 * @struts.action
 *  path="/mostrarDocumento"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarDocumentoAction extends BaseAction
{	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		// Obtenemos documento almacenado en la sesion (por el controller)
		DocumentoVerifier documento = (DocumentoVerifier) request.getSession().getAttribute(request.getParameter("id"));
		request.setAttribute(Constants.DESTINOFICHERO_KEY,Constants.DESTINOFICHERO_PDFNAVEGADOR_KEY );		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, documento.getNombreFicheroFormateado() );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, documento.getDatosFicheroFormateado());		
		request.setAttribute("documento",documento);
		
		return mapping.findForward("success");		
		
    }
}
