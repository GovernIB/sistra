package org.ibit.rol.form.back.action.paleta.componente;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.ComponenteDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Selecciona la lista de los componentes de una paleta en "componenteOptions".
 * Necesita el atributo idPaleta al request.
 */
public class ListaComponentesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaComponentesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaComponentesController");
            Long idPaleta = new Long(request.getParameter("idPaleta"));
            if (idPaleta != null) {
                ComponenteDelegate delegate = DelegateUtil.getComponenteDelegate();
                tileContext.putAttribute("componenteOptions", delegate.listarComponentesPaleta(idPaleta));
                tileContext.putAttribute("idPaleta", idPaleta);
            }

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
