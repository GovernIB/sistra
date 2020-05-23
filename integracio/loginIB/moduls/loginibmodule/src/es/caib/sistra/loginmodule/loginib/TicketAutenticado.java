package es.caib.sistra.loginmodule.loginib;

import java.util.Date;

/**
 * Ticket autenticado.
 *
 * @author Indra
 *
 */
public class TicketAutenticado {

   /** Ultima vez que se ha usado el ticket para (re)autenticar. */
	private Date fechaLogin;
	/** Principal. */
    private LoginIBPrincipal principalAut;


	/**
	 * @return the principalAut
	 */
	public LoginIBPrincipal getPrincipalAut() {
		return principalAut;
	}
	/**
	 * @param principalAut the principalAut to set
	 */
	public void setPrincipalAut(LoginIBPrincipal principalAut) {
		this.principalAut = principalAut;
	}
	/**
	 *	Devuelve fechaLogin.
	 * @return fechaLogin
	 */
	public Date getFechaLogin() {
		return fechaLogin;
	}
	/**
	 * Establece fechaLogin.
	 * @param fechaLogin fechaLogin
	 */
	public void setFechaLogin(Date fechaLogin) {
		this.fechaLogin = fechaLogin;
	}

}
