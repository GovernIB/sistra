package es.caib.sistra.front.util;

import java.security.Principal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.ParametrosMensaje;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.plugins.PluginFactory;

public class TramiteRequestHelper
{
	public static String getLang(HttpServletRequest request) {
        return getLocale(request).getLanguage();
    }
    
	public static Principal getPrincipal( HttpServletRequest request )
    {
		return ( Principal ) request.getUserPrincipal();
    }
    
	public static char getMetodoAutenticacion( HttpServletRequest request ) throws Exception
    {
		Principal principal = getPrincipal( request );
    	return PluginFactory.getInstance().getPluginLogin().getMetodoAutenticacion( principal );
    }
    
	public static void setRespuestaFront( HttpServletRequest request, RespuestaFront respuestaFront ) throws Exception
    {
    	TramiteFront tramite = respuestaFront.getInformacionTramite();
		HashMap returnedParams = respuestaFront.getParametros();
		MensajeFront message = respuestaFront.getMensaje();
		
		request.setAttribute( Constants.RESPUESTA_FRONT_KEY, respuestaFront );
		if ( tramite != null )
		{
			request.setAttribute( Constants.TRAMITE_KEY, tramite );
			request.setAttribute( Constants.DESCRIPCION_TRAMITE_PARAMS_KEY, tramite.getDescripcion() );
			request.setAttribute( Constants.DIAS_PERSISTENCIA_PARAMS_KEY, String.valueOf( tramite.getDiasPersistencia() ));
			request.setAttribute( Constants.DATOS_SESION_PARAMS_KEY, tramite.getDatosSesion() );
		}
		if ( message != null ){
			request.setAttribute( Constants.MENSAJE_KEY, message );
			// TODO Podría pasarse desde persistence parametros para controlar la redirección
			request.setAttribute( Constants.MENSAJE_PARAM, new ParametrosMensaje() ); 
		}
		if ( returnedParams != null )
			request.setAttribute( Constants.PARAMS_KEY, returnedParams );
		
		request.setAttribute( Constants.PRINCIPAL_KEY, getPrincipal( request ) );
		request.setAttribute( Constants.MODO_AUTENTICACION_KEY, String.valueOf( getMetodoAutenticacion( request ) ));
    }
    
	public static RespuestaFront getRespuestaFront( HttpServletRequest request )
    {
    	return ( RespuestaFront ) request.getAttribute( Constants.RESPUESTA_FRONT_KEY );
    }
    
	public static TramiteFront getTramiteFront( HttpServletRequest request )
    {
    	return ( TramiteFront ) request.getAttribute( Constants.TRAMITE_KEY );
    }
    
	public static Map getParametros( HttpServletRequest request )
    {
    	return ( Map ) request.getAttribute( Constants.PARAMS_KEY );
    }
    
	public static MensajeFront getMessage( HttpServletRequest request )
    {
    	return ( MensajeFront ) request.getAttribute( Constants.MENSAJE_KEY );
    }
    
	// -- Mensajes de Tipo Warning
	public static void setWarnMessage( HttpServletRequest request, String messageKey, Object args[],ParametrosMensaje param )
    {
    	setMessage( request, messageKey, MensajeFront.TIPO_WARNING, args,param, null );
    }
	
	public static void setWarnMessage( HttpServletRequest request, String messageKey, Object args[] )
    {
		setWarnMessage(request, messageKey, args ,null);
    }
    
	public static void setWarnMessage( HttpServletRequest request, String messageKey )
    {
    	setWarnMessage( request, messageKey, null ,null);
    }
	
	public static void setWarnMessage( HttpServletRequest request, String messageKey,ParametrosMensaje param )
    {
    	setWarnMessage( request, messageKey, null,param );
    }
    
	// -- Mensajes de Tipo Error
	public static void setErrorMessage( HttpServletRequest request, String errorMessageKey, Object args[],ParametrosMensaje param, String exceptionMessage )
    {
    	setMessage( request, errorMessageKey, MensajeFront.TIPO_ERROR, args, param, exceptionMessage );
    }
	
	public static void setErrorMessage( HttpServletRequest request, String errorMessageKey, Object args[],ParametrosMensaje param )
    {
    	setMessage( request, errorMessageKey, MensajeFront.TIPO_ERROR, args, param, null );
    }
	
