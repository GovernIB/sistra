package es.caib.zonaper.delega.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.json.JSONObject;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  path="/habilitarEntidad"
 * 
 */
public class HabilitarEntidadAction extends Action
{
	private static Log _log = LogFactory.getLog( HabilitarEntidadAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String nif = request.getParameter("nif");
		String habilitar = request.getParameter("habilitar");
		JSONObject json = new JSONObject();	
		String error = "";
		if(!StringUtils.isBlank(nif)){
			DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
			try{
				deleg.habilitarDelegacion(nif, habilitar);
			}catch(Exception e){
				_log.error("No se ha podido habilitar la entidad.",e);
				error = this.getResources(request).getMessage("error.entidad.habilitar.general");
			}
		}else{
			error = this.getResources(request).getMessage("error.nif.entidad.vacio");
		}
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
