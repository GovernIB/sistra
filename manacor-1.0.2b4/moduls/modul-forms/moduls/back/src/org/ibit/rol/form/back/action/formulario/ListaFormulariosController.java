package org.ibit.rol.form.back.action.formulario;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.FormularioDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista formularios en el contexto.
 */
public class ListaFormulariosController implements Controller{
    protected static Log log = LogFactory.getLog(ListaFormulariosController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaFormulariosController");

            FormularioDelegate formularioDelegate = DelegateUtil.getFormularioDelegate();
            request.setAttribute("formularioOptions", formularioDelegate.listarUltimasVersionesFormularios());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
