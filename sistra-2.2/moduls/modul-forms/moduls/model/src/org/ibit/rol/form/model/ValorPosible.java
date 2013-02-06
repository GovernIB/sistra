package org.ibit.rol.form.model;

public class ValorPosible extends Traducible {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    private String parentValor;

    public String getParentValor() {
        return parentValor;
    }

    public void setParentValor(String parentId) {
        this.parentValor = parentId;
    }

    private Campo campo;

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private boolean defecto;

    public boolean isDefecto() {
        return defecto;
    }

    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
    }

    public void addTraduccion(String lang, TraValorPosible traduccion) {
        setTraduccion(lang, traduccion);
    }

}
