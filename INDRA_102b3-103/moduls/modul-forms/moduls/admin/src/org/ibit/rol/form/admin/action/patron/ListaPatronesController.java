package org.ibit.rol.form.admin.action.patron;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PatronDelegate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Selecciona la llista de todos los patrones en "patronOptions".
 */
public class ListaPatronesController implements Controller {

    protected static Log log = LogFactory.getLog(ListaPatronesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        try {
            log.debug("Entramos en ListaPatronesController");
            PatronDelegate patronDelegate = DelegateUtil.getPatronDelegate();
            request.setAttribute("patronOptions", patronDelegate.listarPatrones());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }
}
