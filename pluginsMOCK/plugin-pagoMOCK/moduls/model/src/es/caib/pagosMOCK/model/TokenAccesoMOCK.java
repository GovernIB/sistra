package es.caib.pagosMOCK.model;

import java.util.Date;

public class TokenAccesoMOCK {
	
	private String token;
	private Date tiempoLimite;
	private String localizador;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	public Date getTiempoLimite() {
		return tiempoLimite;
	}
	public void setTiempoLimite(Date tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

}
