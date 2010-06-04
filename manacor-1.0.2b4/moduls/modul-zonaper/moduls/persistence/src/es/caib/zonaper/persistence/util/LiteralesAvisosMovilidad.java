package es.caib.zonaper.persistence.util;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.StringUtil;
import es.caib.zonaper.model.OrganismoInfo;

/**
 * Clase gestión literales aviso movilidad para internacionalización
 */
public class LiteralesAvisosMovilidad {

	private static Log log = LogFactory.getLog(LiteralesAvisosMovilidad.class);
	
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
		literales = ResourceBundle.getBundle("pad-persistence-messages",currentLocale);
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
			
			// Reemplazamos variables: #ZONA_PERSONAL# 
			OrganismoInfo info = ConfigurationUtil.getInstance().obtenerOrganismoInfo();
			String texto = literales.getString(clave);
			texto = StringUtil.replace(texto,"#ZONA_PERSONAL#",(String) info.getReferenciaPortal().get(idioma));
			
			return texto;
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
			for (int i=0;i<params.length;i++){
				texto = StringUtil.replace(texto,"{"+i+"}",params[i]);
			}
			return texto;
		}catch (Exception e){
			log.error("Literal '" + clave + "' no encontrado");
			return "Literal '" + clave + "' no encontrado";
		}
	}
	
}

