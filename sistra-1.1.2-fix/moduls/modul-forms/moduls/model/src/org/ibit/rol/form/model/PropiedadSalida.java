package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Parámetro necesario para un punto de salida.
 */
public class PropiedadSalida implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** nombre de la propiedad. */
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** el valor de la propiedad */
    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    /** Inidica si el valor debe és un valor final o és una expresión que deba ejecutarse */
    private boolean expresion;

    public boolean isExpresion() {
        return expresion;
    }

    public void setExpresion(boolean expresion) {
        this.expresion = expresion;
    }

    private Salida salida;

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

    private Archivo plantilla;

    public Archivo getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Archivo plantilla) {
        this.plantilla = plantilla;
    }
}
