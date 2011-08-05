package es.caib.zonaper.helpdesk.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.ModificarUsuarioForm;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 *  name="modificarUsuarioForm"
 *  path="/modificarUsuario"
 *  scope="request"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".mensaje"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ModificarUsuarioAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(ModificarUsuarioAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.USUARIOS_TAB);
		
		// Buscamos usuario segun filtro de busqueda
		ModificarUsuarioForm modificarForm = ( ModificarUsuarioForm ) form;
		PadAplicacionDelegate pd = DelegateUtil.getPadAplicacionDelegate();
		pd.modificarHelpdeskCodigoUsuario(modificarForm.getUsuarioCodiOld(),
					modificarForm.getUsuarioCodiNew());
		
		
		// Mostramos mensaje de usuario modificado
		request.setAttribute(Constants.MENSAJE_KEY,getResources(request).getMessage("usuarios.modificado"));
		return mapping.findForward( "success" );
    }
	
}
