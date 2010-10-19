package es.caib.sistra.persistence.plugins;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.sql.DataSource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.intf.DominioEJB;
import es.caib.sistra.persistence.intf.DominioEJBHome;
import es.caib.sistra.persistence.util.UsernamePasswordCallbackHandler;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
import es.caib.util.CifradoUtil;
import es.caib.util.EjbUtil;

/**
 * Plugin que permite acceder a dominios
 */
public class PluginDominio {
	
	private static Log log = LogFactory.getLog(PluginDominio.class);
	
	private static final String SEPARATOR = "#@@#";
	
	private Hashtable dominios = new Hashtable();
	private Hashtable parametrosDominios = new Hashtable();
	private Hashtable valoresDominios = new Hashtable();
		
	/**
	 * Crea instancia de un determinado dominio
	 * @param idDominio Identificador dominio
	 * @return Clave para poder acceder al dominio a través del plugin
	 */
	public String crearDominio(String idDominio){				
		String ls_clave = generarClave();
		ArrayList parametros = new ArrayList();
		dominios.put(ls_clave,idDominio);		
		parametrosDominios.put(ls_clave,parametros);
		return ls_clave;
	}
	
	/**
	 * Establece parametro para el dominio
	 * 
	 * @param claveDominio
	 * @param parametro
	 * @throws Exception
	 */
	public void establecerParametro(String claveDominio,String parametro) throws Exception{
		// Comprobamos que existe dominio
		if (!dominios.containsKey(claveDominio)) throw new Exception("No existe instancia del dominio");
		// Establecemos parametro
		((List) parametrosDominios.get(claveDominio)).add(parametro);
	}
	
	/**
	 * Recupera los valores de un dominio (previamente debe haberse creado)
	 * @param claveDominio Clave del dominio generado
	 * @return identificador generado para poder consultar el dominio 
	 */
	public void recuperaDominio(String claveDominio) throws Exception
	{
		// Comprobamos que existe dominio
		if (!dominios.containsKey(claveDominio)) throw new Exception("No existe instancia del dominio");				
		
		// Obtenemos identificador dominio y parametros
		String idDominio = (String) dominios.get(claveDominio);
		List parametros = (List) parametrosDominios.get(claveDominio);
						
		// Recuperamos datos dominio y los almacemos
		ValoresDominio dominio = resuelveDominio( idDominio,parametros);				
		dominios.put(claveDominio,dominio);		
	}
		
	/**
	 * Devolvemos dominio almacenado con la clave indicada
	 * @return Devuelve dominio.
	 */
	public ValoresDominio getValoresDominio(String claveDominio)throws Exception{	
		// Comprobamos que existe dominio
		if (!dominios.containsKey(claveDominio)) throw new Exception("No existe instancia del dominio");
		return (ValoresDominio) dominios.get(claveDominio);
	}
	
	/**
	 * Borra dominio
	 * @param idDom
	 */
	public void removeDominio(String claveDominio) throws Exception{	
		// Comprobamos que existe dominio
		if (!dominios.containsKey(claveDominio)) throw new Exception("No existe instancia del dominio");
		dominios.remove(claveDominio);
		parametrosDominios.remove(claveDominio);
		if (valoresDominios.containsKey(claveDominio)) valoresDominios.remove(claveDominio);		
	}
	
	
	// --- FUNCIONES AUXILIARES ----
	private String generarClave(){
		String idDom = Long.toString(System.currentTimeMillis());		
		return idDom;
	}
	
	private ValoresDominio resuelveDominio(String idDominio,List parametros) throws Exception{		
		// Obtenemos informacion dominio
		String url = "";
		String cacheKey =  null;
		Dominio dominio;
		try{
			log.debug("Accediendo a dominio " + idDominio);
			dominio = DelegateUtil.getDominioDelegate().obtenerDominio(idDominio);
			if (dominio == null) throw new Exception("No se devuelve dominio");			
		}catch(Exception e){
			throw new Exception("No se puede encontrar dominio con id " + idDominio ,e);
		}
		try{
			url = dominio.getUrl();
    		if(url != null && !"".equals(url)){
    			url = StringUtils.replace(url,"@backoffice.url@",DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("backoffice.url"));
    		}
		}catch(Exception e){
			throw new Exception("No existe la propiedad backoffice.url en el global.properties.");
		}
		// Comprobamos si el dominio es cacheable y tiene los datos cacheados
		if ( dominio.getCacheable() == 'S' )
		{	
			cacheKey = idDominio + SEPARATOR + parametros.hashCode();
			ValoresDominio cached = ( ValoresDominio ) getFromCache(cacheKey);
	        if (cached != null) return cached;
		}
						
		// Accedemos a dominio
		ValoresDominio valoresDominio = null;
		switch (dominio.getTipo())
		{
			case Dominio.DOMINIO_EJB:
				valoresDominio = resuelveDominioEJB(dominio,parametros,url);
				break;
			case Dominio.DOMINIO_WEBSERVICE:
				valoresDominio = resuelveDominioWS(dominio,parametros,url);
				break;
			case Dominio.DOMINIO_SQL:
				valoresDominio = resuelveDominioSQL(dominio,parametros,url);
				break;
			default:
				throw new Exception("Tipo de dominio no soportado: " + dominio.getTipo());
		}
		
		// Controlamos que el dominio devuelve datos nulos
		if (valoresDominio == null) throw new Exception("Dominio " + dominio.getIdentificador() + " devuelve datos nulos");
		
		// Comprobamos si debe cachearse (si es cacheable y no tiene error)
		if ( dominio.getCacheable() == 'S' && !valoresDominio.isError() )
		{
			this.saveToCache( cacheKey, valoresDominio );
		}
		
		return valoresDominio;
	}
	
