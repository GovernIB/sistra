package es.caib.redose.persistence.plugin;

/**
 * Interface que deben cumplir los plugin de
 * almacenamiento
 *
 */
public interface PluginAlmacenamientoRDS {
	public void guardarFichero(Long id,byte[] datos) throws Exception;	
	public byte[] obtenerFichero(Long id) throws Exception;
	public void eliminarFichero(Long id)  throws Exception;	
}