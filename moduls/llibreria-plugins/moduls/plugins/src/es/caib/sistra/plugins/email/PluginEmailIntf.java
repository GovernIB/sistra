package es.caib.sistra.plugins.email;

import es.caib.sistra.plugins.PluginSistraIntf;

/**
 * 
 * Interfaz con env�os Email (consulta de env�os).
 *
 */
public interface PluginEmailIntf  extends PluginSistraIntf {
	
	/**
	 * Consulta el estado de un env�o
	 * <br/>
	 * Esta funcionalidad se podr� implementar si es soportada por el organismo 
	 * @param idEnvio C�digo de env�o
	 * @return EstadoEnvio estado del env�o
	 * @throws Exception
	 */
	public EstadoEnvio consultarEstadoEnvio(String idEnvio) throws Exception;
	
}
