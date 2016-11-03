package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representaci&oacute; de la relació grup formulari
 */
public class RolGrupoFormulario implements Serializable {

	private RolGrupoFormularioId id;

	public RolGrupoFormulario(RolGrupoFormularioId id) {
		this.id = id;
	}

	public RolGrupoFormulario() {
		super();
	}

	public RolGrupoFormularioId getId() {
		return id;
	}

	public void setId(RolGrupoFormularioId id) {
		this.id = id;
	}
	
	
	
}
