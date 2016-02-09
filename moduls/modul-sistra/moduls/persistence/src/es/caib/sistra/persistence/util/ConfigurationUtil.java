package es.caib.sistra.persistence.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import es.caib.sistra.model.OrganismoInfo;

/**
 * Accede a la configuracion del modulo
 *
 */
public class ConfigurationUtil {

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
				if (propiedades == null) obtenerPropiedades();
				organismoInfo = new  OrganismoInfo();
				organismoInfo.setNombre(propiedades.getProperty("organismo.nombre"));
				organismoInfo.setUrlLogo(propiedades.getProperty("organismo.logo"));
				organismoInfo.setUrlLoginLogo(propiedades.getProperty("organismo.logo.login"));
				organismoInfo.setUrlPortal(propiedades.getProperty("organismo.portal.url"));
				organismoInfo.setPieContactoHTML(propiedades.getProperty("organismo.footer.contacto"));
				organismoInfo.setTelefonoIncidencias(propiedades.getProperty("organismo.soporteTecnico.telefono"));
				organismoInfo.setUrlSoporteIncidencias(propiedades.getProperty("organismo.soporteTecnico.url"));
				organismoInfo.setEmailSoporteIncidencias(propiedades.getProperty("organismo.soporteTecnico.email"));
				organismoInfo.setUrlCssCustom(propiedades.getProperty("organismo.cssCustom"));
				organismoInfo.setUrlLoginCssCustom(propiedades.getProperty("organismo.cssLoginCustom"));
				
				
				
	    		// Obtenemos titulo y referencia a la zona personal
	    		for (Iterator it=propiedades.keySet().iterator();it.hasNext();){
	    			String key = (String) it.next();
	    			if (key.startsWith("organismo.zonapersonal.titulo.")){
	    				organismoInfo.getTituloPortal().put(key.substring(key.lastIndexOf(".") +1),propiedades.get(key));
	    			}
	    			if (key.startsWith("organismo.zonapersonal.referencia.")){
	    				organismoInfo.getReferenciaPortal().put(key.substring(key.lastIndexOf(".") +1),propiedades.get(key));
	    			}
	    		}	    		
	    }         
		
		return organismoInfo;
		
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
			if (key.startsWith(PREFIX_SAR + "sistra")) {
				propiedades.put(key.substring((PREFIX_SAR + "sistra").length() + 1), value);
			}			
		}
	}
	
	/**
	 * Lee propiedades desde filesystem.
	 * @throws Exception
	 */
	private void readPropertiesFromFilesystem() throws Exception {
		InputStream fisGlobal=null,fisModul=null; 
		 propiedades = new Properties();
         try {
        	 // Path directorio de configuracion
        	 String pathConf = System.getProperty("ad.path.properties");
        	 
         	 // Propiedades globales
        	 fisGlobal = new FileInputStream(pathConf + "sistra/global.properties");
        	 propiedades.load(fisGlobal);
    		 
        	 // Propiedades modulo
    		 fisModul = new FileInputStream(pathConf + "sistra/sistra.properties");
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

