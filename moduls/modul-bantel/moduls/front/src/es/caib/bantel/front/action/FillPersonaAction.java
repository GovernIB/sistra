package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.json.JSONObject;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * @struts.action
 *  path="/fillPersona"
 * 
 */
public class FillPersonaAction extends Action
{
	private static Log _log = LogFactory.getLog( FillPersonaAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonaPAD p =null;
		try{
			 request.setCharacterEncoding("UTF-8");
			 String seycon = "";
			 String nif = "";
			 if(StringUtils.isNotEmpty(request.getParameter("seycon"))){
				 seycon = request.getParameter("seycon");
			 }else if(StringUtils.isNotEmpty(request.getParameter("nif"))){
				 nif = request.getParameter("nif"); 
			 }
			 // p = DelegatePADUtil.getPadDelegate().obtenerDatosPersonaPADporUsuario(seycon);
			 if(StringUtils.isNotEmpty(seycon)){
				 _log.debug("Rellenando persona a partir seycon " + seycon);
				 p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(seycon);
			 }else if(StringUtils.isNotEmpty(nif)){
				 _log.debug("Rellenando persona a partir nif " + nif);
				 p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nif); 
			 }
		}catch(Exception ex){
			_log.error(ex);
		}
		// Si ha habido algun error creamos persona vacio
		if (p == null) p=new PersonaPAD();
		// Convertimos a json
		JSONObject json = new JSONObject();
		json.put("usuarioSeycon", StringUtils.defaultString(p.getUsuarioSeycon()));
		json.put("nif",StringUtils.defaultString(p.getNif()));
		//json.put("nombre",StringUtils.defaultString(p.getApellido2()) + " " + StringUtils.defaultString(p.getApellido1()) + " " + StringUtils.defaultString(p.getNombre()));
		json.put("nombre",p.getApellidosNombre());
		json.put("municipio",StringUtils.defaultString(p.getMunicipio()));
		json.put("provincia",StringUtils.defaultString(p.getProvincia()));		
		// Devolvemos expe
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