	private ValoresDominio resuelveDominioEJB(Dominio dominio,List parametros, String url) throws Exception
	{
		log.debug("Accedemos a Dominio EJB");
		LoginContext lc = null;
		String jndiName = dominio.getJNDIName();
		DominioEJBHome home = null;
		try
		{
			if ( dominio.isSecured() )
			{	
				CallbackHandler handler = null; 
				
				switch (dominio.getAutenticacionExplicita()){
					// Autenticacion implicita
					case Dominio.AUTENTICACION_EXPLICITA_SINAUTENTICAR:
						break;
					// Autenticacion explicita a traves de usuario/password
					case Dominio.AUTENTICACION_EXPLICITA_ESTANDAR:
						log.debug("Autenticación explicita a traves de usuario/password");
						String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
						String user = CifradoUtil.descifrar(claveCifrado,dominio.getUsr());
						String pass = CifradoUtil.descifrar(claveCifrado,dominio.getPwd());
						handler = new UsernamePasswordCallbackHandler( user, pass );
						break;
                    // Autenticacion explicita a traves de plugin autenticacion organismo
					case Dominio.AUTENTICACION_EXPLICITA_ORGANISMO:
						log.debug("Autenticación explicita a traves de plugin autenticacion organismo");
						AutenticacionExplicitaInfo authInfo = null;
						try{
							authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo();
							log.debug("Usuario plugin autenticacion organismo: " + authInfo.getUser());
						}catch (Exception ex){
							throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
						}						
						handler = new UsernamePasswordCallbackHandler( authInfo.getUser(), authInfo.getPassword() );
						break;
				}
								
				lc = new LoginContext("client-login", handler);
			    lc.login();
			}
			
			home = ( DominioEJBHome ) lookupHome( dominio, jndiName, url, DominioEJBHome.class );
			DominioEJB dominioEJB = home.create();
			return dominioEJB.obtenerDominio( dominio.getIdentificador(), parametros );
		}
		catch( Exception exc )
		{
			log.error( exc );
			throw exc;
		}
		finally
		{
			if ( lc != null )
			{
				lc.logout();
			}
		}
	}
	
			
	private Object lookupHome( Dominio dominio, String jndiName,String url, Class narrowTo) throws Exception {
		
		if (dominio.getLocalizacionEJB() == Dominio.EJB_LOCAL){
			log.debug("Acceso local a " + dominio.getJNDIName());
		}else{
			log.debug("Acceso remoto a " + dominio.getJNDIName() + " [" + url + "]");
		}
		
		return EjbUtil.lookupHome(dominio.getJNDIName(),(dominio.getLocalizacionEJB() == Dominio.EJB_LOCAL),url,narrowTo);  		
	}
		
