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
	
	private String perfilAcceso; // CIUDADANO / DELEGADO ENTIDAD
	
	private PersonaPAD personaPAD;
	
	private String permitirDelegacion; // Indica si para la  entidad esta habilitada la delegacion (S/N)
	
	// En caso de delegacion, aqui tendriamos los datos de la entidad
	private PersonaPAD entidadPAD;
	private String permisosDelegacion;
		
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
	
	public PersonaPAD getEntidadPAD() {
		return entidadPAD;
	}
	
	public void setEntidadPAD(PersonaPAD entidadPAD) {
		this.entidadPAD = entidadPAD;
	}
	
	public String getNifEntidad() {		
		return (entidadPAD!=null?entidadPAD.getNif():null);
	}
	
	public String getNombreEntidad() {
		return (entidadPAD!=null?entidadPAD.getNombreCompleto():null);		
	}
	
	public String getCodigoEntidad() {
		return (entidadPAD!=null?entidadPAD.getUsuarioSeycon():null);
	}
	public String getPerfilAcceso() {
		return perfilAcceso;
	}
	public void setPerfilAcceso(String perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}
	public String getPermisosDelegacion() {
		return permisosDelegacion;
	}
	public void setPermisosDelegacion(String permisosDelegacion) {
		this.permisosDelegacion = permisosDelegacion;
	}
	public String getPermitirDelegacion() {
		return permitirDelegacion;
	}
	public void setPermitirDelegacion(String permitirDelegacion) {
		this.permitirDelegacion = permitirDelegacion;
	}
	
}
