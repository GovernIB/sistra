package es.caib.mobtratel.modelInterfaz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Clase que modeliza un Mensaje Envio al módulo de movilidad.
 * <p>
 * Un Mensaje Envio se compone de:
 * <ul>
 * 	<li>lista de envios SMS</li>
 * 	<li>lista de envios Email</li>
 * </ul>
 * </p>
 * 
 */
public class MensajeEnvio implements Serializable{


	/**
	 * Nombre descriptivo del envio
	 */
	private String nombre;	

	/**
	 * Identificador de la cuenta emisora con la que 
	 * se realizara el envio. Si es nulo se utilizara
	 * la cuenta por defecto
	 */
	private String cuentaEmisora;	
	/**
	 * Fecha a partir de la cual se puede realizar el envío.
	 * Si es nulo se enviara en el proximo envio.
	 */
	private Date fechaProgramacionEnvio;	
	/**
	 * Fecha a partir de la cual no se realizara el envio
	 * si todavia no se ha realizado (reintentos)
	 */
	private Date fechaCaducidad;
	
	/**
	 * Indica si el envio es inmediato (tras recibirse se ejecutará) y no programado (programación cada hora).
	 * Sólo debe utilizarse para demo.
	 */
	private boolean inmediato;

	/**
	 * Lista de mensajes de tipo Email. Contiene elementos del tipo MensajeEnvioEmail
	 */

	private List emails = new ArrayList();

	/**
	 * Lista de mensajes de tipo SMS. Contiene elementos del tipo MensajeEnvioSMS
	 */

	private List smss = new ArrayList();

	/**
	 * Devuelve el codigo de la Cuenta Emisora. Si no se especifica cuenta, utilizara la
	 * cuenta por defecto
	 * @return
	 */
	public String getCuentaEmisora() {
		return cuentaEmisora;
	}
	/**
	 * Establece el codigo de la Cuenta Emisora. Si no se especifica cuenta, utilizara la
	 * cuenta por defecto
	 * @param cuentaEmisora
	 */
	public void setCuentaEmisora(String cuentaEmisora) {
		this.cuentaEmisora = cuentaEmisora;
	}
	/**
	 * Devuelve la Fecha de Caducidad del Envío. Si no se rellena el Envío no caducara
	 * @return
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	/**
	 * Establece la Fecha de Caducidad del Envío. Si no se rellena el Envío no caducara
	 * @param fechaCaducidad
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	/**
	 * Devuelve la Fecha de Programacion del Envío. Si no se rellena el Envío se realizara
	 * en cuanto se pueda
	 * @return
	 */
	public Date getFechaProgramacionEnvio() {
		return fechaProgramacionEnvio;
	}
	/**
	 * Establece la Fecha de Programacion del Envío. Si no se rellena el Envío se realizara
	 * en cuanto se pueda
	 * @param fechaProgramacionEnvio
	 */
	public void setFechaProgramacionEnvio(Date fechaProgramacionEnvio) {
		this.fechaProgramacionEnvio = fechaProgramacionEnvio;
	}

	/**
	 * Devuelve la Lista de mensajes Email
	 * @return
	 */

	public List getEmails() {
		return emails;
	}
	/**
	 * Establece la Lista de mensajes Email
	 * @return
	 */

	public void setEmails(List emails) {
		this.emails = emails;
	}

	/**
	 * Devuelve la Lista de mensajes SMS
	 * @return
	 */

	public List getSmss() {
		return smss;
	}
	/**
	 * Establece la Lista de mensajes SMS
	 * @return
	 */
	public void setSmss(List smss) {
		this.smss = smss;
	}
	/**
	 * Devuelve el nombre descriptivo del envio
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece el nombre descriptivo del envio
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Anyade un Mensaje Email
	 * @param nombre
	 */

	public void addEmail(MensajeEnvioEmail email)
	{
		this.emails.add(email);
	}

	/**
	 * Anyade un Mensaje SMS
	 * @param nombre
	 */

	public void addSMS(MensajeEnvioSms sms)
	{
		this.smss.add(sms);
	}
	
	/**
	 * Indica si el mensaje es inmediato y no programado. Este tipo de mensajes debe utilizarse sólo para envios urgentes.  
	 * @return
	 */	
	public boolean isInmediato() {
		return inmediato;
	}
	
	/**
	 * Establece si el mensaje es inmediato y no programado. Este tipo de mensajes debe utilizarse sólo para envios urgentes.
	 * @param inmediato
	 */
	public void setInmediato(boolean inmediato) {
		this.inmediato = inmediato;
	}

	
}
