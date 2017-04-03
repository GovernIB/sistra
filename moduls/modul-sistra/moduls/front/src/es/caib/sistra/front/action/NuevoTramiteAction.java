package es.caib.sistra.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="nuevoTramiteForm"
 *  path="/protected/nuevoTramite"
 *  scope="request"
 *  validate="false"
 *  
 *  
 * @struts.action-forward
 *  name="tramiteReducido" path="/protected/irAFormularioTramiteReducido.do"
 *
 * @struts.action-forward
 *  name="success" path=".pasoPasos"
 *  
 *  @struts.action-forward
 *  name="successNew" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="error" path="/fail.do"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class NuevoTramiteAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
		// Se inicia el trámite		
		RespuestaFront respuestaFront = delegate.iniciarTramite();
		this.setRespuestaFront( request, respuestaFront );
		
		 // Establecemos info organismo particularizada por entidad
		setOrganismoInfoEntidad(request, respuestaFront);	
		
		// Verifica si hay error
		if ( this.isSetError( request ) )
		{
			return mapping.findForward("error");
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
		        
        /*
         * MODIFICACION: NO MOSTRAMOS PAGINA DE PASOS
         * 
         * return mapping.findForward("success");
         */        
        // Pasamos directamente a primer paso
        respuestaFront = delegate.siguientePaso();
 		this.setRespuestaFront( request, respuestaFront );
 		if ( this.isSetError( request ) )
 		{
 			return mapping.findForward("error");
 		}
 		
 		
 		// Redirigimos a paso actual (evitar problema refresco pagina)
 		ActionForward redirectForward = new ActionForward();
 		redirectForward.setRedirect(true);
 	    redirectForward.setPath("/protected/irAPaso.do?&ID_INSTANCIA="+(String) request.getAttribute("ID_INSTANCIA"));
 	    return redirectForward;
 		
 		//return mapping.findForward("successNew");
		
    }
}
