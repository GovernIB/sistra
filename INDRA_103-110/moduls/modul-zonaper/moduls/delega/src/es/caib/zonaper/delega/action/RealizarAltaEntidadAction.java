package es.caib.zonaper.delega.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.util.NifCif;
import es.caib.zonaper.delega.form.DetalleEntidadForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
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
	private static Log _log = LogFactory.getLog( RealizarAltaEntidadAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEntidadForm entidadForm = (DetalleEntidadForm)form;
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		ActionErrors messages = new ActionErrors();
		try{
			
			// Obtenemos persona a partir del nif
			PersonaPAD persona = padAplic.obtenerDatosPersonaPADporNif(entidadForm.getPersona().getNif());
			
			// Comprobamos si es modificacion o alta
			if(entidadForm.getModificacio() != null && "S".equals(entidadForm.getModificacio()) ){
				// Modificamos datos entidad
				persona.setNombre(entidadForm.getPersona().getNombre());
				persona.setApellido1(entidadForm.getPersona().getApellido1());
				persona.setCodigoPostal(entidadForm.getPersona().getCodigoPostal());
				persona.setDireccion(entidadForm.getPersona().getDireccion());
				persona.setEmail(entidadForm.getPersona().getEmail());
				persona.setHabilitarAvisosExpediente(entidadForm.getPersona().isHabilitarAvisosExpediente());
				persona.setMunicipio(entidadForm.getPersona().getMunicipio());
				persona.setProvincia(entidadForm.getPersona().getProvincia());
				persona.setTelefonoFijo(entidadForm.getPersona().getTelefonoFijo());
				persona.setTelefonoMovil(entidadForm.getPersona().getTelefonoMovil());
				padAplic.modificarPersona(persona);				
			}else{
				// Alta entidad
				datosCorrectos(entidadForm.getPersona().getNif(),entidadForm.getPersona().getNombre(),request,messages);
				if(persona != null){
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
					// Generamos codigo usuario de forma automatica
					PersonaPAD persNew = padAplic.altaPersonaCodigoUsuarioAuto(entidadForm.getPersona());
					entidadForm.getPersona().setUsuarioSeycon(persNew.getUsuarioSeycon());
					request.getSession().setAttribute("altaCorrecta","S");
				}
			}
		
			// Redirigimos a la modificar entidad
			response.sendRedirect("modificarEntidad.do?nif=" + entidadForm.getPersona().getNif() );
			return null;
			
		}catch(Exception e){
			_log.error("Error modificando entidad", e);
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.guardar.entidad"));
        	saveErrors(request,messages);  
			return mapping.findForward("fail");
		}
		
    }

	private void datosCorrectos(String nif, String nombre, HttpServletRequest request, ActionErrors messages){
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
