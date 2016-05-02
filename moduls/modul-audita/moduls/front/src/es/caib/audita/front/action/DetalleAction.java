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

import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.CuadroMandoDetalle;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="detalleForm"
 *  path="/detalle"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path=".detalle"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class DetalleAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( DetalleAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{


		String ls_hasta = request.getParameter("hasta");
		String ls_desde = request.getParameter("desde");
		String ls_opcion = request.getParameter("opcion");
		String ls_evento = request.getParameter("evento");

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = dateFormat.parse(ls_desde);
		Date fechaFinal = null;
		if((ls_hasta != null) && (!ls_hasta.equals("")))
		{
			fechaFinal = dateFormat.parse(ls_hasta);
		}

		if(ls_opcion.equals("anual")) ls_opcion = AuditConstants.ANUAL;
		if(ls_opcion.equals("mensual")) ls_opcion = AuditConstants.MENSUAL;
		if(ls_opcion.equals("diario")) ls_opcion = AuditConstants.DIARIO;


		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		CuadroMandoDetalle cuadroMandoDetalle = delegate.obtenerCuadroMandoDetalle(getLang(request),ls_evento, ls_opcion, fechaInicio, fechaFinal);
		request.setAttribute( "cuadroMandoDetalle", cuadroMandoDetalle);

		request.setAttribute("desde",ls_desde);
		request.setAttribute("opcion",ls_opcion);
		request.setAttribute("evento",ls_evento);


		return mapping.findForward( "success" );
	}
}
