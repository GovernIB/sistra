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

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.zonaper.front.form.FirmarDocumentoDelegadoForm;
import es.caib.zonaper.persistence.delegate.BandejaFirmaDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="firmarDocumentoDelegadoForm"
 *  path="/protected/firmarDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 * name="success" path=".bandejaFirma"
 * 
 * @struts.action-forward
 *  name="fail" path=".irFirmarDocumento"
 */
public class FirmarDocumentoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( FirmarDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		try{
			FirmarDocumentoDelegadoForm docForm = (FirmarDocumentoDelegadoForm)form;
			//Buscamos Documentos pendientes de firma de la entidad delegante
			BandejaFirmaDelegate firmas = DelegateUtil.getBandejaFirmaDelegate();
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			FirmaIntf firma = plgFirma.parseFirmaFromHtmlForm(docForm.getFirma());
			firmas.firmarDocumentoBandejaFirma(new Long(docForm.getIdentificador()),firma);
		}catch(Exception e){
			ActionErrors messages = new ActionErrors();
			messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.firmar.documento.Firma"));
        	saveErrors(request,messages);
        	return mapping.findForward("fail");
		}
		response.sendRedirect(request.getContextPath() + "/protected/mostrarBandejaFirma.do");
		return null;
    }
}