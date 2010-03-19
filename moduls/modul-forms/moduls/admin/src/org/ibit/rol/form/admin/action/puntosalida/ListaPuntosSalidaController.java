package org.ibit.rol.form.admin.action.puntosalida;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.PuntoSalidaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Selecciona la lista de todos los puntos de salida en "puntosalidaOptions".
 */
public class ListaPuntosSalidaController implements Controller {
     protected static Log log = LogFactory.getLog(ListaPuntosSalidaController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        try {
            log.debug("Entramos en ListaSalidasFormularioController");
            PuntoSalidaDelegate puntoSalidaDelegate = DelegateUtil.getPuntoSalidaDelegate();
            request.setAttribute("puntosalidaOptions", puntoSalidaDelegate.listarPuntosSalida());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }
}
