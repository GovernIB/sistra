package org.ibit.rol.form.front.action;

import java.util.ArrayList;
import java.util.List;

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
 *  path="/logScript"
 *
 * @struts.action-forward name="success" path="/logScript.jsp"
 */
public class LogScript extends BaseAction {

    protected static Log log = LogFactory.getLog(LogScript.class);

    public ActionForward execute(ActionMapping       mapping,
                                 ActionForm          form,
                                 HttpServletRequest  request,
                                 HttpServletResponse response) throws Exception {
        
        
    	InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        List log = delegate.obtenerLogScripts();
        if (log==null) log=new ArrayList();
        request.setAttribute("logScript",log);
        return mapping.findForward("success");
    }
}
