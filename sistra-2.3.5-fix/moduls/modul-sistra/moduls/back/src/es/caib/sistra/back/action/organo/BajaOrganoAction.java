package es.caib.sistra.back.action.organo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;

/**
 * Action para preparar borrar un Organo.
 *
 * @struts.action
 *  name="organoForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/organo/baja"
 *
 * @struts.action-forward
 *  name="success" path=".organo.lista"
 */
public class BajaOrganoAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaOrganoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaOrgano");
        OrganoResponsableDelegate organoDelegate = DelegateUtil.getOrganoResponsableDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        organoDelegate.borrarOrganoResponsable(id);
        //request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
