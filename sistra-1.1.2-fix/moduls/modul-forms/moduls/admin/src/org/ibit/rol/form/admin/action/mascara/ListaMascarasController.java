package org.ibit.rol.form.admin.action.mascara;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.MascaraDelegate;

/**
 * Selecciona la llista de todas las mascaras en "mascaraOptions".
 */
public class ListaMascarasController implements Controller {
    protected static Log log = LogFactory.getLog(ListaMascarasController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaMascarasController");

            MascaraDelegate mascaraDelegate = DelegateUtil.getMascaraDelegate();
            request.setAttribute("mascaraOptions", mascaraDelegate.listarMascaras());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }

}
