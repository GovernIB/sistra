package es.caib.redose.front.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.front.Constants;
import es.caib.redose.modelInterfaz.DocumentoVerifier;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FicheroFirma;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

/**
 * @struts.action
 *  path="/descargarFirma"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DescargarFirmaAction extends BaseAction
{	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		// Obtenemos documento almacenado en la sesion
		DocumentoVerifier documento = (DocumentoVerifier) request.getSession().getAttribute(request.getParameter("id"));
		
		// Generamos SMIME de la firma solicitada
		int indexFirma = Integer.parseInt(request.getParameter("index"));	
		FirmaIntf s = documento.getFirmas()[indexFirma];
		
		// Generar smime		
		ByteArrayInputStream bis = new ByteArrayInputStream(documento.getDatosFichero());
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		FicheroFirma docfirma = plgFirma.parseFirmaToFile(bis,s);
		bis.close();			
		
		// Obtenemos firma solicitida
		request.setAttribute(Constants.DESTINOFICHERO_KEY,Constants.DESTINOFICHERO_DOWNLOAD_KEY );		
		request.setAttribute( Constants.NOMBREFICHERO_KEY, docfirma.getNombreFichero());		
		request.setAttribute( Constants.DATOSFICHERO_KEY, docfirma.getContenido());		
		request.setAttribute("documento",documento);
		
		return mapping.findForward("success");		
		
    }
}
