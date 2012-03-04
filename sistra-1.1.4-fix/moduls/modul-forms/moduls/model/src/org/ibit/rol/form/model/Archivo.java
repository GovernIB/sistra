package org.ibit.rol.form.model;

import java.io.Serializable;

/**
 * Representaci&oacute;n de un fichero.
 */
public class Archivo implements Serializable {

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

    private String tipoMime;

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    private int pesoBytes;

    public int getPesoBytes() {
        return pesoBytes;
    }

    public void setPesoBytes(int pesoBytes) {
        this.pesoBytes = pesoBytes;
    }

    private byte[] datos;

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
}
