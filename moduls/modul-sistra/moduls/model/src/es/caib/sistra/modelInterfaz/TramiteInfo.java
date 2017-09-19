package es.caib.sistra.modelInterfaz;

import java.io.Serializable;

/**
 * Informaci�n b�sica tr�mite
 */
public class TramiteInfo implements Serializable{
	
	/**
	 * Codigo tramite.
	 */
	private String codigo;
	
	/**
	 * Descripcion tr�mite
	 */
	private String descripcion;
	
	/**
	 * Procedimiento ID.
	 */
	private String procedimientoId;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(String procedimientoId) {
		this.procedimientoId = procedimientoId;
	}
	
		

}
