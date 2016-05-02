package es.caib.audita.front.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;
import es.indra.util.graficos.generadorGraficos.ConfiguracionGrafico;
import es.indra.util.graficos.generadorGraficos.DatosGrafico;
import es.indra.util.graficos.generadorGraficos.GeneradorGraficos;

/**
 * @struts.action
 *  path="/mostrarGrafico"
 *  scope="request"
 *  validate="false"
 */
public class MostrarGraficoAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( MostrarGraficoAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{



		String ls_desde = (String) request.getParameter("desde");
		String ls_opcion = (String) request.getParameter("opcion");
		String ls_evento = (String) request.getParameter("evento");


		ConfiguracionGrafico conf = new ConfiguracionGrafico();
		conf.setAlto(350);
		conf.setAncho(750);
		conf.setLeyenda(true);
		conf.setColorSeries("ROJO-AZUL-VERDE-GRIS-NARANJA-ROSA-AMARILLO-CYAN");

//		GeneradorGraficos.generarImagen(datos,conf,response.getOutputStream());

		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		// Realizar llamada a obtener grafico

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = sdf.parse(ls_desde);

		DatosGrafico datosGrafico = delegate.obtenerDatosGrafico(ls_evento, fecha, ls_opcion );

		// Generar imagen

		GeneradorGraficos.generarImagen(datosGrafico,conf,response.getOutputStream());

		return null;
	}
}
