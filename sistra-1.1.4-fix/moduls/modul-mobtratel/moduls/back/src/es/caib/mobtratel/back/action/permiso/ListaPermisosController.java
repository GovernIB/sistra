package es.caib.mobtratel.back.action.permiso;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import es.caib.mobtratel.persistence.delegate.DelegateException;
import es.caib.mobtratel.persistence.delegate.DelegateUtil;
import es.caib.mobtratel.persistence.delegate.PermisoDelegate;




public class ListaPermisosController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaPermisosController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		
		try 
		{
            log.info("Entramos en ListaGestoresBandejaController");

            PermisoDelegate permisoDelegate = DelegateUtil.getPermisoDelegate();
            request.setAttribute("permisoOptions", permisoDelegate.listarPermisos() );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
        
		

	}

}
