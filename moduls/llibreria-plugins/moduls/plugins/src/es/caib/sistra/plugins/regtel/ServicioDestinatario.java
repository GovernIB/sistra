package es.caib.sistra.plugins.regtel;

import java.io.Serializable;

/**
 * Modeliza información servicio destinatario
 */
public class ServicioDestinatario implements Serializable{
	/**
	 * Código
	 */
	private String codigo;
	/**
	 * Descripción
	 */
	private String descripcion;
	/**
	 * Código padre
	 */
	private String codigoPadre;
	
	/**
	 * Obtiene código
	 * @return Código
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * Establece código
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * Obtiene descripción
	 * @return Descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece descripción
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
