package org.ibit.rol.form.admin.action.validador;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar borrar un ValidadorFirma
 *
 * @struts.action
 *  path="/admin/validador/baja"
 *
 * @struts.action-forward
 *  name="success" path=".validadorfirma.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".validadorfirma.lista"
 */
public class BajaValidadorAction extends BaseAction {
     protected static Log log = LogFactory.getLog(BajaValidadorAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaValidador");
        ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Borrando el validador firma " + idString);
        Long id = new Long(idString);
        validadorDelegate.borrarValidadorFirma(id);
        return mapping.findForward("success");
    }
}
