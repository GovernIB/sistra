package es.caib.bantel.persistence.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Clase que implementa una cache para optimizar 
 * los lookups de los ejb home de procesamiento de 
 * backoffice 
 * 
 */

public class EjbBackOfficeFactory {

	private final static String JNP_PROTOCOL = "jnp://";
	private final static String HTTP_PROTOCOL = "http";
	
	private final static String JNP_INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	private final static String HTTP_INITIAL_CONTEXT_FACTORY = "org.jboss.naming.HttpNamingContextFactory";
	private final static String URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
	
	private static Log log = LogFactory.getLog(EjbBackOfficeFactory.class);
	
	/**
	 * Map estatica con los homes cacheados
	 */
	private static Map homes = new HashMap();
	
	
	private static EjbBackOfficeFactory factory;
	
	
	private EjbBackOfficeFactory(){		
	}
	
	/**
	 * Obtiene factoria para obtener homes
	 * @return
	 */
	public static EjbBackOfficeFactory getInstance(){
		if (factory == null) factory = new EjbBackOfficeFactory();
		return factory;
	}
	
	/**
	 * Obtiene home
	 * 
	 * @param jndi
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Object getHome(String jndi,String url) throws Exception{
		
		String key = jndi + url;
		Object home = homes.get(key);
		if (home != null) {
			return home;
		}
		else{
			home = lookupHome(jndi,url);			
			homes.put(key,home);			
			return home;
		}
		
	}
		
	//
	//  FUNCIONES DE UTILIDAD
	//
	/**
	 * Obtiene referencia al EJB para procesamiento avanzado
	 * @return
	 * @throws Exception
	 */
	private Object lookupHome(String jndi,String url) throws Exception {		  
	
		if (StringUtils.isEmpty(jndi)) throw new Exception("Debe indicarse el jndi del ejb");
		if (StringUtils.isEmpty(url)) throw new Exception("La url debe ser una url remota o la key LOCAL");
		
		// Obtener Context
		  log.debug( "Obteniendo contexto ..." );
	      InitialContext initialContext = getInitialContext(url);	     	      
	      try {
	    	  // Lookup para obtener home
	    	 log.debug("Lookup ..." );	    	 
	    	 Object objRef = initialContext.lookup(jndi);
	    	 log.debug("Lookup ok" );	   
	         return objRef;
	      } finally {
	    	  if (initialContext != null) initialContext.close();
	      }
	   }
	
	
	
	/**
	 * Obtiene contexto en función de si el ejb está en el servidor local o en el remoto
	 * @return
	 * @throws Exception
	 */
	private InitialContext getInitialContext(String url) throws Exception
	{
		if (url.equals("LOCAL")){
			log.debug( "Acceso EJB local" );			
			InitialContext initialContext = new javax.naming.InitialContext();
			return initialContext;	
		}else{
			log.debug( "Acceso EJB remoto" );			 
			Properties environment = new Properties();
			environment.put( Context.PROVIDER_URL,  url );
			environment.put( Context.INITIAL_CONTEXT_FACTORY, getInitialContextFactory( url ));
			environment.put (Context.URL_PKG_PREFIXES,  URL_PKG_PREFIXES );
			InitialContext initialContext = initialContext = new javax.naming.InitialContext(environment);			
			return initialContext;
		}						
	}
	
	/**
	 * Obtiene protocolo a utilizar
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	private String getInitialContextFactory( String url ) throws Exception
	{
		if (url.startsWith( HTTP_PROTOCOL )) return HTTP_INITIAL_CONTEXT_FACTORY;
		if (url.startsWith( JNP_PROTOCOL )) return JNP_INITIAL_CONTEXT_FACTORY;
		throw new Exception("Url de acceso no valida: " + url + ". Debe ser una url remota (http,https o jnp) o si es local la key LOCAL");		
	}
	
	
	
}
