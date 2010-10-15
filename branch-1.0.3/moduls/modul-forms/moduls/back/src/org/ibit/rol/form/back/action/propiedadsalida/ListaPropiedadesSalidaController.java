package org.ibit.rol.form.back.action.propiedadsalida;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.PropiedadSalidaDelegate;

/**
 *
 */
public class ListaPropiedadesSalidaController implements Controller {
    protected static Log log = LogFactory.getLog(ListaPropiedadesSalidaController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        try {
            log.debug("Entramos ListaPropiedadesSalidaController");

            HttpSession sesion = request.getSession(true);
            Long idSalida = (Long) sesion.getAttribute("idSalida");
            if (idSalida == null) {
                String idString = request.getParameter("idSalida");
                if (idString == null || idString.length() == 0) {
                    idString = request.getParameter("id");
                }
                idSalida = new Long(idString);
            }

            PropiedadSalidaDelegate delegate = DelegateUtil.getPropiedadSalidaDelegate();
            Set propiedades = delegate.listarPropiedadesSalida(idSalida);

            request.setAttribute("propiedadSalidaOptions", propiedades);
            request.setAttribute("idSalida", idSalida);

        } catch (Throwable e) {
            log.error(e);
            throw new ServletException(e);
        }
    }
}
