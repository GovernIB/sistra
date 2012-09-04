package org.ibit.rol.form.model;

public class ListBox extends Campo {

    public ListBox() {
        tipoValor = "java.lang.String[]";
    }

    private int altura;

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public boolean isIndexed() {
        return true;
    }

    private boolean seleccionMultiple;
    
	public boolean isSeleccionMultiple() {
		return seleccionMultiple;
	}

	public void setSeleccionMultiple(boolean seleccionMultiple) {
		this.seleccionMultiple = seleccionMultiple;
	}
}
