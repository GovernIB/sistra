package es.caib.zonaper.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.DelegateUtil;



/**
 * @web.filter name="PADFilter"
 * @web.filter-init-param name="enabled" value="true"
 * @web.filter-init-param name="auth-class" value="es.caib.zonaper.filter.PADAuth"
 * @web.filter-init-param name="auth-url" value="/zonaperfilter/datosPAD.do"
 * @web.filter-init-param name="auth-error" value="/zonaperfilter/errorPAD.do"
 * @web.filter-init-param name="excluded-url" value="^.*datosPAD.do.*$,^.*actualizarDatosPAD.do.*$,^.*errorPAD.do.*$,^.*perfilAccesoPAD.do.*$,^.*elegirPerfilAccesoPAD.do.*$"
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

	// Parametros donde vendra el perfil y la entidad
	public static String PARAMETER_PERFIL_KEY = "perfilAF";
	public static String PARAMETER_ENTIDAD_KEY = "entidadAF";
	
	
	public void init(FilterConfig filterConfig) throws ServletException
	{
		try
		{
			_log.debug( "Init. Enabled [" + filterConfig.getInitParameter( "enabled" ) + "]" );
			this.filterConfig = filterConfig;
			enabled = Boolean.valueOf( filterConfig.getInitParameter( "enabled" ) ).booleanValue();
			if ( conf == null )
			{
				Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
				
				conf = new AuthConf();
				conf.setAuthURL( props.getProperty("sistra.contextoRaiz") + filterConfig.getInitParameter( "auth-url" ) );
				conf.setAuthErrorURL( props.getProperty("sistra.contextoRaiz") +  filterConfig.getInitParameter( "auth-error" ) );
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
			
			System.out.println(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY + " = " + req.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY));
			
			// Filtro de autenticacion 
			if ( enabled && req.getAttribute( "internal") == null )
			{
				req.setAttribute( "internal", "true" );
				String uri = Util.getUrl( req );
				if ( _log.isDebugEnabled() ) _log.debug( uri );
				if ( !conf.isExcluded ( uri ) )
				{
						
					// Check autenticacion
					checkAutenticacion(request,response);
					if ( res.isCommitted() )
						{
						return;
						}
						
					// Filtro de seleccion perfil acceso
					// Comprobamos la primera vez si el usuario es delegado de alguna entidad
					// para que elija el perfil de acceso
					checkPerfilAcceso(request,response);
										
					if ( res.isCommitted() )
					{
						return;
					}
				}
				
			}
			
			// Usuario autenticado, permitimos pasar 
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

	
	private void checkAutenticacion(ServletRequest request, ServletResponse response) throws Exception{
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse res = ( HttpServletResponse ) response;
		
		// Comprobamos si usuario esta definido en la tabla de personas de zonaper
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
	}
	
	
	private void checkPerfilAcceso(ServletRequest request, ServletResponse response) throws Exception{
		
		HttpServletRequest req = ( HttpServletRequest ) request;
		HttpServletResponse res = ( HttpServletResponse ) response;
		
		Principal seycon = req.getUserPrincipal();
		if ( seycon == null )
		{
			throw new Exception( "Se está ejecutando el filtro de acceso sin que exista un usuario logado" );			
		}
		
		// Obtenemos plugin de login
		PluginLoginIntf plgLogin = null;
		try{
			plgLogin = PluginFactory.getInstance().getPluginLogin();
		}catch (Exception ex){
			_log.error(ex);
			throw new AuthException(AuthException.ERROR_DESCONOCIDO,ex);
		}
		
		// Para usuarios anónimos no es necesario		
		if ( plgLogin.getMetodoAutenticacion( seycon ) == 'A' ){
			return;
		}
		
		
		// Comprobamos si ya se ha establecido el perfil de acceso en la sesion
		String perfilAcceso = (String) req.getSession().getAttribute( ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY);
		
		if ( perfilAcceso == null )
		{
			
			// Comprobamos si se indica el perfil de acceso como parametro y lo metemos en la sesion
			if (req.getParameter(PARAMETER_PERFIL_KEY) != null){
				req.getSession().setAttribute( ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY,req.getParameter(PARAMETER_PERFIL_KEY));
				
				
				if (  ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(req.getParameter(PARAMETER_PERFIL_KEY)) && req.getParameter(PARAMETER_ENTIDAD_KEY) == null){
					throw new Exception( "No se ha indicado de que entidad es delegado" );				
				}
				req.getSession().setAttribute( ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY,req.getParameter(PARAMETER_ENTIDAD_KEY));
				return;
			}

			// Si no se ha establecido comprobamos si el usario puede ser delegado de entidades,
			// y en caso afirmativo le solicitamos el perfil de acceso			
			if (DelegatePADUtil.getPadDelegate().esDelegado()){
				// Redirigimos a pagina peticion perfil acceso
				String lang = Util.getLang( req );
				StringBuffer sbAuthAction = new StringBuffer( "/zonaperfilter/perfilAccesoPAD.do" );
				sbAuthAction.append( sbAuthAction.indexOf( "?" ) >= 0 ? "&" : "?" ).append( "urlOriginal=" ).append( URLEncoder.encode( Util.getUrl( req ), "UTF-8" ) );
				if ( !StringUtils.isEmpty( lang ) )
				{
					sbAuthAction.append( "&lang=" ).append( lang );
				}
				if ( _log.isDebugEnabled() ) _log.debug( "Redireccionando a " + sbAuthAction.toString() );
				res.sendRedirect(  sbAuthAction.toString()  );				
			}else{
				req.getSession().setAttribute( ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY,ConstantesZPE.DELEGACION_PERFIL_ACCESO_CIUDADANO );
			}
		}
		 
	}

}
