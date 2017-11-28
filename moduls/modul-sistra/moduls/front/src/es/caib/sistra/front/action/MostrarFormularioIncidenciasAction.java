package es.caib.sistra.front.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.util.StringUtil;

/**
 * @struts.action
 *  path="/protected/mostrarFormularioIncidencias"
 *  scope="request"
 *  validate="false"
 */
public class MostrarFormularioIncidenciasAction extends BaseAction
{
	
	 private static Log log = LogFactory.getLog(MostrarFormularioIncidenciasAction.class);
	
	 public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		String nivelAutenticacion = "";
		
		try {
			InstanciaDelegate delegate = InstanciaManager
					.recuperarInstancia(request);
			RespuestaFront respuestaFront = null;
			respuestaFront = delegate.obtenerInfoTramite();

			lang = respuestaFront.getInformacionTramite().getDatosSesion().getLocale().getLanguage();
			nivelAutenticacion = String.valueOf(respuestaFront.getInformacionTramite().getDatosSesion().getNivelAutenticacion());
			idPersistencia = respuestaFront.getInformacionTramite().getIdPersistencia();
			tramiteDesc = respuestaFront.getInformacionTramite().getDescripcion();		
			tramiteId = respuestaFront.getInformacionTramite().getModelo();
			procedimientoId = respuestaFront.getInformacionTramite().getIdProcedimiento();
			if (respuestaFront.getInformacionTramite().getDatosSesion().getPersonaPAD() != null) {			
				nif = respuestaFront.getInformacionTramite().getDatosSesion().getPersonaPAD().getNif();
				nombre = respuestaFront.getInformacionTramite().getDatosSesion().getPersonaPAD().getNombreCompleto();
			}
			fechaCreacion = StringUtil.fechaACadena(respuestaFront.getInformacionTramite().getFechaCreacion(), StringUtil.FORMATO_TIMESTAMP);			
		} catch (Exception ex) {
			log.error("No se ha podido recuperar informacion del tramite para poder mostrar debug");
		}
		 
		 String contextoRaiz = (String) request.getSession().getServletContext().getAttribute(Constants.CONTEXTO_RAIZ);
		 String url = contextoRaiz + "/incidencias/formulario?";
		 url += "lang=" + lang;
		 url += "&tramiteDesc=" + encodeParameter(tramiteDesc);
		 url += "&tramiteId=" + encodeParameter(tramiteId);
		 url += "&procedimientoId=" + encodeParameter(procedimientoId);
		 url += "&idPersistencia=" + encodeParameter(idPersistencia);
		 url += "&nif=" + encodeParameter(nif);
		 url += "&nombre=" + encodeParameter(nombre);
		 url += "&fechaCreacion=" + encodeParameter(fechaCreacion);
		 url += "&nivelAutenticacion=" + encodeParameter(nivelAutenticacion);
		 
		 response.sendRedirect(url);	 
		 return null;
     }

	private String encodeParameter(String value)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "ISO-8859-1");
	}
}
