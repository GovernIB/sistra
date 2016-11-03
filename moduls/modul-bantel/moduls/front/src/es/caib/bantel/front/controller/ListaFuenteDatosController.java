package es.caib.bantel.front.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.bantel.front.Constants;
import es.caib.bantel.model.Page;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;

public class ListaFuenteDatosController extends BaseController
{

		
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		
		Integer paginaActual = (Integer) request.getSession().getAttribute(Constants.FUENTE_DATOS_PAGINA_ACTUAL);
		
		if (paginaActual == null) {
			paginaActual = new Integer(0);
			request.getSession().setAttribute(Constants.FUENTE_DATOS_PAGINA_ACTUAL, paginaActual);
		}
		
		FuenteDatosDelegate delegate = DelegateUtil.getFuenteDatosDelegate();
		
		Page page = delegate.busquedaPaginadaFuenteDatosGestor(paginaActual.intValue(), Constants.FUENTE_DATOS_PAGINA_TAMANYO);
		
		
		// Si la pagina es mayor de 0 y no hay resultados, redirigimos a pagina 0
		if (paginaActual.intValue() > 0 && page.getList().size() == 0) {
			paginaActual = new Integer(0);
			request.getSession().setAttribute(Constants.FUENTE_DATOS_PAGINA_ACTUAL, paginaActual);
			page = delegate.busquedaPaginadaFuenteDatosGestor(paginaActual.intValue(), Constants.FUENTE_DATOS_PAGINA_TAMANYO);
		}
		
		request.setAttribute( "page", page );
		
	}

}