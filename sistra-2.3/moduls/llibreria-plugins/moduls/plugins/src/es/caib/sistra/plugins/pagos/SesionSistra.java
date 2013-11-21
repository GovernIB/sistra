package es.caib.sistra.plugins.pagos;

import java.io.Serializable;

/**
 * Informaci�n de la sesi�n en sistra para retornar
 * de la pasarela de pagos
 */
public class SesionSistra implements Serializable{

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocar� a la funci�n comprobarEstadoSesionPago para actualizar el asistente de tramitaci�n. 
	 */
	private String urlRetornoSistra;
	
	/**
	 * Url de mantenimiento de sesi�n en sistra. Como m�todo para mantener la sesi�n activa en sistra se puede incluir
	 * esta url en las p�ginas de la pasarela de pagos.
	 */
	private String urlMantenimientoSesionSistra;

	/**
	 * Url de mantenimiento de sesi�n en sistra. Como m�todo para mantener la sesi�n activa en sistra se puede incluir
	 * esta url en las p�ginas de la pasarela de pagos.
	 */
	public String getUrlMantenimientoSesionSistra() {
		return urlMantenimientoSesionSistra;
	}

	/**
	 * Url de mantenimiento de sesi�n en sistra. Como m�todo para mantener la sesi�n activa en sistra se puede incluir
	 * esta url en las p�ginas de la pasarela de pagos.
	 */
	public void setUrlMantenimientoSesionSistra(String urlMantenimientoSesionSistra) {
		this.urlMantenimientoSesionSistra = urlMantenimientoSesionSistra;
	}

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocar� a la funci�n comprobarEstadoSesionPago para actualizar el asistente de tramitaci�n. 
	 */
	public String getUrlRetornoSistra() {
		return urlRetornoSistra;
	}

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocar� a la funci�n comprobarEstadoSesionPago para actualizar el asistente de tramitaci�n. 
	 */
	public void setUrlRetornoSistra(String urlRetornoSistra) {
		this.urlRetornoSistra = urlRetornoSistra;
	}
	
	
	
}
