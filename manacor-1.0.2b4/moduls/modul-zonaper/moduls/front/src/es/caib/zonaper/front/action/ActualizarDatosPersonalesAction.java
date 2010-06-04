package es.caib.zonaper.front.action;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.form.ActualizarDatosPersonalesForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 *  name="actualizarDatosPersonalesForm"
 *  path="/protected/actualizarDatosPersonales"        
 *  scope="request"
 *  validate="true"
 *  input=".datosPersonales"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/menuAutenticado"
 *
 */
public class ActualizarDatosPersonalesAction extends BaseAction
{
	
	private static Log _log = LogFactory.getLog( ActualizarDatosPersonalesAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActualizarDatosPersonalesForm formulario = ( ActualizarDatosPersonalesForm ) form;
		
		// Realizamos validaciones sobre los datos
		Principal seyconPrincipal = request.getUserPrincipal();
		if ( _log.isDebugEnabled() ) _log.debug( "Actualizando datos PAD para el usuario " + seyconPrincipal.getName() );
		
		// Ajustamos textos 
		formulario.setNombre(formulario.getNombre().trim().replaceAll( "\\s+", " " ));
		formulario.setApellido1(formulario.getApellido1() != null ? formulario.getApellido1().trim().replaceAll( "\\s+", " " ) : formulario.getApellido1());
		formulario.setApellido2(formulario.getApellido2() != null ? formulario.getApellido2().trim().replaceAll( "\\s+", " " ) : formulario.getApellido2());
		
		// Actualizamos datos
		PadAplicacionDelegate delegate = DelegateUtil.getPadAplicacionDelegate();		
		PersonaPAD personaPAD = delegate.obtenerDatosPersonaPADporUsuario(this.getDatosSesion(request).getCodigoUsuario());
		String nifOriginal = personaPAD.getNif();
		BeanUtils.copyProperties(personaPAD,formulario);
		personaPAD.setNif(nifOriginal);
		delegate.modificarPersona(personaPAD);
		
		return mapping.findForward("success");
	}
	
	
	
}
