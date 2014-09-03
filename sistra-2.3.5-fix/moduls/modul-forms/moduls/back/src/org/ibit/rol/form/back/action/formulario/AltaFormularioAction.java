package org.ibit.rol.form.back.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.FormularioForm;

/**
 * Action para preparar el alta de un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/alta"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 */
public class AltaFormularioAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        FormularioForm fForm = (FormularioForm) obtenerActionForm(mapping,request, "/back/formulario/editar");
        fForm.destroy(mapping, request);
        return mapping.findForward("success");
    }

}
