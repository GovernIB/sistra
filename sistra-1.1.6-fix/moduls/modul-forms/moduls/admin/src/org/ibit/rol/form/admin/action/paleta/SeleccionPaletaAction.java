package org.ibit.rol.form.admin.action.paleta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.admin.action.BaseAction;

/**
 * Action para consultar una Paleta.
 *
 * @struts.action
 *  path="/admin/paleta/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".paleta.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".paleta.lista"
 */
public class SeleccionPaletaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionPaletaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionPaleta");

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar la paleta " + idString);

        Long id = new Long(idString);
        guardarPaleta(mapping, request, id);

        return mapping.findForward("success");
    }

}
