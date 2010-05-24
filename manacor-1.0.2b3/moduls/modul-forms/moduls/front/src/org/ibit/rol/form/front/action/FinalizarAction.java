package org.ibit.rol.form.front.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  name="finalizarForm"
 *  path="/finalizar"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/finalizar"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/secure/finalizar"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/secure/finalizar"
 *  scope="request"
 *  validate="false"
 */
public class FinalizarAction extends BaseAction {

    protected static Log log = LogFactory.getLog(FinalizarAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        RegistroManager.desregistrarInstancia(request);        

        return mapping.findForward("main");
    }
}
