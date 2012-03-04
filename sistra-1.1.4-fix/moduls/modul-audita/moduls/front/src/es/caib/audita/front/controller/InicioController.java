package es.caib.audita.front.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

public class InicioController extends BaseController
{
	private static Log _log = LogFactory.getLog( InicioController.class );
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{

		Calendar fecha = Calendar.getInstance();
	    int anyo = fecha.get(Calendar.YEAR);
		List anyos = new ArrayList();
		anyos.add(String.valueOf(anyo-3));
		anyos.add(String.valueOf(anyo-2));
		anyos.add(String.valueOf(anyo-1));
		anyos.add(String.valueOf(anyo));
		anyos.add(String.valueOf(anyo+1));
		anyos.add(String.valueOf(anyo+2));
		anyos.add(String.valueOf(anyo+3));
		request.setAttribute("anyos", anyos);

		_log.debug( "execute" );
	}

}
