package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.Set;

public class Mascara implements Serializable {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*
        Empra les dues propietats, la nova "variables" de la taula RFR_MASVAR i
        l'antiga "variablesOld" de dins el camp binary MAS_VARIAB.
     */
    public String[] getAllVariables() {
        if ((variables == null || variables.length == 0) && variablesOld != null) {
            variables = new String[variablesOld.length];
            System.arraycopy(variablesOld, 0, variables, 0, variables.length);
        }
        return variables;
    }

    public void setAllVariables(String[] allVariables) {
        // Método para evitar errors de lectura de Bean.
    }

    private String[] variables = new String[0];

    public String[] getVariables() {
        return variables;
    }

    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public void addVariable(String variable) {
        int newLength = variables.length + 1;
        String[] newVariables = new String[newLength];
        System.arraycopy(variables, 0, newVariables, 0, variables.length);
        newVariables[newLength - 1] = variable;
        variables = newVariables;
    }

    private String[] variablesOld = new String[0];

    public String[] getVariablesOld() {
        return variablesOld;
    }

    public void setVariablesOld(String[] variablesOld) {
        this.variablesOld = variablesOld;
    }

    private Set validaciones;

    public Set getValidaciones() {
        return validaciones;
    }

    public void setValidaciones(Set validaciones) {
        this.validaciones = validaciones;
    }
}
