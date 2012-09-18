package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.MostrarDocumentoForm;
import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.DocumentoBandejaDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;

/**
 * @struts.action
 *  name="mostrarDocumentoForm"
 *  path="/mostrarDocumentoXML"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarDocumentoXMLAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarDocumentoForm documento = ( MostrarDocumentoForm ) form;
		DocumentoBandejaDelegate documentoDelegate = DelegateUtil.getDocumentoBandejaDelegate();
		
		DocumentoBandeja documentoBandeja = documentoDelegate.obtenerDocumentoBandeja(documento.getCodigo());
		
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setCodigo(documentoBandeja.getRdsCodigo().longValue());
		refRDS.setClave(documentoBandeja.getRdsClave());
		
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(refRDS);
		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, documentoRDS.getNombreFichero() );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, documentoRDS.getDatosFichero());
		
		return mapping.findForward("success");		
    }
}
