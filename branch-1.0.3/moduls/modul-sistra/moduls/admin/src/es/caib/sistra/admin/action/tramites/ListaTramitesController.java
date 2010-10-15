package es.caib.sistra.admin.action.tramites;

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
import es.caib.sistra.persistence.delegate.GruposDelegate;
import es.caib.sistra.persistence.delegate.TramiteDelegate;


/**
 * Guarda la lista grupos en el contexto.
 */
public class ListaTramitesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaTramitesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaTramitesController");

            TramiteDelegate tramiteDelegate =  DelegateUtil.getTramiteDelegate();
            request.setAttribute("tramites", tramiteDelegate.listarTramites());

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
