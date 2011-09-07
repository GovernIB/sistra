package es.caib.sistra.front.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.ContinuarTramitacionForm;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.formulario.ResultadoProcesoFormulario;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="continuarTramitacionForm"
 *  path="/protected/continuarTramitacion"
 *  scope="request"
 *  validate="false"
 * 
 * @struts.action-forward
 *  name="success" path=".redireccion"
 *  
 * @struts.action-forward
 *  name="registroTramiteReducido" path="/protected/registrarTramiteReducido.do"
 * 
 * @struts.action-forward
 *  name="asistenteTramiteReducido" path="/protected/asistenteTramiteReducido.do"
 *  
 * @struts.action-forward
 *  name="error" path="/fail.do"
 */
public class ContinuarTramitacionAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ContinuarTramitacionForm formulario = (ContinuarTramitacionForm) form;
		GestorFlujoFormulario gestorFormularios = this.obtenerGestorFormulario( request, formulario.getGstfrm(), false );
		RespuestaFront respuestaFront = null;
		if ( gestorFormularios != null )
		{
			ResultadoProcesoFormulario resultadoProcesoFormulario = gestorFormularios.continuarTramitacion( formulario.getTOKEN() );
			this.resetGestorFormulario( request, formulario.getGstfrm() );
			InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
			respuestaFront = delegate.guardarFormulario( resultadoProcesoFormulario.getFormulario().getIdentificador(), resultadoProcesoFormulario.getFormulario().getInstancia(), resultadoProcesoFormulario.getXmlInicial(), resultadoProcesoFormulario.getXmlActual() );
			this.setRespuestaFront( request, respuestaFront );
		}
		
        // Si es tramite reducido, vamos directamente a registrar el tramite
        if ( respuestaFront != null 
        		&& respuestaFront.getInformacionTramite() != null && 
        			respuestaFront.getInformacionTramite().isCircuitoReducido() )
        {
        	// Si es de tipo asistente vamos a pagina de imprimir
        	if (respuestaFront.getInformacionTramite().isAsistente()){
        		return mapping.findForward( "asistenteTramiteReducido" );
        	}else{
        		return mapping.findForward( "registroTramiteReducido" );
        	}
        }
		
		request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
		return mapping.findForward( "success" );
	}

}
