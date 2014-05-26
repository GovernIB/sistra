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
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  path="/protected/registrarTramiteReducido"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".redireccion"
 *
 * @struts.action-forward
 *  name="urlfin" path="/protected/finalizar.do"
 *  
 * @struts.action-forward
 *  name="error" path="/fail.do"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class RegistrarTramiteReducido extends BaseAction
{
	private static Log _log = LogFactory.getLog( RegistrarTramiteReducido.class );

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		if ( _log.isDebugEnabled() )
		_log.debug( "Registro circuito reducido" );
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		// 1º Nos posicionamos en el paso registrar
		RespuestaFront respuestaFront = null;
		int tipoPasoAnterior = PasoTramitacion.PASO_RELLENAR;
		do
		{
			// Avanzamos hasta paso registrar
			respuestaFront = delegate.siguientePaso();
			
			// Controlamos si hay error
			if (respuestaFront.getMensaje() != null && 
				 (respuestaFront.getMensaje().getTipo() ==	 MensajeFront.TIPO_ERROR || respuestaFront.getMensaje().getTipo() == MensajeFront.TIPO_ERROR_CONTINUABLE )
				){
					this.setRespuestaFront(request,respuestaFront);
					return null;
			}
			
			// Si no avanzamos de paso -> error
			if (respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() == tipoPasoAnterior) 
				throw new Exception("La configuracion del tramite no es correcta para circuito reducido: no se puede avanzar hasta el paso registrar despues de rellenar");
			else
				tipoPasoAnterior = respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso();
		}while( respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() < PasoTramitacion.PASO_REGISTRAR );		
		
		// 2º Obtenemos el asiento registral
		AsientoCompleto asiento = (AsientoCompleto) respuestaFront.getParametros().get( "asiento" );
		
		// 3º Registramos el trámite
		RespuestaFront res = delegate.registrarTramite( asiento.getAsiento(), null );
		this.setRespuestaFront( request, res );
		
		// 4º Redirigimos a paso final
			// Controlamos si esta activado la redirección a la url de finalización
		if (	
				respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_FINALIZAR &&
				respuestaFront.getInformacionTramite().isRedireccionFin()
			){
				return mapping.findForward( "urlfin" );
		}
			// Vamos a justificante
		request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
		return mapping.findForward( "success" );
	}

}
