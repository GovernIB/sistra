package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * Action que obtiene la ayuda de pantalla.
 *
 * @struts.action path="/ayuda"
 *
 * @struts.action path="/auth/ayuda"
 *
 * @struts.action path="/secure/ayuda"
 *
 * @struts.action path="/auth/secure/ayuda"
 *
 * @struts.action-forward
 *  name="success" path=".ayuda"
 */
public class AyudaAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        request.setAttribute("ayudaPantalla", delegate.obtenerAyudaPantalla());
        return mapping.findForward("success");
    }
}
