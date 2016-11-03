package es.caib.bantel.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.front.json.Provincia;
import es.caib.bantel.front.util.Dominios;

/**
 * @struts.action
 *  path="/listarProvincias"
 * 
 */
public class ListarProvinciasAction extends Action
{
	private static Log _log = LogFactory.getLog( ListarProvinciasAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		_log.debug("Obteniendo listado provincias ");
		List prov = Dominios.listarProvincias();
		if (prov == null)  return null;
		JSONObject json = new JSONObject();		
        for (int i = 0; i < prov.size(); i++) {
        	Provincia provincia = (Provincia) prov.get(i);
        	json.put(provincia.getCodigo(),provincia.getDescripcion());            
        }	
		_log.debug("Listado provincias en formato JSON " + json);
		populateWithJSON(response,json.toString());
				
		return null;		
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
	
}
