package es.caib.sistra.admin.action.ficheroCuaderno;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.admin.action.BaseController;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.FicheroCuadernoDelegate;

public class ListaFicherosCuadernoController extends BaseController
{
	private static Log _log = LogFactory.getLog( ListaFicherosCuadernoController.class );
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		Long idCuadernoCarga = new Long ( request.getParameter( "codigo" ) );
		// TODO Auto-generated method stub
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Entrando a ListaFicherosCuadernoController" );
		}
		try 
		{
			FicheroCuadernoDelegate delegate = DelegateUtil.getFicheroCuadernoDelegate();
			request.setAttribute( "ficherosCuaderno",  delegate.listarFicherosCuaderno( idCuadernoCarga ) );
			//throw new DelegateException();
		}
		catch(DelegateException e) {
            throw new ServletException(e);
        }
	}

}
