package es.caib.bantel.back.action.tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;

/**
 * @struts.action
 *  path="/back/tramite/mostrarFichero"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action-forward
 *  name="success" path="/downloadFichero.do"
 *  
 * @struts.action-forward
 *  name="fail" path="/init.do"
 */
public class MostrarFicheroAction extends Action  {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
			
		// TODO PARA REHACER
		throw new RuntimeException("PENDIENTE");
		/*
		String id = request.getParameter("codigo");
		
		TramiteDelegate pd = DelegateUtil.getTramiteDelegate();
		Procedimiento tramite = pd.obtenerTramite(id);
			
		request.setAttribute("nombreFichero", tramite.getNombreFicheroExportacion() );		
		request.setAttribute(  "datosFichero", tramite.getArchivoFicheroExportacion().getDatos() );
		
		return mapping.findForward("success");
		*/
	}
	
}

