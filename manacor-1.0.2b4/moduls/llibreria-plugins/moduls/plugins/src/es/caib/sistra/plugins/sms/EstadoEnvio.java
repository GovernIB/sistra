package es.caib.sistra.plugins.sms;

import java.io.Serializable;

/**
 * Indica el estado de un envío SMS
 * 
 */
public class EstadoEnvio implements Serializable{

	/**
	 * Estado del envío
	 * @see ConstantesSMS
	 */
	public char estado;
	
	/**
	 * Descripción del estado 
	 */
	public String descripcionEstado;

	/**
	 *  Descripción del estado 
	 * @return  descripción
	 */
	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	/**
	 *  Descripción del estado 
	 * @param descripcionEstado descripción
	 */
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	/**
	 * Estado del envío
	 * @return estado
	 * @see ConstantesSMS
	 */
	public char getEstado() {
		return estado;
	}
	/**
	 * Estado del envío
	 * @return estado
	 * @see ConstantesSMS
	 */
	public void setEstado(char estado) {
		this.estado = estado;
	}
	
}
