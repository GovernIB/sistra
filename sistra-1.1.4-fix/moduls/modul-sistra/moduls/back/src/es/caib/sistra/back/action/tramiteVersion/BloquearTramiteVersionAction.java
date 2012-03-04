package es.caib.sistra.back.action.tramiteVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

/**
 * Action para consultar una tramiteVersion.
 *
 * @struts.action
 *  path="/back/tramiteVersion/bloquear"
 *
 *
 * @struts.action-forward
 *  name="success" path="/back/tramiteVersion/seleccion.do"
 *
 * @struts.action-forward
 *  name="fail" path=".tramiteVersion.lista"
 *
 */
public class BloquearTramiteVersionAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BloquearTramiteVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BloquearTramiteVersionAction");
        
        String bloquear = request.getParameter("bloquear");        
        if (StringUtils.isEmpty(bloquear)) {
            log.error("El parametro bloquear es null");
            return mapping.findForward("fail");
        }
        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.error("El parametro codigo es null");
            return mapping.findForward("fail");
        }
    
        Long id = new Long(idString);        
        TramiteVersionDelegate tramiteVersionDelegate = DelegateUtil.getTramiteVersionDelegate();
        tramiteVersionDelegate.bloquearTramiteVersion(id,bloquear,request.getUserPrincipal().getName());
                
        return mapping.findForward("success");
    }



}
