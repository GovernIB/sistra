package es.caib.sistra.back.action.mensajeTramite;

import java.io.IOException;
import java.util.Map;

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
import es.caib.sistra.persistence.delegate.MensajeTramiteDelegate;

/**
 * Guarda la lista de mensajeTramites de un mensajeTramite en el contexto.
 */
public class ListaMensajesTramiteController implements Controller{
    protected static Log log = LogFactory.getLog(ListaMensajesTramiteController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaMensajeTramitesController");
            Long id = (Long) request.getAttribute("idTramiteVersion");
            if ( id == null )
            {
            	id = Long.valueOf( request.getParameter( "idTramiteVersion" ) ); 
            }
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            MensajeTramiteDelegate mensajeTramiteDelegate = DelegateUtil.getMensajeTramiteDelegate();
            
            Map sMensajeTramites = mensajeTramiteDelegate.listarMensajeTramites( id );
            //log.info( mMensajeTramitees );
            tileContext.putAttribute("mensajeTramiteOptions", sMensajeTramites);
            request.setAttribute( "mensajeTramiteOptions", sMensajeTramites);
            tileContext.putAttribute("idTramiteVersion", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
