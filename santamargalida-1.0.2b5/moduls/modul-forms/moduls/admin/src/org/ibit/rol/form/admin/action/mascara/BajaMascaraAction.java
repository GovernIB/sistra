package org.ibit.rol.form.admin.action.mascara;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar una Mascara.
 *
 * @struts.action
 *  path="/admin/mascara/baja"
 *
 * @struts.action-forward
 *  name="success" path=".mascara.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".mascara.lista"
 */
public class BajaMascaraAction extends BaseAction {
    protected static Log log = LogFactory.getLog(BajaMascaraAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaMascara");
        MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Borrant la mascara " + idString);
        Long id = new Long(idString);
        mascaraDelegate.borrarMascara(id);
        return mapping.findForward("success");
    }
}
