package es.caib.zonaper.front.util;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.model.DatosSesion;

public class ZonapersFrontRequestHelper
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
    
    public static Principal getPrincipal( HttpServletRequest request )
    {
		return request.getUserPrincipal();
    }
    
    public static DatosSesion getDatosSesion( HttpServletRequest request )
    {
    	return ( DatosSesion ) request.getSession().getAttribute( Constants.DATOS_SESION_KEY );
    }
    
    public static void setDatosSesion( HttpServletRequest request, DatosSesion sesion )
    {
    	request.getSession().setAttribute( Constants.DATOS_SESION_KEY, sesion );
    }

	public static boolean isControlarEntregaNotificaciones(
			HttpServletRequest request) {
		Boolean control = (Boolean) request.getSession().getServletContext().getAttribute(Constants.CONTROLAR_ENTREGA_NOTIFICACIONES);
		return control.booleanValue();
	}
	
	public static boolean isHabilitarApartadoAlertas(
			HttpServletRequest request) {
		Boolean control = (Boolean) request.getSession().getServletContext().getAttribute(Constants.HABILITAR_APARTADO_ALERTAS);
		return control.booleanValue();
	}
}
