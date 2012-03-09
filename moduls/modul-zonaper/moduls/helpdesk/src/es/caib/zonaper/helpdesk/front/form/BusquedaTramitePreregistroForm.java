package es.caib.zonaper.helpdesk.front.form;

import org.apache.struts.validator.ValidatorForm;

public class BusquedaTramitePreregistroForm extends ValidatorForm
{
			
	private String fechaInicial;
	private String fechaFinal;
	private String modelo;
	private char caducidad;
	
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public char getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(char caducidad) {
		this.caducidad = caducidad;
	}
	
		

}
