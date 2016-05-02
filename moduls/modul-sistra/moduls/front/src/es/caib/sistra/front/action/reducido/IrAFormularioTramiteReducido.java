package es.caib.sistra.front.action.reducido;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.form.formulario.IrAFormularioForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  path="/protected/irAFormularioTramiteReducido"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/protected/irAFormulario.do"
 *
 * @struts.action-forward
 *  name="error" path="/fail.do"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class IrAFormularioTramiteReducido extends BaseAction
{
	private static Log _log = LogFactory.getLog( IrAFormularioTramiteReducido.class );
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuestaFront = null;
		do
		{
			respuestaFront = delegate.siguientePaso();

		}while( respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() < PasoTramitacion.PASO_RELLENAR );

		List lstFormularios = respuestaFront.getInformacionTramite().getFormularios();
		if ( lstFormularios.size() < 1 )
		{
			this.setErrorMessage( request, "error.noFormularioCircuitoReducido" );
			return mapping.findForward( "error" );
		}
		// Cogemos el primer formulario y nos dirigimos hacia su tramitación en el sistema de formularios
		DocumentoFront formulario = ( DocumentoFront ) lstFormularios.get( 0 );
		String idInstancia = InstanciaManager.getIdInstancia( request );
		formulario.getIdentificador();
		formulario.getInstancia();
		IrAFormularioForm formStruts = ( IrAFormularioForm ) obtenerActionForm( mapping, request, "/protected/irAFormulario" );
		formStruts.setID_INSTANCIA( idInstancia );
		formStruts.setIdentificador( formulario.getIdentificador() );
		formStruts.setInstancia( formulario.getInstancia() );
		formStruts.setModelo( formulario.getModelo() );
		//request.setAttribute( Globals.FORM_BEANS_KEY, formStruts );
		return mapping.findForward( "success" );
	}

}
