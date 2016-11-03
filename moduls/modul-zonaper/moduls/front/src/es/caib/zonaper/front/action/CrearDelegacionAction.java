package es.caib.zonaper.front.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.zonaper.front.json.JSONObject;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 * name="detalleDelegacionForm"
 * path="/protected/crearDelegacion"
 * 
 */
public class CrearDelegacionAction extends Action
{
	private static Log _log = LogFactory.getLog( CrearDelegacionAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		
		String codigoRDS = request.getParameter("codigoRDS");
		String claveRDS = request.getParameter("claveRDS");
		String firmaJSP = request.getParameter("firmaJSP");
		
		JSONObject json = new JSONObject();	
		String error = "";
		
		// Damos de alta la delegacion de representante
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
		FirmaIntf firma = plgFirma.parseFirmaFromHtmlForm(firmaJSP);
		ReferenciaRDS refRDS = new ReferenciaRDS(Long.parseLong(codigoRDS),claveRDS);
		try{
			int res = deleg.altaDelegacion(refRDS,firma);
			if (res != 0){
		 		String msgError="error.altaDelegacion.errorInesperado";
			 	switch (res){
			 		case -1: //Documento incorrecto
			 			msgError = "error.altaDelegacion.documentoIncorrecto";
			 			break;
			 		case -2: //Datos no válidos
			 			msgError = "error.altaDelegacion.datosNoValidos";
			 			break;
			 		case -3: // No esta habilitada delegacion
			 			msgError = "error.altaDelegacion.noHabilitadaDelegacion";
			 			break;
			 		case -4: // Alta representante solo para role delegacion
			 			msgError = "error.altaDelegacion.altaRepresentante";
			 			break;
			 		case -5: // Ya existe representante
			 			msgError = "error.altaDelegacion.existeRepresentante";
			 			break;
			 		case -6: // Firmante incorrecto
			 			msgError = "error.altaDelegacion.firmanteIncorrecto";
			 			break;
			 		case -7: // Error verificacion firma
			 			msgError = "error.altaDelegacion.errorFirma";
			 			break;
			 	}
			 	error = this.getResources(request).getMessage(msgError);
		 	}
		}catch(Exception ex){
			_log.error("Excepcion alta delegacion",ex);
			error = this.getResources(request).getMessage("error.altaDelegacion.errorInesperado");
		}
	 	
		// Retornamos datos
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
