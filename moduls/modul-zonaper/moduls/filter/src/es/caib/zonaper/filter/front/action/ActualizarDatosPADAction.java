package es.caib.zonaper.filter.front.action;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.NifCif;
import es.caib.zonaper.filter.front.form.ActualizarDatosPADForm;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="actualizarDatosPADForm"
 *  path="/actualizarDatosPAD"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="failValidation" path="/datosPAD.do"
 */
public class ActualizarDatosPADAction extends Action
{
	private static String KEY_ERROR_DESCONOCIDO = "actualizarDatosPAD.error.desconocido";
	private static String KEY_ORDEN_PARTICULAS_FALTA_NOMBRE = "actualizarDatosPAD.error.ordenparticulas.faltaNombre";
	private static String KEY_ORDEN_PARTICULAS_FALTA_APELLIDO1 = "actualizarDatosPAD.error.ordenparticulas.faltaApellido1";
	private static String KEY_NUMERO_PARTICULAS_DEFECTO = "actualizarDatosPAD.error.numeroparticulas.defecto";
	private static String KEY_NUMERO_PARTICULAS_EXCESO = "actualizarDatosPAD.error.numeroparticulas.exceso";
	private static String KEY_PARTICULAS_NOMBRE_ERRONEAS = "actualizarDatosPAD.error.particulasErroneas";
	private static String KEY_ERROR_NIF_INCORRECTO = "actualizarDatosPAD.error.nifIncorrecto";
	
	private static Log _log = LogFactory.getLog( ActualizarDatosPADAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ActualizarDatosPADForm formulario = ( ActualizarDatosPADForm ) form;
		PadAplicacionDelegate delegate = DelegateUtil.getPadAplicacionDelegate();	
		Principal seyconPrincipal = request.getUserPrincipal();
		String nombre = formulario.getNombre().trim().replaceAll( "\\s+", " " );
		
		String apellido1 = formulario.getApellido1();
		apellido1 = apellido1 != null ? apellido1.trim().replaceAll( "\\s+", " " ) : apellido1;
		
		String apellido2 = formulario.getApellido2();
		apellido2 = apellido2 != null ? apellido2.trim().replaceAll( "\\s+", " " ) : apellido2;
		
		PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
		
		PersonaPAD personaPAD = new PersonaPAD();
		personaPAD.setApellido1( apellido1 );
		personaPAD.setApellido2( apellido2 );
		personaPAD.setNif( plgLogin.getNif(seyconPrincipal).toUpperCase());
		personaPAD.setUsuarioSeycon( seyconPrincipal.getName() );
		personaPAD.setNombre( nombre );
		personaPAD.setPersonaJuridica( NifCif.esCIF( personaPAD.getNif() ) );
		
		
		String keyError = validar(personaPAD);
		if ( keyError != null )
		{
			if ( _log.isDebugEnabled() ) _log.debug( "Error en validacion datos de la persona PAD " + keyError );
			request.setAttribute( "keyError", keyError );
			return mapping.findForward( "failValidation" );
		}
		
		if ( _log.isDebugEnabled() ) _log.debug( "Actualizando datos PAD para el usuario " + seyconPrincipal.getName() );
		// Evitamos rebotes
		PersonaPAD personaOld = delegate.obtenerDatosPersonaPADporNif(personaPAD.getNif());
		if (personaOld == null) {	
			delegate.altaPersona( personaPAD );
		} else {
			// Comprobamos si es un usuario generado de forma automatica: actualizamos codigo usuario y modificamos datos
			if (personaOld.isUsuarioSeyconGeneradoAuto()) {					
				delegate.actualizarCodigoUsuario(personaOld.getUsuarioSeycon(), seyconPrincipal.getName());
				delegate.modificarPersona(personaPAD);				
			}
		}
		
		if ( _log.isDebugEnabled() ) _log.debug( "Redirigiendo a URL original " + formulario.getUrlOriginal() );
		response.sendRedirect( formulario.getUrlOriginal() );
		return null;
	}
	
	
	
	private String validar(PersonaPAD personaPAD) {		
		int errores[];		
		try{
			errores = DelegateUtil.getPadAplicacionDelegate().validarModificacionDatosPersonaPAD(personaPAD);
		}catch (Exception ex){			
			return KEY_ERROR_DESCONOCIDO;
		}
		
		// Mostramos primer error		
		if (errores.length > 0){
			switch (errores[0]){
				case PersonaPAD.ERROR_EMAIL:
					return KEY_ERROR_DESCONOCIDO;
				case PersonaPAD.ERROR_NIF_INCORRECTO:
					return KEY_ERROR_NIF_INCORRECTO;
				case PersonaPAD.ERROR_NUMERO_PARTICULAS_DEFECTO:
					return KEY_NUMERO_PARTICULAS_DEFECTO;
				case PersonaPAD.ERROR_NUMERO_PARTICULAS_EXCESO:
					return KEY_NUMERO_PARTICULAS_EXCESO;
				case PersonaPAD.ERROR_ORDEN_PARTICULAS_FALTA_APELLIDO1:
					return KEY_ORDEN_PARTICULAS_FALTA_APELLIDO1;	
				case PersonaPAD.ERROR_ORDEN_PARTICULAS_FALTA_NOMBRE:
					return KEY_ORDEN_PARTICULAS_FALTA_NOMBRE;
				case PersonaPAD.ERROR_PARTICULAS_NOMBRE_ERRONEAS:					
					return KEY_PARTICULAS_NOMBRE_ERRONEAS;
				case PersonaPAD.ERROR_TELEFONO_MOVIL:
					return KEY_ERROR_DESCONOCIDO;
				case PersonaPAD.ERROR_USUARIO_SEYCON_INCORRECTO:					
					return KEY_ERROR_DESCONOCIDO;
				default:					
					return KEY_ERROR_DESCONOCIDO;
			}
		}
		
		// Valido
		return null;
	}
		
}
