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

public class ListaCuadernosFinalizadosController extends BaseController
{
	
	private static Log _log = LogFactory.getLog( ListaCuadernosFinalizadosController.class );

	public void perform(ComponentContext arg0, HttpServletRequest req,
			HttpServletResponse res, ServletContext arg3)
			throws ServletException, IOException
	{
		if ( _log.isDebugEnabled() )
		{
			_log.debug( "Entrando a ListaCuadernosFinalizadosController" );
		}
		try 
		{
			CuadernoCargaDelegate delegate = DelegateUtil.getCuadernoCargaDelegate();
			req.setAttribute( "cuadernosCarga",  delegate.listarCuadernosFinalizados() );
			Util.setDevelopperMode( req );
			
			req.setAttribute( "cabeceraKey", "cuadernoCarga.cabecera.finalizados" );
			
			
			//throw new DelegateException();
		}
		catch(DelegateException e) {
            throw new ServletException(e);
        }

	}

}
