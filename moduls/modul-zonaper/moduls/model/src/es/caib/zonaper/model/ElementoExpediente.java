package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Date;

public class ElementoExpediente implements Serializable
{

	// TIPOS DE ELEMENTO DE UN EXPEDIENTE
	public final static String TIPO_ENTRADA_TELEMATICA = "T";
	public final static String TIPO_ENTRADA_PREREGISTRO = "P";
	public final static String TIPO_NOTIFICACION = "N";
	public final static String TIPO_AVISO_EXPEDIENTE = "A";


	private Long codigo;
	private Expediente expediente;
	private Date fecha;
	private String tipoElemento;
	private boolean bandeja;
	private Long codigoElemento;
	// En caso de que se haya generado aviso movilidad para avisar del elemento
	private String codigoAviso;

	// Id persistencia del elemento
	private String identificadorPersistencia;

	// Indica si se tiene acceso de forma anonima al expediente a traves del id persistencia del elemento
	private boolean accesoAnonimoExpediente;

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTipoElemento() {
		return tipoElemento;
	}
	public void setTipoElemento(String tipo) {
		this.tipoElemento = tipo;
	}
	public Long getCodigoElemento() {
		return codigoElemento;
	}
	public void setCodigoElemento(Long codigoElemento) {
		this.codigoElemento = codigoElemento;
	}
	public String getCodigoAviso() {
		return codigoAviso;
	}
	public void setCodigoAviso(String codigoAviso) {
		this.codigoAviso = codigoAviso;
	}
	public String getIdentificadorPersistencia() {
		return identificadorPersistencia;
	}
	public void setIdentificadorPersistencia(String idPersistencia) {
		this.identificadorPersistencia = idPersistencia;
	}
	public boolean isAccesoAnonimoExpediente() {
		return accesoAnonimoExpediente;
	}
	public void setAccesoAnonimoExpediente(boolean accesoAnonimoExpediente) {
		this.accesoAnonimoExpediente = accesoAnonimoExpediente;
	}
	/**
	 *	Devuelve bandeja.
	 * @return bandeja
	 */
	public boolean isBandeja() {
		return bandeja;
	}
	/**
	 * Establece bandeja.
	 * @param bandeja bandeja
	 */
	public void setBandeja(boolean bandeja) {
		this.bandeja = bandeja;
	}



}
