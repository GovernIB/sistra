package es.caib.zonaper.helpdesk.front.plugins;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.persistence.delegate.AuditaDelegate;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.persistence.delegate.SistraDelegate;
import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.util.Util;


/**
 * Plugin que permite acceder a Datos Cacheados de Sistar y Audita.
 */
public class PluginAudita {
	
	private static Log log = LogFactory.getLog(PluginAudita.class);
	
	private static final String SEPARATOR = "#@@#";
	private static final String TIPOS_EVENTOS = "TIPOS_EVENTOS";
	private static final String CODIGO_TRAMITES = "CODIGO_TRAMITES";
	
	private static PluginAudita instance =  null;
	
	public static PluginAudita getInstance()
	{
		if(instance ==  null)
		{
			instance = new PluginAudita();
		}
		return instance;
		
	}
	
	
	/**
	 * Recupera los tipos de eventos
	 * @param idioma
	 * @return identificador generado para poder consultar el dominio 
	 */
	public Map obtenerTiposEventos(String idioma) throws Exception
	{
		String cacheKey = TIPOS_EVENTOS + SEPARATOR + idioma;
		Map result = ( Map ) getFromCache(cacheKey);
        if (result != null) return result;
        
        AuditaDelegate delegate = DelegateAUDUtil.getAuditaDelegate();

        result = delegate.obtenerDescripcionEventos(idioma);
        this.saveToCache( cacheKey, (Serializable) result );
        return result;
	}

	/**
	 * Recupera las descripciones de los tramites
	 * @param idioma
	 * @return Mapa con las descripciones de los tramites 
	 */
	public Map obtenerDescripcionTramites(String idioma) throws Exception
	{
		String cacheKey = CODIGO_TRAMITES + SEPARATOR + idioma;
		Map result = ( Map ) getFromCache(cacheKey);
        if (result != null) return result;
        
        SistraDelegate delegate = DelegateSISTRAUtil.getSistraDelegate();

        result = delegate.obtenerDescripcionTramites(idioma);
        result = Util.sortByKey(result);
        this.saveToCache( cacheKey, (Serializable) result );
        return result;
	}


	
	private static Cache getCache() throws CacheException {
        String cacheName = PluginAudita.class.getName();
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(cacheName)) {
            cache = cacheManager.getCache(cacheName);
        } else {
            // Cache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds)
//            cache = new Cache(cacheName, 1000, false, false, 3000, 300);
            cache = new Cache(cacheName, 1000, false, false, Constants.TIEMPO_EN_CACHE, 300);
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
        String cacheName = PluginAudita.class.getName();
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

