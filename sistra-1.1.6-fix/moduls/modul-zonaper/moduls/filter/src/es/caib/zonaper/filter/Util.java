package es.caib.zonaper.filter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;

public class Util
{
	public static String getUrl(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        if(request.getQueryString() != null)
        {
            if(!uri.endsWith("?"))
                uri = uri + "?";
            uri = uri + request.getQueryString();
        }
        //uri = uri.substring(request.getContextPath().length(), uri.length());
        uri = uri.substring(0, uri.length());
        return uri;
    }
	
	public static String getLang(HttpServletRequest request) 
	{
        HttpSession session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if ( locale != null )
        	return locale.getLanguage();
        String lang = request.getParameter( "lang" );
        return StringUtils.isEmpty( lang ) ? request.getParameter( "language" ) : lang;
    }
}
