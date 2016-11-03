package es.caib.sistra.admin.action.grupos;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.sistra.model.Tramite;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GruposDelegate;

/**
 * Guarda la lista grupos en el contexto.
 */
public class ListaGruposTramiteController implements Controller{
    protected static Log log = LogFactory.getLog(ListaGruposTramiteController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            log.debug("Entramos en ListaGruposTramiteController");
            if(request.getAttribute("tramite") != null){
            	Tramite attribute = (Tramite)request.getAttribute("tramite");
            	Long id = new Long(attribute.getCodigo());
            
            	GruposDelegate gruposDelegate = DelegateUtil.getGruposDelegate();
            	tileContext.putAttribute("gruposForm", gruposDelegate.listarGruposByTramite(id));
            	tileContext.putAttribute("gruposNoForm", gruposDelegate.listarGruposByNotTramite(id));
            	tileContext.putAttribute("existeGrupo",gruposDelegate.existenGrupos());
            	tileContext.putAttribute("idTramite", id);
            }
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
