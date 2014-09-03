package org.ibit.rol.form.back.action.puntosalida;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para relacionar un formulario a un punto de salida
 *
 * @struts.action
 *  path="/back/salida/baja"
 *
 *
 * @struts.action-forward
 *  name="success" path=".formulario.editar"
 *
 * @struts.action-forward
 *  name="fail" path=".formulario.lista"
 *
 */
public class BajaSalidaAction extends BaseAction {
    protected static Log log = LogFactory.getLog(BajaSalidaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {



        String idSalidaString = request.getParameter("idSalida");
        if (idSalidaString == null || idSalidaString.length() == 0) {
            log.debug("idSalida es null");
            return mapping.findForward("fail");
        }

        Long idSalida = new Long(idSalidaString);

        FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
        formularioDelegate.eliminarSalida(idSalida);

        String idFormularioString = request.getParameter("idFormulario");
        if (idFormularioString == null || idFormularioString.length() == 0) {
            log.warn("El paràmetre idFormulario és null");
            return mapping.findForward("fail");
        }

        Long idFormulario = new Long(idFormularioString);
        Formulario formulario = guardarFormulario(mapping, request, idFormulario);
        
        if (formulario.isBloqueado() && formulario.getBloqueadoPor().equals(request.getUserPrincipal().getName())) {
            request.setAttribute("bloqueado","true");     
            request.setAttribute("bloqueadoPor",formulario.getBloqueadoPor());            
        }
        
        return mapping.findForward("success");
    }
}
