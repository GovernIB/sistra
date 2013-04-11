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
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="continuarTramitacionForm"
 *  path="/protected/continuarFormCancelacion"
 *  scope="request"
 *  validate="false"
 * 
 * @struts.action-forward
 *  name="success" path=".redireccion"
 * 
 * @struts.action-forward
 *  name="error" path="/fail.do"
 */
public class ContinuarCancelacionAction extends BaseAction
{
	
	private static Log log = LogFactory.getLog(ContinuarCancelacionAction.class);
			
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		ContinuarTramitacionForm formulario = (ContinuarTramitacionForm) form;
		
		log.debug("DEBUGFORM: ContinuarCancelacionAction [GF:" + formulario.getGstfrm() + " - TF:" + formulario.getTOKEN() + "]" );
		
		// Obtiene gestor formulario del contexto
		GestorFlujoFormulario gestorFormularios = this.obtenerGestorFormulario( request, formulario.getGstfrm());
		if ( gestorFormularios == null )
		{
			throw new Exception("No se encuentra gestor formulario en contexto");
		}
		
		// Recupera informacion del gestor de formularios y borra gestor del contexto
		boolean resultadoProcesoFormulario = gestorFormularios.continuarCancelacion( formulario.getTOKEN() );
		if ( !resultadoProcesoFormulario )
		{
			this.setErrorMessage( request, "errors.errorCancelacionForm" );
		}
		this.resetGestorFormulario( request, formulario.getGstfrm() );
		
		
		// Recupera instancia tramitacion y va a paso actual
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = delegate.pasoActual();
        // Si es trámite con circuito reducido abandonamos tramite
		// Si no, vamos a paso actual
		String accionRedireccion = "/protected/irAPaso.do";
		if ( respuestaFront != null 
        		&& respuestaFront.getInformacionTramite() != null && 
        			respuestaFront.getInformacionTramite().isCircuitoReducido() )
        {
			// Simplemente abandonamos el trámite 
			accionRedireccion = "/protected/abandonarTramite.do";			
        }
		request.setAttribute( "accionRedireccion", accionRedireccion );
        return mapping.findForward( "success" );
	}
}
