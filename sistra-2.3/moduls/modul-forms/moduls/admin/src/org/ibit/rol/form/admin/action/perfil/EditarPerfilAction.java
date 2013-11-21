package org.ibit.rol.form.admin.action.perfil;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PerfilForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;
import org.ibit.rol.form.model.PerfilUsuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar un Perfil de Usuario.
 *
 * @struts.action
 *  name="perfilForm"
 *  scope="session"
 *  validate="false"
 *  path="/admin/perfil/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".perfil.editar"
 *
 * @struts.action-forward
 *  name="success" path=".perfil.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".perfil.lista"
 */
public class EditarPerfilAction extends BaseAction{
    protected static Log log = LogFactory.getLog(EditarPerfilAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarPerfil");

        PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();
        PerfilForm perfilForm = (PerfilForm) form;
        PerfilUsuario perfil = (PerfilUsuario) perfilForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            perfilForm.destroy(mapping, request);
            return mapping.findForward("cancel");
        }

        // Elimina traducciones que no son validas
        perfilForm.validaTraduccion(mapping, request);

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");
            perfilDelegate.gravarPerfil(perfil);
            log.debug("Creat/Actualitzat " + perfil.getId());
            perfilForm.destroy(mapping, request);
            return mapping.findForward("success");
        }

        // Cambio de idioma
        perfilForm.reloadLang();
        return mapping.findForward("reload");
    }

}
