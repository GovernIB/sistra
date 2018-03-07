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
	 * Nivel de autenticación de sesión en sistra. 
	 */
	private String nivelAutenticacion;
	
	/**
	 * NIF de usuario autenticado de sesión en sistra.
	 */
	private String NifUsuario;
	
	/**
	 * Nombre completo de usuario autenticado de sesión en sistra.
	 */
	private String NombreCompletoUsuario;
	
	/**
	 * codigo de usuario autenticado de sesión en sistra.
	 */
	private String codigoUsuario;

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
	
	public String getNivelAutenticacion() {
		return nivelAutenticacion;
	}

	public void setNivelAutenticacion(String nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}

	public String getNifUsuario() {
		return NifUsuario;
	}

	public void setNifUsuario(String nifUsuario) {
		NifUsuario = nifUsuario;
	}

	public String getNombreCompletoUsuario() {
		return NombreCompletoUsuario;
	}

	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		NombreCompletoUsuario = nombreCompletoUsuario;
	}

	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	
	
	
}
