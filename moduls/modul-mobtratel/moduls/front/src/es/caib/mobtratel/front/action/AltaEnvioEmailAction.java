package es.caib.mobtratel.front.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.mobtratel.front.action.BaseAction;
import es.caib.mobtratel.front.form.EditarEnvioEmailForm;

/**
 * Action para preparar el alta de una Cuenta.
 *
 * @struts.action
 *  path="/altaEnvioEmail"
 *
 * @struts.action-forward
 *  name="success" path=".editarEmail"
 */
public class AltaEnvioEmailAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        EditarEnvioEmailForm fForm = (EditarEnvioEmailForm) obtenerActionForm(mapping,request, "/editarEnvioEmail");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
