package org.ibit.rol.form.admin.action.paleta.componente;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Selecciona la lista de los componentes de una paleta en "componenteOptions".
 * Necesita l'atribut idPaleta al request.
 */
public class ListaComponentesController implements Controller {

    protected static Log log = LogFactory.getLog(ListaComponentesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaComponentesController");

            Long id = (Long) request.getAttribute("idPaleta");
            if (id != null) {
                ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();
                tileContext.putAttribute("componenteOptions", delegate.listarComponentesPaleta(id));
                tileContext.putAttribute("idPaleta", id);
            }

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }

}
