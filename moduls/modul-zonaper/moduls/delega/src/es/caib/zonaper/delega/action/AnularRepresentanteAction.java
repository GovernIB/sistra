package es.caib.zonaper.delega.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.json.JSONObject;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  path="/anularRepresentante"
 * 
 */
public class AnularRepresentanteAction extends Action
{
	private static Log _log = LogFactory.getLog( AnularRepresentanteAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String codigoDelegacion = request.getParameter("codigoDelegacion");
		JSONObject json = new JSONObject();	
		String error = "";
		String datos = "";
		String rep = "N";
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		
		
		// Obtenemos delegacion
		Delegacion d = deleg.obtenerDelegacion(Long.parseLong(codigoDelegacion));
		
		// Anulamos delegacion
		try{
			deleg.anularDelegacion(Long.parseLong(codigoDelegacion));
		}catch(Exception ex){
			_log.error("No podemos anular la delegación con codigo "+codigoDelegacion,ex);
			error = this.getResources(request).getMessage("error.delegacion.general");
		}
		
		// Indicamos si hay representante
		if (deleg.obtenerRepresentanteEntidad(d.getNifDelegante())!= null){
			rep = "S";
		}
		
		// Devolvemos datos
		json.put("representante",rep);
		json.put("datos",datos);
		json.put("error",error);
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
