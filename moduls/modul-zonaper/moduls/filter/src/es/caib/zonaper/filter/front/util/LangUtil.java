package es.caib.zonaper.filter.front.util;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import es.caib.zonaper.filter.front.Constants;

/**
 * Métodos de utilitad para obtener los lenguajes.
 */
public final class LangUtil {

    private LangUtil() {

    }

    public static Locale getLocale( HttpServletRequest request )
    {
    	HttpSession session = request.getSession(true);
        return (Locale) session.getAttribute(Globals.LOCALE_KEY);
    }
    
    public static String getLang(HttpServletRequest request) {
        return getLocale( request ).getLanguage();
    }

    public static String getDefaultLang(HttpServletRequest request) {
        ServletContext context = request.getSession(true).getServletContext();
        String lang = (String) context.getAttribute(Constants.DEFAULT_LANG_KEY);
        return lang;
    }

}
