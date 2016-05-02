package es.caib.sistra.front.action.reducido;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.action.BaseAction;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  path="/protected/asistenteTramiteReducido"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".redireccion"
 *
 * @struts.action-forward
 *  name="error" path="/fail.do"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class AsistenteTramiteReducido extends BaseAction
{
	private static Log _log = LogFactory.getLog( AsistenteTramiteReducido.class );

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );

		// 1º Nos posicionamos en el siguiente paso: imprimir
		RespuestaFront respuestaFront = null;
		do
		{
			respuestaFront = delegate.siguientePaso();

		}while( respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() != PasoTramitacion.PASO_IMPRIMIR );

		request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
		return mapping.findForward( "success" );
	}

}
