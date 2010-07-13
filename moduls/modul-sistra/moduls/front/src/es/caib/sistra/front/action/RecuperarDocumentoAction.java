package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import es.caib.util.ConvertUtil;
import es.caib.sistra.front.json.JSONObject;

/**
 * @struts.action
 *  path="/recuperarDocumento"
 *  validate="true"
 */
public class RecuperarDocumentoAction extends BaseAction
{
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();		        				
		String idInstancia = request.getParameter("idInstancia");
		byte[] documentoBytes = (byte[])request.getSession().getAttribute(idInstancia);
		String documento = ConvertUtil.bytesToBase64UrlSafe(documentoBytes);
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
