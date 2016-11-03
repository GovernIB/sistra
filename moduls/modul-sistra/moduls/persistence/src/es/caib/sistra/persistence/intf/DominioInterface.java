package es.caib.sistra.persistence.intf;

import java.util.List;
import es.caib.sistra.modelInterfaz.ValoresDominio;

/**
 * Interface que deben implementar los dominios
 *
 */
public interface DominioInterface {
	
	/**
	 * Obtiene los valores asociados a un dominio
	 * @param id Identificador del dominio
	 * @param parametros Parámetro del dominio (lista de Strings)
	 * @return ValoreDominio
	 */
	public ValoresDominio obtenerDominio(String id, List parametros);
}
