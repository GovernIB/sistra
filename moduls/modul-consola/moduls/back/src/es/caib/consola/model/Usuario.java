package es.caib.consola.model;

import es.caib.consola.model.types.TypeModoAcceso;

/**
 * Informacion usuario.
 * @author rsanz
 *
 */
public class Usuario {
	/** Username.*/
	private String username;	
	/** Nombre completo.*/
	private String nombreCompleto;
	/** Idioma.*/
	private String idioma;
	/** Organismo que esta editando el usuario.*/
	private Long organismo; 
	/** Modo acceso consola: consulta / edicion. */
	private TypeModoAcceso modoAcceso;
	/** Entorno. */
	private String entorno; 
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public TypeModoAcceso getModoAcceso() {
		return modoAcceso;
	}
	public void setModoAcceso(TypeModoAcceso modoAcceso) {
		this.modoAcceso = modoAcceso;
	}
	public Long getOrganismo() {
		return organismo;
	}
	public void setOrganismo(Long organismo) {
		this.organismo = organismo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEntorno() {
		return entorno;
	}
	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}
	
}
