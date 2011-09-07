package org.ibit.rol.form.admin.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

/**
 * Action para consultar un Formulario.
 *
 * @struts.action
 * 	name="formularioForm"
 *  path="/admin/formulario/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 */
public class SeleccionFormularioAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionFormularioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionFormulario");

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el formulario " + idString);

        Long id = new Long(idString);
        FormularioDelegate delegate = DelegateUtil.getFormularioDelegate();
        Formulario formulario = delegate.obtenerFormulario(id);

        request.setAttribute("formulario", formulario);
        
        return mapping.findForward("success");
    }
}
