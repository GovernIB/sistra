package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Identificador de la relación usuario trámite
 */
public class RolUsuarioTramiteId implements Serializable {

    private String codiUsuario;
    private Long codiTra;
    
    
	public RolUsuarioTramiteId() {
		super();
	}
	public RolUsuarioTramiteId(String codiUsuario, Long codiTra) {
		super();
		this.codiUsuario = codiUsuario;
		this.codiTra = codiTra;
	}
	
	
	public Long getCodiTra() {
		return codiTra;
	}
	public void setCodiTra(Long codiTra) {
		this.codiTra = codiTra;
	}
	public String getCodiUsuario() {
		return codiUsuario;
	}
	public void setCodiUsuario(String codiUsuario) {
		this.codiUsuario = codiUsuario;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolUsuarioTramiteId))
			return false;
		RolUsuarioTramiteId castOther = (RolUsuarioTramiteId) other;

		return ((this.getCodiUsuario() == castOther.getCodiUsuario()) || (this
					.getCodiUsuario() != null && castOther.getCodiUsuario() != null && this
					.getCodiUsuario().equals(castOther.getCodiUsuario())))
				&& ((this.getCodiTra() == castOther.getCodiTra()) || (this
						.getCodiTra() != null	&& castOther.getCodiTra() != null && this
						.getCodiTra().equals(castOther.getCodiTra())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getCodiUsuario() == null ? 0 : this.getCodiUsuario()
						.hashCode());
		result = 37
				* result
				+ (getCodiTra() == null ? 0 : this.getCodiTra()
						.hashCode());
		return result;
	}
}
