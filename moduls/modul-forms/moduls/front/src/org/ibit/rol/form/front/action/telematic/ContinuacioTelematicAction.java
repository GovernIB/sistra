package org.ibit.rol.form.front.action.telematic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action path="/continuacioTelematic"
 * @struts.action path="/auth/continuacioTelematic"
 * @struts.action path="/secure/continuacioTelematic"
 * @struts.action path="/auth/secure/continuacioTelematic"
 */
public class ContinuacioTelematicAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ContinuacioTelematicAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idInstancia = request.getParameter(RegistroManager.ID_INSTANCIA);

        log.info("Recibida continuación telemática: " + idInstancia);

        boolean success = RegistroManager.asignarInstanciaPreregistrada(request, idInstancia);

        if (!success) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.telematico"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        setLocale(request, delegate.obtenerIdioma());
      
        response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/ver.do"));
        response.flushBuffer();
        return null;
    }

}
