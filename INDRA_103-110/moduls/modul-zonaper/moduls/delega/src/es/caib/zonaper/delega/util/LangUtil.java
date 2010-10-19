package es.caib.zonaper.delega.util;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;

import es.caib.zonaper.delega.Constants;

/**
 * Métodos de utilitad para obtener los lenguajes.
 */
public final class LangUtil {

    private LangUtil() {

    }

    public static String getLang(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        return locale.getLanguage();
    }

    public static String getDefaultLang(HttpServletRequest request) {
        ServletContext context = request.getSession(true).getServletContext();
        String lang = (String) context.getAttribute(Constants.DEFAULT_LANG_KEY);
        return lang;
    }

}
