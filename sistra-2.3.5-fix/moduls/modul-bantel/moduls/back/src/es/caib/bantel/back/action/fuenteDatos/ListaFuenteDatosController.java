package es.caib.bantel.back.action.fuenteDatos;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;


public class ListaFuenteDatosController implements Controller
{
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
           FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
           request.setAttribute("fuenteDatosOptions", delegate.listarFuentesDatos() );
        } catch (DelegateException e) {
            throw new ServletException(e);
        }
	}

}
