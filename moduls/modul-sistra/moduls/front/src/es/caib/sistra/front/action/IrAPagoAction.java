package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.IrAPagoForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.front.util.Util;
import es.caib.sistra.model.PasoTramitacion;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="irAPagoForm"
 *  path="/protected/irAPago"
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
public class IrAPagoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		IrAPagoForm formulario = ( IrAPagoForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
		// Establecemos urls de retorno a sistra y de mantenimiento de sesion
		String urlSistra = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("sistra.url");
		String urlRetorno = urlSistra + request.getContextPath() +  
							"/protected/confirmarPago.do?" +
							"ID_INSTANCIA="+(String) request.getAttribute("ID_INSTANCIA")	+
							"&identificador="+formulario.getIdentificador() + 
							"&instancia="+formulario.getInstancia();
		String urlMantenimientoSesion = Util.generaUrlMantenimientoSesion(InstanciaManager.getIdInstancia(request));
		
		// Iniciamos sesion de pago contra pasarela de pagos
		RespuestaFront respuestaFront = delegate.irAPago(formulario.getIdentificador(),
														formulario.getInstancia(),
														urlRetorno,
														urlMantenimientoSesion);
		// Establecemos resultado en el manejador
		this.setRespuestaFront( request, respuestaFront );
		
		// Si se ha generado mensaje (error, warning, info, etc), mostramos mensaje
		if (this.isSetMessage(request)) return mapping.findForward("success");		
		
		// Si tras ir a pago, el paso actual pasa a ser registro  
		if (respuestaFront.getInformacionTramite().getPasoTramitacion().getTipoPaso() == PasoTramitacion.PASO_REGISTRAR) {
			request.setAttribute( "accionRedireccion", "/protected/irAPaso.do" );
			return mapping.findForward( "redireccion" );
		} 
		
		// Si no ha sucedido ninguna incidencia redirigimos al asistente de pagos
		String urlSesionPago = (String) respuestaFront.getParametros().get(Constants.URL_SESIONPAGO_KEY);
		response.sendRedirect(urlSesionPago);
		return null;
    }
}
