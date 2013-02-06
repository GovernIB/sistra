package es.caib.sistra.plugins.pagos;

import java.io.Serializable;

/**
 * Información de la sesión en sistra para retornar
 * de la pasarela de pagos
 */
public class SesionSistra implements Serializable{

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocará a la función comprobarEstadoSesionPago para actualizar el asistente de tramitación. 
	 */
	private String urlRetornoSistra;
	
	/**
	 * Url de mantenimiento de sesión en sistra. Como método para mantener la sesión activa en sistra se puede incluir
	 * esta url en las páginas de la pasarela de pagos.
	 */
	private String urlMantenimientoSesionSistra;

	/**
	 * Url de mantenimiento de sesión en sistra. Como método para mantener la sesión activa en sistra se puede incluir
	 * esta url en las páginas de la pasarela de pagos.
	 */
	public String getUrlMantenimientoSesionSistra() {
		return urlMantenimientoSesionSistra;
	}

	/**
	 * Url de mantenimiento de sesión en sistra. Como método para mantener la sesión activa en sistra se puede incluir
	 * esta url en las páginas de la pasarela de pagos.
	 */
	public void setUrlMantenimientoSesionSistra(String urlMantenimientoSesionSistra) {
		this.urlMantenimientoSesionSistra = urlMantenimientoSesionSistra;
	}

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocará a la función comprobarEstadoSesionPago para actualizar el asistente de tramitación. 
	 */
	public String getUrlRetornoSistra() {
		return urlRetornoSistra;
	}

	/**
	 * Url de retorno a sistra tras finalizar en la pasarela de pagos. Al retornar de la pasarela de pagos
	 * se invocará a la función comprobarEstadoSesionPago para actualizar el asistente de tramitación. 
	 */
	public void setUrlRetornoSistra(String urlRetornoSistra) {
		this.urlRetornoSistra = urlRetornoSistra;
	}
	
	
	
}
