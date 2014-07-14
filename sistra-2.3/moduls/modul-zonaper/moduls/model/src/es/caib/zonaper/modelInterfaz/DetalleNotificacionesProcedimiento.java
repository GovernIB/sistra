package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * Detalle notificaciones procedimiento en un intervalo de tiempo.
 * @author rsanz
*/
public class DetalleNotificacionesProcedimiento implements Serializable{
	
	/**
	 * Estado notificaciones con acuse.
	 */
	private EstadoNotificacionesProcedimiento notificacionesConAcuse = new EstadoNotificacionesProcedimiento();
	
	/**
	 * Estado notificaciones sin acuse.
	 */
	private EstadoNotificacionesProcedimiento notificacionesSinAcuse = new EstadoNotificacionesProcedimiento();

	public EstadoNotificacionesProcedimiento getNotificacionesConAcuse() {
		return notificacionesConAcuse;
	}

	public void setNotificacionesConAcuse(
			EstadoNotificacionesProcedimiento notificacionesConAcuse) {
		this.notificacionesConAcuse = notificacionesConAcuse;
	}

	public EstadoNotificacionesProcedimiento getNotificacionesSinAcuse() {
		return notificacionesSinAcuse;
	}

	public void setNotificacionesSinAcuse(
			EstadoNotificacionesProcedimiento notificacionesSinAcuse) {
		this.notificacionesSinAcuse = notificacionesSinAcuse;
	}
	
	
	
}
