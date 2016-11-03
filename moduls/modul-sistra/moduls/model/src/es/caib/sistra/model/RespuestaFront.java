package es.caib.sistra.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Las llamadas del Front a TramiteProcessor devuelven un objeto 
 * de este tipo. 
 * El Front deberá visualizar la pantalla a partir de este objeto.
 * 
 */
public class RespuestaFront  implements Serializable{

	/**
	 * Información de la instancia del trámite en un momento dado 
	 */
	private TramiteFront informacionTramite;
	/**
	 * Permite indicar mensajes
	 */
	private MensajeFront mensaje;
	/**
	 * Parámetros del paso actual
	 */
	private HashMap parametros;

	public TramiteFront getInformacionTramite() {
		return informacionTramite;
	}

	public void setInformacionTramite(TramiteFront informacionTramite) {
		this.informacionTramite = informacionTramite;
	}

	public MensajeFront getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeFront mensaje) {
		this.mensaje = mensaje;
	}

	public HashMap getParametros() {
		return parametros;
	}

	public void setParametros(HashMap parametros) {
		this.parametros = parametros;
	}		
	
}
