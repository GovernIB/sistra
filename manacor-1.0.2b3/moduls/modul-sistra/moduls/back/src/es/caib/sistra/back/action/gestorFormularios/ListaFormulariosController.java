package es.caib.sistra.back.action.gestorFormularios;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;


public class ListaFormulariosController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaFormulariosController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.info("Entramos en ListaFormulariosController");
            
            GestorFormularioDelegate gestorFormulariosDelegate = DelegateUtil.getGestorFormularioDelegate();
            request.setAttribute("gestorFormulariosOptions", gestorFormulariosDelegate.listar() );
            
            log.info("Salimos de ListaFormulariosController");
        } catch (DelegateException e) {
            log.info("Error al cargar la lista de formularios "+e.getCause()+" "+e.getMessage());
            throw new ServletException(e);
        }
		

	}

}
