package es.caib.bantel.back.action.ficheroExportacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;

/**
 * @struts.action
 *  path="/back/ficheroExportacion/mostrarFichero"
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
			
		
		String id = request.getParameter("codigo");
		
		FicheroExportacionDelegate pd = DelegateUtil.getFicheroExportacionDelegate();
		FicheroExportacion ficheroExportacion = pd.obtenerFicheroExportacion(id);
			
		request.setAttribute("nombreFichero", ficheroExportacion.getNombre() );		
		request.setAttribute(  "datosFichero", ficheroExportacion.getArchivoFicheroExportacion().getDatos() );
		
		return mapping.findForward("success");
		
	}
	
}

