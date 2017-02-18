package es.caib.bantel.model;

import java.io.Serializable;

public class Entidad implements Serializable {

	private String identificador;

	private String descripcion;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String codigo) {
		this.identificador = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
