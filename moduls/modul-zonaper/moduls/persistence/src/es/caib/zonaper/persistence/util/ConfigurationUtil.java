package es.caib.zonaper.persistence.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.util.StringUtil;
import es.caib.zonaper.model.OrganismoInfo;

/**
 * Accede a la configuracion del modulo
 *
 */
public class ConfigurationUtil {

	private static Log log = LogFactory.getLog( ConfigurationUtil.class );
	
	private static ConfigurationUtil confUtil = new ConfigurationUtil();
	private static final String PREFIX_SAR = "es.caib.sistra.configuracion.sistra.";
	private Properties propiedades = null;
	private OrganismoInfo organismoInfo = null;
	
	/**
	 * Constructor privado
	 */
	private ConfigurationUtil(){		
	}

	/**
	 * Obtiene singleton
	 * @return singleton
	 */
	public static ConfigurationUtil getInstance(){
		return confUtil;
	}
	
	/**
	 * Obtiene las propiedades de configuracion
	 * @return Propiedades configuracion
	 * @throws Exception
	 */
	public synchronized Properties obtenerPropiedades() throws Exception{
		if (propiedades == null){
			readProperties();
		}
		return propiedades;
	}
	
	/**
	 * Unifica las propiedades del organismo en un objeto
	 * @return Propiedades configuracion
	 * @throws Exception
	 */
	public synchronized OrganismoInfo obtenerOrganismoInfo() throws Exception{
		// Creamos info para el organismo
		if (organismoInfo == null){
			organismoInfo = obtenerOrganismoInfoImpl();	    		
	    }         
		
		return organismoInfo;
		
	}

	private OrganismoInfo obtenerOrganismoInfoImpl() throws Exception {
		if (propiedades == null) obtenerPropiedades();
		OrganismoInfo oi = new  OrganismoInfo();
		oi.setNombre(propiedades.getProperty("organismo.nombre"));
		oi.setUrlLogo(propiedades.getProperty("organismo.logo"));
		oi.setUrlLoginLogo(propiedades.getProperty("organismo.logo.login"));
		oi.setUrlPortal(propiedades.getProperty("organismo.portal.url"));
		oi.setPieContactoHTML(propiedades.getProperty("organismo.footer.contacto"));
		oi.setTelefonoIncidencias(propiedades.getProperty("organismo.soporteTecnico.telefono"));
		oi.setUrlSoporteIncidencias(propiedades.getProperty("organismo.soporteTecnico.url"));
		oi.setEmailSoporteIncidencias(propiedades.getProperty("organismo.soporteTecnico.email"));
		String formularioIncidencias = propiedades.getProperty("organismo.soporteTecnico.formulario");
		if (StringUtils.isNotBlank(formularioIncidencias) && "true".equals(formularioIncidencias)) {
			oi.setFormularioIncidencias(true);
		} else {
			oi.setFormularioIncidencias(false);
		}
		oi.setUrlCssCustom(propiedades.getProperty("organismo.cssCustom"));
		oi.setUrlLoginCssCustom(propiedades.getProperty("organismo.cssLoginCustom"));
		
		
		
		// Obtenemos titulo y referencia a la zona personal
		for (Iterator it=propiedades.keySet().iterator();it.hasNext();){
			String key = (String) it.next();
			if (key.startsWith("organismo.zonapersonal.titulo.")){
				oi.getTituloPortal().put(key.substring(key.lastIndexOf(".") +1),propiedades.get(key));
			}
			if (key.startsWith("organismo.zonapersonal.referencia.")){
				oi.getReferenciaPortal().put(key.substring(key.lastIndexOf(".") +1),propiedades.get(key));
			}
		}
		
		return oi;
	}
	
	
	/**
	 * Unifica las propiedades del organismo en un objeto
	 * @return Propiedades configuracion
	 * @throws Exception
	 */
	public OrganismoInfo obtenerOrganismoInfo(String entidad) throws Exception{
		
		// Obtenemos info por defecto
		OrganismoInfo oi = obtenerOrganismoInfoImpl();
		
		// Sobreescribimos info por entidad
		String valorPropEntidad = null;
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.nombre", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setNombre(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.logo", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlLogo(valorPropEntidad);
		}
				
		valorPropEntidad = obtenerPropiedadEntidad("organismo.logo.login", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlLoginLogo(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.portal.url", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlPortal(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.footer.contacto", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setPieContactoHTML(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.soporteTecnico.telefono", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setTelefonoIncidencias(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.soporteTecnico.url", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlSoporteIncidencias(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.soporteTecnico.formulario", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setFormularioIncidencias(StringUtils.isNotBlank(valorPropEntidad) && "true".equals(valorPropEntidad));
			oi.setUrlLogo(valorPropEntidad);
		}
				
		valorPropEntidad = obtenerPropiedadEntidad("organismo.soporteTecnico.email", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setEmailSoporteIncidencias(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.cssCustom", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlCssCustom(valorPropEntidad);
		}
		
		valorPropEntidad = obtenerPropiedadEntidad("organismo.cssLoginCustom", entidad);
		if (StringUtils.isNotBlank(valorPropEntidad)) {
			oi.setUrlLoginCssCustom(valorPropEntidad);
		}			
		
		return oi;
		
	}
	
	
	private String obtenerPropiedadEntidad(String nomProp, String entidad) {
		String res = null;
		try {
			String nomPropEntidad = StringUtil.replace(nomProp,  "organismo.", "organismo.entidad." + entidad + ".");
			res = propiedades.getProperty(nomPropEntidad);					
		} catch (Exception ex) {
			log.error("Error estableciendo propiedad entidad " + nomProp + ": " + ex.getMessage(), ex);
		}
		return res;
	}
	
	
	/**
	 * Lee las propiedades de los ficheros de configuracion
	 * @throws Exception 
	 *
	 */
	private void readProperties() throws Exception{
		String sar = System.getProperty(PREFIX_SAR + "sar");
		if (sar != null && "true".equals(sar)) {
			readPropertiesFromSAR();
		} else {
			readPropertiesFromFilesystem();
		}
	}

	/**
	 * Lee propiedades desde SAR.
	 * @throws Exception
	 */
	private void readPropertiesFromSAR() throws Exception {
		propiedades = new Properties();
		Properties propSystem = System.getProperties();
		for (Iterator it = propSystem.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = propSystem.getProperty(key);
			if (key.startsWith(PREFIX_SAR + "global")) {
				propiedades.put(key.substring((PREFIX_SAR + "global").length() + 1), value);
			}
			if (key.startsWith(PREFIX_SAR + "zonaper")) {
				propiedades.put(key.substring((PREFIX_SAR + "zonaper").length() + 1), value);
			}			
		}
	}
	
	/**
	 * Lee las propiedades de los ficheros de configuracion
	 * @throws Exception 
	 *
	 */
	private void readPropertiesFromFilesystem() throws Exception{
		 InputStream fisGlobal=null,fisModul=null; 
		 propiedades = new Properties();
         try {
        	 // Path directorio de configuracion
        	 String pathConf = System.getProperty("ad.path.properties");
        	 
         	 // Propiedades globales
        	 fisGlobal = new FileInputStream(pathConf + "sistra/global.properties");
        	 propiedades.load(fisGlobal);
    		 
        	 // Propiedades modulo
    		 fisModul = new FileInputStream(pathConf + "sistra/zonaper.properties");
    		 propiedades.load(fisModul);
        	 	    		 
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
         } finally {
             try{if (fisGlobal != null){fisGlobal.close();}}catch(Exception ex){}
             try{if (fisModul != null){fisModul.close();}}catch(Exception ex){}
         }		
	}
	
}

