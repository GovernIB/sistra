package es.caib.redose.back.action.ubicacion;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.UbicacionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista ubicacions en el contexto.
 */
public class ListaUbicacionesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaUbicacionesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaUbicacionesController");

            UbicacionDelegate ubicacionDelegate = DelegateUtil.getUbicacionDelegate();
            request.setAttribute("ubicacionOptions", ubicacionDelegate.listarUbicaciones());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
