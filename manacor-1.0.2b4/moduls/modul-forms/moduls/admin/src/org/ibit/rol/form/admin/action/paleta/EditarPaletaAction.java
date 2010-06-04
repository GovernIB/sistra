package org.ibit.rol.form.admin.action.paleta;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PaletaForm;
import org.ibit.rol.form.persistence.delegate.PaletaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.model.Paleta;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para editar una Paleta.
 *
 * @struts.action
 *  name="paletaForm"
 *  scope="request"
 *  validate="true"
 *  input=".paleta.editar"
 *  path="/admin/paleta/editar"
 *
 * @struts.action-forward
 *  name="success" path=".paleta.lista"
 *
 * @struts.action-forward
 *  name="cancel" path=".paleta.lista"
 */
public class EditarPaletaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarPaletaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en EditarPaleta");
        PaletaDelegate paletaDelegate = DelegateUtil.getPaletaDelegate();
        PaletaForm paletaForm = (PaletaForm) form;
        Paleta paleta = paletaForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (isAlta(request) || isModificacio(request)) {
            log.debug("isAlta || isModificacio");
            paletaDelegate.gravarPaleta(paleta);
            log.debug("Creat/Actualitzat " + paleta.getNombre());
            return mapping.findForward("success");
        }

        return mapping.findForward("success");
    }

}
