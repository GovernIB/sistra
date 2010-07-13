package es.caib.sistra.back.action.mensajePlataforma;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.MensajePlataformaDelegate;

/**
 * Guarda la lista de mensajePlataformas de un mensajePlataforma en el contexto.
 */
public class ListaMensajePlataformaController implements Controller{
    protected static Log log = LogFactory.getLog(ListaMensajePlataformaController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaMensajePlataformaController");           
            MensajePlataformaDelegate mensajePlataformaDelegate = DelegateUtil.getMensajePlataformaDelegate();
            tileContext.putAttribute("mensajePlataformaOptions", mensajePlataformaDelegate.listarMensajePlataformas());            
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
