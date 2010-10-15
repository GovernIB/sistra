package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * Configuraci�n de avisos de movilidad para el expediente
 * 
 *  Si no se establece este par�metro en la creaci�n del expediente se establecer� esta informaci�n 
 *  autom�ticamente a partir de la entrada BTE que gener� el expediente (en caso de que la entrada tenga 
 *  establecida la configuraci�n de los avisos).
 * 
 * 	Si no se establece informaci�n ni al crear el expediente ni en la entrada que lo genera se 
 * 	har� caso de la configuraci�n de avisos por defecto establecida en la zona personal
 *  
 * 
 */
public class ConfiguracionAvisosExpedientePAD implements Serializable{

	private Boolean habilitarAvisos;
	private String avisoSMS;
	private String avisoEmail;
	
	/**
	 * Indica si se habilitan para el expediente la generacion de avisos sms y email	
	 * @return habilitarAvisos Si es nulo se har� caso de la configuraci�n de avisos establecida en la zona personal
	 */
	public void setHabilitarAvisos(Boolean habilitarAvisos) {
		this.habilitarAvisos = habilitarAvisos;
	}
	
	/**
	 * Indica si se habilitan para el expediente la generacion de avisos sms y email
	 * @return
	 */
	public Boolean getHabilitarAvisos() {
		return habilitarAvisos;
	}
	
	/**
	 * Obtiene email para avisos expediente
	 * @return Email para avisos
	 */
	public String getAvisoEmail() {
		return avisoEmail;
	}
	
	/**
	 * Establece email para avisos expediente. 
	 *
	 * @param avisoEmail Email Expediente
	 */
	public void setAvisoEmail(String avisoEmail) {
		this.avisoEmail = avisoEmail;
	}
	
	/**
	 * Obtiene telefono para avisos expediente
	 * @return
	 */
	public String getAvisoSMS() {
		return avisoSMS;
	}
	
	/**
	 * Establece telefonono para avisos expediente	
	 * @param avisoSMS
	 */
	public void setAvisoSMS(String avisoSMS) {
		this.avisoSMS = avisoSMS;
	}
	

	

}
