package org.ibit.rol.form.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action
 *  path="/limpiarLogScript"
 *
 */
public class LimpiarLogScript extends BaseAction {

    protected static Log log = LogFactory.getLog(LimpiarLogScript.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        delegate.limpiarLogScripts();
        response.sendRedirect("logScript.do?ID_INSTANCIA="+request.getParameter("ID_INSTANCIA"));
        return null;
    }
}
