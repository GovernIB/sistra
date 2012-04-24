package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representa un validador de firma.
 */
public class ValidadorFirma implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Nombre de la classe que implementará la validación. */
    private String implementacion;

    public String getImplementacion() {
        return implementacion;
    }

    public void setImplementacion(String implementacion) {
        this.implementacion = implementacion;
    }
}
