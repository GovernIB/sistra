package es.caib.pagosMOCK.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.pagosMOCK.front.Constants;
import es.caib.pagosMOCK.front.form.PagoForm;
import es.caib.pagosMOCK.persistence.delegate.SesionPagoDelegate;

/**
 * @struts.action 
 * 	name="pagoForm"
 *  path="/realizarPago"        
 *  scope="session"
 *  validate="true"
 *  input=".datosPago"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 *  
 */
public class RealizarPagoAction extends BaseAction
{

	Log logger = LogFactory.getLog( RealizarPagoAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		PagoForm pagoForm = (PagoForm) form;
				
		// Realizamos el pago
		SesionPagoDelegate dlg = getSesionPago(request);
		int resultado = dlg.realizarPago(pagoForm.getModoPago(),pagoForm.getNumeroTarjeta(),pagoForm.getFechaCaducidadTarjeta(),pagoForm.getCodigoVerificacionTarjeta());
		
		if (resultado != 0){
			// TODO Tratamiento errores
			request.setAttribute(Constants.MESSAGE_KEY,"sesionPagos.errorPagar");
			return mapping.findForward("fail");
 		}else{
			// Volvemos a sistra
			response.sendRedirect(getUrlRetornoSistra(request));
			return null;
 		}
				
    }
		
}
