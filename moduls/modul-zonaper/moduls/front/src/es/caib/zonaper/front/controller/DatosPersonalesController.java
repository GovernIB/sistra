package es.caib.zonaper.front.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.front.form.ActualizarDatosPersonalesForm;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

public class DatosPersonalesController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{

		ActualizarDatosPersonalesForm f = (ActualizarDatosPersonalesForm) request.getAttribute("actualizarDatosPersonalesForm");
		
		// Obtenemos provincias y municipios
		List provincias=DelegateUtil.getDominiosDelegate().listarProvincias();
		request.setAttribute("provincias",provincias);
		List locs = DelegateUtil.getDominiosDelegate().listarLocalidadesProvincia(f.getProvincia());
		request.setAttribute("municipios",locs);
				
	}	
}
