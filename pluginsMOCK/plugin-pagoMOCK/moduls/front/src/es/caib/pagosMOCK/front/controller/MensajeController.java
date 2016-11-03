package es.caib.pagosMOCK.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.pagosMOCK.front.Constants;

public class MensajeController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		request.setAttribute( "subtitulo", tileContext.getAttribute( "subtitulo" ) );
		if (request.getAttribute(Constants.MESSAGE_KEY) != null) {
			request.setAttribute( "texto", request.getAttribute(Constants.MESSAGE_KEY) );
		}else{
			request.setAttribute( "texto", tileContext.getAttribute( "texto" ) );
		}									
	}

}
