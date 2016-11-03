package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Identificador de la relación usuario formulario
 */
public class RolUsuarioFormularioId implements Serializable {

    private String codiUsuario;
    private Long codiForm;
    
    
	public RolUsuarioFormularioId() {
		super();
	}
	public RolUsuarioFormularioId(String codiUsuario, Long codiForm) {
		super();
		this.codiUsuario = codiUsuario;
		this.codiForm = codiForm;
	}
	
	public Long getCodiForm() {
		return codiForm;
	}
	public void setCodiForm(Long codiForm) {
		this.codiForm = codiForm;
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
		if (!(other instanceof RolUsuarioFormularioId))
			return false;
		RolUsuarioFormularioId castOther = (RolUsuarioFormularioId) other;

		return ((this.getCodiUsuario() == castOther.getCodiUsuario()) || (this
					.getCodiUsuario() != null && castOther.getCodiUsuario() != null && this
					.getCodiUsuario().equals(castOther.getCodiUsuario())))
				&& ((this.getCodiForm() == castOther.getCodiForm()) || (this
						.getCodiForm() != null	&& castOther.getCodiForm() != null && this
						.getCodiForm().equals(castOther.getCodiForm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getCodiUsuario() == null ? 0 : this.getCodiUsuario()
						.hashCode());
		result = 37
				* result
				+ (getCodiForm() == null ? 0 : this.getCodiForm()
						.hashCode());
		return result;
	}
}
