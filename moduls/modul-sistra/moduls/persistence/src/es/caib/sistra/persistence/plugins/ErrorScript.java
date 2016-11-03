package es.caib.sistra.persistence.plugins;

/**
 * Plugin que permite a los scripts establecer un mensaje de error particularizado
 *
 */
public class ErrorScript {

	/**
	 * Indica si existe error
	 */
	private boolean existeError=false;
	
	/**
	 * Código error particularizado. Para establecer error por codigo mensaje.
	 */
	private String codigoError;
	
	/**
	 * Mensaje error particularizado. Para establecer error directamente con el texto.
	 */
	private String mensajeDinamicoError;
	
	
	public String getMensajeError() {
		return codigoError;
	}

	public void setMensajeError(String codigoError) {
		this.codigoError = codigoError;
	}
	
	

	public boolean isExisteError() {
		return existeError;
	}

	public void setExisteError(boolean existeError) {
		this.existeError = existeError;
	}

	public String getMensajeDinamicoError() {
		return mensajeDinamicoError;
	}

	public void setMensajeDinamicoError(String mensajeDinamicoError) {
		this.mensajeDinamicoError = mensajeDinamicoError;
	}
	
}
