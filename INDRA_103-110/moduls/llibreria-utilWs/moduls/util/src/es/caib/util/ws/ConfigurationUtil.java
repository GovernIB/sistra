package es.caib.util.ws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Accede a la configuracion del modulo
 *
 */
public class ConfigurationUtil {

	private static ConfigurationUtil confUtil = new ConfigurationUtil();
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
		 InputStream fisGlobal=null; 
		 propiedades = new Properties();
         try {
        	 // Path directorio de configuracion
        	 String pathConf = System.getProperty("ad.path.properties");
        	 
         	 // Propiedades globales
        	 fisGlobal = new FileInputStream(pathConf + "sistra/global.properties");
        	 propiedades.load(fisGlobal);
    		        	 	    		 
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
         } finally {
             try{if (fisGlobal != null){fisGlobal.close();}}catch(Exception ex){}             
         }		
	}
	
}
