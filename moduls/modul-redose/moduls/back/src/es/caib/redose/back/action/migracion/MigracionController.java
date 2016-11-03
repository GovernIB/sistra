package es.caib.redose.back.action.migracion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;

/**
 * Migracion controller.
 */
public class MigracionController implements Controller{
    
    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
         try {
        	 List ubicaciones = DelegateUtil.getUbicacionDelegate().listarUbicaciones();
        	 request.setAttribute( "ubicaciones", ubicaciones );
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}

