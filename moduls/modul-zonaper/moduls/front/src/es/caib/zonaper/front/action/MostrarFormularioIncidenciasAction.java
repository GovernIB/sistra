package es.caib.zonaper.front.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;

/**
 * @struts.action
 *  path="/protected/mostrarFormularioIncidencias"
 *  scope="request"
 *  validate="false"
 */
public class MostrarFormularioIncidenciasAction extends BaseAction
{	
	 
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		 
		// Intentamos recuperar info del tramite		 
		String nif = "";
		String nombre = "";		
		String lang = "es";
		
		DatosSesion datosSesion = this.getDatosSesion(request);
		lang = datosSesion.getLocale().getLanguage();
		if (datosSesion.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
			nif = datosSesion.getNifUsuario();
			nombre = datosSesion.getNombreUsuario();
		}						
		 
		 String contextoRaiz = (String) request.getSession().getServletContext().getAttribute(Constants.CONTEXTO_RAIZ);
		 String url = contextoRaiz + "/incidencias/formulario?";
		 url += "lang=" + lang;
		 url += "&nif=" + encodeParameter(nif);
		 url += "&nombre=" + encodeParameter(nombre);
		 
		 response.sendRedirect(url);		 
		 return null;
     }

	private String encodeParameter(String value)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "ISO-8859-1");
	}
}
