package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

/**
 * Representación de los datos introducidos en un formulario.
 */
public class InstanciaBean implements Serializable {

    private static final long serialVersionUID = 2L;

    private Locale locale;
    private String modelo;
    private int version = 1;
    private String perfil;
    private List dataMaps = new ArrayList();
    private Map anexos = new HashMap();

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public List getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(List dataMaps) {
        this.dataMaps = dataMaps;
    }

    public void addDataMap(Map dataMap) {
        dataMaps.add(dataMap);
    }

    public Map getAnexos() {
        return anexos;
    }

    public void setAnexos(Map anexos) {
        this.anexos = anexos;
    }
}
