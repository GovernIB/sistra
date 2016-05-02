package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.form.ConfirmarPagoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="confirmarPagoForm"
 *  path="/protected/confirmarPago"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 *
 * @struts.action-forward
 *  name="redireccion" path=".redireccion"
 */
public class ConfirmarPagoAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(ConfirmarPagoAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		ConfirmarPagoForm formulario = ( ConfirmarPagoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		RespuestaFront respuesta = delegate.confirmarPago( formulario.getIdentificador(), formulario.getInstancia() );
		this.setRespuestaFront( request, respuesta );

		// En caso de que se haya confirmado correctamente el pago notificamos que se de por finalizada la sesion de pago
		if (!this.isSetError(request)){
			DocumentoFront docPago = respuesta.getInformacionTramite().getPago(formulario.getIdentificador(), formulario.getInstancia());
			if (docPago.getEstado() == DocumentoFront.ESTADO_CORRECTO){
				try{
					delegate.finalizarSesionPago(formulario.getIdentificador(), formulario.getInstancia());
				}catch(Exception ex){
					log.error("Error finalizando sesion pago",ex);
				}
			}
		}

		// Si se ha pasado a paso registrar, redirigimo a irAPAso
		if (respuesta.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_REGISTRAR) {
			request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
			return mapping.findForward( "redireccion" );
		} else {
			return mapping.findForward("success");
		}
    }
}
