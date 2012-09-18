package org.ibit.rol.form.model;

public class TraFormulario implements Traduccion {

    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String nombreEntidad1;

    public String getNombreEntidad1() {
        return nombreEntidad1;
    }

    public void setNombreEntidad1(String nombreEntidad1) {
        this.nombreEntidad1 = nombreEntidad1;
    }

    private String nombreEntidad2;

    public String getNombreEntidad2() {
        return nombreEntidad2;
    }

    public void setNombreEntidad2(String nombreEntidad2) {
        this.nombreEntidad2 = nombreEntidad2;
    }

    private Archivo plantilla;

    public Archivo getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(Archivo plantilla) {
        this.plantilla = plantilla;
    }
}
