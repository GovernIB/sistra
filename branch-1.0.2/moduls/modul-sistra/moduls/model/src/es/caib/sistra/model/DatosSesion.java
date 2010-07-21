package es.caib.sistra.model;

import java.io.Serializable;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * Almacena los datos de usuario de la sesión
 *
 */
public class DatosSesion implements Serializable{
	
	// Datos autenticacion seycon
	private Locale locale;
	private String codigoUsuario;
	private char nivelAutenticacion;
	
	public void setNivelAutenticacion(char nivelAutenticacion) {
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public char getNivelAutenticacion() {
		return nivelAutenticacion;
	}
	public String getCodigoUsuario() {
		return codigoUsuario;
	}
	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	// Datos PAD
	private PersonaPAD personaPAD; // En caso de estar autenticado
	
	public void setPersonaPAD(PersonaPAD p){
		personaPAD = p;
	}
	
	public String getNifUsuario() {
		return (personaPAD != null?personaPAD.getNif():null);
	}
	public String getNombreUsuario() {
		return (personaPAD != null?personaPAD.getNombre():null);
	}
	public String getApellido1Usuario() {
		return (personaPAD != null?personaPAD.getApellido1():null);
	}
	public String getApellido2Usuario() {
		return (personaPAD != null?personaPAD.getApellido2():null);
	}
	public String getNombreCompletoUsuario() {
		return 
			(StringUtils.isNotEmpty(getNombreUsuario())?" " + getNombreUsuario():"") +
			(StringUtils.isNotEmpty(getApellido1Usuario())?" " + getApellido1Usuario():"") + 
			(StringUtils.isNotEmpty(getApellido2Usuario())?" " + getApellido2Usuario():"");
	}
	public String getCodigoPostal() {
		return (personaPAD != null?personaPAD.getCodigoPostal():null);
	}
	public String getDireccion() {
		return (personaPAD != null?personaPAD.getDireccion():null);
	}
	public String getEmail() {
		return (personaPAD != null?personaPAD.getEmail():null);
	}
	public String getMunicipio() {
		return (personaPAD != null?personaPAD.getMunicipio():null);
	}
	public String getProvincia() {
		return (personaPAD != null?personaPAD.getProvincia():null);
	}
	public String getTelefonoFijo() {
		return (personaPAD != null?personaPAD.getTelefonoFijo():null);
	}	
	public String getTelefonoMovil() {
		return (personaPAD != null?personaPAD.getTelefonoMovil():null);
	}	
	public boolean getHabilitarAvisos() {
		return (personaPAD != null?personaPAD.isHabilitarAvisosExpediente():false);
	}
	
}
