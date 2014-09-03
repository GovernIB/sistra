package es.caib.redose.back.action.plantilla;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.redose.model.Formateador;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FormateadorDelegate;

/**
 * Guarda la lista de versiones de un formulario en el contexto.
 */
public class EditarPlantillaController implements Controller{
    protected static Log log = LogFactory.getLog(EditarPlantillaController.class);

    public void perform(ComponentContext tileContext,
                        HttpServletRequest request, HttpServletResponse response,
                        ServletContext servletContext)
            throws ServletException, IOException {

         try {     
        	 Properties props = new Properties();
        	 FormateadorDelegate fDelegate = DelegateUtil.getFormateadorDelegate();
        	 List formateadores = fDelegate.listar();
        	 for(int i=formateadores.size();i>0;i--){
        		 Formateador formateador = (Formateador)formateadores.get(i-1);
        		 props.put(formateador.getIdentificador(),formateador.getDescripcion());
        	 }
        	 request.setAttribute("formateadores", props);        	 
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
