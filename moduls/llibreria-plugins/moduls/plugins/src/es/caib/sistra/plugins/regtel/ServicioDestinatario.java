package es.caib.sistra.plugins.regtel;

import java.io.Serializable;

/**
 * Modeliza informaci�n servicio destinatario
 */
public class ServicioDestinatario implements Serializable{
	/**
	 * C�digo
	 */
	private String codigo;
	/**
	 * Descripci�n
	 */
	private String descripcion;
	/**
	 * C�digo padre
	 */
	private String codigoPadre;
	
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
	/**
	 * Obtiene codigo padre
	 * @return codigo padre
	 */
	public String getCodigoPadre() {
		return codigoPadre;
	}
	
	/**
	 * Establece codigo padre
	 * @param codigo padre
	 */
	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}	
}
