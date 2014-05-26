package es.caib.bantel.front.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.MostrarFirmaDocumentoForm;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FicheroFirma;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * @struts.action
 *  name="mostrarFirmaDocumentoForm"
 *  path="/mostrarFirmaDocumento"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MostrarFirmaDocumentoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		MostrarFirmaDocumentoForm documento = ( MostrarFirmaDocumentoForm ) form;
		
		ReferenciaRDS refRDS = new ReferenciaRDS();
		refRDS.setCodigo(documento.getCodigo().longValue());
		refRDS.setClave(documento.getClave());
		
		RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = rdsDelegate.consultarDocumento(refRDS);
		
		FirmaIntf firma = null;
		if (documentoRDS.getFirmas() != null) {
			for (int i = 0; i < documentoRDS.getFirmas().length; i++) {
				if (documentoRDS.getFirmas()[i].getNif().equals(documento.getNif())) {
					firma = documentoRDS.getFirmas()[i];
					break;
				}
			}
		}
		
		String nombreFichero="Error";
		byte[] datosFichero="Error".getBytes();
		if (firma != null) {
			PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
			ByteArrayInputStream bis = new ByteArrayInputStream(documentoRDS.getDatosFichero());
			FicheroFirma ficheroFirma = plgFirma.parseFirmaToFile(bis, firma);
			bis.close();
			nombreFichero = ficheroFirma.getNombreFichero();
			datosFichero = ficheroFirma.getContenido();
		}
		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, nombreFichero );		
		request.setAttribute( Constants.DATOSFICHERO_KEY, datosFichero);
		
		return mapping.findForward("success");		
    }
}
