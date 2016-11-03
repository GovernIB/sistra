package es.caib.sistra.plugins.pagos;

import java.io.Serializable;

/**
 * Sesi�n contra la pasarela de pagos. Debe devolver una url de acceso para redirigir el
 * navegador y un localizador del pago que ser� almacenado como referencia de la sesi�n de pagos en
 * la plataforma de pagos.
 */ 
public class SesionPago implements Serializable{
	/**
	 * Url de acceso a la sesion de pago
	 */
	private String urlSesionPago;
	
	/**
	 * Localizador sesion pago
	 */
	private String localizador;

	
	public SesionPago() {
	}

	/**
	 * Localizador sesion pago
	 * @return Localizador sesi�n
	 */
	public String getLocalizador() {
		return localizador;
	}
	/**
	 * Localizador sesion pago
	 * @param localizador Localizador sesi�n
	 */
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	/**
	 * Url de acceso a la sesion de pago
	 * @return Url acceso
	 */
	public String getUrlSesionPago() {
		return urlSesionPago;
	}

	/**
	 * Url de acceso a la sesion de pago
	 * @param urlSesionPago Url acceso
	 */
	public void setUrlSesionPago(String urlSesionPago) {
		this.urlSesionPago = urlSesionPago;
	}
	
}
