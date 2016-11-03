package es.caib.zonaper.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * Delegaciones de una entidad
 *
 */
public class Delegacion implements Serializable 
{	
	private Long codigo;
	private String nifDelegante;
	private String nifDelegado;
	private String permisos;
	private Timestamp fechaInicioDelegacion;
	private Timestamp fechaFinDelegacion;
	private String claveRdsDocDelegacion;
	private Long codigoRdsDocDelegacion;
	private String anulada;
	
	public String getAnulada() {
		return anulada;
	}
	public void setAnulada(String anulada) {
		this.anulada = anulada;
	}
	public String getClaveRdsDocDelegacion() {
		return claveRdsDocDelegacion;
	}
	public void setClaveRdsDocDelegacion(String claveRdsDocDelegacion) {
		this.claveRdsDocDelegacion = claveRdsDocDelegacion;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Long getCodigoRdsDocDelegacion() {
		return codigoRdsDocDelegacion;
	}
	public void setCodigoRdsDocDelegacion(Long codigoRdsDocDelegacion) {
		this.codigoRdsDocDelegacion = codigoRdsDocDelegacion;
	}
	public Timestamp getFechaFinDelegacion() {
		return fechaFinDelegacion;
	}
	public void setFechaFinDelegacion(Timestamp fechaFinDelegacion) {
		this.fechaFinDelegacion = fechaFinDelegacion;
	}
	public Timestamp getFechaInicioDelegacion() {
		return fechaInicioDelegacion;
	}
	public void setFechaInicioDelegacion(Timestamp fechaInicioDelegacion) {
		this.fechaInicioDelegacion = fechaInicioDelegacion;
	}
	public String getPermisos() {
		return permisos;
	}
	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
	public String getNifDelegado() {
		return nifDelegado;
	}
	public void setNifDelegado(String usuarioDelegado) {
		this.nifDelegado = usuarioDelegado;
	}
	public String getNifDelegante() {
		return nifDelegante;
	}
	public void setNifDelegante(String usuarioEntidad) {
		this.nifDelegante = usuarioEntidad;
	}	
	
}
