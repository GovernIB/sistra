package es.caib.sistra.util.loginib;


/**
 * Datos customizados según contexto (sistrafront / zonaperfront).
 *
 * @author Indra
 *
 */
public class LoginPageLoginIBCustom {

	/** Idioma. */
	private String idioma;

	/** Niveles autenticación. */
	private String nivelesAutenticacion;

	/** Login anonimo automatico (sin pasar por LoginIB). */
	private boolean loginAnonimoAuto;

	/** Login clave automatico (pasando por LoginIB). */
	private boolean loginClaveAuto;

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
	 *	Devuelve loginAnonimoAuto.
	 * @return loginAnonimoAuto
	 */
	public boolean isLoginAnonimoAuto() {
		return loginAnonimoAuto;
	}

	/**
	 * Establece loginAnonimoAuto.
	 * @param loginAnonimoAuto loginAnonimoAuto
	 */
	public void setLoginAnonimoAuto(boolean loginAnonimoAuto) {
		this.loginAnonimoAuto = loginAnonimoAuto;
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

}
