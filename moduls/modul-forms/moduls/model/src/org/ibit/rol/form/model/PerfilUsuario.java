package org.ibit.rol.form.model;

public class PerfilUsuario extends Traducible {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String codigoEstandard;

    public String getCodigoEstandard() {
        return codigoEstandard;
    }

    public void setCodigoEstandard(String codigoEstandard) {
        this.codigoEstandard = codigoEstandard;
    }

    private String pathIconografia;

    public String getPathIconografia() {
        return pathIconografia;
    }

    public void setPathIconografia(String pathIconografia) {
        this.pathIconografia = pathIconografia;
    }

    public void addTraduccion(String lang, TraPerfilUsuario traduccion) {
        setTraduccion(lang, traduccion);
    }
}
