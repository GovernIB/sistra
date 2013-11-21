package es.caib.redose.back.action.plantilla;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.PlantillaDelegate;

/**
 * Guarda la lista de plantillas de un version en el contexto.
 */
public class ListaPlantillasController implements Controller{
    protected static Log log = LogFactory.getLog(ListaPlantillasController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaPlantillasController");
            Long id = (Long) request.getAttribute("idVersion");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            PlantillaDelegate plantillaDelegate = DelegateUtil.getPlantillaDelegate();
            tileContext.putAttribute("plantillaOptions", plantillaDelegate.listarPlantillasVersion(id));
            tileContext.putAttribute("idVersion", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
