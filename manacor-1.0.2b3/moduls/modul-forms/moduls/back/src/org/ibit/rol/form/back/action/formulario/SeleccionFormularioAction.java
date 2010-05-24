package org.ibit.rol.form.back.action.formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.util.FormularioConfig;
import org.ibit.rol.form.model.Formulario;

/**
 * Action para consultar un Formulario.
 *
 * @struts.action
 *  path="/back/formulario/seleccion"
 *
 * @struts.action-forward
 *  name="formulario" path=".formulario.editar"
 * 
 * @struts.action-forward
 *  name="formularioseguro" path=".formularioseguro.editar"
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
        Formulario formulario = guardarFormulario(mapping, request, id);

        request.setAttribute("botonVersion", "true");

        String tipo = FormularioConfig.getTipo(formulario);

        log.debug("mapping findForward " + tipo);
        return mapping.findForward(tipo);
    }
}
