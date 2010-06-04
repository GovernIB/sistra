package es.caib.sistra.front.action.formulario;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.IrAFormularioForm;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.front.util.Util;
import es.caib.sistra.model.ConfiguracionGestorFlujoFormulario;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
/**
 * @struts.action
 *  name="irAFormularioForm"
 *  path="/protected/irAFormulario"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="fail" path=".mainLayout"
 *  
 * @struts.action-forward
 *  name="mostrarDocumento" path=".mainLayout"
 *  
 *  @struts.action-forward
 *  name="error" path="/fail.do"
 */
public class IrAFormularioAction extends BaseAction
{
	
	private static Log log = LogFactory.getLog( IrAFormularioAction.class );
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		IrAFormularioForm formularioForm = ( IrAFormularioForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		/**En los parametros de vuelta de la llamada irAFormulario, tendremos lo siguiente :
		- Parametros: 
			* datos = <xml> datos iniciales del formulario.
			* configuracion = es.caib.sistra.model.ConfiguracionFormulario@11e1e67
			* modelo = 001
			* version = 1
			*/
	
		RespuestaFront respuestaFront = delegate.irAFormulario( formularioForm.getIdentificador(), formularioForm.getInstancia() );
		this.setRespuestaFront( request, respuestaFront );
		
		if ( this.isSetMessage( request ) )
		{
			return mapping.findForward( "fail" );
		}
		
		TramiteFront tramiteInfo = respuestaFront.getInformacionTramite();
		
		DocumentoFront formulario = tramiteInfo.getFormulario( formularioForm.getIdentificador(), formularioForm.getInstancia() );
		
		if ( formulario == null )
		{
			this.setErrorMessage( request, "errors.formulario.exist", new Object[ ]{ formulario.getDescripcion(), new Integer( formulario.getVersion() )} );
			return mapping.findForward( "error" );
		}
		
		
		ConfiguracionGestorFlujoFormulario confGestorForm = ( ConfiguracionGestorFlujoFormulario ) respuestaFront.getParametros().get( "configuracionGestorForm" );
				
		// Si el formulario esta marcado como solo lectura mostramos documento
		if (confGestorForm.getConfiguracionFormulario().isReadOnly()){			
			return mapping.findForward("mostrarDocumento");
		}
		
		// NO confundir con el que se se genera en un paso posterior para que forms redirija la pagina.
		String tokenGestionFormulario = Util.generateToken(); 
		
		GestorFlujoFormulario gestorFormularios = this.obtenerGestorFormulario( request,tokenGestionFormulario, true );		
		
		Map parametrosRetorno = new LinkedHashMap();
		//parametrosRetorno.put( "jsessionid", request.getSession().getId() );
		parametrosRetorno.put( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO, tokenGestionFormulario ); 
		parametrosRetorno.put( Constants.GESTOR_FORM_PARAM_TOKEN_LLAMADA, formularioForm.getID_INSTANCIA() );
		
		// Redireccionamos a la url proporcionada por el gestor de formularios
		String urlRedireccion = response.encodeRedirectURL( 
					gestorFormularios.irAFormulario(confGestorForm,formulario,tramiteInfo,parametrosRetorno) 
				);
		if (urlRedireccion == null){
			this.setErrorMessage( request, "errors.errorConexionForm");
			return mapping.findForward( "error" );
		}
		
		log.debug("Redireccionant a: " + urlRedireccion );
		
        response.sendRedirect( urlRedireccion );
        
		return null;
		//return mapping.findForward("success");
    }
	
}
