package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class EstadoExpedientesController extends BaseController
{
	private static final String PAGE_PARAM = "pagina";
	private static final int LONGITUD_PAGINA = 10;
	
	public void execute(ComponentContext tileContext, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws Exception
	{		 
		String strPage = request.getParameter( PAGE_PARAM );
		
		if (StringUtils.isEmpty( strPage)){
			strPage =(String) request.getSession().getAttribute(PAGE_PARAM); 
		}
		
		strPage = StringUtils.isEmpty( strPage ) ? "0" : strPage;
		int pagina = Integer.parseInt( strPage, 10 );		
		
		// Almacenamos en session la pagina xa despues volver a esa pagina
		request.getSession().setAttribute(PAGE_PARAM,strPage);
		
		request.setAttribute( "page", DelegateUtil.getEstadoExpedienteDelegate().busquedaPaginadaExpedientes( pagina, LONGITUD_PAGINA ) );		
	}
}
