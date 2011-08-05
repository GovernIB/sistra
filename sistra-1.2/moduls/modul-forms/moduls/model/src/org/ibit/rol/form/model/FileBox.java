package org.ibit.rol.form.model;

public class FileBox extends Campo {

    public FileBox() {
        tipoValor = "java.lang.String";
    }

    private int maxSize;

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isIndexed() {
        return false;
    }

    private boolean multifichero;

    public boolean isMultifichero() {
        return multifichero;
    }

    public void setMultifichero(boolean multifichero) {
        this.multifichero = multifichero;
    }
}