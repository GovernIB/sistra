package es.caib.mobtratel.front.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.form.EditarEnvioSmsForm;

/**
 * Action para preparar el alta de una Cuenta.
 *
 * @struts.action
 *  path="/altaEnvioSMS"
 *
 * @struts.action-forward
 *  name="success" path=".editarSMS"
 */
public class AltaEnvioSmsAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        EditarEnvioSmsForm fForm = (EditarEnvioSmsForm) obtenerActionForm(mapping,request, "/editarEnvioSMS");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
