package org.ibit.rol.form.front.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.front.registro.RegistroManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  path="/resultados"
 *
 * @struts.action
 *  path="/auth/resultados"
 *
 * @struts.action
 *  path="/secure/resultados"
 *
 * @struts.action
 *  path="/auth/secure/resultados"
 *
 * @struts.action-forward
 *  name="view" path=".resultados"
 *
 * @struts.action-forward
 *  name="end" path=".penultima"
 */
public class ResultadosAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ResultadosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        return mapping.findForward("view");
    }
}
