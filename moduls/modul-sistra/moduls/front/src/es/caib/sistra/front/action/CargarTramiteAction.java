package es.caib.sistra.front.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.CargarTramiteForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.ParametrosMensaje;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="cargarTramiteForm"
 *  path="/protected/cargarTramite"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="tramiteReducido" path="/protected/irAFormularioTramiteReducido.do"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class CargarTramiteAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		char modoAutenticacion = this.getMetodoAutenticacion( request );
		CargarTramiteForm formulario = ( CargarTramiteForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );

		String idPersistencia = formulario.getIdPersistencia();

		if ( StringUtils.isEmpty( idPersistencia ) )
		{
			this.setErrorMessage( request, "errors.tramite.idPersistenciaNoValido" );
			return mapping.findForward( "fail" );
		}

		idPersistencia = idPersistencia.trim();

		RespuestaFront respuestaFront = delegate.cargarTramite(idPersistencia);

		// Establecemos info organismo particularizada por entidad
		setOrganismoInfoEntidad(request, respuestaFront);

		MensajeFront mensaje = respuestaFront.getMensaje();

		if(mensaje != null && MensajeFront.MENSAJE_TRAMITETERMINADO.equals(mensaje.getMensaje())){

			String entidadParam = "";
			if (respuestaFront.getInformacionTramite() != null && respuestaFront.getInformacionTramite().getEntidad() != null) {
				entidadParam = "&entidad" +  respuestaFront.getInformacionTramite().getEntidad();
			}

			 // Redirigimos a zonaperfront
	 	    String urlZonaper = "/zonaperfront/protected/init.do?tramite=" + idPersistencia +
	 	    			(StringUtils.isBlank(formulario.getLanguage())?"":"&language="+formulario.getLanguage()) +
	 	    			entidadParam;
			response.sendRedirect(request.getSession().getServletContext().getAttribute(Constants.CONTEXTO_RAIZ) + urlZonaper);
	 	    return null;
		}
		this.setRespuestaFront( request, respuestaFront );

		if ( mensaje != null && mensaje.getTipo() == MensajeFront.TIPO_ERROR  )
		{
			// Indicamos destino tras mensaje: zonaperfront
			ParametrosMensaje param = new ParametrosMensaje();
			param.setAction("irAZonaPersonal");
			request.setAttribute( Constants.MENSAJE_PARAM, param );

			// Mostramos pantalla error
			return mapping.findForward( "fail" );
		}

		// En caso de que el trámite este en un idioma diferente al de la sesión  ajustamos el
		// idioma de la sesión
		if ( !respuestaFront.getInformacionTramite().getDatosSesion().getLocale().getLanguage().equals(this.getLocale(request).getLanguage()) )
        {
        	this.setLocale(request,new Locale(respuestaFront.getInformacionTramite().getDatosSesion().getLocale().getLanguage()));
        }


        // Si es tramite reducido, vamos directamente al formulario
        if ( respuestaFront.getInformacionTramite().isCircuitoReducido() )
        {
        	return mapping.findForward( "tramiteReducido" );
        }

        // Si el paso es registrar y está activado registro automático, redirigimos a paso anterior
        String paramPaso = "";
        if (respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_REGISTRAR &&
        	respuestaFront.getInformacionTramite().isRegistroAutomatico()) {
        	paramPaso = "&step=" + (respuestaFront.getInformacionTramite().getPasoActual() - 1);
        }

        // Redirigimos a paso actual (evitar problema refresco pagina)
        ActionForward redirectForward = new ActionForward();
 		redirectForward.setRedirect(true);
 	    redirectForward.setPath("/protected/irAPaso.do?&ID_INSTANCIA="+(String) request.getAttribute("ID_INSTANCIA") + paramPaso) ;
 	    return redirectForward;

		//return mapping.findForward( "success" );

    }
}
