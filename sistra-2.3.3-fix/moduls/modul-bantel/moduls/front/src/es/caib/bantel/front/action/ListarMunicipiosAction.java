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
import es.caib.bantel.front.json.Localidad;
import es.caib.bantel.front.util.Dominios;

/**
 * @struts.action
 *  path="/listarMunicipios"
 * 
 */
public class ListarMunicipiosAction extends Action
{
	private static Log _log = LogFactory.getLog( ListarMunicipiosAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String codProv = request.getParameter("provincia");
		_log.debug("Obteniendo listado municipios para provincia " + codProv);
		List muni = Dominios.listarLocalidadesProvincia(codProv);
		if (muni == null)  return null;
		JSONObject json = new JSONObject();		
        for (int i = 0; i < muni.size(); i++) {
        	Localidad localidad = (Localidad) muni.get(i);
        	json.put(localidad.getCodigo(),localidad.getDescripcion());            
        }	
		_log.debug("Listado municipios en formato JSON " + json);
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
