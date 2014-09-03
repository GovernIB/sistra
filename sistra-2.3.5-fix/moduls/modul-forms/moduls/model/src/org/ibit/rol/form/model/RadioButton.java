package org.ibit.rol.form.model;

public class RadioButton extends Campo {

    public boolean isIndexed() {
        return true;
    }
    
    /**
     * Orientacion opciones: Horinzontal H / Vertical V.
     */
    private String orientacion = "H";

	public String getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(String orientacion) {
		this.orientacion = orientacion;
	}
    
    
}
