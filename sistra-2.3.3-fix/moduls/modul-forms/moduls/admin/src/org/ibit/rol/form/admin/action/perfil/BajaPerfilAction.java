package org.ibit.rol.form.admin.action.perfil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;

/**
 * Action para preparar borrar un Perfil de Usuario.
 *
 * @struts.action
 *  path="/admin/perfil/baja"
 *
 * @struts.action-forward
 *  name="success" path=".perfil.lista"
 *
 * @struts.action-forward
 *  name="fail" path=".perfil.lista"
 */
public class BajaPerfilAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaPerfilAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en BajaPerfil");
        PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Borrant el perfil " + idString);
        Long id = new Long(idString);
        perfilDelegate.borrarPerfil(id);
        return mapping.findForward("success");
    }

}
