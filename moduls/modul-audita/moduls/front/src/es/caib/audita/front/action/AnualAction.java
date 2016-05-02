package es.caib.audita.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.form.AnualForm;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="anualForm"
 *  path="/anual"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".anual"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AnualAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( AnualAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AnualForm formulario = ( AnualForm ) form;


		int li_anyoFinal = formulario.getAnyoFinal();
		if(formulario.getAnyoFinal() == 0)
		{
			li_anyoFinal = formulario.getAnyoInicio() + 1;
		}

		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		request.setAttribute( "cuadroMando",
				              delegate.obtenerCuadroMandoIntervaloTemporal( getLang(request), formulario.getAnyoInicio(), li_anyoFinal) );


		String anyoInicio = String.valueOf(formulario.getAnyoInicio());
		String anyoFinal = String.valueOf(formulario.getAnyoFinal());
		request.setAttribute("anyoInicio", anyoInicio);
		request.setAttribute("anyoFinal", anyoFinal);

		String hasta = (formulario.getAnyoFinal() == 0) ? "" : ("01/01/" + anyoFinal);
		request.setAttribute("desde", "01/01/" + anyoInicio);
		request.setAttribute("hasta", hasta);


		return mapping.findForward( "success" );
	}

}
