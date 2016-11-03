package es.caib.sistra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Modeliza configuración dinámica del formulario (especificada por script)
 */
public class ConfiguracionFormulario implements Serializable {
	
	/**
	 * Campos solo lectura
	 */
	ArrayList camposReadOnly;
	
	/**
	 * Indica si el formulario es solo lectura
	 */
	boolean readOnly=false;
	
	/**
	 * Propiedades específicas del gestor de formularios
	 */
	private HashMap propiedades = new HashMap();
	

	public ArrayList getCamposReadOnly() {
		return camposReadOnly;
	}

	public void setCamposReadOnly(ArrayList camposReadOnly) {
		this.camposReadOnly = camposReadOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
		
	public HashMap getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(HashMap propiedades) {
		this.propiedades = propiedades;
	}

}
