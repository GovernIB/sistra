package es.caib.sistra.persistence.plugins;

/**
 * Destinatario del tramite  
 */
public class DestinatarioTramite {
	
	private boolean calculado;
	
	private String oficinaRegistral;
	
	private String organoDestino;
	
	private String unidadAdministrativa;

	public String getOficinaRegistral() {
		return oficinaRegistral;
	}

	public void setOficinaRegistral(String oficinaRegistral) {
		this.oficinaRegistral = oficinaRegistral;
	}

	public String getOrganoDestino() {
		return organoDestino;
	}

	public void setOrganoDestino(String organoDestino) {
		this.organoDestino = organoDestino;
	}

	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public boolean isCalculado() {
		return calculado;
	}

	public void setCalculado(boolean calculado) {
		this.calculado = calculado;
	}
	
	
}
