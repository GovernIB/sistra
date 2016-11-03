package es.caib.pagosMOCK.front.action;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.pagosMOCK.front.Constants;
import es.caib.pagosMOCK.front.form.InitForm;
import es.caib.pagosMOCK.persistence.delegate.DelegateUtil;
import es.caib.pagosMOCK.persistence.delegate.SesionPagoDelegate;
import es.caib.sistra.plugins.pagos.DatosPago;

/**
 * @struts.action
 *  name="initForm"
 *  path="/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class InitAction extends BaseAction
{

	Log logger = LogFactory.getLog( InitAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		// Forzamos a reiniciar sesion
    	request.getSession().invalidate();
    	request.getSession();
		
		// Obtenemos token de acceso
		InitForm initForm = ( InitForm ) form;
			
		// Creamos sesion de pago
		SesionPagoDelegate dlg = DelegateUtil.getSesionPagoDelegate();
		try{
			dlg.create(initForm.getToken());
		}catch(Exception ex){
			request.setAttribute(Constants.MESSAGE_KEY,"sesionPagos.errorIniciar");
			return mapping.findForward("fail");
		}
		
		// Almacenamos sesion de pago y la url de retorno a sistra
		setSesionPago(request,dlg);
		setUrlRetornoSistra(request,dlg.obtenerUrlRetornoSistra());
		
		// Obtenemos datos del pago para establecer locale
		DatosPago dp = dlg.obtenerDatosPago();
		setLocale(request, new Locale( dp.getIdioma() ) );
				
		// Redirigimos a asistente pago
		response.sendRedirect("datosPago.do");
        return null;
		
    }
		
}
