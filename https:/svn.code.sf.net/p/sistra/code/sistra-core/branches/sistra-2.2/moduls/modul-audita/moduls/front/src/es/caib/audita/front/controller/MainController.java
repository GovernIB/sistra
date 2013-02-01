package es.caib.audita.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

public class MainController extends BaseController
{
	//private static Log log = LogFactory.getLog( MainController.class );
	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		/*
		LoggerEventoDelegate delegate = DelegateUtil.getLoggerEventoDelegate();
		List listaEventos = delegate.listarTiposEvento();
		log.info( listaEventos );
		request.setAttribute( "tiposEvento", listaEventos );
		*/
	}

}
