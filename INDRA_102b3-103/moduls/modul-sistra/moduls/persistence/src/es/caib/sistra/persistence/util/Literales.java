package es.caib.sistra.persistence.util;

import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.OrganismoInfo;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.util.StringUtil;

/**
 * Clase gestión literales para internacionalización
 */
public class Literales {

	private static Log log = LogFactory.getLog(Literales.class);
	
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
		literales = ResourceBundle.getBundle("sistra-persistence-messages",currentLocale);
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
			// Obtenemos literal del fichero
			ResourceBundle literales = getResourceBundle(idioma);
			String literal = literales.getString(clave);
			
			// Obtenemos info organismo
			OrganismoInfo oi = DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo();
			
			// Reemplazamos variables: #ZONA_PERSONAL#
			literal = StringUtil.replace(literal,"#ZONA_PERSONAL#",(String) oi.getReferenciaPortal().get(idioma));
			
			return literal;
			
		}catch (Throwable t){
			log.error("Literal '" + clave + "' no encontrado");
			return "Literal '" + clave + "' no encontrado";
		}
	}
	
}

