package es.caib.mobtratel.modelInterfaz;

import java.io.Serializable;


/**
 * 
 * Clase que modeliza un Mensaje SMS
 */
public class MensajeEnvioSms implements Serializable{

	/**
	 * Lista de destinatarios (números teléfono móvil)
	 */
	private String[] destinatarios;
	/**
	 * Contenido SMS
	 */
	private String texto;	

	/**
	 * Indica si se ha de verificar el envío (solo si hay un único destinatario).
	 */
	private boolean verificarEnvio;
	
	/**
	 * Devuelve el array de Destinatarios a los que se enviara el mensaje (números teléfono móvil)
	 * @return
	 */
	public String[] getDestinatarios() {
		return destinatarios;
	}
	/**
	 * Establece el array de Destinatarios a los que se enviara el mensaje (números teléfono móvil)
	 * @param emails
	 */
	public void setDestinatarios(String[] destinatarios) {
		this.destinatarios = destinatarios;
	}
	/**
	 * Devuelve el texto del Mensaje
	 * @return
	 */
	public String getTexto() {
		return texto;
	}
	/**
	 * Establece el texto del Mensaje
	 * @param texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public boolean isVerificarEnvio() {
		return verificarEnvio;
	}
	public void setVerificarEnvio(boolean verificarEnvio) {
		this.verificarEnvio = verificarEnvio;
	}
	

	
}
