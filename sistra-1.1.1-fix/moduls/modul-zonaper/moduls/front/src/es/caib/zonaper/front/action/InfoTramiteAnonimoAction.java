package es.caib.zonaper.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.InfoTramiteAnonimoForm;
import es.caib.zonaper.model.EstadoExpediente;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
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
		
		if ( StringUtils.isEmpty( formulario.getIdPersistencia() ) )
		{
			request.setAttribute(Constants.MENSAJE_TEXTO, "errors.tramiteNoExiste" );
			return mapping.findForward( "fail" );
		}
		
		formulario.setIdPersistencia( formulario.getIdPersistencia().trim() );
			
		// Almacenamos en sesion el id persistencia con que se entra
		this.setIdPersistencia(request,formulario.getIdPersistencia());
		
		// 1º Comprobar si existe el tramite persistente
		PadDelegate zonaPersonalDelegate = DelegatePADUtil.getPadDelegate();
		String existe = zonaPersonalDelegate.obtenerEstadoTramiteAnonimo(formulario.getIdPersistencia());
		
		// Si no existe el trámite o pertenece a otro usuario
		if ( "N".equals( existe ) )
		{
			request.setAttribute(Constants.MENSAJE_MENU_NAVEGACION, "menuAnonimo.mensaje" );
			request.setAttribute(Constants.MENSAJE_TEXTO, "errors.tramiteNoExiste" );
			return mapping.findForward( "fail" );
		}
				
		// Tramite en persistencia, todavia no enviado
		if ( "P".equals( existe ) )
		{		
			TramitePersistentePAD tramitePersistente = null;
			
			try
			{
				tramitePersistente = zonaPersonalDelegate.obtenerTramitePersistente( formulario.getIdPersistencia() );
			}
			catch( DelegateException exc )
			{
			}
			if ( tramitePersistente != null && tramitePersistente.getNivelAutenticacion() == Constants.NIVEL_AUTENTICACION_ANONIMO )
			{
				request.setAttribute( "tramitePersistente", tramitePersistente );
				return mapping.findForward( "tramiteAnonimoSinEnviar" );
			}
		}
		
		// Tramite enviado (entrada telematica o preregistro)
		EstadoExpediente exp = DelegateUtil.getEstadoExpedienteDelegate().obtenerExpedienteAnonimo(formulario.getIdPersistencia());
		request.getSession().setAttribute(Constants.ULTIMO_DETALLE_EXPEDIENTE,exp.getTipo() + exp.getCodigo());
		return mapping.findForward("detalleExpediente");
	}
}
