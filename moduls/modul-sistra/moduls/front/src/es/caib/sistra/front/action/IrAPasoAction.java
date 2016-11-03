package es.caib.sistra.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;


/**
 * @struts.action
 *  path="/protected/irAPaso"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mainLayout"
 *
 * @struts.action-forward
 *  name="fail" path="/fail.do"
 */
public class IrAPasoAction extends BaseAction
{
	public ActionForward executeTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		int iPaso;
		String step = request.getParameter( "step" );
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request );
		
		if (delegate == null){
			this.setErrorMessage( request, "errors.tramite.instanciaNoExiste" );
			return mapping.findForward( "fail" );
		}
		
		RespuestaFront respuestaFront = null;
		if ( StringUtils.isEmpty( step ) )
		{
			respuestaFront = delegate.pasoActual();
		}	
		else
		{
			iPaso = Integer.parseInt( step );
			respuestaFront = delegate.irAPaso( iPaso );
		}
		
		this.setRespuestaFront( request, respuestaFront );
		return mapping.findForward("success");
    }
}
