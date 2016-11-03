package es.caib.audita.model;

import java.io.Serializable;

public class InicioTramitacion implements Serializable
{
	private int tramitesTelematicos;
	private int tramitesPreRegistro;
	private int numeroMaximoTramites;
	private String fechaMaximoTramites;

	public int getTramitesPreRegistro() {
		return tramitesPreRegistro;
	}
	public void setTramitesPreRegistro(int tramitesPreRegistro) {
		this.tramitesPreRegistro = tramitesPreRegistro;
	}
	public int getTramitesTelematicos() {
		return tramitesTelematicos;
	}
	public void setTramitesTelematicos(int tramitesTelematicos) {
		this.tramitesTelematicos = tramitesTelematicos;
	}
	public String getFechaMaximoTramites() {
		return fechaMaximoTramites;
	}
	public void setFechaMaximoTramites(String fechaMaximoTramites) {
		this.fechaMaximoTramites = fechaMaximoTramites;
	}
	public int getNumeroMaximoTramites() {
		return numeroMaximoTramites;
	}
	public void setNumeroMaximoTramites(int numeroMaximoTramites) {
		this.numeroMaximoTramites = numeroMaximoTramites;
	}
	
}