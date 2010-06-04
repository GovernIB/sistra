package es.caib.mobtratel.back.action.cuenta;


import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import es.caib.mobtratel.back.action.BaseAction;
import es.caib.mobtratel.back.form.CuentaForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para preparar el alta de una Cuenta.
 *
 * @struts.action
 *  path="/back/cuenta/alta"
 *
 * @struts.action-forward
 *  name="success" path=".cuenta.editar"
 */
public class AltaCuentaAction extends BaseAction {
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        CuentaForm fForm = (CuentaForm) obtenerActionForm(mapping,request, "/back/cuenta/editar");
        fForm.destroy( mapping, request );

        return mapping.findForward("success");
    }

}
