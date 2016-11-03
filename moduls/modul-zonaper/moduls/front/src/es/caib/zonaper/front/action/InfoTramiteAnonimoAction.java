package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.InfoTramiteAnonimoForm;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * @struts.action
 *  name="infoTramiteAnonimoForm"
 *  path="/protected/infoTramiteAnonimo"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="tramiteAnonimoSinEnviar" path=".tramiteAnonimoSinEnviar"
 * 
 * @struts.action-forward
 *  name="detalleExpediente" path="/protected/mostrarDetalleExpediente.do"
 * 
 * @struts.action-forward
 *  name="fail" path=".mensaje"
 */
public class InfoTramiteAnonimoAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		InfoTramiteAnonimoForm formulario = ( InfoTramiteAnonimoForm ) form;
		
		// Obtenemos clave de acceso
		if ( StringUtils.isEmpty( formulario.getIdPersistencia() ) )
		{
			request.setAttribute(Constants.MENSAJE_TEXTO, "errors.tramiteNoExiste" );
			return mapping.findForward( "fail" );
		}
		String idPersistencia = formulario.getIdPersistencia().trim();
			
		// Almacenamos en sesion la clave de acceso con que se entra
		this.setIdPersistencia(request,idPersistencia);
		
		// Comprobamos si la clave hace referencia a algun tramite que todavia esta en persistencia
		PadDelegate padDelegate = DelegatePADUtil.getPadDelegate();
		TramitePersistentePAD tramitePersistente = padDelegate.obtenerTramitePersistente(idPersistencia);
		if (tramitePersistente != null) {
			// Redirigimos a tramite anonimo pendiente enviar
			if (tramitePersistente.getNivelAutenticacion() == Constants.NIVEL_AUTENTICACION_ANONIMO )
			{
				request.setAttribute( "tramitePersistente", tramitePersistente );
				return mapping.findForward( "tramiteAnonimoSinEnviar" );
			}
		} else {
			// Comprobamos si se tiene acceso a algun expediente con esa clase
			ElementoExpediente elementoExpe = DelegateUtil.getElementoExpedienteDelegate().obtenerElementoExpediente(idPersistencia);
			if (elementoExpe != null && elementoExpe.isAccesoAnonimoExpediente()) {
				// Redirigimos a expediente
				request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE, elementoExpe.getExpediente().getCodigo());
				return mapping.findForward("detalleExpediente");
			}				
		}

		// Si no se ha redirigido antes es que no existe
		request.setAttribute(Constants.MENSAJE_MENU_NAVEGACION, "menuAnonimo.mensaje" );
		request.setAttribute(Constants.MENSAJE_TEXTO, "errors.tramiteNoExiste" );
		return mapping.findForward( "fail" );						
	}
}
