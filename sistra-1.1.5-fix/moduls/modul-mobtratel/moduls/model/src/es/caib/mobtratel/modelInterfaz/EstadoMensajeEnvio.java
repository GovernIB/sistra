package es.caib.mobtratel.modelInterfaz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Clase que modeliza el estado de un Mensaje Envio al módulo de movilidad.
 * 
 */
public class EstadoMensajeEnvio implements Serializable{

	/**
	 * Estado envio (ver Constantes).
	 */
	private int estado;
	
	/**
	 * Fecha en la que se ha realizado el envio.
	 */
	private Date fechaEnvio;
	
	/**
	 * Fecha a partir de la cual se puede realizar el envío.
	 * Si es nulo se enviara en el proximo envio.
	 */
	private Date fechaProgramacionEnvio;	
	/**
	 * Fecha a partir de la cual no se realizara el envio
	 * si todavia no se ha realizado (reintentos)
	 */
	private Date fechaCaducidad;
	
	/**
	 * Indica si el envio es inmediato (tras recibirse se ejecutará) y no programado (programación cada hora).
	 * Sólo debe utilizarse para demo.
	 */
	private boolean inmediato;	
	
	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	private List estadoEmails = new ArrayList();

	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	private List estadoSmss = new ArrayList();

	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	public List getEstadoEmails() {
		return estadoEmails;
	}

	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	public void setEstadoEmails(List estadoEmails) {
		this.estadoEmails = estadoEmails;
	}

	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	public List getEstadoSmss() {
		return estadoSmss;
	}

	/**
	 * Lista de estado mensajes de tipo SMS.
	 */
	public void setEstadoSmss(List estadoSmss) {
		this.estadoSmss = estadoSmss;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaProgramacionEnvio() {
		return fechaProgramacionEnvio;
	}

	public void setFechaProgramacionEnvio(Date fechaProgramacionEnvio) {
		this.fechaProgramacionEnvio = fechaProgramacionEnvio;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public boolean isInmediato() {
		return inmediato;
	}

	public void setInmediato(boolean inmediato) {
		this.inmediato = inmediato;
	}
}
