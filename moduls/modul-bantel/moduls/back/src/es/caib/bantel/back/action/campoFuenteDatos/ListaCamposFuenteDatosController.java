package es.caib.bantel.back.action.campoFuenteDatos;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;


/**
 * Guarda la lista de campos de una fuente de datos en el contexto.
 */
public class ListaCamposFuenteDatosController implements Controller{
    protected static Log log = LogFactory.getLog(ListaCamposFuenteDatosController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {
            String id = (String) request.getAttribute("idFuenteDatos");
            if(id==null){
                ComponentContext context = ComponentContext.getContext(request);
                if (context != null){
                    id = (String) context.getAttribute("idFuenteDatos");                    
                }
            }
            if (id != null) {
	            FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
	            FuenteDatos fuenteDatos = delegate.obtenerFuenteDatos(id);
	            tileContext.putAttribute("camposOptions", fuenteDatos.getCampos());
	            tileContext.putAttribute("idFuenteDatos", id);
            } else {
            	tileContext.putAttribute("idFuenteDatos", "");
            	tileContext.putAttribute("camposOptions", new ArrayList());
            }
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
    }
}
