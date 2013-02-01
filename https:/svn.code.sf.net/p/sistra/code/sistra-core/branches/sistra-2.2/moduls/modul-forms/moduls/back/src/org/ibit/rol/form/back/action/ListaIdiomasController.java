package org.ibit.rol.form.back.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.IdiomaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 * Controller para obtener una lista de idiomas.
 */
public class ListaIdiomasController implements Controller {

    public void perform(ComponentContext componentContext, HttpServletRequest request,
                        HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

        try {
            IdiomaDelegate delegate = DelegateUtil.getIdiomaDelegate();
            request.setAttribute("idiomas", delegate.listarLenguajes());
        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }
}
