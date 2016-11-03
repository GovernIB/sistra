package es.caib.bantel.persistence.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Accede a la configuracion de las versiones de los WS del modulo
 *
 */
public class VersionesWSBantel {

	private static VersionesWSBantel confUtil = new VersionesWSBantel();
	private Properties propiedades = null;
	
	/**
	 * Constructor privado
	 */
	private VersionesWSBantel(){		
	}

	/**
	 * Obtiene singleton
	 * @return singleton
	 */
	public static VersionesWSBantel getInstance(){
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
		 propiedades = new Properties();
         try {
    		 propiedades.load(this.getClass().getResourceAsStream("versionesWSBantel.properties"));
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
         }		
	}
	
}
