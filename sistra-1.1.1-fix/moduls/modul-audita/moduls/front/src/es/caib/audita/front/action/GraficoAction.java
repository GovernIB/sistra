package es.caib.audita.front.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.front.form.GraficoForm;
import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="graficoForm"
 *  path="/grafico"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="success" path=".grafico"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class GraficoAction extends BaseAction
{
	private static Log _log = LogFactory.getLog( GraficoAction.class );
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		_log.debug( "execute" );
		GraficoForm formulario = ( GraficoForm ) form;
		String ls_desde = formulario.getDesde();
		String ls_opcion = formulario.getOpcion();
		String ls_evento = formulario.getEvento();
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//Date fechaInicio = dateFormat.parse(ls_desde);
		
		request.setAttribute("anyoInicio", ls_desde.substring(6));
		request.setAttribute("mesInicio", ls_desde.substring(3,5));
		request.setAttribute("diaInicio", ls_desde.substring(0,1));
		
		request.setAttribute("desde",ls_desde);
		request.setAttribute("opcion",ls_opcion);
		request.setAttribute("evento",ls_evento);
		
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		List eventos = delegate.obtenerListaEventosGrafico(getLang(request));
		request.setAttribute( "eventos", eventos);


		return mapping.findForward( "success" );
	}
}
