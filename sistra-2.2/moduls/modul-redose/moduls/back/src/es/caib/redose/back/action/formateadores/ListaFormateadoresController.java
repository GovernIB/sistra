package es.caib.redose.back.action.formateadores;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FormateadorDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista ubicacions en el contexto.
 */
public class ListaFormateadoresController implements Controller{
    protected static Log log = LogFactory.getLog(ListaFormateadoresController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaFormateadoresController");
            FormateadorDelegate fDelegate = DelegateUtil.getFormateadorDelegate();
            request.setAttribute("formateadoresOptions", fDelegate.listar());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
