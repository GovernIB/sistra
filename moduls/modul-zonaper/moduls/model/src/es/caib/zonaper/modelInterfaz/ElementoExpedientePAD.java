package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * Elemento de un expediente: tr�mite, evento o notificaci�n
 * @see es.caib.zonaper.modelInterfaz.EventoExpedientePAD
 * @see es.caib.zonaper.modelInterfaz.TramiteExpedientePAD
 * @see es.caib.zonaper.modelInterfaz.NotificacionExpedientePAD
 */
public abstract class ElementoExpedientePAD implements Serializable{
	
	/**
	 * Formato de la fecha que identificar� al evento 
	 * dd/MM/yyyy HH:mm:ss
	 */
	public final static String FORMATO_FECHA = "dd/MM/yyyy HH:mm:ss";
	
	/**
	 * Fecha del elemento
	 */
	private String fecha;
	
	/**
	 * Devuelve la fecha del evento,  dd/MM/yyyy HH:mm:ss
	 * @return String en formato dd/MM/yyyy HH:mm:ss
	 */
	public String getFecha()
	{
		return fecha;
	}
	/**
	 * Establece la fecha del evento, par�metro obligatorio. 
	 * @param fecha String en formato dd/MM/yyyy HH:mm:ss
	 */
	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}	

}
