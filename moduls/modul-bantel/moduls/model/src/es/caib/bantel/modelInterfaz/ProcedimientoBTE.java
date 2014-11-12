package es.caib.bantel.modelInterfaz;

import java.io.Serializable;

/**
 * Informacion del procedimiento.
 */
public class ProcedimientoBTE implements Serializable {
	/**
	 * Identificador.
	 */
	private String identificador;
	/**
	 * Descripción.
	 */
	private String descripcion;	
	/**
	 * Unidad administrativa.
	 */
	private Long unidadAdministrativa;
	/**
	 * Indica si permite SMS.
	 */
	private boolean permitirSMS;
	/**
	 * Indica si permite plazo de notificaciones variable.
	 */
	private boolean permitirPlazoNotificacionesVariable; ;
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public boolean isPermitirSMS() {
		return permitirSMS;
	}
	public void setPermitirSMS(boolean permitirSMS) {
		this.permitirSMS = permitirSMS;
	}
	public boolean isPermitirPlazoNotificacionesVariable() {
		return permitirPlazoNotificacionesVariable;
	}
	public void setPermitirPlazoNotificacionesVariable(
			boolean permitirPlazoNotificacionesVariable) {
		this.permitirPlazoNotificacionesVariable = permitirPlazoNotificacionesVariable;
	}
	
}
