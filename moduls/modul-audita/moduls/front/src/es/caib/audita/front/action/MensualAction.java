package es.caib.audita.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.form.MensualForm;
import es.caib.audita.front.util.Util;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="mensualForm"
 *  path="/mensual"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".mensual"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class MensualAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( MensualAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{


		MensualForm formulario = ( MensualForm ) form;

		int li_anyoFinal = formulario.getAnyoFinal();
		int li_mesFinal = formulario.getMesFinal();
		if((formulario.getAnyoFinal() == 0) && (formulario.getMesFinal() == 0))
		{
			if(formulario.getMesInicio() == 12)
			{
				li_anyoFinal = formulario.getAnyoInicio() + 1;
				li_mesFinal = 1;
			}
			else
			{
				li_anyoFinal = formulario.getAnyoInicio();
				li_mesFinal = formulario.getMesInicio() + 1;
			}
		}

		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		request.setAttribute( "cuadroMando",
				delegate.obtenerCuadroMandoIntervaloTemporal( getLang(request),
						                                      formulario.getAnyoInicio(),
						                                      formulario.getMesInicio(),
						                                      li_anyoFinal,
						                                      li_mesFinal) );

		String anyoInicio = String.valueOf(formulario.getAnyoInicio());
		String mesInicio = Util.getNumber(formulario.getMesInicio(),2);
		String anyoFinal = String.valueOf(formulario.getAnyoFinal());
		String mesFinal = Util.getNumber(formulario.getMesFinal(),2);
		request.setAttribute("anyoInicio", anyoInicio);
		request.setAttribute("mesInicio", mesInicio);
		request.setAttribute("anyoFinal", anyoFinal);
		request.setAttribute("mesFinal", mesFinal);

		String hasta = (formulario.getAnyoFinal() == 0) ? "" : ("01/" + mesFinal + "/" + anyoFinal);

		request.setAttribute("desde", "01/" + mesInicio + "/" + anyoInicio);
		request.setAttribute("hasta", hasta);

		return mapping.findForward( "success" );
	}

}
