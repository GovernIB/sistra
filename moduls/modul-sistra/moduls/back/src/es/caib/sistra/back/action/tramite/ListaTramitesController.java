package es.caib.sistra.back.action.tramite;

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
import es.caib.sistra.persistence.delegate.TramiteDelegate;

/**
 * Guarda la lista de tramites de un tramite en el contexto.
 */
public class ListaTramitesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaTramitesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaTramitesController");
            Long id = (Long) request.getAttribute("idOrgano");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            TramiteDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
            tileContext.putAttribute("tramiteOptions", tramiteDelegate.listarTramitesOrganoResponsable( id ));
            tileContext.putAttribute("idOrgano", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
