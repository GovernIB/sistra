package es.caib.audita.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.model.CuadroMandoInicio;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/refresh"
 *  scope="request"
 *  validate="false"
 *  
 *  @struts.action-forward
 *  name="success" path=".inicio"
 *
 * @struts.action-forward
 *  name="fail" path=".error"

 */
public class RefreshAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		//_log.debug( "execute" );
		
		
		
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		delegate.generaCuadroMandoInicio();
		CuadroMandoInicio cuadroMandoDetalle = delegate.obtenerCuadroMandoInicio(getLang(request));
		request.setAttribute( "cuadro", cuadroMandoDetalle);
		

		return mapping.findForward( "success" );
    }

}
