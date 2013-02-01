package es.caib.mobtratel.persistence.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa una cache para que no se procese
 * la misma entrada a la vez dos veces
 * 
 */
public class CacheProcesamiento {

	private static Map cache = new HashMap();
		
	/**
	 * Almacena entrada en cache (indicaría que se esta procesando)
	 * @return Devuelve si ha podido insertarse en cache (true) o al intentar insertarse ya estaba en cache (false)
	 * @param entradas
	 */
	public static boolean guardar(String entrada){
		synchronized (cache){
			if (cache.containsKey(entrada)) return false;
			cache.put(entrada,new Date());
			return true;
		}
	}
	
	/**
	 * Borra entradas de cache (indicaría que se han terminado de procesar)
	 * @param entradas
	 */
	public static void borrar(List entradas){	
		synchronized (cache){
			for (int i=0;i<entradas.size();i++){
				if (cache.containsKey(entradas.get(i))) cache.remove(entradas.get(i));
			}
		}
	}
	
	/**
	 * Borra entradas de cache (indicaría que se han terminado de procesar)
	 * @param entradas
	 */
	public static void borrar(String entradas[]){	
		synchronized (cache){
			for (int i=0;i<entradas.length;i++){
				if (cache.containsKey(entradas[i])) cache.remove(entradas[i]);
			}
		}
	}
	
	/**
	 * Borra entrada de cache (indicaría que se ha terminado de procesar)
	 * @param entradas
	 */
	public static void borrar(String entrada){
		String entradas [] = {entrada};
		borrar(entradas);
	}
	
	/**
	 * Busca si la entrada existe en la cache (indicaría que actualmente se esta procesando)
	 * @param entrada
	 * @return
	 */
	public static boolean existe(String entrada){
		return cache.containsKey(entrada);
	}
	
}
