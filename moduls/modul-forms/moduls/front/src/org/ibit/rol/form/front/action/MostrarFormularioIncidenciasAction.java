package org.ibit.rol.form.front.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;


/**
 * @struts.action
 *  path="/mostrarFormularioIncidencias"
 *  scope="request"
 *  validate="false"
 */
public class MostrarFormularioIncidenciasAction extends BaseAction
{
	
	 private static Log log = LogFactory.getLog(MostrarFormularioIncidenciasAction.class);
	
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
             HttpServletResponse response) throws Exception 
     {
		 
		// Intentamos recuperar info del tramite		 
		String tramiteDesc = "";
		String tramiteId = "";
		String procedimientoId = "";
		String nif = "";
		String nombre = "";
		String idPersistencia = "";
		String fechaCreacion = "";
		String lang = "es";
		
		try {
			
			InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
	        if (delegate == null) {
	            ActionErrors errors = new ActionErrors();
	            errors.add(null, new ActionError("errors.session"));
	            saveErrors(request, errors);
	            return mapping.findForward("fail");
	        }
	
	        InstanciaTelematicaDelegate itd = (InstanciaTelematicaDelegate) delegate;
	        
	        lang = itd.obtenerIdioma().getLanguage();
			Map propsForm = itd.obtenerPropiedadesFormulario();
			tramiteDesc = (String) propsForm.get("tramite");
			tramiteId = (String) propsForm.get("tramiteId");
			procedimientoId = (String) propsForm.get("procedimiento");
			if (propsForm.get("nif") != null) {
				nif = (String) propsForm.get("nif");
				nombre = (String) propsForm.get("usuario");
			}
			idPersistencia = (String) propsForm.get("claveTramitacion");
			fechaCreacion = (String) propsForm.get("fechaTramite");
			
			
		} catch (Exception ex) {
			log.error("No se ha podido recuperar informacion del tramite para poder mostrar debug");
		}
		 
		 String contextoRaiz = (String) request.getSession().getServletContext().getAttribute( org.ibit.rol.form.front.Constants.CONTEXTO_RAIZ);
		 String url = contextoRaiz + "/incidencias/formulario?";
		 url += "lang=" + lang;
		 url += "&tramiteDesc=" + encodeParameter(tramiteDesc);
		 url += "&tramiteId=" + encodeParameter(tramiteId);
		 url += "&procedimientoId=" + encodeParameter(procedimientoId);
		 url += "&idPersistencia=" + encodeParameter(idPersistencia);
		 url += "&nif=" + encodeParameter(nif);
		 url += "&nombre=" + encodeParameter(nombre);
		 url += "&fechaCreacion=" + encodeParameter(fechaCreacion);
		 
		 response.sendRedirect(url);		 
		 return null;
     }

	private String encodeParameter(String value)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "ISO-8859-1");
	}
}
