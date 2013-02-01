package es.caib.bantel.front.action;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.json.JSONArray;
import es.caib.bantel.front.json.JSONObject;

/**
 * @struts.action
 *  path="/altaParametro"
 *  validate="true"
 */
public class AltaParametroNotificacionAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		String codi = request.getParameter("codi");
		String valor = request.getParameter("valor");
		JSONArray params = new JSONArray();
		Map parametros = (Map)request.getSession().getAttribute("parametrosAltaNotificacion");
		if(parametros == null){
			parametros = new LinkedHashMap();
		}
		if(StringUtils.isNotBlank(codi) && StringUtils.isNotBlank(valor)){
			if(!parametros.containsKey(codi)){
				parametros.put(codi,valor);
			}
			request.getSession().setAttribute("parametrosAltaNotificacion",parametros);
		}
		Set set = parametros.keySet();
	    Iterator iter = set.iterator();
	    while (iter.hasNext()) {
	    	JSONObject jsonObject = new JSONObject();
	    	String key = (String) iter.next();
	    	jsonObject.put("codi",key);
	    	jsonObject.put("valor",parametros.get(key));
	    	params.put(jsonObject);
	    }
		populateWithJSON(response,params.toString());
		return null;
    }

	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
}