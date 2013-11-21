package org.ibit.rol.form.admin.action.patron;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar un Patrón.
 *
 * @struts.action
 *  path="/admin/patron/baja"
 *
 * @struts.action-forward
 *  name="success" path=".patron.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".patron.lista"
 */
public class BajaPatronAction extends BaseAction {

    protected static Log log = LogFactory.getLog(BajaPatronAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaPatron");
        PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Borrant el patro " + idString);
        Long id = new Long(idString);
        patronDelegate.borrarPatron(id);
        return mapping.findForward("success");
    }
}
