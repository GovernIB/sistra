package es.caib.zonaper.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.front.util.ZonapersFrontRequestHelper;

public class MenuAutenticadoController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		request.setAttribute("habilitarAlertas", Boolean.toString(ZonapersFrontRequestHelper.isHabilitarApartadoAlertas(request)));
		
	}
	
	

}
