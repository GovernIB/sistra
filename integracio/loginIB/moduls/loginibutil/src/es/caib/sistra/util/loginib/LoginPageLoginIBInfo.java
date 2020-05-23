package es.caib.sistra.util.loginib;


/**
 * Información necesaria para login page de LoginIB.
 *
 * @author Indra
 *
 */
public class LoginPageLoginIBInfo {

	/** Idioma. */
	private String idioma;

	/** Titulo pagina login. */
	private String titulo;

	/** Css entidad. */
	private String css;

	/** Indica si hay que redirigir a LoginIB (a urlLoginIB). */
	private boolean redirigirLoginIB;

	/** Url para redirigir a LoginIB. */
	private String urlLoginIB;

	/** Indica si hay error en proceso login. */
	private boolean loginError;

	/** Indica si se debe realizar login con el username y password indicado. */
	private boolean realizarLogin;

	/** Username para realizar el login. */
	private String usernameNameLogin;

	/** Password para realizar el login. */
	private String passwordLogin;


	/**
	 *	Devuelve loginError.
	 * @return loginError
	 */
	public boolean isLoginError() {
		return loginError;
	}

	/**
	 * Establece loginError.
	 * @param loginError loginError
	 */
	public void setLoginError(boolean loginError) {
		this.loginError = loginError;
	}

	/**
	 *	Devuelve loginClave.
	 * @return loginClave
	 */
	public boolean isRealizarLogin() {
		return realizarLogin;
	}

	/**
	 * Establece loginClave.
	 * @param loginClave loginClave
	 */
	public void setRealizarLogin(boolean loginClave) {
		this.realizarLogin = loginClave;
	}

	/**
	 *	Devuelve urlLoginIB.
	 * @return urlLoginIB
	 */
	public String getUrlLoginIB() {
		return urlLoginIB;
	}

	/**
	 * Establece urlLoginIB.
	 * @param urlLoginIB urlLoginIB
	 */
	public void setUrlLoginIB(String urlLoginIB) {
		this.urlLoginIB = urlLoginIB;
	}

	/**
	 *	Devuelve ticketLoginIB.
	 * @return ticketLoginIB
	 */
	public String getPasswordLogin() {
		return passwordLogin;
	}

	/**
	 * Establece ticketLoginIB.
	 * @param ticketLoginIB ticketLoginIB
	 */
	public void setPasswordLogin(String ticketLoginIB) {
		this.passwordLogin = ticketLoginIB;
	}

	/**
	 *	Devuelve titulo.
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Establece titulo.
	 * @param titulo titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 *	Devuelve css.
	 * @return css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * Establece css.
	 * @param css css
	 */
	public void setCss(String css) {
		this.css = css;
	}

	/**
	 *	Devuelve redirigirLoginIB.
	 * @return redirigirLoginIB
	 */
	public boolean isRedirigirLoginIB() {
		return redirigirLoginIB;
	}

	/**
	 * Establece redirigirLoginIB.
	 * @param redirigirLoginIB redirigirLoginIB
	 */
	public void setRedirigirLoginIB(boolean redirigirLoginIB) {
		this.redirigirLoginIB = redirigirLoginIB;
	}

	/**
	 *	Devuelve usernameTicket.
	 * @return usernameTicket
	 */
	public String getUsernameNameLogin() {
		return usernameNameLogin;
	}

	/**
	 * Establece usernameTicket.
	 * @param usernameTicket usernameTicket
	 */
	public void setUsernameNameLogin(String usernameTicket) {
		this.usernameNameLogin = usernameTicket;
	}

	/**
	 *	Devuelve idioma.
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Establece idioma.
	 * @param idioma idioma
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

}
