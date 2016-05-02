package es.caib.sistra.front.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static Log log = LogFactory.getLog(ContinuarTramitacionAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ContinuarTramitacionForm formulario = (ContinuarTramitacionForm) form;

		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );

		boolean debug = delegate.isDebugEnabled();

		if (debug) {
			log.debug("DEBUGFORM: ContinuarTramitacionAction [GF:" + formulario.getGstfrm() + " - TF:" + formulario.getTOKEN() + "]" );
		}

		// Obtiene gestor formulario de contexto
		GestorFlujoFormulario gestorFormularios = this.obtenerGestorFormulario( request, formulario.getGstfrm());
		if ( gestorFormularios == null ) {
			throw new Exception("No se encuentra gestor formulario en contexto");
		}

		// Recuperamos resultado formulario del gestor de formularios y lo eliminamos del contexto
		ResultadoProcesoFormulario resultadoProcesoFormulario = gestorFormularios.continuarTramitacion( formulario.getTOKEN(), debug );
		this.resetGestorFormulario( request, formulario.getGstfrm() );

		// Recupera instancia tramitacion e invoca a guardar formulario
		RespuestaFront respuestaFront = delegate.guardarFormulario( resultadoProcesoFormulario.getFormulario().getIdentificador(), resultadoProcesoFormulario.getFormulario().getInstancia(),
															resultadoProcesoFormulario.getXmlInicial(), resultadoProcesoFormulario.getXmlActual(), resultadoProcesoFormulario.isGuardadoSinFinalizar() );
		this.setRespuestaFront( request, respuestaFront );

		// Si es tramite reducido, vamos directamente a registrar el tramite
		// Si no es reducido vamos a paso actual
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
        } else {
        	request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
        	return mapping.findForward( "success" );
        }
	}

}
