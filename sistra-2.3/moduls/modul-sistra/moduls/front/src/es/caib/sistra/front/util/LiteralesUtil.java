package es.caib.sistra.front.util;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.StringUtil;

/**
 * Clase gestión literales aviso movilidad para internacionalización
 */
public class LiteralesUtil {

	private static Log log = LogFactory.getLog(LiteralesUtil.class);
	
	/**
	 * Hashtable que contiene los bundle
	 */
	private static Hashtable literalesResBund = new Hashtable();
	
	/**
	 * Obtiene bundle para idioma
	 * @param idioma Código idioma (es:Castellano/ca:Catalan)
	 * @return
	 */
	public static ResourceBundle getResourceBundle(String idioma){		
		ResourceBundle literales;		
		String ls_key = idioma;
		literales = (ResourceBundle) literalesResBund.get(ls_key);		
		if (literales != null) return literales;				
		Locale currentLocale = new Locale(idioma);
		literales = ResourceBundle.getBundle("sistra-front-messages",currentLocale);
		return literales;
	}
	
	/**
	 * Devuelve el literal para un idioma y una clave
	 * @param idioma	Código idioma
	 * @param clave	Código del literal
	 * @return Literal traducido al idioma correspondiente
	 */
	public static String getLiteral(String idioma,String clave){
		try{
			ResourceBundle literales = getResourceBundle(idioma);
			return literales.getString(clave);
		}catch (Exception t){
			log.error("Literal '" + clave + "' no encontrado");
			return "Literal '" + clave + "' no encontrado";
		}
	}
	
	/**
	 * Devuelve el literal para un idioma y una clave
	 * @param idioma	Código idioma
	 * @param clave	Código del literal
	 * @param params Parámetros a sustituir en el literal {0}, {1}, etc.
	 * @return Literal traducido al idioma correspondiente
	 */
	public static String getLiteral(String idioma,String clave,String params[]){
		try{
			String texto = getLiteral(idioma,clave);
			if (params != null) {
				for (int i=0;i<params.length;i++){
					texto = StringUtil.replace(texto,"{"+i+"}",params[i]);
				}
			}
			return texto;
		}catch (Exception e){
			log.error("Literal '" + clave + "' no encontrado");
			return "Literal '" + clave + "' no encontrado";
		}
	}
	
}

