package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Asiento preparado para registrarse de forma externa (no por Sistra).
 *
 */
public class RegistroExternoPreparado implements Serializable 
{	
	private Long codigoRdsAsiento;
	private String claveRdsAsiento;
	private String referenciasAnexos;
	private String idPersistencia;
	private Date fechaCreacion;
	private Date fechaEliminacion;
	
	public Long getCodigoRdsAsiento() {
		return codigoRdsAsiento;
	}
	public void setCodigoRdsAsiento(Long codigoRdsAsiento) {
		this.codigoRdsAsiento = codigoRdsAsiento;
	}
	public String getClaveRdsAsiento() {
		return claveRdsAsiento;
	}
	public void setClaveRdsAsiento(String claveRdsAsiento) {
		this.claveRdsAsiento = claveRdsAsiento;
	}
	public String getReferenciasAnexos() {
		return referenciasAnexos;
	}
	public void setReferenciasAnexos(String referenciasAnexos) {
		this.referenciasAnexos = referenciasAnexos;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	
	
}
