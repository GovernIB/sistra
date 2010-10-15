package org.ibit.rol.form.back.action.puntosalida;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para relacionar un formulario a un punto de salida que corresponde con el alta de una salida
 *
 * @struts.action
 *  name="salidaForm"
 *  scope="request"
 *  validate="true"
 *  input=".puntosalida.lista"
 *  path="/back/salida/alta"
 *
 *
 * @struts.action-forward
 *  name="success" path=".salida.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 *
 */
public class AltaSalidaAction extends BaseAction {
    protected static Log log = LogFactory.getLog(AltaSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idFormularioString = request.getParameter("idFormulario");
        if (idFormularioString == null || idFormularioString.length() == 0) {
            log.debug("idFormulario es null");
            return mapping.findForward("fail");
        }

        Long idFormulario = new Long(idFormularioString);

        String idPuntoSalidaString = request.getParameter("idPuntoSalida");
        if (idPuntoSalidaString == null || idPuntoSalidaString.length() == 0) {
            log.debug("idPuntoSalida es null");
            return mapping.findForward("fail");
        }

        Long idPuntoSalida = new Long(idPuntoSalidaString);

        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        Long idSalida = formularioDelegate.anyadirSalida(idFormulario, idPuntoSalida);

        guardarSalida(mapping, request, idSalida);

        return mapping.findForward("success");
    }
}
