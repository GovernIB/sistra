package org.ibit.rol.form.admin.action.paleta.componente;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.util.ComponenteConfig;
import org.ibit.rol.form.model.Componente;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @struts.action
 *  path="/admin/componente/seleccion"
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
 *  name="fail" path=".paleta.lista"
 *
 */
public class SeleccionComponenteAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionComponenteAction.class);

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {


        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el componente " + idString);

        Long id = new Long(idString);
        Componente componente = guardarComponente(mapping, request, id);
        String tipo = ComponenteConfig.getTipo(componente);

        log.debug("mapping findForward " + tipo);
        return mapping.findForward(tipo);
    }


}
