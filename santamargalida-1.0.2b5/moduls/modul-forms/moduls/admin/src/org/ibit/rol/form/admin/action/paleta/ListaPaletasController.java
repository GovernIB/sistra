package org.ibit.rol.form.admin.action.paleta;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PaletaDelegate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Selecciona la llista de todas las paletas en "paletaOptions".
 */
public class ListaPaletasController implements Controller {

    protected static Log log = LogFactory.getLog(ListaPaletasController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaPaletasController");

            PaletaDelegate paletaDelegate = DelegateUtil.getPaletaDelegate();
            request.setAttribute("paletaOptions", paletaDelegate.listarPaletas());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }

}
