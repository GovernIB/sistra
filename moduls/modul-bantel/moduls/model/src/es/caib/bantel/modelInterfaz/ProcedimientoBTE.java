package es.caib.bantel.modelInterfaz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Informacion del procedimiento.
 */
public class ProcedimientoBTE implements Serializable {
	/**
	 * Identificador.
	 */
	private String identificador;
	/**
	 * Entidad.
	 */
	private EntidadBTE entidad;
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
	private boolean permitirPlazoNotificacionesVariable;
	/**
	 * Indica remitente para avisos procedimiento.
	 */
	private String remitenteAvisosProcedimiento;
	/**
	 * Indica email respuesta para avisos procedimiento.
	 */
	private String emailRespuestaAvisosProcedimiento;
	/**
	 * Indica email respuesta para incidencias.
	 */
	private String emailIncidencias;
	/**
	 * Emails gestores.
	 */
	private List emailGestores = new ArrayList();
	
	public String getRemitenteAvisosProcedimiento() {
		return remitenteAvisosProcedimiento;
	}
	public void setRemitenteAvisosProcedimiento(String remitenteAvisosProcedimiento) {
		this.remitenteAvisosProcedimiento = remitenteAvisosProcedimiento;
	}
	public String getEmailRespuestaAvisosProcedimiento() {
		return emailRespuestaAvisosProcedimiento;
	}
	public void setEmailRespuestaAvisosProcedimiento(
			String emailRespuestaAvisosProcedimiento) {
		this.emailRespuestaAvisosProcedimiento = emailRespuestaAvisosProcedimiento;
	}
	
	public String getEmailIncidencias() {
		return emailIncidencias;
	}
	public void setEmailIncidencias(String emailIncidencias) {
		this.emailIncidencias = emailIncidencias;
	}
	
	
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
	public List getEmailGestores() {
		return emailGestores;
	}
	public void setEmailGestores(List emailGestores) {
		this.emailGestores = emailGestores;
	}
	public EntidadBTE getEntidad() {
		return entidad;
	}
	public void setEntidad(EntidadBTE entidad) {
		this.entidad = entidad;
	}
	
}
