package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.form.ActualizarAlertasForm;
import es.caib.zonaper.front.util.ZonapersFrontRequestHelper;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/protected/mostrarAlertas"
 *  
 * @struts.action-forward
 *  name="success" path=".alertas"
 *  
 */
public class MostrarAlertasAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (!ZonapersFrontRequestHelper.isHabilitarApartadoAlertas(request)) {
			throw new Exception("No esta habilitado el apartado de alertas");
		}
		
		// Cargamos los datos actuales
		PersonaPAD persona = DelegateUtil.getPadAplicacionDelegate().obtenerDatosPersonaPADporUsuario(this.getDatosSesion(request).getCodigoUsuario());		
		ActualizarAlertasForm f = (ActualizarAlertasForm)  obtenerActionForm(mapping,request, "/protected/actualizarAlertas");
		f.setHabilitarAvisosExpediente(persona.isHabilitarAvisosExpediente());
		
		return mapping.findForward("success");		
	}

}
