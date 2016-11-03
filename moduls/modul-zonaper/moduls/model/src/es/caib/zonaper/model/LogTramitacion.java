package es.caib.zonaper.model;

import java.io.Serializable;

/**
 * Obtiene datos de la persona almacenadas en la PAD 
 * 
 */

public class LogTramitacion implements Serializable{
	private String tipoEvento;
	private String hora;
	private String descTramite;
	private String clavePersistencia;
	private String idioma;
	private String clave;
	
	public String getClavePersistencia() {
		return clavePersistencia;
	}
	public void setClavePersistencia(String clavePersistencia) {
		this.clavePersistencia = clavePersistencia;
	}
	public String getDescTramite() {
		return descTramite;
	}
	public void setDescTramite(String descTramite) {
		this.descTramite = descTramite;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	
}
