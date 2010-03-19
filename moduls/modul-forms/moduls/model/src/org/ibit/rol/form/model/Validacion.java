package org.ibit.rol.form.model;

import java.io.Serializable;

public class Validacion implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private String[] valores = new String[0];

    public String[] getValores() {
        return valores;
    }

    public void setValores(String[] valores) {
        this.valores = valores;
    }

    public void addValor(String valor) {
        int newLength = valores.length + 1;
        String[] newValores = new String[newLength];
        System.arraycopy(valores, 0, newValores, 0, valores.length);
        newValores[newLength - 1] = valor;
        valores = newValores;
    }

    private Campo campo;

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    private Mascara mascara;

    public Mascara getMascara() {
        return mascara;
    }

    public void setMascara(Mascara mascara) {
        this.mascara = mascara;
    }
}
