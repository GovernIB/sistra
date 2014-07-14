package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * Estado notificaciones procedimiento en un intervalo de tiempo.
 * @author rsanz
*/
public class EstadoNotificacionesProcedimiento implements Serializable{
	
	/**
	 * Nuevas notificaciones.
	 */
	private int nuevas;
	
	/**
	 * Notificaciones rechazadas.
	 */
	private int rechazadas;
	
	/**
	 * Notificaciones aceptadas.
	 */
	private int aceptadas;

	public int getNuevas() {
		return nuevas;
	}

	public void setNuevas(int nuevas) {
		this.nuevas = nuevas;
	}

	public int getRechazadas() {
		return rechazadas;
	}

	public void setRechazadas(int rechazadas) {
		this.rechazadas = rechazadas;
	}

	public int getAceptadas() {
		return aceptadas;
	}

	public void setAceptadas(int aceptadas) {
		this.aceptadas = aceptadas;
	}
	
}
