package es.caib.sistra.plugins.email;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con envíos Email (consulta de envíos).
 *
 */
public interface PluginEmailIntf  extends PluginSistraIntf {
	
	/**
	 * Consulta el estado de un envío
	 * <br/>
	 * Esta funcionalidad se podrá implementar si es soportada por el organismo 
	 * @param idEnvio Código de envío
	 * @return EstadoEnvio estado del envío
	 * @throws Exception
	 */
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception;
	
}
