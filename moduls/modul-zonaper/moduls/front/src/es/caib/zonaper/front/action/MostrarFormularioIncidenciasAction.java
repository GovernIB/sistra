package es.caib.zonaper.front.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.StringUtil;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

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
		String tramiteDesc = "";
		String tramiteId = "";
		String procedimientoId = "";
		String fechaCreacion = "";
		String idPersistencia = "";
		String nivelAutenticacion = "";
		
		DatosSesion datosSesion = this.getDatosSesion(request);
		lang = this.getLocale(request).getLanguage();
		nivelAutenticacion = String.valueOf(datosSesion.getNivelAutenticacion());
		
		if (datosSesion.getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
			nif = datosSesion.getNifUsuario();
			nombre = datosSesion.getNombreUsuario();
		} else {
		
			// Si es anonimo pasamos el tramite / procedimiento en cuestion
			if (this.getIdPersistencia(request) != null) {
				
				String idPersistenciaSesion = this.getIdPersistencia(request);
				
				// Comprobamos si la clave hace referencia a algun tramite que todavia esta en persistencia y pasamos datos tramite
				PadDelegate padDelegate = DelegatePADUtil.getPadDelegate();
				TramitePersistentePAD tramitePersistente = padDelegate.obtenerTramitePersistente(idPersistenciaSesion);
				if (tramitePersistente != null) {
					idPersistencia = idPersistenciaSesion;
					tramiteDesc = tramitePersistente.getDescripcion();
					tramiteId = tramitePersistente.getTramite();
					procedimientoId = tramitePersistente.getIdProcedimiento();					
					fechaCreacion = StringUtil.fechaACadena(tramitePersistente.getFechaCreacion(), StringUtil.FORMATO_TIMESTAMP);
				} else {
					// Comprobamos si se tiene acceso a algun expediente con esa clase y pasamos id procedimiento
					ElementoExpediente elementoExpe = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(idPersistenciaSesion);
					if (elementoExpe != null && elementoExpe.isAccesoAnonimoExpediente()) {
						procedimientoId = elementoExpe.getExpediente().getIdProcedimiento();
					}				
				}	
			}
			
		}
		 
		 String contextoRaiz = (String) request.getSession().getServletContext().getAttribute(Constants.CONTEXTO_RAIZ);
		 String url = contextoRaiz + "/incidencias/formulario?";
		 url += "lang=" + lang;
		 url += "&nif=" + encodeParameter(nif);
		 url += "&nombre=" + encodeParameter(nombre);
		 url += "&tramiteDesc=" + encodeParameter(tramiteDesc);
		 url += "&tramiteId=" + encodeParameter(tramiteId);
		 url += "&procedimientoId=" + encodeParameter(procedimientoId);
		 url += "&fechaCreacion=" + encodeParameter(fechaCreacion);
		 url += "&idPersistencia=" + encodeParameter(idPersistencia);
		 url += "&nivelAutenticacion=" + encodeParameter(nivelAutenticacion);
		 		 		 
		 response.sendRedirect(url);		 
		 return null;
     }

	private String encodeParameter(String value)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "ISO-8859-1");
	}
	
	
}
