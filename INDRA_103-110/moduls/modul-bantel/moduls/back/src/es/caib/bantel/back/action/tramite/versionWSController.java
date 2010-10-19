package es.caib.bantel.back.action.tramite;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteDelegate;
import es.caib.bantel.persistence.delegate.VersionWSDelegate;


public class versionWSController implements Controller
{
	protected static Log log = LogFactory.getLog(versionWSController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.info("Entramos en versionWSController");
            
            // Obtenemos lista de versiones
            VersionWSDelegate vWSdelegate = DelegateUtil.getVersionWSDelegate();
            List versiones = vWSdelegate.obtenerVersiones();
            request.setAttribute( "listaVersionesWS", versiones);

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
