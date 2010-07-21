package org.ibit.rol.form.back.action.propiedadsalida;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;

/**
 * Action para consultar una propiedad salida
 *
 * @struts.action
 *  path="/back/propiedadsalida/seleccion"
 *
 *
 * @struts.action-forward
 *  name="success" path=".propiedadsalida.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".propiedadsalida.lista"
 *
 */
public class SeleccionPropiedadSalidaAction extends BaseAction {
    protected static Log log = LogFactory.getLog(SeleccionPropiedadSalidaAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionPropiedadSalida");

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar la propiedad salida " + idString);

        Long id = new Long(idString);
        guardarPropiedadSalida(mapping, request, id);
        
        return mapping.findForward("success");
    }
}
