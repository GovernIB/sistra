package es.caib.sistra.loginibclient;

/**
 * Peticion de inicio de sesion.
 *
 * @author Indra
 *
 */
public class DatosInicioSesion {

	/** Idioma. */
	private String idioma;
	/** Aplicacion. */
	private String aplicacion;
	/** Entidad. */
	private String entidad;
	/** Forzar autenticacion. */
	private boolean forzarAutenticacion;
	/** Auditar autenticacion. */
	private boolean auditar;
	/** Metodos autenticacion permitidos en LoginIB. */
	private String metodosAutenticacion;
	/** QAA: 1 (bajo), 2 (medio), 3 (alto). */
	private int qaa;
	/** Url callback. */
	private String urlCallback;
	/** Url callback error. */
	private String urlCallbackError;
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
	 *	Devuelve metodosAutenticacion.
	 * @return metodosAutenticacion
	 */
	public String getMetodosAutenticacion() {
		return metodosAutenticacion;
	}
	/**
	 * Establece metodosAutenticacion.
	 * @param metodosAutenticacion metodosAutenticacion
	 */
	public void setMetodosAutenticacion(String metodosAutenticacion) {
		this.metodosAutenticacion = metodosAutenticacion;
	}
	/**
	 *	Devuelve auditar.
	 * @return auditar
	 */
	public boolean isAuditar() {
		return auditar;
	}
	/**
	 * Establece auditar.
	 * @param auditar auditar
	 */
	public void setAuditar(boolean auditar) {
		this.auditar = auditar;
	}
	/**
	 *	Devuelve aplicacion.
	 * @return aplicacion
	 */
	public String getAplicacion() {
		return aplicacion;
	}
	/**
	 * Establece aplicacion.
	 * @param aplicacion aplicacion
	 */
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

}
