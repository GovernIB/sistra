package org.ibit.rol.form.admin.action.paleta.valor;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import java.io.IOException;

/**
 * Selecciona la lista de los valores de un componente en "valoresOptions".
 * Necesita l'atribut idPaleta al request.
 */
public class ListaValoresPosiblesController implements Controller {
    protected static Log log = LogFactory.getLog(ListaValoresPosiblesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaValoresPosiblesController");

            Long id = (Long) request.getAttribute("idComponente");
            if (id != null) {
                ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();
                tileContext.putAttribute("valoresOptions", delegate.listarValoresPosiblesCampo(id));
                tileContext.putAttribute("idComponente", id);
            }

        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }

}
