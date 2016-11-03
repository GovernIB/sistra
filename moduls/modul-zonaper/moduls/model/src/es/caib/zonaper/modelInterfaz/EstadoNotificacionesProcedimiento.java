package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.List;

/**
 * Estado notificaciones procedimiento en un intervalo de tiempo.
 * Devuelve id expediente de las notificaciones.
 * @author rsanz
*/
public class EstadoNotificacionesProcedimiento implements Serializable{
	
	/**
	 * Nuevas notificaciones.
	 */
	private List nuevas;
	
	/**
	 * Notificaciones rechazadas.
	 */
	private List rechazadas;
	
	/**
	 * Notificaciones aceptadas.
	 */
	private List aceptadas;

	public List getNuevas() {
		return nuevas;
	}

	public void setNuevas(List nuevas) {
		this.nuevas = nuevas;
	}

	public List getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(List rechazadas) {
		this.rechazadas = rechazadas;
	}

	public List getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(List aceptadas) {
		this.aceptadas = aceptadas;
	}

	
	
}
