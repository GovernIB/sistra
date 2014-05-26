package org.ibit.rol.form.admin.action.perfil;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PerfilForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Perfil de Usuario.
 *
 * @struts.action
 *  path="/admin/perfil/alta"
 *
 * @struts.action-forward
 *  name="success" path=".perfil.editar"
 */
public class AltaPerfilAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        PerfilForm pForm = (PerfilForm) obtenerActionForm(mapping,request, "/admin/perfil/editar");
        pForm.destroy(mapping, request);

        return mapping.findForward("success");
    }
}
