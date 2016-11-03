package es.caib.sistra.plugins.email;

import java.io.Serializable;

/**
 * Indica el estado de un envío Email
 * 
 */
public class EstadoEnvio implements Serializable{

	/**
	 * Estado del envío
	 * @see ConstantesEmail
	 */
	public String estado;
	
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
	public String getEstado() {
		return estado;
	}
	/**
	 * Estado del envío
	 * @return estado
	 * @see ConstantesSMS
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
