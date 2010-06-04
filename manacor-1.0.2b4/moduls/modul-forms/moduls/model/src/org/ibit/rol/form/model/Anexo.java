package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Anexo de un formulario.
 */
public class Anexo implements Serializable {

    private String name;
    private String contentType;
    private byte[] data;

    public Anexo() {
    }

    public Anexo(String name, String contentType, byte[] data) {
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Anexo anexo = (Anexo) o;

        if (contentType != null ? !contentType.equals(anexo.contentType) : anexo.contentType != null) return false;
        if (!Arrays.equals(data, anexo.data)) return false;
        if (name != null ? !name.equals(anexo.name) : anexo.name != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }
}
