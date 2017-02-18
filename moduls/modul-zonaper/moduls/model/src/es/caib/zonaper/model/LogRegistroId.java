package es.caib.zonaper.model;

import java.io.Serializable;

/**
 * Identificador del logRegistro
 */
public class LogRegistroId implements Serializable {

	private String entidad;
    private String tipoRegistro;
    private String numeroRegistro;
    
    
    
	public LogRegistroId(String entidad, String tipoRegistro, String numeroRegistro) {
		super();
		this.entidad = entidad;
		this.tipoRegistro = tipoRegistro;
		this.numeroRegistro = numeroRegistro;
	}
	public LogRegistroId() {
		super();
	}
	
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LogRegistroId))
			return false;
		LogRegistroId castOther = (LogRegistroId) other;

		return ((this.getTipoRegistro() == castOther.getTipoRegistro()) || (this
					.getTipoRegistro() != null && castOther.getTipoRegistro() != null && this
					.getTipoRegistro().equals(castOther.getTipoRegistro())))
				&& ((this.getNumeroRegistro() == castOther.getNumeroRegistro()) || (this
						.getNumeroRegistro() != null	&& castOther.getNumeroRegistro() != null && this
						.getNumeroRegistro().equals(castOther.getNumeroRegistro())))
				&& ((this.getEntidad() == castOther.getEntidad()) || (this
						.getEntidad() != null	&& castOther.getEntidad() != null && this
						.getEntidad().equals(castOther.getEntidad())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result 
				+ (getTipoRegistro() == null ? 0 : this.getTipoRegistro()
						.hashCode());
		result = 37
				* result
				+ (getNumeroRegistro() == null ? 0 : this.getNumeroRegistro()
						.hashCode());
		return result;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
    
}
