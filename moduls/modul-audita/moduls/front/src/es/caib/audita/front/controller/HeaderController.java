package es.caib.audita.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

public class HeaderController extends BaseController
{

	private static String OPCION_INICIO = "inicio";
	private static String OPCION_ANUAL = "anual";
	private static String OPCION_MENSUAL = "mensual";
	private static String OPCION_DIARIO = "diario";

	private static Log _log = LogFactory.getLog( HeaderController.class );

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{

		String url = request.getRequestURL().toString();
		String opcion = getOpcionPestanya( url );

		request.setAttribute( "opcion", opcion );


	}

	private String getOpcionPestanya( String url )
	{
		if ( url.indexOf( OPCION_INICIO ) != -1 || url.indexOf( "init" ) != -1 ||
			 url.indexOf("refresh") != -1)
			return OPCION_INICIO;
		if ( url.indexOf( OPCION_ANUAL ) != -1 )
			return OPCION_ANUAL;
		if ( url.indexOf( OPCION_MENSUAL ) != -1 )
			return OPCION_MENSUAL;
		if ( url.indexOf( OPCION_DIARIO ) != -1 )
			return OPCION_DIARIO;
		return "";
	}

}
