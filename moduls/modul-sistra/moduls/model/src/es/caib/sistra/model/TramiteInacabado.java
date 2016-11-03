package es.caib.sistra.model;

import java.util.Date;

public class TramiteInacabado {

	private Date ultimaModificacion;
	private int diasPersistencia;
	private String idPersistencia;
	private String remitidoA;
	private String remitidoPor;
	
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	public String getRemitidoA() {
		return remitidoA;
	}
	public void setRemitidoA(String remitidoA) {
		this.remitidoA = remitidoA;
	}
	public String getRemitidoPor() {
		return remitidoPor;
	}
	public void setRemitidoPor(String remitidoPor) {
		this.remitidoPor = remitidoPor;
	}
	public int getDiasPersistencia() {
		return diasPersistencia;
	}
	public void setDiasPersistencia(int diasPersistencia) {
		this.diasPersistencia = diasPersistencia;
	}
	public Date getUltimaModificacion() {
		return ultimaModificacion;
	}
	public void setUltimaModificacion(Date ultimaModificacion) {
		this.ultimaModificacion = ultimaModificacion;
	}
	
	
	
}
