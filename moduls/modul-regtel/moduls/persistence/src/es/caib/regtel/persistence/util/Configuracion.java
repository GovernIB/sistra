package es.caib.regtel.persistence.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class Configuracion {
	
	private static Properties propiedades = null;
	private static final String PREFIX_SAR = "es.caib.sistra.configuracion.sistra.";
	
	/**
	 * Lee las propiedades de los ficheros de configuracion
	 * @throws Exception 
	 *
	 */
	private static void readProperties() throws Exception{
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
	private static void readPropertiesFromSAR() throws Exception {
		propiedades = new Properties();
		Properties propSystem = System.getProperties();
		for (Iterator it = propSystem.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = propSystem.getProperty(key);
			if (key.startsWith(PREFIX_SAR + "global")) {
				propiedades.put(key.substring((PREFIX_SAR + "global").length() + 1), value);
			}
			if (key.startsWith(PREFIX_SAR + "regtel")) {
				propiedades.put(key.substring((PREFIX_SAR + "regtel").length() + 1), value);
			}			
		}
	}
	
	/**
	 * Lee las propiedades de los ficheros de configuracion
	 * @throws Exception 
	 *
	 */
	private static void readPropertiesFromFilesystem() throws Exception{
		 InputStream fisGlobal=null,fisModul=null; 
		 propiedades = new Properties();
         try {
        	 // Path directorio de configuracion
        	 String pathConf = System.getProperty("ad.path.properties");
        	 
         	 // Propiedades globales
        	 fisGlobal = new FileInputStream(pathConf + "sistra/global.properties");
        	 propiedades.load(fisGlobal);
    		 
        	 // Propiedades modulo
    		 fisModul = new FileInputStream(pathConf + "sistra/regtel.properties");
    		 propiedades.load(fisModul);
        	 	    		 
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
         } finally {
             try{if (fisGlobal != null){fisGlobal.close();}}catch(Exception ex){}
             try{if (fisModul != null){fisModul.close();}}catch(Exception ex){}
         }				
	}
	
	public static String getProperty(String prop) throws Exception{
		if (propiedades == null) readProperties();
		return propiedades.getProperty(prop);
	}
	
}
