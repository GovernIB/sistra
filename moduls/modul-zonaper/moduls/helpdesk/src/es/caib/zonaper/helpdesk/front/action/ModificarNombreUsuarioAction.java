package es.caib.zonaper.helpdesk.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.NifCif;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.ModificarUsuarioForm;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 *  name="modificarUsuarioForm"
 *  path="/modificarNombreUsuario"
 *  scope="request"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".mensaje"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ModificarNombreUsuarioAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(ModificarNombreUsuarioAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.USUARIOS_TAB);
		
		ModificarUsuarioForm modificarForm = ( ModificarUsuarioForm ) form;		
    	
		// Actualizamos nif usuario
		PadAplicacionDelegate pd = DelegateUtil.getPadAplicacionDelegate();
		pd.actualizarNombreUsuario(modificarForm.getUsuarioCodiOld(), StringUtils.trim(modificarForm.getUsuarioNombreNew()), 
				StringUtils.trim(modificarForm.getUsuarioApellido1New()), StringUtils.trim(modificarForm.getUsuarioApellido2New()));					
				
		// Mostramos mensaje de usuario modificado
		request.setAttribute(Constants.MENSAJE_KEY,getResources(request).getMessage("usuarios.modificado"));
		request.setAttribute( Constants.MESSAGE_ACTION_KEY, "/usuarios" );
		return mapping.findForward( "success" );
    }
	
}
