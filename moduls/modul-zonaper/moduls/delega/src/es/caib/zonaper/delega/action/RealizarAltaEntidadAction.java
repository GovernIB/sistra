package es.caib.zonaper.delega.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.NifCif;
import es.caib.zonaper.delega.form.DetalleEntidadForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 * 	name="detalleEntidadForm"
 *  path="/realizarAltaEntidad"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".altaEntidad"
 *
 * @struts.action-forward
 *  name="errors" path=".altaEntidad"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class RealizarAltaEntidadAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEntidadForm entidadForm = (DetalleEntidadForm)form;
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		ActionErrors messages = new ActionErrors();
		try{
			if(entidadForm.getModificacio() != null && "S".equals(entidadForm.getModificacio()) ){
				boolean habilitada = deleg.habilitadaDelegacion(entidadForm.getPersona().getNif());
				padAplic.modificarPersona(entidadForm.getPersona());
				if(habilitada){
					deleg.habilitarDelegacion(entidadForm.getPersona().getNif(),"S");
				}
			}else{
				datosCorrectos(entidadForm.getPersona().getNif(),entidadForm.getPersona().getNombre(),entidadForm.getPersona().getUsuarioSeycon(),request,messages);
				if(padAplic.obtenerDatosPersonaPADporNif(entidadForm.getPersona().getNif()) != null){
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nif.ya.existe"));
				}
				if(padAplic.obtenerDatosPersonaPADporUsuario(entidadForm.getPersona().getUsuarioSeycon()) != null){
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.usuario.ya.existe"));
				}
				if(NifCif.esNIF(entidadForm.getPersona().getNif())){
					entidadForm.getPersona().setPersonaJuridica(false);						
				}else if(NifCif.esCIF(entidadForm.getPersona().getNif())){
					entidadForm.getPersona().setPersonaJuridica(true);
				}else{
					messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.tipo.nif.usuario"));
				}
				if(messages.size() > 0){
		        	saveErrors(request,messages);  
					return mapping.findForward("errors");
				}else{
					padAplic.altaPersona(entidadForm.getPersona());
					request.getSession().setAttribute("altaCorrecta","S");
				}
			}
//			Redirigimos a la modificar entidad
			response.sendRedirect("modificarEntidad.do?nif=" + entidadForm.getPersona().getNif() );
			return null;
			
		}catch(Exception e){
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.guardar.entidad"));
        	saveErrors(request,messages);  
			return mapping.findForward("fail");
		}
		
    }

	private void datosCorrectos(String nif, String nombre, String nombreUsuario, HttpServletRequest request, ActionErrors messages){
       	if(StringUtils.isBlank(nombreUsuario)){
       		messages.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.altaEntidad.seycon.vacio"));
       	}
       	if(StringUtils.isBlank(nif)){
       		messages.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.altaEntidad.NIF.vacio"));
       	}else if(NifCif.validaDocumento(nif) == -1){
       		messages.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.altaEntidad.NIF.incorrecto"));
           }
       	if(StringUtils.isBlank(nombre)){
       		messages.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.altaEntidad.nombre.vacio"));
      	}
	}
}
