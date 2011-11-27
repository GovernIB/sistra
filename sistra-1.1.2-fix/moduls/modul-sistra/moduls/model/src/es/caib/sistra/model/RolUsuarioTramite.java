package es.caib.sistra.model;

import java.io.Serializable;

/**
 * Representaci&oacute; de la relaci� grup tr�mit
 */
public class RolUsuarioTramite implements Serializable {

    private RolUsuarioTramiteId id;
    
	public RolUsuarioTramite() {
		super();
	}
	public RolUsuarioTramite(RolUsuarioTramiteId id) {
		super();
		this.id = id;
	}
	public RolUsuarioTramiteId getId() {
		return id;
	}
	public void setId(RolUsuarioTramiteId id) {
		this.id = id;
	}
    
}
