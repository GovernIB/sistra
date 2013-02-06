package org.ibit.rol.form.admin.action.paleta.componente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.EleccionComponenteForm;
import org.ibit.rol.form.admin.form.ComponenteForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * @struts.action
 *  name="eleccionComponenteForm"
 *  scope="request"
 *  validate="true"
 *  input=".componente.eleccion"
 *  path="/admin/componente/eleccion"
 *
 * @struts.action-forward
 *  name="textbox" path=".textbox.editar"
 * @struts.action-forward
 *  name="label" path=".label.editar"
 * @struts.action-forward
 *  name="checkbox" path=".checkbox.editar"
 * @struts.action-forward
 *  name="filebox" path=".filebox.editar"
 * @struts.action-forward
 *  name="combobox" path=".combobox.editar"
 * @struts.action-forward
 *  name="listbox" path=".listbox.editar"
 * @struts.action-forward
 *  name="radiobutton" path=".radiobutton.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".paleta.editar"
 */
public class EleccionComponenteAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {


        EleccionComponenteForm ecForm = (EleccionComponenteForm) form;

        if (isCancelled(request)) {
            Long idPaleta = ecForm.getIdPaleta();
            guardarPaleta(mapping, request, idPaleta);
            return mapping.findForward("cancel");
        }

        ComponenteForm cForm = (ComponenteForm) obtenerActionForm(mapping, request, "/admin/" + ecForm.getTipo() + "/editar") ;
        cForm.destroy(mapping, request);
        cForm.setIdPaleta(ecForm.getIdPaleta());

        return mapping.findForward(ecForm.getTipo());
    }
}
