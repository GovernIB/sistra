package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Detalle acuse de recibo.
 *
 */
public class DetalleAcuseRecibo implements Serializable {
	/**
	 * Notificacion pendiente de entregar.
	 */
	public final static String ESTADO_PENDIENTE = "PENDIENTE";
	/**
	 * Notificacion entregada.
	 */
	public final static String ESTADO_ENTREGADA = "ENTREGADA";
	/**
	 * Notificacion rechazada.
	 */
	public final static String ESTADO_RECHAZADA = "RECHAZADA";
	
	/**
	 * Estado notificación (pendiente, entregada o rechazada).
	 */
	private String estado;
	/**
	 * Fecha fin plazo.
	 */
	private Date fechaFinPlazo;
	/**
	 * Fecha generación acuse de recibo (entregada o rechazada).
	 */
	private Date fechaAcuseRecibo;
	/**
	 * Fichero de acuse de recibo (codigo).
	 */
	private Long codigoRdsAcuseRecibo;
	/**
	 * Fichero de acuse de recibo (clave).
	 */
	private String claveRdsAcuseRecibo;	
	/**
	 * Avisos de la notificacion (sms / email).	
	 */
	private List avisos;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaAcuseRecibo() {
		return fechaAcuseRecibo;
	}
	public void setFechaAcuseRecibo(Date fechaAcuseRecibo) {
		this.fechaAcuseRecibo = fechaAcuseRecibo;
	}	
	public List getAvisos() {
		return avisos;
	}
	public void setAvisos(List avisos) {
		this.avisos = avisos;
	}
	public Long getCodigoRdsAcuseRecibo() {
		return codigoRdsAcuseRecibo;
	}
	public void setCodigoRdsAcuseRecibo(Long codigoRDSAcuseRecibo) {
		this.codigoRdsAcuseRecibo = codigoRDSAcuseRecibo;
	}
	public String getClaveRdsAcuseRecibo() {
		return claveRdsAcuseRecibo;
	}
	public void setClaveRdsAcuseRecibo(String claveRDSAcuseRecibo) {
		this.claveRdsAcuseRecibo = claveRDSAcuseRecibo;
	}
	public Date getFechaFinPlazo() {
		return fechaFinPlazo;
	}
	public void setFechaFinPlazo(Date fechaFinPlazo) {
		this.fechaFinPlazo = fechaFinPlazo;
	}

}
