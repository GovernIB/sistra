package es.caib.zonaper.persistence.util;

import java.awt.image.BufferStrategy;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
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
	
	/**
	 * Calcula texto de soporte 
	 * 	
	 * 
	 * @param oi
	 * @param idioma
	 * @return
	 * @throws Exception
	 */
	public static String calcularTextoSoporte(OrganismoInfo oi, String idioma) throws Exception{		
		// Calculamos texto de soporte
		StringBuffer sb = new StringBuffer(300);
		String textoSoporte="";
		String textoAsunto = StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.avisoTramitacion"));
		String urlSoporteFinal = es.caib.util.StringUtil.replace(oi.getUrlSoporteIncidencias(),"@asunto@",textoAsunto);
	    urlSoporteFinal = es.caib.util.StringUtil.replace(urlSoporteFinal,"@idioma@",idioma);	
		if (StringUtils.isNotEmpty(oi.getUrlSoporteIncidencias())){
			if (StringUtils.isNotEmpty(oi.getTelefonoIncidencias())){
				sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteUrlTelefono.1")));
				sb.append("<strong><a href=\"").append(urlSoporteFinal).append("\">");
				sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteUrlTelefono.2")));
				sb.append("</a></strong>");
				sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteUrlTelefono.3")));
				sb.append("<strong>").append(oi.getTelefonoIncidencias()).append("</strong>");
			}else{
				sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteUrl.1")));
				sb.append("<strong><a href=\"").append(urlSoporteFinal).append("\">");
				sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteUrl.2")));
				sb.append("</a></strong>");				
			}				
		}else{
			if (StringUtils.isNotEmpty(oi.getEmailSoporteIncidencias())){
				if (StringUtils.isNotEmpty(oi.getTelefonoIncidencias())){
					sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteEmailTelefono.1")));
					sb.append("<strong>").append(oi.getEmailSoporteIncidencias()).append("</strong>");
					sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteEmailTelefono.2")));
					sb.append("<strong>").append(oi.getTelefonoIncidencias()).append("</strong>");					
				}else{
					sb.append(StringEscapeUtils.escapeHtml(LiteralesAvisosMovilidad.getLiteral(idioma,"aviso.email.cuerpo.soporteEmail.1")));
					sb.append("<strong>").append(oi.getEmailSoporteIncidencias()).append("</strong>");					
				}
			}
		}			
		return sb.toString();
	}
}

