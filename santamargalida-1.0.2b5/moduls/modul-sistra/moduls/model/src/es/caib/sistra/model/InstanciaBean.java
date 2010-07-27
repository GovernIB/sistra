package es.caib.sistra.model;

import java.util.Locale;

public class InstanciaBean implements java.io.Serializable {

	private String idTramite;
	private int version;
	private Locale locale;
	private char nivelAutenticacion; // Si esta vac�o todav�a no se ha realizado login 
	private String idPersistencia=null; // Si es nulo no se ha guardado todav�a en Pad
	
	
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Locale getLocale()
	{
		return locale;
	}
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
	
}
