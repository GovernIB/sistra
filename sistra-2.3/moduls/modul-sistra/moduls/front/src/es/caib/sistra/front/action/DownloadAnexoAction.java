package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.util.ConvertUtil;
import es.caib.sistra.front.form.DownloadDocumentoForm;
import es.caib.sistra.front.json.JSONObject;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="downloadDocumentoForm"
 *  path="/protected/downloadAnexo"
 *  validate="true"
 */
public class DownloadAnexoAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		DownloadDocumentoForm formulario = (DownloadDocumentoForm) form;
		
		request.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();		        				
		
		
		// Recuperamos instancia tramitacion
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( formulario.getID_INSTANCIA(), request );
		
		// Recuperamos anexo uploadeado
		String documento = null;
		RespuestaFront resp = delegate.downloadAnexo(formulario.getIdentificador(), formulario.getInstancia());
		if (resp.getMensaje() == null || resp.getMensaje().getTipo() != MensajeFront.TIPO_ERROR) {
			byte[] datosFichero = (byte[]) resp.getParametros().get("datosfichero");
			documento = ConvertUtil.bytesToBase64UrlSafe(datosFichero);
		}
		
		// Devolvemos fichero
		if(documento != null && !"".equals(documento)){
			jsonObject.put("error","");
			jsonObject.put("documento",documento);
		}else{
			jsonObject.put("error","anexar.documento.null");
		}
		populateWithJSON(response,jsonObject.toString());
		return null;
    }
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}

}
