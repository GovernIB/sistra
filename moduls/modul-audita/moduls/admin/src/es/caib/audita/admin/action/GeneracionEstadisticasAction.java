package es.caib.audita.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  path="/generacionEstadisticas"
 *  scope="request"
 *  validate="false"
 */
public class GeneracionEstadisticasAction extends Action
{


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		try {
			delegate.generaCuadroMandoInicio();
		} catch (DelegateException e) {
		  throw new Exception(e.getMessage(), e);
		}

		response.getOutputStream().write("Proceso finalizado".getBytes());
		return null;
	}


}
