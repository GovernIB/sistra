package es.caib.mobtratel.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import es.caib.mobtratel.modelInterfaz.ConstantesMobtratel;
import es.caib.xml.ConstantesXML;

public class MensajeSms implements Serializable 
{	
	
	public static int MAX_LENGTH_ERROR = 500;

	/**
	 * Identificador del mensaje
	 */
	private Long codigo;
	/**
	 *	Destinatarios(Lista Telefonos separados por ';'
	 */
	private byte[] destinatarios;
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
	private int estado = ConstantesMobtratel.ESTADOENVIO_PENDIENTE;

	
	/**
	 *	Error en el envio
	 */
	private String error;


	/**
	 *	Envio al que pertenece el mensaje
	 */

	private Envio envio;

	
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
	 *	Fecha Inicio del Envio
	 */
	private Timestamp fechaInicioEnvio;

	/**
	 *	Fecha Fin del Envio
	 */
	private Timestamp fechaFinEnvio;

	/**
	 * Indica si se ha de verificar el envío (solo si hay un único destinatario).
	 */
	private boolean verificarEnvio;
	
	/**
	 * En caso de verificar el envío indica el estado devuelto por el proceso de verificación (a través del plugin de envíos).
	 */
	private String estadoVerificarEnvio;
	
	/**
	 * En caso de verificar el envío y estado error indica el error devuelto por el proceso de verificación (a través del plugin de envíos).
	 */
	private String errorVerificarEnvio;
	
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


	public Envio getEnvio() {
		return envio;
	}


	public void setEnvio(Envio envio) {
		this.envio = envio;
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



	public byte[] getMensaje() {
		return mensaje;
	}


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


	public boolean isVerificarEnvio() {
		return verificarEnvio;
	}


	public void setVerificarEnvio(boolean verificarEnvio) {
		this.verificarEnvio = verificarEnvio;
	}


	public String getEstadoVerificarEnvio() {
		return estadoVerificarEnvio;
	}


	public void setEstadoVerificarEnvio(String estadoVerificarEnvio) {
		this.estadoVerificarEnvio = estadoVerificarEnvio;
	}


	public String getErrorVerificarEnvio() {
		return errorVerificarEnvio;
	}


	public void setErrorVerificarEnvio(String errorVerificarEnvio) {
		this.errorVerificarEnvio = errorVerificarEnvio;
	}

	
	

}
