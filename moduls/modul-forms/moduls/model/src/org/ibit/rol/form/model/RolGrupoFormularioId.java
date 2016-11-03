package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Identificador de la relación grupo formulario
 */
public class RolGrupoFormularioId implements Serializable {

    private String codiGrup;
    private Long codiForm;
    
	public Long getCodiForm() {
		return codiForm;
	}
	public void setCodiForm(Long codiForm) {
		this.codiForm = codiForm;
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
		if (!(other instanceof RolGrupoFormularioId))
			return false;
		RolGrupoFormularioId castOther = (RolGrupoFormularioId) other;

		return ((this.getCodiGrup() == castOther.getCodiGrup()) || (this
					.getCodiGrup() != null && castOther.getCodiGrup() != null && this
					.getCodiGrup().equals(castOther.getCodiGrup())))
				&& ((this.getCodiForm() == castOther.getCodiForm()) || (this
						.getCodiForm() != null	&& castOther.getCodiForm() != null && this
						.getCodiForm().equals(castOther.getCodiForm())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getCodiGrup() == null ? 0 : this.getCodiGrup()
						.hashCode());
		result = 37
				* result
				+ (getCodiForm() == null ? 0 : this.getCodiForm()
						.hashCode());
		return result;
	}
    
}
