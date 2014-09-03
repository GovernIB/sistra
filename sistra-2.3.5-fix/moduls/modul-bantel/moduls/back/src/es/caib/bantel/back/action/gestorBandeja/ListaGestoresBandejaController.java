package es.caib.bantel.back.action.gestorBandeja;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;


public class ListaGestoresBandejaController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaGestoresBandejaController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.debug("Entramos en ListaGestoresBandejaController");

            GestorBandejaDelegate gestorBandejaDelegate = DelegateUtil.getGestorBandejaDelegate();
            request.setAttribute("gestorBandejaOptions", gestorBandejaDelegate.listarGestoresBandeja() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
