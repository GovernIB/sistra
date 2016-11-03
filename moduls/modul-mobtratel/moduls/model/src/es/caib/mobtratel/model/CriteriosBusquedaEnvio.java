package es.caib.mobtratel.model;

import java.io.Serializable;

public class CriteriosBusquedaEnvio implements Serializable
{
	public static final String TODOS = "T";
	
	private int anyo;
	private int mes;
	private String cuenta;
	private String tipo = TODOS;
	private String enviado = TODOS;
	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
		
}
