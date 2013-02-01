package es.caib.bantel.modelInterfaz;

/**
 * 
 * Referencia a una entrada BTE: numero entrada + clave de acceso 
 *
 */
public class ReferenciaEntradaBTE {
	
	private String numeroEntrada;
	
	private String claveAcceso;

	/**
	 * Obtiene clave acceso
	 * @return Clave de acceso
	 */
	public String getClaveAcceso() {
		return claveAcceso;
	}

	/**
	 * Establece clave de acceso
	 * @param claveAcceso Clave de acceso
	 */
	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	/**
	 * Obtiene numero de entrada
	 * @return Numero de entrada
	 */
	public String getNumeroEntrada() {
		return numeroEntrada;
	}

	/**
	 * Establece numero de entrada
	 *  
	 * @param numeroEntrada Numero de entrada
	 */
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}

}
