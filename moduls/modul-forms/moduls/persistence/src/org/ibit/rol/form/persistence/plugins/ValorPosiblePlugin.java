package org.ibit.rol.form.persistence.plugins;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.ValorPosible;

/**
 * Clase amb infrastrucutra que poden sobreescriure les classes destinades
 * a generar valors posibles.
 * @see TestValorPosiblePlugin
 */
public abstract class ValorPosiblePlugin {

	// Tiempo en cache (Segundos)
	protected long tiempoEnCache = 600;
	
    protected ValorPosible crearValorPosible(String valor, String lang, String nom) {
        final ValorPosible vp = new ValorPosible();
        vp.setDefecto(false);
        vp.setValor(valor);
        final TraValorPosible trVp = new TraValorPosible();
        trVp.setEtiqueta(nom);
        vp.setTraduccion(lang, trVp);
        vp.setCurrentLang(lang);
        return vp;
    }
    
    protected ValorPosible crearValorPosible(String valor, String lang, String nom,String parentValor) {
    	final ValorPosible vp = crearValorPosible(valor,lang,nom);
    	vp.setParentValor(parentValor);
        return vp;
    }

    protected ValorPosible crearValorPosible(String valor, String lang, String nom, Archivo arxiu) {
        final ValorPosible vp = crearValorPosible(valor, lang, nom);
        ((TraValorPosible) vp.getTraduccion(lang)).setArchivo(arxiu);
        return vp;
    }

    private Cache getCache() throws CacheException {
        String cacheName = this.getClass().getName();
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache;
        if (cacheManager.cacheExists(cacheName)) {
            cache = cacheManager.getCache(cacheName);
        } else {
            // Cache(String name, int maxElementsInMemory, boolean overflowToDisk, boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds)
//            cache = new Cache(cacheName, 1000, false, false, 3000, 300);
        	cache = new Cache(cacheName, 1000, false, false, tiempoEnCache, tiempoEnCache);
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

    public abstract Object execute(String lang) throws Exception;



}
