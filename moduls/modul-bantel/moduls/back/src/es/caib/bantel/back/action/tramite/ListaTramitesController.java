package es.caib.bantel.back.action.tramite;
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
import es.caib.bantel.persistence.delegate.ProcedimientoDelegate;


public class ListaTramitesController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaTramitesController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.debug("Entramos en ListaTramitesController");

            ProcedimientoDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
            request.setAttribute("tramiteOptions", tramiteDelegate.listarProcedimientos() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
