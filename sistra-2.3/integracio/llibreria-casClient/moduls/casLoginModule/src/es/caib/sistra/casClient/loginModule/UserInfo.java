package es.caib.sistra.casClient.loginModule;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Informacion del usuario devuelta por la clase autenticadora
 * 
 */
public class UserInfo {
	
	private String user;
	private String nombreApellidos;
	private String nif;
	private List<String> roles = new ArrayList<String>();
	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombreApellidos() {
		return nombreApellidos;
	}
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	
	
}
