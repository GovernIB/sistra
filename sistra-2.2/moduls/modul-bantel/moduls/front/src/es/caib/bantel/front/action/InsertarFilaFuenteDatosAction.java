package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.form.DetalleFuenteDatosForm;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

/**
 * @struts.action
 *  name="detalleFuenteDatosForm"
 *  path="/insertarFilaFuenteDatos"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".detalleFuenteDatos"
 *  
 * @struts.action-forward
 *  name="fail" path=".error"
 * 
 */
public class InsertarFilaFuenteDatosAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		
		try {
			DetalleFuenteDatosForm detalleFuenteDatos = ( DetalleFuenteDatosForm ) form;
			FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
			delegate.altaFilaFuenteDatos(detalleFuenteDatos.getIdentificador());
			
			response.sendRedirect("detalleFuenteDatos.do?identificador=" + detalleFuenteDatos.getIdentificador());
			return null;
		} catch (Exception ex) {
			String keyError = "fuenteDatos.errorPKNuevaFila";
			
			// Controlamos si es una excepcion de PK
			if (ExceptionUtils.getRootCauseMessage(ex).indexOf("PK-Exception") != -1) {
				keyError = "fuenteDatos.errorPK";
			}
			
			request.setAttribute("message",resources.getMessage( getLocale( request ), keyError));
			return mapping.findForward( "fail" );
		}
		
    }

}
