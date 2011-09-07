package org.ibit.rol.form.back.action;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.PaletaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Controller para obtener una lista de Paletas
 */
public class ListaPaletasController implements Controller{
    protected static Log log = LogFactory.getLog(ListaPaletasController.class);

        public void perform(ComponentContext tileContext,
                            HttpServletRequest request, HttpServletResponse response,
                            ServletContext servletContext)
                throws ServletException, IOException {
            try {
                HttpSession session = request.getSession();

                Long idPantalla = new Long (request.getParameter("idPantalla"));
                log.debug("Entramos en ListaPaletasController");
                PaletaDelegate paletaDelegate = DelegateUtil.getPaletaDelegate();
                request.setAttribute("paletaOptions", paletaDelegate.listarPaletas());
                request.removeAttribute("id");
                session.setAttribute("idPantalla", idPantalla);

            } catch (DelegateException e) {
                throw new ServletException(e);
            }
        }

}
