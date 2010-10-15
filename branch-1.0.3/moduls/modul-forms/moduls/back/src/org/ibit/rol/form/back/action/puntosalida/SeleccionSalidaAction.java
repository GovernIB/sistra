package org.ibit.rol.form.back.action.puntosalida;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

/**
 * Action para consultar un Punto Salida.
 *
 * @struts.action
 *  path="/back/salida/seleccion"
 *
 * @struts.action-forward
 *  name="success" path=".salida.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.editar"
 */
public class SeleccionSalidaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(SeleccionSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en SeleccionSalida");

        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();

        String idString = request.getParameter("id");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre id és null!!");
            return mapping.findForward("fail");
        }

        log.debug("Seleccionar el punto salida " + idString);

        Long id = new Long(idString);
        Salida salida = guardarSalida(mapping, request, id);

        // Esta bloqueado el formulario?
        if (salida.getFormulario().isBloqueado() && salida.getFormulario().getBloqueadoPor().equals(request.getUserPrincipal().getName())) {
            request.setAttribute("bloqueado","true");
            request.setAttribute("bloqueadoPor",salida.getFormulario().getBloqueadoPor());            
        }

        return mapping.findForward("success");
    }
}
