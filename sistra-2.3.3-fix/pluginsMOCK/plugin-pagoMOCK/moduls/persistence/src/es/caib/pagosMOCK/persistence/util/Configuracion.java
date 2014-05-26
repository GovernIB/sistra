package es.caib.pagosMOCK.persistence.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Configuracion {

	private static Log log = LogFactory.getLog(Configuracion.class);
	
	private static Configuracion configuracion = null;
	private Properties props = null;
	
	private Configuracion(){
		// Accedemos a propiedades
		try{
			props = new Properties();
			props.load(new FileInputStream(System.getProperty("ad.path.properties") + "sistra/global.properties"));
		}catch(Exception ex){
			log.error("Error obteniendo propiedades plugin",ex);						
		}	
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
