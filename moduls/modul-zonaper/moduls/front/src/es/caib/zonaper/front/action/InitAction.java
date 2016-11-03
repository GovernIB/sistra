package es.caib.zonaper.front.action;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="initForm"
 *  path="/protected/init"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path="/protected/menuAutenticado.do"
 *  
 * @struts.action-forward
 *  name="inicioAnonimo" path="/protected/menuAnonimo.do" 
 *
 */
public class InitAction extends BaseAction
{

	Log logger = LogFactory.getLog( InitAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {				 	 		
 		
		// Si no existe sesion la creamos
		DatosSesion datosSesion = this.getDatosSesion( request );
		if ( datosSesion == null )
		{
			datosSesion = crearSesion(request);
			this.setDatosSesion( request, datosSesion );
		}
		
		// Enlaces directos a elementos: notificacion , aviso y tramite
		if (request.getParameter("notificacion")!=null || request.getParameter("aviso")!=null || request.getParameter("tramite")!=null ) {
			// Obtenemos codigo expediente asociado a enlace directo
			Long codigoExpediente = obtenerCodigoExpedienteEnlaceDirecto(request);
			// Redirigimos a elemento
			if (codigoExpediente == null) {
				throw new Exception("No se ha podido obtener expediente asociado");
			}
			response.sendRedirect(request.getContextPath() + "/protected/mostrarDetalleExpediente.do?id=" + codigoExpediente);
			return null;
		}
		
		
		// Redirigimos a inicio segun tipo de autenticacion
		if ( datosSesion.getNivelAutenticacion() == Constants.NIVEL_AUTENTICACION_ANONIMO )
		{
			return mapping.findForward( "inicioAnonimo" );
		} else {
			return mapping.findForward( "success" );
		}
    }


	/**
	 * Obtiene codigo expediente asociado a: notificacion, aviso y tramite (si tras finalizar asistente se redirecciona a zona personal)
	 * @param request
	 * @return Codigo expediente a visualizar
	 * @throws DelegateException
	 */
	private Long obtenerCodigoExpedienteEnlaceDirecto(HttpServletRequest request)
			throws DelegateException {
		
		// Obtenemos idpersistencia
		String idPersistencia = null;
		if (request.getParameter("notificacion")!=null ){			
			idPersistencia = request.getParameter("notificacion");
		} else if (request.getParameter("aviso")!=null ){			
			idPersistencia = request.getParameter("aviso");									
		} else if (request.getParameter("tramite")!=null ){
			idPersistencia = request.getParameter("tramite");			
		}
		
		// En caso de ser anonimo establecemos clave de acceso en sesion
		if (this.getDatosSesion(request).getNivelAutenticacion() == 'A') {
			this.setIdPersistencia(request, idPersistencia);
		}
		
		// Obtenemos codigo expediente asociado
		ElementoExpediente elementoExpediente = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(idPersistencia);
		if (elementoExpediente != null) {
			// Verificamos que coincida el tipo
			if (request.getParameter("notificacion")!=null &&
					!elementoExpediente.getTipoElemento().equals(ElementoExpediente.TIPO_NOTIFICACION)){			
				elementoExpediente = null;
			} else if (request.getParameter("aviso")!=null &&
					!elementoExpediente.getTipoElemento().equals(ElementoExpediente.TIPO_AVISO_EXPEDIENTE)){			
				elementoExpediente = null;					
			} else if (request.getParameter("tramite")!=null  &&
					!elementoExpediente.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA) &&
					!elementoExpediente.getTipoElemento().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)){
				elementoExpediente = null;		
			}			
		}
		
