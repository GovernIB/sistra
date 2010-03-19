package org.ibit.rol.form.back.action;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista de puntos salida de un formulario en el contexto.
 */
public class ListaSalidasFormularioController implements Controller {
    protected static Log log = LogFactory.getLog(ListaSalidasFormularioController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaSalidasFormularioController");
            Long id = (Long) request.getAttribute("idFormulario");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
            tileContext.putAttribute("salidaOptions", formularioDelegate.listarSalidasFormulario(id));
            tileContext.putAttribute("idFormulario", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
