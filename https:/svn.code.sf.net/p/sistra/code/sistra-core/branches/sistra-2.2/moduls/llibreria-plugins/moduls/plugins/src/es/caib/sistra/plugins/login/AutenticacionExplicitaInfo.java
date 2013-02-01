package es.caib.sistra.plugins.login;

/**
 * 
 * Modeliza información para autenticación explícita: usuario y password
 *
 */
public class AutenticacionExplicitaInfo {
	/**
	 * Usuario
	 */
	private String user;
	/**
	 * Password
	 */
	private String password;
	/**
	 * Password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Usuario
	 */
	public String getUser() {
		return user;
	}
	/**
	 * Usuario
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	

}