		// Devolvemos codigo expediente
		Long codigoExpediente = null;
		if (elementoExpediente != null) {
			codigoExpediente = elementoExpediente.getExpediente().getCodigo();
		}
		return codigoExpediente;
	}

	/**
	 * Crea sesion y realiza log de acceso
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private DatosSesion crearSesion(HttpServletRequest request)
			throws Exception {
		String descripcion = "";
		String result = "S";
		DatosSesion datosSesion = null;
		try
		{
			Principal seyconPrincipal = this.getPrincipal( request );
			
			PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
			
			datosSesion = new DatosSesion();
			datosSesion.setNivelAutenticacion(  plgLogin.getMetodoAutenticacion(seyconPrincipal) );
			datosSesion.setLocale( this.getLocale( request ));
			
			if ( datosSesion.getNivelAutenticacion() != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO )
			{
				PadDelegate delegate = DelegatePADUtil.getPadDelegate();
				PersonaPAD personaPAD = delegate.obtenerDatosPersonaPADporUsuario( seyconPrincipal.getName() );
				datosSesion.setPersonaPAD(personaPAD);					
				
				datosSesion.setPerfilAcceso((String) request.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY));
				if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso()) ){
					String nifEntidad = (String) request.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY);
					// Obtenemos datos entidad
					datosSesion.setEntidadPAD(delegate.obtenerDatosPersonaPADporNif(nifEntidad));
					// Obtenemos permisos
					datosSesion.setPermisosDelegacion(delegate.obtenerPermisosDelegacion(nifEntidad));
					// Indicamos que la entidad tiene permitida la delegacion, sino no podría accederse de forma delegada
					datosSesion.setPermitirDelegacion("S");
				}else{
					// Miramos si el usuario tiene activada la delegacion
					datosSesion.setPermitirDelegacion(delegate.habilitadaDelegacion(datosSesion.getNifUsuario())?"S":"N");
				}
				
				
			}else{
				// Si entra como anonimo ponemos perfil a ciudadano
				datosSesion.setPerfilAcceso(ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO);
			}
			
			
		}
		catch( Exception exc )
		{
			result = "N";
			descripcion = exc.getMessage();
			logger.error( "Error accediendo a la zona personal", exc );
			throw (exc);
		}
		
		if (datosSesion != null && ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
			descripcion = "Acceso delegado " + datosSesion.getNifUsuario();
			logEventoAccesoZonaPersonal( datosSesion.getNivelAutenticacion(), datosSesion.getCodigoEntidad(), datosSesion.getNifEntidad(), datosSesion.getNombreUsuario(), datosSesion.getLocale().getLanguage(),  result, descripcion );
		}else{
			logEventoAccesoZonaPersonal( datosSesion.getNivelAutenticacion(), datosSesion.getCodigoUsuario(), datosSesion.getNifUsuario(), datosSesion.getNombreUsuario(), datosSesion.getLocale().getLanguage(),  result, descripcion );
		}
		return datosSesion;
	}
	
	
	public void logEventoAccesoZonaPersonal(  
			char nivelAutenticacion, String seyconUser, String idDocumentoIdPersonal, String nombre, String lang, String result, String descripcion ) throws Exception
	{
		try{
			Evento eventoAuditado = new Evento();
			eventoAuditado.setTipo( ConstantesAuditoria.EVENTO_ACCESO_ZONA_PERSONAL );
			eventoAuditado.setNivelAutenticacion( String.valueOf( nivelAutenticacion ) );
			if ( nivelAutenticacion != 'A' )
			{
				eventoAuditado.setUsuarioSeycon( seyconUser );
				eventoAuditado.setNumeroDocumentoIdentificacion( idDocumentoIdPersonal );
				eventoAuditado.setNombre( nombre );
			}
			eventoAuditado.setDescripcion( descripcion );
			eventoAuditado.setIdioma( lang );
			eventoAuditado.setResultado( result );
			DelegateAUDUtil.getAuditaDelegate().logEvento(eventoAuditado, true);			
		}catch(Exception ex){
			logger.error("Excepción auditoria en InitAction Zona Personal: " + ex.getMessage(),ex);
		}
	}
	

}
