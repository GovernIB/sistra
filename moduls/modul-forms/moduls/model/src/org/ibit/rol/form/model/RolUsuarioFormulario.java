package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representaci&oacute; de la relació grup formulari
 */
public class RolUsuarioFormulario implements Serializable {

    private RolUsuarioFormularioId id;
    
	public RolUsuarioFormulario() {
		super();
	}
	public RolUsuarioFormulario(RolUsuarioFormularioId id) {
		super();
		this.id = id;
	}
	public RolUsuarioFormularioId getId() {
		return id;
	}
	public void setId(RolUsuarioFormularioId id) {
		this.id = id;
	}
    
}
