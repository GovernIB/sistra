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
	private Long codigoElemento;
	
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
	


}
