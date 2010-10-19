package es.caib.redose.back.action.erroresGestorDocumental;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista ubicacions en el contexto.
 */
public class ListaLogsGestorDocumentalErroresController implements Controller{
    protected static Log log = LogFactory.getLog(ListaLogsGestorDocumentalErroresController.class);

    private static final String PAGE_PARAM = "pagina";
    private static final int LONGITUD_PAGINA = 20;
    
    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaLogsGestorDocumentalErroresController");

            String strPage = request.getParameter( PAGE_PARAM );
        	strPage = StringUtils.isEmpty( strPage ) ? "0" : strPage;
        	int pagina = Integer.parseInt( strPage, 10 );
        	
        	LogGestorDocumentalErroresDelegate delegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
        	request.setAttribute( "page", delegate.busquedaPaginada(pagina, LONGITUD_PAGINA ) );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}

