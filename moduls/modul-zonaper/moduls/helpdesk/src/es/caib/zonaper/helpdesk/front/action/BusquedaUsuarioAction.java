package es.caib.zonaper.helpdesk.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.BusquedaUsuarioForm;
import es.caib.zonaper.helpdesk.front.form.ModificarUsuarioForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 *  name="busquedaUsuarioForm"
 *  path="/busquedaUsuario"
 *  scope="request"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaUsuario"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaUsuarioAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(BusquedaUsuarioAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.USUARIOS_TAB);
		
		// Buscamos usuario segun filtro de busqueda
		BusquedaUsuarioForm busquedaForm = ( BusquedaUsuarioForm ) form;
		
		PadAplicacionDelegate pd = DelegateUtil.getPadAplicacionDelegate();
		PersonaPAD pers = null;
		if (StringUtils.isNotBlank(busquedaForm.getUsuarioNif())){
			pers = pd.obtenerHelpdeskDatosPersonaPorNif(busquedaForm.getUsuarioNif());
		}else{
			pers = pd.obtenerHelpdeskDatosPersonaPorUsuario(busquedaForm.getUsuarioCodi());
		}
		
		ModificarUsuarioForm formStruts = ( ModificarUsuarioForm ) obtenerActionForm( mapping, request, "/modificarUsuario" );
		if (pers != null){
			formStruts.setUsuarioCodiOld(pers.getUsuarioSeycon());
			formStruts.setUsuarioCodiNew(pers.getUsuarioSeycon());
			formStruts.setUsuarioNifNew(pers.getNif());
			formStruts.setUsuarioNombreNew(pers.getNombre());
			formStruts.setUsuarioApellido1New(pers.getApellido1());
			formStruts.setUsuarioApellido2New(pers.getApellido2());
		}
		
		request.setAttribute("persona",pers);		
		
		
		return mapping.findForward( "success" );
    }
	
}
