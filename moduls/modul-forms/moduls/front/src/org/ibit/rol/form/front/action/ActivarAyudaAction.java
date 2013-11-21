package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.Constants;

/**
 * Almacena estado ayuda en sesion.
 * 
 * @struts.action
 *  path="/activarAyuda"
 */
public class ActivarAyudaAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ActivarAyudaAction.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {
        
    	String activar = request.getParameter("activar");
    	String valor = Boolean.toString("true".equals(activar));
		request.getSession().setAttribute(Constants.AYUDA_ACTIVADA_KEY, valor);
		response.reset();
    	response.getWriter().write(valor);
    	return null;
    }
}
