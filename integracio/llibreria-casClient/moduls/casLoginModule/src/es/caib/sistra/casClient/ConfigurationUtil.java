package es.caib.sistra.casClient;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Accede a la configuracion del modulo
 *
 */
public class ConfigurationUtil {

	private static ConfigurationUtil confUtil = new ConfigurationUtil();
	private static final String PREFIX_SAR_SISTRA = "es.caib.sistra.configuracion.sistra.";
	private static final String PREFIX_SAR_PLUGINS = "es.caib.sistra.configuracion.plugins.";
	private Properties propiedades = null;
	
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
	 * Lee las propiedades de los ficheros de configuracion
	 * @throws Exception 
	 *
	 */
	private void readProperties() throws Exception{
		String sar = System.getProperty(PREFIX_SAR_SISTRA + "sar");
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
			if (key.startsWith(PREFIX_SAR_SISTRA + "global")) {
				propiedades.put(key.substring((PREFIX_SAR_SISTRA + "global").length() + 1), value);
			}
			if (key.startsWith(PREFIX_SAR_PLUGINS + "plugin-login")) {
				propiedades.put(key.substring((PREFIX_SAR_PLUGINS + "plugin-login").length() + 1), value);
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
    		 fisModul = new FileInputStream(pathConf + "sistra/plugins/plugin-login.properties");
    		 propiedades.load(fisModul);
        	 	    		 
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades de login", e);
         } finally {
             try{if (fisGlobal != null){fisGlobal.close();}}catch(Exception ex){}
             try{if (fisModul != null){fisModul.close();}}catch(Exception ex){}
         }		
	}
	
}
