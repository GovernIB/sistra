package es.caib.zonaper.front.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.persistence.delegate.BandejaFirmaDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/protected/rechazarDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 * name="success" path=".bandejaFirma"
 * 
 * @struts.action-forward
 *  name="fail" path=".bandejaFirma"
 */
public class RechazarDocumentoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( RechazarDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String identificador = request.getParameter("identificador");
		
		try{
			//Buscamos Documentos pendientes de firma de la entidad delegante
			BandejaFirmaDelegate firmas = DelegateUtil.getBandejaFirmaDelegate();
			firmas.rechazarDocumentoBandejaFirma(new Long(identificador));
		}catch(Exception e){
			ActionErrors messages = new ActionErrors();
			messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.rechazar.documento.Firma"));
        	saveErrors(request,messages);
        	return mapping.findForward("fail");
		}
		response.sendRedirect(request.getContextPath() + "/protected/mostrarBandejaFirma.do");
		return null;
    }
}