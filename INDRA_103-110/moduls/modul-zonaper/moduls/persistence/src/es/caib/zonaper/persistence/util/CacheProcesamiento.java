package es.caib.zonaper.persistence.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa una cache para que no se procese
 * el mismo documento a la vez dos veces
 * 
 */
public class CacheProcesamiento {

	private static Map cache = new HashMap();
		
	/**
	 * Almacena documento en cache (indicaría que se esta procesando)
	 * @return Devuelve si ha podido insertarse en cache (true) o al intentar insertarse ya estaba en cache (false)
	 * @param documento
	 */
	public static boolean guardar(String documento){
		synchronized (cache){
			if (cache.containsKey(documento)) return false;
			cache.put(documento,new Date());
			return true;
		}
	}
	
	/**
	 * Borra documentos de cache (indicaría que se han terminado de procesar)
	 * @param documentos
	 */
	public static void borrar(List documentos){	
		synchronized (cache){
			for (int i=0;i<documentos.size();i++){
				if (cache.containsKey(documentos.get(i))) cache.remove(documentos.get(i));
			}
		}
	}
	
	/**
	 * Borra documentos de cache (indicaría que se han terminado de procesar)
	 * @param documentos
	 */
	public static void borrar(String documentos[]){	
		synchronized (cache){
			for (int i=0;i<documentos.length;i++){
				if (cache.containsKey(documentos[i])) cache.remove(documentos[i]);
			}
		}
	}
	
	/**
	 * Borra documento de cache (indicaría que se ha terminado de procesar)
	 * @param documentos
	 */
	public static void borrar(String documento){
		String documentos [] = {documento};
		borrar(documentos);
	}
	
	/**
	 * Busca si la documento existe en la cache (indicaría que actualmente se esta procesando)
	 * @param documento
	 * @return
	 */
	public static boolean existe(String documento){
		return cache.containsKey(documento);
	}
	
}
