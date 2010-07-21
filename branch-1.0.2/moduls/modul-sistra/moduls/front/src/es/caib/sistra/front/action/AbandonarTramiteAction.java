package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.form.AbandonarTramiteForm;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.ParametrosMensaje;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  name="abandonarTramiteForm"
 *  path="/protected/abandonarTramite"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".exit"
 *  
 * @struts.action-forward
 *  name="init" path="/protected/init.do"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class AbandonarTramiteAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AbandonarTramiteForm formulario = ( AbandonarTramiteForm ) form;
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( formulario.getID_INSTANCIA(), request );
		
		// Establecemos info del tramite en la request
		RespuestaFront respuestaFront = delegate.pasoActual();
		this.setRespuestaFront( request, respuestaFront );
		
		// Eliminamos tramite
		if ( formulario.getIdPersistencia() == null )
		{
			delegate.borrarTramite();
			
			// Mensaje de cancelación de trámite
			ParametrosMensaje param = new ParametrosMensaje();
			param.setAction("main");			
			this.setInfoMessage( request, "cancelacionTramite.mensaje",param);		
			InstanciaManager.desregistrarInstancia( request );
			return mapping.findForward( "success" );
		}
		else
		{
			delegate.borrarTramite( formulario.getIdPersistencia() );
			
			request.setAttribute( Constants.NO_REINIT, new Boolean( true ) );
			InstanciaManager.desregistrarInstancia( request );
			formulario.setIdPersistencia( null );
			return mapping.findForward( "init" );
		}		
		
	}

}
