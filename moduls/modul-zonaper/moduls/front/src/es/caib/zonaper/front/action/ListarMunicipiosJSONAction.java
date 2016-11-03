package es.caib.zonaper.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.json.JSONObject;
import es.caib.zonaper.model.ValorDominio;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/protected/listarMunicipiosJSON"
 * 
 */
public class ListarMunicipiosJSONAction extends Action
{
	private static Log _log = LogFactory.getLog( ListarMunicipiosJSONAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		 request.setCharacterEncoding("UTF-8");
		
		// Obtenemos listado municipios para provincia
		String codProv = request.getParameter("provincia");
		_log.debug("Obteniendo listado municipios para provincia " + codProv);
		List muni = DelegateUtil.getDominiosDelegate().listarLocalidadesProvincia(codProv);
		String json = listaLocalidadesToJSON(muni);
		_log.debug("Listado municipios en formato JSON " + json);
		
		// Devolvemos listado municipios
		populateWithJSON(response,json);
				
		return null;		
	}
	
	private static void populateWithJSON(HttpServletResponse response,String json) throws Exception{
		String CONTENT_TYPE="text/x-json;charset=UTF-8";
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json); 		 
	}
	
	/**
	 * Convierte lista localidades a array JSON
	 * @param localidades
	 * @return
	 */
	private String listaLocalidadesToJSON(List localidades){
		if (localidades == null)  return null;
		JSONObject json = new JSONObject();		
        for (int i = 0; i < localidades.size(); i++) {
        	ValorDominio localidad = (ValorDominio) localidades.get(i);
        	json.put(localidad.getCodigo(),localidad.getDescripcion());            
        }	
        return json.toString();        
	}
	
}
