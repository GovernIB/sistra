package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Conjunto de componentes para reusar.
 */
public class Paleta implements Serializable {

    private Long id;
    private String nombre;
    private List componentes = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List getComponentes() {
        return componentes;
    }

    protected void setComponentes(List componentes) {
        this.componentes = componentes;
    }

    public void addComponente(Componente componente) {
        componente.setPaleta(this);
        componente.setOrden(componentes.size());
        componentes.add(componente);
    }

    public void removeComponente(Componente componente) {
        int ind = componentes.indexOf(componente);
        componentes.remove(ind);
        for (int i = ind; i < componentes.size(); i++) {
            Componente comp = (Componente) componentes.get(i);
            comp.setOrden(i);
        }
    }
}
