package es.caib.sistra.loginmodule.loginib;

import java.io.Serializable;
import java.security.Principal;

@SuppressWarnings("serial")
public class LoginIBPrincipal implements Principal, Serializable {

	/** Identificador sesión autenticación en LoginIB. */
	private String idSesionAutenticacion;
	/** Username. */
	private String name;
	/** Autenticación Sistra: A (Anónimo) / C (Certificado) / U (Usuario). */
	private char autenticacionSistra;
	/** Método de autenticación utilizado en LoginIB. */
	private String metodoAutenticacion;
	/** Nivel de autenticación (qaa). */
	private Integer qaaAutenticacion;
	/** Datos persona autenticada. */
    private Persona autenticado;
    /** En caso de que exista representación, indica los datos del representante. */
    private Persona representante;


    /**
     * Constructor.
     * @param idSesionAutenticacion
     * @param name
     * @param autenticacionSistra
     * @param metodoAutenticacion
     * @param qaaAutenticacion
     * @param autenticado
     * @param representante
     */
    public LoginIBPrincipal(String name,
			char autenticacionSistra, String idSesionAutenticacion, String metodoAutenticacion,
			Integer qaaAutenticacion, Persona autenticado, Persona representante) {
		super();
		this.idSesionAutenticacion = idSesionAutenticacion;
		this.name = name;
		this.autenticacionSistra = autenticacionSistra;
		this.metodoAutenticacion = metodoAutenticacion;
		this.qaaAutenticacion = qaaAutenticacion;
		this.autenticado = autenticado;
		this.representante = representante;
	}

	/**
     * Función a implementar en Principal.
     */
    public String getName() {
		return name;
	}

	/**
	 *	Devuelve idSesionAutenticacion.
	 * @return idSesionAutenticacion
	 */
	public String getIdSesionAutenticacion() {
		return idSesionAutenticacion;
	}
	/**
	 * Establece idSesionAutenticacion.
	 * @param idSesionAutenticacion idSesionAutenticacion
	 */
	public void setIdSesionAutenticacion(String idSesionAutenticacion) {
		this.idSesionAutenticacion = idSesionAutenticacion;
	}
	/**
	 *	Devuelve autenticacionSistra.
	 * @return autenticacionSistra
	 */
	public char getAutenticacionSistra() {
		return autenticacionSistra;
	}
	/**
	 * Establece autenticacionSistra.
	 * @param autenticacionSistra autenticacionSistra
	 */
	public void setAutenticacionSistra(char autenticacionSistra) {
		this.autenticacionSistra = autenticacionSistra;
	}
	/**
	 *	Devuelve metodoAutenticacion.
	 * @return metodoAutenticacion
	 */
	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}
	/**
	 * Establece metodoAutenticacion.
	 * @param metodoAutenticacion metodoAutenticacion
	 */
	public void setMetodoAutenticacion(String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}
	/**
	 *	Devuelve qaaAutenticacion.
	 * @return qaaAutenticacion
	 */
	public Integer getQaaAutenticacion() {
		return qaaAutenticacion;
	}
	/**
	 * Establece qaaAutenticacion.
	 * @param qaaAutenticacion qaaAutenticacion
	 */
	public void setQaaAutenticacion(Integer qaaAutenticacion) {
		this.qaaAutenticacion = qaaAutenticacion;
	}
	/**
	 *	Devuelve autenticado.
	 * @return autenticado
	 */
	public Persona getAutenticado() {
		return autenticado;
	}
	/**
	 *	Devuelve representante.
	 * @return representante
	 */
	public Persona getRepresentante() {
		return representante;
	}
	/**
	 * Establece autenticado.
	 * @param autenticado autenticado
	 */
	public void setAutenticado(Persona autenticado) {
		this.autenticado = autenticado;
	}

	/**
	 * Establece representante.
	 * @param representante representante
	 */
	public void setRepresentante(Persona representante) {
		this.representante = representante;
	}

}
