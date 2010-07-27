package es.caib.zonaper.model;

import java.io.Serializable;
import java.util.Locale;

import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * Almacena los datos de usuario de la sesión
 *
 */
public class DatosSesion implements Serializable{
	
	private Locale locale;
	private char nivelAutenticacion;	
	
	private PersonaPAD personaPAD;
	
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}
	
	public void setPersonaPAD(PersonaPAD p){
		personaPAD = p;
	}
	
	public String getNifUsuario() {		
		return (personaPAD!=null?personaPAD.getNif():null);
	}
	
	public String getNombreUsuario() {
		return (personaPAD!=null?personaPAD.getNombreCompleto():null);		
	}
	
	public String getCodigoUsuario() {
		return (personaPAD!=null?personaPAD.getUsuarioSeycon():null);
	}
	
	
}
