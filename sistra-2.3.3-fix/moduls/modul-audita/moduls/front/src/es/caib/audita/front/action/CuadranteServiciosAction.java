package es.caib.audita.front.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.form.CuadranteServiciosForm;
import es.caib.audita.model.AuditConstants;
import es.caib.audita.model.CuadroMandoTablaCruzada;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="cuadranteServiciosForm"
 *  path="/cuadranteServicios"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".cuadranteServicios"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class CuadranteServiciosAction extends BaseAction
{

	private static Log _log = LogFactory.getLog( CuadranteServiciosAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		_log.debug( "execute" );
		
		CuadranteServiciosForm formulario = ( CuadranteServiciosForm ) form;
		String ls_desde = formulario.getDesde();
		String ls_opcion = formulario.getOpcion();
		String ls_evento = formulario.getEvento();
		String ls_detalle = formulario.getDetalle();
		
		if(ls_detalle == null) // Inicializamos detalle
		{
			ls_detalle = "0";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = dateFormat.parse(ls_desde);
		
		request.setAttribute("anyoInicio", ls_desde.substring(6));
		request.setAttribute("mesInicio", ls_desde.substring(3,5));
		
		request.setAttribute("desde",ls_desde);
		request.setAttribute("opcion",ls_opcion);
		request.setAttribute("evento",ls_evento);
		request.setAttribute("detalle",ls_detalle);
		
		if(ls_opcion.equals("anual")) ls_opcion = AuditConstants.ANUAL;
		if(ls_opcion.equals("mensual")) ls_opcion = AuditConstants.MENSUAL;
		if(ls_opcion.equals("diario")) ls_opcion = AuditConstants.DIARIO;
		
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		CuadroMandoTablaCruzada cuadroMando = delegate.obtenerCuadroMandoTablaCruzada(getLang(request),ls_evento,ls_opcion,fecha);
		request.setAttribute( "cuadroMando", cuadroMando);
		
		List eventos = delegate.obtenerListaEventosDetalle(getLang(request));
		request.setAttribute( "eventos", eventos);

		return mapping.findForward( "success" );
	}

}
