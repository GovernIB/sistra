package org.ibit.rol.form.admin.action.validador;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.ValidadorFirmaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Selecciona la llista de todos los validadores de firma en "validadorOptions".
 */
public class ListaValidadoresController implements Controller {
    protected static Log log = LogFactory.getLog(ListaValidadoresController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        try {
            log.debug("Entramos en ListaValidadoresController");
            ValidadorFirmaDelegate validadorDelegate = DelegateUtil.getValidadorFirmaDelegate();
            request.setAttribute("validadorOptions", validadorDelegate.listarValidadoresFirma());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }
}
