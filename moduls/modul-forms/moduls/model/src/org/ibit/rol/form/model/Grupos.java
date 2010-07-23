package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representaci&oacute; d'un idioma.
 */
public class Grupos implements Serializable {

    private String codigo;
    private String nombre;
    private String descripcion;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
}
