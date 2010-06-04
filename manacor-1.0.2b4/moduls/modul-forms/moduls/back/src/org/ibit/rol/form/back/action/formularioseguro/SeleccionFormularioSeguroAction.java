package org.ibit.rol.form.back.action.formularioseguro;

import org.ibit.rol.form.back.action.BaseAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Formulario Seguro.
 *
 * @struts.action
 *  path="/back/formularioseguro/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".formularioseguro.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 */
public class SeleccionFormularioSeguroAction extends BaseAction {
    protected static Log log = LogFactory.getLog(SeleccionFormularioSeguroAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionFormularioSeguro");

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el formulario " + idString);

        Long id = new Long(idString);
        guardarFormularioSeguro(mapping, request, id);

        return mapping.findForward("success");
    }
}
