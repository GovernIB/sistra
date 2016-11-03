package es.caib.zonaper.filter.front.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.filter.front.Constants;

public abstract class MainController implements Controller
{
	public static Log _log = LogFactory.getLog( MainController.class );
	public void perform(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException
	{

		// Inicializamos informacion organismo	
 		if (request.getSession().getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY) == null){
 			OrganismoInfo oi;
			try {
				oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
			} catch (DelegateException e) {
				_log.error("Exception obteniendo configuracion organismo");
				throw new ServletException(e);
			}
 			request.getSession().getServletContext().setAttribute(Constants.ORGANISMO_INFO_KEY,oi);
 		}
		
		String look = request.getParameter( Constants.LOOK_PARAM );
		look = StringUtils.defaultString( look, Constants.AT_LOOK );
		
		if ( _log.isDebugEnabled() ) _log.debug( "Escogido look " + look );
		
		// capsal : capsalAT para Asistente de tramitacion o capsalOP para Zona Personal 
		tileContext.putAttribute( "capsal", tileContext.getAttribute( "capsal" + look ) );
		
//		 peu : peuAT para Asistente de tramitacion o peuOP para Zona Personal 
		tileContext.putAttribute( "peu", tileContext.getAttribute( "peu" + look ) );
		
		request.setAttribute( "look", look );
		
		performTask( tileContext, request, response, servletContext );

	}
	
	public abstract void performTask (ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws ServletException, IOException;

}
