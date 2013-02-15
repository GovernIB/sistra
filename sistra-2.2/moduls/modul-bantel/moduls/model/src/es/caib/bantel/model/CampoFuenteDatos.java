package es.caib.bantel.model;

import java.io.Serializable;

public class CampoFuenteDatos implements Serializable{
	
	
	private Long codigo;
	private String identificador;
	private FuenteDatos fuenteDatos;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public FuenteDatos getFuenteDatos() {
		return fuenteDatos;
	}
	public void setFuenteDatos(FuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}	
}
