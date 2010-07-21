package org.ibit.rol.form.admin.action.perfil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Selecciona la llista de todos los perfiles en "perfilOptions".
 */
public class ListaPerfilesController implements Controller {

    protected static Log log = LogFactory.getLog(ListaPerfilesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        try {
            log.debug("Entramos en ListaPerfilesController");

            PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();
            request.setAttribute("perfilOptions", perfilDelegate.listarPerfiles());
        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }

}
