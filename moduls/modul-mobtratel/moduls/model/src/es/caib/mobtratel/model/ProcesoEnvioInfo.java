package es.caib.mobtratel.model;

import java.util.Date;

/**
 * 
 * Informacion del proceso de envio  
 * 
 */
public class ProcesoEnvioInfo {

	/**
	 * Numero de sms enviados en todo el proceso. Sirve para poder controlar el num maximo de sms por proceso.
	 */
	private int smsEnviados;
	
	/**
	 * Fecha inicio del proceso de envio. Sirve para poder controlar el tiempo maximo por proceso
	 */
	private Date fechaInicio;


	public int getSmsEnviados() {
		return smsEnviados;
	}

	public void setSmsEnviados(int smsEnviados) {
		this.smsEnviados = smsEnviados;
	}

	

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	
}
