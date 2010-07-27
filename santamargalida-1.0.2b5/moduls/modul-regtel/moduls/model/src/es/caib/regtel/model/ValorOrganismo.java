package es.caib.regtel.model;

import java.io.Serializable;

/**
 * Modeliza valores que puede devolver el organismo
 * para las listas de valores (oficinas, asuntos, etc.)
 */
public class ValorOrganismo implements Serializable{
	/**
	 * Código
	 */
	private String codigo;
	/**
	 * Descripción
	 */
	private String descripcion;
	
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
}
