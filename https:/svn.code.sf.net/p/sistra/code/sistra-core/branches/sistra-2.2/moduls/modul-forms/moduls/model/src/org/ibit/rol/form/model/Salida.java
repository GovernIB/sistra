package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

/**
 * Permite enlazar un Formulario con un punto de salida.
 */
public class Salida implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Ordenación de la salida respecto al formulario */
    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    /** Punto de salida usado */
    private PuntoSalida punto;

    public PuntoSalida getPunto() {
        return punto;
    }

    public void setPunto(PuntoSalida punto) {
        this.punto = punto;
    }

    private Formulario formulario;

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    private Set propiedades = new HashSet();

    public Set getPropiedades() {
        return propiedades;
    }

    protected void setPropiedades(Set propiedades) {
        this.propiedades = propiedades;
    }

    public void addPropiedad(PropiedadSalida propiedad) {
        propiedad.setSalida(this);
        this.propiedades.add(propiedad);
    }

    public void removePropiedad(PropiedadSalida propiedad) {
        this.propiedades.remove(propiedad);
    }
}
