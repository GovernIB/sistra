package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * Parametros subsanacion
 *
 */
public class ParametrosTramiteSubsanacionPAD implements Serializable 
{	
	private Long expedienteUnidadAdministrativa;
	private String expedienteCodigo;
	private Map parametros;
	
	public String getExpedienteCodigo() {
		return expedienteCodigo;
	}
	public void setExpedienteCodigo(String expedienteCodigo) {
		this.expedienteCodigo = expedienteCodigo;
	}
	public Long getExpedienteUnidadAdministrativa() {
		return expedienteUnidadAdministrativa;
	}
	public void setExpedienteUnidadAdministrativa(
			Long expedienteUnidadAdministrativa) {
		this.expedienteUnidadAdministrativa = expedienteUnidadAdministrativa;
	}
	public Map getParametros() {
		return parametros;
	}
	public void setParametros(Map parametros) {
		this.parametros = parametros;
	}
		
}
