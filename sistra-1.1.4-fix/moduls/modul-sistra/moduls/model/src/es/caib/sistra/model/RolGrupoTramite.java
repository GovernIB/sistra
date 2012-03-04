package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Representaci&oacute; de la relació grup formulari
 */
public class RolGrupoTramite implements Serializable {

	private RolGrupoTramiteId id;

	public RolGrupoTramite(RolGrupoTramiteId id) {
		this.id = id;
	}

	public RolGrupoTramite() {
		super();
	}

	public RolGrupoTramiteId getId() {
		return id;
	}

	public void setId(RolGrupoTramiteId id) {
		this.id = id;
	}
	
	
	
}
