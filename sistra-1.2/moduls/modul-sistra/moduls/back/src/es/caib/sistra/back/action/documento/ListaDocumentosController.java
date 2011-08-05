package es.caib.sistra.back.action.documento;

import java.io.IOException;
import java.util.Set;

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
import es.caib.sistra.persistence.delegate.DocumentoDelegate;

/**
 * Guarda la lista de documentos de un documento en el contexto.
 */
public class ListaDocumentosController implements Controller{
    protected static Log log = LogFactory.getLog(ListaDocumentosController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaDocumentosController");
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
            DocumentoDelegate documentoDelegate = DelegateUtil.getDocumentoDelegate();
            
            Set sDocumentos = documentoDelegate.listarDocumentos( id );
            //log.info( mDocumentoes );
            tileContext.putAttribute("documentoOptions", sDocumentos);
            request.setAttribute( "documentoOptions", sDocumentos);
            tileContext.putAttribute("idTramiteVersion", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
