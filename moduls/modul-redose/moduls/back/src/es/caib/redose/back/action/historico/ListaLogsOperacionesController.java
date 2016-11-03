package es.caib.redose.back.action.historico;

import org.apache.struts.tiles.Controller;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogOperacionDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Guarda la lista ubicacions en el contexto.
 */
public class ListaLogsOperacionesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaLogsOperacionesController.class);

    private static final String PAGE_PARAM = "pagina";
    private static final int LONGITUD_PAGINA = 20;
    
    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaLogsOperacionesController");

            /*
            LogOperacionDelegate delegate = DelegateUtil.getLogOperacionDelegate();
            request.setAttribute("historicoOptions", delegate.listarLogOperaciones( null, null, null, null ));
            */
            
            String strPage = request.getParameter( PAGE_PARAM );
        	strPage = StringUtils.isEmpty( strPage ) ? "0" : strPage;
        	int pagina = Integer.parseInt( strPage, 10 );
        	
        	LogOperacionDelegate delegate = DelegateUtil.getLogOperacionDelegate();
        	request.setAttribute( "page", delegate.busquedaPaginada(pagina, LONGITUD_PAGINA ) );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}

