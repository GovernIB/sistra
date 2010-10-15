package org.ibit.rol.form.admin.action.mascara;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.MascaraForm;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.Mascara;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar una Mascara.
 *
 * @struts.action
 *  name="mascaraForm"
 *  scope="request"
 *  validate="false"
 *  path="/admin/mascara/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".mascara.editar"
 *
 * @struts.action-forward
 *  name="success" path=".mascara.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".mascara.lista"
 */
public class EditarMascaraAction extends BaseAction{
    protected static Log log = LogFactory.getLog(EditarMascaraAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EdicionMascara");
        MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();
        MascaraForm mascaraForm = (MascaraForm) form;
        Mascara mascara = mascaraForm.getValues();

        if (request.getParameter("addValor") != null){
            int actual = mascaraForm.getVariables().length;
            mascaraForm.numVariables(actual + 1);
            return mapping.findForward("reload");
        }

        if (request.getParameter("removeValor") != null){
            int actual = mascaraForm.getVariables().length;
            mascaraForm.numVariables(actual - 1);
            return mapping.findForward("reload");
        }

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");
            mascaraDelegate.gravarMascara(mascara);
            log.debug("Creat/Actualitzat " + mascara.getNombre());
            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

}
