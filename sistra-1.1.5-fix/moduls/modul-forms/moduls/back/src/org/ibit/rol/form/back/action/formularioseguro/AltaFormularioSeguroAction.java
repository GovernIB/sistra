package org.ibit.rol.form.back.action.formularioseguro;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.FormularioForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de un Formulario Seguro.
 *
 * @struts.action
 *  path="/back/formularioseguro/alta"
 *
 * @struts.action-forward
 *  name="success" path=".formularioseguro.editar"
 */
public class AltaFormularioSeguroAction extends BaseAction {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

           FormularioForm fForm = (FormularioForm) obtenerActionForm(mapping,request, "/back/formularioseguro/editar");
           fForm.destroy(mapping, request);

           return mapping.findForward("success");
       }


}
