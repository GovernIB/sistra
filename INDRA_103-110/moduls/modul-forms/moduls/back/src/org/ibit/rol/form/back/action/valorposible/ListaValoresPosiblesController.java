package org.ibit.rol.form.back.action.valorposible;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista de valores posibles de un campo en el contexto.
 */
public class ListaValoresPosiblesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaValoresPosiblesController.class);

    public void perform(ComponentContext tileContext,
                          HttpServletRequest request, HttpServletResponse response,
                          ServletContext servletContext)
              throws ServletException, IOException {
        try {
            log.debug("Entramos en ListaValoresPosiblesController");

            HttpSession sesion = request.getSession(true);
            Long id = (Long) sesion.getAttribute("idComponente");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            ValorPosibleDelegate delegate = DelegateUtil.getValorPosibleDelegate();
            tileContext.putAttribute("valoresOptions", delegate.listarValoresPosiblesCampo(id));
            tileContext.putAttribute("idComponente", id);

        } catch (DelegateException e) {
            throw new ServletException(e);
        }

    }

}
