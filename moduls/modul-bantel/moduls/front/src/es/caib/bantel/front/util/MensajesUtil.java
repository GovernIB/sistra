package es.caib.bantel.front.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

public class MensajesUtil {

	public static MessageResources msg;

	public static void setMsg(MessageResources msg) {
		if(MensajesUtil.msg==null){
			MensajesUtil.msg = msg;
		}
	}
	
	public static String getValue(String key, HttpServletRequest request) {
		Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		if (locale == null) {
			locale = new Locale("es");
		} 
		return getValue(key, locale);
	}
	
	public static String getValue(String key, Locale locale) {
		if(msg!=null)
			return msg.getMessage(locale, key);
		else
			return "";
	}
	
}
