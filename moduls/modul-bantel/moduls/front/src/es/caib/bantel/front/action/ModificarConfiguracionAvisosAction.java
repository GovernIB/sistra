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
import es.caib.util.ValidacionesUtil;
import es.caib.zonaper.modelInterfaz.ConfiguracionAvisosExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeUtil;

/**
 * @struts.action
 *  path="/modificarConfiguracionAvisos"
 * 
 */
public class ModificarConfiguracionAvisosAction extends Action
{
	private static Log _log = LogFactory.getLog( ModificarConfiguracionAvisosAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String error = "";
		
		try{
			String unidadAdministrativa = StringUtils.trim(request.getParameter("unidadAdministrativa"));
			String identificadorExpediente = StringUtils.trim(request.getParameter("identificadorExpediente"));
			String claveExpediente = StringUtils.trim(request.getParameter("claveExpediente"));
			String habilitar = StringUtils.trim(request.getParameter("habilitar"));
			String email = StringUtils.trim(request.getParameter("email"));
			String sms = StringUtils.trim(request.getParameter("sms"));	
			 
			 
			 _log.debug("modificar avisos: " + habilitar + " - " + email + " - " + sms);
			 
			// Validar email
			if (StringUtils.isNotEmpty(email)) {
				if (!ValidacionesUtil.validarEmail(email)) {
					 error = "expediente.aviso.configuracionAvisos.emailNoValido";
				}
			}
			
			// Validar sms
			if (StringUtils.isEmpty(error) && StringUtils.isNotEmpty(sms)) {
				if (!ValidacionesUtil.validarMovil(sms)) {
					 error = "expediente.aviso.configuracionAvisos.movilNoValido";
				}
			}
			 
			// Realizamos modificacion
			if (StringUtils.isEmpty(error)) {
				ConfiguracionAvisosExpedientePAD configuracionAvisos = new ConfiguracionAvisosExpedientePAD();
				Boolean habilitarAvisos;
				if ("S".equals(habilitar)) {
					habilitarAvisos = new Boolean(true);
				} else if ("N".equals(habilitar)) {
					habilitarAvisos = new Boolean(false);
				} else if ("O".equals(habilitar)) {
					habilitarAvisos = null;
					email = null;
					sms = null;
				} else {
					throw new Exception("Valor no valido para parametro habilitar");
				}
				configuracionAvisos.setHabilitarAvisos(habilitarAvisos);
				configuracionAvisos.setAvisoEmail(email);
				configuracionAvisos.setAvisoSMS(sms);
				PadBackOfficeUtil.getBackofficeExpedienteDelegate().modificarAvisosExpediente(Long.parseLong(unidadAdministrativa),
						identificadorExpediente, claveExpediente, configuracionAvisos );
			}
			
		}catch(Exception ex){
			_log.error("Exception modificacion avisos expediente: " + ex.getMessage(), ex);
			error = "expediente.aviso.configuracionAvisos.errorDesconocido";
		}
		
		
		// Convertimos a json
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(error)) {
			error = getMessage(request, error);
		}
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
