package es.caib.sistra.persistence.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Accede a la configuracion de las versiones de los WS del modulo
 *
 */
public class VersionesWSSistra {

	private static VersionesWSSistra confUtil = new VersionesWSSistra();
	private Properties propiedades = null;
	
	/**
	 * Constructor privado
	 */
	private VersionesWSSistra(){		
	}

	/**
	 * Obtiene singleton
	 * @return singleton
	 */
	public static VersionesWSSistra getInstance(){
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
    		 propiedades.load(getClass().getResourceAsStream("versionesWSSistra.properties"));
        	 	    		 
         } catch (Exception e) {
        	 propiedades = null;
             throw new Exception("Excepcion accediendo a las propiedadades del modulo", e);
         }		
	}
	
	
	public static void main(String[] s){
		try {
			System.out.println(VersionesWSSistra.getInstance().obtenerPropiedades().values());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
