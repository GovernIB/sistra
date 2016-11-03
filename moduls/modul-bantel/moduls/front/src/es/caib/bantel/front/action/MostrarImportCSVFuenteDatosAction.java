package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.form.DetalleFuenteDatosForm;

/**
 * @struts.action
 *  path="/mostrarImportCSVFuenteDatos"
 *  name="detalleFuenteDatosForm"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".importCSVFuenteDatos"
 *  
 */
public class MostrarImportCSVFuenteDatosAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(MostrarImportCSVFuenteDatosAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleFuenteDatosForm formulario = (DetalleFuenteDatosForm) form;
		request.setAttribute("identificador", formulario.getIdentificador());
		return  mapping.findForward("success");			
    }
	
	
}