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
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  path="/buscarNombreDelegado"
 * 
 */
public class BuscarNombreDelegadoAction extends Action
{
	private static Log _log = LogFactory.getLog( BuscarNombreDelegadoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String nif = request.getParameter("nif");
		String error = "";
		String nombre = null;
		if(StringUtils.isNotBlank(nif)){
			nif = nif.trim();
			nif = nif.replaceAll("-","");
			nif = nif.replaceAll(" ","");
		}
		ConsultaPADDelegate consultaPAD = DelegateUtil.getConsultaPADDelegate();
		PersonaPAD persona = consultaPAD.obtenerDatosPADporNif(nif);
		if(persona != null){
			nombre = persona.getNombreCompleto();
		}
		if (nombre == null){  
			nombre= "";
			error = this.getResources(request).getMessage("error.nombre.persona");
		}
		JSONObject json = new JSONObject();
       	json.put("nombre",nombre); 
       	json.put("error",error);       
       	json.put("nif",nif); 
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
