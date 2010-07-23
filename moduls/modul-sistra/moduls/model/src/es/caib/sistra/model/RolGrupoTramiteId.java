package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Identificador de la relación grupo formulario
 */
public class RolGrupoTramiteId implements Serializable {

    private String codiGrup;
    private Long codiTra;
    
    
	public RolGrupoTramiteId() {
		super();
	}
	public RolGrupoTramiteId(String codiGrup, Long codiTra) {
		super();
		this.codiGrup = codiGrup;
		this.codiTra = codiTra;
	}
	
	public Long getCodiTra() {
		return codiTra;
	}
	public void setCodiTra(Long codiTra) {
		this.codiTra = codiTra;
	}
	public String getCodiGrup() {
		return codiGrup;
	}
	public void setCodiGrup(String codiGrup) {
		this.codiGrup = codiGrup;
	}

	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolGrupoTramiteId))
			return false;
		RolGrupoTramiteId castOther = (RolGrupoTramiteId) other;

		return ((this.getCodiGrup() == castOther.getCodiGrup()) || (this
					.getCodiGrup() != null && castOther.getCodiGrup() != null && this
					.getCodiGrup().equals(castOther.getCodiGrup())))
				&& ((this.getCodiTra() == castOther.getCodiTra()) || (this
						.getCodiTra() != null	&& castOther.getCodiTra() != null && this
						.getCodiTra().equals(castOther.getCodiTra())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getCodiGrup() == null ? 0 : this.getCodiGrup()
						.hashCode());
		result = 37
				* result
				+ (getCodiTra() == null ? 0 : this.getCodiTra()
						.hashCode());
		return result;
	}
    
}
