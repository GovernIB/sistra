package es.caib.zonaper.model;

import java.io.Serializable;

public class PeticionImpresionSello implements Serializable {

	private Long codigoEntradaPreregistro;
	
	private String codigoOficinaRegistro;

	public Long getCodigoEntradaPreregistro() {
		return codigoEntradaPreregistro;
	}

	public void setCodigoEntradaPreregistro(Long codigoEntradaPreregistro) {
		this.codigoEntradaPreregistro = codigoEntradaPreregistro;
	}

	public String getCodigoOficinaRegistro() {
		return codigoOficinaRegistro;
	}

	public void setCodigoOficinaRegistro(String codigoOficinaRegistro) {
		this.codigoOficinaRegistro = codigoOficinaRegistro;
	}
	
	
	
}
