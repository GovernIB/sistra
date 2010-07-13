package org.ibit.rol.form.model;

public class AyudaPantalla extends Traducible {

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

    private PerfilUsuario perfil;

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public void addTraduccion(String lang, TraAyudaPantalla traduccion) {
        setTraduccion(lang, traduccion);
    }
}
