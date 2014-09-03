package org.ibit.rol.form.model;

public class Captcha extends Campo {

    public boolean isIndexed() {
        return false;
    }

    public ValorPosible getValorPosible() {
        if (getValoresPosibles().isEmpty()) {
            ValorPosible vp = new ValorPosible();
            vp.setDefecto(true);
            addValorPosible(vp);
        }

        return (ValorPosible) getValoresPosibles().get(0);
    }

    public void setTraduccion(String idioma, Traduccion traduccion) {
        super.setTraduccion(idioma, traduccion);
        if (traduccion == null) {
            getValorPosible().setTraduccion(idioma, null);
        }
    }

}
