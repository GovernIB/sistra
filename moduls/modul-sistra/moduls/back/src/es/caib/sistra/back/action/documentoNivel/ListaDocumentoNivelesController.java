package es.caib.sistra.back.action.documentoNivel;

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
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;

/**
 * Guarda la lista de documentoNivels de un documentoNivel en el contexto.
 */
public class ListaDocumentoNivelesController implements Controller{
    protected static Log log = LogFactory.getLog(ListaDocumentoNivelesController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.info("Entramos en ListaDocumentoNivelsController");
            Long id = (Long) request.getAttribute("idDocumento");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    if (context.getAttribute("id").toString().length() > 0) {
                         id = new Long(context.getAttribute("id").toString());
                    }
                }
            }
            DocumentoNivelDelegate documentoNivelDelegate = DelegateUtil.getDocumentoNivelDelegate();
            
            Set mDocumentoNivels = documentoNivelDelegate.listarDocumentoNiveles( id );
            //log.info( mDocumentoNiveles );
            tileContext.putAttribute("documentoNivelOptions", mDocumentoNivels);
            tileContext.putAttribute("idDocumento", id);
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
