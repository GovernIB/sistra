package org.ibit.rol.form.back.action;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.ibit.rol.form.persistence.delegate.PerfilDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  Controller para obtener una lista de Perfiles
 */
public class ListaPerfilesController implements Controller{
      public void perform(ComponentContext componentContext, HttpServletRequest request,
                        HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException {

            try {
                PerfilDelegate perfilDelegate = DelegateUtil.getPerfilDelegate();
                request.setAttribute("perfilOptions", perfilDelegate.listarPerfiles());
            } catch (DelegateException e) {
                throw new ServletException(e);
            }
      }
}
