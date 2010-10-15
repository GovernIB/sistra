package org.ibit.rol.form.back.action.pantalla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.model.Pantalla;

/**
 * Action para consultar una pantalla detalle.
 *
 * @struts.action
 *  path="/back/pantalla/seleccionDetalle"
 *
 *
 * @struts.action-forward
 *  name="success" path=".pantalla.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".pantalla.lista"
 *
 */
public class SeleccionDetalleAction extends BaseAction{
    protected static Log log = LogFactory.getLog(SeleccionDetalleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en seleccionDetalle");

        String idString = request.getParameter("componente");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre componente és null!!");
            return mapping.findForward("fail");
        }
        
        log.debug("Seleccionar la pantalla " + idString);

        Long id = new Long(idString);
        Pantalla pantalla = guardarPantallaDetalle(mapping, request, id);
        
        return mapping.findForward("success");
    }



}
