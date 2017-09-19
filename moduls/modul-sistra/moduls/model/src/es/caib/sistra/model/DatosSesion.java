package es.caib.sistra.model;

import java.io.Serializable;
import java.util.Locale;

import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * Almacena los datos de usuario de la sesión
 *
 */
public class DatosSesion implements Serializable{
	
	// Nivel autenticacion
	private char nivelAutenticacion;
	
	// Autenticacion usuario
	private String codigoUsuario;
	
	// Perfil acceso: CIUDADANO / DELEGADO
	private String perfilAcceso;
		
	// Usuario autenticado o en caso de delegacion la entidad delegada
	private PersonaPAD personaPAD;
	
	// En caso de delegacion seria el usuario delegado (usuario autenticado)
	private PersonaPAD delegadoPAD;
	
	// En caso de delegacion indica los permisos de delegacion
	private String permisosDelegacion;
	
	// Idioma
	private Locale locale;

	// Datos representante provenientes del certificado (usuario autenticado con certificado)
	private DatosRepresentanteCertificado datosRepresentanteCertificado;
		
	
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
	
	public void setPersonaPAD(PersonaPAD p){
		personaPAD = p;
	}
	
	public String getNifUsuario() {
		return (personaPAD != null?personaPAD.getNif():null);
	}
	
	public String getNombreCompletoUsuario() {
		return (personaPAD != null?personaPAD.getNombreCompleto():null);			
	}
	
	public String getCodigoDelegado() {
		return (delegadoPAD != null?delegadoPAD.getUsuarioSeycon():null);
	}
	
	public String getNifDelegado() {
		return (delegadoPAD != null?delegadoPAD.getNif():null);
	}
	
	public String getNombreCompletoDelegado() {
		return (delegadoPAD != null?delegadoPAD.getNombreCompleto():null);			
	}
	
	public String getNombreDelegado() {
		return (delegadoPAD != null?delegadoPAD.getNombre():null);			
	}
	
	public String getApellido1Delegado() {
		return (delegadoPAD != null?delegadoPAD.getApellido1():null);			
	}
	
	public String getApellido2Delegado() {
		return (delegadoPAD != null?delegadoPAD.getApellido2():null);			
	}
	
	public PersonaPAD getDelegadoPAD() {
		return delegadoPAD;
	}
	public void setDelegadoPAD(PersonaPAD delegadoPAD) {
		this.delegadoPAD = delegadoPAD;
	}
	public String getPerfilAcceso() {
		return perfilAcceso;
	}	
	public void setPerfilAcceso(String perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}	
	public PersonaPAD getPersonaPAD() {
		return personaPAD;
	}
	public String getPermisosDelegacion() {
		return permisosDelegacion;
	}
	public void setPermisosDelegacion(String permisosDelegacion) {
		this.permisosDelegacion = permisosDelegacion;
	}
	public DatosRepresentanteCertificado getDatosRepresentanteCertificado() {
		return datosRepresentanteCertificado;
	}
	public void setDatosRepresentanteCertificado(
			DatosRepresentanteCertificado datosRepresentanteCertificado) {
		this.datosRepresentanteCertificado = datosRepresentanteCertificado;
	}
}
