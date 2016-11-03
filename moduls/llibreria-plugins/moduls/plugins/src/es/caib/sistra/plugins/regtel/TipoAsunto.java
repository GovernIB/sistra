package es.caib.sistra.plugins.regtel;

import java.io.Serializable;

/**
 * Modeliza informaci�n tipo de asunto
 */
public class TipoAsunto implements Serializable{
	/**
	 * C�digo
	 */
	private String codigo;
	/**
	 * Descripci�n
	 */
	private String descripcion;
	
	/**
	 * Obtiene c�digo
	 * @return C�digo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * Establece c�digo
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * Obtiene descripci�n
	 * @return Descripci�n
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece descripci�n
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}
