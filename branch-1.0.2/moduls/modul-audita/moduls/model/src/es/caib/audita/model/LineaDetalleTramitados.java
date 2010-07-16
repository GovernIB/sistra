package es.caib.audita.model;

import java.io.Serializable;

public class LineaDetalleTramitados implements Serializable
{
	private String modelo;
	private String organismo;
	private int valor;
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

}