package es.caib.sistra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Representa todos los componentes que disponen de traducci&oacute;n.
 */

public class Traducible implements Serializable
{
	private Map traducciones = new HashMap();

    public Map getTraducciones() {
        return traducciones;
    }

    protected void setTraducciones(Map traducciones) {
        this.traducciones = traducciones;
    }

    public Traduccion getTraduccion(String idioma) {
        return (Traduccion) traducciones.get(idioma);
    }

    public void setTraduccion(String idioma, Traduccion traduccion) {
        if (traduccion == null) {
            traducciones.remove(idioma);
        } else {
            traducciones.put(idioma, traduccion);
        }
    }

    public Set getLangs() {
        return this.traducciones.keySet();
    }

    protected String currentLang = Idioma.DEFAULT;

    public String getCurrentLang() {
        return currentLang;
    }

    public void setCurrentLang(String currentLang) {
        if (currentLang != null && getLangs().contains(currentLang)) {
            this.currentLang = currentLang;
        }
    }

    public Traduccion getTraduccion() {
        return (Traduccion) traducciones.get(currentLang);
    }
    
}    
