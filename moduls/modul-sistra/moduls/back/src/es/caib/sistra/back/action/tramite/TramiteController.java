package es.caib.sistra.back.action.tramite;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.sistra.back.action.BaseController;

/**
 * Tramite controller.
 */
public class TramiteController extends BaseController{
    protected static Log log = LogFactory.getLog(TramiteController.class);
    

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en TramiteController");
            
            // TODO Obtener procedimientos
            /*
            List entidades  = DelegateBTEUtil.getBteSistraDelegate().			
			request.setAttribute( "procedimientos", procedimientos );
			*/
			
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
   

}
