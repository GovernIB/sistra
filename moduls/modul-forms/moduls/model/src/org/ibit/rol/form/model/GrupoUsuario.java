package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representaci&oacute; de la relació grup formulari
 */
public class GrupoUsuario implements Serializable {

	private GrupoUsuarioId id;
	
	public GrupoUsuario(GrupoUsuarioId id) {
		this.id = id;
	}

	public GrupoUsuario() {
		super();
	}

	public GrupoUsuarioId getId() {
		return id;
	}

	public void setId(GrupoUsuarioId id) {
		this.id = id;
	}
	
}
