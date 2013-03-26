package es.caib.pagos.front.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import es.caib.pagos.front.Constants;
import es.caib.pagos.persistence.delegate.SesionPagoDelegate;

public class PagosFrontRequestHelper
{
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
    
    public static String getLang(HttpServletRequest request) {
        return getLocale(request).getLanguage();
    }
    
    public static SesionPagoDelegate getSesionPago( HttpServletRequest request )
    {
    	return ( SesionPagoDelegate ) request.getSession().getAttribute( Constants.DATOS_SESION_KEY );
    }
    
    public static void setSesionPago( HttpServletRequest request, SesionPagoDelegate sesion )
    {
    	request.getSession().setAttribute( Constants.DATOS_SESION_KEY, sesion );
    }
    
    public static String getUrlRetornoSistra( HttpServletRequest request )
    {
    	return ( String ) request.getSession().getAttribute( Constants.URL_RETORNO_SISTRA_KEY );
    }
    
    public static void setUrlRetornoSistra( HttpServletRequest request, String url )
    {
    	request.getSession().setAttribute( Constants.URL_RETORNO_SISTRA_KEY, url );
    }
    
}
