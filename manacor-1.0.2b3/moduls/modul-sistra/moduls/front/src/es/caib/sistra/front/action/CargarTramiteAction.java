package es.caib.sistra.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.form.CargarTramiteForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.MensajeFront;
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
		MensajeFront mensaje = respuestaFront.getMensaje();
		
		this.setRespuestaFront( request, respuestaFront );
		
		if ( mensaje != null && mensaje.getTipo() == MensajeFront.TIPO_ERROR  )
		{
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
		
        
        // Redirigimos a paso actual (evitar problema refresco pagina)
        ActionForward redirectForward = new ActionForward();
 		redirectForward.setRedirect(true);
 	    redirectForward.setPath("/protected/irAPaso.do?&ID_INSTANCIA="+(String) request.getAttribute("ID_INSTANCIA"));
 	    return redirectForward;
        
		//return mapping.findForward( "success" );
		
    }
}
