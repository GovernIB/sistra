package es.caib.sistra.loginmodule.loginib;

import java.io.Serializable;

/**
 * Datos persona.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class Persona implements Serializable {

	/** Nif. */
    private String nif;
    /** Nombre. */
    private String nombre;
    /** Apellidos. */
    private String apellidos;
    /** Apellido 1. */
    private String apellido1;
    /** Apellido 2. */
    private String apellido2;

	/**
	 *	Devuelve nif.
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * Establece nif.
	 * @param nif nif
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 *	Devuelve nombre.
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Establece nombre.
	 * @param nombre nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 *	Devuelve apellidos.
	 * @return apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * Establece apellidos.
	 * @param apellidos apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 *	Devuelve apellido1.
	 * @return apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}
	/**
	 * Establece apellido1.
	 * @param apellido1 apellido1
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	/**
	 *	Devuelve apellido2.
	 * @return apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}
	/**
	 * Establece apellido2.
	 * @param apellido2 apellido2
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Obtiene nombre completo.
	 */
	public String getNombreCompleto() {
		String res = getNombre();
		if (getApellidos() != null && getApellidos().length() > 0) {
			res += " " + getApellidos();
		}
		return res;
	}

}
