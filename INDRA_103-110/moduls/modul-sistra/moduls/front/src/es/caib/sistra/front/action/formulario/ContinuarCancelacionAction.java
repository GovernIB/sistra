package es.caib.sistra.front.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String accionRedireccion = "/protected/irAPaso.do";
		ContinuarTramitacionForm formulario = (ContinuarTramitacionForm) form;
		GestorFlujoFormulario gestorFormularios = this.obtenerGestorFormulario( request, formulario.getGstfrm(), false );
		if ( gestorFormularios != null )
		{
			boolean resultadoProcesoFormulario = gestorFormularios.continuarCancelacion( formulario.getTOKEN() );
			if ( !resultadoProcesoFormulario )
			{
				this.setErrorMessage( request, "errors.errorCancelacionForm" );
			}
			this.resetGestorFormulario( request, formulario.getGstfrm() );
		}
		// Obtenemos la informacion del trámite
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = delegate.pasoActual();
        // Si se ha abandonado el formulario y se trata de un trámite con circuito reducido
		if ( respuestaFront != null 
        		&& respuestaFront.getInformacionTramite() != null && 
        			respuestaFront.getInformacionTramite().isCircuitoReducido() )
        {
			// Simplemente abandonamos el trámite 
			accionRedireccion = "/protected/abandonarTramite.do";
			
			// vamos a la página inicial OLD
			/*
			String modelo = respuestaFront.getInformacionTramite().getModelo();
			int version = respuestaFront.getInformacionTramite().getVersion();
        	accionRedireccion = "/protected/init.do?ID_INSTANCIA=" + InstanciaManager.getIdInstancia( request ) + "&modelo=" + modelo + "&version=" + version;
        	*/
        }
       	request.setAttribute( "accionRedireccion", accionRedireccion );
        	
		return mapping.findForward( "success" );
	}
}
