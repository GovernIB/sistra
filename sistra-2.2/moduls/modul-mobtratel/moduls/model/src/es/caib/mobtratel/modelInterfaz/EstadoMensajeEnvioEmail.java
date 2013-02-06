package es.caib.mobtratel.modelInterfaz;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Clase que modeliza el estado de un Mensaje Envio Email.
 * 
 */
public class EstadoMensajeEnvioEmail implements Serializable{
	/**
	 * Estado (ver constantes).
	 */
	private int estado;
	/**
	 * Fecha inicio envio.
	 */
	private Date fechaInicioEnvio;
	/**
	 * Fecha fin envio.
	 */
	private Date fechaFinEnvio;
	/**
	 * Lista de destinatarios (direcciones email).
	 */
	private String[] destinatarios;
	/**
	 * Lista de destinatarios a los que se ha enviado (direcciones email).
	 */
	private String[] destinatariosEnviados;
	/**
	 * Verificación envíos habilitada.
	 */
	private boolean verificacionEnvio;
	/**
	 * Estado de verificación (ver constantes).
	 */
	private int estadoVerificacionEnvio = ConstantesMobtratel.CONFIRMACION_PENDIENTE;
	/**
	 * Si existe error verificación indica el mensaje.
	 */
	private String errorVerificacionEnvio;
	
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Date getFechaInicioEnvio() {
		return fechaInicioEnvio;
	}
	public void setFechaInicioEnvio(Date fechaInicioEnvio) {
		this.fechaInicioEnvio = fechaInicioEnvio;
	}
	public Date getFechaFinEnvio() {
		return fechaFinEnvio;
	}
	public void setFechaFinEnvio(Date fechaFinEnvio) {
		this.fechaFinEnvio = fechaFinEnvio;
	}
	public String[] getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String[] destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String[] getDestinatariosEnviados() {
		return destinatariosEnviados;
	}
	public void setDestinatariosEnviados(String[] destinatariosEnviados) {
		this.destinatariosEnviados = destinatariosEnviados;
	}
	public boolean isVerificacionEnvio() {
		return verificacionEnvio;
	}
	public void setVerificacionEnvio(boolean habilitarVerificacionEnvio) {
		this.verificacionEnvio = habilitarVerificacionEnvio;
	}
	public int getEstadoVerificacionEnvio() {
		return estadoVerificacionEnvio;
	}
	public void setEstadoVerificacionEnvio(int estadoVerificacionEnvio) {
		this.estadoVerificacionEnvio = estadoVerificacionEnvio;
	}
	public String getErrorVerificacionEnvio() {
		return errorVerificacionEnvio;
	}
	public void setErrorVerificacionEnvio(String errorVerificacionEnvio) {
		this.errorVerificacionEnvio = errorVerificacionEnvio;
	}
	
	
}
