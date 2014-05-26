package es.caib.bantel.model;

import java.io.Serializable;

public class ValorFuenteDatos implements Serializable{

	private Long codigo;	
	private FilaFuenteDatos filaFuenteDatos;
	private CampoFuenteDatos campoFuenteDatos;
	private String valor;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public FilaFuenteDatos getFilaFuenteDatos() {
		return filaFuenteDatos;
	}
	public void setFilaFuenteDatos(FilaFuenteDatos filaFuenteDatos) {
		this.filaFuenteDatos = filaFuenteDatos;
	}
	public CampoFuenteDatos getCampoFuenteDatos() {
		return campoFuenteDatos;
	}
	public void setCampoFuenteDatos(CampoFuenteDatos campoFuenteDatos) {
		this.campoFuenteDatos = campoFuenteDatos;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}	
}
