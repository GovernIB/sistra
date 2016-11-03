package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.front.Constants;

public class MensajeController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		// Textos x defecto
		request.setAttribute( "subtitulo", tileContext.getAttribute( "subtitulo" ) );
		request.setAttribute( "texto", tileContext.getAttribute( "texto" ) );
		
		// Comprobamos si se han establecido el titulo y/o el texto particularizado
		String menu = (String) request.getAttribute(Constants.MENSAJE_MENU_NAVEGACION);
		String titulo = (String) request.getAttribute(Constants.MENSAJE_TITULO);
		String texto = (String) request.getAttribute(Constants.MENSAJE_TEXTO);
		if (StringUtils.isNotEmpty(titulo)){
			request.setAttribute( "subtitulo", titulo );
		}
		if (StringUtils.isNotEmpty(texto)){
			request.setAttribute( "texto", texto );
		}
				
		// Comprobamos si se ha establecido un menu molla pa y si no cogemos el ultimo molla pa y lo metemos
		if (StringUtils.isNotEmpty(menu)){
			tileContext.putAttribute( Constants.MENU_NAVEGACION_PREFFIX , menu); 
		}else{
			String entryKey = ( String ) request.getSession().getAttribute(Constants.MENU_NAVEGACION_LAST);
			if (StringUtils.isNotEmpty(entryKey)){
				tileContext.putAttribute( Constants.MENU_NAVEGACION_PREFFIX , entryKey); 
			}
		}
		
	}

}
