package es.caib.audita.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

public class CuadroMandoTemporalController extends BaseController
{

	private static Log _log = LogFactory.getLog( CuadroMandoTemporalController.class );
	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		_log.debug( "CuadroMandoTemporalController" );
	}

}
