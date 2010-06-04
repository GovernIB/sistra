package org.ibit.rol.form.admin.action.puntosalida;

import org.ibit.rol.form.admin.action.BaseAction;
import org.ibit.rol.form.admin.form.PuntoSalidaForm;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PuntoSalidaDelegate;
import org.ibit.rol.form.model.PuntoSalida;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para consultar un Punto Salida.
 *
 * @struts.action
 *  path="/admin/puntosalida/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".puntosalida.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".puntosalida.lista"
 */
public class SeleccionPuntoSalidaAction extends BaseAction {
     protected static Log log = LogFactory.getLog(SeleccionPuntoSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionPuntoSalida");

        PuntoSalidaDelegate puntoSalidaDelegate = DelegateUtil.getPuntoSalidaDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el punto salida " + idString);

        Long id = new Long(idString);
        PuntoSalida puntoSalida = puntoSalidaDelegate.obtenerPuntoSalida(id);
        // Ficar dins el form.
        PuntoSalidaForm psForm = (PuntoSalidaForm) obtenerActionForm(mapping, request, "/admin/puntosalida/editar");
        psForm.setValues(puntoSalida);

        return mapping.findForward("success");
    }
}
