package org.ibit.rol.form.back.action.pantalla;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.PantallaForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Pantalla.
 *
 * @struts.action
 *  path="/back/pantalla/alta"
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 *
 */
public class AltaPantallaAction extends BaseAction{
    protected static Log log = LogFactory.getLog(AltaPantallaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idFormularioString = request.getParameter("idFormulario");
        if (idFormularioString == null || idFormularioString.length() == 0) {
            log.debug("idFormulario es null");
            return mapping.findForward("fail");
        }

        Long idFormulario = new Long(idFormularioString);

        PantallaForm pantallaForm = (PantallaForm) obtenerActionForm(mapping,request, "/back/pantalla/editar");
        pantallaForm.destroy(mapping, request);
        pantallaForm.setIdFormulario(idFormulario);

        return mapping.findForward("success");
    }
}
