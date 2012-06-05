package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import es.caib.util.NifCif;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeUtil;

/**
 * @struts.action
 *  path="/altaPersona"
 * 
 */
public class AltaPersonaAction extends Action
{
	private static Log _log = LogFactory.getLog( AltaPersonaAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String error = "";
		
		try{
			 //request.setCharacterEncoding("UTF-8");
			 String nif = StringUtils.trim(request.getParameter("nif"));
			 String nombre = StringUtils.trim(request.getParameter("nombre"));
			 String apellido1 = StringUtils.trim(request.getParameter("apellido1"));	
			 String apellido2 = StringUtils.trim(request.getParameter("apellido2"));
			 
			 _log.debug("alta persona: " + nif + " - " + nombre + " - " + apellido1 + " - " + apellido2);
			 
			// Validar nif
			int tipoNif = NifCif.DOCUMENTO_NO_VALIDO;
			if (StringUtils.isEmpty(nif)) {
				error = getMessage(request, "expediente.alta.altaDestinatario.errorNifNoValido");
			}  else {
				tipoNif = NifCif.validaDocumento(nif);
				if (tipoNif != NifCif.TIPO_DOCUMENTO_CIF && tipoNif != NifCif.TIPO_DOCUMENTO_NIF) {
					error = getMessage(request, "expediente.alta.altaDestinatario.errorNifNoValido");
				}
			}
			
			// Validar nombre
			if (StringUtils.isEmpty(error) && StringUtils.isEmpty(nombre) ) {
				error = getMessage(request, "expediente.alta.altaDestinatario.errorNombreVacio");
			}
			
			// Validar apellidos
			if (StringUtils.isEmpty(error)) {
				if (tipoNif == NifCif.TIPO_DOCUMENTO_NIF && StringUtils.isEmpty(apellido1)) {
					error = getMessage(request, "expediente.alta.altaDestinatario.errorApellido1Vacio");
				} else if (tipoNif == NifCif.TIPO_DOCUMENTO_CIF && (StringUtils.isNotEmpty(apellido1) || StringUtils.isNotEmpty(apellido2) ) ){
					error = getMessage(request, "expediente.alta.altaDestinatario.errorApellidosCif");
				}
			}
			
			// Comprobamos si existe
			if (StringUtils.isEmpty(error)) {
				PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nif); 
				if (p != null) {
					error = getMessage(request, "expediente.alta.altaDestinatario.yaExisteNif");
				}
			}
			
			// Realizamos el alta
			if (StringUtils.isEmpty(error)) {
				PadBackOfficeUtil.getBackofficeExpedienteDelegate().altaZonaPersonalUsuario(nif, nombre, apellido1, apellido2);
			}
			
		}catch(Exception ex){
			_log.error("Exception alta persona: " + ex.getMessage(), ex);
			error = getMessage(request, "expediente.alta.altaDestinatario.errorDesconocido");
		}
		
		
		// Convertimos a json
		JSONObject json = new JSONObject();
		json.put("error", error);
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
