package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Identificador de la relación grupo formulario
 */
public class GrupoUsuarioId implements Serializable {

    private String codiGrup;
    private String usuario;
    
    
    
	public GrupoUsuarioId(String codiGrup, String usuario) {
		super();
		// TODO Auto-generated constructor stub
		this.codiGrup = codiGrup;
		this.usuario = usuario;
	}
	public GrupoUsuarioId() {
		super();
	}
	public String getCodiGrup() {
		return codiGrup;
	}
	public void setCodiGrup(String codiGrup) {
		this.codiGrup = codiGrup;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GrupoUsuarioId))
			return false;
		GrupoUsuarioId castOther = (GrupoUsuarioId) other;

		return ((this.getCodiGrup() == castOther.getCodiGrup()) || (this
					.getCodiGrup() != null && castOther.getCodiGrup() != null && this
					.getCodiGrup().equals(castOther.getCodiGrup())))
				&& ((this.getUsuario() == castOther.getUsuario()) || (this
						.getUsuario() != null	&& castOther.getUsuario() != null && this
						.getUsuario().equals(castOther.getUsuario())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getCodiGrup() == null ? 0 : this.getCodiGrup()
						.hashCode());
		result = 37
				* result
				+ (getUsuario() == null ? 0 : this.getUsuario()
						.hashCode());
		return result;
	}
    
}
