package es.caib.sistra.front.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.formulario.FactoriaGestorFlujoFormulario;
import es.caib.sistra.front.formulario.GestorFlujoFormulario;
import es.caib.sistra.front.storage.Storable;
import es.caib.sistra.front.storage.StorageFactory;

public class FlujoFormularioRequestHelper
{
	private static Log log = LogFactory.getLog( FlujoFormularioRequestHelper.class );
	
	public static GestorFlujoFormulario obtenerGestorFormulario( HttpServletRequest request, String token, boolean create ) throws Exception
	{
		GestorFlujoFormulario gestorFormulario = ( GestorFlujoFormulario ) recoverObject( request, Constants.GESTOR_FORM_SESSION_KEY + token );
		
		if ( gestorFormulario == null )
		{
			if ( !create )
			{
				log.error( "En este punto no se puede reiniciar el proceso del formulario" );
				return null;
			}
			
			gestorFormulario = FactoriaGestorFlujoFormulario.getInstance().obtenerGestorFlujoFormulario( Constants.GESTOR_FORM_CLASS );			
			gestorFormulario.setExpirationTime( ( new java.util.Date() ).getTime() + request.getSession().getMaxInactiveInterval() * 1000 ) ;
			

			// Introducimos en sesion el token con el que se almacena el gestor de formularios en el servlet context,
			// para que si no se completa el formulario, el evento de expiración de sesión lo borre de este mismo contexto
			request.getSession().setAttribute( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO, token );
			
			
			storeObject( request, Constants.GESTOR_FORM_SESSION_KEY + token, gestorFormulario );
		}
		return gestorFormulario;
	}

	public static void resetGestorFormulario( HttpServletRequest request, String token ) throws Exception
	{
		// Eliminamos el token de sesión, indicando que se han liberado los recursos del servlet context
		request.getSession().removeAttribute( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO );
		// Eliminamos el gestor del formulario del scope de servlet context
		removeObject( request, Constants.GESTOR_FORM_SESSION_KEY + token);
	}
	
	public static void resetGestorFormulario( ServletContext servletContext, String token ) throws Exception
	{
		removeObject( servletContext, token );
	}
	
	public static void storeObject( HttpServletRequest request, String key, Storable storableObject ) throws Exception
	{
		StorageFactory.getInstance().getStorageScope( getServletContext( request ) ).storeObject( key, storableObject );
	}
	
	public static Storable recoverObject( HttpServletRequest request, String key ) throws Exception
	{
		return StorageFactory.getInstance().getStorageScope( getServletContext( request ) ).getObject( key );
	}
	
	
	public static void removeObject( HttpServletRequest request, String key ) throws Exception
	{
		removeObject(  getServletContext( request ), key );

	}
	
	public static void removeObject( ServletContext servletContext, String key ) throws Exception
	{
		StorageFactory.getInstance().getStorageScope( servletContext ).removeObject( key );
	}
	
	private static ServletContext getServletContext( HttpServletRequest request )
	{
		return request.getSession().getServletContext();
	}
	
	

}
