package es.caib.bantel.back.action.ficheroExportacion;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;


public class ListaFicheroExportacionController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaFicheroExportacionController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.debug("Entramos en ListaFicheroExportacionController");

            FicheroExportacionDelegate delegate = DelegateUtil.getFicheroExportacionDelegate();
            request.setAttribute("ficheroExportacionOptions", delegate.listarFicherosExportacion() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
