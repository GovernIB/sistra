package es.caib.sistra.back.action.tramiteVersion;

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
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;

/**
 * Guarda la lista de tramiteVersions de un tramiteVersion en el contexto.
 */
public class ListaTramiteVersionesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaTramiteVersionesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaTramiteVersionesController");
            Long id = (Long) request.getAttribute("idTramite");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            TramiteVersionDelegate tramiteVersionDelegate = DelegateUtil.getTramiteVersionDelegate();
            tileContext.putAttribute("tramiteVersionOptions", tramiteVersionDelegate.listarTramiteVersiones( id ));
            tileContext.putAttribute("idTramite", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
