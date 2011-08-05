package org.ibit.rol.form.admin.action.paleta;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.PaletaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  path="/admin/paleta/baja"
 *
 * @struts.action-forward
 *  name="success" path=".paleta.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".paleta.lista"
 */
public class BajaPaletaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(BajaPaletaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        log.debug("Entramos en BajaPaletaAction");

        PaletaDelegate delegate = DelegateUtil.getPaletaDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null");
            return mapping.findForward("fail");
        }

        log.debug("Borrando la paleta " + idString);
        Long id = new Long(idString);
        delegate.borrarPaleta(id);

        return mapping.findForward("success");
    }
}
