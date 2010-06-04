package es.caib.mobtratel.modelInterfaz;

import java.io.Serializable;


/**
 * 
 * Clase que modeliza un Mensaje Email
 */
public class MensajeEnvioEmail implements Serializable{

	/**
	 * Lista de destinatarios (direcciones email)
	 */
	private String[] destinatarios;
	/**
	 * Contenido email
	 */
	private String texto;	
	/**
	 * Indica si el texto esta en formato html
	 */
	private boolean html;
	/**
	 * Titulo email
	 */
	private String titulo;	
	/**
	 * Devuelve el titulo del Email
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * Establece el titulo del Email
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}	
	
	/**
	 * Devuelve el array de Destinatarios a los que se enviara el mensaje (direcciones email)
	 * @return
	 */
	public String[] getDestinatarios() {
		return destinatarios;
	}
	/**
	 * Establece el array de Destinatarios a los que se enviara el mensaje (direcciones email)
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
	/**
	 * Indica si el texto esta en formato html
	 * @return
	 */
	public boolean isHtml() {
		return html;
	}
	/**
	 * Indica si el texto esta en formato html
	 * @param html
	 */
	public void setHtml(boolean html) {
		this.html = html;
	}
	

	
}
