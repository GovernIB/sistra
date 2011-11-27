package es.caib.zonaper.back.util;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

import es.caib.zonaper.back.Constants;

public class ZonaperbackRequestHelper
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
    
    public static void setMessage(  HttpServletRequest request, String messageKey )
    {
    	setMessage( request, messageKey, null );
    }
    
    public static void setMessage( HttpServletRequest request, String messageKey, Object [] args )
    {
    	MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
    	request.setAttribute( Constants.MESSAGE_KEY,  resources.getMessage( getLocale( request ), messageKey, args ) );
    }
    
    public static boolean isSetMessage( HttpServletRequest request )
    {
    	return request.getAttribute( Constants.MESSAGE_KEY ) != null;
    }
    
    public static void setMessage( HttpServletRequest request, String messageKey, Object [] args, String action, Map actionParams, String actionLabelKey )
    {
    	setMessage( request, messageKey, args );
    	request.setAttribute( Constants.MESSAGE_ACTION_KEY, action );
    	request.setAttribute( Constants.MESSAGE_ACTION_PARAMS_KEY, actionParams );
    	request.setAttribute( Constants.MESSAGE_ACTION_LABEL_KEY, actionLabelKey );
    }
}
