package es.caib.sistra.back.action.dominio;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.DominioForm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Dominio.
 *
 * @struts.action
 *  path="/back/dominio/alta"
 *
 * @struts.action-forward
 *  name="success" path=".dominio.editar"
 */
public class AltaDominioAction extends BaseAction {
	
	 protected static Log log = LogFactory.getLog( AltaDominioAction.class );
	
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
    	 
         String idOrganoString = request.getParameter("idOrgano");
         if (idOrganoString == null || idOrganoString.length() == 0) {
             log.info("idOrgano es null");
             //return mapping.findForward("fail");
         } 

        DominioForm fForm = (DominioForm) obtenerActionForm(mapping,request, "/back/dominio/editar");
        fForm.destroy( mapping, request );

        if ( !StringUtils.isEmpty( idOrganoString ) )
        {        	
	        Long idOrgano = new Long(idOrganoString);
	        fForm.setIdOrgano( idOrgano );
        }
        return mapping.findForward("success");
        
    }

}
