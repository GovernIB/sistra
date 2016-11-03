package es.caib.zonaper.model;

import java.io.Serializable;

public class IndiceElemento implements Serializable 
{
	// TIPOS DE ELEMENTO
	public final static String TIPO_EXPEDIENTE = "E";
	public final static String TIPO_ENTRADA_TELEMATICA = "T";
	public final static String TIPO_ENTRADA_PREREGISTRO = "P";
	public final static String TIPO_NOTIFICACION = "N";
	public final static String TIPO_AVISO_EXPEDIENTE = "A";
		
	
	private Long codigo;
	private String nif;
	private String tipoElemento;
	private Long codigoElemento;
	private String descripcion;
	private String valor;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getTipoElemento() {
		return tipoElemento;
	}
	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}
	public Long getCodigoElemento() {
		return codigoElemento;
	}
	public void setCodigoElemento(Long codigoElemento) {
		this.codigoElemento = codigoElemento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}