	private ValoresDominio resuelveDominioWS(Dominio dominio,List parametros, String url) throws Exception
	{
		log.debug("Accedemos a Dominio WS");
		
		// Obtenemos informacion de autenticacion
		String user=null,pass=null;
		switch (dominio.getAutenticacionExplicita()){
			// Autenticacion implicita
			case Dominio.AUTENTICACION_EXPLICITA_SINAUTENTICAR:
				break;
			// Autenticacion explicita a traves de usuario/password
			case Dominio.AUTENTICACION_EXPLICITA_ESTANDAR:
				log.debug("Autenticación explicita a traves de usuario/password");
				String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
				user = CifradoUtil.descifrar(claveCifrado,dominio.getUsr());
				pass = CifradoUtil.descifrar(claveCifrado,dominio.getPwd());
				break;
	        // Autenticacion explicita a traves de plugin autenticacion organismo
			case Dominio.AUTENTICACION_EXPLICITA_ORGANISMO:
				log.debug("Autenticación explicita a traves de plugin autenticacion organismo");
				AutenticacionExplicitaInfo authInfo = null;
				try{
					authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo();
					log.debug("Usuario plugin autenticacion organismo: " + authInfo.getUser());
				}catch (Exception ex){
					throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
				}				
				user = authInfo.getUser();
				pass = authInfo.getPassword();			
				break;
		}
		
		if(dominio.getVersionWS() != null && "v1".equals(dominio.getVersionWS())){
			return es.caib.sistra.wsClient.v1.client.ClienteWS.obtenerDominio(url,user,pass,dominio.getIdentificador(),parametros);
		}else if(dominio.getVersionWS() != null && "v2".equals(dominio.getVersionWS())){
			return es.caib.sistra.wsClient.v2.client.ClienteWS.obtenerDominio(url,user,pass,dominio.getIdentificador(),parametros);
		}else{
			throw new Exception("Excepcion obteniendo la versión "+dominio.getVersionWS()+" del WS de obtención de dominios. ");
		}
		       		
	}
	
	
	private ValoresDominio resuelveDominioSQL(Dominio dominio,List parametros, String url)throws Exception{
		log.debug("Dominio SQL");
		// Obtenemos datasource
		DataSource datasource;
		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			datasource  = ( javax.sql.DataSource ) ctx.lookup("java:/" + url);
	      } catch( Exception e ) {
	              throw new Exception( "Error consiguiendo conexion : "+e.toString() );
	      }	  
	      
	   
	    try{	    	  
	    	// Obtenemos conexion
	    	con = datasource.getConnection();
			// Creamos sentencia
		    PreparedStatement stmt = con.prepareStatement(dominio.getSql());
		    stmt.setQueryTimeout(60);
	        			      
			// Establecemos parametros
		    for (int i=0;i<parametros.size();i++){
		    	stmt.setString(i+1,(String) parametros.get(i));
		    }
		    
		    // Ejecutamos sentencia
		    stmt.execute();
		    ResultSet rs = stmt.getResultSet();
		    	    
			// Creamos ValoresDominio
			ValoresDominio val = new ValoresDominio();
			int numCols = rs.getMetaData().getColumnCount();
			String ls_valor;
			String ls_columnas[] = new String[numCols];		
			for (int i=0;i<numCols;i++){
				ls_columnas[i] = rs.getMetaData().getColumnName(i+1);
			}		
			Object l_valor;
			int fila=0;		
			while (rs.next()){
				fila = val.addFila();
				for (int i=0;i<numCols;i++){					
					l_valor = rs.getObject(i + 1);
					if (!rs.wasNull()){
						ls_valor = l_valor.toString();
					}else{
						ls_valor = null;
					}
					val.setValor(fila,ls_columnas[i],ls_valor);				
				}
			}
			log.debug("Dominio resuelto");
			return val;
	    }finally{
	    	if (con != null){
	    		try{con.close();}catch(Exception e){}
	    	}
	    }
	}
	
	private static Cache getCache() throws CacheException {
        String cacheName = PluginDominio.class.getName();
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(cacheName)) {
            cache = cacheManager.getCache(cacheName);
        } else {
            // Cache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds)
//            cache = new Cache(cacheName, 1000, false, false, 3000, 300);
            cache = new Cache(cacheName, 1000, false, false, ConstantesSTR.TIEMPO_EN_CACHE, 300);
            cacheManager.addCache(cache);
        }
        return cache;
    }

    protected Serializable getFromCache(Serializable key) throws CacheException {
        Cache cache = getCache();
        Element element = cache.get(key);
        if (element != null && !cache.isExpired(element)) {
            return element.getValue();
        } else {
            return null;
        }
    }

    protected void saveToCache(Serializable key, Serializable value) throws CacheException {
        Cache cache = getCache();
        cache.put(new Element(key, value));
    }
	
	
    public static void limpiarCache(String idDominio) throws CacheException{
    	log.debug("Queremos borrarCache: " + idDominio);
        String cacheName = PluginDominio.class.getName();
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(cacheName)) {
            cache = cacheManager.getCache(cacheName);
            List keys = cache.getKeys();
        	log.debug("Queremos borrarCache: " + idDominio + " que tiene " + keys.size() + " elementos.");
    		for (Iterator it=keys.iterator();it.hasNext();){
    			String key = (String) it.next();
    			int idx = key.indexOf(SEPARATOR);
    			if(idx != -1) cache.remove(key);
    		}
        }

    }
    
}

