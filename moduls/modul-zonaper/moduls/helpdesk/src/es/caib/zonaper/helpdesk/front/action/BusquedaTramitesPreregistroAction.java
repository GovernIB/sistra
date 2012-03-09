package es.caib.zonaper.helpdesk.front.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.BusquedaTramitePreregistroForm;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;

/**
 * @struts.action
 *  name="busquedaTramitePreregistroForm"
 *  path="/busquedaTramitesPreregistro"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaTramitesPreregistro"
 *
 */
public class BusquedaTramitesPreregistroAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(BusquedaTramitesPreregistroAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		BusquedaTramitePreregistroForm preregistroForm = ( BusquedaTramitePreregistroForm ) form;
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.PREREGISTRO_TAB);		
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicial = df.parse(preregistroForm.getFechaInicial());
		Date fechaFinal = df.parse(preregistroForm.getFechaFinal());
		String modelo = preregistroForm.getModelo();
		char caducidad = preregistroForm.getCaducidad(); 
		
		EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
		try {
			List result = epd.listarEntradaPreregistrosNoConfirmados(modelo, caducidad, fechaInicial, fechaFinal);
			request.setAttribute( "lstTramites", result);
			return mapping.findForward( "success" );
		} catch (DelegateException e) {
			request.setAttribute("estado", "X");
			return mapping.findForward( "success" );
		}
		
		
    }

    
   
}
