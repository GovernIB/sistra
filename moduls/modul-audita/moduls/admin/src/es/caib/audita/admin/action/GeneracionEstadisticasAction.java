package es.caib.audita.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.audita.persistence.delegate.AuditoriaDelegate;
import es.caib.audita.persistence.delegate.DelegateException;
import es.caib.audita.persistence.delegate.DelegateUtil;


/**
 * @struts.action
 *  path="/generacionEstadisticas"
 *  scope="request"
 *  validate="false"
 */
public class GeneracionEstadisticasAction extends Action
{
	
	private Log log = LogFactory.getLog(GeneracionEstadisticasAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		log.debug("Generacion estadisticas - inicio");
		AuditoriaDelegate delegate = DelegateUtil.getAuditoriaDelegate();
		try {
			delegate.generaCuadroMandoInicio();
		} catch (DelegateException e) {
			log.error("Excepcion lanzando Job: " + e.getMessage(), e);
		}
		log.debug("Generacion estadisticas - fin");
		
		response.getOutputStream().write("Proceso finalizado".getBytes());
		return null;
	}    
	

}
