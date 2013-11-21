package es.caib.zonaper.modelInterfaz;

import java.util.Date;

/**
 * Detalle aviso de un elemento de expediente (notificacion / evento)
 *
 */
public class DetalleAviso {
	
	/**
	 * Tipo aviso email.
	 */
	public final static String TIPO_EMAIL = "EMAIL";
	/**
	 * Tipo aviso sms.
	 */
	public final static String TIPO_SMS  = "SMS";
	
	/**
	 * Confirmacion aviso: enviado.
	 */
	public final static String CONFIRMADO_ENVIADO = "CONFIRMADO_ENVIADO";
	
	/**
	 * Confirmacion aviso: no enviado.
	 */
	public final static String CONFIRMADO_NO_ENVIADO = "CONFIRMADO_NO_ENVIADO";
	
	/**
	 * Confirmacion aviso: desconocido.
	 */
	public final static String CONFIRMADO_DESCONOCIDO = "CONFIRMADO_DESCONOCIDO";
	
	/**
	 * Tipo aviso (sms / email)
	 */
	private String tipo;
	
	/**
	 * Indica si se ha completado el envio.
	 */
	private boolean enviado;
	
	/**
	 * Destinatarios mensajes: direccion email o numero de movil
	 */
	private String destinatario;
	
	/**
	 * Fecha envio.
	 */
	private Date fechaEnvio;
	
	/**
	 * Indica si esta marcado para comprobar si se ha enviado. 
	 */
	private boolean confirmarEnvio;
	
	/**
	 * Indica si se ha confirmado si se ha enviado: CONFIRMADO_ENVIADO / CONFIRMADO_NO_ENVIADO / CONFIRMADO_DESCONOCIDO 
	 */
	private String confirmadoEnvio =  CONFIRMADO_DESCONOCIDO;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public boolean isConfirmarEnvio() {
		return confirmarEnvio;
	}

	public void setConfirmarEnvio(boolean confirmarEnvio) {
		this.confirmarEnvio = confirmarEnvio;
	}

	public String getConfirmadoEnvio() {
		return confirmadoEnvio;
	}

	public void setConfirmadoEnvio(String confirmadoEnvio) {
		this.confirmadoEnvio = confirmadoEnvio;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
	
}
