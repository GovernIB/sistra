package org.ibit.rol.form.front.action.telematic;

import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  name="finalizarForm"
 *  path="/finalitzarTelematic"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/finalitzarTelematic"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/secure/finalitzarTelematic"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/secure/finalitzarTelematic"
 *  scope="request"
 *  validate="false"
 *
 */
public class FinalitzarTelematicAction extends BaseAction {

    protected static Log log = LogFactory.getLog(FinalitzarTelematicAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        if (!(delegate instanceof InstanciaTelematicaDelegate)) {
                ActionErrors errors = new ActionErrors();
                errors.add(null, new ActionError("errors.telematico"));
                saveErrors(request, errors);
                return mapping.findForward("fail");
        }

        InstanciaTelematicaDelegate tDelegate = (InstanciaTelematicaDelegate) delegate;
        boolean debugEnabled = tDelegate.isDebugEnabled();

        if (isCancelled(request)) {

            if (request.getParameter("SAVE") != null) {

                log.warn("El formulari telemàtic no soporta guardar");
                return null;

            } else if (request.getParameter("DISCARD") != null) {

            	if (debugEnabled) {
            		log.debug("Cancel·lant formulari");
            	}

                try {
                    String redirectUrl = tDelegate.cancelarFormulario();
                    response.reset();
                    response.sendRedirect(redirectUrl);
                    response.flushBuffer();
                    return null;
                } finally {
                    RegistroManager.desregistrarInstancia(request);
                }

            } else { // Es tornar enrera.

                String pantallaAnterior = request.getParameter("PANTALLA_ANTERIOR");
                if (pantallaAnterior == null || pantallaAnterior.length() == 0) {
                    tDelegate.retrocederPantalla();
                } else {
                    tDelegate.retrocederPantalla(pantallaAnterior);
                }

                response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/ver.do"));
                response.flushBuffer();
                return null;
            }
        }

        try {
            String redirectUrl = tDelegate.tramitarFormulario(false);
            response.reset();
            response.sendRedirect(redirectUrl);
            response.flushBuffer();
            return null;
        } finally {
            RegistroManager.desregistrarInstancia(request);
        }
    }

}
