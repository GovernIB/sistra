package es.caib.audita.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  path="/mostrarGraficoFichero"
 *  scope="request"
 *  validate="false"
 */
public class MostrarGraficoFicheroAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( MostrarGraficoFicheroAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{

		String ls_fichero = (String) request.getParameter("fichero");
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		byte[] datos = delegate.obtenerGraficoFichero(ls_fichero);
		response.getOutputStream().write(datos);

		return null;
	}
}
