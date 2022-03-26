package es.caib.sistra.plugins.login;

/**
 * Peticion de inicio de sesion de autenticacion remota.
 *
 * @author Indra
 *
 */
public class PeticionInicioSesionAutenticacion {

	/** Idioma. */
	private String idioma;
	/** Entidad. */
	private String entidad;
	/** Forzar autenticacion. */
	private boolean forzarAutenticacion;
	/** Auditar autenticacion. */
	private Boolean auditar;
	/** Niveles autenticacion (C/U/A). */
	private String nivelesAutenticacion;
	/** QAA: 1 (bajo), 2 (medio), 3 (alto). */
	private int qaa;
	/** Url callback. */
	private String urlCallback;
	/** Url callback error. */
	private String urlCallbackError;
	/** Login Clave Auto. */
	private boolean loginClaveAuto;
	/** Indica si filtra autenticación usu/pass. */
	private boolean permitirUserPass;
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
	/**
	 *	Devuelve entidad.
	 * @return entidad
	 */
	public String getEntidad() {
		return entidad;
	}
	/**
	 * Establece entidad.
	 * @param entidad entidad
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 *	Devuelve nivelesAutenticacion.
	 * @return nivelesAutenticacion
	 */
	public String getNivelesAutenticacion() {
		return nivelesAutenticacion;
	}
	/**
	 * Establece nivelesAutenticacion.
	 * @param nivelesAutenticacion nivelesAutenticacion
	 */
	public void setNivelesAutenticacion(String nivelesAutenticacion) {
		this.nivelesAutenticacion = nivelesAutenticacion;
	}
	/**
	 *	Devuelve qaa.
	 * @return qaa
	 */
	public int getQaa() {
		return qaa;
	}
	/**
	 * Establece qaa.
	 * @param qaa qaa
	 */
	public void setQaa(int qaa) {
		this.qaa = qaa;
	}
	/**
	 *	Devuelve urlCallback.
	 * @return urlCallback
	 */
	public String getUrlCallback() {
		return urlCallback;
	}
	/**
	 * Establece urlCallback.
	 * @param urlCallback urlCallback
	 */
	public void setUrlCallback(String urlCallback) {
		this.urlCallback = urlCallback;
	}
	/**
	 *	Devuelve urlCallbackError.
	 * @return urlCallbackError
	 */
	public String getUrlCallbackError() {
		return urlCallbackError;
	}
	/**
	 * Establece urlCallbackError.
	 * @param urlCallbackError urlCallbackError
	 */
	public void setUrlCallbackError(String urlCallbackError) {
		this.urlCallbackError = urlCallbackError;
	}
	/**
	 *	Devuelve forzarAutenticacion.
	 * @return forzarAutenticacion
	 */
	public boolean isForzarAutenticacion() {
		return forzarAutenticacion;
	}
	/**
	 * Establece forzarAutenticacion.
	 * @param forzarAutenticacion forzarAutenticacion
	 */
	public void setForzarAutenticacion(boolean forzarAutenticacion) {
		this.forzarAutenticacion = forzarAutenticacion;
	}
	/**
	 *	Devuelve auditar.
	 * @return auditar
	 */
	public Boolean getAuditar() {
		return auditar;
	}
	/**
	 * Establece auditar.
	 * @param auditar auditar
	 */
	public void setAuditar(Boolean auditar) {
		this.auditar = auditar;
	}
	/**
	 *	Devuelve loginClaveAuto.
	 * @return loginClaveAuto
	 */
	public boolean isLoginClaveAuto() {
		return loginClaveAuto;
	}
	/**
	 * Establece loginClaveAuto.
	 * @param loginClaveAuto loginClaveAuto
	 */
	public void setLoginClaveAuto(boolean loginClaveAuto) {
		this.loginClaveAuto = loginClaveAuto;
	}
	/**
	 *	Devuelve permitirUserPass.
	 * @return permitirUserPass
	 */
	public boolean isPermitirUserPass() {
		return permitirUserPass;
	}
	/**
	 * Establece permitirUserPass.
	 * @param permitirUserPass permitirUserPass
	 */
	public void setPermitirUserPass(boolean permitirUserPass) {
		this.permitirUserPass = permitirUserPass;
	}


}
