package es.caib.redose.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsProcesosDelegate;

/**
 * @struts.action
 *  path="/mantenimientoRDS"
 *  scope="request"
 *  validate="false"
 */
public class MantenimientoRDSAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		RdsProcesosDelegate delegate = DelegateUtil.getRdsProcesosDelegate();
		delegate.purgadoDocumentos();
		
		response.getOutputStream().write("Proceso finalizado".getBytes());
		return null;
	}    
	

}
