package es.caib.bantel.front.util;

/**
 * Modeliza valores que puede devolver el organismo
 * para las listas de valores (oficinas, asuntos, etc.)
 */
public class ValorOrganismo {
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
