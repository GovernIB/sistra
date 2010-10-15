package es.caib.sistra.back.action.dominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.PingDominioForm;

/**
 * Action para realizar un ping sobre un dominio
 *
 * @struts.action
 *  path="/back/dominio/ping"
 *  
 * @struts.action-forward
 *  name="success" path=".dominio.ping"
 */
public class PingDominioAction extends BaseAction {
	
	 protected static Log log = LogFactory.getLog( PingDominioAction.class );
	
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	 
    	PingDominioForm fForm = (PingDominioForm) obtenerActionForm(mapping,request, "/back/dominio/realizarPing"); 	 
    	fForm.setDominio(request.getParameter("dominio"));
    	fForm.setParametros("");
    	
    	return mapping.findForward("success");
        
    }

}
