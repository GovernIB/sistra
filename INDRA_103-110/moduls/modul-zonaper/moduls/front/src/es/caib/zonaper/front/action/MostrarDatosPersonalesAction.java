package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.form.ActualizarDatosPersonalesForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/protected/mostrarDatosPersonales"
 *  
 * @struts.action-forward
 *  name="success" path=".datosPersonales"
 *  
 */
public class MostrarDatosPersonalesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Cargamos los datos actuales
		PersonaPAD persona = DelegateUtil.getPadAplicacionDelegate().obtenerDatosPersonaPADporUsuario(this.getDatosSesion(request).getCodigoUsuario());		
		ActualizarDatosPersonalesForm f = (ActualizarDatosPersonalesForm)  obtenerActionForm(mapping,request, "/protected/actualizarDatosPersonales");
		BeanUtils.copyProperties(f,persona);
		
		return mapping.findForward("success");
		
		
	}

}
