package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.NifCif;
import es.caib.zonaper.front.form.ActualizarDatosPersonalesForm;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
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
		PersonaPAD persona = null;
		
		DatosSesion datosSesion = this.getDatosSesion( request );
		if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
			persona = DelegateUtil.getPadAplicacionDelegate().obtenerDatosPersonaPADporNif(this.getDatosSesion(request).getNifEntidad());
		}else{
			persona = DelegateUtil.getPadAplicacionDelegate().obtenerDatosPersonaPADporUsuario(this.getDatosSesion(request).getCodigoUsuario());
		}
	
		ActualizarDatosPersonalesForm f = (ActualizarDatosPersonalesForm)  obtenerActionForm(mapping,request, "/protected/actualizarDatosPersonales");
		BeanUtils.copyProperties(f,persona);
		f.setTipoPersona(NifCif.esCIF(f.getNif())?"CIF":"NIF");
		
		return mapping.findForward("success");
		
		
	}

}
