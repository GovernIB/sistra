package org.ibit.rol.form.back.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.ibit.rol.form.back.util.Util;


public class SesionCaducadaFilter  implements Filter{
	private static Log _log = LogFactory.getLog(SesionCaducadaFilter.class );
	
	public void init(FilterConfig filterConfig) throws ServletException
	{
		_log.debug( "init" );			
	}
		
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		try
		{
			HttpServletRequest req = ( HttpServletRequest ) request;
			HttpServletResponse res = ( HttpServletResponse ) response;
	
			// Si la sesion ha caducado redirigimos a inicio
	    	 HttpSession sesion = req.getSession(false);
	    	 String url = Util.getUrl( req );
	    	 boolean esInit = url.matches( "[\\S]*/init.do[\\S]*" );	    	 
	    	 if(!esInit){
	    		 if (sesion == null || sesion.getAttribute("org.ibit.rol.form.back.datosSesion") == null){
	    		 	_log.debug("Sesion caducada o no existe. Redirigimos a init.do");
	    		 	if (sesion != null) sesion.invalidate();
	    		 	res.sendRedirect(req.getContextPath() + "/init.jsp");
	    		 	return;
	    	 	}
	    	 }
			
			// Si la sesion esta activa dejamos pasar
			chain.doFilter( request, response );
		}
		catch( Exception exc )
		{
			_log.error( exc );
			throw new ServletException( exc );
		}
	}

	public void destroy()
	{
		_log.debug( "destroy" );	
	}
	
}

