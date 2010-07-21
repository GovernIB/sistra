package es.caib.redose.back.action.modelo;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.ModeloDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista modelos en el contexto.
 */
public class ListaModelosController implements Controller{
    protected static Log log = LogFactory.getLog(ListaModelosController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaModelosController");

            ModeloDelegate modeloDelegate = DelegateUtil.getModeloDelegate();
            request.setAttribute("modeloOptions", modeloDelegate.listarModelos());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
