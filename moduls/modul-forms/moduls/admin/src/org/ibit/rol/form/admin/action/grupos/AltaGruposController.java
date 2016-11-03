package org.ibit.rol.form.admin.action.grupos;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

/**
 * Guarda la lista grupos en el contexto.
 */
public class AltaGruposController implements Controller{
    protected static Log log = LogFactory.getLog(AltaGruposController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

        log.debug("Entramos en AltaGruposController");

		if(request.getSession().getAttribute("usuarios")!=null && request.getSession().getAttribute("error")==null){
			request.getSession().setAttribute("usuarios",null);
		}
    }
}
