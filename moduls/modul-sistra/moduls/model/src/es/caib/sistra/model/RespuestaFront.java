package es.caib.sistra.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Las llamadas del Front a TramiteProcessor devuelven un objeto 
 * de este tipo. 
 * El Front deber� visualizar la pantalla a partir de este objeto.
 * 
 */
public class RespuestaFront  implements Serializable{

	/**
	 * Informaci�n de la instancia del tr�mite en un momento dado 
	 */
	private TramiteFront informacionTramite;
	/**
	 * Permite indicar mensajes
	 */
	private MensajeFront mensaje;
	/**
	 * Par�metros del paso actual
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
