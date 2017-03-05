package es.caib.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;


public class ContactoUtil {

	private static Map bundles = new ConcurrentHashMap();
	
	public static String generarLiteralContacto(
			String telefono, String email, String url, String asunto,
			boolean formulario, String lang) throws Exception {
		
		// Construimos url de soporte reemplazando variables
		url = es.caib.util.StringUtil.replace(url,"@asunto@", java.net.URLEncoder.encode(asunto, "ISO-8859-1"));
		url = es.caib.util.StringUtil.replace(url,"@idioma@", lang);		    	    
		
		boolean urlEnabled = StringUtils.isNotBlank(url);
		boolean emailEnabled = StringUtils.isNotBlank(email);
		boolean telefonoEnabled = StringUtils.isNotBlank(telefono);
		
		String lanzaFormulario = "javascript:formularioIncidencias();";
		
		String literal = getMessage(lang,"administrador.encabezado") + "<ul>";
		
		if (formulario) {
			literal += getMessage(lang, "administrador.soporteFormulario", lanzaFormulario);
		}
		if (urlEnabled){
			literal += getMessage(lang,"administrador.soporteUrl", url);
		}
		if (emailEnabled){
			literal += getMessage(lang,"administrador.soporteEmail", email);
		}
		if (telefonoEnabled){
			literal += getMessage(lang,"administrador.soporteTelefono", telefono);
		}
		
		literal += "</ul>";
		
		
		return literal;
	}
	
	private static String getMessage(String lang, String key, Object... params) {
		String res = null;
		try {
			ResourceBundle messages = getBundle(lang);
			res = MessageFormat.format(messages.getString(key), params);
		} catch (MissingResourceException e) {
	        res = "!" + key + "!";
	    }
		return res;
	}
	 

	private static ResourceBundle getBundle(String lang) {
		ResourceBundle messages = null;
		if (bundles.containsKey(lang)) {
			messages = (ResourceBundle) bundles.get(lang);
		} else {
			 messages =  ResourceBundle.getBundle(ContactoUtil.class.getName(), new Locale(lang));
			 bundles.put(lang, messages);
		}
		return messages;
	}
	
}
