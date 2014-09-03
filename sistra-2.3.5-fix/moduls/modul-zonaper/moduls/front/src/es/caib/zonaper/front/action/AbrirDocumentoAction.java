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

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;

/**
 * @struts.action
 *  path="/protected/abrirDocumento"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".bandejaFirma"
 */
public class AbrirDocumentoAction extends BaseAction
{
	private static Log log = LogFactory.getLog( AbrirDocumentoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String codigo = request.getParameter("codigo");
		String clave = request.getParameter("clave");
		
		try{
			ReferenciaRDS refRDS = new ReferenciaRDS();
			refRDS.setCodigo(Long.parseLong(codigo));
			refRDS.setClave(clave);
				
			RdsDelegate rdsDelegate = DelegateRDSUtil.getRdsDelegate();
			DocumentoRDS documentoRDS = rdsDelegate.consultarDocumentoFormateado(refRDS);
		
		
			response.reset();					
			response.setContentType("application/octet-stream");
			
			// Normalizamos fichero quitando los blancos (problema firefox)
			String nombreFichero = documentoRDS.getNombreFichero().replaceAll(" ","_");
			
			response.setHeader("Content-Disposition","attachment; filename="+ nombreFichero + ";");
	        response.getOutputStream().write(documentoRDS.getDatosFichero());
	            		
	        return null;
		}catch(Exception e){
			ActionErrors messages = new ActionErrors();
			messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.abrir.documento.Firma"));
        	saveErrors(request,messages);
        	return mapping.findForward("fail");	
		}
    }
}