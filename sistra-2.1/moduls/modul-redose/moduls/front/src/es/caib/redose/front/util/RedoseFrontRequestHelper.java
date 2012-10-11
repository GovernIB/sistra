package es.caib.redose.front.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

public class RedoseFrontRequestHelper
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
   
}
