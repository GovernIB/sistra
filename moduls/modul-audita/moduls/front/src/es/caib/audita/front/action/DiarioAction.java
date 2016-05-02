package es.caib.audita.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.form.DiarioForm;
import es.caib.audita.front.util.Util;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="diarioForm"
 *  path="/diario"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".diario"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DiarioAction extends BaseAction
{

	private static final long DIA = 60 * 60 * 24 * 1000 ; // en milisegundos

	private static Log _log = LogFactory.getLog( DiarioAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{


		DiarioForm formulario = ( DiarioForm ) form;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = null;
		Date fechaFinal = null;
		String ls_inicio = Util.getNumber(formulario.getDiaInicio(),2) + "/" +
						   Util.getNumber(formulario.getMesInicio(),2) + "/" +
		                   formulario.getAnyoInicio();
		String ls_final = "";
		boolean existeHasta = false;
		if((formulario.getDiaFinal() != 0) && (formulario.getMesFinal() != 0) &&
		   (formulario.getAnyoFinal() != 0))
		{
			ls_final = Util.getNumber(formulario.getDiaFinal(),2) + "/" +
					   Util.getNumber(formulario.getMesFinal(),2) + "/" +
                       formulario.getAnyoFinal();
			existeHasta = true;
		}
		else
		{
			ls_final = getNextDay(ls_inicio);
		}
		try {
			fechaInicio = dateFormat.parse(ls_inicio);
			if(!ls_final.equals(""))
			{
			   fechaFinal = dateFormat.parse(ls_final);
			}
		} catch (ParseException e) {
			_log.error( "Error al parsear las fechas: " + e.getMessage(), e );
		}


		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		request.setAttribute( "cuadroMando",
				delegate.obtenerCuadroMandoIntervaloTemporal( getLang(request),fechaInicio, fechaFinal ));

		/*
		String desde = Util.getNumber(formulario.getDiaInicio(),2) + "/" +
					   Util.getNumber(formulario.getMesInicio(),2) + "/" +
					   String.valueOf(formulario.getAnyoInicio()).substring(2);
		String hasta = "";
		if((formulario.getDiaFinal() != 0) && (formulario.getMesFinal() != 0) &&
		   (formulario.getAnyoFinal() != 0))
		{
			hasta = Util.getNumber(formulario.getDiaFinal(),2) + "/" +
			        Util.getNumber(formulario.getMesFinal(),2) + "/" +
			        String.valueOf(formulario.getAnyoFinal()).substring(2);
		}
		*/

		request.setAttribute("desde", ls_inicio);
		request.setAttribute("hasta", (existeHasta) ? ls_final : "");

		return mapping.findForward( "success" );
	}

	private String getNextDay(String today)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date fecha = sdf.parse(today);
			long ll_fecha = fecha.getTime();
			ll_fecha += DIA;
			fecha.setTime(ll_fecha);
			return sdf.format(fecha);
		} catch (ParseException e) {
			_log.error("Excepcion " + e.getMessage(), e);
			return null;
		}

	}

}
