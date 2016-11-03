package es.caib.pagosMOCK.persistence.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuracion {

	private static Log log = LogFactory.getLog(Configuracion.class);
	private static final String PREFIX_SAR = "es.caib.sistra.configuracion.sistra.";
	private static Configuracion configuracion = null;
	private Properties props = null;
	
	private Configuracion(){
		// Accedemos a propiedades
		try{
			String sar = System.getProperty(PREFIX_SAR + "sar");
			if (sar != null && "true".equals(sar)) {
				readPropertiesFromSAR();
			} else {
				readPropertiesFromFilesystem();
			}
		}catch(Exception ex){
			log.error("Error obteniendo propiedades configuracion",ex);						
		}			
	}

	/**
	 * Lee propiedades desde SAR.
	 * @throws Exception
	 */
	private void readPropertiesFromSAR() throws Exception {
		props = new Properties();
		Properties propSystem = System.getProperties();
		for (Iterator it = propSystem.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = propSystem.getProperty(key);
			if (key.startsWith(PREFIX_SAR + "global")) {
				props.put(key.substring((PREFIX_SAR + "global").length() + 1), value);
			}					
		}
	}
		
	private void readPropertiesFromFilesystem() throws IOException, FileNotFoundException {
		props = new Properties();
		props.load(new FileInputStream(System.getProperty("ad.path.properties") + "sistra/global.properties"));
	}
	
	public static Configuracion getInstance(){
		if (configuracion == null){
			configuracion = new Configuracion();
		}
		return configuracion;
	}
	
	public String getProperty(String property){
		return props.getProperty(property);
	}
	
}
