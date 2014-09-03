package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/modificarValorCampoFuenteDatos"
 * 
 */
public class ModificarValorCampoFuenteDatosAction extends Action
{
	private static Log _log = LogFactory.getLog( ModificarValorCampoFuenteDatosAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String error = "";
		
		 String idFuenteDatos = StringUtils.trim(request.getParameter("idFuenteDatos"));
		 String numFila = StringUtils.trim(request.getParameter("numFila"));
		 String idCampo = StringUtils.trim(request.getParameter("idCampo"));	
		 String valorCampo = StringUtils.trim(request.getParameter("valorCampo"));
		 
		
		try{
			 //request.setCharacterEncoding("UTF-8");
			 
			 
			DelegateUtil.getFuenteDatosDelegate().establecerValorFuenteDatos(idFuenteDatos, Integer.parseInt(numFila), idCampo, valorCampo);
			
		}catch(Exception ex){
			_log.error("Exception estableciendo valor campo: " + ex.getMessage(), ex);
			
			String keyError = "fuenteDatos.errorDesconocido";
			
			// Controlamos si es una excepcion de PK
			if (ExceptionUtils.getRootCauseMessage(ex).indexOf("PK-Exception") != -1) {
				keyError = "fuenteDatos.errorPK";
			}
			
			error = getMessage(request, keyError);
		}
		
		// Convertimos a json
		JSONObject json = new JSONObject();
		json.put("error", error);
		json.put("numfila", numFila);
		json.put("idCampo", idCampo);
		json.put("valorCampo", valorCampo);
		
		populateWithJSON(response,json.toString());
		return null;
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 		
	}
	
	private String getMessage(HttpServletRequest request, String key){
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		return resources.getMessage( getLocale( request ), key );
	}
	
	
}
