package es.caib.redose.back.action.version;

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
import es.caib.redose.persistence.delegate.VersionDelegate;

/**
 * Guarda la lista de versiones de un formulario en el contexto.
 */
public class ListaVersionesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaVersionesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaVersionesController");
            Long id = (Long) request.getAttribute("codigo");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            VersionDelegate versionDelegate = DelegateUtil.getVersionDelegate();
            tileContext.putAttribute("versionOptions", versionDelegate.listarVersionesModelo( id ));
            tileContext.putAttribute("idModelo", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
