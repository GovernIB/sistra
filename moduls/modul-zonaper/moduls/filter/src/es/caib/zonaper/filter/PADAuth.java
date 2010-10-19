package es.caib.zonaper.filter;

import java.net.URLEncoder;
import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

public class PADAuth implements AuthInterface
{
	private static Log _log = LogFactory.getLog( PADAuth.class );
	private static String PAD_DATA_SESSION_KEY = "IS_PAD_DATA";
	
	public Principal getAuthObject(AuthConf conf, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws AuthException
	{
		Principal seycon = request.getUserPrincipal();
		if ( seycon == null )
		{
			_log.error( "Se está ejecutando el filtro de acceso a la path sin que exista un usuario logado" );
			return null;
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
		if ( plgLogin.getMetodoAutenticacion( seycon ) == 'A' )
		{
			return seycon;
		}
		// Llegados a este punto, hay que comprobar, 
		// 1º Si existe en la sesión los datos del usuario quiere decir que el usuario tiene
		Boolean exists = ( Boolean ) request.getSession().getAttribute( PAD_DATA_SESSION_KEY );
		if ( exists != null && exists.booleanValue() )
		{
			return seycon;
		}
		// 2º Si no existe esa información en sesión accedemos a la zona personal para comprobar si existe
		try{
			// Si no tiene nif/cif asociado no puede tramitar
			if (StringUtils.isEmpty(plgLogin.getNif(seycon))){
				_log.error("Usuario no puede tramitar ya que no tiene asignado nif");
				throw new AuthException(AuthException.ERROR_NO_NIF);
			}
			// Comprobamos si esta dado de alta en la zona personal
			PadDelegate delegate = DelegatePADUtil.getPadDelegate();
			if ( delegate.existePersonaPADporUsuario( seycon.getName() ) )
			{
				// Comprobamos que el nif de seycon concuerda con el de la zona personal
				PersonaPAD pers = delegate.obtenerDatosPersonaPADporUsuario(seycon.getName());
				if (!pers.getNif().equalsIgnoreCase(plgLogin.getNif(seycon))){
					throw new AuthException(AuthException.ERROR_NIF_NO_CONCUERDA);					
				}
				
				// Si existe, cachear
				request.getSession().setAttribute( PAD_DATA_SESSION_KEY, new Boolean( true ) );
				return seycon;
			}
			
			// 3º Si no se encuentra registrado comprobamos si existe alguna persona con ese nif.
			//    En caso de que haya alguien registrado, actualizamos usuario seycon asociado
			PersonaPAD p = delegate.obtenerDatosPersonaPADporNif(plgLogin.getNif(seycon));
			if ( p != null){
				delegate.actualizarCodigoUsuario(p.getUsuarioSeycon(),seycon.getName());
				return seycon;
			}	
		
		}catch (DelegateException de){
			throw new AuthException(AuthException.ERROR_CONEXION_PAD);
		}
		
		// 4º Si no esta registrado y no hay nadie con ese nif devolvemos null	
		return null;
	}

	public void doAuthAction(AuthConf conf, ServletContext context,
			HttpServletRequest request, HttpServletResponse response) throws AuthException
	{
		if ( _log.isDebugEnabled() ) _log.debug( "Redireccionando a la accion que permite dar de alta al usuario seycon" );
		try
		{
			String lang = Util.getLang( request );
			StringBuffer sbAuthAction = new StringBuffer( conf.getAuthURL() );
			sbAuthAction.append( sbAuthAction.indexOf( "?" ) >= 0 ? "&" : "?" ).append( "urlOriginal=" ).append( URLEncoder.encode( Util.getUrl( request ), "UTF-8" ) );
			if ( !StringUtils.isEmpty( lang ) )
			{
				sbAuthAction.append( "&lang=" ).append( lang );
			}
			if ( _log.isDebugEnabled() ) _log.debug( "Redireccionando a " + sbAuthAction.toString() );
			response.sendRedirect(  sbAuthAction.toString()  );
		}
		catch( Exception exc )
		{
			_log.error( exc );
			throw new AuthException (AuthException.ERROR_DESCONOCIDO,exc);
		}
	}
	
	public void doAuthErrorAction(int codigoError,AuthConf conf, ServletContext context,
			HttpServletRequest request, HttpServletResponse response) throws AuthException
	{
		if ( _log.isDebugEnabled() ) _log.debug( "Redireccionando a la accion que muestra error en proceso de autenticacion" );
		try
		{
			String lang = Util.getLang( request );
			StringBuffer sbAuthAction = new StringBuffer( conf.getAuthErrorURL() );
			sbAuthAction.append( sbAuthAction.indexOf( "?" ) >= 0 ? "&" : "?" ).append( "error=" + codigoError );
			if ( !StringUtils.isEmpty( lang ) )
			{
				sbAuthAction.append( "&lang=" ).append( lang );
			}
			if ( _log.isDebugEnabled() ) _log.debug( "Redireccionando a " + sbAuthAction.toString() );
			response.sendRedirect(  sbAuthAction.toString()  );
		}
		catch( Exception exc )
		{
			_log.error( exc );
			throw new AuthException (AuthException.ERROR_DESCONOCIDO,exc);
		}
	}
	
}
