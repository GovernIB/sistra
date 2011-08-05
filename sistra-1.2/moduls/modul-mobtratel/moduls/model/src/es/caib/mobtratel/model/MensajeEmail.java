package es.caib.mobtratel.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import es.caib.xml.ConstantesXML;

public class MensajeEmail implements Serializable 
{
	public static int PENDIENTE_ENVIO = 0;
	public static int ENVIADO = 1;
	public static int CON_ERROR = 2;

	public static int MAX_LENGTH_ERROR = 500;

	/**
	 * Identificador del mensaje
	 */
	private Long codigo;
	/**
	 *	Destinatarios(Lista Emails separados por ';'
	 */
	private byte[] destinatarios;
	/**
	 *	Para tipo Email, indica el Título
	 */
	private String titulo;
	/**
	 * Indica si el mensaje es html
	 */
	private boolean html;	
	/**
	 *	Contenido del mensaje
	 */
	private byte[] mensaje;

	/**
	 *	Contenido del mensaje en String
	 */
	private String mensajeStr;

	/**
	 *	Estado de envio del mensaje
	 */
	private int estado = PENDIENTE_ENVIO;

	
	/**
	 *	Error en el envio
	 */
	private String error;

	/**
	 *	Envio al que pertenece el mensaje
	 */

	private Envio envio;

	
	/**
	 *	Fecha Inicio del Envio
	 */
	private Timestamp fechaInicioEnvio;

	/**
	 *	Fecha Fin del Envio
	 */
	private Timestamp fechaFinEnvio;

	
	/**
	 *	Destinatarios Enviados(Lista Telefonos separados por ';')
	 */
	private byte[] destinatariosEnviados;
	
	/**
	 * Numero de Destinatarios
	 */
	private int numeroDestinatarios;
	
	/**
	 * Numero de Destinatarios Enviados
	 */
	private int numeroDestinatariosEnviados;
	

	/**
	 * Destinatarios a los que se ha podido enviar el mail
	 */
	private List destinatariosValidos;
	
	
	public byte[] getDestinatariosEnviados() {
		return destinatariosEnviados;
	}


	public void setDestinatariosEnviados(byte[] destinatariosEnviados) {
		this.destinatariosEnviados = destinatariosEnviados;
	}


	public int getNumeroDestinatarios() {
		return numeroDestinatarios;
	}


	public void setNumeroDestinatarios(int numeroDestinatarios) {
		this.numeroDestinatarios = numeroDestinatarios;
	}


	public int getNumeroDestinatariosEnviados() {
		return numeroDestinatariosEnviados;
	}


	public void setNumeroDestinatariosEnviados(int numeroDestinatariosEnviados) {
		this.numeroDestinatariosEnviados = numeroDestinatariosEnviados;
	}


	public Timestamp getFechaFinEnvio() {
		return fechaFinEnvio;
	}


	public void setFechaFinEnvio(Timestamp fechaFinEnvio) {
		this.fechaFinEnvio = fechaFinEnvio;
	}


	public Timestamp getFechaInicioEnvio() {
		return fechaInicioEnvio;
	}


	public void setFechaInicioEnvio(Timestamp fechaInicioEnvio) {
		this.fechaInicioEnvio = fechaInicioEnvio;
	}


	public void setMensaje(byte[] mensaje) {
		this.mensaje = mensaje;
		try {
			this.mensajeStr =  new String(this.mensaje, ConstantesXML.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public byte[] getDestinatarios() {
		return destinatarios;
	}


	public void setDestinatarios(byte[] destinatarios) {
		this.destinatarios = destinatarios;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		if((error != null) && (error.length() > MAX_LENGTH_ERROR)) error = error.substring(0,MAX_LENGTH_ERROR);
		this.error = error;
	}


	public int getEstado() {
		return estado;
	}


	public void setEstado(int estado) {
		this.estado = estado;
	}


	public String getMensajeStr() {
		return mensajeStr;
	}


	public void setMensajeStr(String mensajeStr) {
		this.mensajeStr = mensajeStr;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public byte[] getMensaje() {
		return mensaje;
	}


	public Envio getEnvio() {
		return envio;
	}


	public void setEnvio(Envio envio) {
		this.envio = envio;
	}


	public boolean isHtml() {
		return html;
	}


	public void setHtml(boolean html) {
		this.html = html;
	}

	public List getDestinatariosValidos() {
		return destinatariosValidos;
	}

	public void setDestinatariosValidos(List destinatariosValidos) {
		this.destinatariosValidos = destinatariosValidos;
	}

	
	

}
