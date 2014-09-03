package es.caib.sistra.admin.action.cuadernoCarga;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.admin.action.BaseController;
import es.caib.sistra.admin.util.Util;
import es.caib.sistra.persistence.delegate.CuadernoCargaDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;

public class ListaCuadernosPendientesController extends BaseController
{
	
	private static Log _log = LogFactory.getLog( ListaCuadernosPendientesController.class );

	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Entrando a ListaCuadernosCargaController" );
		}
		try 
		{
			CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
			request.setAttribute( "cuadernosCarga",  delegate.listarCuadernosPendientes() );
			boolean isAuditor = Util.hasRoleAuditor( request );
			request.setAttribute( "isAuditor", new Boolean( isAuditor ) );
			request.setAttribute( "isAdmin", new Boolean( !isAuditor ) );
			
			request.setAttribute( "cabeceraKey", "cuadernoCarga.cabecera.pendientes" ); 
			
			//throw new DelegateException();
		}
		catch(DelegateException e) {
            throw new ServletException(e);
        }
	}

}
