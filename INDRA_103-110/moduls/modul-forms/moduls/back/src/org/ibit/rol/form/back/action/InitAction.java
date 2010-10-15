package org.ibit.rol.form.back.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * @struts.action
 *  path="/init"
 *
 */
public class InitAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

    	// Establecemos vble en sesion que indique que esta activa
        HttpSession session = request.getSession(true);
        session.setAttribute("org.ibit.rol.form.back.datosSesion","true");

        // Redirigimos a pagina de inicio
        String modelo = request.getParameter("modelo");
        String version = request.getParameter("version");
        String url = request.getContextPath() + "/index.jsp" + (modelo!=null?"?modelo="+modelo+"&version="+version:"");
        response.sendRedirect(url);        
        return null;
    }

}