	public static void setErrorMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
		setErrorMessage( request, errorMessageKey, args,null);
    }
    
	public static void setErrorMessage( HttpServletRequest request, String errorMessageKey,ParametrosMensaje param )
    {
    	setErrorMessage( request, errorMessageKey, null,null );
    }
	
	public static void setErrorMessage( HttpServletRequest request, String errorMessageKey )
    {
    	setErrorMessage( request, errorMessageKey, null ,null);
    }
	
	// -- Mensajes de Tipo Error Recuperable
	public static void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey, Object args[] ,ParametrosMensaje param )
    {
    	setMessage( request, errorMessageKey, MensajeFront.TIPO_ERROR_CONTINUABLE, args,param, null );
    }
	
	public static void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
		setErrorRecoverableMessage( request, errorMessageKey, args , null );
    }
	
	public static void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey  ,ParametrosMensaje param )
    {
    	setErrorRecoverableMessage( request, errorMessageKey, null,param );
    }
    
	public static void setErrorRecoverableMessage( HttpServletRequest request, String errorMessageKey )
    {
    	setErrorRecoverableMessage( request, errorMessageKey, null,null );
    }
	
	
	// -- Mensajes de Tipo Información
	public static void setInfoMessage( HttpServletRequest request, String errorMessageKey, Object args[],ParametrosMensaje param )
    {
    	setMessage( request, errorMessageKey, MensajeFront.TIPO_INFO, args ,param, null);
    }
	
	public static void setInfoMessage( HttpServletRequest request, String errorMessageKey, Object args[] )
    {
		setInfoMessage( request, errorMessageKey, args,null );
    }
    
	public static void setInfoMessage( HttpServletRequest request, String errorMessageKey,ParametrosMensaje param )
    {
    	setInfoMessage( request, errorMessageKey, null,param );
    }
	
	public static void setInfoMessage( HttpServletRequest request, String errorMessageKey )
    {
    	setInfoMessage( request, errorMessageKey, null,null);
    }
    
    /**
     * establece un mensaje multiidioma que sera obtenido del fichero sistra-front-messages.properties
     * @param request
     * @param messageKey
     * @param tipoMensaje
     */
    private static void setMessage( HttpServletRequest request, String messageKey, int tipoMensaje, Object args[],ParametrosMensaje param, String exceptionMessage )
    {
    	MensajeFront mensaje = new MensajeFront();
		//mensaje.setTipo( MensajeFront.TIPO_ERROR );
		//mensaje.setMensaje( "EJBException in method: public abstract void javax.ejb.EJBLocalObject.remove()" );
		mensaje.setTipo( tipoMensaje );
		//MessageResources resources = this.getResources( request );
		MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
		mensaje.setMensaje( resources.getMessage( getLocale( request ), messageKey, args ) );
		mensaje.setMensajeExcepcion(exceptionMessage);
		request.setAttribute( Constants.MENSAJE_KEY, mensaje );
		if (param != null) {
			request.setAttribute( Constants.MENSAJE_PARAM, param );
		}else{
			request.setAttribute( Constants.MENSAJE_PARAM, new ParametrosMensaje() );
		}		
    }
    
    public static boolean isSetMessage( HttpServletRequest request )
    {
    	return request.getAttribute( Constants.MENSAJE_KEY ) != null;
    }
    
    public static boolean isSetError( HttpServletRequest request )
    {
    	return isSetMessage( request ) 
    		&& ( getMessage( request ).getTipo() == MensajeFront.TIPO_ERROR 
    			|| getMessage( request ).getTipo() == MensajeFront.TIPO_ERROR_CONTINUABLE ); 
    }
    
    public static boolean isErrorHandled( HttpServletRequest request )
    {
    	return request.getAttribute( Constants.ERROR_TRATADO_KEY ) != null;
    }
    
    public static void setErrorHandled( HttpServletRequest request )
    {
    	request.setAttribute( Constants.ERROR_TRATADO_KEY, "true" );
    }
    
    /**
     * Return the user's currently selected Locale.
     * @param request The request we are processing
     */
    public static Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return (locale);
    }
    
}
