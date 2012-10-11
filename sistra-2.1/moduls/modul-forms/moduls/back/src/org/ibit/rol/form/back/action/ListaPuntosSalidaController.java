package org.ibit.rol.form.back.action;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.PuntoSalidaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Lista todos los puntos salida
 */
public class ListaPuntosSalidaController implements Controller {
     public void perform(ComponentContext tileContext, HttpServletRequest request,
                         HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

        try {
            String idFormulario = request.getParameter("idFormulario");
            PuntoSalidaDelegate puntoSalidaDelegate = DelegateUtil.getPuntoSalidaDelegate();
            request.setAttribute("puntosalidaOptions", puntoSalidaDelegate.listarPuntosSalida());
            request.setAttribute("idFormulario", idFormulario);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }
}
