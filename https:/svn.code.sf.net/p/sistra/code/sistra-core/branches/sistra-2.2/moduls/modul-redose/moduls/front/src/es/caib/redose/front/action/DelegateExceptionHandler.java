package es.caib.redose.front.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * Handler para excepciones del Delegate.
 */
public class DelegateExceptionHandler extends ExceptionHandler {

    protected static Log log = LogFactory.getLog(DelegateExceptionHandler.class);

    public ActionForward execute(Exception e, ExceptionConfig exceptionConfig,
                                 ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        log.info("Excepción en la petición: " + mapping.getPath());
        return super.execute(e, exceptionConfig, mapping, form, request, response);
    }
}
