package es.caib.bantel.model;

import java.io.Serializable;
// Generated 07-mar-2006 18:19:33 by Hibernate Tools 3.1.0 beta3


/**
 * Representaci&oacute; de un idioma.
 */

public class Idioma implements Serializable {

    public static final String DEFAULT = "es";

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
