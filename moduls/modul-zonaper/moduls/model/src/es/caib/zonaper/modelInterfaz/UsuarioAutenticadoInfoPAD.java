package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;

/**
 * Información del usuario autenticado para el que se ha de generar ticket de autenticacion
 * 
 */
public class UsuarioAutenticadoInfoPAD implements Serializable{

	private String apellido1;
	private String apellido2;
	private String autenticacion;
	private String email;
	private String metodoAutenticacion;
	private String nif;
	private String nombre;
	private String username;
	
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getAutenticacion() {
		return autenticacion;
	}
	public void setAutenticacion(String autenticacion) {
		this.autenticacion = autenticacion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}
	public void setMetodoAutenticacion(String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	


}
