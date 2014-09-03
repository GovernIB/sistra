package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.MostrarDocumentoCustodiaForm;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;

/**
 * @struts.action
 *  name="mostrarDocumentoCustodiaForm"
 *  path="/mostrarDocumentoCustodia"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarDocumentoCustodiaAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		MostrarDocumentoCustodiaForm documento = ( MostrarDocumentoCustodiaForm ) form;
		
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setCodigo(documento.getCodigo().longValue());
		refRDS.setClave(documento.getClave());
		
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(refRDS, false);
		
		String urlDocCustodia = null;
		if (StringUtils.isNotBlank(documentoRDS.getCodigoDocumentoCustodia()) ) {
			try {
				PluginCustodiaIntf plgCustodia = PluginFactory.getInstance().getPluginCustodia();
				urlDocCustodia = plgCustodia.obtenerUrlDocumento(documentoRDS.getCodigoDocumentoCustodia());
			} catch (Exception ex) {
				urlDocCustodia = null;
			}
		}
		
		
		if (urlDocCustodia != null) {
			response.sendRedirect(urlDocCustodia);
			return null;
		} else {
			request.setAttribute("message",resources.getMessage( getLocale( request ), "errors.urlCustodiaNoSoportada"));
			return mapping.findForward( "fail" );
		}
				
    }
}
