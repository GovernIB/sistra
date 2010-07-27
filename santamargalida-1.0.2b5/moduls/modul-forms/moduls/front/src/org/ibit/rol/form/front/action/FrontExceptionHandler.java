package org.ibit.rol.form.front.action;

import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.config.ExceptionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.front.registro.RegistroManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

/**
 * Processa excepcions del front office.
 */
public class FrontExceptionHandler extends ExceptionHandler {

    protected static Log log = LogFactory.getLog(FrontExceptionHandler.class);

    public ActionForward execute(Exception ex,
                                 ExceptionConfig ae,
                                 ActionMapping mapping,
                                 ActionForm formInstance,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException {
        log.error(mapping.getPath(), ex);
        if (ex instanceof DelegateException) {
            log.debug("Descartant instància");
            RegistroManager.desregistrarInstancia(request);
        }
        return super.execute(ex, ae, mapping, formInstance, request, response);
    }
}
