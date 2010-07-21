package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.*;

/**
 * Representa un posible punto de salida para un formulario.
 */
public class PuntoSalida implements Serializable {

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

    /** Nombre de la classe que implementará el conector de salida. */
    private String implementacion;

    public String getImplementacion() {
        return implementacion;
    }

    public void setImplementacion(String implementacion) {
        this.implementacion = implementacion;
    }

    private Set salidas = new HashSet();

    public Set getSalidas() {
        return salidas;
    }

    public void setSalidas(Set salidas) {
        this.salidas = salidas;
    }

    public void addSalida(Salida salida){
        salidas.add(salida);
    }

    public void removeSalida(Salida salida){
        salidas.remove(salida);
    }
}
