package org.ibit.rol.form.admin.action.perfil;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PerfilForm;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.PerfilUsuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Perfil de Usuario.
 *
 * @struts.action
 *  path="/admin/perfil/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".perfil.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".perfil.editar"
 */
public class SeleccionPerfilAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionPerfilAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionPerfil");
        PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el perfil " + idString);

        Long id = new Long(idString);
        PerfilUsuario perfil = perfilDelegate.obtenerPerfil(id);
        // Ficar dins el form.
        PerfilForm pForm = (PerfilForm) obtenerActionForm(mapping, request, "/admin/perfil/editar");
        pForm.setValues(perfil);

        return mapping.findForward("success");
    }
}
