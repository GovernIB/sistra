package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.util.MensajesUtil;

/**
 * @struts.action
 *  path="/inicioGestionExpediente"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaExpedientes"
 * 
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class IniciGestionExpediente extends BaseAction
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		//request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY2,"3");
		MensajesUtil.setMsg(this.getResources(request));
		return mapping.findForward( "success" );
    }
}
