package es.caib.zonaper.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @web.filter name="PADFilter"
 * @web.filter-init-param name="enabled" value="true"
 * @web.filter-init-param name="auth-class" value="es.caib.zonaper.filter.PADAuth"
 * @web.filter-init-param name="auth-url" value="/zonaperfilter/datosPAD.do"
 * @web.filter-init-param name="auth-error" value="/zonaperfilter/errorPAD.do"
 * @web.filter-init-param name="excluded-url" value="^.*datosPAD.do.*$,^.*actualizarDatosPAD.do.*$,^.*errorPAD.do.*$"
 * @web.filter-mapping url-pattern = "*.do" servlet-name = "PADFilter"
 *
 */
public class AuthFilterServlet implements Filter
{
	private static Log _log = LogFactory.getLog( AuthFilterServlet.class );
	private AuthConf conf = null;
	private FilterConfig filterConfig;
	boolean enabled = true;
	private Class authClass;

	public void init(FilterConfig filterConfig) throws ServletException
	{
		try
		{
			_log.debug( "Init. Enabled [" + filterConfig.getInitParameter( "enabled" ) + "]" );
			this.filterConfig = filterConfig;
			enabled = Boolean.valueOf( filterConfig.getInitParameter( "enabled" ) ).booleanValue();
			if ( conf == null )
			{
				conf = new AuthConf();
				conf.setAuthURL( filterConfig.getInitParameter( "auth-url" ) );
				conf.setAuthErrorURL( filterConfig.getInitParameter( "auth-error" ) );
				conf.setExcluded( filterConfig.getInitParameter( "excluded-url" ) );
			}
			String authImpl = filterConfig.getInitParameter( "auth-class" );
			_log.debug( "Init. Implementation " + authImpl );
			authClass = Class.forName( authImpl );
			if ( !AuthInterface.class.isAssignableFrom( authClass ) )
				throw new Exception( "La clase " + authClass.getName() + " no implementa " + AuthInterface.class.getName() );
		}
		catch( Exception exc )
		{
			_log.error( exc );
			throw new ServletException( exc );
		}
	}
		

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		try
		{
			HttpServletRequest req = ( HttpServletRequest ) request;
			HttpServletResponse res = ( HttpServletResponse ) response;
			if ( enabled && req.getAttribute( "internal") == null )
			{
				req.setAttribute( "internal", "true" );
				String uri = Util.getUrl( req );
				if ( _log.isDebugEnabled() ) _log.debug( uri );
				if ( !conf.isExcluded ( uri ) )
				{
					AuthInterface authenticator = getAuthImpl();
					
					Object authObject = null;
					try{
						// Comprobamos si se esta registrado						
						authObject = authenticator.getAuthObject( conf, filterConfig.getServletContext(), req, res );
						
						//  Si no devuelve objeto de autenticacion indica que no se encuentra registrado 
						if ( authObject == null )
						{
							authenticator.doAuthAction( conf, filterConfig.getServletContext(), req, res );
						}
						
					}catch (AuthException aex){
						authenticator.doAuthErrorAction(aex.getCodigoError(), conf, filterConfig.getServletContext(), req, res );
					}
										
					if ( res.isCommitted() )
					{
						return;
					}
				}
			}
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
		conf = null;
		authClass = null;
	}
	
	private AuthInterface getAuthImpl() throws Exception
	{
		return ( AuthInterface ) authClass.newInstance();
	}

}
