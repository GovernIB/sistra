package org.ibit.rol.form.back.action.componente;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Guarda la lista de componentes de una pantalla en el contexto.
 */
public class ListaComponentesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaComponentesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaComponenteController");

            Object attribute = tileContext.getAttribute("id");
            Long id = new Long(attribute.toString());

            ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();
            tileContext.putAttribute("componenteOptions", delegate.listarComponentesPantalla(id));            
            tileContext.putAttribute("idPantalla", id);

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
