package org.ibit.rol.form.model;

public class ComboBox extends Campo {

    private boolean obligatorio;

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isIndexed() {
        return true;
    }
    
    private boolean indiceAlfabetico;

	public boolean isIndiceAlfabetico() {
		return indiceAlfabetico;
	}

	public void setIndiceAlfabetico(boolean indiceAlfabetico) {
		this.indiceAlfabetico = indiceAlfabetico;
	}
   
    
}
