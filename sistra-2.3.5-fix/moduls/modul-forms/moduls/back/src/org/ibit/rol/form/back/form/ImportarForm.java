package org.ibit.rol.form.back.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * Formulario para elegir el tipo de componente a crear y a que
 * pantalla pertenecerà.
 */
public class ImportarForm extends ValidatorForm {

    private transient FormFile fitxer;
   
    public FormFile getFitxer() {
        return fitxer;
    }

    public void setFitxer(FormFile fitxer) {
        this.fitxer = fitxer;
    }

    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private String generarDuplicados;    
	public String getGenerarDuplicados() {
		return generarDuplicados;
	}

	public void setGenerarDuplicados(String generarDuplicados) {
		this.generarDuplicados = generarDuplicados;
	}
}

