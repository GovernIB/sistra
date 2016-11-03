package es.caib.redose.persistence.plugin;

import java.util.HashMap;

import es.caib.redose.model.Ubicacion;


/**
 * Clase que me permite cachear las clases de plugins
 *
 */
public class PluginClassCache {
	
		private static PluginClassCache instance;
		private HashMap classCache;
		private PluginClassCache()
		{
			classCache = new HashMap();					
		}
		
		public static PluginClassCache getInstance()
		{
			if ( instance == null )
				instance = new PluginClassCache();
			return instance;
		}
		
		/**
		 * Obtiene instancia del plugin en cuestion
		 * @param as_clase
		 * @return
		 */
		public PluginAlmacenamientoRDS getPluginAlmacenamientoRDS(Ubicacion ubicacion) throws Exception{		
			try{
				String as_clase = ubicacion.getPluginAlmacenamiento();
				Class classDispatcher = ( Class )  classCache.get( as_clase );
				if ( classDispatcher == null )
				{
					classDispatcher = Class.forName(as_clase);
					classCache.put( as_clase, classDispatcher );
				}
				PluginAlmacenamientoRDS plg = ( PluginAlmacenamientoRDS ) classDispatcher.newInstance();
				plg.setCodigoUbicacion(ubicacion.getCodigo());
				return plg;
			}catch(Throwable t){
				throw new Exception("Error creando clase para " + ubicacion.getPluginAlmacenamiento(),t);
			} 
		}

	}

