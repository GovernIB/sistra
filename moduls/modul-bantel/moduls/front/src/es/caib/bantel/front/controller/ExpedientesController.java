package es.caib.bantel.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.persistence.delegate.ConfiguracionDelegate;
import es.caib.bantel.persistence.delegate.DelegateUtil;

public class ExpedientesController extends BaseController
{

		
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		String urlSistra = "";
		try{
			ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
			urlSistra = delegate.obtenerConfiguracion().getProperty("sistra.url.back");
		}catch (Exception e) {
			urlSistra = "";
		}
		request.setAttribute( "urlSistraAFirma", urlSistra );
		
	}

}