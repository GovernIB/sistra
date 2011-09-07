package es.caib.regtel.persistence.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {
	
	private static Properties propiedades = null;
	
	/**
	 * Lee propiedades de ficheros configuracion
	 * @throws Exception
	 */
	private static void readProperties() throws Exception{
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
