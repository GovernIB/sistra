package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

public class LogVerificacionMovil implements Serializable 
{
	private Long codigo;
	private String idPersistencia;
	private String movil;
	private String codigoSms;
	private Date fecha;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	public String getMovil() {
		return movil;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public String getCodigoSms() {
		return codigoSms;
	}
	public void setCodigoSms(String codigoSms) {
		this.codigoSms = codigoSms;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	

}
