package org.ibit.rol.form.model;

public class Label extends Traducible implements Componente {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Pantalla pantalla;

    public Pantalla getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    public boolean hasPantalla() {
        return pantalla != null;
    }

    private Paleta paleta;

    public Paleta getPaleta() {
        return paleta;
    }

    public void setPaleta(Paleta paleta) {
        this.paleta = paleta;
    }

    private String nombreLogico;

    public String getNombreLogico() {
        return nombreLogico;
    }

    public void setNombreLogico(String nombreLogico) {
        this.nombreLogico = nombreLogico;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private int posicion;

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    private String estilo;

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public void addTraduccion(String lang, TraLabel traduccion) {
        setTraduccion(lang, traduccion);
    }

}
